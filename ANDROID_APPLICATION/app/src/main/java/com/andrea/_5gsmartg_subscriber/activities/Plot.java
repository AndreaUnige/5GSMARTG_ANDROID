package com.andrea._5gsmartg_subscriber.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.andrea._5gsmartg_subscriber.R;
import com.andrea._5gsmartg_subscriber.accelerations.AccelerometricData;
import com.andrea._5gsmartg_subscriber.graphics.PlotData;
import com.andrea._5gsmartg_subscriber.misc.Constants;
import com.andrea._5gsmartg_subscriber.network.MQTT;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

public class Plot extends Activity implements MqttCallbackExtended {

    private final String TAG = "Plot";
    private PlotData plotData = null;

    private MqttCallbackExtended myListener = null;
    private AccelerometricData accelerometricData = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot_data);

        myListener = this;
        accelerometricData = new AccelerometricData();

        MQTT mqtt = new MQTT(getApplicationContext(), myListener);
        mqtt.connect_and_subscribe(Constants.root_topic_ALL + "1");


//        plotData = new PlotData(this, R.id.accXTime, R.id.accYTime, R.id.accZTime,
//                R.id.accXFreq, R.id.accYFreq, R.id.accZFreq, R.id.bttBack);
        plotData = new PlotData(this, R.id.accXFreq, R.id.accYFreq, R.id.accZFreq);
    }

    private void addData(int idxNode) {
        plotData.addData(accelerometricData.getDataAsVector(idxNode, Constants.AXIS.x_AXIS),
                accelerometricData.getDataAsVector(idxNode, Constants.AXIS.y_AXIS),
                accelerometricData.getDataAsVector(idxNode, Constants.AXIS.z_AXIS));
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {

    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.i(TAG, "Topic : " + topic);
        Log.i(TAG, "Data : " + message.toString());

        // Extract the clientID
        String _id = topic.substring(topic.lastIndexOf("/")+1);
        accelerometricData.doParse(message.toString(), Constants.getNodeIdx(_id));
        addData(Constants.getNodeIdx(_id));

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
