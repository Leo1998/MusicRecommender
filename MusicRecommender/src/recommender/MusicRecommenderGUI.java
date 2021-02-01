package recommender;


import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class MusicRecommenderGUI {

	private JFrame frame;
	private JTextField txtChosenSong;
	private JList<String> list;
	private DefaultListModel<String> listModel;
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
		
		helper = new AmuseHelper(workingDir, taskDir);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicRecommenderGUI window = new MusicRecommenderGUI();					
					window.frame.setLocationRelativeTo(null);
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
		txtChosenSong.setBounds(12, 12, 848, 28);
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
				
				listModel.clear();
			}
		});
		btnChooseSong.setBounds(12, 52, 189, 25);
		frame.getContentPane().add(btnChooseSong);
		
		JButton btnFindSongs = new JButton("finde ähnliche Songs");
		btnFindSongs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Tuple<File, Integer>> result = Main.findSimilar(helper, Songpfad);
				for(Tuple<File, Integer> t : result) {
					String filename = t.getFirst().getName();
					String songname = "";
					for (String token : filename.split("_")) {
						if (token.startsWith("1")) break;
						
						songname += token + "_";
					}
					songname = songname.substring(0, songname.length() - 1);
					listModel.addElement(songname );//+ "\t similarWindows: " + t.getSecond());
				}
			}
		});
		btnFindSongs.setBounds(383, 563, 189, 25);
		frame.getContentPane().add(btnFindSongs);
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setBounds(139, 117, 625, 357);
		
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		           String selectedItem = (String) list.getSelectedValue();

		           if (selectedItem != null) {
				       String songname = selectedItem.split("\t")[0];
				       File mp3 = Main.findMp3(songname);
				       
				       try {
						Runtime.getRuntime().exec("vlc " + mp3.getAbsolutePath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		           }
		        }
		    }
		};
		list.addMouseListener(mouseListener);
		
		frame.getContentPane().add(list);
		
		JLabel lblTitelDoppeltKlicken = new JLabel("Titel doppelt klicken zum Abspielen");
		lblTitelDoppeltKlicken.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitelDoppeltKlicken.setBounds(139, 476, 625, 15);
		frame.getContentPane().add(lblTitelDoppeltKlicken);
	}
}
