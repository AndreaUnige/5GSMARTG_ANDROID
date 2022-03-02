package com.andrea._5gsmartg_subscriber.network;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.andrea._5gsmartg_subscriber.misc.Constants;

public class MQTT {

    private final String TAG = "MQTT";

    private MqttAndroidClient client = null;
    private MqttConnectOptions options = null;
    private MqttCallbackExtended myActionCallback = null;

    private Context context = null;


    public MQTT(Context _context, MqttCallbackExtended _myActionCallback) {
        context = _context;
        myActionCallback = _myActionCallback;
    }


    public void connect_and_subscribe(String _topic) {
        String clientId = MqttClient.generateClientId();

        this.options = new MqttConnectOptions();
        this.options.setUserName(Constants.username);
        this.options.setPassword(Constants.password.toCharArray());

        client = new MqttAndroidClient(context, Constants.broker, clientId);

        try {
            Log.i(TAG, "Trying to connect...");
            IMqttToken token = this.client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    subscribe(_topic, (byte) 2);


                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    private void subscribe(String topic, byte qos) {
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Topic: " + topic);
                    Log.d(TAG, "subscribed: " + asyncActionToken.toString());

                    client.setCallback(myActionCallback);
                    /*
                    client.setCallback(new MqttCallbackExtended() {
                        @Override
                        public void connectComplete(boolean b, String s) {
                        }
                        @Override
                        public void connectionLost(Throwable throwable) {
                        }
                        @Override
                        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                            Log.i(TAG, "String s: " + s);
                            Log.i(TAG, "MqttMessage : " + mqttMessage.toString());


                        }
                        @Override
                        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                        }
                    });
                    */

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    Log.d(TAG, "subscribing error");
                }
            });


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            IMqttToken disconToken = this.client.disconnect();
            disconToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("mqtt:", "disconnected");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {


                    Log.d("mqtt:", "couldnt disconnect");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

}