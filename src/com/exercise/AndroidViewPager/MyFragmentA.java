package com.exercise.AndroidViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragmentA extends Fragment {
	
	public Button mbtActBtn;
	public Button mbtDisBtn;
	public ImageButton NaoFwd;
	public ImageButton NaoBwd;
	public ImageButton NaoLeft;
	public ImageButton NaoRight;
	public ImageButton NaoRotateL;
	public ImageButton NaoRotateR;
	
	//Declare the Motor control boolean
	public float ControlByBtn;	
	public float BtnCmd = (float) 0.0;
	
	public TextView DispBtnControlStatus;
	public TextView DispBtnControlONOFF;
	
	
	//JOYSTICK Variables
	RelativeLayout layout_joystick;
	ImageView image_joystick, image_border;
	TextView textView1, textView2, textView3, textView4, textView5;
	
	JoyStickClass js;
	
	public MyFragmentA(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.fragment_a, container, false);
		
		/* -------------------------------------------------------------- */
				/* -- JoyStick Initialization -- */
		/* -------------------------------------------------------------- */ 
		
		textView1 = (TextView)myFragmentView.findViewById(R.id.textViewJoystick1);
		textView2 = (TextView)myFragmentView.findViewById(R.id.textViewJoystick2);
		textView3 = (TextView)myFragmentView.findViewById(R.id.textViewJoystick3);
		textView4 = (TextView)myFragmentView.findViewById(R.id.textViewJoystick4);
		textView5 = (TextView)myFragmentView.findViewById(R.id.textViewJoystick5);
		
		layout_joystick = (RelativeLayout)myFragmentView.findViewById(R.id.layout_joystick);
		
		js = new JoyStickClass(getActivity()
		, layout_joystick, R.drawable.image_button);
		js.setStickSize(150, 150);
		js.setLayoutSize(500, 500);
		js.setLayoutAlpha(150);
		js.setStickAlpha(100);
		js.setOffset(90);
		js.setMinimumDistance(50);
		
		layout_joystick.setOnTouchListener(new OnTouchListener() {
		public boolean onTouch(View arg0, MotionEvent arg1) {
		js.drawStick(arg1);
		if(arg1.getAction() == MotionEvent.ACTION_DOWN
				|| arg1.getAction() == MotionEvent.ACTION_MOVE) {
			textView1.setText("X : " + String.valueOf(js.getX()));
			textView2.setText("Y : " + String.valueOf(js.getY()));
			textView3.setText("Angle : " + String.valueOf(js.getAngle()));
			textView4.setText("Distance : " + String.valueOf(js.getDistance()));
			
			int direction = js.get8Direction();
			if(direction == JoyStickClass.STICK_UP) {
				textView5.setText("Direction : Up");
			} else if(direction == JoyStickClass.STICK_UPRIGHT) {
				textView5.setText("Direction : Up Right");
			} else if(direction == JoyStickClass.STICK_RIGHT) {
				textView5.setText("Direction : Right");
			} else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
				textView5.setText("Direction : Down Right");
			} else if(direction == JoyStickClass.STICK_DOWN) {
				textView5.setText("Direction : Down");
			} else if(direction == JoyStickClass.STICK_DOWNLEFT) {
				textView5.setText("Direction : Down Left");
			} else if(direction == JoyStickClass.STICK_LEFT) {
				textView5.setText("Direction : Left");
			} else if(direction == JoyStickClass.STICK_UPLEFT) {
				textView5.setText("Direction : Up Left");
			} else if(direction == JoyStickClass.STICK_NONE) {
				textView5.setText("Direction : Center");
			}
		} else if(arg1.getAction() == MotionEvent.ACTION_UP) {
			textView1.setText("X :");
			textView2.setText("Y :");
			textView3.setText("Angle :");
			textView4.setText("Distance :");
			textView5.setText("Direction :");
		}
		return true;
		}
		});    
		
		/* -------------------------------------------------------------- */
					/* -- Button Initialization -- */
		/* -------------------------------------------------------------- */ 
		
		DispBtnControlStatus=(TextView)myFragmentView.findViewById(R.id.txtBtnControlStatus);
		DispBtnControlONOFF=(TextView)myFragmentView.findViewById(R.id.txtBtnControlONOFF);
		
		mbtActBtn = (Button) myFragmentView.findViewById(R.id.btnStartButtons);
		mbtActBtn.setOnClickListener(BtnListener); 
		
		mbtDisBtn = (Button) myFragmentView.findViewById(R.id.btnStopButtons);
		mbtDisBtn.setOnClickListener(BtnListener);
		
		NaoFwd = (ImageButton) myFragmentView.findViewById(R.id.btnNaoFwd);
		NaoFwd.setOnClickListener(BtnListener); 
		
		NaoBwd = (ImageButton) myFragmentView.findViewById(R.id.btnNaoBwd);
		NaoBwd.setOnClickListener(BtnListener);
		
		NaoLeft = (ImageButton) myFragmentView.findViewById(R.id.btnNaoLeft);
		NaoLeft.setOnClickListener(BtnListener);
		
		NaoRight = (ImageButton) myFragmentView.findViewById(R.id.btnNaoRight);
		NaoRight.setOnClickListener(BtnListener);
		
		NaoRotateL = (ImageButton) myFragmentView.findViewById(R.id.btnNaoRotateL);
		NaoRotateL.setOnClickListener(BtnListener);
		
		NaoRotateR = (ImageButton) myFragmentView.findViewById(R.id.btnNaoRotateR);
		NaoRotateR.setOnClickListener(BtnListener);
		
		
		return myFragmentView;
	}
	
	
	OnClickListener BtnListener
	= new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			
			 case R.id.btnNaoFwd:
		        	
		        	BtnCmd = 1;
		        	AndroidViewPagerActivity.fragmentD.TCP_updateBtnCmd(1);
		        	
		        	//words = "Forward";
		            //speakWords(words);
		        	
		            Toast.makeText(getActivity(), "Nao Forward" + "+", Toast.LENGTH_SHORT).show();
		            break;
		            
		        case R.id.btnNaoLeft:
		        	
		        	BtnCmd = 2;
		        	AndroidViewPagerActivity.fragmentD.TCP_updateBtnCmd(2);
		        	
		        	//words = "Footstep Left";
		            //speakWords(words);
		        	
		            Toast.makeText(getActivity(), "Nao Left" + "+", Toast.LENGTH_SHORT).show();
		            break;
		            
		        case R.id.btnNaoBwd:
		        	
		        	BtnCmd = 3;
		        	AndroidViewPagerActivity.fragmentD.TCP_updateBtnCmd(3);
		        	
		        	//words = "Back";
		            //speakWords(words);
		            
		            Toast.makeText(getActivity(), "Nao Backward" + "+", Toast.LENGTH_SHORT).show();
		            break;
		            
		        case R.id.btnNaoRight:
		        	
		        	BtnCmd = 4;
		        	AndroidViewPagerActivity.fragmentD.TCP_updateBtnCmd(4);
		        	
		        	//words = "Footstep right";
		            //speakWords(words);
		        	
		            Toast.makeText(getActivity(), "Nao Right" + "+", Toast.LENGTH_SHORT).show();
		            break;
		            
		        case R.id.btnNaoRotateL:
		        	
		        	BtnCmd = 5;
		        	AndroidViewPagerActivity.fragmentD.TCP_updateBtnCmd(5);
		        	
		        	//words = "Rotate Left";
		            //speakWords(words);
		        	
		            Toast.makeText(getActivity(), "Nao Rotate Left 10°" + "+", Toast.LENGTH_SHORT).show();
		            break;
		            
		        case R.id.btnNaoRotateR:
		        	
		        	BtnCmd = 6;
		        	AndroidViewPagerActivity.fragmentD.TCP_updateBtnCmd(6);
		        	
		        	//words = "Rotate Right";
		            //speakWords(words);
		        	
		            Toast.makeText(getActivity(), "Nao Rotate Right 10°" + "+", Toast.LENGTH_SHORT).show();
		            break;
		            
		        case R.id.btnStartButtons:
		        	
		        	//words = "Buttons control activated";
		            //speakWords(words);
		            
		            DispBtnControlONOFF.setText(""+ControlByBtn);
		            //DispAccONOFF.setText("OFF");
		            
		            AndroidViewPagerActivity.fragmentD.TCP_updateBtnControl(1);
		            
		        	//ControlByVoice= 0;
		        	//ControlByAcc = 0;
		        	ControlByBtn = 1;
		            Toast.makeText(getActivity(), "Buttons control activated" + "+", Toast.LENGTH_SHORT).show();
		            break;
		            
		        case R.id.btnStopButtons:
		        	
		        	//words = "Buttons control disactivated";
		            //speakWords(words);
		            
		            DispBtnControlONOFF.setText(""+ControlByBtn);
		            //DispAccONOFF.setText("ON");
		            
		            AndroidViewPagerActivity.fragmentD.TCP_updateBtnControl(0);
		            
		        	//ControlByVoice = 0;
		        	//ControlByAcc = 1;
		        	ControlByBtn = 0;
		            Toast.makeText(getActivity(), "Buttons control disactivated" + "+", Toast.LENGTH_SHORT).show();
		            break;
	            
	            
	            default:
	            	break;
	            
			}

		}
		
	};
	
	

}
