;ALL CONSTANTS OF THE MOD

;[COLLISIONS]

Const HIT_MAP% = 1
Const HIT_PLAYER% = 2
Const HIT_ITEM% = 3
Const HIT_APACHE% = 4
Const HIT_178% = 5
Const HIT_DEAD% = 6

;[?]

;Const Infinity# = (999.0) ^ (99999.0)
Const NAN# = (-1.0) ^ (0.5)

Const ClrR = 50
Const ClrG = 50
Const ClrB = 50

;{~--<MOD>--~}

;[FILES]

GFXPath$ = scpModding_ProcessFilePath$("GFX\")

;[SOUNDS]

SFXPath$ = scpModding_ProcessFilePath$("SFX\")

;[FONTS]

Const MaxFontAmount% = 5
Const MaxCreditsFontAmount% = 2

FontPath$ = scpModding_ProcessFilePath$("GFX\"+"font\")

;{~--<END>--~}

;[TEXTURES]

Const MaxDTextures = 18

;{~--<MOD>--~}

Const MaxDecalTextureIDAmount = 24
Const MaxOtherTextureIDAmount = 21
Const MaxParticleTextureIDAmount = 10
Const MaxLightSpriteTextureIDAmount = 11
Const MaxOverlayIDAmount = 17
Const MaxOverlayTextureIDAmount = 23

;[OBJECTS]

Const MaxOBJTunnelIDAmount = 7
Const MaxMonitorIDAmount = 3
Const MaxDoorIDAmount = 11
Const MaxButtonIDAmount = 5
Const MaxLeverIDAmount = 2
Const MaxCamIDAmount = 2
Const MaxOtherModelsIDAmount = 2

;{~--<END>--~}

;[FOREST GENERATION]

Const gridsize% = 10
Const deviation_chance% = 40 ;out of 100
Const branch_chance% = 65
Const branch_max_life% = 4
Const branch_die_chance% = 18
Const max_deviation_distance% = 3
Const return_chance% = 27
Const center = 5 ;(gridsize-1) / 2

;[MAP]

Const MaxRoomLights% = 32
Const MaxRoomEmitters% = 8
Const MaxRoomObjects% = 30

Const ROOM1% = 1
Const ROOM2% = 2
Const ROOM2C% = 3
Const ROOM3% = 4
Const ROOM4% = 5

Const ZONEAMOUNT = 3

Const gridsz% = 19 ;Same size as the main map itself (better for the map creator)

;{~--<MOD>--~}

MapPath$ = scpModding_ProcessFilePath$("GFX\"+"map\")

;[3D MENU]

Const MaxRenderingObjAmount = 3
Const MaxSpriteAmount = 3
Const MaxMiscAmount = 11

Const MaxMenuLightsAmount = 11
Const MaxMenuLightSpritesAmount = 11
Const MaxMenuLightSprites2Amount = 11

;[PLAYER]

Const SubjectName$ = "Subject D-9341"

;{~--<END>--~}

;[ACHIEVEMENTS]

Const MAXACHIEVEMENTS = 59

Const Achv008% = 0, Achv012% = 1, Achv035% = 2, Achv049% = 3, Achv055 = 4,  Achv079% = 5, Achv096% = 6, Achv106% = 7, Achv148% = 8
Const Achv205 = 9, Achv294% = 10, Achv372% = 11, Achv420% = 12, Achv427 = 13, Achv500% = 14, Achv513% = 15, Achv714% = 16
Const Achv789% = 17, Achv860% = 18, Achv895% = 19, Achv914% = 20, Achv939% = 21, Achv966% = 22, Achv970 = 23
Const Achv1025% = 24, Achv1048 = 25, Achv1123 = 26, Achv1162% = 27, Achv1499% = 28

Const AchvConsole% = 29, AchvHarp% = 30, AchvKeter% = 31, AchvMaynard% = 32, AchvOmni% = 33
Const AchvPD% = 34, AchvSNAV% = 35, AchvTesla% = 36

;{~--<MOD>--~}

Const Achv005% = 37, Achv009% = 38, Achv066% = 39, Achv109% = 40, Achv178% = 41, Achv198% = 42, Achv207% = 43, Achv215% = 44 
Const Achv357% = 45, Achv402% = 46, Achv409% = 47, Achv447% = 48, Achv457% = 49, Achv650% = 50, Achv1033RU% = 51, Achv1079% = 52

Const AchvDuck% = 53, AchvGears% = 54, AchvMTF% = 55, AchvO5% = 56, AchvKeyCard6% = 57, AchvThaumiel% = 58

;{~--<END>--~}


;[NPCs]

;{~--<MOD>--~}

Const MaxNPCModelIDAmount = 40

NPCsPath$ = scpModding_ProcessFilePath$("GFX\"+"npcs\")

;{~--<END>--~}

Const NPCtype008_1% = 1, NPCtype035_Tentacle% = 2, NPCtype049% = 3, NPCtype049_2% = 4, NPCtype066% = 5
Const NPCtype096% = 6, NPCtype106% = 7, NPCtype173% = 8, NPCtype372% = 9, NPCtype513_1% = 10, NPCtype860_2% = 11
Const NPCtype939% = 12, NPCtype966% = 13, NPCtype1048_A% = 14, NPCtype1499_1% = 15

Const NPCtypeApache% = 16, NPCtypeClerk% = 17, NPCtypeD% = 18
Const NPCtypeGuard% = 19, NPCtypeMTF% = 20, NPCtypePdPlane% = 21

;{~--<MOD>--~}

Const NPCtype008_2% = 22, NPCtype049_3% = 23, NPCtype178_1% = 24, NPCtype457% = 25, NPCtype650% = 26

Const NPCtypeCI% = 27, NPCtypeMTF2% = 28, NPCtypeVehicle% = 29

;{~--<END>--~}

;[DIFFICULTY]

Const SAFE = 0
Const EUCLID = 1
Const KETER = 2

;{~--<MOD>--~}

Const THAUMIEL = 3

;{~--<END>--~}

Const CUSTOM = 4

Const SAVEANYWHERE = 0, SAVEONQUIT = 1, SAVEONSCREENS = 2, NOTSAVE = 3

Const EASY = 0, NORMAL = 1, HARD = 2

;[FMOD]

Const Freq = 44100 ;Hz
Const Channels = 64	;Standartwert
Const Flags	= 0
Const Mode	= 2	;Mode = 2 means that the sounds play in a loop
Const F_Offset = 0
Const Lenght = 0
Const MaxVol = 255
Const MinVol = 0
Const PanLeft = 0
Const PanRight = 255
Const PanMid = -1
Const AllChannel = -3
Const FreeChannel = -1

;[FULLSCREEN_FIX]

Const C_GWL_STYLE = -16
Const C_WS_POPUP = $80000000
Const C_HWND_TOP = 0
Const C_SWP_SHOWWINDOW = $0040

;[MUSIC]

MusicPath$ = scpModding_ProcessFilePath$("SFX\"+"Music\")
MusicPath2$ = scpModding_ProcessFilePath$("SFX\"+"Radio\UserTracks\")

;[VERSIONS]

Const GameVersionNumber$ = "1.3.11"
Const ModVersionNumber$ = "5.5.4.1"
Const RemasteredVersionNumber$ = "1.6"

;[OPTIONS]

Const OptionFile$ = "options.ini"

;[SAVE]

Const SavePath$ = "Saves\"

;{~--<MOD>--~}

;[ITEMS]

ItemsPath$ = scpModding_ProcessFilePath$("GFX\"+"items\")

;{~--<END>--~}

;~IDEal Editor Parameters:
;~C#Blitz3D