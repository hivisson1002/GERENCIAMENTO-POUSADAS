package bo;

import dao.QuartoDAO;
import dao.PousadaDAO;
import dto.Quarto;
import dto.IQuartoLuxo;
import java.util.List;

/**
 * Business Object para Quarto
 * Contém regras de negócio e validações antes de acessar o DAO
 */
public class QuartoBO {
    private QuartoDAO quartoDAO;
    private PousadaDAO pousadaDAO;
    
    public QuartoBO() {
        this.quartoDAO = new QuartoDAO();
        this.pousadaDAO = new PousadaDAO();
    }
    
    /**
     * Adiciona um quarto após validações
     * @param quarto Quarto a ser adicionado
     * @throws Exception Se alguma validação falhar
     */
    public void adicionarQuarto(Quarto quarto) throws Exception {
        validarQuarto(quarto);
        
        // Regra de negócio: Pousada deve existir antes de adicionar quarto
        if (!pousadaDAO.pousadaExiste(quarto.getPousada())) {
            throw new Exception("Pousada com ID " + quarto.getPousada() + " não existe");
        }
        
        quartoDAO.adicionar(quarto);
    }
    
    /**
     * Lista quartos de uma pousada específica
     * @param idPousada ID da pousada
     * @return Lista de quartos
     * @throws Exception Se ID for inválido ou houver erro no banco
     */
    public List<Quarto> listarQuartosPorPousada(int idPousada) throws Exception {
        if (idPousada <= 0) {
            throw new Exception("ID da pousada deve ser maior que zero");
        }
        
        if (!pousadaDAO.pousadaExiste(idPousada)) {
            throw new Exception("Pousada com ID " + idPousada + " não existe");
        }
        
        return quartoDAO.listarQuartosPorPousada(idPousada);
    }
    
    /**
     * Deleta um quarto
     * @param id ID do quarto a ser deletado
     * @throws Exception Se ID for inválido
     */
    public void deletarQuarto(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID do quarto deve ser maior que zero");
        }
        
        quartoDAO.deletar(id);
    }
    
    /**
     * Valida os dados de um quarto
     * @param quarto Quarto a ser validado
     * @throws Exception Se alguma validação falhar
     */
    private void validarQuarto(Quarto quarto) throws Exception {
        // Regra 1: Nome é obrigatório
        if (quarto.getNome() == null || quarto.getNome().trim().isEmpty()) {
            throw new Exception("Nome do quarto é obrigatório");
        }
        
        // Regra 2: Nome deve ter pelo menos 3 caracteres
        if (quarto.getNome().trim().length() < 3) {
            throw new Exception("Nome do quarto deve ter pelo menos 3 caracteres");
        }
        
        // Regra 3: Número de camas deve ser positivo e razoável
        if (quarto.getCamas() < 1 || quarto.getCamas() > 10) {
            throw new Exception("Número de camas deve estar entre 1 e 10");
        }
        
        // Regra 4: Valor da diária deve ser no mínimo R$ 50
        if (quarto.getValor_dia() < 50) {
            throw new Exception("Valor da diária deve ser no mínimo R$ 50,00");
        }
        
        // Regra 5: Valor da diária não pode ser excessivo
        if (quarto.getValor_dia() > 10000) {
            throw new Exception("Valor da diária não pode ser superior a R$ 10.000,00");
        }
        
        // Regra 6: ID da pousada deve ser positivo
        if (quarto.getPousada() <= 0) {
            throw new Exception("ID da pousada deve ser maior que zero");
        }
    }
    
    /**
     * Calcula o valor total da hospedagem com desconto progressivo
     * REGRA DE NEGÓCIO: Sistema de descontos por tempo de permanência
     * - 7+ dias: 15% desconto
     * - 3-6 dias: 10% desconto
     * - 1-2 dias: sem desconto
     * 
     * @param quarto Quarto selecionado
     * @param dias Número de dias de hospedagem
     * @return Valor total com desconto aplicado
     * @throws Exception Se parâmetros forem inválidos
     */
    public double calcularValorTotal(Quarto quarto, int dias) throws Exception {
        if (dias <= 0) {
            throw new Exception("Número de dias deve ser maior que zero");
        }
        
        if (dias > 365) {
            throw new Exception("Número de dias não pode ser superior a 365");
        }
        
        double valorTotal = quarto.getValor_dia() * dias;
        
        // Aplicar desconto progressivo
        if (dias >= 7) {
            valorTotal = valorTotal * 0.85; // 15% desconto
        } else if (dias >= 3) {
            valorTotal = valorTotal * 0.90; // 10% desconto
        }
        
        return valorTotal;
    }
    
    /**
     * Calcula o valor total incluindo adicional de luxo (se aplicável)
     * REGRA DE NEGÓCIO: Quartos de luxo têm valor adicional por amenidades
     * 
     * @param quarto Quarto selecionado
     * @param dias Número de dias de hospedagem
     * @return Valor total com adicional de luxo
     * @throws Exception Se parâmetros forem inválidos
     */
    public double calcularValorComAdicionalLuxo(Quarto quarto, int dias) throws Exception {
        double valorBase = calcularValorTotal(quarto, dias);
        
        // Se o quarto implementa IQuartoLuxo, adiciona o valor das amenidades
        if (quarto instanceof IQuartoLuxo) {
            IQuartoLuxo quartoLuxo = (IQuartoLuxo) quarto;
            double adicionalPorDia = quartoLuxo.calcularAdicionalLuxo();
            valorBase += (adicionalPorDia * dias);
        }
        
        return valorBase;
    }
    
    /**
     * Conta quantos quartos uma pousada possui
     * REGRA DE NEGÓCIO: Estatística da pousada
     * 
     * @param idPousada ID da pousada
     * @return Número de quartos
     * @throws Exception Se houver erro no acesso ao banco
     */
    public int contarQuartosPorPousada(int idPousada) throws Exception {
        List<Quarto> quartos = listarQuartosPorPousada(idPousada);
        return quartos.size();
    }
    
    /**
     * Calcula a capacidade total de hóspedes de uma pousada
     * REGRA DE NEGÓCIO: Soma todas as camas disponíveis
     * 
     * @param idPousada ID da pousada
     * @return Número total de camas
     * @throws Exception Se houver erro no acesso ao banco
     */
    public int calcularCapacidadeTotal(int idPousada) throws Exception {
        List<Quarto> quartos = listarQuartosPorPousada(idPousada);
        
        int capacidadeTotal = 0;
        for (Quarto q : quartos) {
            capacidadeTotal += q.getCamas();
        }
        
        return capacidadeTotal;
    }
    
    /**
     * Busca quartos disponíveis com valor máximo especificado
     * REGRA DE NEGÓCIO: Filtro por orçamento
     * 
     * @param idPousada ID da pousada
     * @param valorMaximo Valor máximo da diária
     * @return Lista de quartos dentro do orçamento
     * @throws Exception Se houver erro no acesso ao banco
     */
    public List<Quarto> buscarQuartosPorValorMaximo(int idPousada, int valorMaximo) throws Exception {
        if (valorMaximo <= 0) {
            throw new Exception("Valor máximo deve ser maior que zero");
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
    
    /**
     * Lista apenas quartos de luxo de uma pousada
     * REGRA DE NEGÓCIO: Filtro por tipo especial
     * 
     * @param idPousada ID da pousada
     * @return Lista de quartos que implementam IQuartoLuxo
     * @throws Exception Se houver erro no acesso ao banco
     */
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
