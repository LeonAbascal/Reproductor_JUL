package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Configuracion extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    static JButton applyChangesButton;
    static JComboBox<String> comboBox;
    private static Logger logger = Logger.getLogger(MainWindow.class.getName());
    
    
    public Configuracion() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Configuration");
        setVisible(true);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // datos de ejemplo
        String[] strings = { 
        		"SWING", 
        		"MOTIF",
        		"NIMBUS",
        		"WINDOWS"
        };

        ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(strings);
        comboBox = new JComboBox<>(comboBoxModel);
        applyChangesButton = new JButton("Apply");

        JPanel comboBoxPanel = new JPanel();
        Border comboBoxPanelBorder = BorderFactory.createTitledBorder("JComboBox");
        
        
        comboBoxPanel.setBorder(comboBoxPanelBorder);
        comboBoxPanel.add(comboBox);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(comboBoxPanel);
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(applyChangesButton);
        
        addActionListeners();
        setPrevoiusConfiguration();
        
    }
    
    private static void setPrevoiusConfiguration() {
    	File f = new File("JUL.init");
    	Properties p = new Properties();

    	// load previous configuration
    	try {
    		// Se crea el objeto y se leen las propiedades del fichero
    		p.load(new FileInputStream(f));
    		String skinS = p.getProperty("skin");
    		
    		comboBox.setSelectedItem(skinS);
    		logger.info("Skin in init file: " + skinS);

    	} catch (IOException ex) { logger.warning("Init file (" + f + ") could not be found."); };

    }

    public static void addActionListeners(){
    	applyChangesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = new File("JUL.init");
		    	Properties p = new Properties();
				 
				try {
					String skinS = (String) comboBox.getSelectedItem();
					
                    // Se puede acceder al valor de las propiedades por nombre
                    p.setProperty("skin", skinS );
                    logger.info("Skin set: " + skinS);
                    // properties.setProperty("windowSize", "");
                    
                    p.store(new FileOutputStream(f), "Init file for ");
                    
                    

                } catch (IOException ex) {
                	System.err.println(ex);
                	logger.warning("Properties file could not be written.");
                }
				
			}
		});
    	
    }
}
