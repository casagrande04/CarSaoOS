package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	
	// Variáveis para configurar o bando de dados
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.44.225:3306/dbsistema";
	private String user = "root";
	private String password = "123@senac";
	// Criação de um objeto para uso da classe Connection(JDBC)
	private Connection con;
	
	/**
	 * Método responsável por abrir a conexão com o banco
	 * @return con
	 */
	public Connection conectar() {
		// Tratamento de exceções 
		try {
			// As linhas abaixo abrem a conexão com o banco
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
