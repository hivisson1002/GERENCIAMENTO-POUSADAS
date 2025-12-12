package bo;

import dao.PousadaDAO;
import dto.Pousada;
import exception.DadosInvalidosException;
import exception.EntidadeNaoEncontradaException;
import exception.ValidacaoException;
import java.util.List;

public class PousadaBO {
    private PousadaDAO dao;
    
    public PousadaBO() {
        this.dao = new PousadaDAO();
    }
    
    public void adicionarPousada(Pousada pousada) throws ValidacaoException, Exception {
        validarPousada(pousada);
        dao.adicionar(pousada);
    }
    
    public Pousada obterPousadaPorId(int id) throws DadosInvalidosException, EntidadeNaoEncontradaException, Exception {
        if (id <= 0) {
            throw new DadosInvalidosException("ID deve ser maior que zero");
        }
        
        Pousada pousada = dao.obterPorId(id);
        
        if (pousada == null) {
            throw new EntidadeNaoEncontradaException("Pousada", id);
        }
        
        return pousada;
    }
    
    public void atualizarPousada(Pousada pousada) throws ValidacaoException, DadosInvalidosException, EntidadeNaoEncontradaException, Exception {
        if (pousada.getId() <= 0) {
            throw new DadosInvalidosException("ID inválido para atualização");
        }
        
        validarPousada(pousada);
        
        if (!dao.pousadaExiste(pousada.getId())) {
            throw new EntidadeNaoEncontradaException("Pousada não existe no banco de dados");
        }
        
        dao.atualizar(pousada);
    }
    
    public void deletarPousada(int id) throws DadosInvalidosException, EntidadeNaoEncontradaException, Exception {
        if (id <= 0) {
            throw new DadosInvalidosException("ID deve ser maior que zero");
        }
        
        if (!dao.pousadaExiste(id)) {
            throw new EntidadeNaoEncontradaException("Pousada não existe no banco de dados");
        }
        
        dao.deletar(id);
    }
    
    public List<Pousada> listarTodas() throws Exception {
        return dao.listarTodos();
    }
    
    public boolean pousadaExiste(int id) {
        return dao.pousadaExiste(id);
    }
    
    // Validações de Pousada
    private void validarPousada(Pousada pousada) throws ValidacaoException {
        if (pousada.getNome() == null || pousada.getNome().trim().isEmpty()) {
            throw new ValidacaoException("Nome da pousada é obrigatório");
        }
        
        if (pousada.getNome().trim().length() < 3) {
            throw new ValidacaoException("Nome deve ter pelo menos 3 caracteres");
        }
        
        if (pousada.getEstrelas() < 1 || pousada.getEstrelas() > 5) {
            throw new ValidacaoException("Número de estrelas deve estar entre 1 e 5");
        }
        
        if (pousada.getTelefone() == null || pousada.getTelefone().trim().isEmpty()) {
            throw new ValidacaoException("Telefone é obrigatório");
        }
        
        if (!validarTelefone(pousada.getTelefone())) {
            throw new ValidacaoException("Telefone inválido. Deve conter pelo menos 8 dígitos");
        }
        
        if (pousada.getCidade() == null || pousada.getCidade().trim().isEmpty()) {
            throw new ValidacaoException("Cidade é obrigatória");
        }
        
        if (pousada.getEstado() == null || pousada.getEstado().trim().isEmpty()) {
            throw new ValidacaoException("Estado é obrigatório");
        }
        
        if (pousada.getEstado().trim().length() != 2) {
            throw new ValidacaoException("Estado deve ser uma sigla de 2 letras (ex: AL, SP, RJ)");
        }
    }
    
    private boolean validarTelefone(String telefone) {
        String numeroLimpo = telefone.replaceAll("[^0-9]", "");
        return numeroLimpo.length() >= 8 && numeroLimpo.length() <= 11;
    }
    
    // Estatísticas
    public double calcularMediaEstrelas() throws Exception {
        List<Pousada> pousadas = listarTodas();
        
        if (pousadas.isEmpty()) {
            return 0.0;
        }
        
        int somaEstrelas = 0;
        for (Pousada p : pousadas) {
            somaEstrelas += p.getEstrelas();
        }
        
        return (double) somaEstrelas / pousadas.size();
    }
    
    public List<Pousada> buscarPorEstrelas(int estrelas) throws DadosInvalidosException, Exception {
        if (estrelas < 1 || estrelas > 5) {
            throw new DadosInvalidosException("Número de estrelas deve estar entre 1 e 5");
        }
        
        List<Pousada> todasPousadas = listarTodas();
        List<Pousada> resultado = new java.util.ArrayList<>();
        
        for (Pousada p : todasPousadas) {
            if (p.getEstrelas() == estrelas) {
                resultado.add(p);
            }
        }
        
        return resultado;
    }
}
