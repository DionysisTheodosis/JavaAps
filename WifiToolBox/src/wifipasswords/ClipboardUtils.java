/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wifipasswords;

/**
 *
 * @author dionysis
 */

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtils {
    public static void copyTextToClipboard(String text) {
        // Get the system clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Create a StringSelection object with the text to be copied
        StringSelection selection = new StringSelection(text);

        // Set the contents of the clipboard to the StringSelection
        clipboard.setContents(selection, null);
    }
}