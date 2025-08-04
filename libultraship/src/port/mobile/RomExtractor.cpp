#ifdef __ANDROID__

#include "RomExtractor.h"
#include <fstream>
#include <filesystem>
#include <cstring>
#include <spdlog/spdlog.h>

// Include the existing Companion system
#include "../../torch/src/Companion.h"

namespace Ship {

AndroidRomExtractionResult AndroidRomExtractor::ExtractRomToO2R(
    const std::string& romFilePath,
    const std::string& outputPath,
    std::function<void(float)> progressCallback) {
    
    SPDLOG_INFO("Starting ROM extraction: {} -> {}", romFilePath, outputPath);
    
    AndroidRomExtractionResult result = {false, "", 0};
    
    try {
        // Update progress
        if (progressCallback) progressCallback(0.1f);
        
        // Validate ROM file
        if (!ValidateRomFile(romFilePath)) {
            result.errorMessage = "Invalid or unsupported ROM file";
            return result;
        }
        
        // Update progress
        if (progressCallback) progressCallback(0.2f);
        
        // Read ROM file
        std::ifstream romFile(romFilePath, std::ios::binary | std::ios::ate);
        if (!romFile) {
            result.errorMessage = "Failed to open ROM file";
            return result;
        }
        
        // Get file size and read data
        std::streamsize romSize = romFile.tellg();
        romFile.seekg(0, std::ios::beg);
        
        std::vector<uint8_t> romData(romSize);
        if (!romFile.read(reinterpret_cast<char*>(romData.data()), romSize)) {
            result.errorMessage = "Failed to read ROM file";
            return result;
        }
        romFile.close();
        
        // Update progress
        if (progressCallback) progressCallback(0.4f);
        
        // Use the existing Companion system to extract the ROM
        bool success = ExtractUsingCompanion(romData, outputPath, 
            [&](float progress) {
                // Scale progress from 0.4 to 1.0 for the extraction phase
                float scaledProgress = 0.4f + (progress * 0.6f);
                if (progressCallback) progressCallback(scaledProgress);
            }
        );
        
        if (!success) {
            result.errorMessage = "ROM extraction failed";
            return result;
        }
        
        // Verify the output file was created
        if (std::filesystem::exists(outputPath)) {
            result.extractedSize = std::filesystem::file_size(outputPath);
            result.success = true;
            SPDLOG_INFO("ROM extraction completed successfully. Output size: {} bytes", result.extractedSize);
        } else {
            result.errorMessage = "Failed to create output file";
        }
        
    } catch (const std::exception& e) {
        result.errorMessage = std::string("Extraction failed: ") + e.what();
        SPDLOG_ERROR("ROM extraction error: {}", result.errorMessage);
    }
    
    return result;
}

bool RomExtractor::ValidateRomFile(const std::string& romFilePath, std::string& version) {
    std::ifstream file(romFilePath, std::ios::binary);
    if (!file) {
        return false;
    }
    
    // Check file size (Mario Kart 64 ROMs are typically 8MB or 12MB)
    file.seekg(0, std::ios::end);
    size_t fileSize = file.tellg();
    file.seekg(0, std::ios::beg);
    
    if (fileSize < 0x800000 || fileSize > 0xC00000) { // 8MB to 12MB range
        return false;
    }
    
    // Read ROM header
    uint8_t header[64];
    file.read(reinterpret_cast<char*>(header), 64);
    
    // Check for N64 ROM signature
    if (header[0] != 0x80 || header[1] != 0x37 || header[2] != 0x12 || header[3] != 0x40) {
        return false;
    }
    
    // Check for Mario Kart 64 game ID
    char gameId[5] = {0};
    std::memcpy(gameId, header + 0x3B, 4);
    
    if (std::string(gameId) != "NKTE") { // Mario Kart 64 game ID
        return false;
    }
    
    // Determine version based on ROM characteristics
    // This is simplified - you'd need more sophisticated version detection
    version = "us"; // Default to US version
    
    return true;
}

bool RomExtractor::ValidateRomChecksum(const std::vector<uint8_t>& romData, const std::string& version) {
    // This is a simplified checksum validation
    // In practice, you'd implement proper N64 ROM checksum validation
    return true; // Skip checksum validation for now
}

std::vector<uint8_t> RomExtractor::DecompressMIO0(const uint8_t* compressedData, size_t compressedSize) {
    std::vector<uint8_t> decompressed;
    
    if (!IsMIO0Header(compressedData)) {
        return decompressed;
    }
    
    // Read MIO0 header
    uint32_t decompressedSize = read_u32_be(compressedData + 4);
    uint32_t compressedOffset = read_u32_be(compressedData + 8);
    uint32_t uncompressedOffset = read_u32_be(compressedData + 12);
    
    decompressed.resize(decompressedSize);
    
    // Use the existing MIO0 decompression logic
    unsigned int end;
    int result = mio0_decode(compressedData, decompressed.data(), &end);
    
    if (result != 0) {
        decompressed.clear();
    }
    
    return decompressed;
}

bool RomExtractor::IsMIO0Header(const uint8_t* data) {
    return data && std::memcmp(data, "MIO0", 4) == 0;
}

bool RomExtractor::CreateO2RFile(
    const std::string& outputPath,
    const std::vector<std::vector<uint8_t>>& extractedAssets) {
    
    try {
        // Create output directory if it doesn't exist
        std::filesystem::path outputDir = std::filesystem::path(outputPath).parent_path();
        if (!std::filesystem::exists(outputDir)) {
            std::filesystem::create_directories(outputDir);
        }
        
        // For now, create a simple concatenated file
        // In practice, you'd need to implement the proper O2R format
        std::ofstream outFile(outputPath, std::ios::binary);
        if (!outFile) {
            return false;
        }
        
        // Write a simple header
        const char* header = "O2R\x00";
        outFile.write(header, 4);
        
        // Write number of assets
        uint32_t assetCount = static_cast<uint32_t>(extractedAssets.size());
        outFile.write(reinterpret_cast<const char*>(&assetCount), sizeof(assetCount));
        
        // Write asset data
        for (const auto& asset : extractedAssets) {
            uint32_t assetSize = static_cast<uint32_t>(asset.size());
            outFile.write(reinterpret_cast<const char*>(&assetSize), sizeof(assetSize));
            outFile.write(reinterpret_cast<const char*>(asset.data()), asset.size());
        }
        
        outFile.close();
        return true;
        
    } catch (const std::exception& e) {
        SPDLOG_ERROR("Failed to create O2R file: {}", e.what());
        return false;
    }
}

} // namespace Mobile
} // namespace Ship

#endif // __ANDROID__
