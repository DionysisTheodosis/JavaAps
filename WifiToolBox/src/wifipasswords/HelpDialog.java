package wifipasswords;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HelpDialog extends JDialog {
    private JTextPane textPane;
    private JScrollPane scrollPane;

    public HelpDialog(Frame owner, String title) {
        super(owner, title, true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        textPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (Exception ex) {
                            //ex.printStackTrace();
                        }
                    }
                }
            }
        });

        scrollPane = new JScrollPane(textPane);
        //this.add(scrollPane);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setSize(650, 500);
    }

   public void loadHelpDocument(String filePath) {
    try {
        File htmlFile = new File(filePath);
        byte[] data;
        try (FileInputStream fis = new FileInputStream(htmlFile)) {
            data = new byte[(int) htmlFile.length()];
            fis.read(data);
        }

        String htmlContent = new String(data, StandardCharsets.UTF_8);
        textPane.setText(htmlContent);
        textPane.setCaretPosition(0);  
    } catch (IOException e) {
       // e.printStackTrace();
    }
}



  
}