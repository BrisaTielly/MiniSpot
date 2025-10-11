package com.example.model;

import java.time.Year;
import java.util.List;

public class Album {
    private long id;
    private String nome;
    private Artista artista;
    private Year ano;
    private List<Faixa> listaFaixas;

    public Album() {
    }

    public Album(long id, String nome, Artista artista, Year ano, List<Faixa> listaFaixas) {
        this.id = id;
        this.nome = nome;
        this.artista = artista;
        this.ano = ano;
        this.listaFaixas = listaFaixas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Year getAno() {
        return ano;
    }

    public void setAno(Year ano) {
        this.ano = ano;
    }

    public List<Faixa> getListaFaixas() {
        return listaFaixas;
    }

    public void setListaFaixas(List<Faixa> listaFaixas) {
        this.listaFaixas = listaFaixas;
    }

    public void exibirFaixas(){}
}

