package com.example.model;

import java.util.List;

public class Playlist implements IPlaylistManipulavel{
    private long id;
    private String nome;
    private List<Faixa> listaFaixas;
    private Boolean isPublic;

    public Playlist() {
    }

    public Playlist(long id, String nome, List<Faixa> listaFaixas, Boolean isPublic) {
        this.id = id;
        this.nome = nome;
        this.listaFaixas = listaFaixas;
        this.isPublic = isPublic;
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

    public List<Faixa> getListaFaixas() {
        return listaFaixas;
    }

    public void setListaFaixas(List<Faixa> listaFaixas) {
        this.listaFaixas = listaFaixas;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void adicionarFaixa(){}
    public void removerFaixa(){}
    public void mostrarPlaylist(){}
    public void reordenar(){}
}

