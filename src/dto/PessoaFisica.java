package dto;

public class PessoaFisica extends Pessoa {
    private String cpf;
    private String sexo;
    
    public PessoaFisica() {}
    
    public PessoaFisica(String user, String nome, String tel, String cpf, String sexo) {
        super(user, nome, tel);
        this.cpf = cpf;
        this.sexo = sexo;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
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