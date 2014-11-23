import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.*;        
 
public class Main {
	private static JFrame mainFrame;
	
	static void changeScreen(JPanel frame){
		mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(frame);
        mainFrame.getContentPane().revalidate();
    }
	
    private static void createAndShowGUI() {
        //Create and set up the window.
        mainFrame = new JFrame("Editor de Casos de Uso");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        DatabaseOperator db_operator = DatabaseOperator.getInstance(); // only to connect
        db_operator.initialize();
        db_operator.insertProject("teste");
        
        mainFrame.getContentPane().add(new InitialScreen());
        
        mainFrame.setSize(800, 500);
        
        //Display the window.
        mainFrame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}