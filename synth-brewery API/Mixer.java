import java.util.*;
import javax.sound.sampled.*; 


class Mixer
{
	List<AudioTrack> audiotracks; 
	List<Integer> offset_table;
	private int sample_index;
	private int length; 
	private short samples[]; 
	private double bpm; 
	private int sampling_rate;
	private int total_samples; 

	public Mixer(int length, double bpm, int sampling_rate){
		audiotracks = new ArrayList<AudioTrack>();
		offset_table = new ArrayList<Integer>(); 
		this.length = length; 
		this.bpm = bpm; 
		this.sampling_rate = sampling_rate;

		//this is equal to samplesPerBeat*NumberOfBeats
		total_samples = (int)(length*sampling_rate*60/bpm);
		samples = new short[total_samples];

		//Initiate all samples to zero 
		for(int i = 0; i < this.length; i++){
			samples[i] = 0; 
		}
	}

	//Add an audio track to this mixer that starts at sample_offset
	public void addTrack(AudioTrack audiotrack, int sample_offset){
		audiotracks.add(audiotrack);
		offset_table.add(sample_offset);
	}


	//Writes an array of short with all the samples of the mixed tracks. 
	//It also returns such array
	public short[] getShortArray(){

		for(int i=0; i < total_samples; i++){
			for(int j=0; j < audiotracks.size(); j++){
				short sample = audiotracks.get(j).samples[i]; 
				samples[i] = (short)(samples[i]+sample);
			}
			samples[i] /= audiotracks.size(); 
		}

		return samples;
	}

	//Obtains an array of the mixed tracks 
	public byte[] getByteArray(){
		getShortArray(); 

		byte[] samples_in_bytes = new byte[samples.length*2]; 
		for (int i = 0; i < samples.length; i++){
			samples_in_bytes[i*2] = (byte) (samples[i] & 0xff);
			samples_in_bytes[(i*2)+1] = (byte) ((samples[i] >>8) & 0xff);
		}
		return samples_in_bytes; 
	}

	public int getLength(){
		return length; 
	}

	public void play(){
		//Using standard java libraries for sound playback
		try{
			//instantiate javax.sound.sampled structures for playback
			AudioFormat audioFormat = new AudioFormat(this.sampling_rate, 16, 1, true, false);
			SourceDataLine dataLine = AudioSystem.getSourceDataLine(audioFormat);
			dataLine.open(audioFormat);
			dataLine.start(); 

			//Obtain the samples array of track1 in bytes
			byte[] mixer_bytes = this.getByteArray(); 
			
			//This writes the audio to the audio buffer and plays it
			System.out.println("Playing: Writing to audio Buffer..");

			int i = 0; 
			while(i < mixer_bytes.length){
				byte[] temp = new byte[this.sampling_rate];
				for(int j = 0; j < this.sampling_rate; j++){		
					if (i >= mixer_bytes.length){
						break;
					}
					temp[j] = mixer_bytes[i++];
				}
				dataLine.write(temp, 0, temp.length);
			}

		} catch (Exception e){
			e.printStackTrace(); 
		}
	}

	public void playFrom(int starting_beat){
		int samplesPerBeat = (int)((sampling_rate*60)/bpm);
		//Using standard java libraries for sound playback
		try{
			//instantiate javax.sound.sampled structures for playback
			AudioFormat audioFormat = new AudioFormat(this.sampling_rate, 16, 1, true, false);
			SourceDataLine dataLine = AudioSystem.getSourceDataLine(audioFormat);
			dataLine.open(audioFormat);
			dataLine.start(); 

			//Obtain the samples array of track1 in bytes
			byte[] mixer_bytes = this.getByteArray(); 
			
			//This writes the audio to the audio buffer and plays it
			System.out.println("Playing: Writing to audio Buffer..");

			int i = starting_beat*samplesPerBeat; 
			while(i < mixer_bytes.length){
				byte[] temp = new byte[this.sampling_rate];
				for(int j = 0; j < this.sampling_rate; j++){		
					if (i >= mixer_bytes.length){
						break;
					}
					temp[j] = mixer_bytes[i++];
				}
				dataLine.write(temp, 0, temp.length);
			}

		} catch (Exception e){
			e.printStackTrace(); 
		}
	}
}
