/*
AudioTrack API developed by
Samuel Navarrete (CakeBrewery on GitHub)
This API allows the creation of an audio
track that performs calculatons for samples per beat
and such. More documentation coming soon */

public class AudioTrack
{
	private int sampling_rate;
	private WaveForm waveform1;
	private int sample_index; 
	private int samplesPerBeat = 0;
	public short[] samples;

	//State variables
	private double curr_freq; //Current frequency of the track
	private double curr_bpm;  //Current bpm of the track

	/* In order to play sounds in java,
		the stream must be reproduced in an array of single
		bytes (8 bits). Since the API produces the samples
		in short we have to convert them to 2 consecutive bytes
		in the stream */
	public byte[] getByteArray(){
		byte[] samples_in_bytes = new byte[samples.length*2]; 
		for (int i = 0; i < samples.length; i++){
			samples_in_bytes[i*2] = (byte) (samples[i] & 0xff);
			samples_in_bytes[(i*2)+1] = (byte) ((samples[i] >>8) & 0xff);
		}

		return samples_in_bytes; 
	}

	public short[] getShortArray(){
		return this.samples; 
	}


	/* This method writes a quarter note */
	public void writeHalfBeat(double freq, int volume){
		waveform1.setFrequency(freq); 
		waveform1.amplitude = volume; 
		int envelope = 2000;

		//Applying starting envelope
		for(int i = 0; i < envelope; i++){
			samples[sample_index++] = (short)(waveform1.getSample()*((double)i/envelope));
			waveform1.incPhaseIndex(); 
		}

		for(int i = envelope; i < this.samplesPerBeat/2-envelope ; i++){
			samples[sample_index++] = (short)waveform1.getSample();
			waveform1.incPhaseIndex();
		}

		//Applying ending envelope
		for(int i = 0; i < envelope; i++){
			samples[sample_index++] = (short)(waveform1.getSample()*(1-(double)i/envelope));
			waveform1.incPhaseIndex(); 
		}
	}

	/* This method writes a single beat or quarter note (for example there would 
		4 beats on a 4:4 measure) to the current audio track */
	public void writeBeat(double freq, int volume){
		waveform1.setFrequency(freq); 
		waveform1.amplitude = volume; 
		int envelope = 2000; 

		//Applying starting envelope
		for(int i = 0; i < envelope; i++){
			samples[sample_index++] = (short)(waveform1.getSample()*((double)i/envelope));
			waveform1.incPhaseIndex(); 
		}

		for(int i = envelope; i < this.samplesPerBeat-envelope ; i++){
			samples[sample_index++] = (short)waveform1.getSample();
			waveform1.incPhaseIndex();
		}

		//Applying ending envelope
		for(int i = 0; i < envelope; i++){
			samples[sample_index++] = (short)(waveform1.getSample()*(1-(double)i/envelope));
			waveform1.incPhaseIndex(); 
		}
	}

	int getLength(){
		return samples.length; 
	}

	AudioTrack(int sampling_rate, int length, double bpm, WaveType wavetype){
		sample_index = 0;  
		this.curr_bpm = bpm; 
		this.sampling_rate = sampling_rate; 
		this.samplesPerBeat = (int)((sampling_rate*60)/bpm);

		samples = new short[length*samplesPerBeat];

		//Instantiate a new Waveform to produce sound
		waveform1 = new WaveForm(0, 0, wavetype, sampling_rate);
	}
}
