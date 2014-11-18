package com.cakebrewery.simplesynth;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {
	
	public final int AMP_MAX = 18000; 
	public final int AMP_START = 18000; 
    public final double twopi = 2*Math.PI;
	
	//App Components
	Thread t; 
	boolean isRunning = true; 
	double sliderval;  
	SeekBar fSlider;
	ToggleButton powerbtn; 
	AudioTrack audioTrack; 

	//Wave parameters
	int sr = 44100; 
	double fundamental = 220; 
	int amp = 0; //initial amplitude is 0
    double ph = 0.0; 
    boolean envelope = false; 
	
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
		        		
		        		
		        		
		        		/*
		        		//different wave
		        		double value = 0;

		        		for (int p = 1; p <=4; p++){
		        			value += Math.sin(ph*p) / p; 
		        		}
		        		
		        		if ((ph += twopi*fr/sr) >= twopi)
		        			ph -= twopi;	
		        			
		        		samples[i] = (short)(amp*value/1.58);
		        		
		        		*/
		        			        	
		        		samples[i] = sampleSquare();
		        			        		
		        		//SawTooth
		        		/*
		        		samples[i] =(short)(((double)(amp*ph) / Math.PI)-1);
		        		if(ph >= twopi)
		        			ph -= twopi;
		        		*/
		        		
		        		//Square
		        		/*
		        		double midpoint = twopi * (0.5);
		        		if (ph >= twopi)
		        			ph -= twopi; //or phase = 0; 
		        		if (ph >= midpoint)
		        			samples[i] = (short)(-1*amp);
		        		else
		        			samples[i] = (short)(1*amp); 
		        		*/
		        		
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
