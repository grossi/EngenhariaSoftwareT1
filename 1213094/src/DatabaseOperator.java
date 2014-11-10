/*
 * criar por fora o database: casos_de_uso_db
 * e usuário "admin" com senha "password"
 * 
 * */

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;


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
		}
		
		return database_instance;
	}
	
	public void initialize(){

		findDriver();
		connectToDabatase();
		// initialize all tables
		createTableProjectsIfNotExists();
	}
	
	private void findDriver(){
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private void connectToDabatase(){
		try {
			c = DriverManager.getConnection(DatabaseOperator.URL, 
					 						 DatabaseOperator.USER, 
					 						 DatabaseOperator.PASSWORD);
		}
		catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}	
	}
	
    private void createTableProjectsIfNotExists(){
    	try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Projects " +
		          		"(id 		SERIAL PRIMARY KEY  NOT NULL," +
			          	" name      TEXT    		 	NOT NULL, " +
			          	" date      NUMERIC(30)     	NOT NULL)"; // storage date in milliseconds
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully");
			stmt.close();
			
			System.out.println("Opened database successfully closed");
			
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    }
    
    public String getProjectName(int id){
    	String name = "";
    	try {
			stmt = c.createStatement();
			
			String sql = "SELECT name FROM Projects";
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				name = result.getString("name");
			}
			
			System.out.println("Opened database successfully closed");
			
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return name;
    }
     
    public void insertProject(String name){
    	try { 			
    		java.util.Date date= new java.util.Date();
 			
 			if(!ifProjectExists(name)){
 				stmt = c.createStatement();
 	 			String sql = "INSERT INTO Projects (name, date) " +
 	 			           	 " VALUES ( '" + name + "','" + String.valueOf(date.getTime()) + "')"; // storage date in milliseconds
 	 			stmt.executeUpdate(sql);
 	 			System.out.println("Project inserted successfully!");
 	 			stmt.close();
 	        }
 			
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
    }
    
    public void insertActor(String name, String id){
    	try { 			
 			
 			if(!ifActorExists(name, id)){
 				stmt = c.createStatement();
 	 			String sql = "INSERT INTO Actors (name, id) " +
 	 			           	 " VALUES ( '" + name + "','" + id + "')"; 
 	 			stmt.executeUpdate(sql);
 	 			System.out.println("Actor inserted successfully!");
 	 			stmt.close();
 	        }
 			
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
    }
    
    public void insertUseCase(String name, String id){
    	try { 			
 			if(!ifUseCaseExists(name, id)){
 				stmt = c.createStatement();
 	 			String sql = "INSERT INTO Use_cases (name, id) " +
 	 			           	 " VALUES ( '" + name + "','" + id + "')"; // storage date in milliseconds
 	 			stmt.executeUpdate(sql);
 	 			System.out.println("Use_case inserted successfully!");
 	 			stmt.close();
 	        }
 			
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
    }
     
    private boolean ifProjectExists(String name){
    	try {
    		stmt = c.createStatement();
  			String sql = "SELECT name " +
  			           	 "FROM Projects "+
  			           	 "WHERE name = '" + name + "';";

  			ResultSet result = stmt.executeQuery(sql);
  			while (result.next()){
  				System.out.println("Project already exists!");
  				return true;
  			}
  			System.out.println("Project not exists!");
  			stmt.close();
  			return false;
      	} catch ( Exception e ) {
      		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      		System.exit(0);
      	}
		return false;
    }
    
    private boolean ifActorExists(String name, String id){
    	try {
    		stmt = c.createStatement();
  			String sql = "SELECT name " +
			           	 "FROM Actors "+
			           	 "WHERE name = '" + name + "AND" +
			           	 "project = " + id + "';";
  			
  			ResultSet result = stmt.executeQuery(sql);
  			while (result.next()){
  				System.out.println("Actor already exists!");
  				return true;
  			}
  			System.out.println("Actor not exists!");
  			stmt.close();
  			return false;
      	} catch ( Exception e ) {
      		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      		System.exit(0);
      	}
		return false;
    }

    private boolean ifUseCaseExists(String name, String id){
    	try {
    		stmt = c.createStatement();
  			String sql = "SELECT name " +
		           	 "FROM Use_cases "+
		           	 "WHERE name = '" + name + "AND" +
		           	 "project = " + id + "';";
  			ResultSet result = stmt.executeQuery(sql);
  			while (result.next()){
  				System.out.println("Use_case already exists!");
  				return true;
  			}
  			System.out.println("Use_case not exists!");
  			stmt.close();
  			return false;
      	} catch ( Exception e ) {
      		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      		System.exit(0);
      	}
		return false;
    }
    
    public Vector<String> listProjects(){
    	Vector<String> project_list = new Vector<String>();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT name FROM Projects";
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				project_list.add(result.getString("name"));
			}
			stmt.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	
    	return project_list;
    }
    
    public Vector<String> listUseCases(String id){
    	Vector<String> use_case_list = new Vector<String>();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT name FROM Use_cases WHERE project = " + id;
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				use_case_list.add(result.getString("name"));
			}
			stmt.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	
    	return use_case_list;
    }
    
    public Vector<String> listActors(String id){
    	Vector<String> actors_list = new Vector<String>();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT name FROM actors WHERE project = " + id;
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				actors_list.add(result.getString("name"));
			}
			stmt.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return actors_list;
    }

	public String getProjectIdFromName(String id) {
		System.out.println("id = " + id);
    	String project_name = new String();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT id FROM projects WHERE name = " + id;
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				project_name = result.getString("name");
			}
			stmt.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return project_name;
	}
    
}