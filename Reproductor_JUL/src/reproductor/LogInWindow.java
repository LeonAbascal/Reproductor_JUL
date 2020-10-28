package reproductor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LogInWindow extends JFrame {

	JTextField userName;
	JTextField userPassword;
	JButton registerUser;
	JButton logIn;
	
	public LogInWindow () {
		
		userName = new JTextField("Nombre de usuario");
		userPassword = new JTextField("Contraseña");
		
		registerUser = new JButton("Registrar");
		registerUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("USUARIO CREADO");
			}
		});
		
		logIn = new JButton("Iniciar sesión");
		logIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sesión iniciada para el usuario: " + userName.getText() + " con contraseña: " + userPassword.getText());
			}
		});
		
		JPanel center = new JPanel();
		// Define the panel to hold the components
        GridBagLayout layout = new GridBagLayout();
        center.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
 
        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        center.add(userName, gbc);
 
        gbc.gridx = 1;
        gbc.gridy = 2;
        
        center.add(userPassword, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        
        center.add(registerUser, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 4;
        
        center.add(logIn, gbc);
		
        add(center);
		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
		setVisible(true);
	}	
}
