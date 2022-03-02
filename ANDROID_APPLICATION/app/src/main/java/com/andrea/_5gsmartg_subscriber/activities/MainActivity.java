package com.andrea._5gsmartg_subscriber.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andrea._5gsmartg_subscriber.R;
import com.andrea._5gsmartg_subscriber.accelerations.AccelerometricData;
import com.andrea._5gsmartg_subscriber.graphics.PlotData;
import com.andrea._5gsmartg_subscriber.network.MQTT;
import com.andrea._5gsmartg_subscriber.misc.Constants;
import com.andrea._5gsmartg_subscriber.processing.Fourier;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private Button bttStart = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttStart = findViewById(R.id.bttStart);
        bttStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Lancio la plot
                Intent intent = new Intent( getString(R.string.LAUNCH_PLOT_ACTIVITY) );
                startActivity(intent);

            }
        });
    }


    private void readConfigFile() {
        File file = new File(Environment.getDataDirectory() + "/config.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String _singleLine;
            while ((_singleLine = br.readLine()) != null) {

                if (_singleLine.charAt(0) == '#')
                    continue;

                _singleLine = _singleLine.replace(" ", "");  // Remove any blank spaces
                String _field = _singleLine.substring(0, _singleLine.indexOf('='));
                String _value = _singleLine.substring(_singleLine.indexOf('=')+1).trim();

                switch (_field) {
                    case "broker":
                        Constants.broker = _field;
                        break;
                    case "root_topic_ALL":
                        Constants.root_topic_ALL = _field;
                        break;
                    case "root_topic_HASH":
                        Constants.root_topic_HASH = _field;
                        break;
                    case "username":
                        Constants.username = _field;
                        break;
                    case "password":
                        Constants.password = _field;
                        break;
                    case "clientIDs":
                        Constants.nodeIDs = _field.split(Constants.SINGLE_VALUE_SEPARATOR);
                        break;
                    default:
                        Log.e(TAG, "Error! Check the config file");
                        break;

                }

            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}