package reproductor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LogInWindow extends JFrame {

	JButton registerUser;
	JButton logIn;
	JLabel userName;
	JLabel userPassword;
	
	public LogInWindow () {
		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		setVisible(true);
	}	
}
