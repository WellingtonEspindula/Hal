package hal.esy.es.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hal.esy.es.model.Pontuacao;
import hal.esy.es.model.Usuario;


public class DBHelper extends SQLiteOpenHelper {

    // Versão BD
    private static final int DATABASE_VERSION = 7;

    // Nome BD
    private static final String DATABASE_NAME = "hal";

    // Nome das tabelas
    private static final String TABELA_USUARIO = "usuario";
    private static final String TABELA_PONTUACAO = "pontuacao";

    // Tabela Usuário - Nome das colunas
    private static final String KEY_USUARIO_ID = "idUsuario";
    private static final String KEY_USUARIO_NOME = "nome";

    // Tabela Pontuacao - Nome das colunas
    private static final String KEY_PONTUACAO_ID = "idPontuacao";
    private static final String KEY_PONTUACAO_PONTOS = "pontos";
    private static final String KEY_PONTUACAO_USUARIO_ID = "usuario_idUsuario";

    // Criação de tabelas sql
    // Criação da tabela usuário sql
    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE " + TABELA_USUARIO
            + "(" +
            KEY_USUARIO_ID + " INTEGER PRIMARY KEY," +
            KEY_USUARIO_NOME + " TEXT" +
            ")";

    // Tag table create statement
    private static final String CREATE_TABLE_PONTUACAO = "CREATE TABLE " + TABELA_PONTUACAO
            + "(" +
            KEY_PONTUACAO_ID + " INTEGER PRIMARY KEY," +
            KEY_PONTUACAO_PONTOS + " INTEGER NOT NULL," +
            KEY_PONTUACAO_USUARIO_ID + " INTEGER NOT NULL" +
            ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_PONTUACAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PONTUACAO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIO);

        onCreate(db);
    }



    // ------------------------ Usuario CRUD ----------------//
    public void inserirUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insertSQL = "INSERT INTO " + TABELA_USUARIO +
                " (" + KEY_USUARIO_NOME + ") VALUES ('" + user.getNome() + "')";

        db.execSQL(insertSQL);
    }

    public Usuario getUsuario(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABELA_USUARIO + " WHERE "
                + KEY_USUARIO_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Usuario user = new Usuario();
        user.setId(c.getInt(c.getColumnIndex(KEY_USUARIO_ID)));
        user.setNome((c.getString(c.getColumnIndex(KEY_USUARIO_NOME))));


        return user;
    }

    public ArrayList<Usuario> getTodosUsuario() {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        String selectQuery = "SELECT  * FROM " + TABELA_USUARIO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Usuario user = new Usuario();
                user.setId(c.getInt(c.getColumnIndex(KEY_USUARIO_ID)));
                user.setNome((c.getString(c.getColumnIndex(KEY_USUARIO_NOME))));

                usuarios.add(user);
            } while (c.moveToNext());
        }

        return usuarios;
    }

    public void deleteUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();

        String deleteSQL = "DELETE FROM " + TABELA_USUARIO + "WHERE " + KEY_USUARIO_ID + " = " + user.getId();
        db.execSQL(deleteSQL);
    }


    // ------------------------ Pontuacao CRUD ----------------//
    public void inserirPontuacao(Pontuacao pontos) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insertSQL = "INSERT INTO " + TABELA_PONTUACAO +
                " (" + KEY_PONTUACAO_PONTOS + ", " + KEY_PONTUACAO_USUARIO_ID + ") VALUES (" + pontos.getPontos() +
                ", " + pontos.getUsuario().getId() +
                ")";
        db.execSQL(insertSQL);
    }

    public Pontuacao getPontuacao(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABELA_PONTUACAO
                + "JOIN " + TABELA_USUARIO + " ON " + KEY_PONTUACAO_USUARIO_ID + " = " + KEY_USUARIO_ID
                + " WHERE " + KEY_PONTUACAO_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Usuario user = new Usuario();
        user.setId(c.getInt(c.getColumnIndex(KEY_USUARIO_ID)));
        user.setNome(c.getString(c.getColumnIndex(KEY_USUARIO_NOME)));

        Pontuacao pontos = new Pontuacao();
        pontos.setId(c.getInt(c.getColumnIndex(KEY_PONTUACAO_ID)));
        pontos.setPontos((c.getInt(c.getColumnIndex(KEY_PONTUACAO_PONTOS))));
        pontos.setUsuario(user);
        return pontos;
    }

    public ArrayList<Pontuacao> getTodosPontuacao() {
        ArrayList<Pontuacao> pontos = new ArrayList<Pontuacao>();
        String selectQuery = "SELECT  * FROM " + TABELA_PONTUACAO+
                             " JOIN "+TABELA_USUARIO+" ON "+KEY_USUARIO_ID+" = "+KEY_PONTUACAO_USUARIO_ID+
                             " ORDER BY "+KEY_PONTUACAO_PONTOS+" DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Usuario user = new Usuario();
                user.setId(c.getInt(c.getColumnIndex(KEY_USUARIO_ID)));
                user.setNome(c.getString(c.getColumnIndex(KEY_USUARIO_NOME)));

                Pontuacao ponto = new Pontuacao();
                ponto.setId(c.getInt(c.getColumnIndex(KEY_PONTUACAO_ID)));
                ponto.setPontos((c.getInt(c.getColumnIndex(KEY_PONTUACAO_PONTOS))));
                ponto.setUsuario(user);

                pontos.add(ponto);
            } while (c.moveToNext());
        }

        return pontos;
    }

    public void deletePontuacao(Pontuacao pontuacao) {
        SQLiteDatabase db = this.getWritableDatabase();

        String deleteSQL = "DELETE FROM " + TABELA_PONTUACAO + "WHERE " + KEY_PONTUACAO_ID + " = " + pontuacao.getId();
        db.execSQL(deleteSQL);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
