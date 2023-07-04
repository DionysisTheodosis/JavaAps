/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package settings;

import java.io.*;
import java.util.Properties;

public class SettingsManager {
    private static final String SETTINGS_FILE_PATH = System.getenv("APPDATA") + File.separator + "Wi-Fi ToolBox" + File.separator + "settings.ini";

    public static String readSetting(String key) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(SETTINGS_FILE_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    public static void writeSetting(String key, String value) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(SETTINGS_FILE_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            // Ignore if the file doesn't exist yet
        }
        properties.setProperty(key, value);
        try (OutputStream outputStream = new FileOutputStream(SETTINGS_FILE_PATH)) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}