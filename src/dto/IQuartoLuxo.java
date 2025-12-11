package dto;

/**
 * Interface que define comportamentos de quartos de luxo
 * Quartos que implementam esta interface possuem amenidades especiais
 */
public interface IQuartoLuxo {
    /**
     * Verifica se o quarto possui jacuzzi
     * @return true se possui jacuzzi, false caso contrário
     */
    boolean temJacuzzi();
    
    /**
     * Verifica se o quarto possui sala de estar
     * @return true se possui sala de estar, false caso contrário
     */
    boolean temSalaDeEstar();
    
    /**
     * Calcula o valor adicional cobrado pelas amenidades de luxo
     * @return Valor adicional em reais
     */
    double calcularAdicionalLuxo();
    
    /**
     * Retorna a descrição das amenidades disponíveis
     * @return String com descrição das amenidades
     */
    String listarAmenidades();
}
