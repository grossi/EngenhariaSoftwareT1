import java.awt.EventQueue;   
 
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	
            	DatabaseOperator db_operator = DatabaseOperator.getInstance();
                db_operator.initialize();

                GUI.getInstance().createAndShowGUI();
                GUI.getInstance().changeScreen(new InitialScreen(), GUI.SELECT_OR_CREATE_PROJECT_LEVEL);
            }
        });
    }
}