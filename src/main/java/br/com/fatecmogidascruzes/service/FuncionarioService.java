package br.com.fatecmogidascruzes.service;

import br.com.fatecmogidascruzes.model.entity.Funcionario;
import java.util.List;

public interface FuncionarioService {

    void adicionarFuncionario(Funcionario funcionario);
    List<Funcionario> buscarFuncionario(int opcao, String valorBuscar);

    boolean excluirFuncionario(int id);

}
