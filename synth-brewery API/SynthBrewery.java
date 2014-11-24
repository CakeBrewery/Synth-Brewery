import java.util.*;

import java.io.FileInputStream; 
import java.io.FileInputStream;
import javax.sound.sampled.*; 

public class SynthBrewery 
{
	public static void main(String[] args)
	{

		//Pre-computations
		final double twopi = 2*Math.PI; 

		//Set Options
		final int AMPLITUDE_MAX = 18000;
		final int AMPLITUDE_START = 4000;
		final int SAMPLING_RATE = 44100; 

		/* 
		This creates an Audio Track with 8 beats 120bpm (beats/min)
		Note: Do not confuse this with an Android AudioTrack, 
		this is the AuioTrack API inside AuioTrack.java
		*/
		AudioTrack track1 = new AudioTrack(SAMPLING_RATE, 8, 120);
		AudioTrack track2 = new AudioTrack(SAMPLING_RATE, 8, 120);
		

		//Let's test our track1 by writing 8 beats
		for (int i = 0; i < 2; i++){ //write 2 beats of 440 (A4)
			track1.writeBeat(440, AMPLITUDE_START); 
			track2.writeBeat(220, AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 M3
			track1.writeBeat(440*5/3, AMPLITUDE_START);
			track2.writeBeat(220, AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4 P5
			track1.writeBeat(440*3/2, AMPLITUDE_START);
			track2.writeBeat(220, AMPLITUDE_START);
		}

		for (int i = 0; i < 2; i++){ //Write 2 beats of A4
			track1.writeBeat(440, AMPLITUDE_START); 
			track2.writeBeat(220*5/3, AMPLITUDE_START);
		}

		Mixer mixer = new Mixer(8, 120, SAMPLING_RATE);
		mixer.addTrack(track1, 0); 
		mixer.addTrack(track2, 0); 


		try{

			//instantiate javax.sound.sampled structures for playback
			AudioFormat audioFormat = new AudioFormat(SAMPLING_RATE, 16, 1, true, false);
			SourceDataLine dataLine = AudioSystem.getSourceDataLine(audioFormat);
			dataLine.open(audioFormat);
			dataLine.start(); 

			//Obtain the samples array of track1 in bytes
			byte[] track1_bytes = mixer.getByteArray(); 
			
			//This writes the audio to the audio buffer and plays it
			System.out.println("Playing: Writing to audio Buffer..");
			dataLine.write(track1_bytes, 0, track1_bytes.length);

		} catch (Exception e){
			e.printStackTrace(); 
		}
	}
}
