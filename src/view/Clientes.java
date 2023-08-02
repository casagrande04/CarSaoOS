package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;

public class Clientes extends JDialog {
	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JTextField txtComplemento;
	private JTextField txtEmail;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JTextField txtReferencia;
	private JTextField txtNumero;
	private JTextField txtCEP;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JLabel lblUF;
	private JComboBox cboUF;
	private JButton btnBuscarCep;
	private JScrollPane scrollPane;
	private JList listaClient;
	private JFormattedTextField txtFone;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
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
	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Clicar no painel do JDialog
				scrollPane.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/note.png")));
		setTitle("Cadastro de Cliente");
		setResizable(false);
		setBounds(100, 100, 625, 497);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(122, 70, 290, 60);
		getContentPane().add(scrollPane);
		
		listaClient = new JList();
		listaClient.setBorder(null);
		listaClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
				listaClient.setEnabled(true);
			}
		});
		scrollPane.setViewportView(listaClient);

		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(122, 9, 86, 20);
		getContentPane().add(txtID);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel.setBounds(24, 11, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(24, 91, 46, 16);
		getContentPane().add(lblNewLabel_2);

		

		
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtNome.setColumns(10);
		txtNome.setBounds(122, 50, 290, 20);
		getContentPane().add(txtNome);
		// Uso do Validador para limitar o número de caracteres
		txtNome.setDocument(new Validador(50));
		

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(24, 51, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(24, 135, 46, 14);
		getContentPane().add(lblNewLabel_3);

		
		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/adicionar.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(94, 387, 60, 60);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarContato();
			}
		});
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/editar.png")));
		btnEditar.setEnabled(false);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(192, 387, 60, 60);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/excluir.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(291, 387, 60, 60);
		getContentPane().add(btnExcluir);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/Borracha.png")));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(398, 387, 60, 60);
		getContentPane().add(btnLimpar);

		JLabel lblNewLabel_4 = new JLabel("Endereço");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(24, 215, 60, 14);
		getContentPane().add(lblNewLabel_4);

		txtEndereco = new JTextField();
		txtEndereco.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtEndereco.setBounds(122, 213, 290, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
		txtEndereco.setDocument(new Validador(50));

		JLabel lblNewLabel_5 = new JLabel("Complemento");
		lblNewLabel_5.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(24, 254, 92, 14);
		getContentPane().add(lblNewLabel_5);

		txtComplemento = new JTextField();
		txtComplemento.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtComplemento.setBounds(122, 252, 199, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
		txtComplemento.setDocument(new Validador(20));

		txtEmail = new JTextField();
		txtEmail.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtEmail.setBounds(122, 134, 290, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
		txtEmail.setDocument(new Validador(50));

		JLabel lblReferencia = new JLabel("Referência");
		lblReferencia.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblReferencia.setBounds(24, 336, 92, 14);
		getContentPane().add(lblReferencia);

		txtReferencia = new JTextField();
		txtReferencia.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtReferencia.setBounds(122, 334, 290, 20);
		getContentPane().add(txtReferencia);
		txtReferencia.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
		txtReferencia.setDocument(new Validador(50));

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtNumero.setBounds(482, 213, 94, 20);
		getContentPane().add(txtNumero);
		// Uso do Validador para limitar o número de caracteres
		txtNumero.setDocument(new Validador(10));

		JLabel lblNewLabel_4_1 = new JLabel("Número");
		lblNewLabel_4_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_4_1.setBounds(422, 215, 60, 14);
		getContentPane().add(lblNewLabel_4_1);

		JLabel lblNewLabel_4_2 = new JLabel("CEP");
		lblNewLabel_4_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_4_2.setBounds(24, 176, 60, 14);
		getContentPane().add(lblNewLabel_4_2);

		txtCEP = new JTextField();
		txtCEP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}
		}});
		txtCEP.setColumns(10);
		txtCEP.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtCEP.setBounds(122, 174, 145, 20);
		getContentPane().add(txtCEP);
		// Uso do Validador para limitar o número de caracteres
		txtCEP.setDocument(new Validador(10));
		

		JLabel lblNewLabel_4_1_1 = new JLabel("Bairro");
		lblNewLabel_4_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_4_1_1.setBounds(331, 254, 60, 14);
		getContentPane().add(lblNewLabel_4_1_1);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtBairro.setBounds(379, 252, 197, 20);
		getContentPane().add(txtBairro);
		// Uso do Validador para limitar o número de caracteres
		txtBairro.setDocument(new Validador(30));

		JLabel lblNewLabel_5_1 = new JLabel("Cidade");
		lblNewLabel_5_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_5_1.setBounds(24, 296, 92, 14);
		getContentPane().add(lblNewLabel_5_1);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtCidade.setBounds(122, 294, 290, 20);
		getContentPane().add(txtCidade);
		// Uso do Validador para limitar o número de caracteres
		txtCidade.setDocument(new Validador(30));

		lblUF = new JLabel("UF");
		lblUF.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblUF.setBounds(422, 296, 28, 14);
		getContentPane().add(lblUF);

		cboUF = new JComboBox();
		cboUF.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
						"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUF.setBounds(447, 292, 46, 22);
		getContentPane().add(cboUF);

		btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(277, 173, 114, 23);
		getContentPane().add(btnBuscarCep);
		
		// Método para marcar o formato do texto
				MaskFormatter msf = null;
				try { msf = new MaskFormatter("(##)#####-####");			
				} catch (Exception e) {
					e.printStackTrace();
				}
		txtFone = new JFormattedTextField(msf);
		txtFone.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtFone.setBounds(122, 91, 290, 20);
		getContentPane().add(txtFone);



	}// fim do método construtor

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtEmail.setText(null);
		txtFone.setText(null);
		txtEndereco.setText(null);
		txtComplemento.setText(null);
		txtCEP.setText(null);
		txtNumero.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUF.setSelectedItem(null);
		txtReferencia.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		listaClient.setEnabled(true);

	}// fim do método limparCampos()

	
	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do cliente");
			txtFone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do cliente");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do cliente");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do cliente");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do cliente");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do cliente");
			txtCidade.requestFocus();
		} else {

			// Lógica Principal
			// CRUD Create
			String create = "insert into cliente(nome,fone,email,cep,endereco,numero,comp,bairro,cidade,uf,ref) values (?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql - CRUD Read)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtCEP.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.setString(11, txtReferencia.getText());
				// executa a query(instrução sql (CRUD - Create))
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Cliente Adicionado");
				// limpar os campos
				limparCampos();
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}// fim do método adicionar

	/**
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarContato() {
		// System.out.println("teste do botão editar");
		// Validação dos campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do cliente");
			txtFone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do cliente");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do cliente");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do cliente");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do cliente");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do cliente");
			txtCidade.requestFocus();
		} else {
			// lógica principal
			// CRUD - Update
			String update = "update cliente set nome=?,fone=?,email=?,cep=?,endereco=?,numero=?,comp=?,bairro=?,cidade=?,uf=?,ref=? where idcli =?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(7, txtID.getText());
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtCEP.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.setString(11, txtReferencia.getText());
				pst.setString(12, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do cliente editado com sucesso.");
				// Limpar os campos
				limparCampos();
				// Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}// fim do método editar contato

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirUsuario() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste cliente?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete
			String delete = "delete from cliente where idcli=?";
			// tratamento de exceções
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// limpar campos
				limparCampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, "Cliente excluído");
				// fechar a conexão
				con.close();
				
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não excluido. \nEste cliente ainda tem um serviço pendente");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	} // fim do método excluir contato
		// PlainDocument -> recursos para formatação

	public class Validador extends PlainDocument {
		// variável que irá armazenar o número máximo de caracteres permitidos
		private int limite;
		// construtor personalizado -> será usado pela caixas de texto JTextFiled

		public Validador(int limite) {
			super();
			this.limite = limite;
		}

		// Método interno para validar o limite de caracteres
		// BadLocation -> Tratamento de exceções
		public void insertString(int ofs, String str, AttributeSet a) throws BadLocationException {
			// Se o limite não for ultrapassado permitir a digitação
			if ((getLength() + str.length()) <= limite) {
				super.insertString(ofs, str, a);
			}
		}

	} // Fim do validador

	/**
	 * buscarCep
	 */
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCEP.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						System.out.println("OK!");
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Método usado para listar o nome dos usuários na lista
	 */
	private void listarClientes() {
		// System.out.println("teste");
		// A linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// Setar o modelo (vetor na lista)
		listaClient.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select * from cliente where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			// Abrir a conexão
			con = dao.conectar();
			// Preparar a query (instrução sql)
			pst = con.prepareStatement(readLista);
			// Executar a query e trazer o resultado para lista
			rs = pst.executeQuery();
			// Uso do while para trzer os usuários enquanto existir
			while (rs.next()) {
				// Mostrar a barra de rolagem (scrollpane)
				scrollPane.setVisible(true);
				// Mostrar a lista
				// listaUsers.setVisible(true);
				// Adicionar os usuários no vetor -> lista
				modelo.addElement(rs.getString(2));
				// Esconder a lista se nenhuma letra for digitada
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
			}
			}
			// Fechar Conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	/**
	 * Método que busca o usuário selecionado na lista
	 */
	private void buscarClienteLista() {
		//System.out.println("Teste");
		// Variável que captura o índice da linha da lista
		int linha = listaClient.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução SQL)
			// limit (0,1) -> seleciona o índice 0 do vetor e 1 usuário da lista
			String readListaUsuario = "select * from cliente where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				//abrir a conexão
				con = dao.conectar();
				//preparar a query para execução
				pst = con.prepareStatement(readListaUsuario);
				// executar e obter o resultado
				rs = pst.executeQuery();
				
				if(rs.next()) {
					// Esconder a lista
					scrollPane.setVisible(false);
					// Setar os campos
					txtID.setText(rs.getString(1)); 
					txtNome.setText(rs.getString(2));
					txtFone.setText(rs.getString(3));
					txtEmail.setText(rs.getString(4)); 
					txtCEP.setText(rs.getString(5)); 
					txtEndereco.setText(rs.getString(6));
					txtNumero.setText(rs.getString(7)); 
					txtComplemento.setText(rs.getString(8)); 
					txtBairro.setText(rs.getString(9)); 
					txtCidade.setText(rs.getString(10));
					cboUF.setSelectedItem(rs.getString(11)); 
					txtReferencia.setText(rs.getString(12)); 
					// Validação (liberação dos botões)
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					listaClient.setEnabled(false);
					btnAdicionar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente");
				}
				// Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// Se não existir no banco um usuário da lista
			scrollPane.setVisible(false);
		}
		
	} // Fim método buscar Usuário AVANÇADO (Busca Google)
}// fim do código
