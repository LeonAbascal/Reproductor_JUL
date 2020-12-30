package reproductor.windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import database.DBManager;
import reproductor.mainClasses.Counter;
import reproductor.mainClasses.PlayList;
import reproductor.mainClasses.Song;

public class PlaylistCreationWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static PlayList playlist;
	private static ArrayList<Song> songs = new ArrayList<Song>();
	
	
	JPanel checkBoxPanelSongs;
	public PlaylistCreationWindow(String username) {
		 
		 
		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	     setTitle("Creacion de Playlist");
	     setVisible(true);
	     setSize(600, 600);
	     setLocationRelativeTo(null);
	     JButton fileChooser;
	     JButton save;
	     fileChooser= new JButton("Select the path of the songs");
	     save = new JButton("Save playlist");
	     JPanel mainPanel = new JPanel();
	     mainPanel.add(fileChooser);
	     mainPanel.add(save);
	     checkBoxPanelSongs = new JPanel(null);
	     
	     mainPanel.add(checkBoxPanelSongs);
	     add(mainPanel);
	     
	     
	     
	     
	     fileChooser.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		               
		               // solo se admiten ficheros con extensión ".txt"
//		               FileFilter filter = new FileNameExtensionFilter("Canciones Mp3", "mp3");
//		               fileChooser.setFileFilter(filter);

		               // en este caso se muestra un dialogo de selección de fichero de
		               // guardado.
		               int result = fileChooser.showSaveDialog(PlaylistCreationWindow.this);
		               if (result == JFileChooser.APPROVE_OPTION) {
		                   // el usuario ha pulsado el boton aceptar
		                   // se obtiene el fichero seleccionado -> File
		                   File file = fileChooser.getSelectedFile();
		                   System.out.println("Fichero seleccionado: " + file.toString());
		                   checkBoxPanelSongs(file.getAbsolutePath());
		               }
		               
				}
			});
	     
	     save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("	Insert the name of the Playlist");
				JOptionPane.showMessageDialog(null, "Playlist" + name+ "created");
				playlist= new PlayList(songs, name);
				//Dbmanager store playlist method
				DBManager.storePlaylist(playlist,username);;
				for (Song song : songs) {
					//Dbmanager store songs method
					DBManager.storeSong(song, name);
				}
				MainWindow.main_window.updatePlayListBox();
				SwingUtilities.updateComponentTreeUI(MainWindow.main_window.menuPanel);
				dispose();
			}
		});
	     
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
    			// BUTTON CREATION FOR EACH SONG
    			
    			JCheckBox l = new JCheckBox(fileName);
    			l.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					JToggleButton toggleButton = (JToggleButton) e.getSource();
    	                System.out.println("Cambio de estado en " + toggleButton.getText() + ". Seleccionado: " + toggleButton.isSelected());
    	                Song s = new Song(fileName,"","",0,"",file.getAbsolutePath());
    	                if (toggleButton.isSelected()) {
							songs.add(s);
						}if (!toggleButton.isSelected()){
							songs.remove(s);
						}
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
    			checkBoxPanelSongs.add(l, gbc);
    			contx.inc();
    			
    		}

    		checkBoxPanelSongs.setLayout(gLayout);
    		SwingUtilities.updateComponentTreeUI(checkBoxPanelSongs);
    	}
		
	


	
}
