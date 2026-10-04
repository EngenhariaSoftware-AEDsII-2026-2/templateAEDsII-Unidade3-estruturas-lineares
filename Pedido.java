import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pedido {

	/** Quantidade máxima de produtos de um pedido */
	private static final int MAX_PRODUTOS = 10;
	
	/** Vetor para armazenar os produtos do pedido */
	private Produto[] produtos;
	
	public Produto[] getProdutos() {
		return produtos;
	}

	/** Armazena a quantidade total de produtos no pedido neste momento */
	private int quantProdutos;

	/** Armazena a data de criação do pedido */
	private LocalDate dataPedido;

	/** Construtor do Pedido. Deve criar o vetor e armazenar a data atual do sistema na data do pedido */
	public Pedido() {
		produtos = new Produto[MAX_PRODUTOS];
		this.dataPedido = LocalDate.now();
		this.quantProdutos = 0;
	}

	

    /**
     * Inclui um produto neste pedido e aumenta a quantidade de produtos armazenados.
     * @param novo O produto a ser incluído no pedido
     * @return A quantidade de produtos no pedido após a inclusão
     */
	public int incluirProduto(Produto novo) {
		if(quantProdutos < MAX_PRODUTOS && novo != null) {
			produtos[quantProdutos++] = novo;
		} else {
			System.out.println("Produto nulo ou excedeu a quantidade de produtos permitida.");
		}
		return quantProdutos;
	}

    /**
     * Calcula e retorna o valor final do pedido (soma do valor de venda de todos os produtos)
     * @return Valor a ser pago pelo pedido (double)
     */
	public double valorFinal() {
		double total = 0d;
		for (int i = 0; i < quantProdutos; i++) {
			total += produtos[i].valorDeVenda();
		}
		return total;
	}

    /**
     * Representação em String do produto. Contém um cabeçalho com sua data e depois, em cada linha,
     * a descrição de cada produto. Ao final, mostra o valor a pagar pelo pedido.
     * @return Uma string contendo dados de um pedido conforme especificado (cabeçalho, detalhes, valor a pagar)
     */
	public String toString() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		StringBuilder retorno = new StringBuilder("Pedido na data "+formato.format(dataPedido)+"\n");
		for (int i = 0; i < quantProdutos; i++) {
			retorno.append(produtos[i].toString()+"\n");
		}
		retorno.append(String.format("Valor a pagar: R$ %.2f", valorFinal()));
		return retorno.toString();
				
	}

	/**
	 * Deve retornar uma descrição resumida do Pedido em uma única linha. O formato deve ser
	 * "Pedido com XX produtos em DD/MM/AAAA, valor total de R$ XX,XX"
	 * @return Uma string como especificada acima
	 */
	public String resumo() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");   
		String valorFinalFormatado = String.format("%.2f",this.valorFinal()).replace(",", ".");     
        String dados = "Pedido com " + quantProdutos + " produtos em " + formato.format(dataPedido) + ", valor total de R$ " + valorFinalFormatado;
        return dados;

	}

}
