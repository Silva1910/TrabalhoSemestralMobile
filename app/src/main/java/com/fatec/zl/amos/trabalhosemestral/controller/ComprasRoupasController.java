package com.fatec.zl.amos.trabalhosemestral.controller;

import com.fatec.zl.amos.trabalhosemestral.model.ComprasRoupas;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasRoupasDao;

import java.sql.SQLException;
import java.util.List;

public class ComprasRoupasController implements IController<ComprasRoupas> {
    private final ComprasRoupasDao rDao;

    public ComprasRoupasController(ComprasRoupasDao rDao) {
        this.rDao = rDao;
    }

    @Override
    public void inserir(ComprasRoupas comprasRoupas) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.inserir(comprasRoupas);
        rDao.close();
    }

    @Override
    public void modificar(ComprasRoupas comprasRoupas) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.atualizar(comprasRoupas);
        rDao.close();
    }

    @Override
    public void deletar(ComprasRoupas comprasRoupas) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.deletar(comprasRoupas);
        rDao.close();
    }

    @Override
    public ComprasRoupas buscar(ComprasRoupas comprasRoupas) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        return rDao.consultar(comprasRoupas);
    }

    @Override
    public List<ComprasRoupas> listar() throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        return rDao.listar();
    }
}
