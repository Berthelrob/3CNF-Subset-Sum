import java.util.ArrayList;

public class Oracle {
	
	//private ArrayList<Integer> conjunto_s = new ArrayList<Integer>();
	private ArrayList<String> conjunto_s = new ArrayList<String>();
	private ArrayList<Integer> target = new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> tabela_reducao;
	
	/*public ArrayList<Integer> getConjunto_s() {
		return conjunto_s;
	}*/
	
	public ArrayList<String> getConjunto_s(){
		return conjunto_s;
	}
	
	private void setConjunto_s(int n_literais, int n_clausulas) { //Faz a concatenacao dos int de cada linha e coloca na lista s
		String s;
	//	int elemento;
		
		for(int i = 0; i<2*n_literais+2*n_clausulas;i++){
			s = String.valueOf(tabela_reducao.get(i).get(0));
			for(int j = 1; j<n_literais+n_clausulas;j++){
				s = s + String.valueOf(tabela_reducao.get(i).get(j));
			}
	//		elemento = Integer.parseInt(s);
	//		this.conjunto_s.add(elemento);
			this.conjunto_s.add(s);
		}
	}

	public ArrayList<Integer> getTarget() {
		return target;
	}
	
	private void calcula_target(int n_literais, int n_clausulas) {
		int i=0, j=0;	
		for(j=0;j<n_literais;j++){
			target.add(0);
			for(i=0;i<2*n_literais;i++){
				if(tabela_reducao.get(i).get(j)>0){
					target.set(j,1);
				}		
			}
		}
		
		for(j=n_literais; j<n_literais+n_clausulas;j++){
			int sum = 0;
			target.add(0);
			for(i=0;i<2*n_literais+2*n_clausulas;i++){
				if(tabela_reducao.get(i).get(j)!=2){
					sum += tabela_reducao.get(i).get(j);
					target.set(j,sum);
				}
			}
		}
	}

	public ArrayList<ArrayList<Integer>> getTabela_reducao() {
		return tabela_reducao;
	}
	
	public void setTabela_reducao(ArrayList<ArrayList<Integer>> tabela_reducao) {
		this.tabela_reducao = tabela_reducao;
	}

	public boolean verifica_satisfazibilidade(int n_literais, int n_clausulas, ArrayList<ArrayList<String>> m_expressao, String[] nome_literais){
		System.out.println("\n\nRealizando redução do problema...");
		if(!reduz_3cnf_subsetSum (n_literais, n_clausulas, m_expressao, nome_literais)){
			System.out.println("Expressão inválida!!");
			return false;
		}else{
			System.out.println("Problema reduzido!");
			return true;
		}
	}
	
	private boolean reduz_3cnf_subsetSum (int n_literais, int n_clausulas, ArrayList<ArrayList<String>> m_expressao, String[] nome_literais){
		inicia_tabela(n_literais, n_clausulas);
		preenche_diagonal(n_literais, n_clausulas);
		if(!preenche_tab_clausulas(n_literais, n_clausulas, m_expressao, nome_literais)){
			return false;
		}
		setConjunto_s(n_literais, n_clausulas);
		calcula_target(n_literais, n_clausulas);
		
		return true;
	}
	
	private boolean preenche_tab_clausulas(int n_literais, int n_clausulas, ArrayList<ArrayList<String>> m_expressao, String[] nome_literais) {
		int valor=0;
		String sinal_neg = "~";
		
		int literal_cont = 0;
		
		for(int clausula_cont = n_literais; clausula_cont < n_clausulas+n_literais; clausula_cont++){
			for(int j = 0; j < 2*n_literais; j=j+2){
				
				valor = compara_literal(nome_literais[literal_cont], m_expressao.get(Math.abs(clausula_cont-n_literais)));	//Compara os literais verdadeiros e adiciona
				this.tabela_reducao.get(j).set(clausula_cont,valor);

				valor = compara_literal(sinal_neg+nome_literais[literal_cont], m_expressao.get(Math.abs(clausula_cont-n_literais)));	//Compara os literais falsos e adiciona
				this.tabela_reducao.get(j+1).set(clausula_cont,valor);				

				if (!exp_eh_valida(this.tabela_reducao.get(j).get(clausula_cont), this.tabela_reducao.get(j+1).get(clausula_cont))){
					return false;                                    //ERRO da expressão inválida
				}
				literal_cont++;
			}
			literal_cont = 0;
		}
		return true;
	}
	
	private int compara_literal(String l1, ArrayList<String> m_expressao) {
		for(int i = 0; i < 3; i++){
			if(m_expressao.get(i).equals(l1)){
				return 1;
			}
		}
		return 0;
	}
	
	private boolean exp_eh_valida(int litV, int litF){
		if((litV==1 && litF==0)||(litV==0 && litF==1)|| (litV==0 && litF==0))
			return true;
		return false;
	}	

	private void inicia_tabela(int n_literais, int n_clausulas) {
		int valor=0;
		ArrayList<ArrayList<Integer>> tabela = new ArrayList<ArrayList<Integer>>() ;
		
		for(int i = 0; i<(2*n_literais+2*n_clausulas); i++){  //Linhas da tabela
			ArrayList<Integer> lista = new ArrayList<Integer>();	
			for(int j = 0; j<(n_literais+n_clausulas); j++){      //Colunas da tabela
				lista.add(valor);
			}
			tabela.add(lista);
		}
		setTabela_reducao(tabela);
	}
	
	private void preenche_diagonal(int n_literais, int n_clausulas) {		
		int linha=0;
		for(int i=0; i< (n_literais+n_clausulas); i++){		
			if(i<n_literais){
				this.tabela_reducao.get(linha).set(i, 1);
				this.tabela_reducao.get(linha+1).set(i, 1);
				linha=linha+2;
			}else{
				this.tabela_reducao.get(linha).set(i, 1);
				this.tabela_reducao.get(linha+1).set(i, 2);
				linha=linha+2;
			}
		}
	}
}
