package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Cursor;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Sobre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Sobre");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/sobre.png")));
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		setModal(true);
		setBounds(100, 100, 450, 356);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sistema de Gestão de Serviços e");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lblNewLabel.setBounds(79, 0, 302, 40);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_1.setBounds(296, 122, 128, 128);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("CarSão");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblNewLabel_2.setBounds(186, 77, 63, 17);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Eduardo Casagrande de Sá");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lblNewLabel_3.setBounds(111, 100, 248, 23);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Sobre a licença MIT");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(143, 179, 143, 29);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Web Service:");
		lblNewLabel_5.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(26, 250, 83, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("https://github.com/casagrande04/CarSaoOS");
		lblNewLabel_6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/casagrande04/CarSaoOS");
			}
		});
		lblNewLabel_6.setForeground(new Color(0, 0, 255));
		lblNewLabel_6.setBounds(111, 247, 265, 23);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblEControleDe = new JLabel("Controle de Estoque de Estética Automotiva");
		lblEControleDe.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblEControleDe.setBounds(67, 26, 334, 40);
		getContentPane().add(lblEControleDe);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 0, 0), 2));
		panel.setBounds(0, 60, 434, 2);
		getContentPane().add(panel);

	}
	
	private void link(String site) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(site);
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
