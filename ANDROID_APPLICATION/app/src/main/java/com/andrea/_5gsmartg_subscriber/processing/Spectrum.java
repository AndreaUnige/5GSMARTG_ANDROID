package com.andrea._5gsmartg_subscriber.processing;

import java.util.ArrayList;

public class Spectrum {

    double [] f = null;
    ArrayList<Double> psd = null;

    public Spectrum(int N) {
        f = new double[N];
        psd = new ArrayList<Double>();
    }

    public double[] getF() {
        return f;
    }

    public void setF(double[] f) {
        this.f = f;
    }

    public ArrayList<Double> getPsd() {
        return psd;
    }

    public void setPsd(ArrayList<Double> psd) {
        this.psd = psd;
    }
}
