MenuWhite = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\menuwhite.png"))
MenuBlack = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\menublack.png"))
MaskImage MenuBlack, 255,255,0
Global QuickLoadIcon% = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\QuickLoading.png"))

ResizeImage(QuickLoadIcon, ImageWidth(QuickLoadIcon) * MenuScale, ImageHeight(QuickLoadIcon) * MenuScale)

For i = 0 To 3
	ArrowIMG(i) = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\arrow.png"))
	RotateImage(ArrowIMG(i), 90 * i)
	HandleImage(ArrowIMG(i), 0, 0)
Next

Global RandomSeed$

ms\MenuBlinkTimer%[0] = 1
ms\MenuBlinkTimer%[1] = 1

Global SelectedInputBox%

Global SaveMSG$

;nykyisen tallennuksen nimi ja samalla miss?� kansiossa tallennustiedosto sijaitsee saves-kansiossa
Global CurrSave$

Global SaveGameAmount%
Dim SaveGames$(SaveGameAmount+1) 
Dim SaveGameTime$(SaveGameAmount + 1)
Dim SaveGameDate$(SaveGameAmount + 1)
Dim SaveGameVersion$(SaveGameAmount + 1)

Global SavedMapsAmount% = 0
Dim SavedMaps$(SavedMapsAmount+1)
Dim SavedMapsAuthor$(SavedMapsAmount+1)

Global SelectedMap$

LoadSaveGames()

Global CurrLoadGamePage% = 0

Dim MenuBlinkTimer%(2), MenuBlinkDuration%(2)
MenuBlinkTimer%(0) = 1
MenuBlinkTimer%(1) = 1

Global MenuBack% = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\back.png"))
;Global Menu173% = LoadImage_Strict("GFX\menu\173back.jpg")
ResizeImage(MenuBack, ImageWidth(MenuBack) * MenuScale * 0.75, ImageHeight(MenuBack) * MenuScale * 0.75)
;ResizeImage(MenuBack, ImageWidth(MenuBack), ImageHeight(MenuBack))
;ResizeImage(Menu173, ImageWidth(Menu173) * MenuScale, ImageHeight(Menu173) * MenuScale)

Function UpdateMainMenu()
       Local fo.Fonts = First Fonts
	Local x%, y%, width%, height%, temp%
	Local fs.FPS_Settings = First FPS_Settings
	
	Color 0,0,0
	Rect 0,0,GraphicWidth,GraphicHeight,True
	
	ShowPointer()
	
	;Update3DMenu()
	DrawImage(MenuBack, 0, 0)
	
	If (MilliSecs2() Mod MenuBlinkTimer(0)) >= Rand(MenuBlinkDuration(0)) Then
		;DrawImage(Menu173, GraphicWidth - ImageWidth(Menu173), GraphicHeight - ImageHeight(Menu173))
	EndIf
		
	If Rand(300) = 1 Then
		ms\MenuBlinkTimer[0] = Rand(4000, 8000)
		ms\MenuBlinkDuration[0] = Rand(200, 500)
	End If
	
	AASetFont fo\Font[0]
	
	ms\MenuBlinkTimer[1] = ms\MenuBlinkTimer[1]-fs\FPSfactor[0]
	If ms\MenuBlinkTimer[1] < ms\MenuBlinkDuration[1] Then
		Color(50, 50, 50)
		AAText(ms\MenuStrX + Rand(-5, 5), ms\MenuStrY + Rand(-5, 5), ms\MenuStr, True)
		If ms\MenuBlinkTimer[1] < 0 Then
			ms\MenuBlinkTimer[1] = Rand(700, 800)
			ms\MenuBlinkDuration[1] = Rand(10, 35)
			ms\MenuStrX = Rand(700, 1000) * MenuScale
			ms\MenuStrY = Rand(100, 600) * MenuScale
			
			Select Rand(0, 23)
				Case 0, 2, 3
					ms\MenuStr = "DON'T BLINK"
				Case 4, 5
					ms\MenuStr = "Secure. Contain. Protect."
				Case 6, 7, 8
					ms\MenuStr = "You want happy endings? Fuck you."
				Case 9, 10, 11
					ms\MenuStr = "Sometimes we would have had time to scream."
				Case 12, 19
					ms\MenuStr = "homosexuals are SCPs"
				Case 13
					ms\MenuStr = "NO"
				Case 14
					ms\MenuStr = "black white black white black white gray"
				Case 15
					ms\MenuStr = "bentley is black"
				Case 16
					ms\MenuStr = "9341"
				Case 17
					ms\MenuStr = "It controls the doors"
				Case 18
					ms\MenuStr = "sharr's a fucking monkey"
				Case 20
					ms\MenuStr = "It has taken over everything"
				Case 21
					ms\MenuStr = "The spiral is growing"
				Case 22
					ms\MenuStr = Chr(34)+"Some kind of gestalt effect due to massive reality damage."+Chr(34)
				Case 23
				    ms\MenuStr = "SEBOTAN"
			End Select
		EndIf
	EndIf
	
	AASetFont fo\Font[1]
	
	If (Not MouseDown1)
		OnSliderID = 0
	EndIf
	
	Color 0,0,0
	AAText(GraphicWidth/2)+3,((GraphicHeight/7)*MenuScale)+3,scpModding_GetGameTitle$(),True,False
	Color 255,255,255
	AAText(GraphicWidth/2),((GraphicHeight/7)*MenuScale),scpModding_GetGameTitle$(),True,False
	
	If ms\MainMenuTab = 0 Then
		;If DrawButton(GraphicWidth - 10 - 330, GraphicHeight - 10 - 30, 330, 30, "RESET STATS AND ACHIEVEMENTS", False, False, False) Then
		;    scpSteam_ResetStats(True);
		;EndIf
		For i% = 0 To 3
			temp = False
			hover% = False
			;x = 159 * MenuScale
			x = (GraphicWidth/2)-(200 * MenuScale)
			y = (286 + 100 * i) * MenuScale
			
			width = 400 * MenuScale
			height = 70 * MenuScale
			
			If MouseOn(x, y, width, height) Then
				;color(30, 30, 30)
				hover = True			
				;If MouseHit1 Then temp = True ;-> Alternate
		    If (MouseHit1 And (Not waitForMouseUp)) Or (MouseUp1 And waitForMouseUp) Then temp = True : PlaySound_Strict(ButtonSFX)
		         ;clicked = True						
			Else		
				;color(0, 0, 0)
			EndIf
						
			temp = (MouseHit1 And MouseOn(x, y, width, height))
			
			Local txt$
			Select i
				Case 0
					txt = scpLang_GetPhrase("menu.newgame")
					RandomSeed = ""
					If temp Then 
						If Rand(15)=1 Then 
							Select Rand(15)
								Case 1 
									RandomSeed = "NIL"
								Case 2
									RandomSeed = "NO"
								Case 3
									RandomSeed = "d9341"
								Case 4
									RandomSeed = "5CP_I73"
								Case 5
									RandomSeed = "DONTBLINK"
								Case 6
									RandomSeed = "CRUNCH"
								Case 7
									RandomSeed = "die"
								Case 8
									RandomSeed = "HTAED"
								Case 9
									RandomSeed = "rustledjim"
								Case 10
									RandomSeed = "larry"
								Case 11
									RandomSeed = "JORGE"
								Case 12
									RandomSeed = "dirtymetal"
								Case 13
									RandomSeed = "whatpumpkin"
								Case 14
									RandomSeed = "the matrix"
								Case 15
									RandomSeed = "Walter White"
							End Select
						Else
							n = Rand(4,8)
							For i = 1 To n
								If Rand(3)=1 Then
									RandomSeed = RandomSeed + Rand(0,9)
								Else
									RandomSeed = RandomSeed + Chr(Rand(97,122))
								EndIf
							Next							
						EndIf
						
						;RandomSeed = MilliSecs()
						ms\MainMenuTab = 1
					EndIf
				Case 1
					txt = scpLang_GetPhrase("menu.loadgame")
					If temp Then
						LoadSaveGames()
						ms\MainMenuTab = 2
					EndIf
				Case 2
					txt = scpLang_GetPhrase("menu.options")
					If temp Then ms\MainMenuTab = 3
				Case 3
					txt = scpLang_GetPhrase("menu.quit")
					If temp Then
						;DeInitExt
						;alDestroy()
						;FMOD_Pause(MusicCHN)
						;FMOD_CloseStream(CurrMusicStream)
						;FMOD_Close()
						;FMOD_StopStream(CurrMusicStream)
						FSOUND_Stream_Stop(CurrMusicStream)
						;FSOUND_Close()
						;BS_SteamAPI_Shutdown()
						scpSteam_Shutdown()
						End
					EndIf
			End Select
			
			If hover And (Rand(20)=1) Then
				Color 100+Rand(50),100,100
				AAText(GraphicWidth/2)+Rand(-10,10)*MenuScale,y+Rand(-10,10)*MenuScale,txt,True,False
			EndIf
			Color 0,0,0
			AAText(GraphicWidth/2)+3*MenuScale,y+3*MenuScale,txt,True,False
			If Not hover Then
				Color 255,255,255
			Else
				Color 230,35,21
			EndIf
			AAText(GraphicWidth/2),y,txt,True,False	
			
			;DrawButton(x, y, width, height, txt)
		Next	
	
	Else
		
		x = 159 * MenuScale
		y = 286 * MenuScale
		
		width = 400 * MenuScale
		height = 70 * MenuScale
;;;;
		Local back%
		If ms\MainMenuTab<>8
		    If MouseOn((GraphicWidth/2)+(330*MenuScale), y, 130, 60) And (Rand(20)=1) Then
		        Color 100+Rand(50),100,100
                AAText(GraphicWidth/2)+(330*MenuScale)+(3*MenuScale)+Rand(-10*MenuScale,10*MenuScale), y+(3*MenuScale)+Rand(-10*MenuScale,10*MenuScale),scpLang_GetPhrase$("menu.back"),False,False
            EndIf
            Color 0,0,0
			AAText(GraphicWidth/2)+(330*MenuScale)+(3*MenuScale), y+(3*MenuScale),scpLang_GetPhrase$("menu.back"),False,False
			If MouseOn((GraphicWidth/2)+(330*MenuScale), y, 130, 60)  Then
				Color 230,35,21
				If MouseHit1 Then back = True : PlaySound_Strict(ButtonSFX)
			Else
				Color 255,255,255
			EndIf
			
			AAText(GraphicWidth/2)+(330*MenuScale),y,scpLang_GetPhrase$("menu.back"),False,False
			;AAText x + width + 20 * MenuScale, y, "BACK", False, False
		EndIf
	
		If back Then		
			Select ms\MainMenuTab
				Case 1
					PutINIValue(OptionFile, "game", "intro enabled", IntroEnabled%)
					ms\MainMenuTab = 0
				Case 2
					CurrLoadGamePage = 0
					ms\MainMenuTab = 0
				Case 3,5,6,7 ;save the options
					SaveOptionsINI()
					
					UserTrackCheck% = 0
					UserTrackCheck2% = 0
					
					AntiAlias Opt_AntiAlias
					ms\MainMenuTab = 0
				Case 4 ;move back to the "new game" tab
					ms\MainMenuTab = 1
					CurrLoadGamePage = 0
					MouseHit1 = False
				Default
					ms\MainMenuTab = 0
			End Select
		EndIf
		;;;;;;;;;;;;;;
		;If DrawButton(x + width + 20 * MenuScale, y, 580 * MenuScale - width - 20 * MenuScale, height, "BACK", False) Then
		;	Select ms\MainMenuTab
		;		Case 1
		;			PutINIValue(OptionFile, "game", "intro enabled", IntroEnabled%)
		;			ms\MainMenuTab = 0
		;		Case 2
		;			CurrLoadGamePage = 0
		;			ms\MainMenuTab = 0
		;		Case 3
		;			SaveOptionsINI()
		;			
		;			UserTrackCheck% = 0
		;			UserTrackCheck2% = 0
		;			
		;			AntiAlias Opt_AntiAlias
		;			ms\MainMenuTab = 0
		;		Case 4
		;			ms\MainMenuTab = 1
		;			CurrLoadGamePage = 0
		;			MouseHit1 = False
		;		Default
		;			ms\MainMenuTab = 0
		;	End Select
		;EndIf
		
		Select ms\MainMenuTab
			Case 1 ; New game
				;[Block]
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont fo\Font[1]
				AAText(GraphicWidth/2, y + height / 2, scpLang_GetPhrase$("menu.newgame"), True, True)
				
				x = (GraphicWidth/2)-(290*MenuScale)							
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 330 * MenuScale	
				
				;x = 160 * MenuScale
				;y = y + height + 20 * MenuScale
				;width = 580 * MenuScale
				;height = 330 * MenuScale		
				
				AASetFont fo\Font[0]
				
				AAText (x + 20 * MenuScale, y + 20 * MenuScale, scpLang_GetPhrase$("menu.name"))
				CurrSave = InputBox(x + 150 * MenuScale, y + 15 * MenuScale, 200 * MenuScale, 30 * MenuScale, CurrSave, 1)
				CurrSave = Left(CurrSave, 15)
				CurrSave = Replace(CurrSave,":","")
				CurrSave = Replace(CurrSave,".","")
				CurrSave = Replace(CurrSave,"/","")
				CurrSave = Replace(CurrSave,"\","")
				CurrSave = Replace(CurrSave,"<","")
				CurrSave = Replace(CurrSave,">","")
				CurrSave = Replace(CurrSave,"|","")
				CurrSave = Replace(CurrSave,"?","")
				CurrSave = Replace(CurrSave,Chr(34),"")
				CurrSave = Replace(CurrSave,"*","")
								
				Color 255,255,255
				If SelectedMap = "" Then
					AAText (x + 20 * MenuScale, y + 60 * MenuScale, scpLang_GetPhrase$("menu.seed"))
					RandomSeed = Left(InputBox(x+150*MenuScale, y+55*MenuScale, 200*MenuScale, 30*MenuScale, RandomSeed, 3),15)	
				Else
					AAText (x + 20 * MenuScale, y + 60 * MenuScale, scpLang_GetPhrase$("menu.map"))
					Color (255, 255, 255)
					Rect(x+150*MenuScale, y+55*MenuScale, 200*MenuScale, 30*MenuScale)
					Color (0, 0, 0)
					Rect(x+150*MenuScale+2, y+55*MenuScale+2, 200*MenuScale-4, 30*MenuScale-4)
					
					Color (255, 0,0)
					If Len(SelectedMap)>15 Then
						AAText(x+150*MenuScale + 100*MenuScale, y+55*MenuScale + 15*MenuScale, Left(SelectedMap,14)+"...", True, True)
					Else
						AAText(x+150*MenuScale + 100*MenuScale, y+55*MenuScale + 15*MenuScale, SelectedMap, True, True)
					EndIf
					
					If DrawButton(x+370*MenuScale, y+55*MenuScale, 120*MenuScale, 30*MenuScale, scpLang_GetPhrase$("menu.deselect"), False) Then
						SelectedMap=""
					EndIf
				EndIf	
				
				AAText(x + 20 * MenuScale, y + 110 * MenuScale, scpLang_GetPhrase$("menu.enableintro"))
				IntroEnabled = DrawTick(x + 280 * MenuScale, y + 110 * MenuScale, IntroEnabled)	
				
				;Local modeName$, modeDescription$, selectedDescription$
				AAText (x + 20 * MenuScale, y + 150 * MenuScale, scpLang_GetPhrase$("menu.difficulty"))				
						
				If DrawTick(x + 20 * MenuScale, y + (180+30*SAFE) * MenuScale, (SelectedDifficulty = difficulties(SAFE))) Then SelectedDifficulty = difficulties(SAFE)
					Color(difficulties(SAFE)\r,difficulties(SAFE)\g,difficulties(SAFE)\b)
					AAText(x + 60 * MenuScale, y + (180+30*SAFE) * MenuScale, difficulties(SAFE)\name)

				If DrawTick(x + 20 * MenuScale, y + (180+30*EUCLID) * MenuScale, (SelectedDifficulty = difficulties(EUCLID))) Then SelectedDifficulty = difficulties(EUCLID)
					Color(difficulties(EUCLID)\r,difficulties(EUCLID)\g,difficulties(EUCLID)\b)
					AAText(x + 60 * MenuScale, y + (180+30*EUCLID) * MenuScale, difficulties(EUCLID)\name)
					
				If DrawTick(x + 20 * MenuScale, y + (180+30*KETER) * MenuScale, (SelectedDifficulty = difficulties(KETER))) Then SelectedDifficulty = difficulties(KETER)
					Color(difficulties(KETER)\r,difficulties(KETER)\g,difficulties(KETER)\b)
					AAText(x + 60 * MenuScale, y + (180+30*KETER) * MenuScale, difficulties(KETER)\name)
						
				If UnlockThaumiel = 0 Then lock% = True
				If UnlockThaumiel = 1 Then lock% = False
			
				If DrawTick(x + 20 * MenuScale, y + (180+30*THAUMIEL) * MenuScale, (SelectedDifficulty = difficulties(THAUMIEL)),lock%) Then SelectedDifficulty = difficulties(THAUMIEL)
					Color(difficulties(THAUMIEL)\r,difficulties(THAUMIEL)\g,difficulties(THAUMIEL)\b)
					AAText(x + 60 * MenuScale, y + (180+30*THAUMIEL) * MenuScale, difficulties(THAUMIEL)\name)
										
				If DrawTick(x + 20 * MenuScale, y + (180+30*CUSTOM) * MenuScale, (SelectedDifficulty = difficulties(CUSTOM))) Then SelectedDifficulty = difficulties(CUSTOM)
					Color(difficulties(CUSTOM)\r,difficulties(CUSTOM)\g,difficulties(CUSTOM)\b)
					AAText(x + 60 * MenuScale, y + (180+30*CUSTOM) * MenuScale, difficulties(CUSTOM)\name)			
				
				Color(255, 255, 255)
				DrawFrame(x + 150 * MenuScale, y + 155 * MenuScale, 450*MenuScale, 170*MenuScale)
				
				If SelectedDifficulty\customizable Then
					SelectedDifficulty\permaDeath = DrawTick(x + 160 * MenuScale, y + 165 * MenuScale, (SelectedDifficulty\permaDeath))
					AAText(x + 200 * MenuScale, y + 165 * MenuScale, scpLang_GetPhrase$("menu.permadeath"))
					
					If DrawTick(x + 160 * MenuScale, y + 195 * MenuScale, SelectedDifficulty\saveType = SAVEANYWHERE And (Not SelectedDifficulty\permaDeath), SelectedDifficulty\permaDeath) Then 
						SelectedDifficulty\saveType = SAVEANYWHERE
					Else
						SelectedDifficulty\saveType = SAVEONSCREENS
					EndIf
					
					AAText(x + 200 * MenuScale, y + 195 * MenuScale, scpLang_GetPhrase$("menu.saveanywhere"))	
					
					SelectedDifficulty\aggressiveNPCs =  DrawTick(x + 160 * MenuScale, y + 225 * MenuScale, SelectedDifficulty\aggressiveNPCs)
					AAText(x + 200 * MenuScale, y + 225 * MenuScale, scpLang_GetPhrase$("menu.aggressivenpcs"))
					
					AAText(x + 200 * MenuScale, y + 255 * MenuScale, scpLang_GetPhrase$("menu.twoinventoryslots"))
					SelectedDifficulty\twoslots =  DrawTick(x + 160 * MenuScale, y + 255 * MenuScale, SelectedDifficulty\twoslots)
					
					;Other factor's difficulty
					Color 255,255,255
					DrawImage ArrowIMG(1),x + 155 * MenuScale, y+285*MenuScale
					If MouseHit1
						If ImageRectOverlap(ArrowIMG(1),x + 155 * MenuScale, y+285*MenuScale, ScaledMouseX(),ScaledMouseY(),0,0)
							If SelectedDifficulty\otherFactors < HARD
								SelectedDifficulty\otherFactors = SelectedDifficulty\otherFactors + 1
							Else
								SelectedDifficulty\otherFactors = EASY
							EndIf
							PlaySound_Strict(ButtonSFX)
						EndIf
					EndIf
					Color 255,255,255
					Select SelectedDifficulty\otherFactors
						Case EASY
							AAText(x + 200 * MenuScale, y + 285 * MenuScale, scpLang_GetPhrase$("menu.otherfactors1"))
						Case NORMAL
							AAText(x + 200 * MenuScale, y + 285 * MenuScale, scpLang_GetPhrase$("menu.otherfactors2"))
						Case HARD
							AAText(x + 200 * MenuScale, y + 285 * MenuScale, scpLang_GetPhrase$("menu.otherfactors3"))
					End Select
				Else
					RowText(SelectedDifficulty\description, x+160*MenuScale, y+160*MenuScale, (440-20)*MenuScale, 200)					
				EndIf	
				
				If UnlockThaumiel = 0 Then				
					If MouseOn(x + 20 * MenuScale, y + (180+30*THAUMIEL) * MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(x+width,y + 210*MenuScale,400*MenuScale,150*MenuScale,scpLang_GetPhrase$("menu.apollyon"))
					EndIf
				EndIf
								
				If DrawButton(x, y + height + 20 * MenuScale, 160 * MenuScale, 70 * MenuScale, scpLang_GetPhrase$("menu.loadmap"), False) Then
					ms\MainMenuTab = 4
					LoadSavedMaps()
				EndIf
				
				AASetFont fo\Font[1]
				
				If DrawButton(x + 450 * MenuScale, y + height + 20 * MenuScale, 160 * MenuScale, 70 * MenuScale, scpLang_GetPhrase$("menu.start"), False) Then
					If CurrSave = "" Then CurrSave = scpLang_GetPhrase$("menu.untitled")
					
					If RandomSeed = "" Then
						RandomSeed = Abs(MilliSecs())
					EndIf
					
					SeedRnd GenerateSeedNumber(RandomSeed)
					
					Local SameFound% = False
					
					For  i% = 1 To SaveGameAmount
						If SaveGames(i - 1) = CurrSave Then SameFound = SameFound + 1
					Next
						
					If SameFound > 0 Then CurrSave = CurrSave + " (" + (SameFound + 1) + ")"
					
					LoadEntities()
					LoadAllSounds()
					InitNewGame()
					ms\MainMenuOpen = False
					FlushKeys()
					FlushMouse()
					
					PutINIValue(OptionFile, "game", "intro enabled", IntroEnabled%)
					
				EndIf
				
				;[End Block]
			Case 2 ;load game
				;[Block]
				
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 510 * MenuScale
						
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont fo\Font[1]
			    AAText(GraphicWidth/2, y + height / 2, scpLang_GetPhrase$("menu.loadgame"), True, True)
				
				;x = 160 * MenuScale			
				x = (GraphicWidth/2)-(290*MenuScale)

				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 296 * MenuScale
				
				;AASetFont fo\Font[0]	
				
				AASetFont fo\Font[1]
				
				If CurrLoadGamePage < Ceil(Float(SaveGameAmount)/6.0)-1 And SaveMSG = "" Then 
					If DrawButton(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, ">") Then
						CurrLoadGamePage = CurrLoadGamePage+1
					EndIf
				Else
					DrawFrame(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+555*MenuScale, y + 537.5*MenuScale, ">", True, True)
				EndIf
				If CurrLoadGamePage > 0 And SaveMSG = "" Then
					If DrawButton(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, "<") Then
						CurrLoadGamePage = CurrLoadGamePage-1
					EndIf
				Else
					DrawFrame(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+25*MenuScale, y + 537.5*MenuScale, "<", True, True)
				EndIf
				
                ;DrawFrame(x+50*MenuScale,y+510*MenuScale,width-100*MenuScale,55*MenuScale)

			    Color(255, 255, 255)				
				AAText(x + (width / 2.0),y+536*MenuScale,scpLang_GetPhrase$("menu.page") + " "+Int(Max((CurrLoadGamePage+1),1))+"/"+Int(Max((Int(Ceil(Float(SaveGameAmount)/6.0))),1)),True,True)									
						
				AASetFont fo\Font[0]
				
				If CurrLoadGamePage > Ceil(Float(SaveGameAmount)/6.0)-1 Then
					CurrLoadGamePage = CurrLoadGamePage - 1
				EndIf
				
				If SaveGameAmount = 0 Then
					AAText (x + 20 * MenuScale, y + 20 * MenuScale, scpLang_GetPhrase$("menu.nosavedgames"))
				Else
					x = x + 20 * MenuScale
					y = y + 20 * MenuScale
					
					For i% = (1+(6*CurrLoadGamePage)) To 6+(6*CurrLoadGamePage)
						If i <= SaveGameAmount Then
							;DrawFrame(x,y,540* MenuScale, 70* MenuScale)
							
							If SaveGameVersion(i - 1) <> ModCompatibleNumber And SaveGameVersion(i - 1) <> "5.5.4" Then
								Color 255,0,0
							Else
								Color 255,255,255
							EndIf
							
							AAText(x + 20 * MenuScale, y + 10 * MenuScale, SaveGames(i - 1))
							AAText(x + 20 * MenuScale, y + (10+18) * MenuScale, SaveGameTime(i - 1)) ;y + (10+23) * MenuScale
							AAText(x + 120 * MenuScale, y + (10+18) * MenuScale, SaveGameDate(i - 1))
							AAText(x + 20 * MenuScale, y + (10+36) * MenuScale, scpModding_Version())
							
							If SaveMSG = "" Then
								If SaveGameVersion(i - 1) <> ModCompatibleNumber And SaveGameVersion(i - 1) <> "5.5.4" Then
									DrawFrame(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
									Color(255, 0, 0)
									AAText(x + 330 * MenuScale, y + 34 * MenuScale, scpLang_GetPhrase$("menu.load"), True, True)
								Else
									If DrawButton(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, scpLang_GetPhrase$("menu.load"), False) Then
										LoadEntities()
										LoadAllSounds()
										LoadGame(SavePath + SaveGames(i - 1) + "\")
										CurrSave = SaveGames(i - 1)
										InitLoadGame()
										ms\MainMenuOpen = False
									EndIf
								EndIf
								
								If DrawButton(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, scpLang_GetPhrase$("menu.delete"), False) Then
									SaveMSG = SaveGames(i - 1)
									Exit
								EndIf
							Else
								DrawFrame(x + 280 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
								If SaveGameVersion(i - 1) <> ModCompatibleNumber And SaveGameVersion(i - 1) <> "5.5.4" Then
									Color(255, 0, 0)
								Else
									Color(100, 100, 100)
								EndIf
								AAText(x + 330 * MenuScale, y + 34 * MenuScale, scpLang_GetPhrase$("menu.Load"), True, True)
								
								DrawFrame(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale)
								Color(100, 100, 100)
								AAText(x + 450 * MenuScale, y + 34 * MenuScale, scpLang_GetPhrase$("menu.delete"), True, True)
							EndIf
							
							y = y + 80 * MenuScale
						Else
							Exit
						EndIf
					Next
					
					If SaveMSG <> ""
						x = 740 * MenuScale
						y = 376 * MenuScale
						DrawFrame(x, y, 420 * MenuScale, 200 * MenuScale)
						RowText(scpLang_GetPhrase$("menu.deletesaveconfirm"), x + 20 * MenuScale, y + 15 * MenuScale, 400 * MenuScale, 200 * MenuScale)
						;AAText(x + 20 * MenuScale, y + 15 * MenuScale, "Are you sure you want to delete this save?")
						If DrawButton(x + 50 * MenuScale, y + 150 * MenuScale, 100 * MenuScale, 30 * MenuScale, scpLang_GetPhrase$("menu.yes"), False) Then
							DeleteFile(CurrentDir() + SavePath + SaveMSG + "\save.txt")
							DeleteDir(CurrentDir() + SavePath + SaveMSG)
							SaveMSG = ""
							LoadSaveGames()
						EndIf
						If DrawButton(x + 250 * MenuScale, y + 150 * MenuScale, 100 * MenuScale, 30 * MenuScale, scpLang_GetPhrase$("menu.no"), False) Then
							SaveMSG = ""
						EndIf
					EndIf
				EndIf
			
				;[End Block]
			Case 3,5,6,7 ;options
				;[Block]
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont fo\Font[1]
								
				AAText(GraphicWidth/2, y + height / 2, scpLang_GetPhrase$("menu.options"), True, True)				

				;x = 160 * MenuScale
				x = (GraphicWidth/2)-(290*MenuScale)
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 60 * MenuScale			
				
				Color 0,255,0
				If ms\MainMenuTab = 3
					Rect(x+15*MenuScale,y+10*MenuScale,(width/5)+10*MenuScale,(height/2)+10*MenuScale,True)
				ElseIf ms\MainMenuTab = 5
					Rect(x+155*MenuScale,y+10*MenuScale,(width/5)+10*MenuScale,(height/2)+10*MenuScale,True)
				ElseIf ms\MainMenuTab = 6
					Rect(x+295*MenuScale,y+10*MenuScale,(width/5)+10*MenuScale,(height/2)+10*MenuScale,True)
				ElseIf ms\MainMenuTab = 7
					Rect(x+435*MenuScale,y+10*MenuScale,(width/5)+10*MenuScale,(height/2)+10*MenuScale,True)
				EndIf
				
				Color 255,255,255
				If DrawButton(x+20*MenuScale,y+15*MenuScale,width/5,height/2, scpLang_GetPhrase$("menu.graphics"), False) Then ms\MainMenuTab = 3
				If DrawButton(x+160*MenuScale,y+15*MenuScale,width/5,height/2, scpLang_GetPhrase$("menu.audio"), False) Then ms\MainMenuTab = 5
				If DrawButton(x+300*MenuScale,y+15*MenuScale,width/5,height/2, scpLang_GetPhrase$("menu.controls"), False) Then ms\MainMenuTab = 6
				If DrawButton(x+440*MenuScale,y+15*MenuScale,width/5,height/2, scpLang_GetPhrase$("menu.advanced"), False) Then ms\MainMenuTab = 7
				
				AASetFont fo\Font[0]
				y = y + 70 * MenuScale
				
				If ms\MainMenuTab <> 5
					UserTrackCheck% = 0
					UserTrackCheck2% = 0
				EndIf
				
				Local tx# = x+width
				Local ty# = y
				Local tw# = 400*MenuScale
				Local th# = 150*MenuScale
				
				;DrawOptionsTooltip(tx,ty,tw,th,"")
				
				If ms\MainMenuTab = 3 ;Graphics
					;[Block]
					;height = 380 * MenuScale
					height = 410 * MenuScale
					DrawFrame(x, y, width, height)
					
					y=y+20*MenuScale
					
					Color 255,255,255				
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.bumpmapping"))
					Local CurrBumpEnabled = BumpEnabled	
					BumpEnabled = DrawTick(x + 310 * MenuScale, y + MenuScale, BumpEnabled)
					If CurrBumpEnabled<>BumpEnabled Then
						;DeInit3DMenu()
						;Init3DMenu()
				    EndIf
					If MouseOn(x + 310 * MenuScale, y + MenuScale, 20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"bump")
					EndIf
					
					y=y+30*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.vsync"))
					Vsync% = DrawTick(x + 310 * MenuScale, y + MenuScale, Vsync%)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"vsync")
					EndIf
					
					y=y+30*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.antialiasing"))
					Opt_AntiAlias = DrawTick(x + 310 * MenuScale, y + MenuScale, Opt_AntiAlias%)
					;AAText(x + 20 * MenuScale, y + 15 * MenuScale, "(fullscreen mode only)")
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"antialias")
					EndIf
					
					y=y+30*MenuScale ;40
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.roomlights"))
                    Local CurrEnableRoomLights = EnableRoomLights
                    EnableRoomLights = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableRoomLights)
                    If CurrEnableRoomLights <>EnableRoomLights Then
	                    ;DeInit3DMenu()
						;Init3DMenu()
				    EndIf
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"roomlights")
					EndIf
					
					y=y+30*MenuScale
					
					ScreenGamma = (SlideBar(x + 310*MenuScale, y+6*MenuScale, 150*MenuScale, ScreenGamma*50.0)/50.0)
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.gamma"))
					
					If MouseOn(x+310*MenuScale,y+6*MenuScale,150*MenuScale+14,20) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"gamma",ScreenGamma)
					EndIf						
					
					y=y+50*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.particleamount"))
					ParticleAmount = Slider3(x+310*MenuScale,y+6*MenuScale,150*MenuScale,ParticleAmount,2,scpLang_GetPhrase$("menu.minimal"),scpLang_GetPhrase$("menu.reduced"),scpLang_GetPhrase$("menu.full"))
					If (MouseOn(x + 310 * MenuScale, y-6*MenuScale, 150*MenuScale+14, 20) And OnSliderID=0) Or OnSliderID=2
						DrawOptionsTooltip(tx,ty,tw,th,"particleamount",ParticleAmount)
					EndIf
					
					y=y+50*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.lodbias"))
					TextureDetails = Slider5(x+310*MenuScale,y+6*MenuScale,150*MenuScale,TextureDetails,3,"0.8","0.4","0.0","-0.4","-0.8")
					Select TextureDetails%
						Case 0
							TextureFloat# = 0.8
						Case 1
							TextureFloat# = 0.4
						Case 2
							TextureFloat# = 0.0
						Case 3
							TextureFloat# = -0.4
						Case 4
							TextureFloat# = -0.8
					End Select
					TextureLodBias TextureFloat
					If (MouseOn(x+310*MenuScale,y-6*MenuScale,150*MenuScale+14,20) And OnSliderID=0) Or OnSliderID=3
						DrawOptionsTooltip(tx,ty,tw,th+100*MenuScale,"texquality")
					EndIf
					
					y=y+50*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.vram"))
					Local CurrSaveTexturesInVRam = SaveTexturesInVRam
					SaveTexturesInVRam = DrawTick(x + 310 * MenuScale, y + MenuScale, SaveTexturesInVRam)
					If CurrSaveTexturesInVRam<>SaveTexturesInVRam Then
						;DeInit3DMenu()
						;Init3DMenu()
					EndIf
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale) And OnSliderID=0
						DrawOptionsTooltip(tx,ty,tw,th,"vram")
					EndIf
					
					y=y+50*MenuScale
					
					Local SlideBarFOV# = FOV#-40
					SlideBarFOV = (SlideBar(x + 310*MenuScale, y+6*MenuScale,150*MenuScale, SlideBarFOV*2.0)/2.0)
					FOV = SlideBarFOV+40
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.fov"))
					Color 255,255,0
					AAText(x + 25 * MenuScale, y + 25 * MenuScale, Int(FOV#)+" FOV")
					If MouseOn(x+310*MenuScale,y+6*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"fov")
					EndIf
					
					;[End Block]
				ElseIf ms\MainMenuTab = 5 ;Audio
					;[Block]
					height = 220 * MenuScale
					DrawFrame(x, y, width, height)	
					
					y = y + 20*MenuScale
					
					MusicVolume = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, MusicVolume*100.0)/100.0)
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.musicvolume"))
					;If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale,20)
					;	DrawOptionsTooltip(tx,ty,tw,th,"musicvol")
					;EndIf

					If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"musicvol",MusicVolume)
					EndIf
																
					y = y + 40*MenuScale
					
					;SFXVolume = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, SFXVolume*100.0)/100.0)
					PrevSFXVolume = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, SFXVolume*100.0)/100.0)
					SFXVolume = PrevSFXVolume
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.soundvolume"))
					;If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale,20)
					;	DrawOptionsTooltip(tx,ty,tw,th,"soundvol")
					;EndIf
					
					If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"soundvol",PrevSFXVolume)
					EndIf	
					;If MouseDown1 Then
					;	If MouseX() >= x And MouseX() <= x + width + 14 And MouseY() >= y And MouseY() <= y + 20 Then
					;		PlayTestSound(True)
					;	Else
					;		PlayTestSound(False)
					;	EndIf
					;Else
					;	PlayTestSound(False)
					;EndIf
					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.soundautorelease")
					EnableSFXRelease = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableSFXRelease)
					If EnableSFXRelease_Prev% <> EnableSFXRelease
						If EnableSFXRelease%
							For snd.Sound = Each Sound
								For i=0 To 31
									If snd\channels[i]<>0 Then
										If ChannelPlaying(snd\channels[i]) Then
											StopChannel(snd\channels[i])
										EndIf
									EndIf
								Next
								If snd\internalHandle<>0 Then
									FreeSound snd\internalHandle
									snd\internalHandle = 0
								EndIf
								snd\releaseTime = 0
							Next
						Else
							For snd.Sound = Each Sound
								If snd\internalHandle = 0 Then snd\internalHandle = LoadSound(snd\name)
							Next
						EndIf
						EnableSFXRelease_Prev% = EnableSFXRelease
					EndIf
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th+220*MenuScale,"sfxautorelease")
					EndIf
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.usertracks")
					EnableUserTracks = DrawTick(x + 310 * MenuScale, y + MenuScale, EnableUserTracks)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"usertrack")
					EndIf
					
					If EnableUserTracks
						y = y + 30 * MenuScale
						Color 255,255,255
						AAText x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.trackmode")
						UserTrackMode = DrawTick(x + 310 * MenuScale, y + MenuScale, UserTrackMode)
						If UserTrackMode
							AAText x + 350 * MenuScale, y + MenuScale, scpLang_GetPhrase$("menu.repeat")
						Else
							AAText x + 350 * MenuScale, y + MenuScale, scpLang_GetPhrase$("menu.random")
						EndIf
						If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
							DrawOptionsTooltip(tx,ty,tw,th,"usertrackmode")
						EndIf
						If DrawButton(x + 20 * MenuScale, y + 30 * MenuScale, 190 * MenuScale, 25 * MenuScale, scpLang_GetPhrase$("menu.trackscan"),False)
							
							UserTrackCheck% = 0
							UserTrackCheck2% = 0
							
							Dir=ReadDir("SFX\Radio\UserTracks")
							Repeat
								file$=NextFile(Dir)
								If file$="" Then Exit
								If FileType("SFX\Radio\UserTracks"+file$) = 1 Then
									UserTrackCheck = UserTrackCheck + 1
									test = LoadSound("SFX\Radio\UserTracks"+file$)
									If test<>0
										UserTrackCheck2 = UserTrackCheck2 + 1
									EndIf
									FreeSound test
								EndIf
							Forever
							CloseDir Dir
							
						EndIf
						If MouseOn(x+20*MenuScale,y+30*MenuScale,190*MenuScale,25*MenuScale)
							DrawOptionsTooltip(tx,ty,tw,th,"usertrackscan")
						EndIf
						If UserTrackCheck%>0
							AAText x + 20 * MenuScale, y + 100 * MenuScale, scpLang_GetPhrase$("menu.trackfound") + " ("+UserTrackCheck2+"/"+UserTrackCheck+" " + scpLang_GetPhrase$("menu.successfullyloaded") + ")"
						EndIf
					Else
						UserTrackCheck%=0
					EndIf
					;[End Block]
				ElseIf ms\MainMenuTab = 6 ;Controls
					;[Block]
					;height = 230 * MenuScale
					
					height = 300 * MenuScale					
					DrawFrame(x, y, width, height)	
										
					y = y + 20*MenuScale
					
					MouseSensitivity = (SlideBar(x + 310*MenuScale, y-4*MenuScale, 150*MenuScale, (MouseSensitivity+0.5)*100.0)/100.0)-0.5
					Color(255, 255, 255)
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.mousesensitivity"))

					If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"mousesensitivity",MouseSensitivity)
					EndIf
																
					y = y + 40*MenuScale
					
					Color(255, 255, 255)
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.invertmouse"))
					InvertMouse = DrawTick(x + 310 * MenuScale, y + MenuScale, InvertMouse)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"mouseinvert")
					EndIf

					y = y + 40*MenuScale
					
					mouse_smooth = DrawTick(x + 310*MenuScale, y+MenuScale,mouse_smooth)
					Color(255, 255, 255)
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.mousesmoothing"))
					If MouseOn(x+310*MenuScale,y-4*MenuScale,150*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"mousesmoothing")
					EndIf
					
					Color(255, 255, 255)				
					y = y + 30*MenuScale
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.controlconfig"))
					y = y + 10*MenuScale
					
					AAText(x + 20 * MenuScale, y + 20 * MenuScale, scpLang_GetPhrase$("menu.moveforward"))
					InputBox(x + 160 * MenuScale, y + 20 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_UP,210)),5)		
					AAText(x + 20 * MenuScale, y + 40 * MenuScale, scpLang_GetPhrase$("menu.strafeleft"))
					InputBox(x + 160 * MenuScale, y + 40 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_LEFT,210)),3)	
					AAText(x + 20 * MenuScale, y + 60 * MenuScale, scpLang_GetPhrase$("menu.movebackward"))
					InputBox(x + 160 * MenuScale, y + 60 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_DOWN,210)),6)				
					AAText(x + 20 * MenuScale, y + 80 * MenuScale, scpLang_GetPhrase$("menu.straferight"))
					InputBox(x + 160 * MenuScale, y + 80 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_RIGHT,210)),4)	
					AAText(x + 20 * MenuScale, y + 100 * MenuScale, scpLang_GetPhrase$("menu.quicksave"))
					InputBox(x + 160 * MenuScale, y + 100 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_SAVE,210)),11)
					
					AAText(x + 280 * MenuScale, y + 20 * MenuScale, scpLang_GetPhrase$("menu.manualblink"))
					InputBox(x + 470 * MenuScale, y + 20 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_BLINK,210)),7)				
					AAText(x + 280 * MenuScale, y + 40 * MenuScale, scpLang_GetPhrase$("menu.sprint"))
					InputBox(x + 470 * MenuScale, y + 40 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_SPRINT,210)),8)
					AAText(x + 280 * MenuScale, y + 60 * MenuScale, scpLang_GetPhrase$("menu.inventory"))
					InputBox(x + 470 * MenuScale, y + 60 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_INV,210)),9)
					AAText(x + 280 * MenuScale, y + 80 * MenuScale, scpLang_GetPhrase$("menu.crouch"))
					InputBox(x + 470 * MenuScale, y + 80 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_CROUCH,210)),10)
					If CanOpenConsole	
					    AAText(x + 280 * MenuScale, y + 100 * MenuScale, scpLang_GetPhrase$("menu.console"))
					    InputBox(x + 470 * MenuScale, y + 100 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_CONSOLE,210)),12)
					EndIf
					AAText(x + 280 * MenuScale, y + 120 * MenuScale, scpLang_GetPhrase$("menu.screenshot"))
					InputBox(x + 470 * MenuScale, y + 120 * MenuScale,100*MenuScale,20*MenuScale,KeyName(Min(KEY_SCREENSHOT,210)),13)
					
					If MouseOn(x+20*MenuScale,y,width-40*MenuScale,140*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"controls")
					EndIf
					
					For i = 0 To 227
						If KeyHit(i) Then key = i : Exit
					Next
					If key<>0 Then
						Select SelectedInputBox
							Case 3
								KEY_LEFT = key
							Case 4
								KEY_RIGHT = key
							Case 5
								KEY_UP = key
							Case 6
								KEY_DOWN = key
							Case 7
								KEY_BLINK = key
							Case 8
								KEY_SPRINT = key
							Case 9
								KEY_INV = key
							Case 10
								KEY_CROUCH = key
							Case 11
								KEY_SAVE = key
							Case 12
								KEY_CONSOLE = key
							Case 13
							    KEY_SCREENSHOT = key
						End Select
						SelectedInputBox = 0
					EndIf
					;[End Block]
				ElseIf ms\MainMenuTab = 7 ;Advanced
					;[Block]
					height = 370 * MenuScale
					DrawFrame(x, y, width, height)	
					
					y = y + 20*MenuScale
					
					Color 255,255,255				
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.hud"))	
					HUDenabled = DrawTick(x + 310 * MenuScale, y + MenuScale, HUDenabled)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"hud")
					EndIf
					
					y=y+30*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.enableconsole"))
					CanOpenConsole = DrawTick(x + 310 * MenuScale, y + MenuScale, CanOpenConsole)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"consoleenable")
					EndIf
					
					y = y + 30*MenuScale
					
					If CanOpenConsole
					    Color 255,255,255
					    AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.consoleonerror"))
					    ConsoleOpening = DrawTick(x + 310 * MenuScale, y + MenuScale, ConsoleOpening)
					    If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						    DrawOptionsTooltip(tx,ty,tw,th,"consoleerror")
					    EndIf
					Else
					    ConsoleOpening = 0
					EndIf
					
					y = y + 30*MenuScale
					
					If CanOpenConsole
					    Color 255,255,255
					    AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.consoleversion"))
				        ConsoleVersion = DrawTick(x + 310 * MenuScale, y + MenuScale, ConsoleVersion)
					    If ConsoleVersion = 1 Then
					       AAText(x + 350 * MenuScale, y, scpLang_GetPhrase$("menu.consolenew"))
					    Else
					        AAText(x + 350 * MenuScale, y, scpLang_GetPhrase$("menu.consoleold"))
					    EndIf    
					    If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						     DrawOptionsTooltip(tx,ty,tw,th,"consoleversion")
					    EndIf
					EndIf
					
					y = y + 50*MenuScale
					
					;Color 255,255,255
					;AAText(x + 20 * MenuScale, y, "Achievement popups:")
					;AchvMSGenabled% = DrawTick(x + 310 * MenuScale, y + MenuScale, False)
					;If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
					;	DrawOptionsTooltip(tx,ty,tw,th,"achpopup")
					;EndIf
					
					If DrawButton(x + 20 * MenuScale, y, 330, 30, scpLang_GetPhrase$("menu.resetstats"), False, False, True) Then
		    				scpSteam_ResetStats(True);
					EndIf
					
					y = y + 50*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.showfps"))
					ShowFPS% = DrawTick(x + 310 * MenuScale, y + MenuScale, ShowFPS%)
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"showfps")
					EndIf
					
					y = y + 30*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.framelimit"))
					Color 255,255,255
					If DrawTick(x + 310 * MenuScale, y, CurrFrameLimit > 0.0) Then
						CurrFrameLimit# = (SlideBar(x + 150*MenuScale, y+30*MenuScale, 100*MenuScale, CurrFrameLimit#*99.0)/99.0)
						CurrFrameLimit# = Max(CurrFrameLimit, 0.01)
						Framelimit% = 19+(CurrFrameLimit*100.0)
						Color 255,255,0
						AAText(x + 25 * MenuScale, y + 25 * MenuScale, Framelimit%+" FPS")
					Else
						CurrFrameLimit# = 0.0
						Framelimit = 0
					EndIf
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"framelimit",Framelimit)
					EndIf
					If MouseOn(x+150*MenuScale,y+30*MenuScale,100*MenuScale+14,20)
						DrawOptionsTooltip(tx,ty,tw,th,"framelimit",Framelimit)
					EndIf
					
					y = y + 80*MenuScale
					
					Color 255,255,255
					AAText(x + 20 * MenuScale, y, scpLang_GetPhrase$("menu.antialiasedtext"))
					AATextEnable% = DrawTick(x + 310 * MenuScale, y + MenuScale, AATextEnable%)
					If AATextEnable_Prev% <> AATextEnable
						For font.AAFont = Each AAFont
							FreeFont font\lowResFont%
							If (Not AATextEnable)
								FreeTexture font\texture
								FreeImage font\backup
							EndIf
							Delete font
						Next
						If (Not AATextEnable) Then
							FreeEntity AATextCam
							;For i%=0 To 149
							;	FreeEntity AATextSprite[i]
							;Next
						EndIf
						InitAAFont()
						fo\Font[0] = AALoadFont("GFX\font\"+"cour\Courier New.ttf", Int(18 * (GraphicHeight / 1024.0)), 0,0,0)
						fo\Font[1] = AALoadFont("GFX\font\"+"courbd\Courier New.ttf", Int(58 * (GraphicHeight / 1024.0)), 0,0,0)
						fo\Font[2] = AALoadFont("GFX\font\"+"DS-DIGI\DS-Digital.ttf", Int(22 * (GraphicHeight / 1024.0)), 0,0,0)
						fo\Font[3] = AALoadFont("GFX\font\"+"DS-DIGI\DS-Digital.ttf", Int(58 * (GraphicHeight / 1024.0)), 0,0,0)
						fo\Font[4] = AALoadFont("GFX\font\"+"Journal\Journal.ttf", Int(58 * (GraphicHeight / 1024.0)), 0,0,0)
						fo\ConsoleFont% = AALoadFont("Blitz", Int(22 * (GraphicHeight / 1024.0)), 0,0,0,1)
						;ReloadAAFont()
						AATextEnable_Prev% = AATextEnable
					EndIf
					If MouseOn(x+310*MenuScale,y+MenuScale,20*MenuScale,20*MenuScale)
						DrawOptionsTooltip(tx,ty,tw,th,"antialiastext")
					EndIf
					;[End Block]
				EndIf
				;[End Block]
			Case 4 ; load map
				;[Block]
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 510 * MenuScale
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				width = 400 * MenuScale
				height = 70 * MenuScale
				
				Color(255, 255, 255)
				AASetFont fo\Font[1]
				AAText(GraphicWidth/2, y + height / 2, scpLang_GetPhrase$("menu.loadmap"), True, True)

				;x = 160 * MenuScale
				x = (GraphicWidth/2)-(290*MenuScale)
				y = y + height + 20 * MenuScale
				width = 580 * MenuScale
				height = 350 * MenuScale
				
				;AASetFont fo\Font[0]
				AASetFont fo\Font[1]
				
				tx# = x+width
				ty# = y
				tw# = 400*MenuScale
				th# = 150*MenuScale
				
				If CurrLoadGamePage < Ceil(Float(SavedMapsAmount)/6.0)-1 Then 
					If DrawButton(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, ">") Then
						CurrLoadGamePage = CurrLoadGamePage+1
					EndIf
				Else
					DrawFrame(x+530*MenuScale, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+555*MenuScale, y + 537.5*MenuScale, ">", True, True)
				EndIf
				If CurrLoadGamePage > 0 Then
					If DrawButton(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale, "<") Then
						CurrLoadGamePage = CurrLoadGamePage-1
					EndIf
				Else
					DrawFrame(x, y + 510*MenuScale, 50*MenuScale, 55*MenuScale)
					Color(100, 100, 100)
					AAText(x+25*MenuScale, y + 537.5*MenuScale, "<", True, True)
				EndIf
				
				;DrawFrame(x+50*MenuScale,y+510*MenuScale,width-100*MenuScale,55*MenuScale)

				Color(255, 255, 255)				
				AAText(x + (width / 2.0),y+536*MenuScale,scpLang_GetPhrase$("menu.page") + " "+Int(Max((CurrLoadGamePage+1),1))+"/"+Int(Max((Int(Ceil(Float(SavedMapsAmount)/6.0))),1)),True,True)										
									
				AASetFont fo\Font[0]
				
				If CurrLoadGamePage > Ceil(Float(SavedMapsAmount)/6.0)-1 Then
					CurrLoadGamePage = CurrLoadGamePage - 1
				EndIf
				
				;AASetFont fo\Font[0]
				
				If SavedMaps(0)="" Then 
					AAText (x + 20 * MenuScale, y + 20 * MenuScale, scpLang_GetPhrase$("menu.nosavedmaps"))
				Else
					x = x + 20 * MenuScale
					y = y + 20 * MenuScale
					For i = (1+(6*CurrLoadGamePage)) To 6+(6*CurrLoadGamePage)
						If i <= SavedMapsAmount Then
							DrawFrame(x,y,540* MenuScale, 70* MenuScale)
							
							AAText(x + 20 * MenuScale, y + 10 * MenuScale, SavedMaps(i - 1))
							AAText(x + 20 * MenuScale, y + (10+27) * MenuScale, SavedMapsAuthor(i - 1))
							
							If DrawButton(x + 400 * MenuScale, y + 20 * MenuScale, 100 * MenuScale, 30 * MenuScale, scpLang_GetPhrase$("menu.load"), False) Then
								SelectedMap=SavedMaps(i - 1)
								ms\MainMenuTab = 1
							EndIf
							If MouseOn(x + 400 * MenuScale, y + 20 * MenuScale, 100*MenuScale,30*MenuScale)
								DrawMapCreatorTooltip(tx,ty,tw,th,SavedMaps(i-1))
							EndIf
							
							y = y + 80 * MenuScale
						Else
							Exit
						EndIf
					Next
				EndIf
				;[End Block]
		End Select
		
	End If
	
	Color 255, 255, 255
	AASetFont fo\ConsoleFont
	AAText 20, GraphicHeight - 30, "SCP:CB Remastered " + scpModding_Version()
	If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
	
	AASetFont fo\Font[0]
End Function

Function FPSMainMenu()
    Local fo.Fonts = First Fonts
    Local fs.FPS_Settings = First FPS_Settings

	If ShowFPS Then AASetFont fo\ConsoleFont : AAText 20, GraphicHeight-50, "FPS: " + fs\FPS : AASetFont fo\Font[0]
	
End Function

Function UpdateLauncher()
    Local fo.Fonts = First Fonts
    Local i%
	MenuScale = 1
	
	Graphics3DExt(600, 500, 0, 2)

	;InitExt
	
	SetBuffer BackBuffer()
	
	RealGraphicWidth = GraphicWidth
	RealGraphicHeight = GraphicHeight

	fo\Font[0] = LoadFont_Strict(scpModding_ProcessFilePath$("GFX\font\"+"cour\Courier New.ttf"), 18, 0,0,0)
	SetFont fo\Font[0]
	MenuWhite = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\menuwhite.png"))
	MenuBlack = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\menublack.png"))	
	MaskImage MenuBlack, 255,255,0
	LauncherIMG = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\launcher.png"))		
	ButtonSFX% = LoadSound_Strict(scpModding_ProcessFilePath$("SFX\Interact\Button.ogg"))		
	For i = 0 To 3
		ArrowIMG(i) = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\menu\arrow.png"))
		RotateImage(ArrowIMG(i), 90 * i)
		HandleImage(ArrowIMG(i), 0, 0)
	Next
	
	For i% = 1 To TotalGFXModes
		Local samefound% = False
		For  n% = 0 To TotalGFXModes - 1
			If GfxModeWidths(n) = GfxModeWidth(i) And GfxModeHeights(n) = GfxModeHeight(i) Then samefound = True : Exit
		Next
		If samefound = False Then
			If GfxModeWidth(i) >= 1024 And GfxModeHeight(i) >= 768
				If GraphicWidth = GfxModeWidth(i) And GraphicHeight = GfxModeHeight(i) Then SelectedGFXMode = GFXModes
				GfxModeWidths(GFXModes) = GfxModeWidth(i)
				GfxModeHeights(GFXModes) = GfxModeHeight(i)
				GFXModes=GFXModes+1 
			EndIf
		End If
	Next
	
	BlinkMeterIMG% = LoadImage_Strict(scpModding_ProcessFilePath$("GFX\"+"blinkmeter.png"))
		
	AppTitle "SCP:CB Remastered Launcher " + scpModding_Version()
	
	Local selectedDevice = 0
	If Fullscreen Then selectedDevice = 0
	If BorderlessWindowed Then selectedDevice = 1
	If (Not Fullscreen) And (Not BorderlessWindowed) Then selectedDevice = 2

	Repeat
	
		; ;Cls
		Color 0,0,0
		Rect 0,0,LauncherWidth,LauncherHeight,True
		
		 MouseHit1 = MouseHit(1)
		
		 Color 255, 255, 255
		 DrawImage(LauncherIMG, 0, 0)
		
		; ;Text(20, 240 - 65, "Resolution: ")
		
		Local x% = 70
		Local y% = 190
		For i = 0 To (GFXModes - 1)
			Color 255, 0, 0
			If SelectedGFXMode = i Then Rect(x - 1, y - 1, 100, 20, False)
			
			Color 255, 255, 255
			Text(x, y, (GfxModeWidths(i) + "x" + GfxModeHeights(i)))
			If MouseOn(x - 1, y - 1, 100, 20) Then
				Color 255, 255, 255
				Rect(x - 1, y - 1, 100, 20, False)
				If MouseHit1 Then SelectedGFXMode = i
			EndIf
			
			y=y+20
			If y >= 350 Then y = 190 : x=x+100
		Next
		
		; ;-----------------------------------------------------------------
		; Color 255, 255, 255
		; x = 30
		; y = 369
		; ;Rect(x - 10, y, 340, 95)
		; ;Text(x - 10, y - 25, "Graphics:")
		
		; ; y=y+10
		; ; For i = 1 To CountGfxDrivers()
		; ;     Color 255, 0, 0
		; ; 	If SelectedGFXDriver = i Then Rect(x - 1, y - 1, 290, 20, False)
		; ; 	;text(x, y, bbGfxDriverName(i))
		; ; 	Color 255, 255, 255
		; ; 	LimitText(GfxDriverName(i), x, y, 290, False)
		; ; 	If MouseOn(x - 1, y - 1, 290, 20) Then
		; ; 		Color 100, 100, 100
		; ; 		Rect(x - 1, y - 1, 290, 20, False)
		; ; 		If MouseHit1 Then SelectedGFXDriver = i
		; ; 	EndIf
			
		; ; 	y=y+20
		; ; Next

		
		If DrawButton(323, 218, 241, 30, GfxDriverName(SelectedGFXDriver), False, False, False) Then
		    If SelectedGFXDriver = CountGfxDrivers()
				SelectedGFXDriver = 1 
			Else 
				SelectedGFXDriver = SelectedGFXDriver + 1 
			EndIf
		EndIf

		Local deviceText$
		If selectedDevice = 0 Then deviceText = scpLang_GetPhrase$("launcher.fullscreen")
		If selectedDevice = 1 Then deviceText = scpLang_GetPhrase$("launcher.borderless")
		If selectedDevice = 2 Then deviceText = scpLang_GetPhrase$("launcher.windowed")
		If DrawButton(323, 281, 241, 30, deviceText, False, False, False) Then
		    If selectedDevice = 2
				selectedDevice = 0
			Else
				selectedDevice = selectedDevice + 1
			EndIf

			If selectedDevice = 0 
				Fullscreen = true
				BorderlessWindowed = false
				Windowed = false
			ElseIf selectedDevice = 1
				Fullscreen = false
				BorderlessWindowed = true
				Windowed = false
			ElseIf selectedDevice = 2
				Fullscreen = false
				BorderlessWindowed = false
				Windowed = true
			EndIf
		EndIf

		If DrawButton(353, 344, 181, 30, scpLang_GetPhrase$("language.name"), False, False, False) Then
			
		EndIf

		If DrawButton(323, 344, 30, 30, "<", False, False, False) Then
			scpLang_BackLang()
			PutINIValue(OptionFile, "game", "lang", scpLang_GetLang())
			SaveOptionsINI()
		EndIf

		If DrawButton(534, 344, 30, 30, ">", False, False, False) Then
			scpLang_NextLang()
			PutINIValue(OptionFile, "game", "lang", scpLang_GetLang())
			SaveOptionsINI()
		EndIf
		
		If (DrawButton(41, 400, 120, 30, scpLang_GetPhrase$("launcher.discord"), False, False, False)) Then
			ExecFile("https://discord.gg/Q7VKS6hwEV")
		EndIf

		If (DrawButton(165, 400, 120, 30, scpLang_GetPhrase$("launcher.mods"), False, False, False)) Then
			ExecFile("https://steamcommunity.com/app/2090230/workshop/")
		EndIf

		If (DrawButton(41, 432, 120, 30, scpLang_GetPhrase$("launcher.reset"), False, False, False)) Then
			scpSDK_ResetOptions()
		EndIf

		If (DrawButton(165, 432, 120, 30, scpLang_GetPhrase$("launcher.store"), False, False, False)) Then
			ExecFile("https://store.steampowered.com/app/2090230/SCP_Containment_Breach_Remastered/")
		EndIf

		; Fullscreen = DrawTick(50, 50, Fullscreen)
		; BorderlessWindowed = DrawTick(50, 70, BorderlessWindowed)
		; Windowed = DrawTick(50, 90, Windowed)
		lock% = False

		; If BorderlessWindowed Or (Not Fullscreen) Then lock% = True
		; Bit16Mode = DrawTick(40 + 630 - 15, 260 - 55 + 65 + 8, Bit16Mode,lock%)
		; LauncherEnabled = DrawTick(40 + 630 - 15, 260 - 55 + 95 + 8, LauncherEnabled)

		If BorderlessWindowed
 		    Color 255, 0, 0
 		    Fullscreen = False
		Else
  		    Color 255, 255, 255
		EndIf

		; Text(40 + 630 + 15, 262 - 55 + 5 - 8, "Fullscreen")
		; Color 255, 255, 255
		; Text(40 + 630 + 15, 262 - 55 + 35 - 8, "Borderless",False,False)
		; Text(40 + 630 + 15, 262 - 55 + 35 + 12, "windowed mode",False,False)

		; If BorderlessWindowed Or (Not Fullscreen)
 		;    Color 255, 0, 0
 		;    Bit16Mode = False
		; Else
		;     Color 255, 255, 255
		; EndIf

		; Text(40 + 630 + 15, 262 - 55 + 65 + 8, "16 Bit")
		; Color 255, 255, 255
		; Text(40 + 630 + 15, 262 - 55 + 95 + 8, "Use launcher")
		
		; If (Not BorderlessWindowed)
		; 	If Fullscreen
		; 		Text(40+ 620 + 15, 262 - 55 + 140, ""+(GfxModeWidths(SelectedGFXMode) + "x" + GfxModeHeights(SelectedGFXMode) + "," + (16+(16*(Not Bit16Mode)))))
		; 	Else
		; 		Text(40+ 620 + 15, 262 - 55 + 140, ""+(GfxModeWidths(SelectedGFXMode) + "x" + GfxModeHeights(SelectedGFXMode) + ",32"))
		; 	EndIf
		; Else
	    ;     Text(40+ 260 + 15, 462 - 55 + 140, ""+GfxModeWidths(SelectedGFXMode) + "x" + GfxModeHeights(SelectedGFXMode) + ",32")
		; 	If GfxModeWidths(SelectedGFXMode)<G_viewport_width Then
		; 		Text(40+ 620 + 15, 262 - 55 + 125, "Upscaled to")
		; 		Text(40+ 620 + 15, 262 - 55 + 145, G_viewport_width + "x" + G_viewport_height + ",32")
		; 	ElseIf GfxModeWidths(SelectedGFXMode)>G_viewport_width Then
		; 		Text(40+ 620 + 15, 262 - 55 + 125, "Downscaled to")
		; 		Text(40+ 620 + 15, 262 - 55 + 145, G_viewport_width + "x" + G_viewport_height + ",32")
		; 	EndIf
		; EndIf
		
		; If DrawButton(LauncherWidth - 20, LauncherHeight - 100, 110, 30, "BUG REPORT", False, False, False) Then
		;     ExecFile("https://discord.gg/scpcbr")
		; EndIf
		
		; If DrawButton(LauncherWidth - 20, LauncherHeight - 65, 110, 30, "DISCORD", False, False, False) Then
		;     ExecFile("https://discord.gg/scpcbr")
		; EndIf
		
		; If DrawButton(LauncherWidth - 280, LauncherHeight - 100, 110, 30, "WEBSITE", False, False, False) Then
		;     ExecFile("https://scpcbr.com")
		; EndIf
		
		; If DrawButton(LauncherWidth - 165,LauncherHeight - 100, 140, 30, "STORE PAGE", False, False, False) Then
		;     ExecFile("https://store.steampowered.com/app/2090230/SCP_Containment_Breach_Remastered/")
		; EndIf
		
		; If DrawButton(LauncherWidth - 280, LauncherHeight - 65, 110, 30, "WORKSHOP", False, False, False) Then
		;     ExecFile("https://steamcommunity.com/app/2090230/workshop/")
		; EndIf
		
		; If DrawButton(LauncherWidth - 165, LauncherHeight - 65, 140, 30, "RESET OPTIONS", False, False, False) Then
		;     scpSDK_ResetOptions()
		; EndIf

		If DrawButton(323, 407, 241, 50, scpLang_GetPhrase$("launcher.launch"), True, False, False) Then
			GraphicWidth = GfxModeWidths(SelectedGFXMode)
			GraphicHeight = GfxModeHeights(SelectedGFXMode)
			RealGraphicWidth = GraphicWidth
			RealGraphicHeight = GraphicHeight
			Exit
		EndIf

		
				
		; If DrawButton(LauncherWidth + 95, LauncherHeight - 65, 75, 30, "EXIT", False, False, False) Then End
		Flip
	Forever
	
	PutINIValue(OptionFile, "options", "width", GfxModeWidths(SelectedGFXMode))
	PutINIValue(OptionFile, "options", "height", GfxModeHeights(SelectedGFXMode))
	If Fullscreen Then
		PutINIValue(OptionFile, "options", "fullscreen", "true")
	Else
		PutINIValue(OptionFile, "options", "fullscreen", "false")
	EndIf
	If LauncherEnabled Then
		PutINIValue(OptionFile, "launcher", "launcher enabled", "true")
	Else
		PutINIValue(OptionFile, "launcher", "launcher enabled", "false")
	EndIf
	If BorderlessWindowed Then
		PutINIValue(OptionFile, "options", "borderless windowed", "true")
	Else
		PutINIValue(OptionFile, "options", "borderless windowed", "false")
	EndIf
	If Bit16Mode Then
		PutINIValue(OptionFile, "options", "16bit", "true")
	Else
		PutINIValue(OptionFile, "options", "16bit", "false")
	EndIf
	PutINIValue(OptionFile, "options", "gfx driver", SelectedGFXDriver)
		
End Function

Function DrawTiledImageRect(img%, srcX%, srcY%, srcwidth#, srcheight#, x%, y%, width%, height%)
	
	Local x2% = x
	While x2 < x+width
		Local y2% = y
		While y2 < y+height
			If x2 + srcwidth > x + width Then srcwidth = srcwidth - Max((x2 + srcwidth) - (x + width), 1)
			If y2 + srcheight > y + height Then srcheight = srcheight - Max((y2 + srcheight) - (y + height), 1)
			DrawImageRect(img, x2, y2, srcX, srcY, srcwidth, srcheight)
			y2 = y2 + srcheight
		Wend
		x2 = x2 + srcwidth
	Wend
	
End Function

Type LoadingScreens
	Field imgpath$
	Field img%
	Field ID%
	Field title$
	Field alignx%, aligny%
	Field disablebackground%
	Field txt$[5], txtamount%
End Type

Function InitLoadingScreens(file$)
	Local TemporaryString$, i%
	Local ls.LoadingScreens
	
	Local f = OpenFile(file)
	
	While Not Eof(f)
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString,1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			
			ls.LoadingScreens = New LoadingScreens
			LoadingScreenAmount=LoadingScreenAmount+1
			ls\ID = LoadingScreenAmount
			
			ls\title = TemporaryString
			ls\imgpath = scpModding_ProcessFilePath$(GetINIString(file, TemporaryString, "image path"))
			
			For i = 0 To 4
				ls\txt[i] = GetINIString(file, TemporaryString, "text"+(i+1))
				If ls\txt[i]<> "" Then ls\txtamount=ls\txtamount+1
			Next
			
			ls\disablebackground = GetINIInt(file, TemporaryString, "disablebackground")
			
			Select Lower(GetINIString(file, TemporaryString, "align x"))
				Case "left"
					ls\alignx = -1
				Case "middle", "center"
					ls\alignx = 0
				Case "right" 
					ls\alignx = 1
			End Select 
			
			Select Lower(GetINIString(file, TemporaryString, "align y"))
				Case "top", "up"
					ls\aligny = -1
				Case "middle", "center"
					ls\aligny = 0
				Case "bottom", "down"
					ls\aligny = 1
			End Select 			
			
		EndIf
	Wend
	
	CloseFile f
End Function

Function DrawLoading(percent%, shortloading=False)
	
	Local fo.Fonts = First Fonts
	Local x%, y%
	
	If percent = 0 Then
		LoadingScreenText=0
		
		temp = Rand(1,LoadingScreenAmount)
		For ls.loadingscreens = Each LoadingScreens
			If ls\id = temp Then
				If ls\img=0 Then ls\img = LoadImage_Strict(scpModding_ProcessFilePath$("Loadingscreens\"+ls\imgpath))
				ls\img = ResizeImage2(ls\img, ImageWidth(ls\img) * MenuScale, ImageHeight(ls\img) * MenuScale)
				SelectedLoadingScreen = ls 
				Exit
			EndIf
		Next
	EndIf	
	
	firstloop = True

	Repeat 
		
		;Color 0,0,0
		;Rect 0,0,GraphicWidth,GraphicHeight,True
		;Color 255, 255, 255
		ClsColor 0,0,0
		Cls
		
		;Cls(True,False)
		
		If percent > 20 Then
			UpdateMusic()
		EndIf
		
		If shortloading = False Then
			If percent > (100.0 / SelectedLoadingScreen\txtamount)*(LoadingScreenText+1) Then
				LoadingScreenText=LoadingScreenText+1
			EndIf
		EndIf
		
		If (Not SelectedLoadingScreen\disablebackground) Then
			DrawImage LoadingBack, GraphicWidth/2 - ImageWidth(LoadingBack)/2, GraphicHeight/2 - ImageHeight(LoadingBack)/2
		EndIf	
		If SelectedLoadingScreen\alignx = 0 Then
			x = GraphicWidth/2 - ImageWidth(SelectedLoadingScreen\img)/2 
		ElseIf  SelectedLoadingScreen\alignx = 1
			x = GraphicWidth - ImageWidth(SelectedLoadingScreen\img)
		Else
			x = 0
		EndIf
		
		If SelectedLoadingScreen\aligny = 0 Then
			y = GraphicHeight/2 - ImageHeight(SelectedLoadingScreen\img)/2 
		ElseIf  SelectedLoadingScreen\aligny = 1
			y = GraphicHeight - ImageHeight(SelectedLoadingScreen\img)
		Else
			y = 0
		EndIf	
		
		DrawImage SelectedLoadingScreen\img, x, y
		
		Local width% = 300, height% = 20
		x% = GraphicWidth / 2 - width / 2
		y% = GraphicHeight / 2 + 30 - 100
		
		Rect(x, y, width+4, height, False)
		For  i% = 1 To Int((width - 2) * (percent / 100.0) / 10)
			DrawImage(BlinkMeterIMG, x + 3 + 10 * (i - 1), y + 3)
		Next
		
		If SelectedLoadingScreen\title = "CWM" Then
			
			If Not shortloading Then 
				If firstloop Then 
					If percent = 0 Then
						PlaySound_Strict LoadTempSound(scpModding_ProcessFilePath$("SFX\"+"SCP\990\cwm1.cwm"))
					ElseIf percent = 100
						PlaySound_Strict LoadTempSound(scpModding_ProcessFilePath$("SFX\"+"SCP\990\cwm2.cwm"))
					EndIf
				EndIf
			EndIf
			
			AASetFont fo\Font[1]
			strtemp$ = ""
			temp = Rand(2,9)
			For i = 0 To temp
				strtemp$ = STRTEMP + Chr(Rand(48,122))
			Next
			AAText(GraphicWidth / 2, GraphicHeight / 2 + 80, scpLang_GetPhrase$(strtemp), True, True)
			
			If percent = 0 Then 
				If Rand(5)=1 Then
					Select Rand(2)
						Case 1
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.happenon") + " " + CurrentDate() + "."
						Case 2
							SelectedLoadingScreen\txt[0] = CurrentTime()
					End Select
				Else
					Select Rand(13)
						Case 1
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.fineradio")
						Case 2
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.burn")
						Case 3
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.cannotcontrol")
						Case 4
							SelectedLoadingScreen\txt[0] = "sebotan is a monkey"
						Case 5
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.trust")
						Case 6 
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.gentleman")
						Case 7
							SelectedLoadingScreen\txt[0] = "???____??_???__????n?"
						Case 8, 9
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.jorge")
						Case 10
							SelectedLoadingScreen\txt[0] = "???????????"
						Case 11
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.midnight")
						Case 12
							SelectedLoadingScreen\txt[0] = "eddie is a jew"
						Case 13
							SelectedLoadingScreen\txt[0] = scpLang_GetPhrase$("dynamicloadingscreens.alloylife")
					End Select
				EndIf
			EndIf
			
			strtemp$ = scpLang_GetPhrase$(SelectedLoadingScreen\txt[0])
			temp = Int(Len(SelectedLoadingScreen\txt[0])-Rand(5))
			For i = 0 To Rand(10,15);temp
				strtemp$ = Replace(SelectedLoadingScreen\txt[0],Mid(SelectedLoadingScreen\txt[0],Rand(1,Len(strtemp)-1),1),Chr(Rand(130,250)))
			Next		
			AASetFont fo\Font[0]
			RowText(scpLang_GetPhrase$(strtemp), GraphicWidth / 2-200, GraphicHeight / 2 +120,400,300,True)		
		ElseIf SelectedLoadingScreen\title = "SCP-789-J" Then
			Color 0,0,0
			AASetFont fo\Font[1]
			AAText(GraphicWidth / 2 + 1, GraphicHeight / 2 + 80 + 1, SelectedLoadingScreen\title, True, True)
			AASetFont fo\Font[0]
			RowText(scpLang_GetPhrase$(SelectedLoadingScreen\txt[LoadingScreenText]), GraphicWidth / 2-200+1, GraphicHeight / 2 +120+1,400,300,True)
			
			Color 255,255,255
			AASetFont fo\Font[1]
			AAText(GraphicWidth / 2, GraphicHeight / 2 +80, SelectedLoadingScreen\title, True, True)
			AASetFont fo\Font[0]
			RowText(scpLang_GetPhrase$(SelectedLoadingScreen\txt[LoadingScreenText]), GraphicWidth / 2-200, GraphicHeight / 2 +120,400,300,True)
		ElseIf SelectedLoadingScreen\title = "Walter White" Then
			Color 0,0,0
			AASetFont fo\Font[1]
			AAText(GraphicWidth / 2 + 1, GraphicHeight / 2 + 80 + 1, SelectedLoadingScreen\title, True, True)
			AASetFont fo\Font[0]
			RowText(scpLang_GetPhrase$(SelectedLoadingScreen\txt[LoadingScreenText]), GraphicWidth / 2-200+1, GraphicHeight / 2 +120+1,400,300,True)
			
			Color 255,255,255
			AASetFont fo\Font[1]
			AAText(GraphicWidth / 2, GraphicHeight / 2 +80, SelectedLoadingScreen\title, True, True)
			AASetFont fo\Font[0]
			RowText(scpLang_GetPhrase$(SelectedLoadingScreen\txt[LoadingScreenText]), GraphicWidth / 2-200, GraphicHeight / 2 +120,400,300,True)
		Else
			Color 0,0,0
			AASetFont fo\Font[1]
			AAText(GraphicWidth / 2 + 1, GraphicHeight / 2 + 80 + 1, SelectedLoadingScreen\title, True, True)
			AASetFont fo\Font[0]
			RowText(scpLang_GetPhrase$(SelectedLoadingScreen\txt[LoadingScreenText]), GraphicWidth / 2-200+1, GraphicHeight / 2 +120+1,400,300,True)
			
			Color 255,255,255
			AASetFont fo\Font[1]
			AAText(GraphicWidth / 2, GraphicHeight / 2 +80, SelectedLoadingScreen\title, True, True)
			AASetFont fo\Font[0]
			RowText(scpLang_GetPhrase$(SelectedLoadingScreen\txt[LoadingScreenText]), GraphicWidth / 2-200, GraphicHeight / 2 +120,400,300,True)
			
		EndIf
		
		Color 0,0,0
		AAText(GraphicWidth / 2 + 1, GraphicHeight / 2 - 100 + 1, scpLang_GetPhrase$("loadingscreens.loading") + " - " + percent + " %", True, True)
		Color 255,255,255
		AAText(GraphicWidth / 2, GraphicHeight / 2 - 100, scpLang_GetPhrase$("loadingscreens.loading") + " - " + percent + " %", True, True)
		
		If percent = 100 Then 
			If firstloop And SelectedLoadingScreen\title = "SCP-789-J" Then PlaySound_Strict LoadTempSound((scpModding_ProcessFilePath$("SFX\"+"SCP\789\butt.ogg")))
			If firstloop And SelectedLoadingScreen\title = "Walter White" Then PlaySound_Strict LoadTempSound((scpModding_ProcessFilePath$("SFX\"+"SCP\WW\walter.ogg")))
			AAText(GraphicWidth / 2, GraphicHeight - 50, scpLang_GetPhrase$("loadingscreens.anykey"), True, True)
		Else
			FlushKeys()
			FlushMouse()
		EndIf
		
		If BorderlessWindowed Then
			If (RealGraphicWidth<>GraphicWidth) Or (RealGraphicHeight<>GraphicHeight) Then
				SetBuffer TextureBuffer(fresize_texture)
				ClsColor 0,0,0 : Cls
				CopyRect 0,0,GraphicWidth,GraphicHeight,1024-GraphicWidth/2,1024-GraphicHeight/2,BackBuffer(),TextureBuffer(fresize_texture)
				SetBuffer BackBuffer()
				ClsColor 0,0,0 : Cls
				ScaleRender(0,0,2050.0 / Float(GraphicWidth) * AspectRatioRatio, 2050.0 / Float(GraphicWidth) * AspectRatioRatio)
				;might want to replace Float(GraphicWidth) with Max(GraphicWidth,GraphicHeight) if portrait sizes cause issues
				;everyone uses landscape so it's probably a non-issue
			EndIf
		EndIf
		
		;not by any means a perfect solution
		;Not even proper gamma correction but it's a nice looking alternative that works in windowed mode
		If ScreenGamma>1.0 Then
			CopyRect 0,0,RealGraphicWidth,RealGraphicHeight,1024-RealGraphicWidth/2,1024-RealGraphicHeight/2,BackBuffer(),TextureBuffer(fresize_texture)
			EntityBlend fresize_image,1
			ClsColor 0,0,0 : Cls
			ScaleRender(-1.0/Float(RealGraphicWidth),1.0/Float(RealGraphicWidth),2048.0 / Float(RealGraphicWidth),2048.0 / Float(RealGraphicWidth))
			EntityFX fresize_image,1+32
			EntityBlend fresize_image,3
			EntityAlpha fresize_image,ScreenGamma-1.0
			ScaleRender(-1.0/Float(RealGraphicWidth),1.0/Float(RealGraphicWidth),2048.0 / Float(RealGraphicWidth),2048.0 / Float(RealGraphicWidth))
		ElseIf ScreenGamma<1.0 Then ;todo: maybe optimize this if it's too slow, alternatively give players the option to disable gamma
			CopyRect 0,0,RealGraphicWidth,RealGraphicHeight,1024-RealGraphicWidth/2,1024-RealGraphicHeight/2,BackBuffer(),TextureBuffer(fresize_texture)
			EntityBlend fresize_image,1
			ClsColor 0,0,0 : Cls
			ScaleRender(-1.0/Float(RealGraphicWidth),1.0/Float(RealGraphicWidth),2048.0 / Float(RealGraphicWidth),2048.0 / Float(RealGraphicWidth))
			EntityFX fresize_image,1+32
			EntityBlend fresize_image,2
			EntityAlpha fresize_image,1.0
			SetBuffer TextureBuffer(fresize_texture2)
			ClsColor 255*ScreenGamma,255*ScreenGamma,255*ScreenGamma
			Cls
			SetBuffer BackBuffer()
			ScaleRender(-1.0/Float(RealGraphicWidth),1.0/Float(RealGraphicWidth),2048.0 / Float(RealGraphicWidth),2048.0 / Float(RealGraphicWidth))
			SetBuffer(TextureBuffer(fresize_texture2))
			ClsColor 0,0,0
			Cls
			SetBuffer(BackBuffer())
		EndIf
		EntityFX fresize_image,1
		EntityBlend fresize_image,1
		EntityAlpha fresize_image,1.0
		
		Flip False
		
		firstloop = False
		If percent <> 100 Then Exit
		
		Local close% = False
		If (GetKey()<>0 Or MouseHit(1))
			close=True
			FlushKeys()
			FlushMouse()
			AASetFont fo\Font[0]
		EndIf
	Until close
	
End Function

Function rInput$(aString$)
	Local value% = GetKey()
	Local length% = Len(aString$)
	
	If value = 8 Then
		value = 0
		If length > 0 Then aString$ = Left(aString, length - 1)
	EndIf
	
	If value = 13 Or value = 0 Then
		Return aString$
	ElseIf value > 0 And value < 7 Or value > 26 And value < 32 Or value = 9
		Return aString$
	Else
		aString$ = aString$ + Chr(value)
		Return aString$
	End If
End Function

Function InputBox$(x%, y%, width%, height%, Txt$, ID% = 0)
	;TextBox(x,y,width,height,Txt$)
	Color (255, 255, 255)
	DrawTiledImageRect(MenuWhite, (x Mod 256), (y Mod 256), 512, 512, x, y, width, height)
	;Rect(x, y, width, height)
	Color (0, 0, 0)
	
	Local MouseOnBox% = False
	If MouseOn(x, y, width, height) Then
		Color(50, 50, 50)
		MouseOnBox = True
		If MouseHit1 Then SelectedInputBox = ID : FlushKeys
	EndIf
	
	Rect(x + 2, y + 2, width - 4, height - 4)
	Color (255, 255, 255)	
	
	If (Not MouseOnBox) And MouseHit1 And SelectedInputBox = ID Then SelectedInputBox = 0
	
	If SelectedInputBox = ID Then
		Txt = rInput(Txt)
		If (MilliSecs2() Mod 800) < 400 Then Rect (x + width / 2 + AAStringWidth(Txt) / 2 + 2, y + height / 2 - 5, 2, 12)
	EndIf	
	
	AAText(x + width / 2, y + height / 2, Txt, True, True)
	
	Return Txt
End Function

Function DrawFrame(x%, y%, width%, height%, xoffset%=0, yoffset%=0)
	Color 255, 255, 255
	DrawTiledImageRect(MenuWhite, xoffset, (y Mod 256), 512, 512, x, y, width, height)
	
	DrawTiledImageRect(MenuBlack, yoffset, (y Mod 256), 512, 512, x+3*MenuScale, y+3*MenuScale, width-6*MenuScale, height-6*MenuScale)	
End Function

Function DrawButton%(x%, y%, width%, height%, txt$, bigfont% = True, waitForMouseUp%=False, usingAA%=True)
	Local clicked% = False
	Local fo.Fonts = First Fonts
	
	DrawFrame (x, y, width, height)
	If MouseOn(x, y, width, height) Then
		Color(30, 30, 30)
		If (MouseHit1 And (Not waitForMouseUp)) Or (MouseUp1 And waitForMouseUp) Then 
			clicked = True
			PlaySound_Strict(ButtonSFX)
		EndIf
		Rect(x + 4, y + 4, width - 8, height - 8)	
	Else
		Color(0, 0, 0)
	EndIf
	
	Color (255, 255, 255)
	; If usingAA Then
	; 	If bigfont Then AASetFont fo\Font[1] Else AASetFont fo\Font[0]
	; 	AAText(x + width / 2, y + height / 2, txt, True, True)
	; Else
	; 	If bigfont Then SetFont fo\Font[1] Else SetFont fo\Font[0]
	; 	Text(x + width / 2, y + height / 2, txt, True, True)
	; EndIf
	If bigfont Then AASetFont fo\Font[1] Else AASetFont fo\Font[0]
	AAText(x + width / 2, y + height / 2, txt, True, True)
	
	Return clicked
End Function

Function DrawButton2%(x%, y%, width%, height%, txt$, bigfont% = True)
	Local clicked% = False
	Local fo.Fonts = First Fonts
	
	DrawFrame (x, y, width, height)
	Local hit% = MouseHit(1)
	If MouseOn(x, y, width, height) Then
		Color(30, 30, 30)
		If hit Then clicked = True : PlaySound_Strict(ButtonSFX)
		Rect(x + 4, y + 4, width - 8, height - 8)	
	Else
		Color(0, 0, 0)
	EndIf
	
	Color (255, 255, 255)
	If bigfont Then SetFont fo\Font[1] Else SetFont fo\Font[0]
	Text(x + width / 2, y + height / 2, txt, True, True)
	
	Return clicked
End Function

Function DrawTick%(x%, y%, selected%, locked% = False)
	Local width% = 20 * MenuScale, height% = 20 * MenuScale
	
	Color (255, 255, 255)
	DrawTiledImageRect(MenuWhite, (x Mod 256), (y Mod 256), 512, 512, x, y, width, height)
	;Rect(x, y, width, height)
	
	Local Highlight% = MouseOn(x, y, width, height) And (Not locked)
	
	If Highlight Then
		Color(50, 50, 50)
		If MouseHit1 Then selected = (Not selected) : PlaySound_Strict (ButtonSFX)
	Else
		Color(0, 0, 0)		
	End If
	
	Rect(x + 2, y + 2, width - 4, height - 4)
	
	If selected Then
		If Highlight Then
			Color 255,255,255
		Else
			Color 200,200,200
		EndIf
		DrawTiledImageRect(MenuWhite, (x Mod 256), (y Mod 256), 512, 512, x + 4, y + 4, width - 8, height - 8)
		;Rect(x + 4, y + 4, width - 8, height - 8)
	EndIf
	
	Color 255, 255, 255
	
	Return selected
End Function

Function SlideBar#(x%, y%, width%, value#)
	
	If MouseDown1 And OnSliderID=0 Then
		If ScaledMouseX() >= x And ScaledMouseX() <= x + width + 14 And ScaledMouseY() >= y And ScaledMouseY() <= y + 20 Then
			value = Min(Max((ScaledMouseX() - x) * 100 / width, 0), 100)
		EndIf
	EndIf
	
	Color 255,255,255
	Rect(x, y, width + 14, 20,False)
	
	DrawImage(BlinkMeterIMG, x + width * value / 100.0 +3, y+3)
	
	Color 170,170,170 
	AAText (x - 50 * MenuScale, y + 4*MenuScale, scpLang_GetPhrase$("menu.low"))					
	AAText (x + width + 38 * MenuScale, y+4*MenuScale, scpLang_GetPhrase$("menu.high"))	
	
	Return value
	
End Function

Function RowText(A$, X, Y, W, H, align% = 0, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=2048
	
	Local LinesShown = 0
	Local Height = AAStringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (AAStringWidth (b$ + temp$) > W) And (AAStringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If AAStringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			If align Then
				AAText(X + W / 2 - (AAStringWidth(b) / 2), LinesShown * Height + Y, b)
			Else
				AAText(X, LinesShown * Height + Y, b)
			EndIf			
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	If (b$ <> "") And((LinesShown + 1) <= H) Then
		If align Then
			AAText(X + W / 2 - (AAStringWidth(b) / 2), LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		Else
			AAText(X, LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		EndIf
	EndIf
	
End Function

Function RowText2(A$, X, Y, W, H, align% = 0, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=2048
	
	Local LinesShown = 0
	Local Height = StringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (StringWidth (b$ + temp$) > W) And (StringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If StringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			If align Then
				Text(X + W / 2 - (StringWidth(b) / 2), LinesShown * Height + Y, b)
			Else
				Text(X, LinesShown * Height + Y, b)
			EndIf
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	If (b$ <> "") And((LinesShown + 1) <= H) Then
		If align Then
			Text(X + W / 2 - (StringWidth(b) / 2), LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		Else
			Text(X, LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		EndIf
	EndIf
	
End Function

Function GetLineAmount(A$, W, H, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=2048
	
	Local LinesShown = 0
	Local Height = AAStringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (AAStringWidth (b$ + temp$) > W) And (AAStringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If AAStringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	Return LinesShown+1
	
End Function

Function GetLineAmount2(A$, W, H, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=2048
	
	Local LinesShown = 0
	Local Height = StringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (StringWidth (b$ + temp$) > W) And (StringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If StringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	Return LinesShown+1
	
End Function

Function LimitText%(txt$, x%, y%, width%, usingAA%=True)
	Local TextLength%
	Local UnFitting%
	Local LetterWidth%
	If usingAA Then
		If txt = "" Or width = 0 Then Return 0
		TextLength = AAStringWidth(txt)
		UnFitting = TextLength - width
		If UnFitting <= 0 Then ;mahtuu
			AAText(x, y, txt)
		Else ;ei mahdu
			LetterWidth = TextLength / Len(txt)
			
			AAText(x, y, Left(txt, Max(Len(txt) - UnFitting / LetterWidth - 4, 1)) + "...")
		End If
	Else
		If txt = "" Or width = 0 Then Return 0
		TextLength = StringWidth(txt)
		UnFitting = TextLength - width
		If UnFitting <= 0 Then ;mahtuu
			Text(x, y, txt)
		Else ;ei mahdu
			LetterWidth = TextLength / Len(txt)
			
			Text(x, y, Left(txt, Max(Len(txt) - UnFitting / LetterWidth - 4, 1)) + "...")
		End If
	EndIf
End Function

Function DrawTooltip(message$)
	Local scale# = GraphicHeight/768.0
	Local width = (AAStringWidth(message$))+20*MenuScale
	Local fo.Fonts = First Fonts
	
	Color 25,25,25
	Rect(ScaledMouseX()+20,ScaledMouseY(),width,19*scale,True)
	Color 150,150,150
	Rect(ScaledMouseX()+20,ScaledMouseY(),width,19*scale,False)
	AASetFont fo\Font[0]
	AAText(ScaledMouseX()+(20*MenuScale)+(width/2),ScaledMouseY()+(12*MenuScale), message$, True, True)
End Function

Global QuickLoadPercent% = -1
Global QuickLoadPercent_DisplayTimer# = 0
Global QuickLoad_CurrEvent.Events

Function DrawQuickLoading()
    Local fo.Fonts = First Fonts
	Local fs.FPS_Settings = First FPS_Settings
	
	If QuickLoadPercent > -1
		MidHandle QuickLoadIcon
		DrawImage QuickLoadIcon,GraphicWidth-90,GraphicHeight-150
		Color 255,255,255
		AASetFont fo\Font[0]
		AAText GraphicWidth-100,GraphicHeight-90,scpLang_GetPhrase$("loadingscreens.loading")+": "+QuickLoadPercent+"%",1
		If QuickLoadPercent > 99
			If QuickLoadPercent_DisplayTimer < 70
				QuickLoadPercent_DisplayTimer# = Min(QuickLoadPercent_DisplayTimer+fs\FPSfactor[0],70)
			Else
				QuickLoadPercent = -1
			EndIf
		EndIf
		QuickLoadEvents()
	Else
		QuickLoadPercent = -1
		QuickLoadPercent_DisplayTimer# = 0
		QuickLoad_CurrEvent = Null
	EndIf
	
End Function

Function DrawOptionsTooltip(x%,y%,width%,height%,option$,value#=0,ingame%=False)
	Local fx# = x+6*MenuScale
	Local fy# = y+6*MenuScale
	Local fw# = width-12*MenuScale
	Local fh# = height-12*MenuScale
	Local lines% = 0, lines2% = 0
	Local txt$ = ""
	Local txt2$ = "", R% = 0, G% = 0, B% = 0
	Local usetestimg% = False, extraspace% = 0
	Local fo.Fonts = First Fonts
	
	AASetFont fo\Font[0]
	Color 255,255,255
	Select Lower(option$)
		;Graphic options
			;[Block]
		Case "bump"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.bumpmapping")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.bumpmapping2")
			txt2 = scpLang_GetPhrase$("tooltips.notingame")
			R = 255
		Case "vsync"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.vsync")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.vsync2")
		Case "antialias"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.antialiasing")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.antialiasing2")
			txt2 = scpLang_GetPhrase$("tooltips.fullscreenonly")
			R = 255
		Case "roomlights"
			txt = scpLang_GetPhrase$("tooltips.roomlights")
		Case "gamma"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.gammacorrection")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.gammacorrection2")
			R = 255
			G = 255
			B = 255
			txt2 = scpLang_GetPhrase$("tooltips.currentval") + " "+Int(value*100)+"% (" + scpLang_GetPhrase$("tooltips.defaultis") + " 100%)"			
		Case "texquality"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.lodbias")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.lodbias2")
		Case "particleamount"
			txt = scpLang_GetPhrase$("tooltips.particleamount")
			Select value
				Case 0
					R = 255
					txt2 = scpLang_GetPhrase$("tooltips.particleamountsmoke")
				Case 1
					R = 255
					G = 255
					txt2 = scpLang_GetPhrase$("tooltips.particleamountfew")
				Case 2
					G = 255
					txt2 = scpLang_GetPhrase$("tooltips.particleamountall")
			End Select
		Case "vram"
			txt = scpLang_GetPhrase$("tooltips.vram")
			txt2 = scpLang_GetPhrase$("tooltips.notingame")
			R = 255
			;[End Block]
		;Sound options
			;[Block]
		Case "musicvol"
			txt = scpLang_GetPhrase$("tooltips.musicvol")
			R = 255
			G = 255
			B = 255
			txt2 = scpLang_GetPhrase$("tooltips.currentval")+" "+Int(value*100)+"% (" + scpLang_GetPhrase$("tooltips.defaultis") + " 50%)"			
		Case "soundvol"
			txt = scpLang_GetPhrase$("tooltips.soundvol")
			R = 255
			G = 255
			B = 255
			txt2 = scpLang_GetPhrase$("tooltips.currentval")+" "+Int(value*100)+"% (" + scpLang_GetPhrase$("tooltips.defaultis") + " 50%)"			
		Case "sfxautorelease"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.soundautorelease")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.soundautorelease2")
			R = 255
			txt2 = scpLang_GetPhrase$("tooltips.notingame")
		Case "usertrack"
			txt = scpLang_GetPhrase$("tooltips.usertrack1")+" " + Chr(34) + "SFX\Radio\UserTracks" + Chr(34)
			txt = txt + " " + scpLang_GetPhrase$("tooltips.usertrack2") + " " + Chr(34) + "1" + Chr(34) + " " + scpLang_GetPhrase$("tooltips.usertrack3")
			R = 255
			txt2 = scpLang_GetPhrase$("tooltips.notingame")
		Case "usertrackmode"
			txt = scpLang_GetPhrase$("tooltips.usertrackmode1") + " "+Chr(34)+scpLang_GetPhrase$("tooltips.usertrackmode2")+Chr(34)+" " + scpLang_GetPhrase$("tooltips.usertrackmode3") + " "+Chr(34)+scpLang_GetPhrase$("tooltips.usertrackmode4")+Chr(34)+" " + scpLang_GetPhrase$("tooltips.usertrackmode5")
			R = 255
			G = 255
			txt2 = scpLang_GetPhrase$("tooltips.usertrackmode6")
		Case "usertrackscan"
			txt = scpLang_GetPhrase$("tooltips.usertrackrescan")
			;[End Block]
		;Control options	
			;[Block]
		Case "mousesensitivity"
			txt = scpLang_GetPhrase$("tooltips.mousesens")
			R = 255
			G = 255
			B = 255
			txt2 = scpLang_GetPhrase$("tooltips.currentval")+" "+Int((0.5+value)*100)+"% (" + scpLang_GetPhrase$("tooltips.defaultis") + " 50%)"			
		Case "mouseinvert"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.mouseinvert")+Chr(34)+" " + scpLang_GetPhrase$("tooltips.selfexplan")
		Case "mousesmoothing"
			txt = scpLang_GetPhrase$("tooltips.mousesmoothing")
		Case "controls"
			txt = scpLang_GetPhrase$("tooltips.controls")
			;[End Block]
		;Advanced options	
			;[Block]
		Case "hud"
			txt = scpLang_GetPhrase$("tooltips.hud")
		Case "consoleenable"
			txt = scpLang_GetPhrase$("tooltips.devconsole") + " " + KeyName(KEY_CONSOLE) + "."
		Case "consoleerror"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.consoleonerror")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.selfexplan")
		Case "achpopup"
			txt = scpLang_GetPhrase$("tooltips.achievements")
		Case "showfps"
			txt = scpLang_GetPhrase$("tooltips.showfps")
		Case "framelimit"
			txt = scpLang_GetPhrase$("tooltips.limitfps")
			If value > 0 And value < 60
				R = 255
				G = 255
				txt2 = scpLang_GetPhrase$("tooltips.limitfps2")
			EndIf
		Case "antialiastext"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.antialiasedtext")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.antialiasedtext2")
			;[End Block]
			
		;{~--<MOD>--~}
		
		    ;[Block]
		Case "thaumiel" ;WIP
			txt = scpLang_GetPhrase$("tooltips.apollyon")
		Case "consoleversion"
		    txt = scpLang_GetPhrase$("tooltips.consoleversion")
		Case "fov"
			txt = Chr(34)+scpLang_GetPhrase$("tooltips.fov")+Chr(34)+" "+scpLang_GetPhrase$("tooltips.fov2")
			R = 255
			G = 255
			B = 255
			txt2 = scpLang_GetPhrase$("tooltips.currentval")+" "+Int(FOV)+" (" + scpLang_GetPhrase$("tooltips.defaultis") + " 74%)"
			;[End Block]
			
        ;{~--<END>--~}

	End Select
	
	lines% = GetLineAmount(txt,fw,fh)
	If usetestimg
		extraspace = 210*MenuScale
	EndIf
	If txt2$ = ""
		DrawFrame(x,y,width,((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+extraspace)
	Else
		lines2% = GetLineAmount(txt2,fw,fh)
		DrawFrame(x,y,width,(((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+(AAStringHeight(txt2)*lines2)+(10+lines2)*MenuScale)+extraspace)
	EndIf
	RowText(txt,fx,fy,fw,fh)
	If txt2$ <> ""
		Color R,G,B
		RowText(txt2,fx,(fy+(AAStringHeight(txt)*lines)+(5+lines)*MenuScale),fw,fh)
	EndIf
	If usetestimg
		MidHandle Menu_TestIMG
		If txt2$ = ""
			DrawImage Menu_TestIMG,x+(width/2),y+100*MenuScale+((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)
		Else
			DrawImage Menu_TestIMG,x+(width/2),y+100*MenuScale+(((AAStringHeight(txt)*lines)+(10+lines)*MenuScale)+(AAStringHeight(txt2)*lines2)+(10+lines2)*MenuScale)
		EndIf
	EndIf
	
End Function

Function DrawMapCreatorTooltip(x%,y%,width%,height%,mapname$)
	Local fx# = x+6*MenuScale
	Local fy# = y+6*MenuScale
	Local fw# = width-12*MenuScale
	Local fh# = height-12*MenuScale
	Local lines% = 0
	Local fo.Fonts = First Fonts
	
	AASetFont fo\Font[0]
	Color 255,255,255
	
	Local txt$[5]
	If Right(mapname,6)="cbmap2" Then
		txt[0] = Left(mapname$,Len(mapname$)-7)
		Local f% = OpenFile("Map Creator\Maps\"+mapname$)
		
		Local author$ = ReadLine(f)
		Local descr$ = ReadLine(f)
		ReadByte(f)
		ReadByte(f)
		Local ramount% = ReadInt(f)
		If ReadInt(f) > 0 Then
			Local hasForest% = True
		Else
			hasForest% = False
		EndIf
		If ReadInt(f) > 0 Then
			Local hasMT% = True
		Else
			hasMT% = False
		EndIf
		
		CloseFile f%
	Else
		txt[0] = Left(mapname$,Len(mapname$)-6)
		author$ = scpLang_GetPhrase$("tooltips.mapauthorunknown")
		descr$ = scpLang_GetPhrase$("tooltips.mapdescnone")
		ramount% = 0
		hasForest% = False
		hasMT% = False
	EndIf
	txt[1] = scpLang_GetPhrase$("tooltips.mapmadeby")+" "+author$
	txt[2] = scpLang_GetPhrase$("tooltips.mapdesc")+" "+descr$
	If ramount > 0 Then
		txt[3] = scpLang_GetPhrase$("tooltips.maproomamm")+" "+ramount
	Else
		txt[3] = scpLang_GetPhrase$("tooltips.maproomammunknown")
	EndIf
	If hasForest Then
		txt[4] = scpLang_GetPhrase$("tooltips.mapforest")+" "+scpLang_GetPhrase$("menu.yes")
	Else
		txt[4] = scpLang_GetPhrase$("tooltips.mapforest")+" "+scpLang_GetPhrase$("menu.no")
	EndIf
	If hasMT Then
		txt[5] = scpLang_GetPhrase$("tooltips.mapmaintenance")+" "+scpLang_GetPhrase$("menu.yes")
	Else
		txt[5] = scpLang_GetPhrase$("tooltips.mapmaintenance")+" "+scpLang_GetPhrase$("menu.no")
	EndIf
	
	lines% = GetLineAmount(txt[2],fw,fh)
	DrawFrame(x,y,width,(AAStringHeight(txt[0])*6)+AAStringHeight(txt[2])*lines+5*MenuScale)
	
	Color 255,255,255
	AAText(fx,fy,txt[0])
	AAText(fx,fy+AAStringHeight(txt[0]),txt[1])
	RowText(txt[2],fx,fy+(AAStringHeight(txt[0])*2),fw,fh)
	AAText(fx,fy+((AAStringHeight(txt[0])*2)+AAStringHeight(txt[2])*lines+5*MenuScale),txt[3])
	AAText(fx,fy+((AAStringHeight(txt[0])*3)+AAStringHeight(txt[2])*lines+5*MenuScale),txt[4])
	AAText(fx,fy+((AAStringHeight(txt[0])*4)+AAStringHeight(txt[2])*lines+5*MenuScale),txt[5])
	
End Function

Global OnSliderID% = 0

Function Slider3(x%,y%,width%,value%,ID%,val1$,val2$,val3$)
	
	If MouseDown1 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			OnSliderID = ID
		EndIf
	EndIf
	
	Color 200,200,200
	Rect(x,y,width+14,10,True)
	Rect(x,y-8,4,14,True)
	Rect(x+(width/2)+5,y-8,4,14,True)
	Rect(x+width+10,y-8,4,14,True)
	
	If ID = OnSliderID
		If (ScaledMouseX() <= x+8)
			value = 0
		ElseIf (ScaledMouseX() >= x+width/2) And (ScaledMouseX() <= x+(width/2)+8)
			value = 1
		ElseIf (ScaledMouseX() >= x+width)
			value = 2
		EndIf
		Color 0,255,0
		Rect(x,y,width+14,10,True)
	Else
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			Color 0,200,0
			Rect(x,y,width+14,10,False)
		EndIf
	EndIf
	
	If value = 0
		DrawImage(BlinkMeterIMG,x,y-8)
	ElseIf value = 1
		DrawImage(BlinkMeterIMG,x+(width/2)+3,y-8)
	Else
		DrawImage(BlinkMeterIMG,x+width+6,y-8)
	EndIf
	
	Color 170,170,170
	If value = 0
		AAText(x+2,y+10+MenuScale,val1,True)
	ElseIf value = 1
		AAText(x+(width/2)+7,y+10+MenuScale,val2,True)
	Else
		AAText(x+width+12,y+10+MenuScale,val3,True)
	EndIf
	
	Return value
	
End Function

Function Slider4(x%,y%,width%,value%,ID%,val1$,val2$,val3$,val4$)
	
	If MouseDown1 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			OnSliderID = ID
		EndIf
	EndIf
	
	Color 200,200,200
	Rect(x,y,width+14,10,True)
	Rect(x,y-8,4,14,True) ;1
	Rect(x+(width*(1.0/3.0))+(10.0/3.0),y-8,4,14,True) ;2
	Rect(x+(width*(2.0/3.0))+(20.0/3.0),y-8,4,14,True) ;3
	Rect(x+width+10,y-8,4,14,True) ;4
	
	If ID = OnSliderID
		If (ScaledMouseX() <= x+8)
			value = 0
		ElseIf (ScaledMouseX() >= x+width*(1.0/3.0)) And (ScaledMouseX() <= x+width*(1.0/3.0)+8)
			value = 1
		ElseIf (ScaledMouseX() >= x+width*(2.0/3.0)) And (ScaledMouseX() <= x+width*(2.0/3.0)+8)
			value = 2
		ElseIf (ScaledMouseX() >= x+width)
			value = 3
		EndIf
		Color 0,255,0
		Rect(x,y,width+14,10,True)
	Else
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			Color 0,200,0
			Rect(x,y,width+14,10,False)
		EndIf
	EndIf
	
	If value = 0
		DrawImage(BlinkMeterIMG,x,y-8)
	ElseIf value = 1
		DrawImage(BlinkMeterIMG,x+width*(1.0/3.0)+2,y-8)
	ElseIf value = 2
		DrawImage(BlinkMeterIMG,x+width*(2.0/3.0)+4,y-8)
	Else
		DrawImage(BlinkMeterIMG,x+width+6,y-8)
	EndIf
	
	Color 170,170,170
	If value = 0
		AAText(x+2,y+10+MenuScale,val1,True)
	ElseIf value = 1
		AAText(x+width*(1.0/3.0)+2+(10.0/3.0),y+10+MenuScale,val2,True)
	ElseIf value = 2
		AAText(x+width*(2.0/3.0)+2+((10.0/3.0)*2),y+10+MenuScale,val3,True)
	Else
		AAText(x+width+12,y+10+MenuScale,val4,True)
	EndIf
	
	Return value
	
End Function

Function Slider5(x%,y%,width%,value%,ID%,val1$,val2$,val3$,val4$,val5$)
	
	If MouseDown1 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			OnSliderID = ID
		EndIf
	EndIf
	
	Color 200,200,200
	Rect(x,y,width+14,10,True)
	Rect(x,y-8,4,14,True) ;1
	Rect(x+(width/4)+2.5,y-8,4,14,True) ;2
	Rect(x+(width/2)+5,y-8,4,14,True) ;3
	Rect(x+(width*0.75)+7.5,y-8,4,14,True) ;4
	Rect(x+width+10,y-8,4,14,True) ;5
	
	If ID = OnSliderID
		If (ScaledMouseX() <= x+8)
			value = 0
		ElseIf (ScaledMouseX() >= x+width/4) And (ScaledMouseX() <= x+(width/4)+8)
			value = 1
		ElseIf (ScaledMouseX() >= x+width/2) And (ScaledMouseX() <= x+(width/2)+8)
			value = 2
		ElseIf (ScaledMouseX() >= x+width*0.75) And (ScaledMouseX() <= x+(width*0.75)+8)
			value = 3
		ElseIf (ScaledMouseX() >= x+width)
			value = 4
		EndIf
		Color 0,255,0
		Rect(x,y,width+14,10,True)
	Else
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			Color 0,200,0
			Rect(x,y,width+14,10,False)
		EndIf
	EndIf
	
	If value = 0
		DrawImage(BlinkMeterIMG,x,y-8)
	ElseIf value = 1
		DrawImage(BlinkMeterIMG,x+(width/4)+1.5,y-8)
	ElseIf value = 2
		DrawImage(BlinkMeterIMG,x+(width/2)+3,y-8)
	ElseIf value = 3
		DrawImage(BlinkMeterIMG,x+(width*0.75)+4.5,y-8)
	Else
		DrawImage(BlinkMeterIMG,x+width+6,y-8)
	EndIf
	
	Color 170,170,170
	If value = 0
		AAText(x+2,y+10+MenuScale,val1,True)
	ElseIf value = 1
		AAText(x+(width/4)+4.5,y+10+MenuScale,val2,True)
	ElseIf value = 2
		AAText(x+(width/2)+7,y+10+MenuScale,val3,True)
	ElseIf value = 3
		AAText(x+(width*0.75)+9.5,y+10+MenuScale,val4,True)
	Else
		AAText(x+width+12,y+10+MenuScale,val5,True)
	EndIf
	
	Return value
	
End Function

Function Slider7(x%,y%,width%,value%,ID%,val1$,val2$,val3$,val4$,val5$,val6$,val7$)
	
	If MouseDown1 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			OnSliderID = ID
		EndIf
	EndIf
	
	Color 200,200,200
	Rect(x,y,width+14,10,True)
	Rect(x,y-8,4,14,True) ;1
	Rect(x+(width*(1.0/6.0))+(10.0/6.0),y-8,4,14,True) ;2
	Rect(x+(width*(2.0/6.0))+(20.0/6.0),y-8,4,14,True) ;3
	Rect(x+(width*(3.0/6.0))+(30.0/6.0),y-8,4,14,True) ;4
	Rect(x+(width*(4.0/6.0))+(40.0/6.0),y-8,4,14,True) ;5
	Rect(x+(width*(5.0/6.0))+(50.0/6.0),y-8,4,14,True) ;6
	Rect(x+width+10,y-8,4,14,True) ;7
	
	If ID = OnSliderID
		If (ScaledMouseX() <= x+8)
			value = 0
		ElseIf (ScaledMouseX() >= x+(width*(1.0/6.0))) And (ScaledMouseX() <= x+(width*(1.0/6.0))+8)
			value = 1
		ElseIf (ScaledMouseX() >= x+(width*(2.0/6.0))) And (ScaledMouseX() <= x+(width*(2.0/6.0))+8)
			value = 2
		ElseIf (ScaledMouseX() >= x+(width*(3.0/6.0))) And (ScaledMouseX() <= x+(width*(3.0/6.0))+8)
			value = 3
		ElseIf (ScaledMouseX() >= x+(width*(4.0/6.0))) And (ScaledMouseX() <= x+(width*(4.0/6.0))+8)
			value = 4
		ElseIf (ScaledMouseX() >= x+(width*(5.0/6.0))) And (ScaledMouseX() <= x+(width*(5.0/6.0))+8)
			value = 5
		ElseIf (ScaledMouseX() >= x+width)
			value = 6
		EndIf
		Color 0,255,0
		Rect(x,y,width+14,10,True)
	Else
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x+width+14) And (ScaledMouseY() >= y-8) And (ScaledMouseY() <= y+10)
			Color 0,200,0
			Rect(x,y,width+14,10,False)
		EndIf
	EndIf
	
	If value = 0
		DrawImage(BlinkMeterIMG,x,y-8)
	ElseIf value = 1
		DrawImage(BlinkMeterIMG,x+(width*(1.0/6.0))+1,y-8)
	ElseIf value = 2
		DrawImage(BlinkMeterIMG,x+(width*(2.0/6.0))+2,y-8)
	ElseIf value = 3
		DrawImage(BlinkMeterIMG,x+(width*(3.0/6.0))+3,y-8)
	ElseIf value = 4
		DrawImage(BlinkMeterIMG,x+(width*(4.0/6.0))+4,y-8)
	ElseIf value = 5
		DrawImage(BlinkMeterIMG,x+(width*(5.0/6.0))+5,y-8)
	Else
		DrawImage(BlinkMeterIMG,x+width+6,y-8)
	EndIf
	
	Color 170,170,170
	If value = 0
		AAText(x+2,y+10+MenuScale,val1,True)
	ElseIf value = 1
		AAText(x+(width*(1.0/6.0))+2+(10.0/6.0),y+10+MenuScale,val2,True)
	ElseIf value = 2
		AAText(x+(width*(2.0/6.0))+2+((10.0/6.0)*2),y+10+MenuScale,val3,True)
	ElseIf value = 3
		AAText(x+(width*(3.0/6.0))+2+((10.0/6.0)*3),y+10+MenuScale,val4,True)
	ElseIf value = 4
		AAText(x+(width*(4.0/6.0))+2+((10.0/6.0)*4),y+10+MenuScale,val5,True)
	ElseIf value = 5
		AAText(x+(width*(5.0/6.0))+2+((10.0/6.0)*5),y+10+MenuScale,val6,True)
	Else
		AAText(x+width+12,y+10+MenuScale,val7,True)
	EndIf
	
	Return value
	
End Function

Global OnBar%
Global ScrollBarY# = 0.0
Global ScrollMenuHeight# = 0.0

Function DrawScrollBar#(x, y, width, height, barx, bary, barwidth, barheight, bar#, dir = 0)
	;0 = vaakasuuntainen, 1 = pystysuuntainen
	
	Local MouseSpeedX = MouseXSpeed()
	Local MouseSpeedY = MouseYSpeed()
	
	Color(0, 0, 0)
	;Rect(x, y, width, height)
	Button(barx, bary, barwidth, barheight, "")
	
	If dir = 0 Then ;vaakasuunnassa
		If height > 10 Then
			Color 250,250,250
			Rect(barx + barwidth / 2, bary + 5*MenuScale, 2*MenuScale, barheight - 10)
			Rect(barx + barwidth / 2 - 3*MenuScale, bary + 5*MenuScale, 2*MenuScale, barheight - 10)
			Rect(barx + barwidth / 2 + 3*MenuScale, bary + 5*MenuScale, 2*MenuScale, barheight - 10)
		EndIf
	Else ;pystysuunnassa
		If width > 10 Then
			Color 250,250,250
			Rect(barx + 4*MenuScale, bary + barheight / 2, barwidth - 10*MenuScale, 2*MenuScale)
			Rect(barx + 4*MenuScale, bary + barheight / 2 - 3*MenuScale, barwidth - 10*MenuScale, 2*MenuScale)
			Rect(barx + 4*MenuScale, bary + barheight / 2 + 3*MenuScale, barwidth - 10*MenuScale, 2*MenuScale)
		EndIf
	EndIf
	
	If MouseX()>barx And MouseX()<barx+barwidth
		If MouseY()>bary And MouseY()<bary+barheight
			OnBar = True
		Else
			If (Not MouseDown1)
				OnBar = False
			EndIf
		EndIf
	Else
		If (Not MouseDown1)
			OnBar = False
		EndIf
	EndIf
	
	If MouseDown1
		If OnBar
			If dir = 0
				Return Min(Max(bar + MouseSpeedX / Float(width - barwidth), 0), 1)
			Else
				Return Min(Max(bar + MouseSpeedY / Float(height - barheight), 0), 1)
			EndIf
		EndIf
	EndIf
	
	Return bar
	
End Function

Function Button%(x,y,width,height,txt$, disabled%=False)
	Local Pushed = False
	
	Color 50, 50, 50
	If Not disabled Then 
		If MouseX() > x And MouseX() < x+width Then
			If MouseY() > y And MouseY() < y+height Then
				If MouseDown1 Then
					Pushed = True
					Color 50*0.6, 50*0.6, 50*0.6
				Else
					Color Min(50*1.2,255),Min(50*1.2,255),Min(50*1.2,255)
				EndIf
			EndIf
		EndIf
	EndIf
	
	If Pushed Then 
		Rect x,y,width,height
		Color 133,130,125
		Rect x+1*MenuScale,y+1*MenuScale,width-1*MenuScale,height-1*MenuScale,False	
		Color 10,10,10
		Rect x,y,width,height,False
		Color 250,250,250
		Line x,y+height-1*MenuScale,x+width-1*MenuScale,y+height-1*MenuScale
		Line x+width-1*MenuScale,y,x+width-1*MenuScale,y+height-1*MenuScale
	Else
		Rect x,y,width,height
		Color 133,130,125
		Rect x,y,width-1*MenuScale,height-1*MenuScale,False	
		Color 250,250,250
		Rect x,y,width,height,False
		Color 10,10,10
		Line x,y+height-1,x+width-1,y+height-1
		Line x+width-1,y,x+width-1,y+height-1		
	EndIf
	
	Color 255,255,255
	If disabled Then Color 70,70,70
	Text x+width/2, y+height/2-1*MenuScale, txt, True, True
	
	Color 0,0,0
	
	If Pushed And MouseHit1 Then PlaySound_Strict ButtonSFX : Return True
End Function






;~IDEal Editor Parameters:
;~F#33#499#4AB#4B5#4E8#5C3#5D6#5F3#5FA#615#629#64A#662#693#6C4#6EA#710#72D#73E#756
;~F#764#787#79F#7A8#7D9#7ED#821#867#8A9
;~C#Blitz3D