import java.util.NoSuchElementException;

public class Pilha<E> {

	private Celula<E> topo;
	private Celula<E> fundo;

	public Pilha() {

		Celula<E> sentinela = new Celula<E>();
		fundo = sentinela;
		topo = sentinela;

	}

	public boolean vazia() {
		return fundo == topo;
	}

	public void empilhar(E item) {

		topo = new Celula<E>(item, topo);
	}

	public E desempilhar() {

		E desempilhado = consultarTopo();
		topo = topo.getProximo();
		return desempilhado;

	}

	public E consultarTopo() {

		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na pilha!");
		}

		return topo.getItem();

	}

	public void imprimir() throws IllegalStateException {

		Celula<E> aux;

		if (vazia())
			throw new IllegalStateException("Pilha vazia!");
		else {
			aux = topo;
			while (aux != fundo) {
				System.out.println(aux.getItem().toString());
				aux = aux.getProximo();
			}
		}
	}

	
	public Pilha<E> subPilha(int numItens) {
		
		Pilha<E> subPilha = new Pilha<>();
        // Implementar sua lógica aqui
		return subPilha;
	}
}