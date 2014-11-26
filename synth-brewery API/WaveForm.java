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
    
    //This method tried to simulate a bell. 
    //Not works well. 
    private double sampleBell(){
    	double samples;
    	int i = (int)((phase_index*sampling_rate)/(double)(current_frequency*twopi));
    	double t = (double)i/(double)sampling_rate;
    	double w = 2 * pi * (double)current_frequency*t;
    	samples = Math.cos(w + 8*Math.sin(w * 7 /5) * Math.exp(-t*4));
    	samples *= Math.exp(-t*3);
    	samples *= amplitude*2;
    	return samples;
    }
    
    
    //This method tries to simulate a Flute sound.
    private double sampleFlute(){
    	double samples;
    	int i = (int)((phase_index*sampling_rate)/(double)(current_frequency*twopi));
    	double t = (double)i/(double)sampling_rate;
    	double w = 2 * pi * (double)current_frequency * t;
    	samples = (Math.sin(w) + 0.75 * Math.sin(w*3) + 0.5 * Math.sin(w * 5) + 0.14 * Math.sin(w *7) + 0.5 * Math.sin(w * 9) + 0.12 * Math.sin(w * 11) + 0.17 * Math.sin(w * 13)) / (1 + 0.75 + 0.5 + 0.14 + 0.17);
    	samples *= Math.exp(t/1.5); 
    	samples *= Math.exp(-1 * t * 1.5);
    	return samples;
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
		case BELL:
			sample = sampleBell();
			break;
		case FLUTE:
			sample = sampleFlute();
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
		case FLUTE:
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
