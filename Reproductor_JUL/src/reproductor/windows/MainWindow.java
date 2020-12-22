package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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
		JPanel menuPanel; // to the left
			JLabel imageText;
		JPanel metadataPanel; // to the right
			JLabel metadataLabel;
			JLabel titleLabel;
			JLabel artistLabel;
			JLabel albumLabel;
		JPanel songsPanel;

	JPanel southPanel;
		JButton playB;
		JButton pauseB;
		JButton randomB;
		JButton previousB;
		JButton nextB;
		
	static LogInWindow login_w;

	// Generados por WindowBuilder
	private JLabel metadataText;
	private JLabel txtMenuDescendente;
	private JLabel txtSongName;

	public MainWindow() {
		guiComponentDeclaration();
		addComponentsToWindow();

		setTitle("Título");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(720, 480);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		logger.log(Level.SEVERE, "logger de prueba");
	}

	private void guiComponentDeclaration() {
		// PANELS
		centerPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new FlowLayout());
		menuPanel = new JPanel();
		metadataPanel = new JPanel(new GridLayout());
		songsPanel = new JPanel(null);

		menuPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		txtMenuDescendente = new JLabel("Men\u00FA Descendente");
		menuPanel.add(txtMenuDescendente);
		metadataPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		metadataText = new JLabel("Metadata");
		metadataPanel.add(metadataText);

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

		// Songs icons + text generator
		JButton pruebaBoton = new JButton("");
		pruebaBoton.setIcon(new ImageIcon("MusicFiles/Icons/DoraemonBumpingRemix.png"));
		pruebaBoton.setBounds(10, 10, 96, 82);
		songsPanel.add(pruebaBoton);

		txtSongName = new JLabel("Song name");
		txtSongName.setHorizontalAlignment(SwingConstants.CENTER);
		txtSongName.setBounds(10, 102, 96, 18);
	}

	private void addComponentsToWindow() {
		southPanel.add(previousB);
		southPanel.add(playB);
		southPanel.add(nextB);
		southPanel.add(randomB);

		centerPanel.add(metadataPanel, BorderLayout.EAST);
		centerPanel.add(menuPanel, BorderLayout.WEST);
		centerPanel.add(songsPanel, BorderLayout.CENTER);

		getContentPane().add(centerPanel, BorderLayout.CENTER);

		songsPanel.add(txtSongName);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
	}

	private static void applySkin() {
		try {
			Scanner sc = new Scanner(new FileInputStream("JUL.init"));
			String skinLine = sc.nextLine();
			switch (skinLine) {
			case "skin=SWING": {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				// logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.init.
				// Default skin applied.");
				break;
			}
			case "skin=MOTIF": {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				// logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini.
				// Motif skin applied.");
				break;
			}
			case "skin=NIMBUS": {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				System.out.println("Classic Macintosh skin applied");
				// logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini.
				// Classic Machintosh skin applied");
				break;
			}
			case "skin=WINDOWS": {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				// logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini.
				// Windows skin applied");
			}
			default:
				System.out.println("Not valid value in init file: " + skinLine);
			}

			sc.close();

		} catch (FileNotFoundException e) {
			// logger.log(Level.FINE, "lionSynth.init file was deleted or does not still
			// exist. Skin won't be applied.");
		} catch (Exception e) {
			System.err.println("Skin could not be applied.");
			// logger.log(Level.WARNING, "Skin could not be applied.");
		}
	}

	public static void main(String[] args) {
		
		try (FileInputStream fis = new FileInputStream("logger/logger.properties")) {
            LogManager.getLogManager().readConfiguration(fis);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "No se pudo leer el fichero de configuración del logger");
        }
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				applySkin();
				login_w = new LogInWindow();
				login_w.setVisible(true);
			}
		});
	}
}