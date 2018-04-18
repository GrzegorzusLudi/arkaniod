package com.example.kaczordonald.arkanoid;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GraphicsView view;
    SensorManager sensorManager;
    Sensor rotationVectorSensor;
    SensorEventListener rvListener;
    Handler h;
    float[] orientations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (GraphicsView)findViewById(R.id.viv);
        h = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                        h.postDelayed(this,25);
                    }
                });
            }
        });
        thread.start();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        rvListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
                // Remap coordinate system
                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_X,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix);

// Convert to orientations
                orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orientations);
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setAcc((float)(Math.sin((double)orientations[0])*40));
                    }
                });
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sensorManager.registerListener(rvListener,
                rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_UP){
            view.touch();
        }
        return super.onTouchEvent(event);
    }
}
