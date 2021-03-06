package src.Client;

import org.fusesource.mqtt.client.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Kalaman on 09.01.18.
 */
public class MQTTClient {
    private MQTT mqtt;
    private static BlockingConnection connection;
    private Topic [] topic;

    private ArrayList<MQTTListener> mqttListener;
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1883;

    public static final String TOPIC_LOG = "log";
    public static final String TOPIC_DRIVE = "drive";
    public static final String TOPIC_SONIC_DISTANCE = "distance";

    public MQTTClient() {
        mqtt = new MQTT();
        mqttListener = new ArrayList<MQTTListener>();
        topic = new Topic[] {new Topic(TOPIC_LOG, QoS.EXACTLY_ONCE),new Topic(TOPIC_SONIC_DISTANCE, QoS.EXACTLY_ONCE)};

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
        public void onLogReceived(String jsonData);
        public void onDriveReceived(float distanceInCM);
        public void onUltrasonicDistanceReceived(float distanceInCM);
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
                        else if (message.getTopic().equals(TOPIC_DRIVE))
                        {
                            float distance = Float.parseFloat(payload) / (float)100;

                            for (MQTTListener listener : mqttListener)
                                listener.onDriveReceived(distance);
                        }
                        else if (message.getTopic().equals(TOPIC_SONIC_DISTANCE)){
                            float sonicDistance = Float.parseFloat(payload);

                            for (MQTTListener listener : mqttListener)
                                listener.onUltrasonicDistanceReceived(sonicDistance);
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

    public static void publish (String message, String topic) {
        try {
            connection.publish(topic, message.getBytes() ,QoS.EXACTLY_ONCE, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishLog (String msg) {
        publish(msg,TOPIC_LOG);
    }

}