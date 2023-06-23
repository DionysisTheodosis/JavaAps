/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wifipasswords;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import java.io.File;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
/**
 *
 * @author dionysis
 */
public class WifiQr {
    private final String wifiContent;
    private final File QRFile;
    
    public WifiQr(WifiProfile wifiP) throws FileNotFoundException{
        wifiContent = "WIFI:S:" + wifiP.getName() + ";T:" + wifiP.getSecurityType()+ ";P:" + wifiP.getPassword() + ";;";
         // Generate the QR code as a BitMatrix
      // Generate the QR code as a byte array
        ByteArrayOutputStream out = QRCode.from(wifiContent).to(ImageType.PNG).stream();
        String file =wifiP.getName()+"_qrcode.png";
        // Save the QR code image as a PNG file
        String directoryPath = "./Qr-Code-Images";
        File directory = new File(directoryPath);
        directory.mkdirs(); // Create the directory if it doesn't exist
        QRFile = new File(directory, file);
        try (FileOutputStream fos = new FileOutputStream(QRFile)) {
            fos.write(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getWifiContent(){
        return wifiContent;
    }
    public ImageIcon getImageFile(){
        return new ImageIcon(QRFile.getAbsolutePath());
    }
}
