#define MyAppName "Wi-Fi ToolBox"
#define MyAppVersion "1.5"
#define MyAppExeName "Wi-Fi ToolBox.exe"
#define MyOutputDir "Wi-Fi ToolBox"
#define MyIconPath "wifiPass.ico"
#define JreSourceDir "jre" ; Assuming the JRE files are in the "jre" directory

[Setup]
AppName={#MyAppName}
AppVersion={#MyAppVersion}
DefaultDirName={pf}\{#MyOutputDir}
DefaultGroupName={#MyAppName}
OutputDir={#MyOutputDir}
OutputBaseFilename="Wi-Fi ToolBox-Setup"
Compression=lzma
SolidCompression=yes
AppPublisher=Dionysis Theodosis
AppPublisherURL=https://github.com/DionysisTheodosis/JavaAps/tree/main/WifiToolBox
SetupIconFile=wifiPass.ico

[Code]
var
  CustomDirPage: TWizardPage;
  CustomDirEdit: TEdit;
  LanguagePage: TWizardPage;
  LanguageCombo: TComboBox;

procedure InitializeWizard;
begin
  CustomDirPage := CreateCustomPage(wpSelectDir, 'Select Installation Directory', 'Choose the installation directory for Wi-Fi ToolBox:');
  CustomDirEdit := TEdit.Create(WizardForm);
  CustomDirEdit.Parent := CustomDirPage.Surface;
  CustomDirEdit.Left := 0;
  CustomDirEdit.Top := CustomDirPage.SurfaceHeight - ScaleY(60);
  CustomDirEdit.Width := CustomDirPage.SurfaceWidth;
  CustomDirEdit.Text := ExpandConstant('{pf}\{#MyOutputDir}');
end;


[Files]
Source: "{#MyAppExeName}"; DestDir: "{app}"
Source: "{#JreSourceDir}\*"; DestDir: "{app}\{#JreSourceDir}"; Flags: recursesubdirs
Source: "wifiPass.png"; DestDir: "{app}"; Flags: ignoreversion
Source: "lib\*.jar"; DestDir: "{app}\lib"; Flags: recursesubdirs
Source: "help_el.html"; DestDir: "{userappdata}\Wi-Fi ToolBox"; Flags: ignoreversion;
Source: "help_en.html"; DestDir: "{userappdata}\Wi-Fi ToolBox" ;  Flags: ignoreversion;
Source: "support_el.html"; DestDir: "{userappdata}\Wi-Fi ToolBox";    Flags: ignoreversion;
Source: "support_en.html"; DestDir: "{userappdata}\Wi-Fi ToolBox" ;   Flags: ignoreversion;
[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; WorkingDir: "{app}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; WorkingDir: "{app}"


[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{#MyAppName}"; Flags: nowait postinstall skipifsilent
