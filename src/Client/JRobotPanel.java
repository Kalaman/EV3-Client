package Client;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.URI;

public class JRobotPanel extends JPanel{
    Graphics2D graphics2D;

    public JRobotPanel() {
        setPreferredSize(new Dimension(JConstants.PANEL_ROBOT_SIZE_X,JConstants.WINDOW_SIZE_Y));
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphics2D = (Graphics2D)g;

        SVGUniverse svgUniverse = new SVGUniverse();
        try {
            URI svgURI = JMainFrame.class.getResource("/files/houses.svg").toURI();
            svgUniverse.loadSVG(svgURI.toURL(),false);
            SVGDiagram diagram = svgUniverse.getDiagram(svgURI);
            diagram.setIgnoringClipHeuristic(true);
            AffineTransform at = new AffineTransform();
            at.setToScale(getWidth()/diagram.getWidth(), getWidth()/diagram.getWidth());
            graphics2D.transform(at);
            diagram.render(graphics2D);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


	public void drawNode (Particle node, Color color,Graphics2D graphics2D) {
        Color oldColor = graphics2D.getColor();

        graphics2D.setColor(color);
        graphics2D.drawOval(node.getPositionX() - (JConstants.NODE_WIDTH/2),node.getPositionY() - (JConstants.NODE_HEIGHT/2),JConstants.NODE_WIDTH,JConstants.NODE_HEIGHT);

        int endX = node.getPositionX() + (int)(Math.cos(Math.toRadians(node.getDegree())) * 10);
        int endY = node.getPositionY() + (int)(Math.sin(Math.toRadians(node.getDegree())) * 10);

        graphics2D.drawLine(node.getPositionX() ,
                node.getPositionY() ,endX ,endY);

        graphics2D.setColor(oldColor);
    }
}
