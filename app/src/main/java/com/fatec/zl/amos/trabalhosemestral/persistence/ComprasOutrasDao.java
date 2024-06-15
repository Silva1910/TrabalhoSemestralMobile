
package com.fatec.zl.amos.trabalhosemestral.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fatec.zl.amos.trabalhosemestral.model.ComprasOutros;

import java.util.ArrayList;
import java.util.List;

public class ComprasOutrasDao implements ICRUDDao<ComprasOutros> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public ComprasOutrasDao(Context context) {
        this.context = context;
    }

    public ComprasOutrasDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void inserir(ComprasOutros comprasOutros) throws SQLException {
        ContentValues contentValues = getContentValues(comprasOutros);
        database.insert("compras_outros", null, contentValues);
    }

    @Override
    public int atualizar(ComprasOutros comprasOutros) throws SQLException {
        ContentValues contentValues = getContentValues(comprasOutros);
        return database.update("compras_outros", contentValues, "id_compras_outros = ?", new String[]{String.valueOf(comprasOutros.getIDCompra())});
    }

    @Override
    public void deletar(ComprasOutros comprasOutros) throws SQLException {
        database.delete("compras_outros", "id_compras_outros = ?", new String[]{String.valueOf(comprasOutros.getIDCompra())});
    }

    @SuppressLint("Range")
    @Override
    public ComprasOutros consultar(ComprasOutros comprasOutros) throws SQLException {
        String sql = "SELECT id_compras_outros, quantidade_outros, valor_outros, descricao_outros FROM compras_outros WHERE id_compras_outros = " + comprasOutros.getIDCompra();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            comprasOutros.setIDCompra(cursor.getInt(cursor.getColumnIndex("id_compras_outros")));
            comprasOutros.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade_outros")));
            comprasOutros.setValor(cursor.getFloat(cursor.getColumnIndex("valor_outros")));
            comprasOutros.setDescricao(cursor.getString(cursor.getColumnIndex("descricao_outros")));
            }

        if (cursor != null) {
            cursor.close();
        }
        return comprasOutros;
    }

    @SuppressLint("Range")
    @Override
    public List<ComprasOutros> listar() throws SQLException {
        List<ComprasOutros> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras_outros";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ComprasOutros comprasOutros = new ComprasOutros();
                comprasOutros.setIDCompra(cursor.getInt(cursor.getColumnIndex("id_compras_outros")));
                comprasOutros.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade_outros")));
                comprasOutros.setValor(cursor.getFloat(cursor.getColumnIndex("valor_outros")));
                comprasOutros.setDescricao(cursor.getString(cursor.getColumnIndex("descricao_outros")));
                 compras.add(comprasOutros);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return compras;
    }

    private static ContentValues getContentValues(ComprasOutros comprasOutros) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_compras_outros", comprasOutros.getIDCompra());
        contentValues.put("quantidade_outros", comprasOutros.getQuantidade());
        contentValues.put("valor_outros", comprasOutros.getValor());
        contentValues.put("descricao_outros", comprasOutros.getDescricao());
        return contentValues;
    }
}
