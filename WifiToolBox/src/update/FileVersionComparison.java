/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package update;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileVersionComparison {
    private boolean versionsMatch;
    public FileVersionComparison() throws IOException {
        String exeFilePath = "./Wi-Fi ToolBox.exe";
        String tempFolderPath = System.getProperty("java.io.tmpdir");
        String tempFilePath = tempFolderPath + File.separator + "config.xml";
        String configXmlUrl = "https://raw.githubusercontent.com/DionysisTheodosis/JavaAps/main/WifiToolBox/config.xml";

        // Download the config.xml file to the temporary folder
        downloadFile(configXmlUrl, tempFilePath);

        // Read the content of the config.xml file
        String configXmlContent = readTextFile(tempFilePath);

        // Extract the file version from config.xml
        String extractedFileVersion = extractFileVersion(configXmlContent);

        // Retrieve the file version of the EXE
        String fileVersion = FileVersionExtractor.getFileVersion(exeFilePath);

        // Compare the file versions and print the result
        versionsMatch = compareFileVersions(fileVersion, extractedFileVersion);
        
        deleteFile(tempFilePath);
    }

    private void downloadFile(String fileUrl, String destinationFilePath) throws FileNotFoundException {
       
       
        try{
            URL url = new URL(fileUrl);
            InputStream in = url.openStream();
            Files.copy(in, Path.of(destinationFilePath), StandardCopyOption.REPLACE_EXISTING);
        }
        catch(IOException ex){
           //return;
           throw new FileNotFoundException();
           
        }
    }

    private  String readTextFile(String filePath) throws IOException {
       
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        return contentBuilder.toString();
    }

    private  String extractFileVersion(String xmlContent) {
     
        String regex = "<fileVersion>(.+?)</fileVersion>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(xmlContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private  boolean compareFileVersions(String version1, String version2) {
        
        if (version1 != null && version2 != null) {
            return version1.equals(version2);
        }
        return false;
    }

    private  void deleteFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        Files.deleteIfExists(path);
        
    }
    
    public boolean isUpdated(){
        return versionsMatch;
    }
}