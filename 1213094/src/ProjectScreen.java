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
	
	final String project_id;
	
	private JButton create_actor;
	private JButton create_use_case;
	private JButton load;
	
	private MyComboBox actors;
	private MyComboBox use_cases;
	private Vector<Observer> observers_list;
	private JTextField new_actor_name;
	private JTextField new_use_case_name;
	
	private Vector<String> use_case_list;
	private Vector<String> actors_list;
	
	public ProjectScreen(String id) {
		super();
		
		project_id = id;
		
		new_actor_name = new JTextField(20);
		new_use_case_name = new JTextField(20);
		observers_list = new Vector<Observer>();
		
		create_actor = new JButton("Create Actor");
		create_use_case = new JButton("Create Use Case");
		
		load = new JButton("Load");
		
		use_case_list = DatabaseOperator.getInstance().listUseCases(id);
		use_cases = new MyComboBox(use_case_list);
		observers_list.addElement(use_cases);
		
		actors_list = DatabaseOperator.getInstance().listActors(id);
		actors = new MyComboBox(actors_list);
		observers_list.addElement(actors);
		
		create_use_case.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (new_actor_name.getText() != ""){
					if(DatabaseOperator.getInstance().insertUseCase(new_use_case_name.getText(), project_id)){
						use_case_list.add(new_use_case_name.getText());
						updateObservers();						
					}
				}
			}
		});
		
		create_actor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (new_actor_name.getText() != ""){
					if(DatabaseOperator.getInstance().insertActor(new_actor_name.getText(), project_id)){
						actors_list.add(new_actor_name.getText());
						updateObservers(); 
					}
				}
			}
		});
		
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.getInstance().changeScreen(new ViewCasoDeUso(DatabaseOperator.getInstance().getUseCaseIdFromName(use_cases.getItemAt(use_cases.getSelectedIndex()))), GUI.USE_CASE_LEVEL);
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
	
	private void updateObservers(){
		for(Observer current:observers_list){
			current.updateUI();
		}
	}
}
