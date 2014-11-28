

public class MegaMan{


	public static void main(String[] args){

		final int SAMPLING_RATE = 44100;
		final int volume = 8000; 


		int song_length = 16*5;  //In beats
		double song_bpm = 150; 

		AudioTrack synth1 = new AudioTrack(SAMPLING_RATE, song_length, song_bpm, WaveType.SQUARE);
		AudioTrack synth2 = new AudioTrack(SAMPLING_RATE, song_length, song_bpm, WaveType.SQUARE);
		AudioTrack bass = new AudioTrack(SAMPLING_RATE, song_length, song_bpm, WaveType.SINE);
		AudioTrack synth3 = new AudioTrack(SAMPLING_RATE, song_length, song_bpm, WaveType.SQUARE);

		Tuning et = new Tuning(TuningMode.EQUALLY_TEMPERED);

		//1
		synth1.write((double)(2+0.5), et.getNoteFreq("CS_4"), volume); synth2.write((double)(2+0.5), et.getNoteFreq("A_3"), volume);
		synth1.write((double)0.5, et.getNoteFreq("FS_3"), volume); synth2.write((double)0.5, et.getNoteFreq("FS_3"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("A_3"), volume); synth2.write((double)0.5, et.getNoteFreq("CS_4"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("B_3"), volume); synth2.write((double)0.5, et.getNoteFreq("B_3"), volume); 

		bass.write((double)4, et.getNoteFreq("FS_1"), volume);  

		synth3.write((double)4, et.getNoteFreq("FS_2"), volume);  

		//2
		synth1.write((double)3, et.getNoteFreq("CS_4"), volume); synth2.write((double)2, et.getNoteFreq("GS_3"), volume); 
		synth2.write((double)2/3, et.getNoteFreq("A_3"), volume); 
		synth2.write((double)2/3, et.getNoteFreq("FS_3"), volume);
		synth2.write((double)2/3, et.getNoteFreq("E_3"), volume);  
		synth1.write((double)1, et.getNoteFreq("E_4"), volume); 

		bass.write((double)3, et.getNoteFreq("E_1"), volume);  
		bass.write((double)0.5, et.getNoteFreq("E_0"), volume);  
		bass.write((double)0.5, et.getNoteFreq("FS_1"), volume);  

		synth3.write((double)3, et.getNoteFreq("E_2"), volume);  
		synth3.write((double)0.5, et.getNoteFreq("E_1"), volume);  
		synth3.write((double)0.5, et.getNoteFreq("FS_2"), volume); 

		//3
		synth1.write((double)(2+0.5), et.getNoteFreq("DS_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("CS_4"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("B_3"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("D_4"), volume);  

		synth2.write((double)2, et.getNoteFreq("DS_3"), volume);  
		synth2.write((double)0.5, et.getNoteFreq("DS_3"), volume);  
		synth2.write((double)0.5, et.getNoteFreq("E_3"), volume);  
		synth2.write((double)0.5, et.getNoteFreq("FS_3"), volume);  
		synth2.write((double)0.5, et.getNoteFreq("A_3"), volume);  

		bass.write((double)3, et.getNoteFreq("B_0"), volume);  
		bass.write((double)0.5, et.getNoteFreq("B_0"), volume);  
		bass.write((double)0.5, et.getNoteFreq("D_1"), volume);  

		synth3.write((double)3, et.getNoteFreq("B_1"), volume);  
		synth3.write((double)0.5, et.getNoteFreq("B_1"), volume);  
		synth3.write((double)0.5, et.getNoteFreq("D_2"), volume);  

		//4
		synth1.write((double)3, et.getNoteFreq("D_4"), volume);  
		synth1.write((double)0.5, et.getNoteFreq("CS_4"), volume);  
		synth1.write((double)0.5, et.getNoteFreq("B_3"), volume);  

		synth2.write((double)2, et.getNoteFreq("A_3"), volume);  
		synth2.write((double)1, et.getNoteFreq("GS_3"), volume);  
		synth2.write((double)0.5, et.getNoteFreq("A_3"), volume);  
		synth2.write((double)0.5, et.getNoteFreq("FS_3"), volume);  

		bass.write((double)3, et.getNoteFreq("D_2"), volume);  
		bass.write((double)0.5, et.getNoteFreq("CS_2"), volume);  
		bass.write((double)0.5, et.getNoteFreq("E_1"), volume);  

		synth3.write((double)3, et.getNoteFreq("D_3"), volume);  
		synth3.write((double)0.5, et.getNoteFreq("CS_2"), volume);  
		synth3.write((double)0.5, et.getNoteFreq("E_2"), volume);  

		//5
		synth1.write((double)0.5, et.getNoteFreq("CS_4"), volume);  
		synth1.write((double)0.5, et.getNoteFreq("A_3"), volume);  
		synth1.write((double)1.5, et.getNoteFreq("FS_3"), volume);  
		synth1.write((double)1, et.getNoteFreq("FS_3"), volume);  
		synth1.write((double)0.5, et.getNoteFreq("A_3"), volume);  

		synth2.write((double)0.5, et.getNoteFreq("A_3"), volume);  
		synth2.write((double)0.5, et.getNoteFreq("FS_3"), volume);  
		synth2.write((double)1, et.getNoteFreq("CS_3"), volume);  
		synth2.write((double)1, et.getNoteFreq("E_3"), volume);  
		synth2.write((double)1, et.getNoteFreq("CS_3"), volume);  

		bass.write((double)4, et.getNoteFreq("FS_1"), volume); 

		synth3.write((double)4, et.getNoteFreq("FS_2"), volume); 

		//6
		synth1.write((double)0.5, et.getNoteFreq("A_3"), volume);  
		synth1.write((double)1, et.getNoteFreq("FS_3"), volume);  
		synth1.write((double)1, et.getNoteFreq("A_3"), volume);  
		synth1.write((double)1, et.getNoteFreq("B_3"), volume);  
		synth1.write((double)0.5, et.getNoteFreq("C_4"), volume);  

		synth2.write((double)1, et.getNoteFreq("A_3"), volume);  
		synth2.write((double)1, et.getNoteFreq("CS_4"), volume);  
		synth2.write((double)1, et.getNoteFreq("A_3"), volume);  
		synth2.write((double)1, et.getNoteFreq("B_3"), volume);  

		bass.write((double)4, et.getNoteFreq("E_1"), volume); 
		
		synth3.write((double)4, et.getNoteFreq("E_2"), volume); 

		//7
		synth1.write((double)0.5, et.getNoteFreq("B_3"), volume);  
		synth1.write((double)1, et.getNoteFreq("A_3"), volume);  
		synth1.write((double)2, et.getNoteFreq("A_3"), volume);  
		synth1.write((double)0.5, et.getNoteFreq("C_5"), volume);  

		synth2.write((double)4, et.getNoteFreq("FS_3"), volume);  

		bass.write((double)4, et.getNoteFreq("D_1"), volume); 
		
		synth3.write((double)4, et.getNoteFreq("D_2"), volume);

		//8
		synth1.write((double)0.5, et.getNoteFreq("B_4"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("A_4"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), volume); 
		synth1.write((double)1, et.getNoteFreq("A_4"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("A_4"), volume); 
		synth1.write((double)1, et.getNoteFreq("B_4"), volume); 

		synth2.write((double)2.5, et.getNoteFreq("FS_3"), 0);  
		synth2.write((double)0.5, et.getNoteFreq("A_3"), volume);
		synth2.write((double)1, et.getNoteFreq("A_3"), volume);

		bass.write((double)2.5, et.getNoteFreq("D_1"), volume); 
		bass.write((double)0.5, et.getNoteFreq("D_1"), volume); 
		bass.write((double)1, et.getNoteFreq("E_1"), volume); 

		synth3.write((double)2.5, et.getNoteFreq("D_2"), volume); 
		synth3.write((double)0.5, et.getNoteFreq("D_2"), volume); 
		synth3.write((double)1, et.getNoteFreq("E_2"), volume); 

		//9
		synth1.write((double)1, et.getNoteFreq("FS_3"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("GS_3"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("A_3"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("B_3"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("CS_4"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("E_4"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), volume); 

		synth2.write((double)4, et.getNoteFreq("FS_3"), volume);  

		bass.write((double)4, et.getNoteFreq("FS_1"), volume); 

		synth3.write((double)4, et.getNoteFreq("FS_2"), volume); 

		//10
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), volume); 
		synth1.write((double)1, et.getNoteFreq("CS_4"), volume); 
		synth1.write((double)1, et.getNoteFreq("E_4"), volume); 
		synth1.write((double)1, et.getNoteFreq("FS_4"), volume); 
		synth1.write((double)0.5, et.getNoteFreq("B_4"), volume); 


		synth2.write((double)4, et.getNoteFreq("A_3"), volume);

		bass.write((double)3, et.getNoteFreq("E_1"), volume); 
		bass.write((double)0.5, et.getNoteFreq("E_1"), volume); 
		bass.write((double)0.5, et.getNoteFreq("FS_1"), volume); 

		synth3.write((double)3, et.getNoteFreq("E_2"), volume); 
		synth3.write((double)0.5, et.getNoteFreq("E_2"), volume); 
		synth3.write((double)0.5, et.getNoteFreq("FS_2"), volume);

		//11
		synth1.write((double)1, et.getNoteFreq("B_4"), volume); 
		synth1.write((double)1, et.getNoteFreq("A_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), volume);
		synth1.write((double)1, et.getNoteFreq("E_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), volume);

		synth2.write((double)4, et.getNoteFreq("CS_4"), volume);

		bass.write((double)4, et.getNoteFreq("B_0"), volume); 

		synth3.write((double)4, et.getNoteFreq("B_1"), volume); 

		//12
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("F_4"), volume);
		synth1.write((double)2, et.getNoteFreq("CS_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("E_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), volume);

		synth2.write((double)4, et.getNoteFreq("E_3"), volume);

		bass.write((double)4, et.getNoteFreq("CS_1"), volume); 

		synth3.write((double)4, et.getNoteFreq("CS_2"), volume); 

		//13
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), 0);
		synth1.write((double)0.5, et.getNoteFreq("CS_5"), volume);
		synth1.write((double)0.5, et.getNoteFreq("A_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("FS_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("CS_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("FS_3"), volume);
		synth1.write((double)0.5, et.getNoteFreq("GS_3"), volume);
		synth1.write((double)0.5, et.getNoteFreq("A_3"), volume);

		synth2.write((double)4, et.getNoteFreq("FS_3"), volume);

		bass.write((double)4, et.getNoteFreq("FS_1"), volume); 
		
		synth3.write((double)4, et.getNoteFreq("FS_2"), volume); 


		//14
		synth1.write((double)4/6, et.getNoteFreq("B_3"), 0);
		synth1.write((double)4/6, et.getNoteFreq("CS_4"), volume);
		synth1.write((double)4/6, et.getNoteFreq("E_4"), volume);
		synth1.write((double)4/6, et.getNoteFreq("FS_4"), volume);
		synth1.write((double)4/6, et.getNoteFreq("A_4"), volume);
		synth1.write((double)4/6, et.getNoteFreq("B_4"), volume);

		synth2.write((double)4, et.getNoteFreq("B_3"), volume);

		bass.write((double)4, et.getNoteFreq("E_1"), volume); 
		
		synth3.write((double)4, et.getNoteFreq("E_2"), volume); 


		//15
		synth1.write((double)0.5, et.getNoteFreq("CS_5"), volume);
		synth1.write((double)0.5, et.getNoteFreq("B_4"), volume);
		synth1.write((double)3/4, et.getNoteFreq("A_4"), volume);
		synth1.write((double)3/4, et.getNoteFreq("FS_4"), volume);
		synth1.write((double)1, et.getNoteFreq("A_4"), volume);
		synth1.write((double)0.5, et.getNoteFreq("GS_4"), volume);

		synth2.write((double)4, et.getNoteFreq("CS_4"), volume);

		bass.write((double)4, et.getNoteFreq("D_1"), volume); 
		
		synth3.write((double)4, et.getNoteFreq("D_2"), volume);

		//16
		synth1.write((double)4/6, et.getNoteFreq("CS_4"), volume);
		synth1.write((double)4/6, et.getNoteFreq("F_4"), volume);
		synth1.write((double)4/6, et.getNoteFreq("GS_4"), volume);
		synth1.write((double)4/6, et.getNoteFreq("B_4"), volume);
		synth1.write((double)4/6, et.getNoteFreq("CS_5"), volume);
		synth1.write((double)4/6, et.getNoteFreq("E_5"), volume);

		synth2.write((double)4, et.getNoteFreq("CS_4"), volume);

		bass.write((double)4, et.getNoteFreq("CS_2"), volume); 
		
		synth3.write((double)4, et.getNoteFreq("CS_3"), volume);

		//17
		synth1.write((double)4, et.getNoteFreq("CS_4"), volume);
		synth2.write((double)4, et.getNoteFreq("CS_3"), volume); 
		bass.write((double)4, et.getNoteFreq("CS_1"), volume); 
		
		synth3.write((double)4, et.getNoteFreq("CS_2"), volume);




		Mixer mixer = new Mixer(song_length, song_bpm, SAMPLING_RATE);
		mixer.addTrack(synth1, 0); 
		mixer.addTrack(synth2, 0);
		mixer.addTrack(bass, 0);
		mixer.addTrack(synth3, 0);
		mixer.play();

	}
}