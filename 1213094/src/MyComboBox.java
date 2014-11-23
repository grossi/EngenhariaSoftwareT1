import java.util.Vector;

import javax.swing.JComboBox;


public class MyComboBox extends JComboBox<String> implements Observer {
	private Vector<String> data;
	
	public MyComboBox(){
		super();
		this.data = new Vector<String>();
	}
	
	@Override
	public void updateUI(){
		this.removeAllItems(); 
		for(String str : this.data){
			this.addItem(new String(str)); // se str nao for um novo objeto, ao remover todos os itens, eles serao 
											// excluidos da Vector data.
		}
	}
	
	public void addItemVector(String item){
		this.data.addElement(item);
	}
}
