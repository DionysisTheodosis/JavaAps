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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dionysis
 */
public class NetWorkProfiles {
    private ArrayList<WifiProfile> profileLists;
    private List<Thread> threads;
    private WifiProfile connected;

    public NetWorkProfiles(WifiProfile connected) {
        this.connected = connected;
        profileLists = new ArrayList<>();
        threads = new ArrayList<>();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("netsh", "wlan", "show", "profiles");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("All User Profile")) {
                    String[] parts = line.split(":");
                    String profileName = parts[1].trim();

                    Thread thread = new Thread(() -> {
                        try {
                            ProcessBuilder profileProcessBuilder = new ProcessBuilder("netsh", "wlan", "show", "profile", profileName, "key=clear");
                            Process profileProcess = profileProcessBuilder.start();
                            BufferedReader profileReader = new BufferedReader(new InputStreamReader(profileProcess.getInputStream()));

                            String profileLine;
                            String password = null;
                            String securityType = null;
                            boolean isConnected = false;
                            while ((profileLine = profileReader.readLine()) != null) {
                                if (profileLine.contains("Key Content")) {
                                    String[] profileParts = profileLine.split(":");
                                    password = profileParts[1].trim();
                                } else if (profileLine.contains("Authentication")) {
                                    String[] profileParts = profileLine.split(":");
                                    securityType = profileParts[1].trim();
                                }
                            }

                            synchronized (profileLists) {
                                WifiProfile wifiProfile = new WifiProfile(profileName, password, securityType, isConnected);
                                if (connected != null && connected.getName().equals(profileName)) {
                                    wifiProfile.setConnected(true);
                                    NetWorkProfiles.this.connected = wifiProfile;
                                }
                                profileLists.add(wifiProfile);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    threads.add(thread);
                }
            }

            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            if (profileLists.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Δεν βρέθηκαν αποθηκευμένα wifi", "", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            removeProfilesWithoutPassword();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void removeProfilesWithoutPassword() {
        Iterator<WifiProfile> iterator = profileLists.iterator();
        while (iterator.hasNext()) {
            WifiProfile profile = iterator.next();
            if (profile.getPassword() == null) {
                iterator.remove();
            }
        }
    }

    public ArrayList<WifiProfile> getWifiNetworkList() {
        return profileLists;
    }

    public void connection() {
        removeProfilesWithoutPassword();
    }

    public WifiProfile getConnected() {
        return connected;
    }
}
