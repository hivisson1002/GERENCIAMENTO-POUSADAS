package dto;

public class Funcionario extends PessoaFisica {
    private String cargo;
    private int salario;
    
    public Funcionario() {}
    
    public Funcionario(String usuario, String nome, String telefone, int cpf, String sexo, String cargo, int salario) {
        super(usuario, nome, telefone, cpf, sexo);
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Funcionario [" + super.toString() + ", cargo=" + cargo + ", salario=" + salario + "]";
    }
}
