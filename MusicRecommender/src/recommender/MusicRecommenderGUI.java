package recommender;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MusicRecommenderGUI {

	private JFrame frame;
	private JTextField txtChosenSong;
	private String Songtitel;
	private String Songpfad;
	
	

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
				//int returnVal = fc.showOpenDialog(fc);
				Songpfad = fc.getSelectedFile().getAbsolutePath();
				Songtitel = fc.getName(fc.getSelectedFile());
				txtChosenSong.setText(Songtitel);
			}
		});
		btnChooseSong.setBounds(12, 52, 189, 25);
		frame.getContentPane().add(btnChooseSong);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(139, 124, 433, 419);
		frame.getContentPane().add(formattedTextField);
		
		JButton btnFindSongs = new JButton("finde ähnliche Songs");
		btnFindSongs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnFindSongs.setBounds(383, 563, 189, 25);
		frame.getContentPane().add(btnFindSongs);
	}
}
