import javax.sound.sampled.*;


public class PlayAnArray {
    private static int sampleRate = 16000;
    public static void main(String[] args) {
        try {
            final AudioFormat audioFormat = new AudioFormat(sampleRate, 8, 1, true, true);
            SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat );
            line.open(audioFormat );
            line.start();
            play(line, generateRandomArray());
            line.drain();
            line.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static byte[] generateRandomArray() {
        int size = 10000;
        System.out.println(size);
        byte[] byteArray = new byte[size];
        for (int i = 0; i < size; i++) {
            byteArray[i] = (byte) (Math.random() * 127f);
        }
        return byteArray;
    }
    private static void play(SourceDataLine line, byte[] array) {
        int length = sampleRate * array.length / 1000;
        line.write(array, 0, array.length);
    }
}
