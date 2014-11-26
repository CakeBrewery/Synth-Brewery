import java.util.*;

import java.io.FileInputStream; 
import java.io.FileInputStream;
import javax.sound.sampled.*; 

public class PlayTwinkleTwinkle
{
	public static void main(String[] args){

		//Pre-computations
		final double twopi = 2*Math.PI; 

		//Set Options
		final int AMPLITUDE_MAX = 10000;
		final int AMPLITUDE_START = 8000;
		final int SAMPLING_RATE = 44100; 

		/* 
		This creates an Audio Track with a length in beats and a specified bpm (beats/min)
		Note: Do not confuse this with an Android AudioTrack, 
		this is the AuioTrack class from Synth-Brewery API 
		*/
		int song_length = 24; 
		double song_bpm= 100;

		AudioTrack track1 = new AudioTrack(SAMPLING_RATE, song_length, song_bpm, WaveType.VIOLIN);
		AudioTrack track2 = new AudioTrack(SAMPLING_RATE, song_length, song_bpm, WaveType.VIOLIN);

		//Initialize a tuning object so we can get Equally tempered frequencies
		Tuning et = new Tuning(TuningMode.EQUALLY_TEMPERED); 
		
		//Let's test our API by writing a song!
		for (int i = 0; i < 2; i++){ //write 2 beats of 440 (A4)
			track1.writeBeat(et.getNoteFreq("C_4"), AMPLITUDE_START); 
			track2.writeBeat(et.getNoteFreq("C_3"), AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 M3
			track1.writeBeat(et.getNoteFreq("G_4"), AMPLITUDE_START);
			track2.writeBeat(et.getNoteFreq("C_3"), AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 P5
			track1.writeBeat(et.getNoteFreq("A_4"), AMPLITUDE_START);
			track2.writeBeat(et.getNoteFreq("F_3"), AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 P5
			track1.writeBeat(et.getNoteFreq("G_4"), AMPLITUDE_START);
			track2.writeBeat(et.getNoteFreq("C_3"), AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 P5
			track1.writeBeat(et.getNoteFreq("F_4"), AMPLITUDE_START);
			track2.writeBeat(et.getNoteFreq("F_3"), AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 P5
			track1.writeBeat(et.getNoteFreq("E_4"), AMPLITUDE_START);
			track2.writeBeat(et.getNoteFreq("C_3"), AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 P5
			track1.writeBeat(et.getNoteFreq("D_4"), AMPLITUDE_START);
			track2.writeBeat(et.getNoteFreq("G_3"), AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 P5
			track1.writeBeat(et.getNoteFreq("C_4"), AMPLITUDE_START);
			track2.writeBeat(et.getNoteFreq("C_3"), AMPLITUDE_START);
		}

		Mixer mixer = new Mixer(song_length, song_bpm, SAMPLING_RATE);
		mixer.addTrack(track1, 0); 
		mixer.addTrack(track2, 0); 


		//Using standard java libraries for sound playback
		try{
			//instantiate javax.sound.sampled structures for playback
			AudioFormat audioFormat = new AudioFormat(SAMPLING_RATE, 16, 1, true, false);
			SourceDataLine dataLine = AudioSystem.getSourceDataLine(audioFormat);
			dataLine.open(audioFormat);
			dataLine.start(); 

			//Obtain the samples array of track1 in bytes
			byte[] mixer_bytes = mixer.getByteArray(); 
			
			//This writes the audio to the audio buffer and plays it
			System.out.println("Playing: Writing to audio Buffer..");

			int i = 0; 
			while(i < mixer_bytes.length){
				byte[] temp = new byte[SAMPLING_RATE];
				for(int j = 0; j < SAMPLING_RATE; j++){		
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
