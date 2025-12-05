package dto;

public class QuartoDeluxe extends Quarto {
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
    
    @Override
    public String getDescricaoTipo() {
        return "Quarto Deluxe - Acomodação superior com jacuzzi";
    }

    @Override
    public String toString() {
        return super.toString() + " | Deluxe: " + (temJacuzzi ? "Com Jacuzzi" : "Sem Jacuzzi");
    }
}
