/*
 * criar por fora o database: casos_de_uso_db
 * e usuário "admin" com senha "password"
 * 
 * */

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DatabaseOperator {
	private Connection c = null;
	private Statement stmt = null;
	private static DatabaseOperator database_instance;

	private static String URL = "jdbc:postgresql://127.0.0.1:5432/casos_de_uso_db";
	private static String USER = "admin";
	private static String PASSWORD = "password";
	
	private DatabaseOperator(){
		// do nothing // SINGLETON
	}
	
	public static DatabaseOperator getInstance(){
		if (database_instance == null){
			database_instance = new DatabaseOperator();
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Where is your PostgreSQL JDBC Driver? "
						+ "Include in your library path!");
				e.printStackTrace();
				System.exit(0);
			}
	 
			System.out.println("PostgreSQL JDBC Driver Registered!");
		}
		
		return database_instance;
	}
	
     public void createTableProjects(){
    	 //exemplo de criação de tabela
    	 try {
			c = DriverManager.getConnection(DatabaseOperator.URL, 
					 						 DatabaseOperator.USER, 
					 						 DatabaseOperator.PASSWORD);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE Projects " +
			          	"(ID INT PRIMARY KEY     NOT NULL," +
			          	" NAME           TEXT    NOT NULL, " +
			          	" DATE           NUMERIC(30)     NOT NULL)"; // storage date in milliseconds
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully");
			stmt.close();
			c.close();
			System.out.println("Opened database successfully closed");
    	 } catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	 }
     }
}