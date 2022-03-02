package com.andrea._5gsmartg_subscriber.misc;

public class Constants {

    public static int MAX_ACCELERATIONS_SAMPLES = 1000;

    // Diversamente da python DEVO specificare tpc:// davanti alla stringa del broker
    public static String broker = "tcp://iconpush.tim.it";
    public static String root_topic_ALL = "unige/5gsmartg/up/ALL/";
    public static String root_topic_HASH = "unige/5gsmartg/up/HASH/";
    public static String username = "unige5gsmartguser1";
    public static String password = "990da8bd-8160-97aca3ed98a7";

    public static String[] nodeIDs = {"1", "2", "3", "4", "5", "6"};

    public static int getNodeIdx(String _nodeName) {
        int res = -1;
        for(int i=0; i<nodeIDs.length;i++) {
            if (Constants.nodeIDs[i].equalsIgnoreCase(_nodeName))
                res = i;
        }
        return res;
    }

    public enum Node_ID {

    }

    public enum AXIS {
        x_AXIS,
        y_AXIS,
        z_AXIS
    }

    public static String SAMPLE_SEPARATOR = ";";
    public static String SINGLE_VALUE_SEPARATOR = ",";

    public static int FS = 100; // Hz

}


