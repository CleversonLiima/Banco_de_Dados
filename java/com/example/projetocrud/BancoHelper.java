package com.example.projetocrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "cadastro_pessoas.db";
    private static final int VERSAO_BANCO = 1;
    private static final String TABELA = "pessoas";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_IDADE = "idade";

    public BancoHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABELA + " ("
                + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_NOME + " TEXT UNIQUE, "
                + COLUNA_IDADE + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

    // CREATE - Cadastrar uma pessoa
    public boolean inserirUsuario(String nome, int idade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, nome);
        values.put(COLUNA_IDADE, idade);

        long resultado = db.insert(TABELA, null, values);
        db.close();
        return resultado != -1;
    }

    // READ - Consultar idade usando o nome
    public Integer buscarUsuario(String nome) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABELA,
                new String[]{COLUNA_IDADE},
                COLUNA_NOME + " = ?",
                new String[]{nome},
                null, null, null
        );

        Integer idade = null;
        if (cursor.moveToFirst()) {
            idade = cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_IDADE));
        }
        cursor.close();
        db.close();
        return idade;
    }

    // UPDATE - Alterar idade da pessoa
    public boolean atualizarUsuario(String nome, int novaIdade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_IDADE, novaIdade);

        int linhasAfetadas = db.update(TABELA, values, COLUNA_NOME + " = ?", new String[]{nome});
        db.close();
        return linhasAfetadas > 0;
    }

    // DELETE - Excluir pessoa pelo nome
    public boolean deletarUsuario(String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        int linhasAfetadas = db.delete(TABELA, COLUNA_NOME + " = ?", new String[]{nome});
        db.close();
        return linhasAfetadas > 0;
    }
}
