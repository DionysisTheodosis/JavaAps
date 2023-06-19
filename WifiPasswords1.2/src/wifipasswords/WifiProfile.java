/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wifipasswords;

/**
 *
 * @author dionysis
 */
public class WifiProfile {
    private String name;
    private String password;
    private  String securityType;
    private boolean connected;
    public WifiProfile(String name){
        this(name,null);
    }
    
    public WifiProfile(String name,String password){
        this(name,password,false);
    }
    public WifiProfile(String name,String password,boolean connected){
        this.name=name;
        this.password=password;
        this.securityType="WPA2";
        this.connected=connected;
    }
    
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    public String getSecurityType() {
        return securityType;
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public String toString() {
        return "WifiProfile{" + "name=" + name + ", password=" + password + ", securityType=" + securityType + ", connected=" + connected + '}';
    }

    
}
