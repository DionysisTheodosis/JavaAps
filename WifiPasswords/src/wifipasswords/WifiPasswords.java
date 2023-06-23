
package wifipasswords;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;


public class WifiPasswords{
    private static  CheckConnected connected;
    public static void main(String[] args) {
        ArrayList<WifiProfile> profileLists= new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        
        //Thread ssidThread = new Thread(() -> {
            connected = new CheckConnected();
        //});
        // Start the SSID thread
       // ssidThread.start();
       // threads.add(ssidThread);
//        try {
//            Process process2 = Runtime.getRuntime().exec("netsh wlan show profiles");
//            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));            
//            String line;
//            while (((line = reader2.readLine()) != null)) {
//                if (line.contains("All User Profile")) {
//                    String[] parts = line.split(":");
//                    String names = parts[1].trim();
//                    profileLists.add(new WifiProfile(names));
//                }
//            }
//            for(int i=0;i<profileLists.size();i++){
//                final int index = i;
//                String profileName =profileLists.get(i).getName();
//
//                Thread thread1 = new Thread(() -> {
//                    try {
//                        Process process = Runtime.getRuntime().exec("netsh wlan show profiles " + profileName + " key=clear");
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                
//                        String line2;
//                        while ((line2 = reader.readLine()) != null) {
//                            if (line2.contains("Key Content")) {
//                                String[] parts = line2.split(":");
//                                String password = parts[1].trim();
//                                //System.out.println("Wi-Fi password for profile " + profileName + ": " + password);
//                                profileLists.get(index).setPassword(password);
//                                if(profileLists.get(index).getName().equals(connected.getConnectedProfile().getName())){
//                                     profileLists.get(index).setConnected(true);
//                                }
//                                break;
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//                // Start the thread
//                
//                thread1.start();
//                threads.add(thread1);
//                
//            }
//             for (Thread thread : threads) {
//                try {
//                    thread.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }        
//        } catch (IOException e) {
//            e.printStackTrace();
//        } 
//        if(profileLists.isEmpty()){
//            JOptionPane.showMessageDialog(null, "Δεν βρέθηκαν αποθηκευμένα wifi","",JOptionPane.INFORMATION_MESSAGE);
//            System.exit(0);
//        }
//        Iterator<WifiProfile> iterator = profileLists.iterator();
//        while (iterator.hasNext()) {
//            WifiProfile profile = iterator.next();
//            if (profile.getPassword() == null) {
//                iterator.remove();
//            }
//        }
        NetWorkProfiles net = new NetWorkProfiles(connected.getConnectedProfile());
        MyJFrame myframe = new MyJFrame(net);
        myframe.setVisible(true);
    }
}




