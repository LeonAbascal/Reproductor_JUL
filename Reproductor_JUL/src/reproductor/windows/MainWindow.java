package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import reproductor.mainClasses.Counter;
import reproductor.mainClasses.MP3;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1073903793200315336L;

	private static Logger logger = Logger.getLogger(MainWindow.class.getName());

	JButton fileChooser;
	
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
			JLabel txtMenuDescendente;
		JPanel metadataPanel; // to the right
			JLabel metadataText;
			JLabel titleLabel;
			JLabel artistLabel;
			JLabel albumLabel;
		JPanel songsPanel;
			JScrollPane songsScroll; // center

	JPanel southPanel;
		JButton playB;
		JButton pauseB;
		JButton randomB;
		JButton previousB;
		JButton nextB;
		
	static LogInWindow login_w;
	static String playingSongPath;

	public MainWindow() {
		guiComponentDeclaration();
		addComponentsToWindow();
		songsScrollPanel("MusicFiles");

		setTitle("Título");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 720);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		logger.log(Level.SEVERE, "logger de prueba");
		
		fileChooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
	               
	               // solo se admiten ficheros con extensiÃ³n ".txt"
	               FileFilter filter = new FileNameExtensionFilter("Canciones Mp3", "mp3");
	               fileChooser.setFileFilter(filter);

	               // en este caso se muestra un dialogo de selecciÃ³n de fichero de guardado
	               int result = fileChooser.showSaveDialog(MainWindow.this);
	               if (result == JFileChooser.APPROVE_OPTION) {
	                   // el usuario ha pulsado el boton aceptar
	                   // se obtiene el fichero seleccionado -> File
	                   File file = fileChooser.getSelectedFile();
	                   System.out.println("Fichero seleccionado: " + file.getAbsolutePath());
	                   MP3 mp3 = new MP3(file.getAbsolutePath());
	   				   mp3.play();
	               }
	               
			}
		});
	}

	private void guiComponentDeclaration() {
		// PANELS A
		centerPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new FlowLayout());
		menuPanel = new JPanel();
		menuPanel.setMaximumSize(new Dimension(20, 20));
		metadataPanel = new JPanel();
		songsPanel = new JPanel(null);
		songsScroll = new JScrollPane(songsPanel);
		

		menuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		menuPanel.setLayout(new BorderLayout(0, 0));

		txtMenuDescendente = new JLabel("Men\u00FA Descendente");
		menuPanel.add(txtMenuDescendente);
		metadataPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		metadataPanel.setLayout(new BorderLayout(0, 0));

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
		fileChooser= new JButton("Choose Songs");
		
		playB = new JButton("Play");
		pauseB = new JButton("Pause");
		randomB = new JButton("Random");
		previousB = new JButton("Previous");
		nextB = new JButton("Next");
		
		// PLAY BUTTON ACTION
		playB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MP3 mp3 = new MP3(playingSongPath);
				mp3.play();
			}
			
		});
	}

	private void addComponentsToWindow() {
		
		southPanel.add(previousB);
		southPanel.add(playB);
		southPanel.add(pauseB);
		southPanel.add(nextB);
		southPanel.add(randomB);
		southPanel.add(fileChooser);

		centerPanel.add(metadataPanel, BorderLayout.EAST);
		centerPanel.add(menuPanel, BorderLayout.WEST);
		centerPanel.add(songsScroll, BorderLayout.CENTER);

		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		
		
	}
	
	// Method that creates the center scroll panel with the songs buttons
		private void songsScrollPanel(String filePath) {

			Counter contx = new Counter();
			Counter conty = new Counter();

			File songsFile = new File(filePath);

			// DEFINING THE PANEL FOR THE COMPONENTS
			GridBagLayout gLayout = new GridBagLayout();
			songsPanel.setLayout(gLayout);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(10, 10, 50, 10);

			// DIFFERENT DIMENSIONS FOR EACH COMPONENT
			gbc.fill = GridBagConstraints.HORIZONTAL;

			for (final File file : songsFile.listFiles()) {
				String fileName = file.getName();
				// BUTTON CREATION FOR EACH SONG
				JButton l = new JButton(fileName);
				l.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						metadataText.setText(fileName);
						playingSongPath = file.getAbsolutePath();
					}
				});

				if (!fileName.contains(".mp3")) {
					continue;
				}
				if (contx.get() >= 2) {
					contx.reset();
					conty.inc();
				}
				gbc.gridx = contx.get();
				gbc.gridy = conty.get();
				songsPanel.add(l, gbc);
				contx.inc();
			}

			songsPanel.setLayout(gLayout);
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