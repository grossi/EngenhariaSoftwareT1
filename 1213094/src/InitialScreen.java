import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InitialScreen extends JPanel{
	
	private JButton create;
	private JButton load;
	
	private MyComboBox projects;
	private JTextField new_project_name;
	
	public InitialScreen(){
		super();
		
		new_project_name = new JTextField(20);
		create = new JButton("Create! :^D");
		
		load = new JButton("Load");
		Vector<String> projects_list = DatabaseOperator.getInstance().listProjects();
		projects = new MyComboBox(projects_list);
		
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (DatabaseOperator.getInstance().insertProject(new_project_name.getText())) {
					GUI.getInstance().changeScreen(new ProjectScreen(DatabaseOperator.getInstance().getProjectIdFromName(new_project_name.getText())), GUI.PROJECT_LEVEL);			
				}
			}
		});
		
		load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.getInstance().changeScreen(new ProjectScreen(DatabaseOperator.getInstance().getProjectIdFromName(projects.getItemAt(projects.getSelectedIndex()))), GUI.PROJECT_LEVEL);
			}
		});
		
		this.add(createMainBox());
	}
	
	public Box createMainBox(){
		Box main_box = Box.createHorizontalBox();
		Box load_box = Box.createVerticalBox();
		Box create_box = Box.createVerticalBox();
		
		
		load_box.add(projects);
		load_box.add(Box.createVerticalStrut(1));
		load_box.add(load);
		load_box.add(Box.createVerticalStrut(1));
		

		create_box.add(new_project_name);
		create_box.add(Box.createVerticalStrut(1));
		create_box.add(create);
		create_box.add(Box.createVerticalStrut(1));
		
		main_box.add(load_box);
		main_box.add(Box.createHorizontalStrut(10));
		main_box.add(create_box);
		
		return main_box;
	}
	
	
}