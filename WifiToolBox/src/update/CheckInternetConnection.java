/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package update;

import java.io.IOException;
import java.net.InetAddress;

public class CheckInternetConnection {
   private  boolean isConnected;
   public  CheckInternetConnection(){
       checkInternetConnectivity();
   }
   private void checkInternetConnectivity() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            isConnected= address.isReachable(1000); // Timeout in milliseconds
        } catch (IOException e) {
           isConnected =false;
        }
    }
   public  boolean isConnected(){
       
       return isConnected;
   }
}
