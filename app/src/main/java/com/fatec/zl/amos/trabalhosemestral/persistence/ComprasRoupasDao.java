package com.fatec.zl.amos.trabalhosemestral.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fatec.zl.amos.trabalhosemestral.model.ComprasRoupas;

import java.util.ArrayList;
import java.util.List;

public class ComprasRoupasDao implements ICRUDDao<ComprasRoupas> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public ComprasRoupasDao(Context context) {
        this.context = context;
    }

    public ComprasRoupasDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void inserir(ComprasRoupas comprasRoupas) throws SQLException {
        ContentValues contentValues = getContentValues(comprasRoupas);
        database.insert("compras_roupas", null, contentValues);
    }

    @Override
    public int atualizar(ComprasRoupas comprasRoupas) throws SQLException {
        ContentValues contentValues = getContentValues(comprasRoupas);
        return database.update("compras_roupas", contentValues, "id_compras_roupas = ?", new String[]{String.valueOf(comprasRoupas.getIDCompra())});
    }

    @Override
    public void deletar(ComprasRoupas comprasRoupas) throws SQLException {
        Log.d("ComprasRoupasDao", "Deletando compra ID: " + comprasRoupas.getIDCompra());
        int rows = database.delete("compras_roupas", "id_compras_roupas = ?", new String[]{String.valueOf(comprasRoupas.getIDCompra())});
        Log.d("ComprasRoupasDao", "Rows affected: " + rows);
    }

    @SuppressLint("Range")
    @Override
    public ComprasRoupas consultar(ComprasRoupas comprasRoupas) throws SQLException {
        String sql = "SELECT id_compras_roupas, quantidade_roupas, valor_roupas, tamanho_roupas, tipo_roupas FROM compras_roupas WHERE id_compras_roupas = " + comprasRoupas.getIDCompra();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            comprasRoupas.setIDCompra(cursor.getInt(cursor.getColumnIndex("id_compras_roupas")));
            comprasRoupas.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade_roupas")));
            comprasRoupas.setValor(cursor.getFloat(cursor.getColumnIndex("valor_roupas")));
            comprasRoupas.setTamanho(cursor.getString(cursor.getColumnIndex("tamanho_roupas")));
            comprasRoupas.setTipoRoupa(cursor.getString(cursor.getColumnIndex("tipo_roupas")));
        }

        if (cursor != null) {
            cursor.close();
        }
        return comprasRoupas;
    }

    @SuppressLint("Range")
    @Override
    public List<ComprasRoupas> listar() throws SQLException {
        List<ComprasRoupas> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras_roupas";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ComprasRoupas comprasRoupas = new ComprasRoupas();
                comprasRoupas.setIDCompra(cursor.getInt(cursor.getColumnIndex("id_compras_roupas")));
                comprasRoupas.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade_roupas")));
                comprasRoupas.setValor(cursor.getFloat(cursor.getColumnIndex("valor_roupas")));
                comprasRoupas.setTamanho(cursor.getString(cursor.getColumnIndex("tamanho_roupas")));
                comprasRoupas.setTipoRoupa(cursor.getString(cursor.getColumnIndex("tipo_roupas")));
                compras.add(comprasRoupas);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return compras;
    }

    private static ContentValues getContentValues(ComprasRoupas comprasRoupas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_compras_roupas", comprasRoupas.getIDCompra());
        contentValues.put("quantidade_roupas", comprasRoupas.getQuantidade());
        contentValues.put("valor_roupas", comprasRoupas.getValor());
        contentValues.put("tamanho_roupas", comprasRoupas.getTamanho());
        contentValues.put("tipo_roupas", comprasRoupas.getTipoRoupa());
        return contentValues;
    }
}
