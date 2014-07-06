package com.exercise.AndroidViewPager;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragmentC extends Fragment implements SensorEventListener {
	
	//Declare Sensors by SM
	SensorManager sensorManager = null;
	
	/*private SensorManager manager;
	private SensorEventListener listener;*/
	
	TextView xCoor; // declare X axis object
	TextView yCoor; // declare Y axis object
	TextView zCoor; // declare Z axis object
	
	//Declare x,y,z parameters in order to use them for sending data to the Nao Robot
	float xAcc;
	float yAcc;
	float zAcc;
	
	public float ControlByAcc;
	
	private Button btnAccActivated;
	private Button btnAccDisabled;

	
	//Declare the Acc status
	TextView DispAccStatus;
	TextView DispAccONOFF;
	TextView DispGyrValues;
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.fragment_c, container, false);
		
		/* -------------------------------------------------------------- */
				/* -- Accelerometer -- */
		/* -------------------------------------------------------------- */  
		xCoor=(TextView)myFragmentView.findViewById(R.id.txtAccX); // create X axis object
		yCoor=(TextView)myFragmentView.findViewById(R.id.txtAccY); // create Y axis object
		zCoor=(TextView)myFragmentView.findViewById(R.id.txtAccZ); // create Z axis object
		
		DispAccStatus=(TextView)myFragmentView.findViewById(R.id.txtGyroscope);
		DispAccONOFF=(TextView)myFragmentView.findViewById(R.id.txtAccONOFF);
		DispAccStatus=(TextView)myFragmentView.findViewById(R.id.accstatus); // create Accelerometer status
		
		//manager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		
		
		/* -------------------------------------------------------------- */
			/* -- Button Initialization -- */
		/* -------------------------------------------------------------- */ 
		
		btnAccActivated = (Button)myFragmentView.findViewById(R.id.btnStartAccelerometer);
		btnAccActivated.setOnClickListener(AccelerometerBtnListener);
		
		btnAccDisabled = (Button)myFragmentView.findViewById(R.id.btnStopAccelerometer);
		btnAccDisabled.setOnClickListener(AccelerometerBtnListener);
		
		
		return myFragmentView;
	}
	
	
	OnClickListener AccelerometerBtnListener
	= new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			
	        case R.id.btnStartAccelerometer:
	        	
	        	//words = "Speech disabled";
	            //speakWords(words);
	            
	            //DispBtnControlONOFF.setText("ON");
	            DispAccONOFF.setText("ON");

				
				AndroidViewPagerActivity.fragmentD.txtAccControl.setText("Control by Accelerometer: ON");
	        	
				AndroidViewPagerActivity.fragmentD.ControlByAcc = 1;
				
	        	//ControlByVoice= 0;
	        	ControlByAcc = 0;
	        	//ControlByBtn = 1;
	            Toast.makeText(getActivity(), "Accelerometer Control activated" + "+", Toast.LENGTH_SHORT).show();
	            break;
	            
	        case R.id.btnStopAccelerometer:
	        	
	        	//words = "Speech disabled";
	            //speakWords(words);
	            
	            //DispBtnControlONOFF.setText("ON");
	            DispAccONOFF.setText("OFF");
	            
	            //AndroidViewPagerActivity.fragmentD.txtAccControl.setText("Control by Accelerometer: OFF");
	            
	            //AndroidViewPagerActivity.fragmentD.ControlByAcc = 0;
	            
	            AndroidViewPagerActivity.fragmentD.TCP_updateAccControl(0);
	            
	        	//ControlByVoice= 0;
	        	ControlByAcc = 0;
	        	//ControlByBtn = 1;
	            Toast.makeText(getActivity(), "Accelerometer Control disactivated" + "+", Toast.LENGTH_SHORT).show();
	            break;
	            
	            
	            default:
	            	break;
	            
			}

		}
		
	};
	
	/* -------------------------------------------------------------- */
				/* -- oN Start/Stop/Pause/Delete -- */
    /* -------------------------------------------------------------- */ 

		@Override
		public void onStart() {
			Log.i("","onStart");
			super.onStart();
		}
		
		@Override
		public void onResume() {
			Log.i("","onResume");
			super.onResume();
			
			sensorManager.registerListener(this,
			sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
			sensorManager.SENSOR_DELAY_FASTEST);
			
			sensorManager.registerListener(this,
			sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
			sensorManager.SENSOR_DELAY_GAME);
		}
		
		@Override
		public void onPause() {
			Log.i("","onPause");
			super.onPause();
		}
		
		@Override
		public void onStop() {    	
			Log.i("","onStop");
			super.onStop();
		
			/*sensorManager.unregisterListener(this,
			sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
			sensorManager.unregisterListener(this,
			sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE));*/
		}
		
		@Override
		public void onDestroy() {
			Log.i("","onDestroy");
			super.onDestroy();
		}
		
		/* -------------------------------------------------------------- */

    

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
	    synchronized (this) {
	        switch (event.sensor.getType()){
	        
	            case Sensor.TYPE_ACCELEROMETER:
	    			// assign directions
	    			float x=event.values[0];
	    			float y=event.values[1];
	    			float z=event.values[2];
	    			
	    			xAcc = x;
	    			yAcc = y;
	    			zAcc = z;
	    			
	    			xCoor.setText("X: "+x);
	    			yCoor.setText("Y: "+y);
	    			zCoor.setText("Z: "+z);
	    			
	    			/*AndroidViewPagerActivity.fragmentD.txtAccX.setText("X: "+x);
	    			AndroidViewPagerActivity.fragmentD.txtAccY.setText("Y: "+y);
	    			AndroidViewPagerActivity.fragmentD.txtAccZ.setText("Z: "+z);*/
	    			
	    			AndroidViewPagerActivity.fragmentD.TCP_updateAccX(x);
	    			AndroidViewPagerActivity.fragmentD.TCP_updateAccY(y);
	    			AndroidViewPagerActivity.fragmentD.TCP_updateAccZ(z);
	    			
	            break;
	            
	        case Sensor.TYPE_AMBIENT_TEMPERATURE:
		        	/*DispGyrValues.setText("Orientation X (Roll) :"+ Float.toString(event.values[2]) +"\n"+  
		                   "Orientation Y (Pitch) :"+ Float.toString(event.values[1]) +"\n"+  
		                   "Orientation Z (Yaw) :"+ Float.toString(event.values[0])); */
	        	float xgyr=event.values[0];
	        	
	        	DispGyrValues.setText("X: "+xgyr);
	        	
	        	
	        	
	        	//DispGyrValues.setText(("x:"+Float.toString(event.values[0])));
	        break;
	 
	        }
	    }
		
	}
	
	
	
	
	
	
	
	


}
