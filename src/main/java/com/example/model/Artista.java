package com.example.model;

import java.util.List;

public class Artista {
    private long id;
    private String nome;
    private List<Album> listaAlbuns;

    public Artista() {
    }

    public Artista(long id, String nome, List<Album> listaAlbuns) {
        this.id = id;
        this.nome = nome;
        this.listaAlbuns = listaAlbuns;
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

    public List<Album> getListaAlbuns() {
        return listaAlbuns;
    }

    public void setListaAlbuns(List<Album> listaAlbuns) {
        this.listaAlbuns = listaAlbuns;
    }

    public void exibirAlbuns(){}
    public void exibirTopFaixas(){}
}

