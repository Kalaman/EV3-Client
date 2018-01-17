package src;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.URI;

public class JRobotPanel extends JPanel{

    public JRobotPanel() {
        setPreferredSize(new Dimension(JConstants.PANEL_ROBOT_SIZE_X,JConstants.WINDOW_SIZE_Y));
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D)g;

        SVGUniverse svgUniverse = new SVGUniverse();
        try {
            URI svgURI = JMainFrame.class.getResource("houses.svg").toURI();
            svgUniverse.loadSVG(svgURI.toURL(),false);
            SVGDiagram diagram = svgUniverse.getDiagram(svgURI);
            diagram.setIgnoringClipHeuristic(true);
            AffineTransform at = new AffineTransform();
            at.setToScale(getWidth()/diagram.getWidth(), getWidth()/diagram.getWidth());
            graphics2D.transform(at);
            diagram.getViewRect().getBounds();
            diagram.render(graphics2D);
        }catch (Exception e){
            e.printStackTrace();
        }

        drawNode(new Node(310,50,0),Color.RED,graphics2D);
        drawNode(new Node(200,70,99),Color.GREEN,graphics2D);
        drawNode(new Node(31,60,30),Color.BLUE,graphics2D);
        drawNode(new Node(180,80,275),Color.MAGENTA,graphics2D);
    }

    public void drawNode (Node node, Color color,Graphics2D graphics2D) {
        Color oldColor = graphics2D.getColor();

        graphics2D.setColor(color);
        graphics2D.drawOval(node.getPositionX(),node.getPositionY(),JConstants.NODE_WIDTH,JConstants.NODE_HEIGHT);

        int endX = node.getPositionX() + (JConstants.NODE_WIDTH / 2) + (int)(Math.cos(Math.toRadians(node.getDegree())) * 10);
        int endY = node.getPositionY() + (JConstants.NODE_HEIGHT / 2) + (int)(Math.sin(Math.toRadians(node.getDegree())) * 10);

        graphics2D.drawLine(node.getPositionX() + (JConstants.NODE_WIDTH / 2),
                node.getPositionY() + (JConstants.NODE_HEIGHT / 2),endX,endY);

        graphics2D.setColor(oldColor);
    }
}
