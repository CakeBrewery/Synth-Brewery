/**
 * Developer: Samuel Navarrete (CakeBrewery on GitHub) 
 * This is currently an API for sampling waveforms at
 * specific phase indexes (in radians)
 */

package com.cakebrewery.simplesynth;
import com.cakebrewery.simplesynth.MainActivity.WaveType;


public class Waveform {	
	
	//Wave configuration attributes
	int sampling_rate; 
	int amplitude;
	double phase_index;
	double phase_increment;
	double current_frequency;
	WaveType type;
	
	//Math pre-computations
	public final double twopi = 2*Math.PI;
	public final double pi = Math.PI;
	
	//Constructor
	Waveform(double frequency, int amplitude, WaveType type, int sampling_rate){
		this.current_frequency = frequency; 
		this.amplitude = amplitude; 
		this.sampling_rate = sampling_rate;
		this.phase_increment = twopi*this.current_frequency/this.sampling_rate;
		this.type = type; 	
	}
	
	//Math formula for the most simple sine wave
    private double sampleSine(){
    	return (double)(this.amplitude*Math.sin(this.phase_index)); 
    }
    
    //Math formula for a sawtooth wave
    private double sampleSaw(){
		double sample = (double)(((this.amplitude*this.phase_index) / pi)-1);
		if(this.phase_index >= twopi)
			this.phase_index -= twopi;
		
		return sample; 
    }
    
    //Math formula for a square wave
    private double sampleSquare(){ 
    	double sample;
		double midpoint = twopi * (0.5);
		
		if (this.phase_index >= twopi)
			this.phase_index -= twopi; //or phase = 0; 
		if (this.phase_index >= midpoint)
			sample = (-1*this.amplitude);
		else
			sample = (1*this.amplitude); 
		
    	return sample; 
    }
    
    //In the future this will allow the user to create his own formulas for waves
    private double sampleCustom(){
		//different wave
		double value = 0;

		for (int p = 1; p <=4; p++){
			value += Math.sin(this.phase_index*p) / p; 
		}
		
		if ((this.phase_index += this.phase_increment) >= twopi)
			this.phase_index -= twopi;	
			
		return (double)(this.amplitude*value/1.58);
    }
	
	/* Returns a sample of the waveform at the current phase index */
	public double getSample(){
		double sample; 
		switch(this.type){
		case SINE: 
			sample = sampleSine();
			break;
		case SQUARE:
			sample = sampleSquare(); 
			break; 
		case SAW:
			sample = sampleSaw();
			break; 
		case CUSTOM:
			sample = sampleCustom();
			break; 
		default:
			sample = sampleSine();
			break;
		}
		
		return sample; 			
	}	
	
	public void incPhaseIndex(){
		this.phase_index += this.phase_increment; 
		
		if(this.phase_index > twopi)
			this.phase_index = 0; 
	}
	
	public void setFrequency(double frequency){
		this.current_frequency = frequency; 
		this.phase_increment = twopi*this.current_frequency/this.sampling_rate;
	}
}
