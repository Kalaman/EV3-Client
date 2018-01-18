package Client;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kalaman on 12.01.18.
 */
public class App {

    public static void main (String[] args) {

        new JMainFrame();

        MQTTClient mqttClient = new MQTTClient();
        mqttClient.addMQTTListener(new MQTTClient.MQTTListener() {
            @Override
            public void onNodeDataReceived(ArrayList<Particle> nodes) {

            }

            @Override
            public void onLogReceived(String log) {
                System.out.println(log);
            }
        });

		mqttClient.startListeningThread();

        LineReader lReader = new LineReader();

        /** For testing purposes
         */

        Particle particle = new Particle(284,80,270);
//        drawNode(particle, Color.RED,graphics2D());

//        particle.drawParticleLine(particle,Color.RED,graphics2D());
        Client.Line line = new Client.Line(245,295 ,50 ,50 );
        //graphics2D.drawLine(line.getX1(),line.getY1(),line.getX2(),line.getY2());
        Point p = particle.findIntersection(line.getX1(),line.getY1(),line.getX2(),line.getY2());
        System.out.println(p);
        System.out.println(particle.getDistanceToWall(p));
        //graphics2D.drawOval( (int) p.getX() - 2, (int) p.getY() -2, 4,4);
        
    }





}
