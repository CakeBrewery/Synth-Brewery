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
	
	//Declarations
    public enum WaveType {
    	SINE, SQUARE, SAW, CUSTOM
    }
	
	//Options
	public final int AMP_MAX = 18000; 
	public final int AMP_START = 18000; 
    public final double twopi = 2*Math.PI;
    
	public final int sr = 44100;
	
	//App Components
	Thread t; 
	boolean isRunning = true; 
	
	double sliderval1, sliderval2, sliderval3;
	SeekBar fSlider1, fSlider2, fSlider3;
	ToggleButton powerbtn1, powerbtn2, powerbtn3;
	Spinner wf_selector1, wf_selector2, wf_selector3; 
	Waveform waveform1, waveform2, waveform3; 
	
	AudioTrack audioTrack;
	
	//Wave parameters
	double fundamental = 220; 
	int amp1, amp2, amp3 = 0; //initial amplitude is 0 
    WaveType selected_waveform1 = WaveType.SINE;  
    WaveType selected_waveform2 = WaveType.SINE;
    WaveType selected_waveform3 = WaveType.SINE;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fundamental = 261.6; 
        
        t = new Thread() {
        	public void run() {
        		//set process priority
        		setPriority(Thread.MAX_PRIORITY); 
        	
	        	//point the sliders to the GUI widgets
	        	fSlider1 = (SeekBar) findViewById(R.id.frequency1);
	        	fSlider1.setProgress((int)fundamental); 
	        	fSlider1.incrementProgressBy(10);
	        	fSlider1.setMax((int)fundamental*7); 
	        	
	        	fSlider2 = (SeekBar) findViewById(R.id.frequency2);
	        	fSlider2.setProgress((int)fundamental); 
	        	fSlider2.incrementProgressBy(10);
	        	fSlider2.setMax((int)fundamental*7);
	        	
	        	fSlider3 = (SeekBar) findViewById(R.id.frequency3);
	        	fSlider3.setProgress((int)fundamental); 
	        	fSlider3.incrementProgressBy(10);
	        	fSlider3.setMax((int)fundamental*7);
	        	
	        	//point power buttons to GUI widgets
	        	powerbtn1 = (ToggleButton) findViewById(R.id.power1);
	        	powerbtn2 = (ToggleButton) findViewById(R.id.power2);
	        	powerbtn3 = (ToggleButton) findViewById(R.id.power3);
	        	
	        	//point spinners to GUI widgets
	        	wf_selector1 = (Spinner) findViewById(R.id.wf_selector1); 
	        	wf_selector2 = (Spinner) findViewById(R.id.wf_selector2); 
	        	wf_selector3 = (Spinner) findViewById(R.id.wf_selector3); 
	        	
	        	//Waveforms
	        	waveform1 = new Waveform(fundamental, amp1, selected_waveform1, sr);
	        	waveform2 = new Waveform(fundamental, amp2, selected_waveform2, sr); 
	        	waveform3 = new Waveform(fundamental, amp3, selected_waveform3, sr); 

	        	//Seekbar listener
	        	OnSeekBarChangeListener listener1 = new OnSeekBarChangeListener() {
	        		public void onStopTrackingTouch(SeekBar seekBar) { }
	        		public void onStartTrackingTouch(SeekBar seekBar) { }
	        		public void onProgressChanged(SeekBar seekBar, int progress,
	        										boolean fromUser){
	        			
	        			progress = progress/(int)fundamental;
	        			progress = progress*(int)fundamental+(int)fundamental; 
	        			
	        			if(fromUser) sliderval1 = progress;
	        		}
	        	};
	        	
	        	OnSeekBarChangeListener listener2 = new OnSeekBarChangeListener() {
	        		public void onStopTrackingTouch(SeekBar seekBar) { }
	        		public void onStartTrackingTouch(SeekBar seekBar) { }
	        		public void onProgressChanged(SeekBar seekBar, int progress,
	        										boolean fromUser){
	        			
	        			progress = progress/(int)fundamental;
	        			progress = progress*(int)fundamental+(int)fundamental; 
	        			
	        			if(fromUser) sliderval2 = progress;
	        		}
	        	};
	        	
	        	OnSeekBarChangeListener listener3 = new OnSeekBarChangeListener() {
	        		public void onStopTrackingTouch(SeekBar seekBar) { }
	        		public void onStartTrackingTouch(SeekBar seekBar) { }
	        		public void onProgressChanged(SeekBar seekBar, int progress,
	        										boolean fromUser){
	        			
	        			progress = progress/(int)fundamental;
	        			progress = progress*(int)fundamental+(int)fundamental; 
	        			
	        			if(fromUser) sliderval3 = progress;
	        		}
	        	};
	        	
   	
	        	// Set the listener on the slider
	        	fSlider1.setOnSeekBarChangeListener(listener1); 
	        	fSlider2.setOnSeekBarChangeListener(listener2); 
	        	fSlider3.setOnSeekBarChangeListener(listener3); 
	        
		        int buffsize = AudioTrack.getMinBufferSize(sr, AudioFormat.CHANNEL_OUT_MONO, 
		        												AudioFormat.ENCODING_PCM_16BIT);
		        
		        // Waveform Selector Listener
		        wf_selector1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		        	public void onItemSelected(AdapterView<?> arg0, View arg1, 
		        			int position, long id) {
		        		wf_selector1.setSelection(position);
		        		String selector_state = (String) wf_selector1.getSelectedItem();
		        		selected_waveform1 = WaveType.valueOf(selector_state.toUpperCase(Locale.getDefault()));
		        		
		        	}

		        	public void onNothingSelected(AdapterView<?> arg0){
		        		selected_waveform1 = WaveType.SINE; 
		        	}
		        });
		        
		        wf_selector2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		        	public void onItemSelected(AdapterView<?> arg0, View arg1, 
		        			int position, long id) {
		        		wf_selector2.setSelection(position);
		        		String selector_state = (String) wf_selector2.getSelectedItem();
		        		selected_waveform2 = WaveType.valueOf(selector_state.toUpperCase(Locale.getDefault()));
		        		
		        	}

		        	public void onNothingSelected(AdapterView<?> arg0){
		        		selected_waveform2 = WaveType.SINE; 
		        	}
		        });
		        
		        wf_selector3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		        	public void onItemSelected(AdapterView<?> arg0, View arg1, 
		        			int position, long id) {
		        		wf_selector3.setSelection(position);
		        		String selector_state = (String) wf_selector3.getSelectedItem();
		        		selected_waveform3 = WaveType.valueOf(selector_state.toUpperCase(Locale.getDefault()));
		        		
		        	}

		        	public void onNothingSelected(AdapterView<?> arg0){
		        		selected_waveform3 = WaveType.SINE; 
		        	}
		        });
		        
		        
		        
		        //Create an audiotrack object
		        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sr, 
		        										AudioFormat.CHANNEL_OUT_STEREO,
		        										AudioFormat.ENCODING_PCM_16BIT,
		        										buffsize,
		        										AudioTrack.MODE_STREAM);
		        
	        	//Power Button listener
	        	powerbtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
	        		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	        			if(isChecked){
	        				amp1 = AMP_START;
	        				//envelope = true; 
	        			} else {
	        				amp1 = 0; 
	        				//ph = 0; 
	        				//envIndex = -1; 
	        				//envelope = false; 
	        			}
	        		}
	        	});
	        	
	        	powerbtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
	        		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	        			if(isChecked){
	        				amp2 = AMP_START;
	        				//envelope = true; 
	        			} else {
	        				amp2 = 0; 
	        				//ph = 0; 
	        				//envIndex = -1; 
	        				//envelope = false; 
	        			}
	        		}
	        	});
	        	
	        	powerbtn3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
	        		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	        			if(isChecked){
	        				amp3 = AMP_START;
	        				//envelope = true; 
	        			} else {
	        				amp3 = 0; 
	        				//ph = 0; 
	        				//envIndex = -1; 
	        				//envelope = false; 
	        			}
	        		}
	        	});
		             
		        short samples[] = new short[buffsize];
		        //int amp = 20000;
		        
		       
		        //start audio
		        audioTrack.play();
		        
		       	//Main Loop 
		        while(isRunning){
		        	waveform1.setFrequency(sliderval1);
		        	waveform1.type = selected_waveform1; 
		        	waveform1.amplitude = amp1; 
		        	
		        	waveform2.setFrequency(sliderval2);
		        	waveform2.type = selected_waveform2; 
		        	waveform2.amplitude = amp2; 
		        	
		        	waveform3.setFrequency(sliderval3);
		        	waveform3.type = selected_waveform3; 
		        	waveform3.amplitude = amp3; 
		        	
		        	//third.setFrequency(waveform.current_frequency*(5/3));
		        	//third.type = selected_waveform;
		        	//third.amplitude = amp; 
		        	
		        	for(int i=0; i < buffsize; i++){
		        		samples[i] = (short)((waveform1.getSample()
		        					+waveform2.getSample()+waveform3.getSample())/3);
		        					//-waveform.getSample()*fifth.getSample());
		        		waveform1.incPhaseIndex();  	
		        		waveform2.incPhaseIndex(); 
		        		waveform3.incPhaseIndex(); 
		        		//third.incPhaseIndex(); 
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
