package com.exercise.AndroidViewPager;

import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragmentB extends Fragment {
	
	public float ControlByVoice;
	
	public float VoiceCmd = (float) 0.0;
	public boolean BtnSendPushed = false;
	public String NaoSpeech = "";
	
	public TextView DispSpeechControlStatus;
	public TextView DispSpeechControlONOFF;
	
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

    private EditText metTextHint;
    private ListView mlvTextMatches;
    private Spinner msTextMatches;
    private ImageButton mbtSpeak;
    private Button mbtSend;
    private Button mbtActSpeech;
    private Button mbtDisSpeech;
    private ArrayList<String> textMatchList;
    
	/* -------------------------------------------------------------- */
					/* -- OnCreate -- */
    /* -------------------------------------------------------------- */ 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.fragment_b, container, false);
		
		/* -------------------------------------------------------------- */
				/* -- Speech Recognition -- */
		/* -------------------------------------------------------------- */ 
		
		DispSpeechControlStatus = (TextView)myFragmentView.findViewById(R.id.txtSpeechControlStatus);
		DispSpeechControlONOFF = (TextView)myFragmentView.findViewById(R.id.txtSpeechControlONOFF);
		
		metTextHint = (EditText)myFragmentView.findViewById(R.id.etTextHint);
		mlvTextMatches = (ListView) myFragmentView.findViewById(R.id.lvTextMatches);
		msTextMatches = (Spinner) myFragmentView.findViewById(R.id.sNoOfMatches);
		mbtSpeak = (ImageButton) myFragmentView.findViewById(R.id.btSpeak);
		mbtSend = (Button) myFragmentView.findViewById(R.id.btSend);
		
		mbtActSpeech = (Button) myFragmentView.findViewById(R.id.btnActSpeechReco);
		mbtActSpeech.setOnClickListener(SpeechRecognition);
		
		mbtDisSpeech = (Button) myFragmentView.findViewById(R.id.btnDisSpeechReco);
		mbtDisSpeech.setOnClickListener(SpeechRecognition);
		
		mbtSpeak = (ImageButton) myFragmentView.findViewById(R.id.btSpeak);
		mbtSpeak.setOnClickListener(SpeechRecognition);
		
		mbtSend = (Button) myFragmentView.findViewById(R.id.btSend);
		mbtSend.setOnClickListener(SpeechRecognition);
		
		
		PackageManager pm = getActivity().getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
		        RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		
		if (activities.size() == 0) {
		mbtSpeak.setEnabled(false);
		Toast.makeText(getActivity(), "Voice recognizer not present",
		                Toast.LENGTH_SHORT).show();
		}
		
		/* -------------------------------------------------------------- */ 

		return myFragmentView;
	}
	
	
		/* -------------------------------------------------------------- */
						/* -- OnClickListener -- */
		/* -------------------------------------------------------------- */ 
		
		OnClickListener SpeechRecognition
		= new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			
		    case R.id.btnDisSpeechReco:
		    	
		    	//words = "Speech disabled";
		        //speakWords(words);
		        
		        DispSpeechControlONOFF.setText("OFF");
		        //DispAccONOFF.setText("OFF");
		        
		        AndroidViewPagerActivity.fragmentD.TCP_updateBtnControl(1);
		        
		    	ControlByVoice= 0;
		    	//ControlByAcc = 0;
		    	//ControlByBtn = 1;
		        Toast.makeText(getActivity(), "Speech Recognition disabled" + "+", Toast.LENGTH_SHORT).show();
		        break;
		        
		    case R.id.btSend:
		    	
		    	BtnSendPushed = true;
		    	
		        Toast.makeText(getActivity(), "Msg sent" + "+", Toast.LENGTH_SHORT).show();
		        break;
		        
		    case R.id.btnActSpeechReco:
		    	
		    	//words = "Speech recognition control activated";
		        //speakWords(words);
		        
		        DispSpeechControlONOFF.setText("ON");
		        //DispAccONOFF.setText("OFF");
		    	
		        AndroidViewPagerActivity.fragmentD.TCP_updateBtnControl(1);
		        
		        ControlByVoice= 1;
		    	//ControlByAcc = 0;
		    	//ControlByBtn = 0;
		        Toast.makeText(getActivity(), "Speech Recognition activated" + "+", Toast.LENGTH_SHORT).show();
		        break;
			
		    case R.id.btSpeak:
		        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		        // Specify the calling package to identify your application
		        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
		                        .getPackage().getName());
		
		        // Display an hint to the user about what he should say.
		        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, metTextHint.getText()
		                        .toString());
		
		        // Given an hint to the recognizer about what the user is going to say
		        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
		                        RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		
		        // If number of Matches is not selected then return show toast message
		        if (msTextMatches.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
		                Toast.makeText(getActivity(), "Please select No. of Matches from spinner",
		                                Toast.LENGTH_SHORT).show();
		                return;
		        }
		
		        int noOfMatches = Integer.parseInt(msTextMatches.getSelectedItem()
		                        .toString());
		        // Specify how many results you want to receive. The results will be
		        // sorted where the first result is the one with higher confidence.
		
		        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);
		
		        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		        break;
		        
		    default:
		        break;
			
			}
		        
		}
		
		};
		
		
		public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getActivity().getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
		                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
		        mbtSpeak.setEnabled(false);
		        Toast.makeText(getActivity(), "Voice recognizer not present",
		                        Toast.LENGTH_SHORT).show();
		}
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
			
		        //If Voice recognition is successful then it returns RESULT_OK
		        if(resultCode == getActivity().RESULT_OK) {
		
		                textMatchList = data
		                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		                
		                for(int i=0;i<textMatchList.size();i++){
		                	
		                	if(textMatchList.get(i).equals("avance")){
		                		Toast.makeText(getActivity(),"Advance!",2000).show();
		                		//AndroidViewPagerActivity.fragmentD.VoiceCmd = 1;
		                			VoiceCmd = 1;
		                		break;
		                	}
		                	if(textMatchList.get(i).equals("forward")){
		                		Toast.makeText(getActivity(),"Advance!",2000).show();
		                			VoiceCmd = 1;
		                			//AndroidViewPagerActivity.fragmentD.VoiceCmd = 1;
		                		break;
		                	}
		                	if(textMatchList.get(i).equals("stop")){
		                		Toast.makeText(getActivity(),"Stop!",2000).show();
		                		VoiceCmd = 0;
		                		//AndroidViewPagerActivity.fragmentD.VoiceCmd = 0;
		                		break;
		                	}
		                	if(textMatchList.get(i).equals("left")){
		                		Toast.makeText(getActivity(),"Left!",2000).show();
		                			VoiceCmd = 2;
		                			//AndroidViewPagerActivity.fragmentD.VoiceCmd = 2;
		                		break;
		                	}
		                	if(textMatchList.get(i).equals("gauche")){
		                		Toast.makeText(getActivity(),"Left!",2000).show();
		                		VoiceCmd = 3;
		                		//AndroidViewPagerActivity.fragmentD.VoiceCmd = 3;
		                		break;
		                	}
		                	if(textMatchList.get(i).equals("Right")){
		                		Toast.makeText(getActivity(),"Right!",2000).show();
		                			VoiceCmd = 4;
		                			//AndroidViewPagerActivity.fragmentD.VoiceCmd = 4;
		                		break;
		                	}
		                	if(textMatchList.get(i).equals("droite")){
		                		Toast.makeText(getActivity(),"Right!",2000).show();
		                		VoiceCmd = 5;
		                		//AndroidViewPagerActivity.fragmentD.VoiceCmd = 5;
		                		break;
		                	}
		                	else
		                	{
		                		NaoSpeech = textMatchList.get(i).toString();
		                		//AndroidViewPagerActivity.fragmentD.NaoSpeech = NaoSpeech;
		                	}
		                }
		                
		
		                if (!textMatchList.isEmpty()) {
		                        // If first Match contains the 'search' word
		                        // Then start web search.
		                        if (textMatchList.get(0).contains("search")) {
		
		                                String searchQuery = textMatchList.get(0).replace("search",
		                                " ");
		                                Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
		                                search.putExtra(SearchManager.QUERY, searchQuery);
		                                startActivity(search);
		                        } else {
		                                // populate the Matches and get the results
		                                mlvTextMatches.setAdapter(new ArrayAdapter<String>(getActivity(),
		                                                android.R.layout.simple_list_item_1,
		                                                textMatchList));
		                                metTextHint.setText(textMatchList.get(0));
		                        }
		
		                }
		        //Result code for various error.        
		        }else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
		                showToastMessage("Audio Error");
		        }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
		                showToastMessage("Client Error");
		        }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
		                showToastMessage("Network Error");
		        }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
		                showToastMessage("No Match");
		        }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
		                showToastMessage("Server Error");
		        }
		super.onActivityResult(requestCode, resultCode, data);
		}
		
		/* -------------------------------------------------------------- */ 
		
		void showToastMessage(String message)
		{
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
		
		/* -------------------------------------------------------------- */ 

	

}
