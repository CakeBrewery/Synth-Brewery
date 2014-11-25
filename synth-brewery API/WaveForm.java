/**
 * Developer: Samuel Navarrete (CakeBrewery on GitHub) 
 * This is currently an API for sampling waveforms at
 * specific phase indexes (in radians)
 */

public class WaveForm
{	
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
	WaveForm(double frequency, int amplitude, WaveType type, int sampling_rate){
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

    //This math formula tries to digitally emulate a violin
    /* how it works: 
    	see http://js.do/blog/sound-waves-with-javascript/
    	The sound is a sum of harmonics with decreasing
    	amplitudes
    */
    private double sampleViolin(){
    	int i = (int)((phase_index*sampling_rate)/(double)(current_frequency*twopi));
    	double t = (double)i/(double)sampling_rate;

    	double sample; 
    	double  y = 0; 
    	double A_total = 0; 

    	for(int harm = 1; harm < 7; harm++){
    		double f2 = (double)current_frequency*(double)harm;
    		double A = (double)1/harm; 
   			A_total += (double)A;
   			y += A*Math.sin(f2*twopi*t);
    	}

    	sample = y/A_total;
    	sample *= (1-0.5*Math.sin(twopi*6*t));
    	sample *= (1-Math.exp(-(t*3)));
    	sample *= amplitude*2;

    	return sample; 
    }
    
    //In the future this will allow the user to create his own formulas for waves
    //They just have to override the following two methods with their own math
    private double sampleCustom(){return -1;}
    public void incPhaseIndexCustom(){}
	
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
		case VIOLIN:
			sample = sampleViolin();
			break; 
		default:
			sample = sampleSine();
			break;
		}
		
		return sample; 			
	}	

	//This function increases the wave's sample offset (or phase index)
	public void incPhaseIndex(){
		switch(this.type){

		case CUSTOM: 
			incPhaseIndexCustom(); 
			break;

		case VIOLIN:
			this.phase_index += this.phase_increment;
			break;	
		default:
			this.phase_index += this.phase_increment; 
		
			if(this.phase_index > twopi)
				this.phase_index = 0; 
			break;
		}
	}
	
	//Set the frequency and change parameters accordingly
	public void setFrequency(double frequency){
		this.current_frequency = frequency; 
		this.phase_increment = twopi*this.current_frequency/this.sampling_rate;
	}
}
