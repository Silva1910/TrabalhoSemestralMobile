package com.fatec.zl.amos.trabalhosemestral.controller;

import com.fatec.zl.amos.trabalhosemestral.model.ComprasOutros;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasOutrasDao;

import java.sql.SQLException;
import java.util.List;

public class ComprasOutrosController implements IController<ComprasOutros> {
    private final ComprasOutrasDao oDao;

    public ComprasOutrosController(ComprasOutrasDao oDao) {
        this.oDao = oDao;
    }

    @Override
    public void inserir(ComprasOutros comprasOutros) throws SQLException {
        if (oDao.open() == null) {
            oDao.open();
        }
        oDao.inserir(comprasOutros);
        oDao.close();
    }

    @Override
    public void modificar(ComprasOutros comprasOutros) throws SQLException {
        if (oDao.open() == null) {
            oDao.open();
        }
        oDao.atualizar(comprasOutros);
        oDao.close();
    }

    @Override
    public void deletar(ComprasOutros comprasOutros) throws SQLException {
        if (oDao.open() == null) {
            oDao.open();
        }
        oDao.deletar(comprasOutros);
        oDao.close();
    }

    @Override
    public ComprasOutros buscar(ComprasOutros comprasOutros) throws SQLException {
        if (oDao.open() == null) {
            oDao.open();
        }
        return oDao.consultar(comprasOutros);
    }

    @Override
    public List<ComprasOutros> listar() throws SQLException {
        if (oDao.open() == null) {
            oDao.open();
        }
        return oDao.listar();
    }
}
