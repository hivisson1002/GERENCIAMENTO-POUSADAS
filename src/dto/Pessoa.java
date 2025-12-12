package dto;

public class Pessoa {
    private String usuario;
    private String nome;
    private String telefone;

    public Pessoa() {}

    public Pessoa(String usuario, String nome, String telefone) {
        this.usuario = usuario;
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Pessoa [usuario=" + usuario + ", nome=" + nome + ", telefone=" + telefone + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pessoa pessoa = (Pessoa) obj;
        return usuario != null && usuario.equals(pessoa.usuario);
    }

    @Override
    public int hashCode() {
        return usuario != null ? usuario.hashCode() : 0;
    }
}