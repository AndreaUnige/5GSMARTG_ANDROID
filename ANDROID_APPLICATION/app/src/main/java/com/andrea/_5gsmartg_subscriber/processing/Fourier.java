package com.andrea._5gsmartg_subscriber.processing;

import android.util.Log;

import org.jtransforms.fft.DoubleFFT_1D;
import java.util.ArrayList;

public class Fourier {

    private final String TAG = "Fourier";

    private ArrayList<Float> samples = null;
    private int fs = -1;

    private int winSizeInSeconds = -1;
    private int winSizeInSamples = -1;

    private float overlapPercentage = -1;

    public Fourier(int fs) {
        /*
        samples = new ArrayList<Float>();
        samples.add(0.0f);
        samples.add(0.707f);
        samples.add(1f);
        samples.add(0.707f);
        samples.add(0.0f);
        samples.add(-0.707f);
        samples.add(-1f);
        samples.add(-0.707f);
        */
        // this.samples = samples;
        this.fs = fs;

    }
    public Fourier(int winSizeInSeconds, float overlapPercentage, int fs) {
        this(fs);
        this.winSizeInSeconds = winSizeInSeconds;
        this.winSizeInSamples = this.winSizeInSeconds * fs;
        this.overlapPercentage = overlapPercentage;
    }

    public ArrayList<Float> getSamples() {
        return samples;
    }

    public void setSamples(ArrayList<Float> samples) {
        this.samples = samples;
    }

    public int getFs() {
        return fs;
    }

    public void setFs(int fs) {
        this.fs = fs;
    }


    public Spectrum doWelchSpectrum(boolean removeMean, boolean logarithmic) {

        int length = samples.size();
        Spectrum spectrum = null;

        if ((winSizeInSeconds == -1) || (overlapPercentage == -1)) {
            Log.e(TAG, "WinSize and/or Overlap percentage not set!");
            return null;
        }
        if (winSizeInSeconds < length) {
            Log.e(TAG, "WinSize cannot be smaller than the input sequence\n" +
                    "WinSize: " + winSizeInSeconds + " InputSequenceLenght: " + length);
            return null;
        }

        ArrayList<ArrayList<Float>> windowedSamples = createWindows();
        // DA FINIRE

        return spectrum;
    }

    private ArrayList<ArrayList<Float>> createWindows() {
        ArrayList<ArrayList<Float>> windowedSamples = new ArrayList<ArrayList<Float>>();

        int nSamplesOverlapped = Math.round(winSizeInSamples * (overlapPercentage/100));

        ArrayList<Float> _singleWindow = null;

        int i = 0;
        while (true) {
            _singleWindow = new ArrayList<Float>();
            int start = i * nSamplesOverlapped;
            int end = start + winSizeInSamples;

            if (end > samples.size())
                break;

            for (i=start; i<end; i++) {
                _singleWindow.add(samples.get(i));
            }
            windowedSamples.add(_singleWindow);
        }
        return windowedSamples;
    }


    // public Spectrum doSpectrum(ArrayList<ArrayList<Float>> windowSampled, boolean removeMean, boolean logarithmic) {
       //to complete
    // }

    public Spectrum doSpectrum(boolean removeMean, boolean logarithmic) {
        return doSpectrum(samples, removeMean, logarithmic);
    }


    private Spectrum doSpectrum(ArrayList<Float> samples, boolean removeMean, boolean logarithmic) {

        int length = samples.size();
        Spectrum spectrum = null;

        if (length % 2 == 0) {
            DoubleFFT_1D dfft1d = new DoubleFFT_1D(length);

            ArrayList<Double> modulus = new ArrayList<Double>();
            double[] fftSamples = new double[2 * length];

            spectrum = new Spectrum(length / 2 + 1);
            double[] freq = new double[length / 2 + 1];

            // compute mean
            double _mean = 0;
            if (removeMean) {
                for (int i = 0; i < length; i++)
                    _mean += samples.get(i);
                _mean /= length;
            }

            //copy samples
            for (int i = 0; i < length; i++)
                fftSamples[i] = samples.get(i) - _mean;

            //FFT samples
            dfft1d.realForwardFull(fftSamples);

            // Take only the first half, Multiply *2
            for (int i = 0; i < length + 1; i = i + 2) {
                double _singlePSDvalues = 2 * (Math.sqrt(Math.pow(fftSamples[i], 2) + Math.pow(fftSamples[i + 1], 2))) / length;
                if (logarithmic) {
                    _singlePSDvalues = 10 * Math.log10(_singlePSDvalues);
                }
                modulus.add(_singlePSDvalues);
                freq[i / 2] = ((float) (this.fs) / length) * i / 2;
            }

            spectrum.setF(freq);
            spectrum.setPsd(modulus);
        }
        else{
            Log.e(TAG, "Spectrum can only be computed for EVEN sequences");
        }

            return spectrum;
    }

}
