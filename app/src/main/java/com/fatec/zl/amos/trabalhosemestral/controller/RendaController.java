package com.fatec.zl.amos.trabalhosemestral.controller;

import com.fatec.zl.amos.trabalhosemestral.model.Renda;
import com.fatec.zl.amos.trabalhosemestral.persistence.RendaDao;

import java.sql.SQLException;
import java.util.List;

public class RendaController implements IController<Renda> {
    private final RendaDao rDao;

    public RendaController(RendaDao rDao) {
        this.rDao = rDao;
    }

    @Override
    public void inserir(Renda renda) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.inserir(renda);
        rDao.close();
    }

    @Override
    public void modificar(Renda renda) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.atualizar(renda);
        rDao.close();
    }

    @Override
    public void deletar(Renda renda) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.deletar(renda);
        rDao.close();
    }

    @Override
    public Renda buscar(Renda renda) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        return rDao.consultar(renda);
    }

    @Override
    public List<Renda> listar() throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        return rDao.listar();
    }
}
