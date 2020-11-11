package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class MainWindow extends JFrame {

	private static Logger logger = Logger.getLogger(MainWindow.class.getName());
	
	JMenuBar menuBar;
		JMenu fileMenu;
			JMenuItem openMp3MU;
			JMenuItem saveMp3MU;
			
		JMenu playlistMenu;
			JMenuItem newPlaylist;
			JMenuItem openPlaylist;
			JMenuItem savePlaylist;
			JMenuItem queue;
	
	
	JPanel centerPanel; // border layout
		JPanel imagePanel; // to the left
		JPanel metadataPanel; // to the right	
			JLabel metadataLabel;
			JLabel titleLabel;
			JLabel artistLabel;
			JLabel albumLabel;
			
	JPanel southPanel;
		JButton playB;
		JButton pauseB;
		JButton randomB;
		JButton previousB;
		JButton nextB;
		
	public MainWindow() {
		guiComponentDeclaration();
		addComponentsToWindow();
		
		setTitle("Título");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		setVisible(true);
		logger.log(Level.SEVERE, "logger de prueba");
	}
	
	private void guiComponentDeclaration() {
		// PANELS
		centerPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new FlowLayout());
		imagePanel = new JPanel();
		metadataPanel = new JPanel(new GridLayout());
		
		// MENU BAR
		fileMenu = new JMenu("File");
		playlistMenu = new JMenu("Playlist");
		openMp3MU = new JMenuItem("Open MP3 file");
		saveMp3MU = new JMenuItem("Save MP3");
		
		newPlaylist = new JMenuItem("New...");
		openPlaylist = new JMenuItem("Open...");
		savePlaylist = new JMenuItem("Save...");
		queue = new JMenuItem("Queue");
		
		// THESE BUTTONS WILL CONTAIN IMAGES
		playB = new JButton("Play");
		pauseB = new JButton("Pause");
		randomB = new JButton("Random");
		previousB = new JButton("Previous");
		nextB = new JButton("Next");
	}
	
	private void addComponentsToWindow() {
		southPanel.add(previousB);
		southPanel.add(playB);
		southPanel.add(nextB);
		southPanel.add(randomB);
		
		centerPanel.add(metadataPanel, BorderLayout.EAST);
		centerPanel.add(imagePanel, BorderLayout.WEST);
		
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
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
				new LogInWindow();
			}
		});
	}
}