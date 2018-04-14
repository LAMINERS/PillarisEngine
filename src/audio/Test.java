package audio;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		AudioMaster.init();
		AudioMaster.setListenerData(0, 0, 0);
		
		int buffer = AudioMaster.loadSound("audio/bounce.wav");
		Source source = new Source();
		source.setLooping(true);
		source.play(buffer);
		
		float xPos = 8;
		source.setPosition(xPos, 0, 2);
		
		char c = ' ';
		while(c != 'q') {
			//c = (char)System.in.read();
			
			xPos -= 0.03f;
			source.setPosition(xPos, 0, 0);
			Thread.sleep(10);
			/*
			if(c == 'p') {
				if(source.isPlaying()) {
					source.pause();
				} else {
					source.continuePlaying();
				}
			}*/
		}
		
		source.delete();
		AudioMaster.cleanUp();

	}

}
