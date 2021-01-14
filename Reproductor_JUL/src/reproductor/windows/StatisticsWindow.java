package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DBManager;

public class StatisticsWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MainWindow.class.getName());

	
	public StatisticsWindow(int songsPlayed,int julExecutedTimes){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Statistics");
        setVisible(true);
        setSize(600, 300);
        setLocationRelativeTo(null);
        JLabel songsPlayedLabelName= new JLabel("Number of songs played on this computer: ");
        JLabel julExecutedTimesLabelName= new JLabel("Number of times opened JUL: ");
        JLabel songsPlayedLabel= new JLabel(String.valueOf(songsPlayed));
        JLabel julExecutedTimesLabel= new JLabel(String.valueOf(julExecutedTimes));
        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(songsPlayedLabelName);
        mainPanel.add(songsPlayedLabel);
        mainPanel.add(julExecutedTimesLabelName);
        mainPanel.add(julExecutedTimesLabel);
        add(mainPanel,BorderLayout.NORTH);
        
        
        JPanel tablepanel = new JPanel();
        DefaultTableModel modelo;
    	JTable tabla;
        
		Class[] clases = {String.class,Integer.class};
				
		modelo = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return clases[columnIndex];
			}
		};
		
		

				
		tabla = new JTable(modelo);
		tabla.setEnabled(false);
		//tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		modelo.addColumn("User");
		modelo.addColumn("Total Playlist");
		
		Map<String, Integer> statisticsTable=new HashMap<String, Integer>();
		statisticsTable.putAll(DBManager.getAllUsersPlaylistsCount());		
		for (Entry<String, Integer> entry : statisticsTable.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		    Object[] fila = {entry.getKey(), entry.getValue()};
			modelo.addRow(fila);
		}
				
				
				
		
		
		tablepanel.setLayout(new BorderLayout());
		JScrollPane panelScroll = new JScrollPane(tabla);
		tablepanel.add(panelScroll);
		add(tablepanel,BorderLayout.CENTER);
		
				
			
		
        
        
	}
}
