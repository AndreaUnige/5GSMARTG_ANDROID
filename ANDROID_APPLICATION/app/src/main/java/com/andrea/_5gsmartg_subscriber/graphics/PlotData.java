package com.andrea._5gsmartg_subscriber.graphics;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andrea._5gsmartg_subscriber.misc.Constants;
import com.andrea._5gsmartg_subscriber.processing.Fourier;
import com.andrea._5gsmartg_subscriber.processing.Spectrum;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;


public class PlotData {

    private final String TAG = "PlotAccelerometer";

    private Activity containerActivity = null;

    private final int MAX_NUMBER_OF_DATA_PLOTTED = 5;

    // private GraphView graphAccTimeX = null, graphAccTimeY = null, graphAccTimeZ = null;
    private GraphView graphAccFreqX = null, graphAccFreqY = null, graphAccFreqZ = null;

    private LineGraphSeries<DataPoint> timeAccX = null;
    private LineGraphSeries<DataPoint> timeAccY = null;
    private LineGraphSeries<DataPoint> timeAccZ = null;

    private LineGraphSeries<DataPoint> freqAccX = null;
    private LineGraphSeries<DataPoint> freqAccY = null;
    private LineGraphSeries<DataPoint> freqAccZ = null;


    private DataPoint[] values = null;
    private int count = 0;
    private double x = 0, y = 0;
    private double graph2LastXValue = 0d;

    private int totalPackages = 0, discardedPackages = 0;

    private final int MAX_DATA_POINTS = 1000;
    private final int MAX_DATA_TO_SHOW = 500;


    private Button bttBack = null;

    private Fourier fourier = null;

//    public PlotData(Activity _activity, int _plotIdAccTimeX, int _plotIdAccTimeY, int _plotIdAccTimeZ,
//                    int _plotIdFreqX, int _plotIdFreqY, int _plotIdFreqZ, int _bttBack) {
      public PlotData(Activity _activity, int _plotIdFreqX, int _plotIdFreqY, int _plotIdFreqZ) {

        this.containerActivity = _activity;
        fourier = new Fourier(Constants.FS);

//        graphAccTimeX = (GraphView) this.containerActivity.findViewById(_plotIdAccTimeX);
//        graphAccTimeY = (GraphView) this.containerActivity.findViewById(_plotIdAccTimeY);
//        graphAccTimeZ = (GraphView) this.containerActivity.findViewById(_plotIdAccTimeZ);

        graphAccFreqX = (GraphView) this.containerActivity.findViewById(_plotIdFreqX);
        graphAccFreqY = (GraphView) this.containerActivity.findViewById(_plotIdFreqY);
        graphAccFreqZ = (GraphView) this.containerActivity.findViewById(_plotIdFreqZ);


        // Acc right tight
        timeAccX = new LineGraphSeries<DataPoint>();
        timeAccY = new LineGraphSeries<DataPoint>();
        timeAccZ = new LineGraphSeries<DataPoint>();

        // Acc right shinbone
        freqAccX = new LineGraphSeries<DataPoint>();
        freqAccY = new LineGraphSeries<DataPoint>();
        freqAccZ = new LineGraphSeries<DataPoint>();

//        graphAccTimeX.getViewport().setXAxisBoundsManual(true);
//        graphAccTimeX.getViewport().setYAxisBoundsManual(true);
//        graphAccTimeX.getViewport().setMinY(0);
//        graphAccTimeX.getViewport().setMaxY(6000);
//        graphAccTimeX.setTitle("X");
//
//        graphAccTimeY.getViewport().setXAxisBoundsManual(true);
//        graphAccTimeY.getViewport().setYAxisBoundsManual(true);
//        graphAccTimeY.getViewport().setMinY(0);
//        graphAccTimeY.getViewport().setMaxY(6000);
//        graphAccTimeY.setTitle("Y");
//
//        graphAccTimeZ.getViewport().setXAxisBoundsManual(true);
//        graphAccTimeZ.getViewport().setYAxisBoundsManual(true);
//        graphAccTimeZ.getViewport().setMinY(0);
//        graphAccTimeZ.getViewport().setMaxY(6000);
//        graphAccTimeZ.setTitle("Z");

        graphAccFreqX.getViewport().setXAxisBoundsManual(true);
        graphAccFreqX.getViewport().setMinX(0);
        graphAccFreqX.getViewport().setMaxX(Constants.FS / 2);
        graphAccFreqX.setTitle("X");

        graphAccFreqY.getViewport().setXAxisBoundsManual(true);
        graphAccFreqY.getViewport().setMinX(0);
        graphAccFreqY.getViewport().setMaxX(Constants.FS / 2);
        graphAccFreqY.setTitle("Y");

        graphAccFreqZ.getViewport().setXAxisBoundsManual(true);
        graphAccFreqZ.getViewport().setMinX(0);
        graphAccFreqZ.getViewport().setMaxX(Constants.FS / 2);
        graphAccFreqZ.setTitle("Z");

        /*
        //accXRightTight.setTitle("Acc X");
        accXRightTight.setColor(Color.BLUE);

        //accYRightTight.setTitle("Acc Y");
        accYRightTight.setColor(Color.GREEN);

        //accZRightTight.setTitle("Acc Z");
        accZRightTight.setColor(Color.RED);

        accXRightShinbone.setColor(Color.BLUE);
        accYRightShinbone.setColor(Color.GREEN);
        accZRightShinbone.setColor(Color.RED);

        accXLeftTight.setTitle("Acc X");
        accXLeftTight.setColor(Color.BLUE);
        accYLeftTight.setTitle("Acc Y");
        accYLeftTight.setColor(Color.GREEN);
        accZLeftTight.setTitle("Acc Z");
        accZLeftTight.setColor(Color.RED);

        accXLeftShinbone.setColor(Color.BLUE);
        accYLeftShinbone.setColor(Color.GREEN);
        accZLeftShinbone.setColor(Color.RED);

        graphRightTight.setTitle("Right Tight");
        graphRightTight.setTitleTextSize(25);
        graphRightTight.addSeries(accXRightTight);
        graphRightTight.addSeries(accYRightTight);
        graphRightTight.addSeries(accZRightTight);

        graphRightTight.getViewport().setXAxisBoundsManual(true);
        graphRightTight.getViewport().setYAxisBoundsManual(true);
        graphRightTight.getViewport().setMinX(0);
        graphRightTight.getViewport().setMaxX(MAX_DATA_POINTS);
        graphRightTight.getViewport().setMinY(-1500);
        graphRightTight.getViewport().setMaxY(1500);

        graphRightShinbone.setTitle("Right Shinbone");
        graphRightShinbone.setTitleTextSize(25);

        graphRightShinbone.addSeries(accXRightShinbone);
        graphRightShinbone.addSeries(accYRightShinbone);
        graphRightShinbone.addSeries(accZRightShinbone);

        graphRightShinbone.getViewport().setXAxisBoundsManual(true);
        graphRightShinbone.getViewport().setYAxisBoundsManual(true);
        graphRightShinbone.getViewport().setMinX(0);
        graphRightShinbone.getViewport().setMaxX(MAX_DATA_POINTS);
        graphRightShinbone.getViewport().setMinY(-1500);
        graphRightShinbone.getViewport().setMaxY(1500);




        graphLeftTight.setTitle("Left Tight");
        graphLeftTight.setTitleTextSize(25);
        graphLeftTight.addSeries(accXLeftTight);
        graphLeftTight.addSeries(accYLeftTight);
        graphLeftTight.addSeries(accZLeftTight);

        graphLeftTight.getViewport().setXAxisBoundsManual(true);
        graphLeftTight.getViewport().setYAxisBoundsManual(true);
        graphLeftTight.getViewport().setMinX(0);
        graphLeftTight.getViewport().setMaxX(MAX_DATA_POINTS);
        graphLeftTight.getViewport().setMinY(-1500);
        graphLeftTight.getViewport().setMaxY(1500);

        graphLeftTight.getLegendRenderer().setVisible(true);
        graphLeftTight.getLegendRenderer().setTextSize(20);
        graphLeftTight.getLegendRenderer().setFixedPosition(0, 0);
        //graphLeftTight.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graphLeftShinbone.setTitle("Left Shinbone");
        graphLeftShinbone.setTitleTextSize(25);

        graphLeftShinbone.addSeries(accXLeftShinbone);
        graphLeftShinbone.addSeries(accYLeftShinbone);
        graphLeftShinbone.addSeries(accZLeftShinbone);

        graphLeftShinbone.getViewport().setXAxisBoundsManual(true);
        graphLeftShinbone.getViewport().setYAxisBoundsManual(true);
        graphLeftShinbone.getViewport().setMinX(0);
        graphLeftShinbone.getViewport().setMaxX(MAX_DATA_POINTS);
        graphLeftShinbone.getViewport().setMinY(-1500);
        graphLeftShinbone.getViewport().setMaxY(1500);
        */

        values = new DataPoint[MAX_NUMBER_OF_DATA_PLOTTED];

//        bttBack = this.containerActivity.findViewById(_bttBack);
//        bttBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                containerActivity.finish();
//            }
//        });

    }


    public void addData(ArrayList<Float> _xData, ArrayList<Float> _yData, ArrayList<Float> _zData) {
                    // ************** \\
                    // **** TIME **** \\
                    // ************** \\
        /*
        if (_xData.size() < MAX_DATA_TO_SHOW) {
            for (int i=0; i<_xData.size(); i++) {
                timeAccX.appendData(new DataPoint(graph2LastXValue, _xData.get(i)), true, MAX_DATA_POINTS);
                timeAccY.appendData(new DataPoint(graph2LastXValue, _yData.get(i)), true, MAX_DATA_POINTS);
                timeAccZ.appendData(new DataPoint(graph2LastXValue, _zData.get(i)), true, MAX_DATA_POINTS);
                graph2LastXValue += 1d;
            }
            int minX = 0;
            int maxX = MAX_DATA_TO_SHOW;
            graphAccTimeX.getViewport().setMinX(minX);
            graphAccTimeX.getViewport().setMaxX(maxX);
            graphAccTimeY.getViewport().setMinX(minX);
            graphAccTimeY.getViewport().setMaxX(maxX);
            graphAccTimeZ.getViewport().setMinX(minX);
            graphAccTimeZ.getViewport().setMaxX(maxX);
        }
        else {
            int _delta = _yData.size() - MAX_DATA_TO_SHOW;
            graphAccTimeX.removeAllSeries();
            graphAccTimeY.removeAllSeries();
            graphAccTimeZ.removeAllSeries();

            timeAccX = null; timeAccX = new LineGraphSeries<DataPoint>();
            timeAccY = null; timeAccY = new LineGraphSeries<DataPoint>();
            timeAccZ = null; timeAccZ = new LineGraphSeries<DataPoint>();

            for (int i=_delta; i<_delta + MAX_DATA_TO_SHOW; i++) {
                timeAccX.appendData(new DataPoint(graph2LastXValue, _xData.get(i)), true, MAX_DATA_POINTS);
                timeAccY.appendData(new DataPoint(graph2LastXValue, _yData.get(i)), true, MAX_DATA_POINTS);
                timeAccZ.appendData(new DataPoint(graph2LastXValue, _zData.get(i)), true, MAX_DATA_POINTS);
                graph2LastXValue += 1d;
            }

            int minX = (int)(graph2LastXValue - MAX_DATA_TO_SHOW);
            int maxX = (int)graph2LastXValue;
            graphAccTimeX.getViewport().setMinX(minX);
            graphAccTimeX.getViewport().setMaxX(maxX);
            graphAccTimeY.getViewport().setMinX(minX);
            graphAccTimeY.getViewport().setMaxX(maxX);
            graphAccTimeZ.getViewport().setMinX(minX);
            graphAccTimeZ.getViewport().setMaxX(maxX);

        }

        graphAccTimeX.addSeries(timeAccX);
        graphAccTimeY.addSeries(timeAccY);
        graphAccTimeZ.addSeries(timeAccZ);
    */
        // ************** \\
        // **** FREQ **** \\
        // ************** \\

        if (_xData.size() > MAX_DATA_TO_SHOW + 1) {
            if (_xData.size() % 2 != 0) {
                _xData = new ArrayList<Float>(_xData.subList(0, _xData.size()-1));
                _yData = new ArrayList<Float>(_yData.subList(0, _yData.size()-1));
                _zData = new ArrayList<Float>(_zData.subList(0, _zData.size()-1));
            }

            ArrayList<Float> last_xData = new ArrayList<Float>(_xData.subList(_xData.size() - MAX_DATA_TO_SHOW, _xData.size()));
            ArrayList<Float> last_yData = new ArrayList<Float>(_yData.subList(_yData.size() - MAX_DATA_TO_SHOW, _yData.size()));
            ArrayList<Float> last_zData = new ArrayList<Float>(_zData.subList(_zData.size() - MAX_DATA_TO_SHOW, _zData.size()));

            fourier.setSamples(last_xData);
            Spectrum xPSD = fourier.doSpectrum(false, true);

            fourier.setSamples(last_yData);
            Spectrum yPSD = fourier.doSpectrum(false, true);

            fourier.setSamples(last_zData);
            Spectrum zPSD = fourier.doSpectrum(false, true);

            int N = xPSD.getPsd().size();
            freqAccX = null; freqAccX = new LineGraphSeries<DataPoint>();
            freqAccY = null; freqAccY = new LineGraphSeries<DataPoint>();
            freqAccZ = null; freqAccZ = new LineGraphSeries<DataPoint>();

            // Discard the DC.
            for (int i = 1; i < N; i++) {
                freqAccX.appendData(new DataPoint(xPSD.getF()[i], xPSD.getPsd().get(i)), true, N);
                freqAccY.appendData(new DataPoint(yPSD.getF()[i], yPSD.getPsd().get(i)), true, N);
                freqAccZ.appendData(new DataPoint(zPSD.getF()[i], zPSD.getPsd().get(i)), true, N);
            }
            graphAccFreqX.removeAllSeries();
            graphAccFreqY.removeAllSeries();
            graphAccFreqZ.removeAllSeries();

            graphAccFreqX.addSeries(freqAccX);
            graphAccFreqY.addSeries(freqAccY);
            graphAccFreqZ.addSeries(freqAccZ);

        }
        else
            Log.i(TAG, "Data " + _xData.size() + " out of " + MAX_DATA_TO_SHOW +
                    "\nNot enough data to compute the spectrum!");


    }

}
