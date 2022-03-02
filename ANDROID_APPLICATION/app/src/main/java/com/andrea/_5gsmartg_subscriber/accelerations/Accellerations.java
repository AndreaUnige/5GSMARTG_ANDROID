package com.andrea._5gsmartg_subscriber.accelerations;
import android.util.Log;

import androidx.core.content.res.TypedArrayUtils;

import com.andrea._5gsmartg_subscriber.misc.Constants;
import java.util.ArrayList;

public class Accellerations {
     private final String TAG = "Accellerations";

    ArrayList<Float> xDataAsVector = null;
    ArrayList<Float> yDataAsVector = null;
    ArrayList<Float> zDataAsVector = null;

    // At the moment this is not used
    ArrayList<Float[]> accDataAsMatrix = null;

    int nodeID = -1;




    public Accellerations(int _nodeID){
        xDataAsVector = new ArrayList<Float>();
        yDataAsVector = new ArrayList<Float>();
        zDataAsVector = new ArrayList<Float>();

        nodeID = _nodeID;
    }

    public void addDataAsVector(ArrayList<Float> _data, Constants.AXIS whichAxis){

        switch (whichAxis){
            case x_AXIS:
                xDataAsVector.addAll(_data);
                break;

            case y_AXIS:
                yDataAsVector.addAll(_data);
                break;

            case z_AXIS:
                zDataAsVector.addAll(_data);
                break;

            default:
                Log.e(TAG, "Wrong axis!");
        }
    }

    public void clearData() {
        xDataAsVector.clear();
        yDataAsVector.clear();
        zDataAsVector.clear();
    }
}
