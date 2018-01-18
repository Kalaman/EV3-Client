package Client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kalaman on 12.01.18.
 */
public class JMainFrame extends JFrame{

    JConsolePanel jConsolePanel;
    JRobotPanel jRobotPanel;

    public JMainFrame () {
        super("EV3-Client");

        jConsolePanel = new JConsolePanel();
        jRobotPanel = new JRobotPanel();

        this.setSize(new Dimension(JConstants.WINDOW_SIZE_X,JConstants.WINDOW_SIZE_Y));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.add(jRobotPanel);
        this.add(jConsolePanel,BorderLayout.WEST);

        this.setVisible(true);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }


}
