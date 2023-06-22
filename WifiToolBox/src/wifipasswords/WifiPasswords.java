
package wifipasswords;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class WifiPasswords{
    private static  CheckConnected connected;
    public static void main(String[] args) {
        ArrayList<WifiProfile> profileLists= new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        
        connected = new CheckConnected();

        NetWorkProfiles net = new NetWorkProfiles(connected.getConnectedProfile());
        MyJFrame myframe = new MyJFrame(net);
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.setVisible(true);
    }
}




