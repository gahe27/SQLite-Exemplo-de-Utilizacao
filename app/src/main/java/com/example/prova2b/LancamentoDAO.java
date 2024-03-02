package com.example.prova2b;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LancamentoDAO implements interfaceDAO{
    public static final String nomeTabela2 = "Lancamento";
    public static final String colunaId = "idLancamento";
    public static final String colunaNomeUsuario = "nomeUsuario";
    public static final String colunaValor = "valorLancamento";
    public static final String colunaData = "dataLancamento";
    public static final String colunaTipo = "tipoLancamento";

    public static final String scriptCriaLancamento = "CREATE TABLE " + nomeTabela2 + "("
            + colunaId + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colunaNomeUsuario + " TEXT, "
            + colunaValor + " TEXT, " + colunaData + " TEXT, " + colunaTipo + " TEXT)";

    public static final String scriptDropLancamento=  "DROP TABLE IF EXISTS " + nomeTabela2;

    private SQLiteDatabase dataBase = null;

    private static LancamentoDAO instance;  // singleton
    public static LancamentoDAO getInstance(Context context) {
        if(instance == null)
            instance = new LancamentoDAO(context);
        return instance;
    }

    private LancamentoDAO(Context context) {
        PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
    }

    public void salvar(Object obj) {
        Lancamento lancamento = (Lancamento) obj;   // casting
        ContentValues values = gerarContentValuesLancamento(lancamento);
        dataBase.insert(nomeTabela2, null, values);
    }

    private ContentValues gerarContentValuesLancamento(Lancamento lancamento) {
        ContentValues values = new ContentValues();
        values.put(colunaNomeUsuario, lancamento.getNomeUsuario());
        values.put(colunaValor, lancamento.getValor());
        values.put(colunaData, lancamento.getData());
        values.put(colunaTipo, lancamento.getTipo());
        // Serve para linkar a coluna com o registro:
        return values;
    }

    public List<String> recuperarLancamentoDatas(String data1, String data2) {
        List<String> listLancamentos = new ArrayList<>();
        Cursor cursor = dataBase.query(nomeTabela2,null, colunaData + " BETWEEN ? AND ?", new String[]{data1, data2}, null, null, null);
        int indexId        = cursor.getColumnIndex(colunaId);
        int indexNomeUsuario        = cursor.getColumnIndex(colunaNomeUsuario);
        int indexValor      = cursor.getColumnIndex(colunaValor);
        int indexData        = cursor.getColumnIndex(colunaData);
        int indexTipo        = cursor.getColumnIndex(colunaTipo);

        Lancamento lancamento = new Lancamento();
        while(cursor.moveToNext()){
            lancamento = new Lancamento(cursor.getInt(indexId), cursor.getString(indexNomeUsuario), cursor.getString(indexValor), cursor.getString(indexData), cursor.getString(indexTipo));
            listLancamentos.add(lancamento.getDados());
        }
        cursor.close();
        return listLancamentos;
    }

    public List<String> recuperarLancamentoCompleto(String data1, String data2, String tipo) {
        List<String> listLancamentos = new ArrayList<>();
        Cursor cursor = dataBase.query(nomeTabela2,null, colunaData + " BETWEEN ? AND ? AND " + colunaTipo + " = ?", new String[]{data1, data2, tipo}, null, null, null);
        int indexId        = cursor.getColumnIndex(colunaId);
        int indexNomeUsuario        = cursor.getColumnIndex(colunaNomeUsuario);
        int indexValor      = cursor.getColumnIndex(colunaValor);
        int indexData        = cursor.getColumnIndex(colunaData);
        int indexTipo        = cursor.getColumnIndex(colunaTipo);

        Lancamento lancamento = new Lancamento();
        while(cursor.moveToNext()){
            lancamento = new Lancamento(cursor.getInt(indexId), cursor.getString(indexNomeUsuario), cursor.getString(indexValor), cursor.getString(indexData), cursor.getString(indexTipo));
            listLancamentos.add(lancamento.getDados());
        }
        cursor.close();
        return listLancamentos;
    }

    public List<String> recuperarLancamentoAutomatico() {
        List<String> listLancamentos = new ArrayList<>();
        Cursor cursor = dataBase.rawQuery("SELECT * FROM " + nomeTabela2 + ";", null);
        int indexId        = cursor.getColumnIndex(colunaId);
        int indexNomeUsuario        = cursor.getColumnIndex(colunaNomeUsuario);
        int indexValor      = cursor.getColumnIndex(colunaValor);
        int indexData        = cursor.getColumnIndex(colunaData);
        int indexTipo        = cursor.getColumnIndex(colunaTipo);

        Lancamento lancamento = new Lancamento();
        while(cursor.moveToNext()){
            lancamento = new Lancamento(cursor.getInt(indexId), cursor.getString(indexNomeUsuario), cursor.getString(indexValor), cursor.getString(indexData), cursor.getString(indexTipo));
            listLancamentos.add(lancamento.getDados());
        }
        cursor.close();
        return listLancamentos;
    }

    private Lancamento construirLancamento(Cursor cursor)
    {
        if (cursor == null)
            return null;
        try {
            cursor.moveToFirst();    // posiciona o cursor - recupera o primeiro que localizar
            // retorna a coluna do campo - inteiro
            int indexId        = cursor.getColumnIndex(colunaId);
            int indexNomeUsuario        = cursor.getColumnIndex(colunaNomeUsuario);
            int indexValor      = cursor.getColumnIndex(colunaValor);
            int indexData        = cursor.getColumnIndex(colunaData);
            int indexTipo        = cursor.getColumnIndex(colunaTipo);

            int idLancamento = cursor.getInt(indexId);
            String nomeUsuario = cursor.getString(indexNomeUsuario);
            String Valor = cursor.getString(indexValor);
            String Data = cursor.getString(indexData);
            String Tipo = cursor.getString(indexTipo);
            // cria uma nova instancia do lancamento e retorna o mesmo
            return new Lancamento(idLancamento, nomeUsuario, Valor, Data, Tipo);
        }
        catch (Exception e)
        {
            return null;
        }

    }
}
