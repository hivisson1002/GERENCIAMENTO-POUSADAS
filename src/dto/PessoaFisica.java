package dto;

public class PessoaFisica extends Pessoa {
    private int cpf;
    private String sexo;
    
    public PessoaFisica() {}
    
    public PessoaFisica(String usuario, String nome, String telefone, int cpf, String sexo) {
        super(usuario, nome, telefone);
        this.cpf = cpf;
        this.sexo = sexo;
    }
    
    public int getCpf() {
        return cpf;
    }
    
    public void setCpf(int cpf) {
        this.cpf = cpf;
    }
    
    public String getSexo() {
        return sexo;
    }
    
    public void setSexo(String sexo) {
        if (sexo != null && (sexo.equalsIgnoreCase("M") || sexo.equalsIgnoreCase("F"))) {
            this.sexo = sexo;
        }
    }
    
    @Override
    public String toString() {
        return "PessoaFisica [" + super.toString() + ", cpf=" + cpf + ", sexo=" + sexo + "]";
    }
}