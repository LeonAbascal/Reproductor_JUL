package reproductor.windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DBManager;
import reproductor.mainClasses.User;

public class LogInWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9084596910689539165L;
	static Logger logger = Logger.getLogger(DBManager.class.getName());
	private String nameu;
	JPanel center;
		JLabel name;
		JTextField userName;
		
		JLabel password;
		JPasswordField userPassword;
		
		JButton registerUser;
		JButton logIn;
		public String getLogInWindowUsername(){
			return nameu;
			
		}
		private void guiComponentDeclaration() {
			// PANEL
			center = new JPanel();

			// NAME + PASSWORD
			name = new JLabel("Nombre:");
			password = new JLabel("Contraseña:");
			userName = new JTextField(20);
			userPassword = new JPasswordField(20);

			// BUTTONS
			registerUser = new JButton("Registrarse");
			logIn = new JButton("Iniciar sesión");

			// REGISTRO
			registerUser.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if ((userName.getText().isBlank()) || (userPassword.getText().isBlank())) {

						System.out.println("No se pudo crear el usuario: TextField vacío");
						JOptionPane.showMessageDialog(null, "Nombre y Contraseña son campos obligatorios",
								"Error de registro", JOptionPane.WARNING_MESSAGE);

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
							logger.log(Level.WARNING, "Cant register user, userName already exists");
							JOptionPane.showMessageDialog(null, "Nombre de usuario ya existente", "Error de registro",
									JOptionPane.WARNING_MESSAGE);

						} else {
							User u = new User(userName.getText(), userPassword.getText(), null);
							nameu=u.getName();
							// ---------------------------------------------------------
							// AÑADIR NUEVO USUARIO A LA LISTA DE USUARIOS
							// ---------------------------------------------------------
							DBManager.store(u);
							logger.log(Level.INFO, "New user registered -> " + u.getName());
							userName.setText("");
							userPassword.setText("");
						}

					}

				}
			});

			// INCIO DE SESIÓN
			logIn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if ((userName.getText().isBlank()) || (userPassword.getText().isBlank())) {

						logger.log(Level.WARNING, "Cant logIn into user, blank TextField");
						JOptionPane.showMessageDialog(null, "Nombre y Contraseña son campos obligatorios",
								"Error de inicio de sesión", JOptionPane.WARNING_MESSAGE);

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
								nameu=u_selected.getName();
								
							}
						}
						if (!exists) {
							logger.log(Level.WARNING, "Cant logIn into user, user does not exist");
							JOptionPane.showMessageDialog(null, "Usuario " + userName.getText() + " no existe",
									"Error de inicio de sesión", JOptionPane.WARNING_MESSAGE);
						} else {
							if (u_selected.getPassword().equals(userPassword.getText())) {
								new MainWindow();
								MainWindow.login_w.setVisible(false);
								logger.log(Level.INFO, "User " + u_selected.getName() + " succesfully logged");
								JOptionPane.showMessageDialog(null,
										"Sesión iniciada para el usuario " + u_selected.getName());
								
							} else {
								logger.log(Level.WARNING, "Cant logIn into user, wrong password");
								JOptionPane.showMessageDialog(null, "Contraseña incorrecta",
										"Error de inicio de sesión", JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				}
			});
		}
	
	private void addComponentsToWindow() {
		// AÑADIR LOS COMPONENTES A LA UI
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


		add(center);		
	}

	public LogInWindow() {
		guiComponentDeclaration();
		addComponentsToWindow();
		
		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
	}
}