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


public class ProjectScreen extends JPanel {
	
	
	
	private JButton create_actor;
	private JButton create_use_case;
	private JButton load;
	
	private JComboBox<String> actors;
	private JComboBox<String> use_cases;
	private JTextField new_actor_name;
	private JTextField new_use_case_name;
	
	public ProjectScreen(int id) {
		super();
		
		final int project_id = id;
		
		new_actor_name = new JTextField(20);
		new_use_case_name = new JTextField(20);
		
		create_actor = new JButton("Create Actor");
		create_use_case = new JButton("Create Use Case");
		
		load = new JButton("Load");
		
		Vector<String> use_case_list = DatabaseOperator.getInstance().listUseCases(String.valueOf(id));
		use_cases = new JComboBox<String>(use_case_list);
		
		Vector<String> actors_list = DatabaseOperator.getInstance().listActors(String.valueOf(id));
		actors = new JComboBox<String>(actors_list);
		
		create_use_case.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DatabaseOperator.getInstance().insertUseCase(new_use_case_name.getText(), String.valueOf(project_id));
			}
		});
		
		create_actor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DatabaseOperator.getInstance().insertActor(new_actor_name.getText(), String.valueOf(project_id));
			}
		});
		
		this.add(createMainBox());
	}
	
	public Box createMainBox(){
		Box main_box = Box.createHorizontalBox();
		Box load_box = Box.createVerticalBox();
		Box create_box = Box.createVerticalBox();
		
		
		load_box.add(use_cases);
		load_box.add(Box.createVerticalStrut(1));
		load_box.add(load);
		load_box.add(Box.createVerticalStrut(1));
		

		create_box.add(actors);
		create_box.add(Box.createVerticalStrut(1));
		create_box.add(new_actor_name);
		create_box.add(Box.createVerticalStrut(1));
		create_box.add(create_actor);
		create_box.add(Box.createVerticalStrut(1));
		
		create_box.add(new_use_case_name);
		create_box.add(Box.createVerticalStrut(1));
		create_box.add(create_use_case);
		create_box.add(Box.createVerticalStrut(1));
		
		main_box.add(load_box);
		main_box.add(Box.createHorizontalStrut(10));
		main_box.add(create_box);
		
		return main_box;
	}


}
