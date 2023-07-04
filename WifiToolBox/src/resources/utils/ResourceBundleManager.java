/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleManager {
    private static final String BASE_NAME = "resources.resourcesFiles.resource"; // Base name of the properties files
    private static Locale currentLocale = Locale.getDefault();
    private static List<PropertyChangeListener> listeners = new ArrayList<>();

    private ResourceBundleManager() {
        // Private constructor to prevent instantiation
    }

    public static String getString(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, currentLocale);
        return bundle.getString(key);
    }

//    public static void setCurrentLocale(Locale locale) {
//        currentLocale = locale;
//    }
    public static Locale getLocale() {
        return currentLocale;
    }
       public static void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    private static void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        PropertyChangeEvent event = new PropertyChangeEvent(ResourceBundleManager.class, propertyName, oldValue, newValue);
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(event);
        }
    }
    public static void setCurrentLocale(Locale locale) {
        Locale oldLocale = currentLocale;
        currentLocale = locale;
        firePropertyChange("locale", oldLocale, currentLocale);
}








}