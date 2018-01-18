package src.Client;

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
            public void onNodeDataReceived(ArrayList<src.Client.Particle> nodes) {

            }

            @Override
            public void onLogReceived(String log) {
                System.out.println(log);
            }
        });

		mqttClient.startListeningThread();

		frame.getjRobotPanel().testParticle();

    }

}
