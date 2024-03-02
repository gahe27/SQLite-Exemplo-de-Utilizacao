package com.example.prova2b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO implements interfaceDAO{
    public static final String nomeTabela = "Usuario";
    public static final String colunaId = "idUsuario";
    public static final String colunaNome = "nomeUsuario";
    public static final String colunaSenha = "senhaUsuario";

    public static final String scriptCriaUsuario = "CREATE TABLE " + nomeTabela + "("
            + colunaId + " INTEGER PRIMARY KEY AUTOINCREMENT," + colunaNome + " TEXT,"
            + colunaSenha + " TEXT" + ")";

    public static final String scriptDropUsuario=  "DROP TABLE IF EXISTS " + nomeTabela;

    private SQLiteDatabase dataBase = null;

    private static UsuarioDAO instance;  // singleton
    public static UsuarioDAO getInstance(Context context) {
        if(instance == null)
            instance = new UsuarioDAO(context);
        return instance;
    }

    private UsuarioDAO(Context context) {
        PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
    }

    public void salvar(Object obj) {
        Usuario usuario = (Usuario) obj;   // casting
        ContentValues values = gerarContentValuesUsuario(usuario);
        dataBase.insert(nomeTabela, null, values);
    }

    private ContentValues gerarContentValuesUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(colunaNome, usuario.getNome());
        values.put(colunaSenha, usuario.getSenha());
        // Serve para linkar a coluna com o registro:
        return values;
    }
    public Usuario recuperarUsuarioNome(Usuario usuario) {
        // Este é um procedimento para recuperar um usuario no BD
        //  a partir do Nome
        String query = "SELECT * FROM " + nomeTabela + " where nomeUsuario = " + " '" + usuario.getNome() + "'";
        Cursor cursor = dataBase.rawQuery(query, null);
        usuario = construirUsuario(cursor);
        return usuario;
    }
    public Usuario recuperarUsuarioNomeSenha(Usuario usuario) {
        // Este é um procedimento para recuperar um usuario no BD
        //  a partir do Nome
        String query = "SELECT * FROM " + nomeTabela + " where nomeUsuario = " + " '" + usuario.getNome() + "' AND senhaUsuario = " + " '" + usuario.getSenha() + "'";
        Cursor cursor = dataBase.rawQuery(query, null);
        usuario = construirUsuario(cursor);
        return usuario;
    }

        public Usuario recuperarUsuarioId(Usuario usuario){
            // Este é um procedimento para recuperar um usuario no BD a partir do id
            String query = "SELECT * FROM " + nomeTabela + " where idUsuario = " + Integer.toString(usuario.getIdUsuario());
            Cursor cursor = dataBase.rawQuery(query, null);
            usuario = construirUsuario(cursor);
            return usuario;
        }

        private Usuario construirUsuario(Cursor cursor){
            if (cursor == null)
                return null;
            try {
                cursor.moveToFirst();    // posiciona o cursor - recupera o primeiro que localizar
                // retorna a coluna do campo - inteiro
                int indexId = cursor.getColumnIndex(colunaId);
                int indexNome = cursor.getColumnIndex(colunaNome);
                //int indexSenha =  cursor.getColumnIndex(colunaSenha);

                int id = cursor.getInt(indexId);
                String nome = cursor.getString(indexNome);
                //String senha = cursor.getString(indexSenha);
                // cria uma nova instancia do cliente e retorna o mesmo
                return new Usuario(id, nome);

            } catch (Exception e) {
                return null;
            }

        }
}
