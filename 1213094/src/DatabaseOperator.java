/*
 * criar por fora o database: casos_de_uso_db
 * e usuário "admin" com senha "password"
 * 
 * */

import java.awt.LayoutManager;
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
		createTableActorsIfNotExists();
		createTableUseCasesIfNotExists();
		createTableLinesIfNotExists();
		createTableExtendsIfNotExists();
		createTableIncludesIfNotExists();
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

    private void createTableActorsIfNotExists(){
    	try {
			stmt = c.createStatement();
			String sql = " CREATE TABLE IF NOT EXISTS actors" +
						   " (id serial PRIMARY KEY NOT NULL," +
						    " name TEXT NOT NULL," +
						     " projectId INTEGER NOT NULL," +
						     " CONSTRAINT actors_fkey FOREIGN KEY (projectId) REFERENCES projects(id))" ;
						    
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully");
			stmt.close();
			
			System.out.println("Opened database successfully closed");
			
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    }
    
    private void createTableUseCasesIfNotExists(){
    	try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS use_cases" + 
					"		  (id serial PRIMARY KEY NOT NULL," +
					"		  name text," +
					"		  projectId INTEGER NOT NULL, " +
					" CONSTRAINT use_cases_fkey FOREIGN KEY (projectId) REFERENCES projects(id))";
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully");
			stmt.close();
			System.out.println("Opened database successfully closed");
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    }
    
    private void createTableLinesIfNotExists(){
    	try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS lines" + 
					"		  (id serial PRIMARY KEY NOT NULL," +
					"		  line text NOT NULL," +
					"		  useCaseId INTEGER NOT NULL, " +
					"		  position INTEGER NOT NULL, " +
					" CONSTRAINT lines_fkey FOREIGN KEY (useCaseId) REFERENCES use_cases(id))";
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully");
			stmt.close();
			System.out.println("Opened database successfully closed");
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    }
    
    private void createTableExtendsIfNotExists(){
    	try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS extends" + 
					"		  (id serial PRIMARY KEY NOT NULL," +
					"		  useCaseId INTEGER NOT NULL REFERENCES use_cases(id), " +
					"		  lineId INTEGER NOT NULL REFERENCES lines(id), " +
					"		  extendedUseCaseId INTEGER NOT NULL REFERENCES use_cases(id)) ";
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully");
			stmt.close();
			System.out.println("Opened database successfully closed");
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    }
    
    private void createTableIncludesIfNotExists(){
    	try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS includes" + 
					"		  (id serial PRIMARY KEY NOT NULL," +
					"		  useCaseId INTEGER NOT NULL REFERENCES use_cases(id), " +
					"		  lineId INTEGER NOT NULL REFERENCES lines(id), " +
					"		  includedUseCaseId INTEGER NOT NULL REFERENCES use_cases(id)) ";
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully");
			stmt.close();
			System.out.println("Opened database successfully closed");
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    }

    
    public String getProjectName(String id){
    	String name = "";
    	try {
			stmt = c.createStatement();
			
			String sql = "SELECT name FROM Projects WHERE id = " + id;
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
     
    public boolean insertProject(String name){
    	try { 			
    		java.util.Date date= new java.util.Date();
 			
 			if(!ifProjectExists(name)){
 				stmt = c.createStatement();
 	 			String sql = "INSERT INTO Projects (name, date) " +
 	 			           	 " VALUES ( '" + name + "','" + String.valueOf(date.getTime()) + "')"; // storage date in milliseconds
 	 			stmt.executeUpdate(sql);
 	 			System.out.println("Project inserted successfully!");
 	 			stmt.close();
 	 			return true;
 	        } 
 			else {
 	        	return false;
 	        }
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
    	return false;
    }
    
    public boolean insertActor(String name, String id){
    	try { 			
 			if(!ifActorExists(name, id)){
 				stmt = c.createStatement();
 	 			String sql = "INSERT INTO Actors (name, projectId) " +
 	 			           	 " VALUES ( '" + name + "'," + id + ")"; 
 	 			stmt.executeUpdate(sql);
 	 			System.out.println("Actor inserted successfully!");
 	 			stmt.close();
 				return true;
 	        }
 			else{
 				return false;
 			}
 			
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
			return false;
    }
    
    public boolean insertUseCase(String name, String id){
    	try { 			
 			if(!ifUseCaseExists(name, id)){
 				stmt = c.createStatement();
 	 			String sql = "INSERT INTO Use_cases (name, projectId) " +
 	 			           	 " VALUES ( '" + name + "'," + id + ")"; // storage date in milliseconds
 	 			stmt.executeUpdate(sql);
 	 			System.out.println("Use_case inserted successfully!");
 	 			stmt.close();
 	 			return true;
 	        }
 			else{
 				return false;
 			}
 			
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
		return false;
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
			           	 "WHERE name = '" + name + "' AND " +
			           	 "projectId = " + id + ";";
  			
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
		           	 "WHERE name = '" + name + "' AND " +
		           	 "projectId = " + id + ";";
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
    

	private boolean ifLineExists(String line, String id) {
    	try {
    		stmt = c.createStatement();
  			String sql = "SELECT id " +
		           	 "FROM lines "+
		           	 "WHERE line = '" + line + "' AND " +
		           	 "useCaseId = " + id + ";";
  			ResultSet result = stmt.executeQuery(sql);
  			while (result.next()){
  				System.out.println("Line already exists!");
  				return true;
  			}
  			System.out.println("Line not exists!");
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
			String sql = "SELECT name FROM Use_cases WHERE projectId = " + id;
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
			String sql = "SELECT name FROM actors WHERE projectId = " + id;
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
    
    public Vector<Line> listLines(String useCaseId)
    {
    	Vector<Line> resultVector = new Vector<Line>();
    	try {
			stmt = c.createStatement();
			
			String sql = "SELECT * FROM lines WHERE usecaseid = " + useCaseId;
			ResultSet result = stmt.executeQuery(sql);
			int max = 0;
			while (result.next()){
				int pos = Integer.parseInt(result.getString("position"));
				Line tempLine = new Line();
				tempLine.id = result.getString("id");
				tempLine.pos = Integer.parseInt(result.getString("position"));
				tempLine.text = result.getString("line");
				resultVector.insertElementAt(tempLine, tempLine.pos); 
			}
			System.out.println("Opened database successfully closed");
			
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return resultVector;
    }
    
    public Vector<Include> listIncludes(String useCaseId)
    {
    	Vector<Include> resultVector = new Vector<Include>();
    	try {
			stmt = c.createStatement();
			
			String sql = "SELECT * FROM includes WHERE usecaseid = " + useCaseId;
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				Include tempInclude = new Include();
				tempInclude.id = result.getString("id");
				tempInclude.line_id = result.getString("lineid");
				tempInclude.use_case_id = result.getString("usecaseid");
				tempInclude.included_use_case_id = result.getString("includedusecaseid");
				resultVector.add(tempInclude); 
			}
			System.out.println("Opened database successfully closed");
			
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return resultVector;
    }

    public Vector<Extend> listExtends(String useCaseId)
    {
    	Vector<Extend> resultVector = new Vector<Extend>();
    	try {
			stmt = c.createStatement();
			
			String sql = "SELECT * FROM extends WHERE usecaseid = " + useCaseId;
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				Extend tempExtend = new Extend();
				tempExtend.id = result.getString("id");
				tempExtend.line_id = result.getString("lineid");
				tempExtend.use_case_id = result.getString("usecaseid");
				tempExtend.extended_use_case_id = result.getString("extendedUsecaseid");
				resultVector.add(tempExtend); 
			}
			System.out.println("Opened database successfully closed");
			
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return resultVector;
    }

    
	public String getProjectIdFromName(String name) {
    	String project_id = new String();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT id FROM projects WHERE name = '" + name + "'";
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				project_id = String.valueOf(result.getInt("id"));
			}
			stmt.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return project_id;
	}

	public String getUseCaseIdFromName(String name) {
    	String use_case_id = new String();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT id FROM use_cases WHERE name = '" + name + "'";
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				use_case_id = String.valueOf(result.getInt("id"));
			}
			stmt.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return use_case_id;
	}

	public UseCase loadUseCase(String id) {
    	UseCase use_case = new UseCase();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT * FROM use_cases WHERE id = '" + id + "'";
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				use_case.name = String.valueOf(result.getString("name"));
				use_case.project_id = String.valueOf(result.getString("projectid"));
			}
			stmt.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return use_case;
	}
	
	public Line addLine(String line, String id, int position) {
		try { 				
			stmt = c.createStatement();
 			String sql = "INSERT INTO lines (line, useCaseId, position)" +
 			           	 " VALUES ( '" + line + "'," + id + "," + position + ")" +
 			           	 "RETURNING id";
 			ResultSet result = stmt.executeQuery(sql);
 			if(!result.next()) {
 				stmt.close();
 				return null;
 			}
 			System.out.println("Line inserted successfully!");
 			Line tempLine = new Line();
 			tempLine.id = result.getString("id");
 			tempLine.text = line;
 			tempLine.pos = position;
 			stmt.close();
 			return tempLine;
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
		return null;
	}
	
	public void addInclude(String useCaseId, String includedUseCaseId, Line line) {
		try { 				
			stmt = c.createStatement();
 			String sql = "INSERT INTO includes (lineId, useCaseId, includedUseCaseId)" +
 			           	 " VALUES ( " + line.id + "," + useCaseId + "," + includedUseCaseId + ")";
 			stmt.executeUpdate(sql);
 			stmt.close();
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
	}

	public void addExtend(String useCaseId, String extendedUseCaseId, Line line) {
		try { 				
			stmt = c.createStatement();
 			String sql = "INSERT INTO extends (lineId, useCaseId, extendedUseCaseId)" +
 			           	 " VALUES ( " + line.id + "," + useCaseId + "," + extendedUseCaseId + ")";
 			stmt.executeUpdate(sql);
 			stmt.close();
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
	}
	
	public void deleteUseCase(String id) {
		try {
				
 				stmt = c.createStatement();
 	 			String sql = "DELETE FROM lines" +
 	 			           	 " WHERE usecaseid = " + id;
 	 			stmt.executeUpdate(sql);
 	 			sql = "DELETE FROM use_cases" +
 	 			           	 " WHERE id = " + id;
 	 			stmt.executeUpdate(sql);
 	 			System.out.println("Use_case deleted successfully!");
 	 			stmt.close();
 			
 			
     	} catch ( Exception e ) {
     		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
     	}
	}

	public void deleteLine(String id) {
		try {
				stmt = c.createStatement();
	 			String sql = "DELETE FROM lines" +
	 			           	 " WHERE id = " + id;
	 			stmt.executeUpdate(sql);
	 			System.out.println("Line deleted successfully!");
	 			stmt.close();
			
	 	} catch ( Exception e ) {
	 		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	 	}		
	}

	public void deleteActor(String id) {
		try {
			stmt = c.createStatement();
 			String sql = "DELETE FROM actors" +
 			           	 " WHERE id = " + id;
 			stmt.executeUpdate(sql);
 			System.out.println("Actor deleted successfully!");
 			stmt.close();
		
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
	}

	public String getActorIdFromName(String name) {
    	String actor_id = new String();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT id FROM actors WHERE name = '" + name + "'";
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				actor_id = String.valueOf(result.getInt("id"));
			}
			stmt.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return actor_id;
	}    
	
	public Line getLine(String id) {
    	Line line = new Line();
    	try {
			stmt = c.createStatement();
			String sql = "SELECT * FROM lines WHERE id = " + id;
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()){
				line.id = String.valueOf(result.getInt("id"));
				line.text = result.getString("line");
				line.pos = result.getInt("position");

			}
			stmt.close();
			return line;
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	return null;
	}

	public void deleteInclude(String id) {
		try {
			stmt = c.createStatement();
 			String sql = "DELETE FROM includes" +
 			           	 " WHERE id = " + id;
 			stmt.executeUpdate(sql);
 			System.out.println("Include deleted successfully!");
 			stmt.close();
		
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
	}
	
	public void deleteExtends(String id) {
		try {
			stmt = c.createStatement();
 			String sql = "DELETE FROM extends" +
 			           	 " WHERE id = " + id;
 			stmt.executeUpdate(sql);
 			System.out.println("Extend deleted successfully!");
 			stmt.close();
		
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
	}
	
}