#pragma once
#ifdef __cplusplus

#include "resource/archive/ArchiveManager.h"
#include "resource/archive/Archive.h"
#include "resource/archive/OtrArchive.h"
#include "resource/archive/O2rArchive.h"
#include "resource/ResourceManager.h"
#include "Context.h"
#include "window/Window.h"
#include "debug/Console.h"
#include "debug/CrashHandler.h"
#include "config/ConsoleVariable.h"
#include "config/Config.h"
#include "window/gui/ConsoleWindow.h"
#include "window/gui/GameOverlay.h"
#include "window/gui/Gui.h"
#include "window/gui/GuiMenuBar.h"
#include "window/gui/GuiElement.h"
#include "window/gui/GuiWindow.h"
#include "window/gui/InputEditorWindow.h"
#include "window/gui/StatsWindow.h"
#include "controller/controldevice/controller/mapping/keyboard/KeyboardScancodes.h"
#include "controller/controldevice/controller/Controller.h"
#include "controller/controldeck/ControlDeck.h"
#include "utils/binarytools/BinaryReader.h"
#include "utils/binarytools/MemoryStream.h"
#include "utils/binarytools/BinaryWriter.h"
#include "audio/Audio.h"
#include "audio/AudioPlayer.h"
#if defined(_WIN32)
#include "audio/WasapiAudioPlayer.h"
#endif
#include "audio/SDLAudioPlayer.h"
#ifdef __APPLE__
#include "utils/AppleFolderManager.h"
#endif
#endif
