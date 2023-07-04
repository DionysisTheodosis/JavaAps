package wifipasswords;


import java.io.File;
import java.io.FileNotFoundException;
import update.UpdateApp;
import update.FileVersionComparison;
import update.DownloadDialog;
import update.CheckInternetConnection;
import java.io.IOException;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.UIManager;
import update.FileDownloader;
import update.InstallerExecutor;
import update.UninstallPathRetriever;
import resources.utils.ResourceBundleManager;
import settings.SettingsManager;
public class WifiPasswords {
    
    
    private static CheckConnected connected;
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static  String fileUrl = "https://github.com/DionysisTheodosis/JavaAps/raw/main/WifiToolBox/Wi-Fi%20ToolBox/Wi-Fi%20ToolBox-Setup.exe";
    private static  String installerFilePath = TEMP_DIR + "/Wi-Fi ToolBox-Setup.exe";
    public static void main(String[] args) {
       
        // Create a BufferedImage with the same size as the panel
        try {
            // Set Windows look and feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String language = SettingsManager.readSetting("Language");
        if(language==null){
            language="en";
        }
   
        // Update the current locale in the ResourceBundleManager
        ResourceBundleManager.setCurrentLocale(new Locale(language));

        // Retrieve the localized values using the ResourceBundleManager
        String greeting = ResourceBundleManager.getString("QrDialogSSIDTitle");

        
        CheckInternetConnection connection = new CheckInternetConnection();
        if(connection.isConnected()){
        try {
            FileVersionComparison compare = new FileVersionComparison();
            if (!compare.isUpdated()) {
                UpdateApp update = new UpdateApp();
                update.setVisible(true);

                // Wait for the user's choice
                while (!update.isChoiceMade()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        throw new InterruptedException();
                    }
                }
                if (update.skip()) {
                    update.dispose();
                } else if (update.update()) {
                    update.dispose();
                    DownloadDialog download = new DownloadDialog();
                    download.setVisible(true);
                    // Download the installer file 
                    FileDownloader.downloadFile(fileUrl, installerFilePath,download);
                    String uninstallPath = UninstallPathRetriever.getUninstallPath();
                    InstallerExecutor.executeInstaller(installerFilePath,uninstallPath);
                    System.exit(0);
                 
                }
            }
       
        } catch (IOException | IllegalArgumentException|InterruptedException ex) {
           // Error from reading file or interupting thread
        }
        }
        // If the update was skipped or completed, launch the main JFrame
        launchMainFrame();
    }
    private static void launchMainFrame() {
        connected = new CheckConnected();
        NetWorkProfiles net = new NetWorkProfiles(connected.getConnectedProfile());
        MyJFrame myframe = new MyJFrame(net);
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.setVisible(true);
    }
}