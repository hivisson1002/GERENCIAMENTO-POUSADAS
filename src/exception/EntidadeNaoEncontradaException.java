package exception;

/**
 * Exceção lançada quando uma entidade não é encontrada no banco de dados
 * Utilizada em operações de busca, atualização ou deleção
 */
public class EntidadeNaoEncontradaException extends Exception {
    
    /**
     * Construtor com nome da entidade e ID
     * @param nomeEntidade Nome da entidade (Pousada, Quarto, etc.)
     * @param id ID que não foi encontrado
     */
    public EntidadeNaoEncontradaException(String nomeEntidade, int id) {
        super(nomeEntidade + " com ID " + id + " não encontrada");
    }
    
    /**
     * Construtor com mensagem personalizada
     * @param mensagem Descrição do erro
     */
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    /**
     * Construtor com mensagem e causa
     * @param mensagem Descrição do erro
     * @param causa Exceção que causou este erro
     */
    public EntidadeNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
