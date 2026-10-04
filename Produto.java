import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public abstract class Produto implements Comparable<Produto> {
    private static final double MARGEM_PADRAO = 0.2;
    private static int ultimoID = 20_000;

    protected int idProduto;
    protected String descricao;
    protected double precoCusto;
    protected double margemLucro;
    protected int quant;
    protected int estoqueMinimo;
        
    /**
     * Inicializador privado. 
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço de custo do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro para o produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param estoqueMinimo Estoque mínimo (mínimo 0)
     * @param validade Data de validade passada como parâmetro
     */
    private void init(String desc, double precoCusto, double margemLucro, int quant, int estoqueMinimo){
               
        if(desc.length()<3 ||precoCusto<=0||margemLucro<=0||quant<0||estoqueMinimo<0)
            throw new IllegalArgumentException("Valores inválidos para o produto");
        descricao = desc;
        this.precoCusto = precoCusto;
        this.margemLucro = margemLucro;
        this.quant = quant;
        this.estoqueMinimo = estoqueMinimo;
        idProduto = ultimoID++;

    }

    /**
     * Construtor completo. 
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param preco Preço do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro para o produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param estoqueMinimo Estoque mínimo (mínimo 0)
     */
    protected Produto(String desc, double precoCusto, double margemLucro, int quant, int estoqueMinimo){
        init(desc, precoCusto, margemLucro, quant, estoqueMinimo);
    }

    /**
     * Construtor sem estoque mínimo - fica considerado como 0 e 
     * sem margem de lucro - usa a margem de lucro padrão do sistema. 
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param preco Preço do produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     */
    protected Produto(String desc, double precoCusto, int quant){
        init(desc, precoCusto, MARGEM_PADRAO, quant, 0);
    }

    /**
     * Retorna o valor de venda do produto, considerando seu preço de custo e margem de lucro
     * @return Valor de venda do produto (double, positivo)
     */
    public abstract double valorDeVenda();
    
    @Override
    /**
     * Retorna o código identificador do produto. É um valor único para cada produto (==chave)
     * @return Inteiro positivo com o identificador do produto
     */
    public int hashCode(){
        return idProduto;
    }

    /**
     * Descrição em string do produto, contendo sua descrição e o valor de venda.
     *  @return String com o formato:
     * [NOME]: R$ [VALOR DE VENDA]
     */
    @Override
    public String toString(){
        NumberFormat moeda = NumberFormat.getCurrencyInstance();
        
        return String.format("%04d - %s: %s", idProduto, descricao, moeda.format(valorDeVenda()));
    }

    @Override
    /**
     * Comparação padrão do produto: descrição.
     * Retorna um valor negativo se este produto tem uma descrição anterior ao outro, na ordem alfabética
     * valor positivo se a descrição é posterior à do outro. 
     * Para o mesmo produto, o retorno é 0.
     * @param outro Produto a ser comparado
     * @return Int de acordo com a regra padrão de Comparable (descrita acima)
     */
    public int compareTo(Produto outro){
        return this.descricao.compareTo(outro.descricao);
    }

    /**
     * Igualdade de produtos: caso possuam o mesmo código. 
     * @param obj Outro produto a ser comparado 
     * @return booleano true/false conforme o parâmetro possua o mesmo código identificador deste objeto
     */
    @Override
    public boolean equals(Object obj){
        try{
            Produto outro = (Produto)obj;
            return this.hashCode() == outro.hashCode();
        }catch (ClassCastException ex){
            return false;
        }
    }
    /**
     * Cria um produto a partir de uma linha de dados em formato texto. A linha de dados deve estar de acordo com a formatação
     * "tipo; descrição;preçoDeCusto;margemDeLucro;quant;estoqueMinimo;[dataDeValidade]"
     * ou o funcionamento não será garantido. Os tipos são 1 para produto não perecível e 2 para perecível.
     * @param linha Linha com os dados do produto a ser criado.
     * @return Um produto com os dados recebidos
     */
    static Produto criarDoTexto(String linha){
        //2;descrição;preçoDeCusto;margemDeLucro;dataDeValidade
        Produto novoProduto = null;
        String[] detalhes = linha.split(";");
        String descr = detalhes[1];
        double precoCusto = Double.parseDouble(detalhes[2].replace(",", "."));
        double margem = Double.parseDouble(detalhes[3].replace(",", "."));
        int quant = Integer.parseInt(detalhes[4]);
        int minimo = Integer.parseInt(detalhes[5]);
        if(detalhes[0].equals("1")){
            novoProduto = new ProdutoNaoPerecivel(descr, precoCusto, margem, quant, minimo);
        }
        else{
            LocalDate dataValidade = 
                LocalDate.parse(detalhes[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            novoProduto = new ProdutoPerecivel(descr, precoCusto, margem, quant, minimo, dataValidade);
        }
        return novoProduto;
    }

    /**
     * Gera uma linha de texto a partir dos dados do produto
     * @return Uma string no formato "tipo; descrição;preçoDeCusto;margemDeLucro;[dataDeValidade]"
     */
    public abstract String gerarDadosTexto();
}
