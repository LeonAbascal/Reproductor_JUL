package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import reproductor.mainClasses.User;

public class LogInWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9084596910689539165L;
	JLabel name;
	JLabel password;
	JLabel messages;
	JTextField userName;
	JPasswordField userPassword;
	JButton registerUser;
	JButton logIn;
	JPanel north;

	public LogInWindow() {

		// PANEL DE ERRORES
		north = new JPanel();
		messages = new JLabel();
		north.add(messages,BorderLayout.CENTER);
		// FIN PANEL DE ERRORES
		
		name = new JLabel("Nombre:");
		password = new JLabel("Contraseña:");
		messages.setForeground(Color.RED);
		userName = new JTextField(20);
		userPassword = new JPasswordField(20);

		// REGISTRO
		registerUser = new JButton("Registrarse");
		registerUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((userName.getText().isBlank()) || (userPassword.getText().isBlank())) {
					System.out.println("No se pudo crear el usuario: TextField vacío");
					messages.setText("Nombre y Contraseña son campos obligatorios");
				} else {
					User u = new User(userName.getText(), userPassword.getText(), null);
					// ---------------------------------------------------------
					// AÑADIR NUEVO USUARIO A LA LISTA DE USUARIOS
					// ---------------------------------------------------------
					System.out.println("Nuevo usuario creado: " + u);
					userName.setText("");
					userPassword.setText("");

				}
			}
		});

		// INCIO DE SESIÓN
		logIn = new JButton("Iniciar sesión");
		logIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sesión iniciada para el usuario: " + userName.getText() + " con contraseña: "
						+ userPassword.getText());
			}
		});

		
		// AÑADIR LOS COMPONENTES A LA UI
		JPanel center = new JPanel();
		// Definir el panel para los componentes
		GridBagLayout layout = new GridBagLayout();
		center.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		// Poner diferentes dimensiones a los componentes
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		center.add(name, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		center.add(userName, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;

		center.add(password, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;

		center.add(userPassword, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;

		center.add(logIn, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;

		center.add(registerUser, gbc);

		add(north,BorderLayout.NORTH);
		
		add(center);

		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
