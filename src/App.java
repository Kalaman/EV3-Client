import org.fusesource.mqtt.client.MQTT;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kalaman on 12.01.18.
 */
public class App {

    public static void main (String [] args) {
        new JMainFrame();

        ArrayList<Node> nodeArrayList = new ArrayList<>();
        nodeArrayList.add(new Node(12,31,23));
        nodeArrayList.add(new Node(299,313,123));
        nodeArrayList.add(new Node(712,931,923));

        MQTTClient mqttClient = new MQTTClient();
        mqttClient.addMQTTListener(new MQTTClient.MQTTListener() {
            @Override
            public void onNodeDataReceived(String jsonData) {
                System.out.println("New Node bro ! \n" + jsonData);
                ArrayList <Node> nodeArrayList1 = MQTTClient.parseJSONNodeData(jsonData);
                nodeArrayList1.size();
            }

            @Override
            public void onLogReceived(String log) {
                System.out.println(log);
            }
        });

        mqttClient.startListeningThread();
    }





}
