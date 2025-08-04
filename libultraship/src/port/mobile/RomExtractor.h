#pragma once

#ifdef __ANDROID__

#include <string>
#include <vector>
#include <cstdint>
#include <functional>

namespace Ship {

struct AndroidRomExtractionResult {
    bool success;
    std::string errorMessage;
    size_t extractedSize;
};

class AndroidRomExtractor {
public:
    /**
     * Extract Mario Kart 64 ROM to O2R format using existing Companion system
     * @param romFilePath Path to the input .z64 ROM file
     * @param outputPath Path where the mk64.o2r file should be created
     * @param progressCallback Optional callback for progress updates (0.0 to 1.0)
     * @return AndroidRomExtractionResult with success status and details
     */
    static AndroidRomExtractionResult ExtractRomToO2R(
        const std::string& romFilePath,
        const std::string& outputPath,
        std::function<void(float)> progressCallback = nullptr
    );

private:
    // ROM validation using existing system
    static bool ValidateRomFile(const std::string& romFilePath);
    
    // Use existing Companion class for extraction
    static bool ExtractUsingCompanion(
        const std::vector<uint8_t>& romData,
        const std::string& outputPath,
        std::function<void(float)> progressCallback
    );
};

} // namespace Ship

#endif // __ANDROID__
