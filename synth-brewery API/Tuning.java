

class Tuning{
	public void setTuning(TuningMode tuning){
		switch(tuning){
		case EQUALLY_TEMPERED:
			C4 = 261.63;
			CS4 = 277.18;
			D4 = 293.66;
			DS4 = 311.13;
			E4 = 329.63;
			F4 = 349.23;
			FS4 = 369.99;
			G4 = 392.00;
			GS4 = 415.30;
			A4 = 440.00; 
			AS4 = 466.16;
			B4 = 493.88; 
			break; 

		default: 
			C4 = 261.63;
			CS4 = 277.18;
			D4 = 293.66;
			DS4 = 311.13;
			E4 = 329.63;
			F4 = 349.23;
			FS4 = 369.99;
			G4 = 392.00;
			GS4 = 415.30;
			A4 = 440.00; 
			AS4 = 466.16;
			B4 = 493.88; 
			break; 
		}
	}

	public double C4, CS4, D4, DS4, E4, F4, FS4, G4, GS4, A4, AS4, B4;

	public Tuning(TuningMode tuning){
		setTuning(tuning); 
	}

	public double getNoteFreq(String note){
		String split_string[] = note.split("_");
		String letter = split_string[0];
		int octave = Integer.parseInt(split_string[1]);

		double freq = -1; 

		switch(letter){
		case "C":
			freq = C4*2*Math.pow(2, octave-4); 
			break; 
		
		case "CS":
			freq = CS4*2*Math.pow(2, octave-4); 
			break; 
		
		case "D":
			freq = D4*2*Math.pow(2, octave-4); 
			break; 
		
		case "DS":
			freq = DS4*2*Math.pow(2, octave-4); 
			break; 
		
		case "E":
			freq = E4*2*Math.pow(2, octave-4); 
			break; 
		
		case "F":
			freq = F4*2*Math.pow(2, octave-4); 
			break; 
		
		case "FS":
			freq = FS4*2*Math.pow(2, octave-4); 
			break; 
		
		case "G":
			freq = G4*2*Math.pow(2, octave-4); 
			break; 
		
		case "GS":
			freq = GS4*2*Math.pow(2, octave-4); 
			break; 
		
		case "A":
			freq = A4*2*Math.pow(2, octave-4); 
			break; 
		
		case "AS":
			freq = AS4*2*Math.pow(2, octave-4); 
			break; 
		
		case "B":
			freq = B4*2*Math.pow(2, octave-4); 
			break; 
		}

		return freq; 	
	}
}