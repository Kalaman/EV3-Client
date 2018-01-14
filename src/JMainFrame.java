import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.URI;
import java.net.URL;

/**
 * Created by Kalaman on 12.01.18.
 */
public class JMainFrame extends JFrame{

    public JMainFrame () {
        super("EVToThe3");

        this.setSize(new Dimension(JConstants.WINDOW_SIZE_X,JConstants.WINDOW_SIZE_Y));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

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
            diagram.render(graphics2D);
        }catch (Exception e){
            e.printStackTrace();
        }
        drawNode(new Node(310,50,0),Color.RED,graphics2D);
    }

    public void drawNode (Node node, Color color,Graphics2D graphics2D) {
        Color oldColor = graphics2D.getColor();

        graphics2D.setColor(color);
        graphics2D.drawOval(node.getPositionX(),node.getPositionY(),JConstants.NODE_WIDTH,JConstants.NODE_HEIGHT);

        int endX = node.getPositionX() + (JConstants.NODE_WIDTH / 2) + (int)Math.cos(Math.toRadians(node.getDegree())) * 10;
        int endY = node.getPositionY() + (JConstants.NODE_HEIGHT / 2) + (int)Math.sin(Math.toRadians(node.getDegree())) * 10;

        graphics2D.drawLine(node.getPositionX() + (JConstants.NODE_WIDTH / 2),
                node.getPositionY() + (JConstants.NODE_HEIGHT / 2),endX,endY);

        graphics2D.setColor(oldColor);
    }
}
