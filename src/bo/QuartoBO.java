package bo;

import dao.PousadaDAO;
import dao.QuartoDAO;
import dto.IQuartoLuxo;
import dto.Quarto;
import exception.DadosInvalidosException;
import exception.EntidadeNaoEncontradaException;
import exception.ValidacaoException;
import java.util.List;

public class QuartoBO {
    private QuartoDAO quartoDAO;
    private PousadaDAO pousadaDAO;
    
    public QuartoBO() {
        this.quartoDAO = new QuartoDAO();
        this.pousadaDAO = new PousadaDAO();
    }
    
    public void adicionarQuarto(Quarto quarto) throws ValidacaoException, EntidadeNaoEncontradaException, Exception {
        validarQuarto(quarto);
        
        if (!pousadaDAO.pousadaExiste(quarto.getPousada())) {
            throw new EntidadeNaoEncontradaException("Pousada", quarto.getPousada());
        }
        
        quartoDAO.adicionar(quarto);
    }
    
    public List<Quarto> listarQuartosPorPousada(int idPousada) throws DadosInvalidosException, EntidadeNaoEncontradaException, Exception {
        if (idPousada <= 0) {
            throw new DadosInvalidosException("ID da pousada deve ser maior que zero");
        }
        
        if (!pousadaDAO.pousadaExiste(idPousada)) {
            throw new EntidadeNaoEncontradaException("Pousada", idPousada);
        }
        
        return quartoDAO.listarQuartosPorPousada(idPousada);
    }
    
    public void deletarQuarto(int id) throws DadosInvalidosException, Exception {
        if (id <= 0) {
            throw new DadosInvalidosException("ID do quarto deve ser maior que zero");
        }
        
        quartoDAO.deletar(id);
    }
    
    // Validações de Quarto
    private void validarQuarto(Quarto quarto) throws ValidacaoException {
        if (quarto.getNome() == null || quarto.getNome().trim().isEmpty()) {
            throw new ValidacaoException("Nome do quarto é obrigatório");
        }
        
        if (quarto.getNome().trim().length() < 3) {
            throw new ValidacaoException("Nome do quarto deve ter pelo menos 3 caracteres");
        }
        
        if (quarto.getCamas() < 1 || quarto.getCamas() > 10) {
            throw new ValidacaoException("Número de camas deve estar entre 1 e 10");
        }
        
        if (quarto.getValor_dia() < 50) {
            throw new ValidacaoException("Valor da diária deve ser no mínimo R$ 50,00");
        }
        
        if (quarto.getValor_dia() > 10000) {
            throw new ValidacaoException("Valor da diária não pode ser superior a R$ 10.000,00");
        }
        
        if (quarto.getPousada() <= 0) {
            throw new ValidacaoException("ID da pousada deve ser maior que zero");
        }
    }
    
    // Cálculos de Valor
    public double calcularValorTotal(Quarto quarto, int dias) throws DadosInvalidosException {
        if (dias <= 0) {
            throw new DadosInvalidosException("Número de dias deve ser maior que zero");
        }
        
        if (dias > 365) {
            throw new DadosInvalidosException("Número de dias não pode ser superior a 365");
        }
        
        double valorTotal = quarto.getValor_dia() * dias;
        
        // Desconto progressivo: 7+ dias = 15%, 3-6 dias = 10%
        if (dias >= 7) {
            valorTotal = valorTotal * 0.85;
        } else if (dias >= 3) {
            valorTotal = valorTotal * 0.90;
        }
        
        return valorTotal;
    }
    
    public double calcularValorComAdicionalLuxo(Quarto quarto, int dias) throws DadosInvalidosException {
        double valorBase = calcularValorTotal(quarto, dias);
        
        if (quarto instanceof IQuartoLuxo) {
            IQuartoLuxo quartoLuxo = (IQuartoLuxo) quarto;
            double adicionalPorDia = quartoLuxo.calcularAdicionalLuxo();
            valorBase += (adicionalPorDia * dias);
        }
        
        return valorBase;
    }
    
    // Estatísticas e Filtros
    public int contarQuartosPorPousada(int idPousada) throws Exception {
        List<Quarto> quartos = listarQuartosPorPousada(idPousada);
        return quartos.size();
    }
    
    public int calcularCapacidadeTotal(int idPousada) throws Exception {
        List<Quarto> quartos = listarQuartosPorPousada(idPousada);
        
        int capacidadeTotal = 0;
        for (Quarto q : quartos) {
            capacidadeTotal += q.getCamas();
        }
        
        return capacidadeTotal;
    }
    
    public List<Quarto> buscarQuartosPorValorMaximo(int idPousada, int valorMaximo) throws DadosInvalidosException, EntidadeNaoEncontradaException, Exception {
        if (valorMaximo <= 0) {
            throw new DadosInvalidosException("Valor máximo deve ser maior que zero");
        }
        
        List<Quarto> todosQuartos = listarQuartosPorPousada(idPousada);
        List<Quarto> resultado = new java.util.ArrayList<>();
        
        for (Quarto q : todosQuartos) {
            if (q.getValor_dia() <= valorMaximo) {
                resultado.add(q);
            }
        }
        
        return resultado;
    }
    
    public List<Quarto> listarQuartosLuxo(int idPousada) throws Exception {
        List<Quarto> todosQuartos = listarQuartosPorPousada(idPousada);
        List<Quarto> quartosLuxo = new java.util.ArrayList<>();
        
        for (Quarto q : todosQuartos) {
            if (q instanceof IQuartoLuxo) {
                quartosLuxo.add(q);
            }
        }
        
        return quartosLuxo;
    }
}
