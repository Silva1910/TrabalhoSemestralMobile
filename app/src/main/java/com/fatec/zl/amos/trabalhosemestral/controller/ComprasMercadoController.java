package com.fatec.zl.amos.trabalhosemestral.controller;

import com.fatec.zl.amos.trabalhosemestral.model.ComprasMercado;
import com.fatec.zl.amos.trabalhosemestral.persistence.ComprasMercadoDao;

import java.sql.SQLException;
import java.util.List;

public class ComprasMercadoController implements IController<ComprasMercado>{
    private final ComprasMercadoDao mDao;

    public ComprasMercadoController(ComprasMercadoDao mDao) {
        this.mDao = mDao;
    }


    @Override
    public void inserir(ComprasMercado comprasMercado) throws SQLException {
        if(mDao.open()==null){
            mDao.open();
        }
        mDao.inserir(comprasMercado);
        mDao.close();

    }

    @Override
    public void modificar(ComprasMercado comprasMercado) throws SQLException {

        if(mDao.open()==null){
            mDao.open();
        }
        mDao.atualizar(comprasMercado);
        mDao.close();

    }

    @Override
    public void deletar(ComprasMercado comprasMercado) throws SQLException {
        if(mDao.open()==null){
            mDao.open();
        }
        mDao.deletar(comprasMercado);
        mDao.close();
    }

    @Override
    public ComprasMercado buscar(ComprasMercado comprasMercado) throws SQLException {
        if(mDao.open()==null){
            mDao.open();
        }
        return mDao.consultar(comprasMercado);

    }

    @Override
    public List<ComprasMercado> listar() throws SQLException {
        if(mDao.open()==null){
            mDao.open();
        }
       return mDao.listar();
       }
}
