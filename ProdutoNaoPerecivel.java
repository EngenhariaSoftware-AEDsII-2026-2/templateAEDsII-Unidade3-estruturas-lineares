
 public class ProdutoNaoPerecivel extends Produto {

    /**
     * Construtor completo. Causa exceção em caso de valores inválidos
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço de compra do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro para a venda (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param estoqueMinimo Estoque mínimo (mínimo 0)
     * @throws IllegalArgumentException em caso dos limites acima serem desrespeitados.
     */
    public ProdutoNaoPerecivel(String descricao, double precoCusto, double margemLucro, int quant, int estoqueMinimo){
        super(descricao, precoCusto, margemLucro, quant, estoqueMinimo);
    }


    /**
     * Construtor com margem de lucro padrão (20%). Causa exceção em caso de valores inválidos
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço de compra do produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @throws IllegalArgumentException em caso dos limites acima serem desrespeitados.
     */
    public ProdutoNaoPerecivel(String descricao, double precoCusto, int quant){
        super(descricao, precoCusto, quant);
    }

    /**
     * Retorna o valor de venda do produto, considerando seu preço de custo e margem de lucro
     * @return Valor de venda do produto (double, positivo)
     */
    public double valorDeVenda(){
        return precoCusto * (1+margemLucro);
    }

    /**
     * Gera uma linha de texto a partir dos dados do produto. Preço e margem de lucro vão formatados com 2 casas decimais.
     * @return Uma string no formato "1; descrição;preçoDeCusto;margemDeLucro;quant;estoqueMinimo"
     */
    @Override
    public String gerarDadosTexto() {
        //1;Borracha;1.80;0.5
        return String.format("1;%s;%.2f;%.2f;%d;%d", 
                                descricao, precoCusto, margemLucro, quant, estoqueMinimo);
      
    }        
}
