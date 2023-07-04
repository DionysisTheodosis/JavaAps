/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package update;

import java.io.IOException;

/**
 *
 * @author dionysis
 */
public class InstallerExecutor {
     public static void executeInstaller(String installerFilePath, String uninstallPath) {
        ProcessBuilder processBuilder1 = new ProcessBuilder(uninstallPath);
        try {
            Process uninstallProcess = processBuilder1.start();
            int exitCode = uninstallProcess.waitFor(); // Wait for the uninstall process to finish
            if (exitCode == 0) {
                ProcessBuilder installerProcessBuilder = new ProcessBuilder(installerFilePath);
                try {
                    Process installerProcess = installerProcessBuilder.start();
                    System.exit(0);
                } 
                catch (IOException ex) {
                    System.out.println("Failed to execute the installer.");
       
                }
            }
            else {
                // The uninstall process failed
                System.out.println("Failed to uninstall the previous version.");
            }
        } catch (IOException ex) {
            System.out.println("Failed to execute the uninstaller.");
        } catch (InterruptedException ex) {
            System.out.println("Interrupted while waiting for the uninstall process.");

        }
    }
}

