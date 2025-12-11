package dto;

/**
 * Quarto Presidencial - Suíte de luxo com jacuzzi e sala de estar
 * Implementa IQuartoLuxo para fornecer funcionalidades de luxo
 */
public class QuartoPresidencial extends Quarto implements IQuartoLuxo {
    private boolean temSalaDeEstar;

    public QuartoPresidencial(int idPousada, String nome, int camas, int valorDia, boolean temSalaDeEstar, boolean jacuzzi) {
        super(idPousada, nome, camas, valorDia, true, temSalaDeEstar);
        this.temSalaDeEstar = temSalaDeEstar;
    }

    public boolean isTemSalaDeEstar() {
        return temSalaDeEstar;
    }

    public void setTemSalaDeEstar(boolean temSalaDeEstar) {
        this.temSalaDeEstar = temSalaDeEstar;
    }
    
    // Implementação da interface IQuartoLuxo
    @Override
    public boolean temJacuzzi() {
        return true; // Quarto Presidencial sempre tem jacuzzi
    }
    
    @Override
    public boolean temSalaDeEstar() {
        return this.temSalaDeEstar;
    }
    
    @Override
    public double calcularAdicionalLuxo() {
        double adicional = 100.0; // R$ 100 por jacuzzi (sempre tem)
        if (temSalaDeEstar) {
            adicional += 150.0; // R$ 150 adicional por sala de estar
        }
        return adicional;
    }
    
    @Override
    public String listarAmenidades() {
        StringBuilder amenidades = new StringBuilder("Amenidades Presidenciais: Jacuzzi");
        if (temSalaDeEstar) {
            amenidades.append(", Sala de Estar");
        }
        return amenidades.toString();
    }
    
    @Override
    public String getDescricaoTipo() {
        return "Quarto Presidencial - Suíte de luxo com jacuzzi e sala de estar";
    }

    @Override
    public String toString() {
        return super.toString() + " | Presidencial: " + (temSalaDeEstar ? "Com Sala de Estar" : "Sem Sala de Estar") + " | ";
    }
}
