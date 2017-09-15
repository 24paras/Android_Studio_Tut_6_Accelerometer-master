package com.example.pc.accelerometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends Activity implements SensorEventListener{

    private TextView xText, yText, zText, gxText, gyText, gzText;
    private Sensor mySensor;
    private Sensor gSensor;
    private SensorManager SM;
    private FileWriter writer;
    private SensorEventListener gyroScopeEventListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create our Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gSensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Register sensor Listener

        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_NORMAL);


        // Assign TextView
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);

        gxText = (TextView)findViewById(R.id.gxText);
        gyText = (TextView)findViewById(R.id.gyText);
        gzText = (TextView)findViewById(R.id.gzText);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onResume();

            }
        });

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onStop();

            }
        });

    }


    protected void onResume(){
        super.onResume();
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //writer = new FileWriter("accelerometer.txt", true);
        //writer2 = new FileWriter("gyroscope.csv", true);


    }

    protected void onStop(){
        super.onStop();
        SM.unregisterListener(this, mySensor);
        SM.unregisterListener(this, gSensor);
        /*
        if(writer != null) {
            writer.close();
        }
        */

    }

    private void appendToFile(String str) throws IOException {
        File file = getFileStreamPath("test.csv");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream writer = openFileOutput(file.getName(), MODE_APPEND | MODE_WORLD_READABLE);
        writer.write(str.getBytes());
        writer.flush();
        writer.close();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    @Override

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            xText.setText("X: " + event.values[0]);
            yText.setText("Y: " + event.values[1]);
            zText.setText("Z: " + event.values[2]);
           // writer.write (xText+","+yText+","+zText+"\n");
        }

        else if(sensor.getType()==Sensor.TYPE_GYROSCOPE)
        {
            gxText.setText("GX: " + event.values[0]);
            gyText.setText("GY: " + event.values[1]);
            gzText.setText("GZ: " + event.values[2]);
            //writer2.write (gxText+","+gyText+","+gzText+"\n");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
