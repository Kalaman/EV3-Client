package Client;

import javax.swing.*;
import java.awt.*;

public class JConsolePanel extends JPanel{
    public static JTextArea consoleTextArea;

    public JConsolePanel() {
        setPreferredSize(new Dimension(JConstants.PANEL_CONSOLE_SIZE_X,JConstants.WINDOW_SIZE_Y));
        setLayout(new BorderLayout());

        consoleTextArea = new JTextArea(32,1);
        consoleTextArea.setSize(new Dimension(JConstants.PANEL_CONSOLE_SIZE_X,JConstants.WINDOW_SIZE_Y));
        consoleTextArea.setEditable(false);

        add(consoleTextArea);

        writeToConsole("EV3-Client started ...");
    }

    public static void writeToConsole (String message){
        if (consoleTextArea.getText().length() > 0)
            consoleTextArea.setText(consoleTextArea.getText() + "\n-> " + message);
        else
            consoleTextArea.setText("-> " + message);
    }

}
