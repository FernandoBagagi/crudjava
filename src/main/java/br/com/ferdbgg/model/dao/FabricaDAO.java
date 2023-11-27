package br.com.ferdbgg.model.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.ferdbgg.bancodedados.BD;
import br.com.ferdbgg.model.dao.impl.DepartamentoDAOJDBC;
import br.com.ferdbgg.model.dao.impl.VendedorDAOJDBC;
import br.com.ferdbgg.model.entidades.Departamento;
import br.com.ferdbgg.model.entidades.Vendedor;

public class FabricaDAO {
    
    private static final Map<String, EntidadeDAO<? extends Object>> DAOS = new HashMap<>();

    static {
        FabricaDAO.DAOS.put(Departamento.class.getName(), new DepartamentoDAOJDBC(BD.getConexao()));
        FabricaDAO.DAOS.put(Vendedor.class.getName(), new VendedorDAOJDBC(BD.getConexao()));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Object> EntidadeDAO<T> getDAO(Class<T> classe) {
        return (EntidadeDAO<T>)FabricaDAO.DAOS.get(classe.getName());
    }
    
    private FabricaDAO() {}

}
