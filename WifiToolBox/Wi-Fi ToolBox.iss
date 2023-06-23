#define MyAppName "Wi-Fi ToolBox"
#define MyAppVersion "1.4"
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
SetupIconFile= wifiPass.ico

[Code]
var
  CustomDirPage: TWizardPage;
  CustomDirEdit: TEdit;

procedure InitializeWizard;
begin
  CustomDirPage := CreateCustomPage(wpSelectDir, 'Select Installation Directory', 'Choose the installation directory for Wi-Fi ToolBox:');
  CustomDirEdit := TEdit.Create(WizardForm);
  CustomDirEdit.Parent := CustomDirPage.Surface;
  CustomDirEdit.Left := 0;
  CustomDirEdit.Top := CustomDirPage.SurfaceHeight - ScaleY(60);
  CustomDirEdit.Width := CustomDirPage.SurfaceWidth;
  CustomDirEdit.Text := ExpandConstant('{pf32}\{#MyOutputDir}');
end;

function NextButtonClick(CurPageID: Integer): Boolean;
begin
  Result := True;
  if CurPageID = CustomDirPage.ID then
  begin
    if CustomDirEdit.Text = '' then
    begin
      MsgBox('Please choose an installation directory.', mbError, MB_OK);
      Result := False;
    end
    else
    begin
      // Set the installation directory based on user input
      WizardForm.DirEdit.Text := CustomDirEdit.Text;
    end;
  end;
end;

[Files]
Source: "{#MyAppExeName}"; DestDir: "{app}"
Source: "{#JreSourceDir}\*"; DestDir: "{app}\{#JreSourceDir}"; Flags: recursesubdirs
Source: "wifiPass.png"; DestDir: "{app}"; Flags: ignoreversion
Source: "lib\*.jar"; DestDir: "{app}\lib"; Flags: recursesubdirs
[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; WorkingDir: "{app}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; WorkingDir: "{app}"

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{#MyAppName}"; Flags: nowait postinstall skipifsilent

