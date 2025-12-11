package exception;

/**
 * Exceção lançada quando dados fornecidos são inválidos
 * Utilizada para validações de valores fora do intervalo permitido, formatos incorretos, etc.
 */
public class DadosInvalidosException extends Exception {
    
    /**
     * Construtor com mensagem de erro
     * @param mensagem Descrição dos dados inválidos
     */
    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
    
    /**
     * Construtor com campo, valor e mensagem
     * @param campo Nome do campo inválido
     * @param valor Valor fornecido
     * @param mensagem Descrição do problema
     */
    public DadosInvalidosException(String campo, Object valor, String mensagem) {
        super("Campo '" + campo + "' com valor '" + valor + "': " + mensagem);
    }
    
    /**
     * Construtor com mensagem e causa
     * @param mensagem Descrição dos dados inválidos
     * @param causa Exceção que causou este erro
     */
    public DadosInvalidosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
