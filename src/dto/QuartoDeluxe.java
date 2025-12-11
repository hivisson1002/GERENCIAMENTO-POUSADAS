package dto;

/**
 * Quarto Deluxe - Quarto superior com jacuzzi
 * Implementa IQuartoLuxo para fornecer funcionalidades de luxo
 */
public class QuartoDeluxe extends Quarto implements IQuartoLuxo {
    private boolean temJacuzzi;

    public QuartoDeluxe(int idPousada, String nome, int camas, int valorDia, boolean temJacuzzi) {
        super(idPousada, nome, camas, valorDia, true, temJacuzzi);
        this.temJacuzzi = temJacuzzi;
    }

    public boolean isTemJacuzzi() {
        return temJacuzzi;
    }

    public void setTemJacuzzi(boolean temJacuzzi) {
        this.temJacuzzi = temJacuzzi;
    }
    
    // Implementação da interface IQuartoLuxo
    @Override
    public boolean temJacuzzi() {
        return this.temJacuzzi;
    }
    
    @Override
    public boolean temSalaDeEstar() {
        return false; // Quarto Deluxe não possui sala de estar
    }
    
    @Override
    public double calcularAdicionalLuxo() {
        double adicional = 0.0;
        if (temJacuzzi) {
            adicional += 100.0; // R$ 100 adicional por jacuzzi
        }
        return adicional;
    }
    
    @Override
    public String listarAmenidades() {
        StringBuilder amenidades = new StringBuilder("Amenidades Deluxe: ");
        if (temJacuzzi) {
            amenidades.append("Jacuzzi");
        } else {
            amenidades.append("Nenhuma amenidade especial");
        }
        return amenidades.toString();
    }
    
    @Override
    public String getDescricaoTipo() {
        return "Quarto Deluxe - Acomodação superior com jacuzzi";
    }

    @Override
    public String toString() {
        return super.toString() + " | Deluxe: " + (temJacuzzi ? "Com Jacuzzi" : "Sem Jacuzzi");
    }
}
