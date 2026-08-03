/*
 @licstart  The following is the entire license notice for the JavaScript code in this file.

 The MIT License (MIT)

 Copyright (C) 1997-2020 by Dimitri van Heesch

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 and associated documentation files (the "Software"), to deal in the Software without restriction,
 including without limitation the rights to use, copy, modify, merge, publish, distribute,
 sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or
 substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 @licend  The above is the entire license notice for the JavaScript code in this file
*/
var NAVTREE =
[
  [ "SpaghettiKart", "index.html", [
    [ "Actors", "actorsmenu.html", "actorsmenu" ],
    [ "Building SpagettiKart", "md_docs_2BUILDING.html", [
      [ "Windows", "md_docs_2BUILDING.html#windows", [
        [ "Developing SpaghettiKart", "md_docs_2BUILDING.html#developing-spaghettikart", [
          [ "Visual Studio", "md_docs_2BUILDING.html#visual-studio", null ],
          [ "Visual Studio Code or another editor", "md_docs_2BUILDING.html#visual-studio-code-or-another-editor", null ]
        ] ],
        [ "Generating the distributable", "md_docs_2BUILDING.html#generating-the-distributable", null ],
        [ "Additional CMake Targets", "md_docs_2BUILDING.html#additional-cmake-targets", [
          [ "Clean", "md_docs_2BUILDING.html#clean", null ]
        ] ]
      ] ],
      [ "Linux", "md_docs_2BUILDING.html#linux", [
        [ "Clone the repo and enter the directory", "md_docs_2BUILDING.html#clone-the-repo-and-enter-the-directory", null ],
        [ "Manual", "md_docs_2BUILDING.html#manual", [
          [ "Install dependencies", "md_docs_2BUILDING.html#install-dependencies", [
            [ "Debian/Ubuntu", "md_docs_2BUILDING.html#debianubuntu", null ],
            [ "Arch", "md_docs_2BUILDING.html#arch", null ],
            [ "Fedora", "md_docs_2BUILDING.html#fedora", null ],
            [ "openSUSE", "md_docs_2BUILDING.html#opensuse", null ]
          ] ],
          [ "Build", "md_docs_2BUILDING.html#build", null ]
        ] ],
        [ "Docker", "md_docs_2BUILDING.html#docker", [
          [ "Create the docker container", "md_docs_2BUILDING.html#create-the-docker-container", null ],
          [ "Configure the project", "md_docs_2BUILDING.html#configure-the-project", null ],
          [ "Compile the project", "md_docs_2BUILDING.html#compile-the-project", null ]
        ] ],
        [ "Generate a distributable", "md_docs_2BUILDING.html#generate-a-distributable", null ],
        [ "Additional CMake Targets", "md_docs_2BUILDING.html#additional-cmake-targets-1", [
          [ "Clean", "md_docs_2BUILDING.html#clean-1", null ]
        ] ]
      ] ],
      [ "macOS", "md_docs_2BUILDING.html#macos", [
        [ "Generating a distributable", "md_docs_2BUILDING.html#generating-a-distributable", null ],
        [ "Additional CMake Targets", "md_docs_2BUILDING.html#additional-cmake-targets-2", [
          [ "Clean", "md_docs_2BUILDING.html#clean-2", null ]
        ] ]
      ] ],
      [ "Android", "md_docs_2BUILDING.html#android", [
        [ "Requirements", "md_docs_2BUILDING.html#requirements", null ],
        [ "The one thing that is not built by Gradle", "md_docs_2BUILDING.html#the-one-thing-that-is-not-built-by-gradle", null ],
        [ "Build", "md_docs_2BUILDING.html#build-1", null ],
        [ "Keeping the SDL glue in sync", "md_docs_2BUILDING.html#keeping-the-sdl-glue-in-sync", null ]
      ] ],
      [ "Getting CI to work on your fork", "md_docs_2BUILDING.html#getting-ci-to-work-on-your-fork", [
        [ "Runner on Windows", "md_docs_2BUILDING.html#runner-on-windows", null ],
        [ "Runner on UNIX systems", "md_docs_2BUILDING.html#runner-on-unix-systems", null ]
      ] ]
    ] ],
    [ "Make a Character", "charactermenu.html", "charactermenu" ],
    [ "Courses", "coursesmenu.html", "coursesmenu" ],
    [ "Custom Audio", "md_docs_2custom-audio.html", [
      [ "Example:", "md_docs_2custom-audio.html#example", null ],
      [ "Future plans", "md_docs_2custom-audio.html#future-plans-1", null ]
    ] ],
    [ "faq", "md_docs_2faq.html", null ],
    [ "Migration: Beta to v1.0", "md_docs_2migrations.html", [
      [ "Overview", "md_docs_2migrations.html#overview", null ],
      [ "Migration Script", "md_docs_2migrations.html#migration-script", [
        [ "Usage", "md_docs_2migrations.html#usage", null ],
        [ "Generated Files", "md_docs_2migrations.html#generated-files", null ]
      ] ],
      [ "Path Changes", "md_docs_2migrations.html#path-changes", [
        [ "Textures", "md_docs_2migrations.html#textures", [
          [ "Track Textures", "md_docs_2migrations.html#track-textures", null ],
          [ "Kart Textures", "md_docs_2migrations.html#kart-textures", null ],
          [ "Other Textures", "md_docs_2migrations.html#other-textures", null ]
        ] ]
      ] ],
      [ "Kart Frame Textures", "md_docs_2migrations.html#kart-frame-textures", [
        [ "Old Structure", "md_docs_2migrations.html#old-structure", null ],
        [ "New Structure", "md_docs_2migrations.html#new-structure", null ]
      ] ],
      [ "New Folder Structure", "md_docs_2migrations.html#new-folder-structure", null ],
      [ "Manual Migration", "md_docs_2migrations.html#manual-migration", null ],
      [ "Troubleshooting", "md_docs_2migrations.html#troubleshooting", null ],
      [ "See Also", "md_docs_2migrations.html#see-also-1", null ]
    ] ],
    [ "Modding", "modding.html", [
      [ "General Structure", "modding.html#general-structure", [
        [ "Supported Formats", "modding.html#supported-formats", null ],
        [ "Mod Loading Order", "modding.html#mod-loading-order", null ]
      ] ],
      [ "Getting Started", "modding.html#getting-started", null ],
      [ "Mod Types", "modding.html#mod-types", null ]
    ] ],
    [ "mods.toml File Structure", "md_docs_2mods-toml.html", [
      [ "Location", "md_docs_2mods-toml.html#location", null ],
      [ "Basic Structure", "md_docs_2mods-toml.html#basic-structure", null ],
      [ "Complete Structure", "md_docs_2mods-toml.html#complete-structure", null ],
      [ "Fields Reference", "md_docs_2mods-toml.html#fields-reference", [
        [ "[mod] Section", "md_docs_2mods-toml.html#mod-section", null ],
        [ "[dependencies] Section", "md_docs_2mods-toml.html#dependencies-section", [
          [ "Version Requirements", "md_docs_2mods-toml.html#version-requirements", null ]
        ] ]
      ] ],
      [ "Core Dependencies", "md_docs_2mods-toml.html#core-dependencies", null ],
      [ "Validation", "md_docs_2mods-toml.html#validation", null ],
      [ "Load Order", "md_docs_2mods-toml.html#load-order", null ],
      [ "Best Practices", "md_docs_2mods-toml.html#best-practices", null ],
      [ "Migration Script Support", "md_docs_2mods-toml.html#migration-script-support", null ],
      [ "See Also", "md_docs_2mods-toml.html#see-also", null ]
    ] ],
    [ "Sequence Information (Music)", "md_docs_2sequences-information.html", [
      [ "Race Tracks", "md_docs_2sequences-information.html#race-tracks", null ],
      [ "Battle Courses", "md_docs_2sequences-information.html#battle-courses", null ],
      [ "General", "md_docs_2sequences-information.html#general", null ]
    ] ],
    [ "Texture Pack", "md_docs_2textures-pack.html", [
      [ "Example:", "md_docs_2textures-pack.html#example-1", null ],
      [ "Tools To Help", "md_docs_2textures-pack.html#tools-to-help-1", null ],
      [ "Future plans", "md_docs_2textures-pack.html#future-plans-2", null ]
    ] ],
    [ "Returning Tracks", "md_docs_2track__returns.html", [
      [ "Specifically Used to help find music and to see how courses are adapted in the future including refer...", "md_docs_2track__returns.html#specifically-used-to-help-find-music-and-to-see-how-courses-are-adapted-in-the-future-including-references-too", null ],
      [ "Battle Courses", "md_docs_2track__returns.html#battle-courses-1", null ],
      [ "Mario Kart World Remixes", "md_docs_2track__returns.html#mario-kart-world-remixes", null ]
    ] ],
    [ "Track Making", "trackmenu.html", "trackmenu" ],
    [ "Tutorials", "tutorials.html", "tutorials" ],
    [ "Bug List", "bug.html", null ],
    [ "Todo List", "todo.html", null ],
    [ "Namespaces", "namespaces.html", [
      [ "Namespace List", "namespaces.html", "namespaces_dup" ],
      [ "Namespace Members", "namespacemembers.html", [
        [ "All", "namespacemembers.html", "namespacemembers_dup" ],
        [ "Functions", "namespacemembers_func.html", null ],
        [ "Variables", "namespacemembers_vars.html", null ],
        [ "Typedefs", "namespacemembers_type.html", null ],
        [ "Enumerations", "namespacemembers_enum.html", null ],
        [ "Enumerator", "namespacemembers_eval.html", null ]
      ] ]
    ] ],
    [ "Data Structures", "annotated.html", [
      [ "Data Structures", "annotated.html", "annotated_dup" ],
      [ "Data Structure Index", "classes.html", null ],
      [ "Class Hierarchy", "hierarchy.html", "hierarchy" ],
      [ "Data Fields", "functions.html", [
        [ "All", "functions.html", "functions_dup" ],
        [ "Functions", "functions_func.html", "functions_func" ],
        [ "Variables", "functions_vars.html", "functions_vars" ],
        [ "Typedefs", "functions_type.html", null ],
        [ "Enumerations", "functions_enum.html", null ],
        [ "Enumerator", "functions_eval.html", null ],
        [ "Related Symbols", "functions_rela.html", null ]
      ] ]
    ] ],
    [ "Files", "files.html", [
      [ "File List", "files.html", "files_dup" ],
      [ "Globals", "globals.html", [
        [ "All", "globals.html", "globals_dup" ],
        [ "Functions", "globals_func.html", "globals_func" ],
        [ "Variables", "globals_vars.html", "globals_vars" ],
        [ "Typedefs", "globals_type.html", null ],
        [ "Enumerations", "globals_enum.html", null ],
        [ "Enumerator", "globals_eval.html", "globals_eval" ],
        [ "Macros", "globals_defs.html", "globals_defs" ]
      ] ]
    ] ]
  ] ]
];

var NAVTREEINDEX =
[
"ActorSpawnDataFactory_8cpp.html",
"Engine_8cpp.html#a91f46dc1e6f545d3ed1fcf8b69b8cbbb",
"Game_8cpp.html#a892acd610181b8a67f9bd4f74d242a4a",
"HMAS_8cpp.html#abf77f70dc931f91a9f09e7b54d8278f5",
"ModManager_8cpp.html#adeda271dcb5c5ad06356d21dd8002133",
"Thwomp_8h_source.html",
"____osSiDeviceBusy_8c.html",
"all__course__model_8h_source.html",
"camera_8h.html",
"ceremony__and__credits_8h.html#a69c2507b42aa5cce73b7d0538580e1ee",
"classACar.html#a7b80788be2823c90c45e7b97675d1d1f",
"classATree.html#a86aa963c74777e0cd4cb34e5628d85e1",
"classGameUI_1_1PortMenu.html#a6107d5040943b730f00a96293d335e23",
"classMooMooFarm.html#a7b794a6f0f3e7955abb3ec2e77c7b30d",
"classOLakitu.html#ada92132e451d313bdc7ad975abcd7ee1",
"classOTrophy.html#a4ca051131f7359fd3579a12c537d538e",
"classSF64_1_1Vec3sArray.html#af79fceba9764695cfe64d2e0015e734d",
"classToadsTurnpike.html#a35b679b83f24475ff3944f043be65207",
"classTrackEditor_1_1LightObject.html#a1e38fe6926857177dcc6ee9f651f3c92",
"classpfd_1_1internal_1_1executor.html#a2d46b9964c60985ea8406c4092393be3",
"code__800029B0_8h.html#a40f34d625901521dc5667a667e65b50c",
"code__80005FD0_8c.html#a62890ad61462895fe84a0a5c69bd041b",
"code__80005FD0_8c.html#acfbf60571bb983113da4af3008afa5b8",
"code__80005FD0_8h.html#a6a3987fc720b4d6bdb1566d092553bcaa4fb47001fdd05308186aa4bb0ddac6dd",
"code__80057C60_8c.html#a0afc173068c27aec26ff0eaab3ff7287",
"code__80057C60_8c.html#aa5436f1ea8ccb9760df83c24504ac49f",
"code__80057C60__var_8c.html#a75103c62c4152073d1e021526563d3df",
"code__80086E70_8c.html#a8e51cb92b1182f52f501900ce4761c0b",
"code__800AF9B0_8c.html#a9ada41ff378d23569643b242db363489",
"controller_8h.html#a8998d13a95bfba3569a75c9bad69b55e",
"defines_8h.html#a25593d987e5cdf0b1ca5393a3e9ffd3a",
"dir_03cd025427b69456b032656e22b85127.html",
"effects_8c.html#ab044d6f7bf1cacf740cf0a27ea0902b5",
"external_8c.html#a518bea6c084cccd3443950737f53a97a",
"freecam_8cpp.html#aebafc6430a3f2591e8051a3afae4a22d",
"globals_defs_d.html",
"hardware_8h.html#a55cf08cec537e733ed36b65b696fbe70",
"internal_8h.html#abab2a592b83806817d5ec0fbe1a3dc01",
"kart__attributes_8h.html#ac8bc0c5d3e3a587e9117f915eabf97f9",
"load_8c.html#ac575f0cdc6f35a84ad1f7a1bfff3b7cf",
"main_8c.html#aac38ea86530be6f64e3ab57c01c15aed",
"mario__sign_2render_8inc_8c_source.html",
"math__util__2_8h.html#a73a14e50dd3e7844fac7d576f0c8adb9",
"menu__items_8c.html",
"menu__items_8c.html#a8051db7229d932b267fdec53a1e794eb",
"menu__items_8c.html#afeda0e9157ad67898932d9d80ff056e7",
"menu__items_8h.html#a1dfc635b44835e2c75290f9a9a2c3643ad22cb60dbd47c371190c25c43d1e0f3a",
"menu__items_8h.html#a7ec398f85e36ce8e49572ff8a48eb379",
"menus_8c.html#a1f299d56d9d5919d9a86e79ffc87dfb0",
"menus_8h.html#ae19736bcf4a3556fd62ad21bf5773e7c",
"miniaudio_8h.html#a33b9e2f007755a6874286b5e73343c47",
"miniaudio_8h.html#a5f465aaee43ad6d2c8565a59eb889b7c",
"miniaudio_8h.html#a848b74eab52b4e53638994f2c6d8c937",
"miniaudio_8h.html#ab0cd15233d44be923dba289137b85d78",
"miniaudio_8h.html#ad42cab3d86b00dec352dd2d9e1a820d7",
"miniaudio_8h.html#afd8ace768b7c6de89c73b03dbd205c66",
"models_2tracks_2bowsers__castle_2bowsers__castle__data_8h.html#ae84f7e8f9496e50de0fa84c8c2b84b53",
"namespaceTrackEditor.html#a30c8330a0c072d3da8abbff873f05216",
"objects_8h.html#aa03ae475a84388a1d190944e65b6d9b0",
"osTimer_8c.html#aab1ae78b18f6455248b3b5c14bc74328",
"piint_8h.html#a457b66841d41a1bc457187634ec350f5",
"player__controller_8c.html#aaba04d0928ff8eaf7ef6c6cea1b93d7d",
"podium__ceremony__actors_8c.html#abe5e7d6664a9f39449fbf6848898baa1",
"race__logic_8c.html#aac5e7f61ed2c0c4e066e6b1811ecdeb0",
"render__objects_8c.html#a5dee2690185a9165ca8cff4b55c65111",
"render__objects_8h.html#a0dede1dbcea07bb01db30db2a6fd92af",
"render__objects_8h.html#ab74f89cbd39e9764c7b96fab3f4e44af",
"render__player_8h.html#a87f9baa216c4a841f52bebb0d06fb75f",
"save_8h.html#a4b14ee705e6fbbed261905de563202b2",
"ship2__model_8c.html#a8cf8b33b4d5b9cd20c672301723e8212",
"ship__model_8h.html#a937f6183abfc5a4983c2b9680c7b640f",
"sounds_8h.html#a86a19f9e4242ae461794ec16e961f2f5",
"starship__model_8c.html#ac8fdd420a3463c1854017c1ffdd312ca",
"structAudioBankSample.html",
"structController.html#a8c4688ece90c09c9c228512234b82fbb",
"structItemWindowObjects.html#a49994e5f5abbe673f179b691911f7913",
"structOSThread__ListHead.html",
"structPlayer.html#ac8b8e3beb59a1df500c48c3be883d06b",
"structSF64_1_1Vec2f.html",
"structSequencePlayer.html#acbad1c26535d2b38817b55ec1962195b",
"structTrainStuff.html#a1448e6a2fa95477bd90de1426f83a637",
"structUnkPlayerStruct258.html#ab258d69935e21839db26b729181f3069",
"struct____OSContRequesFormat.html#ae534a71fa222287340270d8522824ad7",
"structma__backend__callbacks.html#a6957420a1cb860465c28a7f6154d2b28",
"structma__delay.html",
"structma__engine__node.html#a466a876c027b0a87f3f4dbfa9d9f06bf",
"structma__lpf__node.html#a84d6333054175d4fb872fd7ddb301bde",
"structma__resource__manager__data__source.html",
"structstruct__8018EE10__entry.html#adf86a545995fd0e4b18fa546ce4e9e5b",
"textures_2tracks_2banshee__boardwalk_2banshee__boardwalk__data_8h.html#a2dcc3f597c10ebb75352c5f9accd72b2",
"textures_8c.html#a429a33118907da14457cefc8cee89bd3",
"textures_8c.html#a9dd1d5d4024529d5121b1a72fe1f1039",
"textures_8c.html#af7472fe75f16d19e777ee463885aff14",
"textures_8h.html#a4c9d54de51895e94788fd67e37c2eda0",
"textures_8h.html#aad3c7ff11c14dadd0134a4392e6b42fe",
"toads__turnpike__offsets_8h.html#aa9aa179efbd24581e12e70e5da3a964b",
"track_8h.html",
"trig__tables_8h.html",
"update__objects_8c.html#abef9e21ca75285520cc71d37b6ef71b5"
];

var SYNCONMSG = 'click to disable panel synchronization';
var SYNCOFFMSG = 'click to enable panel synchronization';