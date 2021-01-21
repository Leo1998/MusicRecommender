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
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;


public class MusicRecommenderGUI {

	private JFrame frame;
	private JTextField txtChosenSong;
	private String Songtitel;
	private File Songpfad;
	private static AmuseHelper helper;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Create new WorkDir for tasks
		File workingDir = new File("../");
		File taskDir = new File(workingDir, "tasks");
		taskDir.mkdir();
		
		Process process = null;
		try {
			ProcessBuilder builder = new ProcessBuilder("./amuse.sh");
			builder.redirectErrorStream(true);
			builder.redirectOutput(Redirect.INHERIT);
			builder.directory(new File("../"));
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		helper = new AmuseHelper(taskDir);
		
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
		

		//File test = new File("/home/fricke/The_FireSoul-Behind_My_Back.mp3");
		//List<Tuple<File, Integer>> similar = Main.findSimilar(helper, test);

//		helper.endLoop();
//		File test = new File("/home/fricke/The_FireSoul-Behind_My_Back.mp3");
//		List<Tuple<File, Integer>> similar = Main.findSimilar(helper, test);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				helper.endLoop();
			}
		}));

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
				Songpfad = fc.getSelectedFile();
				Songtitel = fc.getName(fc.getSelectedFile());
				txtChosenSong.setText(Songtitel);
			}
		});
		btnChooseSong.setBounds(12, 52, 189, 25);
		frame.getContentPane().add(btnChooseSong);
		
		JFormattedTextField frmtdtxtfldHierKnnteDein = new JFormattedTextField();
		frmtdtxtfldHierKnnteDein.setText("Hier könnte dein neuer Lieblinssong stehen");
		frmtdtxtfldHierKnnteDein.setBounds(139, 124, 433, 419);
		frame.getContentPane().add(frmtdtxtfldHierKnnteDein);
		
		JButton btnFindSongs = new JButton("finde ähnliche Songs");
		btnFindSongs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.findSimilar(helper, Songpfad);
			}
		});
		btnFindSongs.setBounds(383, 563, 189, 25);
		frame.getContentPane().add(btnFindSongs);
	}
	
}
