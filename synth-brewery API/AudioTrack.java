
public class AudioTrack
{
	private int sampling_rate;
	private WaveForm waveform1;
	private int sample_index; 
	private int samplesPerBeat = 0;
	private int[] samples;

	//State variables
	private double curr_freq;
	private double curr_bpm; 

	public byte[] getByteArray()
	{
		byte[] samples_in_bytes = new byte[samples.length*2]; 
		for (int i = 0; i < samples.length; i++){
			samples_in_bytes[i*2] = (byte) (samples[i] & 0xff);
			samples_in_bytes[(i*2)+1] = (byte) ((samples[i] >>8) & 0xff);
		}

		return samples_in_bytes; 
	}

	public void writeBeat(double freq){
		for(int i = 0; i < this.samplesPerBeat; i++){
			waveform1.setFrequency(freq); 
			waveform1.amplitude = 100; 

			samples[sample_index++] = (int)waveform1.getSample();
			waveform1.incPhaseIndex();
		}
	}

	AudioTrack(int sampling_rate, int num_beats, double bpm)
	{
		sample_index = 0; 
		this.curr_bpm = bpm; 
		this.sampling_rate = sampling_rate; 
		this.samplesPerBeat = (int)((sampling_rate*60)/bpm);

		samples = new int[num_beats*samplesPerBeat];
		waveform1 = new WaveForm(0, 0, WaveType.SINE, sampling_rate);

	}
}