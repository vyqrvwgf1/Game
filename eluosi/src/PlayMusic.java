
import java.io.File;
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlayMusic { 
	static File file;
	static Clip clip;
	public static void play_start(String path) {
		
		try { 
		 file = new File(path);
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
          clip = AudioSystem.getClip(); 
          clip.open(audioInputStream);
          clip.start();

  // Let the music play for a while
  Thread.sleep(5000);

  
  

} catch (Exception e) {
  System.out.println("Error playing music: " + e.getMessage());
}
} 
	public static void play_end() {
		try {
		clip.stop();
		clip.close();
			
		}catch (Exception e) {
			  System.out.println("Error playing music: " + e.getMessage());
		}
	}

}