package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import view.Clientes.Validador;

import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Servicos extends JDialog {
	// Instanciar objetos JDBC
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtEquipamento;
	private JTextField txtValor;
	private JTextField txtIDCli;
	private JButton btnBuscar;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JTextField txtCliente;
	private JScrollPane scrollPane;
	private JList listaClient;
	private JButton btnOS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
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
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/note.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setModal(true);
		setBounds(100, 100, 561, 322);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("OS");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 36, 30, 14);
		getContentPane().add(lblNewLabel);
		
		txtOS = new JTextField();
		txtOS.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtOS.setEditable(false);
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}
			}});
		txtOS.setBounds(102, 33, 115, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 82, 30, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtData = new JTextField();
		txtData.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtData.setEditable(false);
		txtData.setBounds(102, 79, 129, 20);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Serviço");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(10, 127, 90, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtEquipamento = new JTextField();
		txtEquipamento.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtEquipamento.setBounds(102, 124, 240, 20);
		getContentPane().add(txtEquipamento);
		txtEquipamento.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
				txtEquipamento.setDocument(new Validador(200));
		
		JLabel lblNewLabel_3 = new JLabel((String) null);
		lblNewLabel_3.setBounds(10, 136, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Valor");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(10, 174, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		txtValor = new JTextField();
		txtValor.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}}
		});
		txtValor.setBounds(102, 171, 129, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
				txtValor.setDocument(new Validador(15));
		
		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/adicionar.png")));
		btnAdicionar.setBounds(20, 208, 64, 64);
		getContentPane().add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarServico();
			}
		});
		btnEditar.setBorder(null);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editar.png")));
		btnEditar.setBounds(122, 208, 64, 64);
		getContentPane().add(btnEditar);
		
		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirServico();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/excluir.png")));
		btnExcluir.setBounds(234, 208, 64, 64);
		getContentPane().add(btnExcluir);
		
		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBorder(null);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/Pesquisar.png")));
		btnBuscar.setBounds(221, 23, 32, 32);
		getContentPane().add(btnBuscar);
		
		btnLimpar = new JButton("");
		btnLimpar.setBorder(null);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/Borracha.png")));
		btnLimpar.setBounds(346, 208, 64, 64);
		getContentPane().add(btnLimpar);

		// substituir o click pela tecla <ENTER> em um botão
		getRootPane().setDefaultButton(btnBuscar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(285, 11, 194, 77);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(10, 33, 174, 61);
		panel.add(scrollPane);
		
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
		scrollPane.setVisible(false);
		
		txtIDCli = new JTextField();
		txtIDCli.setEditable(false);
		txtIDCli.setBounds(88, 43, 69, 20);
		panel.add(txtIDCli);
		txtIDCli.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}
			}});
		txtIDCli.setColumns(10);
		
		txtCliente = new JTextField();
		txtCliente.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		txtCliente.setColumns(10);
		txtCliente.setBounds(10, 15, 174, 20);
		panel.add(txtCliente);
		

				
		JLabel lblNewLabel_5 = new JLabel("ID do Cliente");
		lblNewLabel_5.setBounds(10, 46, 79, 14);
		panel.add(lblNewLabel_5);
		
		btnOS = new JButton("");
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setBorder(null);
		btnOS.setContentAreaFilled(false);
		btnOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/pdficon2.png")));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(459, 208, 64, 64);
		getContentPane().add(btnOS);
		
	}// Fim do Construtor

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtIDCli.setText(null);
		txtCliente.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamento.setText(null);
		txtValor.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscar.setEnabled(true);

	}// fim do método limparCampos()
	
	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscar() {
		
		// Captura do número da OS (sem usar a caixa de texto)
		String numOS = JOptionPane.showInputDialog("Número da OS");
		
		// dica - testar o evento primeiro
		// System.out.println("teste do botão buscar");
		// Criar uma variável com a query (instrução do banco)
		String read = "select * from servicos where os = ?";
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
				// preencher as caixas de texto com as informações
				txtOS.setText(rs.getString(1)); // 1º Campo da tabela ID
				txtData.setText(rs.getString(2)); // 3º Campo da tabela (Fone)
				txtEquipamento.setText(rs.getString(3)); // 3º Campo da tabela (Fone)
				txtValor.setText(rs.getString(4));
				txtIDCli.setText(rs.getString(5));
				// Valição (liberação dos botões alterar e excluir)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnBuscar.setEnabled(false);
				btnAdicionar.setEnabled(false);

			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "OS inexistente");
				// Validação (liberação do botão adicionar)
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(false);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO METODO BUSCAR
	
	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatórios
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Serviço do Cliente");
			txtEquipamento.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Valor do Serviço");
			txtValor.requestFocus();
		} else {
			// Lógica Principal
			// CRUD Create
			String create = "insert into servicos(servico,valor,idcli) values (?,?,?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql - CRUD Read)
				pst = con.prepareStatement(create);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtValor.getText());
				pst.setString(3, txtIDCli.getText());
				// executa a query(instrução sql (CRUD - Create))
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Serviço Adicionado");
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
	private void editarServico() {
		// System.out.println("teste do botão editar");
		// Validação dos campos obrigatórios
		 if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Equipamento do Cliente");
			txtEquipamento.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Valor do Serviço");
			txtValor.requestFocus();
		} else {
			// Lógica Principal
			// CRUD Create
			String update = "update servicos set os=?,dataOS=?,servico=?,valor=? where idcli =?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(5, txtIDCli.getText());
				pst.setString(1, txtOS.getText());
				pst.setString(2, txtData.getText());
				pst.setString(3, txtEquipamento.getText());
				pst.setString(4, txtValor.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do serviço editado com sucesso.");
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
	private void excluirServico() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste serviço?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete
			String delete = "delete from servicos where idcli=?";
			// tratamento de exceções
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtIDCli.getText());
				// executar a query
				pst.executeUpdate();
				// limpar campos
				limparCampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, "Serviço excluído");
				// fechar a conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
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
	private void listarClientes() {
		// System.out.println("teste");
		// A linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// Setar o modelo (vetor na lista)
		listaClient.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select * from cliente where nome like '" + txtCliente.getText() + "%'" + "order by nome";
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
				if (txtCliente.getText().isEmpty()) {
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
			String readListaCliente = "select * from cliente where nome like '" + txtCliente.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				//abrir a conexão
				con = dao.conectar();
				//preparar a query para execução
				pst = con.prepareStatement(readListaCliente);
				// executar e obter o resultado
				rs = pst.executeQuery();
				
				if(rs.next()) {
					// Esconder a lista
					scrollPane.setVisible(false);
					// Setar os campos
					txtCliente.setText(rs.getString(2));
					txtIDCli.setText(rs.getString(1)); 	
					// Validação (liberação dos botões)
					listaClient.setEnabled(false);

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
	
	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
		// instanciar objeto para usar os métodos da biblioteca
		Document document = new Document();
		// documento pdf
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			String readOS = " select * from servicos where os = ?";
			// conexão com o banco
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql)
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				// executar a query
				rs = pst.executeQuery();
				// se existir a OS
				if (rs.next()) {					
					//document.add(new Paragraph("OS: " + rs.getString(1)));
					Paragraph os = new Paragraph ("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);
					
					Paragraph equipamento = new Paragraph ("Equipamento: " + rs.getString(3));
					equipamento.setAlignment(Element.ALIGN_LEFT);
					document.add(equipamento);
					
					Paragraph valor = new Paragraph ("Valor: " + rs.getString(4));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
					
					Paragraph data = new Paragraph ("Data: " + rs.getString(2));
					data.setAlignment(Element.ALIGN_LEFT);
					document.add(data);
				
					//imprimir imagens
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/os.png"));
					//resolução da imagem
					imagem.scaleToFit(75,75);
					//(x,y)
					imagem.setAbsolutePosition(20, 625);
					document.add(imagem);					
				}
				// fechar a conexão com o banco
				con.close();
				} catch (Exception e) {
					System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO RELATÓRIO OS

}// FIM DO CÓDIGO
