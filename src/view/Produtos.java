package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.toedter.calendar.JDateChooser;

import model.DAO;

public class Produtos extends JDialog {

	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// Instancioar objetos para fluxo de bytes
	private FileInputStream fis;

	// Variável global para armazenar o tamanho da imagem(bytes)
	private int tamanho;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtBarCode;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTextField txtLocalArmazenamento;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JLabel lblFoto;
	private JButton btnCarregar;
	private JComboBox cboUNID;
	private JLabel txt;
	private JScrollPane scrollPane;
	private JList listaFornecedor;
	private JTextField txtFornecedor;
	private JTextField txtIDFornecedor;
	private JButton btnBuscar;
	private JTextField txtDescricao;
	private JLabel lblProduto;
	private JTextField txtProduto;
	private JTextField txtFabricante;
	private JTextField txtLote;
	private JLabel lblDataDeValidade;
	private JTextField txtCusto;
	private JTextField txtLucro;
	private JLabel lblCusto;
	private JLabel lblLucro;
	private JDateChooser dateEnt;
	private JDateChooser dateVal;
	private JButton btnBuscar_1;
	private JButton btnBuscar_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtos dialog = new Produtos();
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
	public Produtos() {
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/note.png")));
		setTitle("Produtos\r\n");
		setBounds(100, 100, 845, 661);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(317, 41, 206, 82);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(11, 38, 185, 54);
		panel.add(scrollPane);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		listaFornecedor = new JList();
		scrollPane.setViewportView(listaFornecedor);
		listaFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
				listaFornecedor.setEnabled(true);
				;
			}
		});
		listaFornecedor.setBorder(null);

		txtFornecedor = new JTextField();
		txtFornecedor.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtFornecedor.setBounds(10, 21, 186, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("ID do Fornecedor*");
		lblNewLabel_2.setBounds(10, 52, 104, 14);
		panel.add(lblNewLabel_2);

		txtIDFornecedor = new JTextField();
		txtIDFornecedor.setEditable(false);
		txtIDFornecedor.setBounds(114, 49, 71, 20);
		panel.add(txtIDFornecedor);
		txtIDFornecedor.setColumns(10);

		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtID.setBounds(158, 85, 86, 20);
		getContentPane().add(txtID);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblNewLabel.setBounds(46, 87, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblBarcode = new JLabel("BarCode");
		lblBarcode.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblBarcode.setBounds(46, 138, 67, 14);
		getContentPane().add(lblBarcode);

		txtBarCode = new JTextField();
		txtBarCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		txtBarCode.setColumns(10);
		txtBarCode.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtBarCode.setBounds(158, 136, 309, 20);
		getContentPane().add(txtBarCode);
		// Uso do Validador para limitar o número de caracteres
		txtBarCode.setDocument(new Validador(20));

		JLabel lblEstoque = new JLabel("Estoque*");
		lblEstoque.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblEstoque.setBounds(46, 357, 86, 14);
		getContentPane().add(lblEstoque);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoque.setColumns(10);
		txtEstoque.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtEstoque.setBounds(158, 355, 309, 20);
		getContentPane().add(txtEstoque);

		JLabel lblEstoqueMin = new JLabel("Estoque Min. *");
		lblEstoqueMin.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblEstoqueMin.setBounds(46, 399, 107, 14);
		getContentPane().add(lblEstoqueMin);

		txtEstoqueMin = new JTextField();
		txtEstoqueMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoqueMin.setColumns(10);
		txtEstoqueMin.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtEstoqueMin.setBounds(158, 397, 309, 20);
		getContentPane().add(txtEstoqueMin);

		btnCarregar = new JButton("Carregar imagem");
		btnCarregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnCarregar.setBounds(602, 273, 139, 23);
		getContentPane().add(btnCarregar);

		JLabel lblUnid = new JLabel("UNID. *\r\n");
		lblUnid.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblUnid.setBounds(361, 480, 61, 14);
		getContentPane().add(lblUnid);

		cboUNID = new JComboBox();
		cboUNID.setModel(
				new DefaultComboBoxModel(new String[] { "", "UN", "CX", "PC", "KG", "g", "M", "cm", "L", "mL" }));
		cboUNID.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		cboUNID.setBounds(421, 476, 46, 22);
		getContentPane().add(cboUNID);

		JLabel lblLocalDeArmazem = new JLabel("Local ");
		lblLocalDeArmazem.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblLocalDeArmazem.setBounds(46, 422, 52, 14);
		getContentPane().add(lblLocalDeArmazem);

		txtLocalArmazenamento = new JTextField();
		txtLocalArmazenamento.setColumns(10);
		txtLocalArmazenamento.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtLocalArmazenamento.setBounds(159, 433, 308, 20);
		getContentPane().add(txtLocalArmazenamento);
		// Uso do Validador para limitar o número de caracteres
		txtLocalArmazenamento.setDocument(new Validador(50));

		JLabel lblArmazenamento = new JLabel("Armazenamento");
		lblArmazenamento.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblArmazenamento.setBounds(46, 436, 121, 21);
		getContentPane().add(lblArmazenamento);

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/adicionar.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(204, 531, 60, 60);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarProdutos();
			}
		});
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/editar.png")));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(316, 531, 60, 60);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirProdutos();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/excluir.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(433, 531, 60, 60);
		getContentPane().add(btnExcluir);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Borracha.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(547, 531, 60, 60);
		getContentPane().add(btnLimpar);

		lblFoto = new JLabel("");
		lblFoto.setBorder(new LineBorder(new Color(128, 0, 0), 3, true));
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/addimage.png")));
		lblFoto.setBounds(533, 13, 256, 256);
		getContentPane().add(lblFoto);

		txt = new JLabel("Descrição*");
		txt.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		txt.setBounds(46, 210, 86, 20);
		getContentPane().add(txt);

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBorder(null);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Pesquisar.png")));
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBounds(254, 77, 32, 32);
		getContentPane().add(btnBuscar);

		txtDescricao = new JTextField();
		txtDescricao.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtDescricao.setBounds(158, 210, 309, 59);
		getContentPane().add(txtDescricao);
		txtDescricao.setColumns(10);

		lblProduto = new JLabel("Produto*");
		lblProduto.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblProduto.setBounds(46, 173, 67, 14);
		getContentPane().add(lblProduto);

		txtProduto = new JTextField();
		txtProduto.setColumns(10);
		txtProduto.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtProduto.setBounds(158, 171, 309, 20);
		getContentPane().add(txtProduto);

		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblFabricante.setBounds(46, 283, 86, 20);
		getContentPane().add(lblFabricante);

		txtFabricante = new JTextField();
		txtFabricante.setColumns(10);
		txtFabricante.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtFabricante.setBounds(158, 281, 309, 20);
		getContentPane().add(txtFabricante);

		JLabel lblLote = new JLabel("Lote*");
		lblLote.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblLote.setBounds(46, 317, 86, 20);
		getContentPane().add(lblLote);

		txtLote = new JTextField();
		txtLote.setColumns(10);
		txtLote.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtLote.setBounds(158, 319, 309, 20);
		getContentPane().add(txtLote);

		JLabel lblDataDeEntrada = new JLabel("Data de Entrada");
		lblDataDeEntrada.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblDataDeEntrada.setBounds(570, 309, 116, 20);
		getContentPane().add(lblDataDeEntrada);

		lblDataDeValidade = new JLabel("Data de Validade*");
		lblDataDeValidade.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblDataDeValidade.setBounds(563, 356, 120, 20);
		getContentPane().add(lblDataDeValidade);

		txtCusto = new JTextField();
		txtCusto.setColumns(10);
		txtCusto.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtCusto.setBounds(550, 418, 133, 20);
		getContentPane().add(txtCusto);

		txtLucro = new JTextField();
		txtLucro.setColumns(10);
		txtLucro.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtLucro.setBounds(550, 464, 133, 20);
		getContentPane().add(txtLucro);

		lblCusto = new JLabel("Custo*");
		lblCusto.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblCusto.setBounds(599, 399, 52, 20);
		getContentPane().add(lblCusto);

		lblLucro = new JLabel("Lucro");
		lblLucro.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblLucro.setBounds(599, 446, 52, 20);
		getContentPane().add(lblLucro);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(128, 0, 0), 3, true));
		panel_1.setBounds(533, 302, 173, 196);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		dateEnt = new JDateChooser();
		dateEnt.setBounds(17, 28, 134, 20);
		panel_1.add(dateEnt);

		dateVal = new JDateChooser();
		dateVal.setBounds(17, 76, 134, 20);
		panel_1.add(dateVal);

		btnBuscar_1 = new JButton("");
		btnBuscar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarBarcode();
			}
		});
		btnBuscar_1.setIcon(new ImageIcon(Produtos.class.getResource("/img/Pesquisar.png")));
		btnBuscar_1.setContentAreaFilled(false);
		btnBuscar_1.setBorder(null);
		btnBuscar_1.setBounds(477, 135, 32, 32);
		getContentPane().add(btnBuscar_1);

		btnBuscar_2 = new JButton("");
		btnBuscar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarProduto();
			}
		});
		btnBuscar_2.setIcon(new ImageIcon(Produtos.class.getResource("/img/Pesquisar.png")));
		btnBuscar_2.setContentAreaFilled(false);
		btnBuscar_2.setBorder(null);
		btnBuscar_2.setBounds(477, 170, 32, 32);
		getContentPane().add(btnBuscar_2);

	}// FIM DO CONSTRUTOR

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
			} catch (Exception e) {
				System.out.println(e);

			}
		}

	}

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtFornecedor.setText(null);
		txtProduto.setText(null);
		txtFabricante.setText(null);
		txtLote.setText(null);
		// datas entrada e saida
		// datas entrada e saida
		txtCusto.setText(null);
		txtLucro.setText(null);
		txtID.setText(null);
		txtBarCode.setText(null);
		txtDescricao.setText(null);
		lblFoto.setIcon(null);
		txtEstoque.setText(null);
		txtEstoqueMin.setText(null);

		txtLocalArmazenamento.setText(null);
		cboUNID.setSelectedItem(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscar.setEnabled(true);
		lblFoto.setIcon(new ImageIcon(Principal.class.getResource("/img/file camera.png")));

	}// fim do método limparCampos()

	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatórios
		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtDescricao.requestFocus();
		} else if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto");
			txtProduto.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto");
			txtLote.requestFocus();
		} else if (txtFabricante.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fabricante do produto");
			txtFabricante.requestFocus();

			// } else if (dateEnt.getText().isEmpty()) {
			// JOptionPane.showMessageDialog(null, "Preencha a data de entrada do produto");
			// dataEnt.requestFocus();
			// } else if (dateVal.getText().isEmpty()) {
			// JOptionPane.showMessageDialog(null, "Preencha a data de validade do
			// produto");
			// dataVal.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto");
			txtCusto.requestFocus();
		} else if (txtLucro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lucro obtido em cima do produto");
			txtLucro.requestFocus();

		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque do produto");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();

		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {

			// Lógica Principal
			// CRUD Create
			String create = "insert into produtos(barcode,descricao,produto,lote,fabricante,dataent,dataval,custo,lucro,foto,estoque,estoquemin,unidademedida,localarmazenagem,idfornecedor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql - CRUD Read)
				pst = con.prepareStatement(create);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtDescricao.getText());
				pst.setString(3, txtProduto.getText());
				pst.setString(4, txtLote.getText());
				pst.setString(5, txtFabricante.getText());
				// pst.setString(6, dateEnt.getText());
				// pst.setString(7, dateVal.getText());
				pst.setString(8, txtCusto.getText());
				pst.setString(9, txtLucro.getText());
				pst.setBlob(10, fis, tamanho);
				pst.setString(11, txtEstoque.getText());
				pst.setString(12, txtEstoqueMin.getText());

				pst.setString(13, cboUNID.getSelectedItem().toString());
				pst.setString(14, txtLocalArmazenamento.getText());
				pst.setString(15, txtIDFornecedor.getText());
				// executa a query(instrução sql (CRUD - Create))
				// pst.executeUpdate();
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto Adicionado");
				} else {
					JOptionPane.showMessageDialog(null, "Erro! Produto não adicionado.");
				}

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
	private void editarProdutos() {
		// System.out.println("teste do botão editar");
		// Validação dos campos obrigatórios
		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtDescricao.requestFocus();
		} else if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto");
			txtProduto.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto");
			txtLote.requestFocus();
		} else if (txtFabricante.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fabricante do produto");
			txtFabricante.requestFocus();
			// data de entrada
			// data de validade
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto");
			txtCusto.requestFocus();
		} else if (txtLucro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lucro obtido em cima do produto");
			txtLucro.requestFocus();

		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();

		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {
			// lógica principal
			// CRUD - Update
			String update = "update produtos set barcode=?,descricao=?,produto=?,lote=?,fabricante=?,dataent=?,dataval=?,custo=?,lucro=?,foto=?,estoque=?,estoquemin=?,unidademedida=?,localarmazenagem=?,idfornecedor=? where codigo=?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtDescricao.getText());
				pst.setString(3, txtProduto.getText());
				pst.setString(4, txtLote.getText());
				pst.setString(5, txtFabricante.getText());
				// pst.setString(6, dateEnt.getText());
				// pst.setString(7, dateVal.getText());
				pst.setString(8, txtCusto.getText());
				pst.setString(9, txtLucro.getText());
				pst.setBlob(10, fis, tamanho);
				pst.setString(11, txtEstoque.getText());
				pst.setString(12, txtEstoqueMin.getText());
				pst.setString(13, cboUNID.getSelectedItem().toString());
				pst.setString(14, txtLocalArmazenamento.getText());
				pst.setString(15, txtIDFornecedor.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do produto editado com sucesso.");
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
	private void excluirProdutos() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete
			String delete = "delete from produtos where codigo=?";
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
				JOptionPane.showMessageDialog(null, "Produto excluído");
				// fechar a conexão
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Produto não excluido. \nEste produto ainda tem um serviço pendente");
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
	 * Método usado para listar o nome dos usuários na lista
	 */
	private void listarFornecedor() {
		// System.out.println("teste");
		// A linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// Setar o modelo (vetor na lista)
		listaFornecedor.setModel(modelo);
		// Query (instrução sql)
		String readListaFornecedor = "select * from fornecedores where razaosocial like '" + txtFornecedor.getText()
				+ "%'" + "order by razaosocial";
		try {
			// Abrir a conexão
			con = dao.conectar();
			// Preparar a query (instrução sql)
			pst = con.prepareStatement(readListaFornecedor);
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
				if (txtFornecedor.getText().isEmpty()) {
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
	private void buscarFornecedorLista() {
		// System.out.println("Teste");
		// Variável que captura o índice da linha da lista
		int linha = listaFornecedor.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução SQL)
			// limit (0,1) -> seleciona o índice 0 do vetor e 1 usuário da lista
			String readListaFornecedor = "select * from fornecedores where razaosocial like '" + txtFornecedor.getText()
					+ "%'" + "order by razaosocial limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query para execução
				pst = con.prepareStatement(readListaFornecedor);
				// executar e obter o resultado
				rs = pst.executeQuery();

				if (rs.next()) {
					// Esconder a lista
					scrollPane.setVisible(false);
					// Setar os campos
					txtFornecedor.setText(rs.getString(2));
					txtIDFornecedor.setText(rs.getString(1));
					// Validação (liberação dos botões)
					listaFornecedor.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
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

	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscar() {
		
		String numOS = JOptionPane.showInputDialog("ID do produto");
		// dica - testar o evento primeiro
		// System.out.println("teste do botão buscar");
		// Criar uma variável com a query (instrução do banco)
		String readCodigo = "select * from produtos where codigo = ?";
		// tratamento de exceções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(readCodigo);
			// Substituir a ?(Parâmetro) pelo número da OS
			pst.setString(1, numOS);
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				//area de texto deve ser utilizado getNString
				txtDescricao.setText(rs.getNString(3));
				txtProduto.setText(rs.getString(4));
				txtLote.setText(rs.getString(5));
				txtFabricante.setText(rs.getString(6));
				//setar a data do JCalendar
				//passo 1: receber a data do mysql
				String setarDataent = rs.getString(7);
				//System.out.println(setarDataent);//apoio a lógica
				//passo 2: formatar a data para o JCalendar
				Date dataEntrada = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataent);
				//passo 3: exibir o resultado no JCalendar
				dateEnt.setDate(dataEntrada);
				
				//setar a data do JCalendar
				//passo 1: receber a data do mysql
				String setarDataval = rs.getString(8);
				//System.out.println(setarDataent);//apoio a lógica
				//passo 2: formatar a data para o JCalendar
				Date dataValidade = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataval);
				//passo 3: exibir o resultado no JCalendar
				dateVal.setDate(dataValidade);
				txtCusto.setText(rs.getString(9));
				txtLucro.setText(rs.getString(10));
				txtEstoque.setText(rs.getString(12));
				txtEstoqueMin.setText(rs.getString(13));
				cboUNID.setSelectedItem(rs.getString(14));
				txtLocalArmazenamento.setText(rs.getString(15));
				txtFornecedor.setText(rs.getString(16));
				Blob blob = (Blob) rs.getBlob(11);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				// Validação (liberação dos botões)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "ID do produto não encontrado");
				// Validação (liberação do botão adicionar)
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO METODO BUSCAR

	private void buscarBarcode() {

		// Captura do número da OS (sem usar a caixa de texto)
		String numOS = JOptionPane.showInputDialog("ID do Produto");

		// dica - testar o evento primeiro
		// System.out.println("teste do botão buscar");
		// Criar uma variável com a query (instrução do banco)
		String read = "select * from produtos where barcode = ?";
		// tratamento de exceções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			// Substituir a ?(Parâmetro) pelo número da OS
			pst.setString(1, numOS);
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				txtDescricao.setText(rs.getString(3));
				txtProduto.setText(rs.getString(4));
				txtLote.setText(rs.getString(5));
				txtFabricante.setText(rs.getString(6));
				// dataEnt.setText(rs.getString(7));
				// dataVal.setText(rs.getString(8));
				txtCusto.setText(rs.getString(9));
				txtLucro.setText(rs.getString(10));
				txtEstoque.setText(rs.getString(12));
				txtEstoqueMin.setText(rs.getString(13));

				cboUNID.setSelectedItem(rs.getString(14));
				txtLocalArmazenamento.setText(rs.getString(15));
				txtFornecedor.setText(rs.getString(16));
				Blob blob = (Blob) rs.getBlob(11);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				// Validação (liberação dos botões)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "ID do produto inexistente");
				// Validação (liberação do botão adicionar)
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do metodo buscar barcode

	
	//metodo para buscar produto
	private void buscarProduto() {

		// Captura do número da OS (sem usar a caixa de texto)
		String numOS = JOptionPane.showInputDialog("Nome do Produto");

		// dica - testar o evento primeiro
		// System.out.println("teste do botão buscar");
		// Criar uma variável com a query (instrução do banco)
		String read = "select * from produtos where produto = ?";
		// tratamento de exceções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			// Substituir a ?(Parâmetro) pelo número da OS
			pst.setString(1, numOS);
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				txtDescricao.setText(rs.getString(3));
				txtProduto.setText(rs.getString(4));
				txtLote.setText(rs.getString(5));
				txtFabricante.setText(rs.getString(6));
				// dataEnt.setText(rs.getString(7));
				// dataVal.setText(rs.getString(8));
				txtCusto.setText(rs.getString(9));
				txtLucro.setText(rs.getString(10));
				txtEstoque.setText(rs.getString(12));
				txtEstoqueMin.setText(rs.getString(13));
				cboUNID.setSelectedItem(rs.getString(14));
				txtLocalArmazenamento.setText(rs.getString(15));
				txtFornecedor.setText(rs.getString(16));
				Blob blob = (Blob) rs.getBlob(11);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				// Validação (liberação dos botões)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "Produto não encontrado");
				// Validação (liberação do botão adicionar)
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do metodo buscar produto
}
