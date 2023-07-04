/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package update;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import javax.swing.SwingUtilities;

/**
 *
 * @author dionysis
 */

public class FileDownloader {
      private static DownloadDialog dialog;
      public static void downloadFile(String url, String filePath,DownloadDialog dialog) {
       FileDownloader.dialog=dialog;
       try {
        URL fileUrl = new URL(url);
        InputStream inputStream = fileUrl.openStream();
        fileUrl = new URL(url);
        Path targetPath = Path.of(filePath);
        
        long fileSize = fileUrl.openConnection().getContentLengthLong();
        long downloadedSize = 0;
        
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                downloadedSize += bytesRead;
                
                // Calculate progress percentage
                int progress = (int) ((downloadedSize * 100) / fileSize);
                
                // Update progress bar
                SwingUtilities.invokeLater(() -> {
                    dialog.updateProgress().setValue(progress);
                    dialog.updateProgress().setString(progress + "%"); 
                });
            }
               // File download completed, dispose of the dialog
            SwingUtilities.invokeLater(() -> {
                dialog.dispose();
            });
        }
        } catch (IOException ex) {
        System.out.println("Could not download file");//Logger.getLogger(WifiPasswords.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
