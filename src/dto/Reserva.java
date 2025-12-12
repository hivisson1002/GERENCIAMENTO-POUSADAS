package dto;

import java.time.LocalDate;

public class Reserva {
    private int id;
    private int pousadaId;
    private int quartoId;
    private String usuarioPessoa;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String status;
    
    public Reserva() {}
    
    public Reserva(int id, int pousadaId, int quartoId, String usuarioPessoa, 
                   LocalDate dataEntrada, LocalDate dataSaida, String status) {
        this.id = id;
        this.pousadaId = pousadaId;
        this.quartoId = quartoId;
        this.usuarioPessoa = usuarioPessoa;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPousadaId() {
        return pousadaId;
    }

    public void setPousadaId(int pousadaId) {
        this.pousadaId = pousadaId;
    }

    public int getQuartoId() {
        return quartoId;
    }

    public void setQuartoId(int quartoId) {
        this.quartoId = quartoId;
    }

    public String getUsuarioPessoa() {
        return usuarioPessoa;
    }

    public void setUsuarioPessoa(String usuarioPessoa) {
        this.usuarioPessoa = usuarioPessoa;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reserva [id=" + id + ", pousadaId=" + pousadaId + ", quartoId=" + quartoId + 
               ", usuarioPessoa=" + usuarioPessoa + ", dataEntrada=" + dataEntrada + 
               ", dataSaida=" + dataSaida + ", status=" + status + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reserva reserva = (Reserva) obj;
        return id == reserva.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
