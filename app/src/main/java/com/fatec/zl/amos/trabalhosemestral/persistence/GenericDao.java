package com.fatec.zl.amos.trabalhosemestral.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "PLANEJADOR.DB";
    private static final int DATABASE_VER = 1;

    private static final String CREATE_TABLE_RENDA =
            "CREATE TABLE renda ( " +
                    "id_renda INTEGER NOT NULL PRIMARY KEY, " +
                    "valor_renda REAL NOT NULL, " +
                    "fonte_renda VARCHAR(20) NOT NULL);";

    private static final String CREATE_TABLE_COMPRAS_ROUPAS =
            "CREATE TABLE compras_roupas ( " +
                    "id_compras_roupas INTEGER NOT NULL PRIMARY KEY, " +
                    "quantidade_roupas INTEGER NOT NULL, " +
                    "tipo_roupas VARCHAR(50) NOT NULL, " +
                    "tamanho_roupas VARCHAR(50) NOT NULL, " +
                    "valor_roupas REAL NOT NULL);";

    private static final String CREATE_TABLE_COMPRAS_OUTROS =
            "CREATE TABLE compras_outros ( " +
                    "id_compras_outros INTEGER NOT NULL PRIMARY KEY, " +
                    "quantidade_outros INTEGER NOT NULL, " +
                    "descricao_outros VARCHAR(50) NOT NULL, " +
                    "valor_outros REAL NOT NULL);";

    private static final String CREATE_TABLE_COMPRAS_MERCADO =
            "CREATE TABLE compras_mercados ( " +
                    "id_compras_mercados INTEGER NOT NULL PRIMARY KEY, " +
                    "quantidade_mercados INTEGER NOT NULL, " +
                    "nome_mercados VARCHAR(50) NOT NULL, " +
                    "data_mercados DATE NOT NULL, " +
                    "valor_mercados REAL NOT NULL);";

    public GenericDao(Context context) {

        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_RENDA);
        sqLiteDatabase.execSQL(CREATE_TABLE_COMPRAS_ROUPAS);
        sqLiteDatabase.execSQL(CREATE_TABLE_COMPRAS_OUTROS);
        sqLiteDatabase.execSQL(CREATE_TABLE_COMPRAS_MERCADO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS compras_mercados");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS compras_roupas");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS compras_outros");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS renda");
            onCreate(sqLiteDatabase);
        }
    }
}
