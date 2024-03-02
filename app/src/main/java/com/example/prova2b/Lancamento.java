package com.example.prova2b;

import java.util.Date;

public class Lancamento {

    private int idLancamento;
    private String nomeUsuario;
    private String valor;
    private String data;
    private String tipo;

    public Lancamento() {
    }

    public Lancamento(int idLancamento, String nomeUsuario, String valor, String data, String tipo) {
        this.idLancamento = idLancamento;
        this.nomeUsuario = nomeUsuario;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    public Lancamento(String nomeUsuario, String valor, String data, String tipo) {
        this.nomeUsuario = nomeUsuario;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(int idLancamento) {
        this.idLancamento = idLancamento;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDados() {
        String dados = getIdLancamento() + " | " + getNomeUsuario() + " | " + getData() + " | " + getValor() + " | " + getTipo();
        return dados;
    }
}
