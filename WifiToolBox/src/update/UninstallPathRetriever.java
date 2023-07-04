/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UninstallPathRetriever {
    public static String getUninstallPath() throws IOException, InterruptedException {
        // Construct the command
        String[] cmd = {
            "cmd.exe",
            "/c",
            "for /f \"tokens=2*\" %A in ('reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\" /s /f \"Wi-Fi ToolBox\" ^| findstr /i \"UninstallString\" ^| findstr /v \"QuietUninstallString\"') do @echo %B"
        };

        // Start the process
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();

        // Read the output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.lineSeparator());
        }
        // Wait for the process to complete
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return output.toString().trim();
        } else {
            throw new RuntimeException("Failed to retrieve the uninstall path.");
        }
    }
}
