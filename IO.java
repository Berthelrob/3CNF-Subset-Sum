
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {
    private int n_literais;
	private int n_clausulas;
	private String[] nome_literais;
	
	public int getN_literais() {
		return n_literais;
	}

	public void setN_literais(int n_literais) {
		this.n_literais = n_literais;
	}

	public int getN_clausulas() {
		return n_clausulas;
	}

	public void setN_clausulas(int n_clausulas) {
		this.n_clausulas = n_clausulas;
	}

	public String[] getNome_literais() {
		return nome_literais;
	}

	public void setNome_literais(String[] nome_literais) {
		this.nome_literais = nome_literais;
	}

	public ArrayList<ArrayList<String>> le_arq(String nome_arq) throws FileNotFoundException{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new FileReader(nome_arq));
		String exp;
		
		ArrayList<ArrayList<String>> m_expressao = new ArrayList<ArrayList<String>>();
                try{
		while (scanner.hasNext()) {
			int n_literais = scanner.nextInt();
			int n_clausulas = scanner.nextInt();
			
			setN_clausulas(n_clausulas);
			setN_literais(n_literais);
			
			String nome_literais[] = new String[n_literais];
			scanner.nextLine();
			for(int i=0;i<n_literais;i++){
				nome_literais[i] = scanner.next();
			}
			
			setNome_literais(nome_literais);
			
			scanner.nextLine();
			for(int i=0;i<n_clausulas;i++){
				scanner.nextLine();
				ArrayList<String> lista = new ArrayList<String>();
				
				for(int j=0;j<3;j++){
					exp = scanner.next();
					lista.add(exp);					
				}
				m_expressao.add(lista);
			}
		}
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    scanner.close();
                    return m_expressao;
                }
	}

	public void salva_saida(ArrayList<Integer> target, ArrayList<String> s) throws IOException{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String nome_arq;
		
		System.out.print("\nNome do arquivo: ");
		nome_arq = scan.next();
		
		FileWriter arq = new FileWriter(nome_arq+".txt");
	    PrintWriter gravarArq = new PrintWriter(arq);
	    
	    for(int i=0; i< target.size();i++){
	      gravarArq.printf("%d",target.get(i));
		}
	    gravarArq.println("");
	    for(int i=0; i<s.size();i++){
	    	gravarArq.println(s.get(i));
	    }
	    arq.close();
	    
	    System.out.println("\nArquivo salvo!");
	}
	
	public void imprime_exp(int n_literais, int n_clausulas,ArrayList<ArrayList<String>> m_expressao) 
	{
		int separadorOu=0;
		int separadorE=0;
	
		System.out.println("Número de literais: "+n_literais);
		System.out.println("Número de cláusulas: "+n_clausulas);
		System.out.println("\nExpressão:");
		
		for(int i = 0;i<n_clausulas;i++){  
		    System.out.print("("); 
			for(int j = 0;j<3;j++){
		         System.out.print(m_expressao.get(i).get(j));
		         if(separadorOu<2){
		        	 System.out.print(" V ");
		         	 separadorOu++;
		         }
			}
			separadorOu = 0;
			System.out.print(")"); 
			if(separadorE<n_clausulas-1){
				 System.out.print(" /\\ ");
	         	 separadorE++;
			}
		}
	}
	
	public void imprime_tabela (int n_literais, int n_clausulas, ArrayList<ArrayList<Integer>> tabela){
		System.out.println("Tabela de redução:\n");
		for(int i=0; i< (2*n_clausulas+2*n_literais); i++){
			for(int j=0; j< (n_clausulas+n_literais); j++){
			    System.out.print("\t"+tabela.get(i).get(j));  
			}
			System.out.println("");
		}  
	}
	
	public void imprime_target(ArrayList<Integer> target){
		System.out.println("\nValor do target:");
		System.out.print("t = ");
		for(int i=0; i< target.size();i++){
			System.out.print(target.get(i));
		}
	}
	
	public void imprime_conjunto_s(ArrayList<String> conjunto_s, int n_literais, int n_clausulas){
		System.out.println("\nConjunto S:\n");
		System.out.print("S = {");
		for(int i = 0; i<2*n_literais+2*n_clausulas;i++){
			System.out.print(conjunto_s.get(i));
			if(i<2*n_literais+2*n_clausulas-1)
				System.out.print(", ");
		}
		System.out.print("}");
	}
}
