package com.fatec.zl.amos.trabalhosemestral.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fatec.zl.amos.trabalhosemestral.model.Renda;

import java.util.ArrayList;
import java.util.List;

public class RendaDao implements ICRUDDao<Renda> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public RendaDao(Context context) {
        this.context = context;
    }

    public RendaDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void inserir(Renda renda) throws SQLException {
        ContentValues contentValues = getContentValues(renda);
        database.insert("renda", null, contentValues);
    }

    @Override
    public int atualizar(Renda renda) throws SQLException {
        ContentValues contentValues = getContentValues(renda);
        return database.update("renda", contentValues, "id_renda = ?", new String[]{String.valueOf(renda.getIDRenda())});
    }

    @Override
    public void deletar(Renda renda) throws SQLException {
        database.delete("renda", "id_renda = ?", new String[]{String.valueOf(renda.getIDRenda())});
    }

    @SuppressLint("Range")
    @Override
    public Renda consultar(Renda renda) throws SQLException {
        String sql = "SELECT * FROM renda WHERE id_renda = " + renda.getIDRenda();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            renda.setIDRenda(cursor.getInt(cursor.getColumnIndex("id_renda")));
            renda.setValorRenda(cursor.getFloat(cursor.getColumnIndex("valor_renda")));
            renda.setFonteRenda(cursor.getString(cursor.getColumnIndex("fonte_renda")));
        }

        if (cursor != null) {
            cursor.close();
        }
        return renda;
    }

    @SuppressLint("Range")
    @Override
    public List<Renda> listar() throws SQLException {
        List<Renda> rendas = new ArrayList<>();
        String sql = "SELECT * FROM renda";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Renda renda = new Renda();
                renda.setIDRenda(cursor.getInt(cursor.getColumnIndex("id_renda")));
                renda.setValorRenda(cursor.getFloat(cursor.getColumnIndex("valor_renda")));
                renda.setFonteRenda(cursor.getString(cursor.getColumnIndex("fonte_renda")));
                rendas.add(renda);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return rendas;
    }

    private static ContentValues getContentValues(Renda renda) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_renda", renda.getIDRenda());
        contentValues.put("valor_renda", renda.getValorRenda());
        contentValues.put("fonte_renda", renda.getFonteRenda());
        return contentValues;
    }
}
