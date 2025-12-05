package dto;

public abstract class Quarto {
    protected int id;
    protected int pousada;
    protected String nome;
    protected int camas;
    protected int valor_dia;
    protected boolean jacuzzi;
    protected boolean salaDeEstar;
    
    public Quarto(int pousada, String nome, int camas, int valor_dia, boolean jacuzzi, boolean salaDeEstar) {
        this.pousada = pousada;
        this.nome = nome;
        this.camas = camas;
        this.valor_dia = valor_dia;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPousada() {
        return pousada;
    }
    public void setPousada(int pousada) {
        this.pousada = pousada;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getCamas() {
        return camas;
    }
    public void setCamas(int camas) {
        this.camas = camas;
    }
    public int getValor_dia() {
        return valor_dia;
    }
    public void setValor_dia(int valor_dia) {
        this.valor_dia = valor_dia;
    }
    public boolean isJacuzzi() {
        return jacuzzi;
    }

    public void setJacuzzi(boolean jacuzzi) {
        this.jacuzzi = jacuzzi;
    }

    public boolean isSalaEstar() {
        return salaDeEstar;
    }

    public void setsalaDeEstar(boolean salaDeEstar) {
        this.salaDeEstar = salaDeEstar;
    }
    
    // Método abstrato - cada tipo de quarto deve implementar
    public abstract String getDescricaoTipo();
    
    @Override
    public String toString() {
        return "QUARTO [id:" + id + " | " + nome + " | " + camas + " camas | valor da diária: R$" + valor_dia + ",00 ]";
    }
}
