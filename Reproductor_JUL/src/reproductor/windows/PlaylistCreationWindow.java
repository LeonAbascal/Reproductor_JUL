package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import database.DBManager;
import reproductor.mainClasses.Counter;
import reproductor.mainClasses.MP3;
import reproductor.mainClasses.PlayList;
import reproductor.mainClasses.Song;

public class PlaylistCreationWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static PlayList playlist;
	private static ArrayList<Song> songs = new ArrayList<Song>();
	private static Logger logger = Logger.getLogger(MainWindow.class.getName());
	
	JPanel checkBoxPanelSongs;
	public PlaylistCreationWindow(String username) {
		 
		 setIconImage(new ImageIcon("MusicFiles\\Icons\\icon_JUL.png").getImage());
		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	     setTitle("Playlist management");
	     setVisible(true);
	     setSize(600, 600);
	     setResizable(false);
	     setLocationRelativeTo(null);
	     JButton fileChooserButton;
	     JButton save;
	     JComboBox<String> comboBox;
	     
	     
	     List<String> playlists = DBManager.getAllPlaylist(username);
	     ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(playlists.toArray(new String[0]));
	     comboBox = new JComboBox<>(comboBoxModel);
	     comboBox.setSelectedIndex(-1);
	     
	     
	     fileChooserButton = new JButton("Select the path of the songs");
	     fileChooserButton.setBounds(17, 5, 200, 21);
	     save = new JButton("Save playlist");
	     save.setBounds(223, 5, 120, 21);
	     
	     comboBox.setBounds(450, 5, 100, 21);
	     JPanel mainPanel = new JPanel();
	     mainPanel.setLayout(null);
	     
	     JLabel delete= new JLabel("Delete Playlist: ");
	     delete.setBounds(353, 5, 89, 21);
	     
	     mainPanel.add(fileChooserButton);
	     mainPanel.add(save);
	     mainPanel.add(comboBox);
	     mainPanel.add(delete);
	     
	     checkBoxPanelSongs = new JPanel();
	     JScrollPane mainScrollPane = new JScrollPane(checkBoxPanelSongs);
	     mainScrollPane.setBounds(10, 35, 566, 518);

	     mainPanel.add(mainScrollPane);
	     getContentPane().add(mainPanel);
	     
	     
	     fileChooserButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		               
		               // Para a�adir un filtro
					   //FileFilter filter = new FileNameExtensionFilter("Canciones Mp3", "mp3");
					   //fileChooser.setFileFilter(filter);

		               // en este caso se muestra un dialogo de selección de fichero de
		               // guardado.
		               int result = fileChooser.showSaveDialog(PlaylistCreationWindow.this);
		               if (result == JFileChooser.APPROVE_OPTION) {
		                   // el usuario ha pulsado el boton aceptar
		                   // se obtiene el fichero seleccionado -> File
		                   File file = fileChooser.getSelectedFile();
		                   logger.info("Directorio seleccionado: " + file.toString());
		                   checkBoxPanelSongs(file.getAbsolutePath());
		               }
		               
				}
			});
	     
	     save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("	Insert the name of the Playlist");
				JOptionPane.showMessageDialog(null, "Playlist " + name+ " created");
				playlist= new PlayList(songs, name);
				//Dbmanager store playlist method
				DBManager.storePlaylist(playlist,username);;
				for (Song song : songs) {
					//Dbmanager store songs method
					DBManager.storeSong(song, name);
				}
				songs.clear();
				MainWindow.main_window.updatePlayListBox();
				SwingUtilities.updateComponentTreeUI(MainWindow.main_window.menuPanel);
				dispose();
			}
		});
	     comboBox.addItemListener(new ItemListener() {

	            @Override
	            public void itemStateChanged(ItemEvent e) {
	                // se comprueba si se ha seleccionado o deseleccionado
	                // un elemento de la lista
	                if (e.getStateChange() == ItemEvent.SELECTED) {
	                	logger.log(Level.INFO, "Seleccionado: " + e.getItem());
	                	int response = JOptionPane.showConfirmDialog(PlaylistCreationWindow.this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
	                    if (response == JOptionPane.YES_OPTION) {
	                    	//Dbmanager delete playlist
	                    	DBManager.Delete(e.getItem().toString(), username);
	                    	MainWindow.main_window.updatePlayListBox();
		    				SwingUtilities.updateComponentTreeUI(MainWindow.main_window.menuPanel);
		    				dispose();
		    			
	                	}else{
	                		comboBox.setSelectedIndex(-1);
	                	}
	            }

	        }});
	     
	}

	protected void checkBoxPanelSongs(String filePath) {

			
			Counter contx = new Counter();
			Counter conty = new Counter();
    		File songsFile = new File(filePath);

    		// DEFINING THE PANEL FOR THE COMPONENTS
    		GridBagLayout gLayout = new GridBagLayout();
    		checkBoxPanelSongs.setLayout(gLayout);
    		GridBagConstraints gbc = new GridBagConstraints();
    		gbc.insets = new Insets(10, 10, 50, 10);

    		// DIFFERENT DIMENSIONS FOR EACH COMPONENT
    		gbc.fill = GridBagConstraints.HORIZONTAL;

    		for (final File file : songsFile.listFiles()) {
    			String fileName = file.getName();
    			if (!fileName.contains(".mp3")) {
    				continue;
    			}
    			
    			// CheckBox CREATION FOR EACH SONG
    			JCheckBox l = new JCheckBox();
    			
    			l.setText(fileName);
    			
    			l.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					JToggleButton toggleButton = (JToggleButton) e.getSource();
    	                logger.info("Cambio de estado en " + toggleButton.getText() + ". Seleccionado: " + toggleButton.isSelected());
    	                //Song s = new Song(fileName,"","",0,"",file.getAbsolutePath());
    	                
    	                String titleTag = MP3.getTitleTag(file);
    	                String artistTag = MP3.getArtistTag(file);
    	                String albumTag = MP3.getAlbumTag(file);
    	                
    	                
    	                
    	                if (toggleButton.isSelected()) {
							songs.add(new Song(titleTag, artistTag, albumTag, 0, "", file.getAbsolutePath()));
							
						} if (!toggleButton.isSelected()){
							songs.remove(new Song(titleTag, artistTag, albumTag, 0, "", file.getAbsolutePath()));
							
						}
    				}
    			});

    			if (contx.get() >= 1) {
    				contx.reset();
    				conty.inc();
    			}
    			gbc.gridx = contx.get();
    			gbc.gridy = conty.get();
    			checkBoxPanelSongs.add(l, gbc);
    			contx.inc();
    			
    		}

    		checkBoxPanelSongs.setLayout(gLayout);
    		SwingUtilities.updateComponentTreeUI(checkBoxPanelSongs);
    	}
		
}
