package bo;

import dao.PousadaDAO;
import dto.Pousada;
import java.util.List;

/**
 * Business Object para Pousada
 * Contém regras de negócio e validações antes de acessar o DAO
 */
public class PousadaBO {
    private PousadaDAO dao;
    
    public PousadaBO() {
        this.dao = new PousadaDAO();
    }
    
    /**
     * Adiciona uma pousada após validações
     * @param pousada Pousada a ser adicionada
     * @throws Exception Se alguma validação falhar
     */
    public void adicionarPousada(Pousada pousada) throws Exception {
        validarPousada(pousada);
        dao.adicionar(pousada);
    }
    
    /**
     * Obtém uma pousada por ID
     * @param id ID da pousada
     * @return Pousada encontrada
     * @throws Exception Se ID for inválido ou pousada não existir
     */
    public Pousada obterPousadaPorId(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID deve ser maior que zero");
        }
        
        Pousada pousada = dao.obterPorId(id);
        
        if (pousada == null) {
            throw new Exception("Pousada com ID " + id + " não encontrada");
        }
        
        return pousada;
    }
    
    /**
     * Atualiza uma pousada após validações
     * @param pousada Pousada com dados atualizados
     * @throws Exception Se validação falhar ou pousada não existir
     */
    public void atualizarPousada(Pousada pousada) throws Exception {
        if (pousada.getId() <= 0) {
            throw new Exception("ID inválido para atualização");
        }
        
        validarPousada(pousada);
        
        if (!dao.pousadaExiste(pousada.getId())) {
            throw new Exception("Pousada não existe no banco de dados");
        }
        
        dao.atualizar(pousada);
    }
    
    /**
     * Deleta uma pousada
     * @param id ID da pousada a ser deletada
     * @throws Exception Se ID for inválido ou pousada não existir
     */
    public void deletarPousada(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID deve ser maior que zero");
        }
        
        if (!dao.pousadaExiste(id)) {
            throw new Exception("Pousada não existe no banco de dados");
        }
        
        dao.deletar(id);
    }
    
    /**
     * Lista todas as pousadas
     * @return Lista de pousadas
     * @throws Exception Se houver erro no acesso ao banco
     */
    public List<Pousada> listarTodas() throws Exception {
        return dao.listarTodos();
    }
    
    /**
     * Verifica se uma pousada existe
     * @param id ID da pousada
     * @return true se existe, false caso contrário
     */
    public boolean pousadaExiste(int id) {
        return dao.pousadaExiste(id);
    }
    
    /**
     * Valida os dados de uma pousada
     * @param pousada Pousada a ser validada
     * @throws Exception Se alguma validação falhar
     */
    private void validarPousada(Pousada pousada) throws Exception {
        // Regra 1: Nome é obrigatório
        if (pousada.getNome() == null || pousada.getNome().trim().isEmpty()) {
            throw new Exception("Nome da pousada é obrigatório");
        }
        
        // Regra 2: Nome deve ter pelo menos 3 caracteres
        if (pousada.getNome().trim().length() < 3) {
            throw new Exception("Nome deve ter pelo menos 3 caracteres");
        }
        
        // Regra 3: Estrelas devem estar entre 1 e 5
        if (pousada.getEstrelas() < 1 || pousada.getEstrelas() > 5) {
            throw new Exception("Número de estrelas deve estar entre 1 e 5");
        }
        
        // Regra 4: Telefone é obrigatório
        if (pousada.getTelefone() == null || pousada.getTelefone().trim().isEmpty()) {
            throw new Exception("Telefone é obrigatório");
        }
        
        // Regra 5: Telefone deve ter formato válido (pelo menos 8 dígitos)
        if (!validarTelefone(pousada.getTelefone())) {
            throw new Exception("Telefone inválido. Deve conter pelo menos 8 dígitos");
        }
        
        // Regra 6: Cidade é obrigatória
        if (pousada.getCidade() == null || pousada.getCidade().trim().isEmpty()) {
            throw new Exception("Cidade é obrigatória");
        }
        
        // Regra 7: Estado é obrigatório
        if (pousada.getEstado() == null || pousada.getEstado().trim().isEmpty()) {
            throw new Exception("Estado é obrigatório");
        }
        
        // Regra 8: Estado deve ter 2 caracteres (sigla)
        if (pousada.getEstado().trim().length() != 2) {
            throw new Exception("Estado deve ser uma sigla de 2 letras (ex: AL, SP, RJ)");
        }
    }
    
    /**
     * Valida formato de telefone
     * @param telefone Telefone a ser validado
     * @return true se válido, false caso contrário
     */
    private boolean validarTelefone(String telefone) {
        // Remove caracteres não numéricos
        String numeroLimpo = telefone.replaceAll("[^0-9]", "");
        
        // Telefone deve ter entre 8 e 11 dígitos
        return numeroLimpo.length() >= 8 && numeroLimpo.length() <= 11;
    }
    
    /**
     * Calcula a média de estrelas de todas as pousadas
     * REGRA DE NEGÓCIO: Estatística do sistema
     * @return Média de estrelas
     * @throws Exception Se houver erro no acesso ao banco
     */
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
    
    /**
     * Busca pousadas por número de estrelas
     * REGRA DE NEGÓCIO: Filtro personalizado
     * @param estrelas Número de estrelas desejado
     * @return Lista de pousadas com o número de estrelas especificado
     * @throws Exception Se houver erro no acesso ao banco
     */
    public List<Pousada> buscarPorEstrelas(int estrelas) throws Exception {
        if (estrelas < 1 || estrelas > 5) {
            throw new Exception("Número de estrelas deve estar entre 1 e 5");
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
