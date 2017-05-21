package lab1_203_02.uwaterloo.ca.lab1_203_02;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import ca.uwaterloo.sensortoy.LineGraphView;

public class Lab1 extends AppCompatActivity {
LineGraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);
        TextView tv = (TextView) findViewById(R.id.label1);//assigns tv to label 1
        tv.setText("");
        LinearLayout l = (LinearLayout) findViewById(R.id.Linear); //making linear layout appear in .java
        graph = new LineGraphView(getApplicationContext(),100, Arrays.asList("x","y","z"));
        l.setOrientation(LinearLayout.VERTICAL); //makes the layout follow a vertial pattern


        //***LABELS*** -<
        TextView Title = new TextView(getApplicationContext());
        TextView tv1 = new TextView(getApplicationContext());//these are the labels i have to use
        TextView tv2 = new TextView(getApplicationContext());
        TextView tv3 = new TextView(getApplicationContext());
        TextView tv4 = new TextView(getApplicationContext());
        TextView read1 = new TextView(getApplicationContext()); //accelerometer reading
        TextView read2 = new TextView(getApplicationContext()); //light sensor
        TextView read3 = new TextView(getApplicationContext()); //stuff
        TextView read4 = new TextView(getApplicationContext());
        Title.setText("LAB 1 : BULLSHIT");
        Title.setTextSize(40);
        Title.setTextColor(Color.MAGENTA);
        tv1.setText("Accelerometer");
        tv1.setTextSize(24);
        tv1.setTextColor(Color.RED);
        tv2.setText("Light Sensor");//set text of all labels --> these are just labels
        tv2.setTextSize(24);
        tv2.setTextColor(Color.RED);
        tv3.setText("Magnetic Sensor");
        tv3.setTextSize(24);
        tv3.setTextColor(Color.RED);
        tv4.setText("Rotational Vector");
        tv4.setTextSize(24);
        tv4.setTextColor(Color.RED);
        // >-


        //LINEAR LAYOUT <-
        l.addView(Title);
        l.addView(tv1); //adds labels to the lienar layout in oder
        l.addView(read1);
        l.addView(tv2);
        l.addView(read2);
        l.addView(tv3);
        l.addView(read3);
        l.addView(tv4);
        l.addView(read4);
        l.addView(graph);
        graph.setVisibility(View.VISIBLE);
        // >-


        //****SENSOR STUFF**** -<
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//this is the sensor manager that I'm using

        //light sensor
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //gets default light sensor class
        SensorEventListener light = new LightSensorEventListener(read2); //has an event listener and makes an new one with output of read //this is the handler
        sensorManager.registerListener(light,lightSensor,sensorManager.SENSOR_DELAY_GAME);//registers handler to light sensor so manager knows to use the light sensor to find shit

        //accelerometer
        Sensor AccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener Accel = new AccelerometerEventListener(read1, graph);
        sensorManager.registerListener(Accel,AccelerometerSensor,sensorManager.SENSOR_DELAY_GAME);

        //magnetic field
        Sensor MagneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        SensorEventListener Mag = new MagneticFieldEventListener(read3);
        sensorManager.registerListener(Mag,MagneticFieldSensor,sensorManager.SENSOR_DELAY_GAME);

        //rotational vector
        Sensor RotationalVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        SensorEventListener Rot = new RotationVectorEventListener(read4);
        sensorManager.registerListener(Rot,RotationalVectorSensor,sensorManager.SENSOR_DELAY_GAME);
        //   >-


    }
}


class LightSensorEventListener implements SensorEventListener {
    TextView output;
    //double lightsensorvalue = 0;
    double maxvalue = 0;
    public LightSensorEventListener(TextView outputView) {
        output = outputView;
    }

    public void onAccuracyChanged(Sensor s, int i) { }

    public void onSensorChanged(SensorEvent se)
    {
        if (se.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            //the variable se.values is an array of type int or double
            //the first value contains the value
            //of the light sensor. store it
            //lightsensorvalue = se.values[0];
            if (maxvalue < se.values[0])
            {
                maxvalue = se.values[0];
            }
            output.setText("MAX: " + String.valueOf(maxvalue) + "\n" + "CURRENT: " + String.valueOf(se.values[0]));
        }
    }
}



class AccelerometerEventListener implements SensorEventListener {
    TextView output;
    LineGraphView graph;
    //double Accelerometersensorvalue = 0;
    double[] maxvalues = {0,0,0};
    public AccelerometerEventListener(TextView outputView, LineGraphView AGraph) {
        output = outputView;
        graph = AGraph;
    }

    public void onAccuracyChanged(Sensor s, int i) { }

    public void onSensorChanged(SensorEvent se)
    {

        if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            graph.addPoint(se.values);
            //the variable se.values is an array of type int or double
            //the first value contains the value
            //of the light sensor. store it
            //Accelerometersensorvalue = se.values[0];
            if (maxvalues[0]< se.values[0])
            {
                maxvalues[0] = se.values[0];
            }
            if (maxvalues[1]< se.values[1])
            {
                maxvalues[1] = se.values[1];
            }
            if (maxvalues[2]< se.values[2])
            {
                maxvalues[2] = se.values[2];
            }
            output.setText("MAX: X: " + String.valueOf(maxvalues[0]) + ", Y: " + String.valueOf(maxvalues[1])+ ", Z: " + String.valueOf(maxvalues[2]) + "\n" + "CURRENT: X: " + String.valueOf(se.values[0]) + ", Y: " + String.valueOf(se.values[1])+ ", Z: " + String.valueOf(se.values[2]));
        }
    }
}



class MagneticFieldEventListener implements SensorEventListener {
    TextView output;
    //double Magsensorvalue = 0;
    double[] maxvalues = {0,0,0};
    public MagneticFieldEventListener(TextView outputView) {
        output = outputView;
    }

    public void onAccuracyChanged(Sensor s, int i) { }

    public void onSensorChanged(SensorEvent se)
    {

        if (se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            //the variable se.values is an array of type int or double
            //the first value contains the value
            //of the light sensor. store it
            //Magsensorvalue = se.values[0];
            if (maxvalues[0]< se.values[0])
            {
                maxvalues[0] = se.values[0];
            }
            if (maxvalues[1]< se.values[1])
            {
                maxvalues[1] = se.values[1];
            }
            if (maxvalues[2]< se.values[2])
            {
                maxvalues[2] = se.values[2];
            }
            output.setText("MAX: X: " + String.valueOf(maxvalues[0]) + ", Y: " + String.valueOf(maxvalues[1])+ ", Z: " + String.valueOf(maxvalues[2]) + "\n" + "CURRENT: X: " + String.valueOf(se.values[0]) + ", Y: " + String.valueOf(se.values[1])+ ", Z: " + String.valueOf(se.values[2]));
        }
    }
}




class RotationVectorEventListener implements SensorEventListener {
    TextView output;
    //double Rotsensorvalue = 0;
    double[] maxvalues = {0,0,0};
    public RotationVectorEventListener(TextView outputView) {
        output = outputView;
    }

    public void onAccuracyChanged(Sensor s, int i) { }

    public void onSensorChanged(SensorEvent se)
    {
        if (se.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            //the variable se.values is an array of type int or double
            //the first value contains the value
            //of the light sensor. store it
            //Rotsensorvalue = se.values[0];
            if (maxvalues[0]< se.values[0])
            {
                maxvalues[0] = se.values[0];
            }
            if (maxvalues[1]< se.values[1])
            {
                maxvalues[1] = se.values[1];
            }
            if (maxvalues[2]< se.values[2])
            {
                maxvalues[2] = se.values[2];
            }
            output.setText("MAX: X: " + String.valueOf(maxvalues[0]) + ", Y: " + String.valueOf(maxvalues[1])+ ", Z: " + String.valueOf(maxvalues[2]) + "\n" + "CURRENT: X: " + String.valueOf(se.values[0]) + ", Y: " + String.valueOf(se.values[1])+ ", Z: " + String.valueOf(se.values[2]));
        }
    }
}