package reproductor;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class VentanaPrincipal extends JFrame {
	
	public VentanaPrincipal() {
		
		setTitle("T�tulo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new VentanaPrincipal();
				
			}
		});
	}
}
