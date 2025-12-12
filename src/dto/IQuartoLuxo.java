package dto;

/**
 * Interface que define comportamentos de quartos de luxo
 * Quartos que implementam esta interface possuem amenidades especiais
 */

public interface IQuartoLuxo {
    boolean temJacuzzi();
    boolean temSalaDeEstar();
    double calcularAdicionalLuxo();
    String listarAmenidades();
}
