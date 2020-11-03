package reproductor.windows;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame {

	public MainWindow() {

		setTitle("Título");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new LogInWindow();
			}
		});
	}
}