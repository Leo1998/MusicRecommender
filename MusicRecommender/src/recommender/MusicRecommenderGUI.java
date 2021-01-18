package recommender;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;

public class MusicRecommenderGUI {

	private JFrame frame;
	private JTextField txtChosenSong;
	String interessen[] = {"Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik","Musik", "keine Musik"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicRecommenderGUI window = new MusicRecommenderGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MusicRecommenderGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 500, 950, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtChosenSong = new JTextField();
		txtChosenSong.setText("noch kein Titel gewählt");
		txtChosenSong.setBounds(12, 12, 206, 28);
		frame.getContentPane().add(txtChosenSong);
		txtChosenSong.setColumns(10);
		
		JButton btnChooseSong = new JButton("Titel wählen");
		btnChooseSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(fc);
			}
		});
		btnChooseSong.setBounds(12, 52, 189, 25);
		frame.getContentPane().add(btnChooseSong);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(139, 124, 433, 419);
		frame.getContentPane().add(formattedTextField);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
