/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wifipasswords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author dionysis
 */
public class NetWorkProfiles {
    private  ArrayList<WifiProfile> profileLists;
    private  List<Thread> threads;
    private  WifiProfile connected;
    public NetWorkProfiles(WifiProfile connected){
        if(connected!=null){
            this.connected=connected;
        }
     
        profileLists= new ArrayList<>();
        threads = new ArrayList<>();
        try {
            Process process2 = Runtime.getRuntime().exec("netsh wlan show profiles");
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));            
            String line;
            while (((line = reader2.readLine()) != null)) {
                if (line.contains("All User Profile")) {
                    String[] parts = line.split(":");
                    String names = parts[1].trim();
                    profileLists.add(new WifiProfile(names));
                }
            }
            for(int i=0;i<profileLists.size();i++){
                final int index = i;
                String profileName =profileLists.get(i).getName();

                Thread thread1 = new Thread(() -> {
                    try {
                        Process process = Runtime.getRuntime().exec("netsh wlan show profiles " + profileName + " key=clear");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                
                        String line2;
                        while ((line2 = reader.readLine()) != null) {
                            if (line2.contains("Key Content")) {
                                String[] parts = line2.split(":");
                                String password = parts[1].trim();
                                //System.out.println("Wi-Fi password for profile " + profileName + ": " + password);
                                profileLists.get(index).setPassword(password);
                                if(connected!=null){       
                                    if(profileLists.get(index).getName().equals(connected.getName())){
                                        profileLists.get(index).setConnected(true);
                                   
                                        this.connected= profileLists.get(index);
                                    }
                                    
                                }
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Start the thread
                
                thread1.start();
                threads.add(thread1);
                
            }
             for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }        
        } catch (IOException e) {
            e.printStackTrace();
        } 
        if(profileLists.isEmpty()){
            JOptionPane.showMessageDialog(null, "Δεν βρέθηκαν αποθηκευμένα wifi","",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        Iterator<WifiProfile> iterator = profileLists.iterator();
        while (iterator.hasNext()) {
            WifiProfile profile = iterator.next();
            if (profile.getPassword() == null) {
                iterator.remove();
            }
        }
        
        
    } 
    public ArrayList<WifiProfile> getWifiNetworkList(){
        return profileLists;
    }
    public void addConnection(){
        
    }
    public void connection(){
        Iterator<WifiProfile> iterator = profileLists.iterator();
        while (iterator.hasNext()) {
            WifiProfile profile = iterator.next();
            if (profile.getPassword() == null) {
                iterator.remove();
            }
        }
    }
    public WifiProfile getConnected(){
        return connected;
    }
}
