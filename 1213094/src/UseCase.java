import java.util.Vector;


public class UseCase {
	
	public String id;
	public String project_id;
	public String name;
	public Vector<String> extends_id;
	public Vector<String> lines;

	public UseCase() {};
	
	public UseCase(String id) {
		UseCase use_case_temp = DatabaseOperator.getInstance().loadUseCase(id);
		this.id = id;
		this.name = use_case_temp.name;
		this.project_id = use_case_temp.project_id;
		this.extends_id = new Vector<String>();
		this.lines = DatabaseOperator.getInstance().listLines(id);
	}

}
