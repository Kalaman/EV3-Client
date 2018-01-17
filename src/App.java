package src;
import org.fusesource.mqtt.client.MQTT;
import org.json.JSONArray;
import org.json.JSONObject;

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
            public void onNodeDataReceived(ArrayList<Node> nodes) {

            }

            @Override
            public void onLogReceived(String log) {
                JConsolePanel.writeToConsole(log);
            }

            @Override
            public void onDriveReceived(float distanceInCM) {

            }
        });

        mqttClient.startListeningThread();
    }





}
