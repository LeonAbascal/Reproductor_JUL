package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.border.Border;

import database.DBManager;
import reproductor.mainClasses.Counter;
import reproductor.mainClasses.MP3;
import reproductor.mainClasses.PlayList;
import reproductor.mainClasses.Song;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Component;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1073903793200315336L;

	private static Logger logger = Logger.getLogger(MainWindow.class.getName());

	JButton fileChooser;
	JButton configuracion;
	JButton crearPlaylist;
	JButton statistics;
	
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
			
			JPanel comboBoxPanelPlaylist;
				List<String> strings;
				ComboBoxModel<String> comboBoxModel;
				JComboBox<String> comboBox;
				Border comboBoxPanelBorder;
				List<Song> selectedPLSongs;
			JLabel playlistotalSongs;
			JPanel playlistButtons;
				
			
		JPanel metadataPanel; // to the right
			JLabel l_title;
			JLabel l_artist;
			JLabel l_duration;
			JLabel txt_title;
			JLabel txt_artist;
			JLabel txt_duration;
		JPanel songsAndPlaylistSongsPanel;
			JPanel songsPanel;
			JPanel songsPlaylistPanel;
				JScrollPane songsScroll; // center
				JScrollPane songsPlaylistScroll;

	JPanel southPanel;
		JButton playB;
		JButton stopB;
		JButton randomB;
		JButton previousB;
		JButton nextB;
	
	static Boolean playing = false;
	static LogInWindow login_w;
	static public MainWindow main_window;
	static String playingSongPath;
	static Configuration config;
	static PlaylistCreationWindow pcw;
	Counter songsCounter;
	Counter executedCounter;
	static StatisticsWindow sw;

	public MainWindow() {
		guiComponentDeclaration();
		addComponentsToWindow();
		songsScrollPanel("MusicFiles");
		
		readSongsCounter();
		readExecutedCounter();
		executedCounter.inc();
		writeExecutedCounter(executedCounter);
	    
		setTitle("T�tulo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 720);
		setResizable(false);
		setVisible(false);
		setLocationRelativeTo(null);
		logger.log(Level.SEVERE, "logger de prueba");
		
		fileChooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	               
	               // Para establecer un filtro
				   //FileFilter filter = new FileNameExtensionFilter("Canciones Mp3", "mp3");
				   //fileChooser.setFileFilter(filter);

	               // en este caso se muestra un dialogo de selección de fichero de
	               // guardado.
	               int result = fileChooser.showSaveDialog(MainWindow.this);
	               if (result == JFileChooser.APPROVE_OPTION) {
	                   // el usuario ha pulsado el boton aceptar
	                   // se obtiene el fichero seleccionado -> File
	                   File file = fileChooser.getSelectedFile();
	                   logger.log(Level.INFO, "Fichero seleccionado: " + file.toString());
	                   songsScrollPanel(file.getAbsolutePath());
	               }
	               
			}
		});
		configuracion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				config = new Configuration();
	               
			}
		});
		crearPlaylist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pcw= new PlaylistCreationWindow(login_w.getLogInWindowUsername());
				
			}
		});
		statistics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sw= new StatisticsWindow(songsCounter.get(),executedCounter.get() );
				
			}
		});
		

	}
	private void readSongsCounter() {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("numberOfSongs"))) {
            songsCounter = (Counter) is.readObject();
            logger.log(Level.INFO, "Songs counter readed");
            
            is.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error. No se pudo deserializar el objeto. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Error. No se pudo encontrar la clase asociada. " + e.getMessage());
        }
	}
	private void readExecutedCounter() {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("timesExecuted"))) {
            executedCounter = (Counter) is.readObject();
            logger.log(Level.INFO, "Executed counter readed");
            is.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error. No se pudo deserializar el objeto. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Error. No se pudo encontrar la clase asociada. " + e.getMessage());
        }
	}
	private void writeSongsCounter(Counter c) {

        // Creamos un stream de salida de objetos a fichero
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("numberOfSongs"))) {
            os.writeObject(c);
            logger.log(Level.INFO, "Songs counter writed");
            os.close();
        } catch (IOException e) {
        	logger.log(Level.WARNING, "Error al serializar los datos al fichero");
        }
	}
	private void writeExecutedCounter(Counter c) {

        // Creamos un stream de salida de objetos a fichero
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("timesExecuted"))) {
            os.writeObject(c);
            logger.log(Level.INFO, "Executed counter writed");
            os.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error al serializar los datos al fichero");
        }
	}
	
	
	private void guiComponentDeclaration() {
		// PANELS A
		centerPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new FlowLayout());
		menuPanel = new JPanel(new GridLayout(0, 1));
		menuPanel.setPreferredSize(new Dimension(200, 200));
		menuPanel.setMaximumSize(new Dimension(20, 20));
		metadataPanel = new JPanel();
		metadataPanel.setSize(new Dimension(100, 100));
		metadataPanel.setPreferredSize(new Dimension(200, 100));
		metadataPanel.setMinimumSize(new Dimension(200, 200));
		metadataPanel.setMaximumSize(new Dimension(200, 200));
		songsPanel = new JPanel(null);
		songsPlaylistPanel = new JPanel(null);
		songsScroll = new JScrollPane(songsPanel);
		songsPlaylistScroll = new JScrollPane(songsPlaylistPanel);
		songsAndPlaylistSongsPanel= new JPanel(new GridLayout(2, 1));
		playlistotalSongs= new JLabel();
		playlistotalSongs.setHorizontalAlignment(SwingConstants.CENTER);
		
		// PlayList panel creation
		comboBoxPanelPlaylist= new JPanel();
		comboBox = new JComboBox<>();
		List<Song> selectedPLSongs = new ArrayList<Song>();
		updatePlayListBox();
		playlistButtons= new JPanel();
		
		menuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		metadataPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		metadataPanel.setLayout(new GridLayout(0, 2, 1, 0));

		// Metadata panel labels and texts (txt_* changes)
		l_title = new JLabel("Title:");
		l_title.setPreferredSize(new Dimension(12, 12));
		l_title.setHorizontalAlignment(SwingConstants.CENTER);
		metadataPanel.add(l_title);
		
		txt_title = new JLabel("\"TITLE\"");
		txt_title.setPreferredSize(new Dimension(12, 12));
		txt_title.setHorizontalAlignment(SwingConstants.LEFT);
		txt_title.setFont(new Font("Tahoma", Font.PLAIN, 9));
		metadataPanel.add(txt_title);
		
		l_artist = new JLabel("Artist:");
		l_artist.setHorizontalAlignment(SwingConstants.CENTER);
		metadataPanel.add(l_artist);
		
		txt_artist = new JLabel("\"ARTIST\"");
		txt_artist.setHorizontalAlignment(SwingConstants.LEFT);
		txt_artist.setFont(new Font("Tahoma", Font.PLAIN, 9));
		metadataPanel.add(txt_artist);
		
		l_duration = new JLabel("Duration:");
		l_duration.setMaximumSize(new Dimension(38, 13));
		l_duration.setPreferredSize(new Dimension(25, 13));
		l_duration.setHorizontalAlignment(SwingConstants.CENTER);
		metadataPanel.add(l_duration);
		
		txt_duration = new JLabel("\"DURATION\"");
		txt_duration.setHorizontalAlignment(SwingConstants.LEFT);
		metadataPanel.add(txt_duration);

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
		configuracion= new JButton("Setting");
		crearPlaylist= new JButton("Create playlist");
		statistics= new JButton("Statistics");
		
		
		// ComboBox BUTTON
		comboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // se comprueba si se ha seleccionado o deseleccionado
                // un elemento de la lista
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	selectedPLSongs.clear();
                    logger.log(Level.INFO, "Seleccionado: " + e.getItem());
                    List<Song> songs = new ArrayList<Song>();
                    songs=DBManager.getSongs((String) e.getItem(), login_w.getLogInWindowUsername());
                    MainWindow.this.selectedPLSongs = songs;               
        			Counter contx = new Counter();
        			Counter conty = new Counter();

        			// DEFINING THE PANEL FOR THE COMPONENTS
        			GridBagLayout gLayout = new GridBagLayout();
        			songsPlaylistPanel.setLayout(gLayout);
        			GridBagConstraints gbc = new GridBagConstraints();
        			gbc.insets = new Insets(10, 10, 50, 10);

        			// DIFFERENT DIMENSIONS FOR EACH COMPONENT
        			gbc.fill = GridBagConstraints.HORIZONTAL;
        			songsPlaylistPanel.removeAll();
        			songsPlaylistPanel.repaint();
                    for (Song song : songs) {
                    	//selectedPLSongs.add(song);
						//System.out.println(song.getName());
                    	// BUTTON CREATION FOR EACH SONG
        				JButton l = new JButton(song.getName());
        				l.addActionListener(new ActionListener() {
        					public void actionPerformed(ActionEvent e) {
        						txt_title.setText(song.getName());
        						txt_artist.setText(song.getAlbum());
//        						txt_duration.setText(MP3.getDuration(songsFile)); TODO Falta por poner
        						playingSongPath = song.getPath();
        					}
        				});
        				if (!song.getName().contains(".mp3")) {
        					continue;
        				}
        				if (contx.get() >= 2) {
        					contx.reset();
        					conty.inc();
        				}
        				gbc.gridx = contx.get();
        				gbc.gridy = conty.get();
        				songsPlaylistPanel.add(l, gbc);
        				contx.inc();
					}
                    songsPlaylistPanel.setLayout(gLayout);
        			SwingUtilities.updateComponentTreeUI(songsPlaylistPanel);
        			playlistotalSongs.setText("Total songs: " +String.valueOf(songs.size()));
                }
            }

        });
	}

	// CONVERT TO PAUSE BUTTON METHOD
	private void pauseConvert(JButton b) {
		b.setIcon(new ImageIcon("MusicFiles\\Icons\\pauseButton.png"));
		stopB.setEnabled(true);
		playing = true;
	}
			
	private void addComponentsToWindow() {
		
		// PLAY BUTTON
		playB = new JButton("");
		playB.setMargin(new Insets(0, 0, 0, 0));
		playB.setBackground(new Color(238,238,238));
		playB.setBorder(null);
		playB.setIcon(new ImageIcon("MusicFiles\\Icons\\playButton.png"));
		
		// PLAY BUTTON ACTION
		playB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MP3 mp3 = new MP3(playingSongPath);
				if (!playing) {
					mp3.play();
					pauseConvert(playB);
					songsCounter.inc();
					writeSongsCounter(songsCounter);
				} else {
					playB.setIcon(new ImageIcon("MusicFiles\\Icons\\playButton.png"));
					// TODO Implementar acci�n que pause la canci�n
					playing = false;
				}
			}
			
		});
		randomB = new JButton("Random");
		
		// STOP BUTTON
		stopB = new JButton("");
		stopB.setMargin(new Insets(0, 0, 0, 0));
		stopB.setBackground(new Color(238,238,238));
		stopB.setBorder(null);
		stopB.setEnabled(false);
		stopB.setIcon(new ImageIcon("MusicFiles\\Icons\\stopButton.png"));
		
		stopB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO STOP ALL PLAYING SONGS
				stopB.setEnabled(false);
				playB.setIcon(new ImageIcon("MusicFiles\\Icons\\playButton.png"));
				playing = false;
			}
		});
		
		//RANDOM BUTTON ACTION
		randomB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<Song> randomized = new ArrayList<Song>(selectedPLSongs);
				Collections.shuffle(randomized);  // Randomizes the PlayList
				Song song = randomized.get(0);
				MP3 mp3 = new MP3(song.getPath());
				mp3.play();
				pauseConvert(playB);
				logger.log(Level.INFO, "NOW PLAYING: " + song.getName());
				/*
				for (Song song : randomized) {
					MP3 mp3 = new MP3(song.getPath());
					mp3.play();
					System.out.println("NOW PLAYING: " + song.getName());	
				}
				*/
			}
			
		});
		southPanel.add(randomB);
		previousB = new JButton("Previous");
		nextB = new JButton("Next");
		
		southPanel.add(previousB);
		southPanel.add(nextB);
		southPanel.add(stopB);
		southPanel.add(playB);
		southPanel.add(fileChooser);
		southPanel.add(configuracion);
		southPanel.add(statistics);
		
		playlistButtons.add(crearPlaylist);
		comboBoxPanelBorder = BorderFactory.createTitledBorder("Playlist");
		comboBoxPanelPlaylist.add(comboBox);
		menuPanel.add(comboBoxPanelPlaylist);
		
		menuPanel.add(playlistotalSongs);
		menuPanel.add(playlistButtons);
		
		songsAndPlaylistSongsPanel.add(songsScroll);
		songsAndPlaylistSongsPanel.add(songsPlaylistScroll);
		
		centerPanel.add(metadataPanel, BorderLayout.EAST);
		centerPanel.add(menuPanel, BorderLayout.WEST);
		centerPanel.add(songsAndPlaylistSongsPanel, BorderLayout.CENTER);

		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		
	}
	
	// Method that updates PlayList ComboBox
	
	public void updatePlayListBox() {
		strings = new ArrayList<String>();
		strings = DBManager.getAllPlaylist(login_w.getLogInWindowUsername());
		comboBoxModel = new DefaultComboBoxModel<>(strings.toArray(new String[0]));
		comboBox.setModel(comboBoxModel);
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

		songsPanel.removeAll();
		songsPanel.repaint();
		for (final File file : songsFile.listFiles()) {
			String fileName = file.getName();
			if (!fileName.contains(".mp3")) {
				continue;
			}
			// BUTTON CREATION FOR EACH SONG
			JButton l = new JButton(fileName);
			l.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txt_title.setText(fileName);
					txt_artist.setText(MP3.getAlbumTag(songsFile));
//					txt_duration.setText(MP3.getDuration(songsFile)); TODO Falta por poner
					playingSongPath = file.getAbsolutePath();
				}
			});

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
		SwingUtilities.updateComponentTreeUI(songsPanel);
	}

	private static void applySkin() {
		File f = new File("JUL.init");
    	Properties p = new Properties();
		
		try {
			p.load(new FileInputStream(f));
			String skinS = p.getProperty("skin");
			
			switch (skinS) {
			case "SWING": {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				// logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.init.
				// Default skin applied.");
				break;
			}
			case "MOTIF": {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				// logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini.
				// Motif skin applied.");
				break;
			}
			case "NIMBUS": {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				System.out.println("Classic Macintosh skin applied");
				// logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini.
				// Classic Machintosh skin applied");
				break;
			}
			case "WINDOWS": {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				// logger.log(Level.INFO, "First line read: " + skinLine + " in lionSynth.ini.
				// Windows skin applied");
			}
			default:
				logger.warning("Not valid value in init file: " + skinS);
			}


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
			logger.log(Level.SEVERE, "No se pudo leer el fichero de configuraci�n del logger");
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
