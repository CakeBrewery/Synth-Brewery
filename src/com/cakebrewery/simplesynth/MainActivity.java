package com.cakebrewery.simplesynth;

import java.util.Locale;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {
	
	//Options
	public final int AMP_MAX = 18000; 
	public final int AMP_START = 18000; 
    public final double twopi = 2*Math.PI;
	public final int sr = 44100;
	
	//App Components
	Thread t; 
	boolean isRunning = true; 
	double sliderval;  
	SeekBar fSlider;
	ToggleButton powerbtn; 
	AudioTrack audioTrack; 
	Spinner wf_selector; 

	//Wave parameters
	double fundamental = 220; 
	int amp = 0; //initial amplitude is 0
    
    //State Variables
    boolean envelope = false; 
    double ph = 0.0; 
    enum Waveform {
    	SINE, SQUARE, SAW, CUSTOM1
    }
    
    Waveform selected_waveform = Waveform.SINE;  
	
	//Envelope
	int envCount = 0; 
	int envIndex = -1; 
	int endVolume = 0; 
	
    public int updateEnvelope(int i, int amp_start, int amp_end, int current_amp, int duration){
    	int a = (amp_end - amp_start) / duration; 
    	current_amp = (i * a) + amp_start; 
    	
    	if (current_amp > AMP_MAX)
    		current_amp = AMP_MAX;
    	return current_amp; 
    }
    
    public short sampleSine(){
    	return (short)(amp*Math.sin(ph)); 
    }
    
    public short sampleSaw(){
		short sample = (short)(((double)(amp*ph) / Math.PI)-1);
		if(ph >= twopi)
			ph -= twopi;
		
		return sample; 
    }
    
    public short sampleSquare(){ 
    	short sample;
		double midpoint = twopi * (0.5);
		
		if (ph >= twopi)
			ph -= twopi; //or phase = 0; 
		if (ph >= midpoint)
			sample = (short)(-1*amp);
		else
			sample = (short)(1*amp); 
		
    	return sample; 
    }
    
    public short sampleCustom(double ph_increment){
		//different wave
		double value = 0;

		for (int p = 1; p <=4; p++){
			value += Math.sin(ph*p) / p; 
		}
		
		if ((ph += ph_increment) >= twopi)
			ph -= twopi;	
			
		return (short)(amp*value/1.58);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fundamental = 2616; 
        
        t = new Thread() {
        	public void run() {
        		//set process priority
        		setPriority(Thread.MAX_PRIORITY); 
        	
	        	//point the slider to the GUI widget
	        	fSlider = (SeekBar) findViewById(R.id.frequency);
	        	fSlider.setProgress((int)fundamental); 
	        	fSlider.incrementProgressBy(10);
	        	fSlider.setMax((int)fundamental*7); 
	        	
	        	//point power button to GUI widget
	        	powerbtn = (ToggleButton) findViewById(R.id.power);
	        	
	        	//point spinner element to GUI widget
	        	wf_selector = (Spinner) findViewById(R.id.wf_selector); 
	        	
	        	 	
	        	//Seekbar listener
	        	OnSeekBarChangeListener listener = new OnSeekBarChangeListener() {
	        		public void onStopTrackingTouch(SeekBar seekBar) { }
	        		public void onStartTrackingTouch(SeekBar seekBar) { }
	        		public void onProgressChanged(SeekBar seekBar, int progress,
	        										boolean fromUser){
	        			
	        			progress = progress/(int)fundamental;
	        			progress = progress*(int)fundamental+(int)fundamental; 
	        			
	        			if(fromUser) sliderval = progress/10;
	        		}
	        	};
	        	
   	
	        	// Set the listener on the slider
	        	fSlider.setOnSeekBarChangeListener(listener); 
	        
		        int buffsize = AudioTrack.getMinBufferSize(sr, AudioFormat.CHANNEL_OUT_MONO, 
		        												AudioFormat.ENCODING_PCM_16BIT);
		        
		        // Waveform Selector Listener
		        wf_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		        	public void onItemSelected(AdapterView<?> arg0, View arg1, 
		        			int position, long id) {
		        		wf_selector.setSelection(position);
		        		String selector_state = (String) wf_selector.getSelectedItem();
		        		selected_waveform = Waveform.valueOf(selector_state.toUpperCase(Locale.getDefault())); 			
		        	}

		        	public void onNothingSelected(AdapterView<?> arg0){
		        		selected_waveform = Waveform.SINE; 
		        	}
		        });
		        
		        
		        //Create an audiotrack object
		        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sr, 
		        										AudioFormat.CHANNEL_OUT_MONO,
		        										AudioFormat.ENCODING_PCM_16BIT,
		        										buffsize,
		        										AudioTrack.MODE_STREAM);
		        
	        	//Power Button listener
	        	powerbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
	        		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	        			if(isChecked){
	        				amp = AMP_START; 
	        				envelope = true; 
	        			} else {
	        				amp = 0; 
	        				ph = 0; 
	        				envIndex = -1; 
	        				envelope = false; 
	        			}
	        		}
	        	});
		             
		        short samples[] = new short[buffsize];
		        //int amp = 20000;

		        double fr = (double)fundamental; 

		        //start audio
		        audioTrack.play();
		        
		       	//Main Loop 
		        while(isRunning){
		        	fr = sliderval;	
		        	
		        	double ph_increment = twopi*fr/sr;
		        	for(int i=0; i < buffsize; i++){
		        		
		        		if (envelope){
			        		int envInc = 0;
			        		if(--envCount <= 0) {
			        			amp = endVolume; 
			        			if(++envIndex < AMP_MAX){
			        				endVolume = envIndex;
			        				envInc = endVolume - amp; 
			        				if(envCount > 0)
			        					envInc /= envCount; 
			        			} else {
			        				envInc = 0; 	
			        			}
			        		}
			        		else{
			        			amp += envInc; 
			        		}
		        		}
		        		
		        		//Obtain a sample from a waveform sample function
		        		switch(selected_waveform){
		        		case SINE:
		        			samples[i] = sampleSine(); 
		        			break;
		        		case SQUARE:
		        			samples[i] = sampleSquare();
		        			break;
		        		case SAW:
		        			samples[i] = sampleSaw();
		        			break; 
		        		case CUSTOM1:
		        			samples[i] = sampleCustom(ph);
		        			break;
		        		default: 
		        			samples[i] = sampleSine();
		        			break;
		        		}
		        		 
		        		
		        		//Increment phase index
		        		ph += ph_increment; 
		        		
		        	}
		        	audioTrack.write(samples,  0,  buffsize); 
		        }   
		        
		        //Finishing
		        audioTrack.stop();
		        audioTrack.release();
        	}
        };     
        t.start();
    }
    
    /*
    public void updateSamples(short[] samples, int amp, double ph){
    	for(int i = 0; i < buffersize; i++){
    		
    	}
    }*/
    
    public void onDestroy(){
    	super.onDestroy(); 
    	isRunning = false; 
    	try{
    		t.join();
    	} catch(InterruptedException e){
    		e.printStackTrace();
    	}
    	
    	t = null; 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
