import java.awt.Dimension;

import javax.swing.*;        
 
public class Main {
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame mainFrame = new JFrame("Editor de Casos de Uso");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ViewCasoDeUso teste_modelo_principal = new ViewCasoDeUso(ViewCasoDeUso.MODEL_PRIMARY);
        ViewCasoDeUso teste_modelo_secundario = new ViewCasoDeUso(ViewCasoDeUso.MODEL_SECONDARY);
        
        JPanel projeto = new JPanel();
        projeto.setLayout(new BoxLayout(projeto, BoxLayout.PAGE_AXIS));  // for horizontal scroll
        projeto.add(teste_modelo_principal);
        projeto.add(teste_modelo_secundario);
        for (int i=0; i<5; i++){
        	ViewCasoDeUso current = new ViewCasoDeUso(ViewCasoDeUso.MODEL_PRIMARY);
        	projeto.add(current);
        }
        JScrollPane listCasosDeUso = new JScrollPane(projeto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        											 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        listCasosDeUso.getVerticalScrollBar().setUnitIncrement(15); // aumenta a velocidade de scrollagem
        
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
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}