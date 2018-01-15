package src;

import org.fusesource.mqtt.client.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Kalaman on 09.01.18.
 */
public class MQTTClient {
    private MQTT mqtt;
    private BlockingConnection connection;
    private Topic [] topic;

    private ArrayList<MQTTListener> mqttListener;
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1883;

    private static final String TOPIC_LOG = "log";
    private static final String TOPIC_NODE = "node";

    public MQTTClient() {
        mqtt = new MQTT();
        mqttListener = new ArrayList<MQTTListener>();
        topic = new Topic[] {new Topic(TOPIC_LOG, QoS.EXACTLY_ONCE),new Topic(TOPIC_NODE, QoS.EXACTLY_ONCE)};

        try {
            mqtt.setHost(SERVER_IP, SERVER_PORT);
            connection = mqtt.blockingConnection();
            connection.connect();
            JConsolePanel.writeToConsole("Connected to MQTT-Server [" + SERVER_IP + ":" + SERVER_PORT + "]");
            connection.subscribe(topic);

        }
        catch (Exception e){
            e.printStackTrace();
            JConsolePanel.writeToConsole("Connection with MQTT-Server failed " + SERVER_IP + ":" + SERVER_PORT);
        }
    }

    public interface MQTTListener {
        public void onNodeDataReceived(ArrayList<Node> nodes);
        public void onLogReceived(String jsonData);
    }

    public boolean addMQTTListener(MQTTListener listener) {
        return mqttListener.add(listener);
    }

    public boolean removeMQTTListener(MQTTListener listener) {
        return mqttListener.remove(listener);
    }

    public void startListeningThread () {
        new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Message message = connection.receive();
                        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);

                        if (message.getTopic().equals(TOPIC_LOG)) {
                            for (MQTTListener listener : mqttListener)
                                listener.onLogReceived(payload);
                        }
                        else if (message.getTopic().equals(TOPIC_NODE))
                        {
                            ArrayList <Node> nodeArrayList = MQTTClient.parseJSONNodeData(payload);
                            if (nodeArrayList != null)
                            {
                                JConsolePanel.writeToConsole("New Nodes received ["+nodeArrayList.size()+"]");

                                for (MQTTListener listener : mqttListener)
                                    listener.onNodeDataReceived(nodeArrayList);
                            }
                        }

                        message.ack();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

    public void publish (String message, String topic) {
        try {
            connection.publish(topic, message.getBytes() ,QoS.EXACTLY_ONCE, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishLog (String msg) {
        publish(msg,TOPIC_LOG);
    }

    public boolean publishNodeData (ArrayList <Node> nodeArrayList) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            for (Node node: nodeArrayList)
            {
                JSONObject item = new JSONObject();

                item.put("x", node.getPositionX());
                item.put("y", node.getPositionY());
                item.put("deg", node.getDegree());

                array.put(item);
            }

            json.put("nodes", array);
            publish(json.toString(),TOPIC_LOG);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Node> parseJSONNodeData (String jsonData) {
        ArrayList<Node> resultList = new ArrayList<>();
        try{
            JSONObject obj = new JSONObject(jsonData);

            JSONArray arr = obj.getJSONArray("nodes");
            for (int i = 0; i < arr.length(); i++)
            {
                int nodeXPos = arr.getJSONObject(i).getInt("x");
                int nodeYPos = arr.getJSONObject(i).getInt("y");
                float nodeDeg = (float)arr.getJSONObject(i).getDouble("deg");

                resultList.add(new Node(nodeXPos,nodeYPos,nodeDeg));
            }
        }
        catch (JSONException je)
        {
            je.printStackTrace();
            JConsolePanel.writeToConsole("Unknown JSON type received");
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }


}