package com.example.sensorexample;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

	 private SensorManager sensorManager = null;  
	    private Sensor orientationSensor = null;  
	    private Sensor accelerometerSensor = null; 
	    
	    private TextView oriTV = null;  
	    private TextView accTV = null;  
	      
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  
         
	        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  
	        orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);  
	  
	        oriTV = (TextView) findViewById(R.id.textViewOri);  
	        accTV = (TextView) findViewById(R.id.textViewAcc);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		  synchronized (this) {  
	            Log.e("Sensor", "onSensorChanged: " + event.sensor.getType()  
	                    + ", x: " + event.values[0] + ", y: " + event.values[1] + ", z: " + event.values[2]);  
	            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){  
	                accTV.setText("Accel \n x: "+ event.values[0] + ", y: " + event.values[1] + ", z: " + event.values[2]);  
	            }else if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){  
	                oriTV.setText("Orientation \n x: "+ event.values[0] + ", y: " + event.values[1] + ", z: " + event.values[2]);  
	                /**  
	                 * float headingAngle = values[0];  
	                    float pitchAngle = values[1];  
	                    float rollAngle = values[2];  
	                 */               
	            }  
	        }  
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	  @Override  
	    protected void onStart() {  
	        super.onStart();  
	          
	        //for game rate  
	        if(accelerometerSensor != null)  
	            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);  
	        if(orientationSensor != null)  
	            sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);  
	    }  
	  
	  
	    @Override  
	    protected void onStop() {  
	          
	        if(accelerometerSensor != null || orientationSensor != null)  
	            sensorManager.unregisterListener(this);  
	          
	        super.onStop();  
	    }  
	  
	  
	    @Override  
	    protected void onDestroy() {  
	          
	        if(accelerometerSensor != null || orientationSensor != null)  
	            sensorManager.unregisterListener(this);  
	          
	        super.onDestroy();  
	    }  
}
