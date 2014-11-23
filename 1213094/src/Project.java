import java.util.ArrayList;

import javax.swing.JPanel;


public class Project extends JPanel{
	
	private ArrayList<ViewCasoDeUso> useCases = new ArrayList<ViewCasoDeUso>();
	
	public Project(String id){
		super();
		DatabaseOperator.getInstance().getProjectName(id);
	}
}
