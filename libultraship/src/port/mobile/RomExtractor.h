#pragma once

#ifdef __ANDROID__

#include <string>
#include <vector>
#include <cstdint>

namespace Ship {
namespace Mobile {

struct RomExtractionResult {
    bool success;
    std::string errorMessage;
    size_t extractedSize;
};

class RomExtractor {
public:
    /**
     * Extract Mario Kart 64 ROM to O2R format
     * @param romFilePath Path to the input .z64 ROM file
     * @param outputPath Path where the mk64.o2r file should be created
     * @param progressCallback Optional callback for progress updates (0.0 to 1.0)
     * @return RomExtractionResult with success status and details
     */
    static RomExtractionResult ExtractRomToO2R(
        const std::string& romFilePath,
        const std::string& outputPath,
        std::function<void(float)> progressCallback = nullptr
    );

private:
    // ROM validation
    static bool ValidateRomFile(const std::string& romFilePath, std::string& version);
    static bool ValidateRomChecksum(const std::vector<uint8_t>& romData, const std::string& version);
    
    // MIO0 decompression (from existing libmio0.c logic)
    static std::vector<uint8_t> DecompressMIO0(const uint8_t* compressedData, size_t compressedSize);
    static bool IsMIO0Header(const uint8_t* data);
    
    // Asset extraction helpers
    static bool ExtractAsset(
        const std::vector<uint8_t>& romData,
        const std::string& version,
        uint32_t offset,
        uint32_t size,
        std::vector<uint8_t>& output
    );
    
    // O2R file creation
    static bool CreateO2RFile(
        const std::string& outputPath,
        const std::vector<std::vector<uint8_t>>& extractedAssets
    );
    
    // Expected ROM checksums for validation
    static const std::map<std::string, std::string> ROM_CHECKSUMS;
};

} // namespace Mobile
} // namespace Ship

#endif // __ANDROID__
