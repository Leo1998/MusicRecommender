package recommender;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MusicRecommenderGUI {

	private JFrame frame;
	private JTextField txtAmusepfad;
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
		
		txtAmusepfad = new JTextField();
		txtAmusepfad.setText("AmusePfad");
		txtAmusepfad.setBounds(12, 12, 206, 28);
		frame.getContentPane().add(txtAmusepfad);
		txtAmusepfad.setColumns(10);
		
		JButton btnPfadsetzen = new JButton("PfadSetzen");
		btnPfadsetzen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnPfadsetzen.setBounds(12, 52, 117, 25);
		frame.getContentPane().add(btnPfadsetzen);
		
		JList list = new JList(interessen);
		list.setBounds(12, 146, 206, 376);
		frame.getContentPane().add(list);
		
		JButton btnSuchezutitelstarten = new JButton("SucheZuTitelStarten");
		btnSuchezutitelstarten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnSuchezutitelstarten.setBounds(12, 540, 189, 25);
		frame.getContentPane().add(btnSuchezutitelstarten);
		
		JMenu mnGenre = new JMenu("Genre");
		mnGenre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
		mnGenre.setBounds(12, 106, 206, 28);
		frame.getContentPane().add(mnGenre);
		
		JMenuItem mntmClassical = new JMenuItem("Classical");
		mnGenre.add(mntmClassical);
		
		JRadioButtonMenuItem rdbtnmntmElectronic = new JRadioButtonMenuItem("Electronic");
		mnGenre.add(rdbtnmntmElectronic);
		
		JCheckBoxMenuItem chckbxmntmJazz = new JCheckBoxMenuItem("Jazz");
		mnGenre.add(chckbxmntmJazz);
		
		JMenuItem mntmRap = new JMenuItem("Rap");
		mnGenre.add(mntmRap);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(378, 146, 433, 419);
		frame.getContentPane().add(formattedTextField);
	}
}
