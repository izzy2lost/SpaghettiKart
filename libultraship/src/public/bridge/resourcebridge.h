#pragma once

#include "stdint.h"

#ifdef __cplusplus
#include "resource/type/Texture.h"
#include "resource/Resource.h"
#include <memory>

std::shared_ptr<Ship::IResource> ResourceLoad(const char* name);
std::shared_ptr<Ship::IResource> ResourceLoad(uint64_t crc);
template <class T> std::shared_ptr<T> ResourceLoad(const char* name) {
    return std::static_pointer_cast<T>(ResourceLoad(name));
}
template <class T> std::shared_ptr<T> ResourceLoad(uint64_t crc) {
    return std::static_pointer_cast<T>(ResourceLoad(crc));
}

extern "C" {
#endif

uint64_t ResourceGetCrcByName(const char* name);
const char* ResourceGetNameByCrc(uint64_t crc);
size_t ResourceGetSizeByName(const char* name);
size_t ResourceGetSizeByCrc(uint64_t crc);
uint8_t ResourceGetIsCustomByName(const char* name);
uint8_t ResourceGetIsCustomByCrc(uint64_t crc);
void* ResourceGetDataByName(const char* name);
void* ResourceGetDataByCrc(uint64_t crc);
uint16_t ResourceGetTexWidthByName(const char* name);
uint16_t ResourceGetTexWidthByCrc(uint64_t crc);
uint16_t ResourceGetTexHeightByName(const char* name);
uint16_t ResourceGetTexHeightByCrc(uint64_t crc);
size_t ResourceGetTexSizeByName(const char* name);
size_t ResourceGetTexSizeByCrc(uint64_t crc);
void ResourceLoadDirectory(const char* name);
void ResourceLoadDirectoryAsync(const char* name);
void ResourceDirtyDirectory(const char* name);
void ResourceDirtyByName(const char* name);
void ResourceDirtyByCrc(uint64_t crc);
void ResourceUnloadByName(const char* name);
void ResourceUnloadByCrc(uint64_t crc);
void ResourceUnloadDirectory(const char* name);
void ResourceClearCache();
void ResourceGetGameVersions(uint32_t* versions, size_t versionsSize, size_t* versionsCount);
uint32_t ResourceHasGameVersion(uint32_t hash);
uint32_t IsResourceManagerLoaded();

#ifdef __cplusplus
};
#endif
