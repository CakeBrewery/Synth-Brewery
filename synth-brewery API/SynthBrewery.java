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

		//Options
		final int AMPLITUDE_MAX = 18000;
		final int AMPLITUDE_START = 16000;
		final int SAMPLING_RATE = 44100; 


		AudioTrack track1 = new AudioTrack(SAMPLING_RATE, 4, 90);
		
		for (int i = 0; i < 1; i++){
			track1.writeBeat(220); 
		}

		for (int i = 0; i < 1; i++){
			track1.writeBeat(220*5/3);
		}

		for (int i = 0; i < 2; i++){
			track1.writeBeat(220*3/2);
		}

		try{
			AudioFormat audioFormat = new AudioFormat(SAMPLING_RATE, 8, 1, true, true);
			SourceDataLine dataLine = AudioSystem.getSourceDataLine(audioFormat);
			dataLine.open(audioFormat);
			dataLine.start(); 
			byte[] byteArray = track1.getByteArray(); 


			for (byte num : byteArray){
				System.out.println(num); 
			}

			System.out.println(byteArray.length);
			dataLine.write(byteArray, 0, byteArray.length);

		} catch (Exception e){
			e.printStackTrace(); 
		}
	}
}