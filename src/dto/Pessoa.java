package dto;

public class Pessoa {
    private String user;
    private String nome;
    private String tel;

    public Pessoa() {}

    public Pessoa(String user, String nome, String tel) {
        this.user = user;
        this.nome = nome;
        this.tel = tel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Pessoa [user=" + user + ", nome=" + nome + ", tel=" + tel + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pessoa pessoa = (Pessoa) obj;
        return user != null && user.equals(pessoa.user);
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}