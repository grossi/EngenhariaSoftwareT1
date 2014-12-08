import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI {
	private JFrame mainFrame;
	private static GUI instance;
	private Vector<JPanel> list_screens;

	public static int SELECT_OR_CREATE_PROJECT_LEVEL = 0;
	public static int PROJECT_LEVEL = 1;
	public static int USE_CASE_LEVEL = 2;
	
	private JButton forward, back;
	
	private int current_level = 0;
	
	private GUI(){
		this.list_screens = new Vector<JPanel>();
		this.forward = new JButton("Forward ->");
		this.back = new JButton("Back <-");
		this.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (list_screens.size() > current_level + 1)
					instance.forwardScreen();				
			}
		});
		this.back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (current_level != 0)
					instance.backScreen();				
			}
		});
	}
	
	public static GUI getInstance(){
		if (instance == null){
			instance = new GUI();
		}
		return instance;
	}
	
	public void changeScreen(JPanel frame, int level){
        while (list_screens.size() > level){
            list_screens.remove(level);
        }
        
        list_screens.add(level, frame);
        current_level = level;
        this.updateScreen();
    }
	
	public void updateScreen(){
        Box main_box = Box.createVerticalBox();
        Box buttons_box = Box.createHorizontalBox();
        
        main_box.add(list_screens.get(current_level));
        buttons_box.add(back);
        buttons_box.add(forward);
        main_box.add(buttons_box);
        
		mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(main_box);
        mainFrame.getContentPane().revalidate();
	}
	
	public void createAndShowGUI() {
        mainFrame = new JFrame("Editor de Casos de Uso");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainFrame.setSize(800, 500);
        mainFrame.setVisible(true);
    }
	
	public void backScreen(){
		current_level--;
		this.updateScreen();
	}
	
	public void forwardScreen(){
		current_level++;
		this.updateScreen();
	}
	
}
