package com.fatec.zl.amos.trabalhosemestral.persistence;

import android.annotation.SuppressLint;

import com.fatec.zl.amos.trabalhosemestral.model.ComprasMercado;

import java.sql.SQLException;
import java.util.List;

public interface ICRUDDao<T> {

    public void inserir(T t) throws SQLException;
    public int atualizar(T t) throws SQLException;
    public void deletar(T t) throws SQLException;
    public T consultar(T t) throws SQLException;


    public List<T> listar() throws SQLException;

}
