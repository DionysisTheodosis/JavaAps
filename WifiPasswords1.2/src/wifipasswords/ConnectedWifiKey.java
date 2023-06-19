/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wifipasswords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author dionysis
 */
public class ConnectedWifiKey {
    private WifiProfile connected;
    public ConnectedWifiKey(WifiProfile connected){
        this.connected=connected;
        try {
            Process process = Runtime.getRuntime().exec("netsh wlan show profiles " + connected.getName() + " key=clear");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                
            String line2;
            while ((line2 = reader.readLine()) != null) {
                if (line2.contains("Key Content")) {
                    String[] parts = line2.split(":");
                    String password = parts[1].trim();
                    this.connected.setPassword(password);
                    this.connected.setConnected(true);
                    break;
                }
                
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
                   
    } 
    public WifiProfile getConnectedProfile(){
        return connected;
    }
}
