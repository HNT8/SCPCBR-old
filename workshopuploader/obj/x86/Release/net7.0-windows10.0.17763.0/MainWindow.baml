   M S B A M L   `   `   `  ���� K  Gworkshopuploader, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null3clr-namespace:workshopuploaderworkshopuploader  O Kmscorlib, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e0891&clr-namespace:System;assembly=mscorlibSystem !    workshopuploader.MainWindow   W SPresentationCore, Version=7.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=7.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=7.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=7.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35^ ZWindowsFormsIntegration, Version=7.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35H 9http://schemas.microsoft.com/winfx/2006/xaml/presentation      R NSystem.Xaml, Version=7.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     @d2http://schemas.microsoft.com/expression/blend/2008     Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     0localclr-namespace:workshopuploader     Z VFirstFloor.ModernUI, Version=1.0.9.0, Culture=neutral, PublicKeyToken=bc9b0c37bf06c6a90mui&http://firstfloorsoftware.com/ModernUI  0sys&clr-namespace:System;assembly=mscorlib  D	 @ModernWpf, Version=0.9.6.0, Culture=neutral, PublicKeyToken=nullM
 IModernWpf.Controls, Version=0.9.6.0, Culture=neutral, PublicKeyToken=null,ui!http://schemas.modernwpf.com/2019 	 
 -     � Title$  SCP:CBR Workshop Uploader��$
��auto��$
��auto��$	��500��$	��500�� � Icon$6 0/workshopuploader;component/Icons/remastered.ico��0 	 *ModernWpf.Controls.Primitives.WindowHelper   UseModernWindowStyle$
 True��! 	 ModernWpf.Controls.TitleBar   IsIconVisible$
 True��.��]� .+��� -   $��
topWarning�� �� TextAlignment$ Center=�$?��9YOU DO NOT CURRENTLY HAVE A UPLOAD CONFIGURATION SELECTED��$	��Top=�$
{�True��$y�Visible=����#FF3333
�������$
��Bold"�o� $	��Top=�.@�m� $��	_Uploader��.@�m� -   $
��_New�� m� Icon �� $/��)/workshopuploader;component/Icons/new.png��m� -   $��_Open�� �� $0��*/workshopuploader;component/Icons/open.png��m� -   $��_Save�� �� $0��*/workshopuploader;component/Icons/save.png���� m� -   $��_Exit�� �� $0��*/workshopuploader;component/Icons/exit.png��m� $��_Help��.@�m� -   $��_Modding Documentation�� �� $0��*/workshopuploader;component/Icons/docs.png��m� -   $��_Application Information�� �� $0��*/workshopuploader;component/Icons/info.png���� m� -	   $��_Discord�� �� $3��-/workshopuploader;component/Icons/discord.png��m� -
   $��_Steam�� �� $1��+/workshopuploader;component/Icons/steam.png���� -   $
��tabs��$
��Auto��$��Visible=�${�False��.���� $��5,5,5,5q�.+��  � ColumnDefinitions �� $��1* ��� $��5* � � RowDefinitions �� .+��� -   $"��uploaderTab_modFolder_Upload��$��Choose File��$��0��$��Stretch=�$��Stretch=��� -   $��uploaderTab_modFolder��${�False��$��4,0,0,0q�$��1���� $
��Name���� -   $��uploaderTab_modName���� $y�Hidden=��� $��Delimiter / ID���� -   $��uploaderTab_modDelimiter���� $y�Hidden=��� $��Version���� -   $��uploaderTab_modVersion���� $y�Hidden=��� $��Description���� -   $��descriptionTabs��$	��300��.@��� $��Editor��.���� -   $ ��uploaderTab_modDescription�� �� AcceptsReturn$
 True��$	 �� HorizontalScrollBarVisibility$
	 Auto=�"
 �� VerticalScrollBarVisibility$

 Auto=� �� TextAlignment$
 Left=� �� TextWrapping$
 Wrap=��� $��Preview��.���� .��6  0FirstFloor.ModernUI.Windows.Controls.BBCodeBlock  -   $��	bbPreview��${�False���� $y�Hidden=��� $
��Tags����  �� Orientation$ 
Horizontal=�$��1,1,1,1q�.+��� -   $��uploaderTab_modTagModel��$��Model  ���� -   $��uploaderTab_modTagTexture��$��	Texture  ���� -   $��uploaderTab_modTagAudio��$��Audio  ���� -   $��uploaderTab_modTagMap��$��Map  ���� -   $ ��uploaderTab_modTagLanguage��$��
Language  ���� -   $��uploaderTab_modTagData��$��Data  ���� $y�Hidden=��� $��
Visibility���� -   $��uploaderTab_modVisibility�� �� 
IsReadOnly$
 True�� �� 
IsEditable$ False��$��Stretch=�.@��� .��Public�� .��Friends Only�� .��	Private�� $��Workshop Icon���  �� $��1* ��� $��5* � �� .+��� -   $%��uploaderTab_workshopIcon_Upload��$��Choose File��$��0��$��Stretch=�$��Stretch=��� -   $��uploaderTab_workshopIcon��${�False��$��4,0,0,0q�$��1���� $y�Hidden=��� $��	Changelog���� -   $��uploaderTab_modChangelog��$
 True��$
	 Auto=�$

 Auto=�$
 Left=�$	��300���� $y�Hidden=��� �� $y�Hidden=�� -   $��uploadProgress�� � Value$ 0Q��� $y�Hidden=��  �� $��4* ��� $��1* �.+��� -   $��uploadSubmit��$��Upload Workshop Item��$
��Auto��$��Stretch=�$��0��$��3q�& 
  ModernWpf.Controls.FlyoutService   Flyout  
 ModernWpf.Controls.Flyout  -    uploadConfirmFlyout��   Content. �� .+��� $0��*Are you sure you want to upload this item?���� $��WrapWithOverflow=�$	��400��$
��Left=�$v��pIf you set a workshop description manually on the workshop page, it will be reset to the one you provided above.��$��12(��� -!   $��uploadConfirm��$��Confirm Upload��$��0,5,0,0q�$��Stretch=��� -"   $��uploadReset��$��Reset Workshop Item��$
��Auto��$��Stretch=�$��1��$��3q�   -#   resetConfirmFlyout��. �� .+��� $/��)Are you sure you want to reset this item?���� $��WrapWithOverflow=�$	��400��$
��Left=�$����Resetting this item will prevent you from uploading this mod to your previous workshop page indefinitely. You should only do this if you deleted the workshop page of the mod or are not going to update the old workshop page any further.��$��12(��� -$   $��resetConfirm��$��Confirm Reset��$��0,5,0,0q�$��Stretch=�