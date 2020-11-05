package reproductor.windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainWindow extends JFrame {
	
	JButton b = new JButton("");
	
	
	public MainWindow() {

		add(b);
		setTitle("Título");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		setVisible(true);
	}
	
	private static void applySkin() {
		try {
			Scanner sc = new Scanner(new FileInputStream("JUL.init"));
			String skinLine = sc.nextLine();
			switch (skinLine) {
			case "skin=SWING": {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				//logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.init. Default skin applied.");
				break;
			}
			case "skin=MOTIF": {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				//logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini. Motif skin applied.");
				break;
			}
			case "skin=NIMBUS": {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				System.out.println("Classic Macintosh skin applied");
				//logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini. Classic Machintosh skin applied");
				break;
			}
			case "skin=WINDOWS": {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				//logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini. Windows skin applied");
			}
			default:
				System.out.println("Not valid value in init file: " + skinLine);
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			//logger.log(Level.FINE, "lionSynth.init file was deleted or does not still exist. Skin won't be applied.");
		} catch (Exception e) {
			System.err.println("Skin could not be applied.");
			//logger.log(Level.WARNING, "Skin could not be applied.");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			
			
			@Override
			public void run() {
				applySkin();
				new MainWindow();
			}
		});
	}
}