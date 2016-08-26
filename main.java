import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
	public static void main(String args[]) throws IOException {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		ArrayList<ArrayList<String>> expressao = null;
		
		IO dados = new IO();
		Oracle reducao = new Oracle();
		
		String arq = null;
		
		int opc, status = 0;
		
		do{
			System.out.println("\n\nDigite a opção desejada:\n");
			System.out.println("1 - Informar o nome do arquivo");
			System.out.println("2 - Imprimir expressão");
			System.out.println("3 - Faz a redução de 3CNF-SAT para Subset-SUM e salva em um arquivo");
			System.out.println("4 - Imprimir tabela de redução");
			System.out.println("5 - Imprimir o valor do target");
			System.out.println("6 - Imprimir o conjunto do Subset-SUM");
			
			System.out.println("0 - Sair");
			
			opc = scanner.nextInt();
			
			switch(opc){
				case 0:
					break;
				case 1:
					if((status==0)||(status == 1)||(status == 2))
					arq = scanner.next();
					expressao = dados.le_arq(arq);
					System.out.println("Arquivo lido!");
					status = 1;
					break;
				case 2:
					if((status == 1)||(status == 2))
						dados.imprime_exp(dados.getN_literais(), dados.getN_clausulas(), expressao);	
					break;
				case 3:
					if(status == 1){
						if(reducao.verifica_satisfazibilidade(dados.getN_literais(), dados.getN_clausulas(), expressao, dados.getNome_literais())){
							dados.salva_saida(reducao.getTarget(), reducao.getConjunto_s());
							status = 2;		
						}
					}
					break;
				case 4:
					if(status == 2)
						dados.imprime_tabela (dados.getN_literais(), dados.getN_clausulas(), reducao.getTabela_reducao());
					break;
				case 5:
					if(status == 2)
						dados.imprime_target(reducao.getTarget());
					break;
				case 6:
					if(status == 2)
						dados.imprime_conjunto_s(reducao.getConjunto_s(), dados.getN_literais(), dados.getN_clausulas());
					break;
				default:
					System.out.println("Opção inválida!\n");
					break;
			}
		}while(opc!=0);
		
	}
}
