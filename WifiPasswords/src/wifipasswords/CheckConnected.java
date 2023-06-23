/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wifipasswords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;

/**
 *
 * @author dionysis
 */
public class CheckConnected {
    private WifiProfile connected;
    public CheckConnected(){
        try {
                Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "netsh", "wlan", "show", "interfaces", "|", "findstr", "SSID"});
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("SSID")) {
                        String ssid = line.split(":")[1].trim();
                        System.out.println("Connected Wi-Fi SSID: " + ssid);
                        connected= new WifiProfile(ssid);
                        break;
                    }
                }
                
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    System.out.println("Failed to retrieve connected Wi-Fi SSID.");
                }
            } 
        catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }
      
    }
    
    public WifiProfile getConnectedProfile(){
        return connected;
    }
} 
