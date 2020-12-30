package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Configuracion extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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
        		"INDOWS"
        };

        ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(strings);
        JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);

        JPanel comboBoxPanel = new JPanel();
        Border comboBoxPanelBorder = BorderFactory.createTitledBorder("JComboBox");
        comboBoxPanel.setBorder(comboBoxPanelBorder);
        comboBoxPanel.add(comboBox);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(comboBoxPanel);
        add(mainPanel, BorderLayout.CENTER);
        
        comboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // se comprueba si se ha seleccionado o deseleccionado un elemento de la lista
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	try (FileReader reader = new FileReader("JUL.init")) {
                        // Se crea el objeto y se leen las propiedades del fichero
                        Properties properties = new Properties();
                        properties.load(reader);
                        
                        // Se puede acceder al valor de las propiedades por nombre
                        properties.setProperty("skin=", (String) comboBox.getSelectedItem());
                        
                        

                    } catch (IOException e1) {
                        System.out.println("No se pudo leer el fichero de propiedades");
                    }
                }

                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    System.out.println("Deseleccionado: " + e.getItem());
                }
            }

        });
    }
}
