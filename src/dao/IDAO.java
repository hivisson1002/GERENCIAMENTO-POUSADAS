package dao;

import java.util.List;

public interface IDAO<T> {
    void adicionar(T obj) throws Exception;
    T obterPorId(int id) throws Exception;
    void atualizar(T obj) throws Exception;
    void deletar(int id) throws Exception;
    List<T> listarTodos() throws Exception;
}
