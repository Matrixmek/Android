package com.exercise.AndroidViewPager;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragmentD extends Fragment {
	
	//Variables to send over TCP
		float ControlByAcc = 0;
		float ControlByBtn = 0;
		float ControlByVoice = 0;
		
		float AccX = 0;
		float AccY = 0;
		float AccZ = 0;
		
		float VoiceCmd = 0;
		float BtnCmd = 0;
		String NaoSpeech = "Waitting..";
		
		//Declare IP Address
		public String IPAddress;
		public String Port;
		
		public boolean connected = false;
		EditText edPort;
		EditText edAddress;
		private Button btnConnect;
		private Button btnValidIP;
		
		public TextView txtBtnControl;
		public TextView txtAccControl;
		public TextView txtVoiceControl;
		
		public TextView txtAccX;
		public TextView txtAccY;
		public TextView txtAccZ;
		
		public TextView txtVoiceCmd;
		public TextView txtNaoSpeech;
		public TextView txtBtnCmd;
		
		public TextView txtTCPIsConnected;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.fragment_d, container, false);
		
		/* -------------------------------------------------------------- */
				/* -- Tag from MainActivity -- */
		/* -------------------------------------------------------------- */  
		
		String TCPTagAccControl = getTag();
		
		((AndroidViewPagerActivity)getActivity()).setTagAccControl(TCPTagAccControl);
		
		
		/* -------------------------------------------------------------- */
				/* -- Button Initialization -- */
		/* -------------------------------------------------------------- */         
		
		btnConnect = (Button) myFragmentView.findViewById(R.id.btnConnect);
		btnConnect.setOnClickListener(NetworkTCPBtnListener);
		
		btnValidIP = (Button) myFragmentView.findViewById(R.id.btnValidAddress);
		btnValidIP.setOnClickListener(NetworkTCPBtnListener);
		
		/* -------------------------------------------------------------- */
				/* -- Edit Text Initialization -- */
		/* -------------------------------------------------------------- */ 
		
		txtBtnControl = (TextView)myFragmentView.findViewById(R.id.txtTCPControlByBtn);
		txtAccControl = (TextView)myFragmentView.findViewById(R.id.txtTCPControlByAcc);
		txtVoiceControl = (TextView)myFragmentView.findViewById(R.id.txtTCPControlByVoice);
		
		txtAccX = (TextView)myFragmentView.findViewById(R.id.txtTCPAccX);
		txtAccY = (TextView)myFragmentView.findViewById(R.id.txtTCPAccY);
		txtAccZ = (TextView)myFragmentView.findViewById(R.id.txtTCPAccZ);
		
		txtVoiceCmd = (TextView)myFragmentView.findViewById(R.id.txtTCPVoiceCmd);
		txtNaoSpeech = (TextView)myFragmentView.findViewById(R.id.txtTCPNaoSpeech);
		txtBtnCmd = (TextView)myFragmentView.findViewById(R.id.txtTCPBtnCmd);
		
		edAddress = (EditText)myFragmentView.findViewById(R.id.editip);
		edPort = (EditText)myFragmentView.findViewById(R.id.editPort);
		
		txtTCPIsConnected = (TextView)myFragmentView.findViewById(R.id.txtTCPIsConnected);
		
		return myFragmentView;
	}
	
		/* -------------------------------------------------------------- */
			/* -- OnClickListener-- */
		/* -------------------------------------------------------------- */ 	
		
		
		OnClickListener NetworkTCPBtnListener
		= new OnClickListener(){
		
		@Override
		public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.btnConnect:
		
		if (!IPAddress.equals("")) {
		
		//words = "Connecting to 192.168.0.6";
		//speakWords(words);
		
		Toast.makeText(getActivity(),"Thread running... ", Toast.LENGTH_SHORT).show();
		
		Thread cThread = new Thread(new ClientThread());
		cThread.start();
		}
		break;
		
		case R.id.btnValidAddress:
		
		if (!connected) {
		IPAddress = edAddress.getText().toString();
		Port = edPort.getText().toString();
		//Set Default IP Address
		if (IPAddress.equals("")){
		IPAddress = "192.168.1.119";
		}
		//Set Default PORT
		if (Port.equals("")){
		Port = "1234";
		}
		//IP Address Regex
		if (!IPAddress.matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
		Toast.makeText(getActivity(),"ERROR IP" + "-", Toast.LENGTH_SHORT).show();
		}
		}
		System.out.println("IP Address2:"+IPAddress);
		Toast.makeText(getActivity(),"IP Address: " +IPAddress, Toast.LENGTH_SHORT).show();
		Toast.makeText(getActivity(),"PORT: " +Port, Toast.LENGTH_SHORT).show();
		break;
		
		default:
		break;
		
		}
		}
		};
		
		/* -------------------------------------------------------------- */
		
		//--------------- Class in which we manage WiFi Connection and TCP/UDP sockets--------------------
		public class ClientThread extends Activity implements Runnable  {
		
		public void run() {
		try {
			InetAddress serverAddr = InetAddress.getByName(IPAddress);
			//Change String Port to int PORT
			int PORT = Integer.parseInt(Port);
		
			Log.d("ClientActivity", "C: Connecting...");
		
			//create a socket to make the connection with the server
			Socket socket = new Socket(serverAddr, PORT);
			
			connected = true;
			
		/*if(connected = true)
			{
				AndroidViewPagerActivity.fragmentD.txtTCPIsConnected.setText("Connected");
				AndroidViewPagerActivity.fragmentD.btnConnect.setText("Disconnect");
			}
		else AndroidViewPagerActivity.fragmentD.txtTCPIsConnected.setText("Disconnected");
		*/
		while (connected) {
		    try {
		        Log.d("ClientActivity", "C: Sending command.");
		        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
		                    .getOutputStream())), true);
		            // where you issue the commands
		        
		        	                 
		        	/*StringBuilder stringBuilder = new StringBuilder();
		        	
					stringBuilder.append("" +ControlByAcc);
					stringBuilder.append("," +ControlByBtn);
					stringBuilder.append("," +ControlByVoice);
					
		        	stringBuilder.append("," +xAcc);
					stringBuilder.append("," +yAcc);
					stringBuilder.append("," +zAcc);
					
					stringBuilder.append("," +BtnCmd);
					stringBuilder.append("," +VoiceCmd);
					
					
					
		        	if(VoiceCmd == '0')out.println("No Cmd Sent");
		        	else{
		        		out.println(VoiceCmd);
		        		
		        		if(BtnSendPushed == true)
		        			{
		        			stringBuilder.append("/ " +NaoSpeech);
		        			BtnSendPushed = false;
		        			}
		        		else
		        		{
		        			stringBuilder.append("/ Waitting...");
		        		}
		        	}
		
					String message = stringBuilder.toString();*/
		        	
		        	String message = "Hello";
		        	out.println(message);
		        	
		        	//BtnCmd = 0;
		        	//VoiceCmd = 0;
		        	                    
		
		            try { 
		            	  Thread.sleep(3000);
		            	}
		            	catch (InterruptedException exception) {
		            	  exception.printStackTrace();
		            	}
		            
		            Log.d("ClientActivity", "C: Sent.");
		    } catch (Exception e) {
		        Log.e("ClientActivity", "S: Error", e);
		    }
		}
		socket.close();
		Log.d("ClientActivity", "C: Closed.");
		} catch (Exception e) {
		Log.e("ClientActivity", "C: Error", e);
		connected = false;
		}
		}
		}
		
		/* -------------------------------------------------------------- */ 
		
		//Update Btn/Voice/Acc Control
		
		public void TCP_updateBtnControl(float f){
		ControlByBtn = f;
		
		if(ControlByBtn == 0)
		{
			txtBtnControl.setText("Control by Buttons: OFF");
		}
		else
		{
			txtBtnControl.setText("Control by Buttons: ON");
			ControlByAcc = 0;
			ControlByVoice = 0;
		}
		
		}
		
		public void TCP_updateAccControl(float f){
		ControlByAcc = f;
		
			if(ControlByAcc == 0)
			{
				txtAccControl.setText("Control by Accelerometer: OFF");
			}
			else
			{
				txtAccControl.setText("Control by Accelerometer: ON");
				ControlByBtn = 0;
				ControlByVoice = 0;
			}
		}
		
		public void TCP_updateVoiceControl(float f){
		ControlByVoice = f;
				
		if(ControlByVoice == 0)
		{
			txtVoiceControl.setText("Control by Voice: OFF");
		}
		else
		{
			txtVoiceControl.setText("Control by Voice: ON");
			ControlByBtn = 0;
			ControlByAcc = 0;
		}
		
		}
		
		//Update Accelerometer Values
		
		public void TCP_updateAccX(float f){
		AccX = f;
		txtAccX.setText("AccX: "+f);
		}
		
		public void TCP_updateAccY(float f){
		AccY = f;
		txtAccY.setText("AccY: "+f);
		}
		
		public void TCP_updateAccZ(float f){
		AccZ = f;
		txtAccZ.setText("AccZ: "+f);
		}
		
		//Update Btn/VoiceCommands Values
		
		public void TCP_updateVoiceCmd(float f){
		VoiceCmd = f;
		txtVoiceCmd.setText("Voice cmd: "+f);
		}
		
		public void TCP_updateNaoSpeech(String t){
		NaoSpeech = t;
		txtAccY.setText("Nao Speech: "+t);
		}
		
		public void TCP_updateBtnCmd(float f){
		BtnCmd = f;
		txtAccZ.setText("Button Cmd: "+f);
		}
		
	

}