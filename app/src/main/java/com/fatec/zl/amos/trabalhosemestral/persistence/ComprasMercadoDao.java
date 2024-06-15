package com.fatec.zl.amos.trabalhosemestral.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fatec.zl.amos.trabalhosemestral.model.ComprasMercado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ComprasMercadoDao implements ICRUDDao<ComprasMercado> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public ComprasMercadoDao(Context context) {
        this.context = context;
    }

    public ComprasMercadoDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void inserir(ComprasMercado comprasMercado) throws SQLException {
        ContentValues contentValues = getContentValues(comprasMercado);
        database.insert("compras_mercados", null, contentValues);
    }

    @Override
    public int atualizar(ComprasMercado comprasMercado) throws SQLException {
        ContentValues contentValues = getContentValues(comprasMercado);
        return database.update("compras_mercados", contentValues, "id_compras_mercados = ?", new String[]{String.valueOf(comprasMercado.getIDCompra())});
    }

    @Override
    public void deletar(ComprasMercado comprasMercado) throws SQLException {
        database.delete("compras_mercados", "id_compras_mercados = ?", new String[]{String.valueOf(comprasMercado.getIDCompra())});
    }

    @SuppressLint("Range")
    @Override
    public ComprasMercado consultar(ComprasMercado comprasMercado) throws SQLException {
        String sql = "SELECT id_compras_mercados, quantidade_mercados, valor_mercados, data_mercados, nome_mercados FROM compras_mercados WHERE id_compras_mercados = " + comprasMercado.getIDCompra();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            comprasMercado.setIDCompra(cursor.getInt(cursor.getColumnIndex("id_compras_mercados")));
            comprasMercado.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade_mercados")));
            comprasMercado.setValor(cursor.getFloat(cursor.getColumnIndex("valor_mercados")));

            // Recuperar a data como String e converter para LocalDate
            String dataString = cursor.getString(cursor.getColumnIndex("data_mercados"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate data = LocalDate.parse(dataString, formatter);
            comprasMercado.setData(data);

            // Recuperar o nome do mercado
            comprasMercado.setMercado(cursor.getString(cursor.getColumnIndex("nome_mercados")));
        }

        if (cursor != null) {
            cursor.close();
        }
        return comprasMercado;
    }

    @SuppressLint("Range")
    @Override
    public List<ComprasMercado> listar() throws SQLException {
        List<ComprasMercado> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras_mercados";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ComprasMercado comprasMercado = new ComprasMercado();
                comprasMercado.setIDCompra(cursor.getInt(cursor.getColumnIndex("id_compras_mercados")));
                comprasMercado.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade_mercados")));
                comprasMercado.setValor(cursor.getFloat(cursor.getColumnIndex("valor_mercados")));

                // Recuperar a data como String e converter para LocalDate
                String dataString = cursor.getString(cursor.getColumnIndex("data_mercados"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate data = LocalDate.parse(dataString, formatter);
                comprasMercado.setData(data);

                comprasMercado.setMercado(cursor.getString(cursor.getColumnIndex("nome_mercados")));
                compras.add(comprasMercado);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return compras;
    }

    private static ContentValues getContentValues(ComprasMercado comprasMercado) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_compras_mercados", comprasMercado.getIDCompra());
        contentValues.put("quantidade_mercados", comprasMercado.getQuantidade());
        contentValues.put("valor_mercados", comprasMercado.getValor());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = comprasMercado.getData().format(formatter);
        contentValues.put("data_mercados", dataFormatada);
        contentValues.put("nome_mercados", comprasMercado.getMercado());

        return contentValues;
    }
}
