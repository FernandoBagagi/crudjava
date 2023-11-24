package br.com.ferdbgg.model.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.ferdbgg.bancodedados.BD;
import br.com.ferdbgg.model.dao.impl.DepartamentoDAOJDBC;

public class FabricaDAO {
    
    public static final Map<String, EntidadeDAO<? extends Object>> DAOS = new HashMap<>();

    static {
        FabricaDAO.DAOS.put("Departamento", new DepartamentoDAOJDBC(BD.getConexao()));
    }
    
    private FabricaDAO() {

    }
}
