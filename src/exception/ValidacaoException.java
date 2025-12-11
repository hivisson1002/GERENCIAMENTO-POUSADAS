package exception;

/**
 * Exceção lançada quando uma validação de dados falha
 * Utilizada para erros de validação de campos obrigatórios, formatos, tamanhos, etc.
 */
public class ValidacaoException extends Exception {
    
    /**
     * Construtor com mensagem de erro
     * @param mensagem Descrição do erro de validação
     */
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
    
    /**
     * Construtor com mensagem e causa
     * @param mensagem Descrição do erro de validação
     * @param causa Exceção que causou este erro
     */
    public ValidacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
