package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.DAO;

public class Principal extends JFrame {
	
	//Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	//estes componentes serão alterada pela tela de Login (public)
	public JLabel lblUsuario;
	public JPanel panelRodape;
	public JButton btnUsuarios;
	public JButton btnFornecedor;
	private JLabel lblNewLabel;
	private JLabel lblClientes;
	private JLabel lblOrdemDeServio;
	private JLabel lblRelatrios;
	private JLabel lblProdutos;
	private JLabel lblFornecedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setResizable(false);
		setTitle("CarSão");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/car.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// Evento ativar janela
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setForeground(new Color(128, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSobre = new JButton("");
		btnSobre.setBorder(null);
		btnSobre.setContentAreaFilled(false);
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abrir a tela de Sobre
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setBounds(613, 11, 48, 48);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/sobre.png")));
		contentPane.add(btnSobre);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.setBorder(new LineBorder(new Color(128, 0, 0), 4, true));
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abrir a tela de Usuários
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/users.png")));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setBounds(47, 34, 135, 128);
		contentPane.add(btnUsuarios);
		
		panelRodape = new JPanel();
		panelRodape.setBackground(new Color(128, 0, 0));
		panelRodape.setBounds(0, 371, 671, 71);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		lblData = new JLabel("New label");
		lblData.setBounds(10, 26, 284, 20);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblUsuario2 = new JLabel("Usuário: ");
		lblUsuario2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario2.setForeground(new Color(255, 255, 255));
		lblUsuario2.setBounds(332, 23, 61, 27);
		panelRodape.add(lblUsuario2);
		
		lblUsuario = new JLabel("");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(314, 26, 232, 20);
		panelRodape.add(lblUsuario);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(613, 11, 48, 48);
		panelRodape.add(lblStatus);
		lblStatus.setToolTipText("StatusOff");
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBackground(SystemColor.desktop);
		lblLogo.setBounds(536, 193, 135, 212);
		contentPane.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/car.png")));
		
		JButton btnservicoOS = new JButton("");
		btnservicoOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
					servicos.setVisible(true);	
			}
		});
		btnservicoOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnservicoOS.setIcon(new ImageIcon(Principal.class.getResource("/img/ordem.png")));
		btnservicoOS.setToolTipText("Ordem de Serviços");
		btnservicoOS.setContentAreaFilled(false);
		btnservicoOS.setBorder(new LineBorder(new Color(128, 0, 0), 4, true));
		btnservicoOS.setBounds(47, 204, 135, 128);
		contentPane.add(btnservicoOS);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/cliente.png")));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorder(new LineBorder(new Color(128, 0, 0), 4, true));
		btnClientes.setBounds(217, 34, 135, 128);
		contentPane.add(btnClientes);
		
		JButton btnRelatorios = new JButton("");
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
				
			}
		});
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/relatorios.png")));
		btnRelatorios.setToolTipText("Relatórios");
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setBorder(new LineBorder(new Color(128, 0, 0), 4, true));
		btnRelatorios.setBounds(217, 204, 135, 128);
		contentPane.add(btnRelatorios);
		
		JButton btnProdutos = new JButton("");
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abrir a tela de produtos
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});
		btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/produtos.png")));
		btnProdutos.setToolTipText("Produtos");
		btnProdutos.setContentAreaFilled(false);
		btnProdutos.setBorder(new LineBorder(new Color(128, 0, 0), 4, true));
		btnProdutos.setBounds(389, 34, 135, 128);
		contentPane.add(btnProdutos);
		
		btnFornecedor = new JButton("");
		btnFornecedor.setEnabled(false);
		btnFornecedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abrir a tela de fornecedor
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);
			}
		});
		btnFornecedor.setIcon(new ImageIcon(Principal.class.getResource("/img/fornecedor.png")));
		btnFornecedor.setToolTipText("Fornecedor");
		btnFornecedor.setContentAreaFilled(false);
		btnFornecedor.setBorder(new LineBorder(new Color(128, 0, 0), 4, true));
		btnFornecedor.setBounds(389, 204, 135, 128);
		contentPane.add(btnFornecedor);
		
		lblNewLabel = new JLabel("USUÁRIO");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 25));
		lblNewLabel.setBounds(59, 6, 114, 29);
		contentPane.add(lblNewLabel);
		
		lblClientes = new JLabel("CLIENTES");
		lblClientes.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 25));
		lblClientes.setBounds(227, 6, 114, 29);
		contentPane.add(lblClientes);
		
		lblOrdemDeServio = new JLabel("ORDEM DE SERVIÇO");
		lblOrdemDeServio.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblOrdemDeServio.setBounds(34, 174, 200, 29);
		contentPane.add(lblOrdemDeServio);
		
		lblRelatrios = new JLabel("RELATÓRIOS");
		lblRelatrios.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 25));
		lblRelatrios.setBounds(212, 173, 155, 29);
		contentPane.add(lblRelatrios);
		
		lblProdutos = new JLabel("PRODUTOS");
		lblProdutos.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 25));
		lblProdutos.setBounds(387, 6, 155, 29);
		contentPane.add(lblProdutos);
		
		lblFornecedor = new JLabel("FORNECEDOR");
		lblFornecedor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 25));
		lblFornecedor.setBounds(380, 173, 173, 29);
		contentPane.add(lblFornecedor);
	}// fim do construtor
	
	/**
	 * Método responsável por exibir o status da conexão
	 */
	private void status(){
		try {
			//abrir a conexão
			con = dao.conectar();
			if(con == null) {
					System.out.println("Erro de conexão");
					lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
			} else {
				System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dbon.png")));
			}
			//NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}//fim do método status()
	/**
	 * Método responsável por setar a data no rodapé
	 */
	private void setarData() {
		// Criar objeto para trazer a data do sistema
		Date data = new Date();
		// Criar objeto para formatar a data
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// Alterar o texto da label pela data atual formatada
		lblData.setText(formatador.format(data));
	}
}//fim do código
