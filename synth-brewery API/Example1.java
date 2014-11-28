

public class Example1 {


	public static void main(String[] args){

		final int SAMPLING_RATE = 44100;
		final int volume = 8000; 


		int song_length = 40;  //In samples
		double song_bpm = 100; 

		AudioTrack track1 = new AudioTrack(SAMPLING_RATE, song_length, song_bpm, WaveType.SAW);

		Tuning et = new Tuning(TuningMode.EQUALLY_TEMPERED);

		for(int i = 0; i < 8; i++){
			track1.writeBeat(et.getNoteFreq("A_3"), volume);
		}

		Mixer mixer = new Mixer(song_length, song_bpm, SAMPLING_RATE);
		mixer.addTrack(track1, 0); 
		mixer.play(); 

	}
}