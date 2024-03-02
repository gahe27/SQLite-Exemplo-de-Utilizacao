package com.example.prova2b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersistenceHelper extends SQLiteOpenHelper{
    public static final String NOME_BANCO =  "Prova2B";
    public static final int VERSAO =  10;

    private static PersistenceHelper instance;

    private PersistenceHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    public static PersistenceHelper getInstance(Context context) {
        if(instance == null)
            instance = new PersistenceHelper(context);

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // criar aqui todas as tabelas do Banco
        db.execSQL(UsuarioDAO.scriptCriaUsuario);
        db.execSQL(LancamentoDAO.scriptCriaLancamento);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioDAO.scriptDropUsuario);
        db.execSQL(LancamentoDAO.scriptDropLancamento);
        onCreate(db);
    }
}

