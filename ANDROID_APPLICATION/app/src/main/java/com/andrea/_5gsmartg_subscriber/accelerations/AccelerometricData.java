package com.andrea._5gsmartg_subscriber.accelerations;

import com.andrea._5gsmartg_subscriber.misc.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccelerometricData {

    Accellerations[] nodeAccelerations = null;


    public AccelerometricData() {
        nodeAccelerations = new Accellerations[Constants.nodeIDs.length];

        for(int i=0; i< Constants.nodeIDs.length; i++) {
            int _singleNodeId = Constants.getNodeIdx(Constants.nodeIDs[i]);
            nodeAccelerations[i] = new Accellerations(_singleNodeId);
        }
    }


    public String[] doParse(String msgAsString, int idxNode) {
        if (msgAsString.endsWith(";"))
            msgAsString = msgAsString.substring(0, msgAsString.length() - 1);

        String[] _samples = msgAsString.split(Constants.SAMPLE_SEPARATOR);

        ArrayList<Float> _x = new ArrayList<Float>();
        ArrayList<Float> _y = new ArrayList<Float>();
        ArrayList<Float> _z = new ArrayList<Float>();

        for (String _singleSample : _samples) {
            String _res[] =  _singleSample.split(Constants.SINGLE_VALUE_SEPARATOR);
            long absoluteSampleTime = Long.parseLong(_res[0]);
            _x.add(Float.parseFloat(_res[1]));
            _y.add(Float.parseFloat(_res[2]));
            _z.add(Float.parseFloat(_res[3]));
        }

        nodeAccelerations[idxNode].addDataAsVector(_x, Constants.AXIS.x_AXIS);
        nodeAccelerations[idxNode].addDataAsVector(_y, Constants.AXIS.y_AXIS);
        nodeAccelerations[idxNode].addDataAsVector(_z, Constants.AXIS.z_AXIS);

        return _samples;
    }

    public ArrayList<Float> getDataAsVector(int idxNode, Constants.AXIS whichAxis) {
        ArrayList<Float> _toReturn = null;
        switch (whichAxis){
            case x_AXIS:
                _toReturn = nodeAccelerations[idxNode].xDataAsVector;
                break;
            case y_AXIS:
                _toReturn = nodeAccelerations[idxNode].yDataAsVector;
                break;
            case z_AXIS:
                _toReturn = nodeAccelerations[idxNode].zDataAsVector;
                break;
        }
        return _toReturn;
    }
}
