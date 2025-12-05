package dto;

public class QuartoStandard extends Quarto {
    public QuartoStandard(int pousada, String nome, int camas, int valor_dia) {
        super(pousada, nome, camas, valor_dia, false, false);
    }
    
    @Override
    public String getDescricaoTipo() {
        return "Quarto Standard - Acomodação básica e confortável";
    }
}
