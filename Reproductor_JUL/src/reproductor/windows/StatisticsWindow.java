package reproductor.windows;

import java.awt.GridLayout;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
        add(mainPanel);
        
	}
}
