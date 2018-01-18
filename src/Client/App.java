package src.Client;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kalaman on 12.01.18.
 */
public class App {

    public static void main (String[] args) {

        JMainFrame frame = new JMainFrame();

        MQTTClient mqttClient = new MQTTClient();
        mqttClient.addMQTTListener(new MQTTClient.MQTTListener() {
            @Override
            public void onDriveReceived(float distanceInCM) {

            }

            @Override
            public void onUltrasonicDistanceReceived(float distanceInCM) {

            }

            @Override
            public void onLogReceived(String log) {
                System.out.println(log);
            }
        });

		mqttClient.startListeningThread();


        /** For testing purposes
         */

        Particle particle = new Particle(284,80,270);
//        drawNode(particle, Color.RED,graphics2D());

//        particle.drawParticleLine(particle,Color.RED,graphics2D());
        Line line = new Line(245,295 ,50 ,50 );
        //graphics2D.drawLine(line.getX1(),line.getY1(),line.getX2(),line.getY2());
        Point p = particle.findIntersection(line.getX1(),line.getY1(),line.getX2(),line.getY2());
        System.out.println(p);
        //graphics2D.drawOval( (int) p.getX() - 2, (int) p.getY() -2, 4,4);
        
    }





}
