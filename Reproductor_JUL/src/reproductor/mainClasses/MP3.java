package reproductor.mainClasses;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayer;


public class MP3 {
    private static BasicPlayer player = new BasicPlayer();

    public static void play(File f) {
        try {
        	player.open(f);
        	player.play();
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + f);
            System.out.println(e);
        }
    }
    
    public static void pause() {
    	try {
    		player.pause();
    	} catch (Exception e) {
    		
    	}
    }
    
    public static void stop() {
    	try {
    		player.stop();
    	} catch (Exception e) {
    		System.err.println("No se pudo detener el reproductor.");
    	}
    }
    
    public static void resume() {
    	try {
    		player.resume();
    	} catch (Exception e) {
    		
    	}
    }

}