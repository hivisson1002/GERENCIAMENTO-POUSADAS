package dto;

public class QuartoPresidencial extends Quarto {
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
    
    @Override
    public String getDescricaoTipo() {
        return "Quarto Presidencial - Suíte de luxo com jacuzzi e sala de estar";
    }

    @Override
    public String toString() {
        return super.toString() + " | Presidencial: " + (temSalaDeEstar ? "Com Sala de Estar" : "Sem Sala de Estar") + " | ";
    }
}
