import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ViewCasoDeUso extends JPanel{

	private static int NUMBER_OF_START_TEXT_FIELDS = 3;
	private static Border BORDER = BorderFactory.createEmptyBorder(3,3,3,3);
	
	private String id;
	private String project_id;
	private JTextField nome; 
	private JTextField 	objetivo;
	private JTextField 	nivel;
	private JTextField trigger;	
	private JTextField 	ator_primario; 
	private JTextField 	ator_de_suporte; 
	private JTextField 	pre_condicoes;
	private JTextField 	pos_condicoes;
	private ArrayList<JTextField> pontos_de_extensao_list;
	private ArrayList<JTextField> fluxo_principal_list;
	private ArrayList<JTextField> extensoes_list;
	
	private JComboBox<String> actors;
	private JComboBox<String> use_cases;
	private Vector<String> use_case_list;
	private Vector<String> actors_list;
	
	public ViewCasoDeUso(String id, String project_id){		
		this.id = id;
		this.nome = new JTextField(50);
		this.objetivo = new JTextField(50);
		this.nivel = new JTextField(50);
		this.ator_primario = new JTextField(50);
		this.ator_de_suporte = new JTextField(50);
		this.pre_condicoes = new JTextField(50);
		this.pos_condicoes = new JTextField(50);
		
		use_case_list = DatabaseOperator.getInstance().listUseCases(project_id);
		use_cases = new JComboBox<String>(use_case_list);
		
		actors_list = DatabaseOperator.getInstance().listActors(project_id);
		actors = new JComboBox<String>(actors_list);
		
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.add(createMainBox());
	}
	
	public Box createMainBox(){
		Box main_box = Box.createVerticalBox();
		
		main_box.add(createNomeBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createExtensionBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createObjetivoBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createNivelBox());
		main_box.add(Box.createVerticalStrut(1));
		
		if(this.trigger != null){
			main_box.add(createTriggerBox());
			main_box.add(Box.createVerticalStrut(1));
		}
		
		main_box.add(createAtorPrimarioBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createAtorDeSuporteBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createPreCondicoesBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createPosCondicoesBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createPontosDeExtensaoBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createFluxoPrincipalBox());
		main_box.add(Box.createVerticalStrut(1));
		main_box.add(createExtensoesBox());
		return main_box;
	}

	public Box createNomeBox(){
		Box nome_box = Box.createHorizontalBox();
		nome_box.add(new JLabel("Nome: "));
		nome_box.add(this.nome);
		nome_box.setBorder(ViewCasoDeUso.BORDER);
		return nome_box;
	}
	
	public Box createExtensionBox(){
		Box extension_box = Box.createHorizontalBox();
		extension_box.add(new JLabel("Extends: "));
		extension_box.add(this.use_cases);
		extension_box.setBorder(ViewCasoDeUso.BORDER);
		return extension_box;
	}
	
	public Box createPreCondicoesBox(){
		Box pre_condicoes_box = Box.createHorizontalBox();
		pre_condicoes_box.add(new JLabel("Pré condições: "));
		pre_condicoes_box.add(this.pre_condicoes);
		pre_condicoes_box.setBorder(ViewCasoDeUso.BORDER);
		return pre_condicoes_box;
	}
	
	public Box createNivelBox(){
		Box nivel_box = Box.createHorizontalBox();
		nivel_box.add(new JLabel("Nível: "));
		nivel_box.add(this.nivel);
		nivel_box.setBorder(ViewCasoDeUso.BORDER);
		return nivel_box;
	}
	
	public Box createAtorPrimarioBox(){
		Box ator_primario_box = Box.createHorizontalBox();
		ator_primario_box.add(new JLabel("Ator Primário: "));
		ator_primario_box.add(this.ator_primario);
		ator_primario_box.setBorder(ViewCasoDeUso.BORDER);
		return ator_primario_box;
	}
	
	public Box createAtorDeSuporteBox(){
		Box ator_de_suporte_box = Box.createHorizontalBox();
		ator_de_suporte_box.add(new JLabel("Ator de Suporte: "));
		ator_de_suporte_box.add(this.ator_de_suporte);
		ator_de_suporte_box.setBorder(ViewCasoDeUso.BORDER);
		return ator_de_suporte_box;
	}
	
	public Box createPosCondicoesBox(){
		Box pos_condicoes_box = Box.createHorizontalBox();
		pos_condicoes_box.add(new JLabel("Pós condições: "));
		pos_condicoes_box.add(this.pos_condicoes);
		pos_condicoes_box.setBorder(ViewCasoDeUso.BORDER);
		return pos_condicoes_box;
	}
	
	public Box createObjetivoBox(){
		Box objetivo_box = Box.createHorizontalBox();
		objetivo_box.add(new JLabel("Objetivo: "));
		objetivo_box.add(this.objetivo);
		objetivo_box.setBorder(ViewCasoDeUso.BORDER);
		return objetivo_box;
	}
	
	public Box createPontosDeExtensaoBox(){
		Box pontos_de_extensao_box = Box.createHorizontalBox();
		JLabel label = new JLabel("Pontos de extensão: ");
		pontos_de_extensao_box.add(label);
		this.pontos_de_extensao_list = new ArrayList<JTextField>();
		pontos_de_extensao_box.add(createPontosDeExtensaoInBox());
		pontos_de_extensao_box.setBorder(ViewCasoDeUso.BORDER);
		
		return pontos_de_extensao_box;
	}
	
	public Box createFluxoPrincipalBox(){
		Vector<String> lineList = DatabaseOperator.getInstance().getLines(this.id); 
		Box fluxo_principal_box = Box.createHorizontalBox();
		JLabel label = new JLabel("Fluxo Principal: ");
		fluxo_principal_box.add(label);
		this.fluxo_principal_list = new ArrayList<JTextField>();
		fluxo_principal_box.add(createFluxoPrincipalInBox());
		fluxo_principal_box.setBorder(ViewCasoDeUso.BORDER);
				
		for(int i = 0; i < lineList.size(); i = i + 1) {
			this.fluxo_principal_list.add(new JTextField(lineList.get(i)));
		}
		
		return fluxo_principal_box;
	}
	
	public Box createExtensoesBox(){
		Box extensoes_box = Box.createHorizontalBox();
		JLabel label = new JLabel("Extensões: ");
		extensoes_box.add(label);
		this.extensoes_list = new ArrayList<JTextField>();
		extensoes_box.add(createExtensoesInBox());
		extensoes_box.setBorder(ViewCasoDeUso.BORDER);
		return extensoes_box;
	}
	
	public Box createPontosDeExtensaoInBox(){
		Box pontos_de_extensao_in_box = Box.createVerticalBox();
		for (int i=0; i<NUMBER_OF_START_TEXT_FIELDS; i++){
			JTextField ponto_de_extensao = new JTextField(40);

			pontos_de_extensao_list.add(ponto_de_extensao);
			pontos_de_extensao_in_box.add(ponto_de_extensao);
		}
		
		return pontos_de_extensao_in_box;
	}
	
	public Box createFluxoPrincipalInBox(){
		Box fluxo_principal_in_box = Box.createVerticalBox();
		for (int i=0; i<NUMBER_OF_START_TEXT_FIELDS; i++){
			JTextField fluxo_principal = new JTextField(40);

			fluxo_principal_list.add(fluxo_principal);
			fluxo_principal_in_box.add(fluxo_principal);
		}
		return fluxo_principal_in_box;
	}
	
	public Box createExtensoesInBox(){
		Box extensoes_in_box = Box.createVerticalBox();
		for (int i=0; i<NUMBER_OF_START_TEXT_FIELDS; i++){
			JTextField extensao = new JTextField(40);
			extensoes_list.add(extensao);
			extensoes_in_box.add(extensao);
		}
		return extensoes_in_box;
	}
	
	public Box createTriggerBox(){
		Box trigger_box = Box.createHorizontalBox();
		trigger_box.add(new JLabel("Trigger: "));
		trigger_box.add(this.trigger);
		trigger_box.setBorder(ViewCasoDeUso.BORDER);
		return trigger_box;
	}
}