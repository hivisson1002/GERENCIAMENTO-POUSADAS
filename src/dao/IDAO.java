package dao;

import java.util.List;

/**
 * Interface genérica para operações CRUD (Create, Read, Update, Delete)
 * @param <T> 
 */
public interface IDAO<T> {
    /**
     * Adiciona um novo objeto no banco de dados
     * @param obj Objeto a ser adicionado
     * @throws Exception Se houver erro na operação
     */
    void adicionar(T obj) throws Exception;
    
    /**
     * Obtém um objeto por seu ID
     * @param id ID do objeto
     * @return Objeto encontrado ou null se não existir
     * @throws Exception Se houver erro na operação
     */
    T obterPorId(int id) throws Exception;
    
    /**
     * Atualiza um objeto existente no banco de dados
     * @param obj Objeto com dados atualizados
     * @throws Exception Se houver erro na operação
     */
    void atualizar(T obj) throws Exception;
    
    /**
     * Deleta um objeto do banco de dados
     * @param id ID do objeto a ser deletado
     * @throws Exception Se houver erro na operação
     */
    void deletar(int id) throws Exception;
    
    /**
     * Lista todos os objetos do banco de dados
     * @return Lista de objetos
     * @throws Exception Se houver erro na operação
     */
    List<T> listarTodos() throws Exception;
}
