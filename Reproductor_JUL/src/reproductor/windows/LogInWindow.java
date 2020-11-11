package reproductor.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import database.DBManager;
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
		north.add(messages, BorderLayout.CENTER);
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
					JOptionPane.showMessageDialog(null,"Nombre y Contraseña son campos obligatorios");

				} else {

					List<User> users = new ArrayList<User>();
					users = DBManager.getAllUsers();
					boolean contiene = false;
					for (Iterator iterator = users.iterator(); iterator.hasNext();) {
						User user = (User) iterator.next();
						if (user.getName().equals(userName.getText())) {
							contiene = true;
						}
					}
					if (contiene) {
						System.out.println("Nombre de usuario ya existente");
						JOptionPane.showMessageDialog(null, "Nombre de usuario ya existente");

					} else {
						User u = new User(userName.getText(), userPassword.getText(), null);
						// ---------------------------------------------------------
						// AÑADIR NUEVO USUARIO A LA LISTA DE USUARIOS
						// ---------------------------------------------------------
						DBManager.store(u);
						System.out.println("Nuevo usuario creado: " + u);
						userName.setText("");
						userPassword.setText("");
					}

				}

			}
		});

		// INCIO DE SESIÓN
		logIn = new JButton("Iniciar sesión");
		logIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((userName.getText().isBlank()) || (userPassword.getText().isBlank())) {

					System.out.println("No se pudo crear el usuario: TextField vacío");
					JOptionPane.showMessageDialog(null,"Nombre y Contraseña son campos obligatorios");
					
				} else {
					// Sacamos los usuarios de la BD
					List<User> users = new ArrayList<User>();
					users = DBManager.getAllUsers();
					Boolean exists = false;
					User u_selected = new User();
					
					for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
						User user = (User) iterator.next();
						String u_name = userName.getText();

						if (u_name.equals(user.getName())) {
							exists = true;
							u_selected = user;
							System.out.println("Usuario seleccionado");
						} else {
							System.out.println(user.getName() + " != " + u_name);
						}
					}
					if (!exists) {
						System.out.println("No se pudo iniciar sesión");
					} else {
						if(u_selected.getPassword().equals(userPassword.getText())) {
							System.out.println("Sesión iniciada para el usuario " + u_selected);
							new MainWindow();
						} else {
							System.out.println("Contraseña incorrecta.");
						}
					}
				}
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

		add(north, BorderLayout.NORTH);

		add(center);

		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}