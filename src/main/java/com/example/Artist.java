package com.example;

import java.util.List;

//Atributos: id, nome, listaAlbuns
//Métodos: exibirAlbuns(), exibirTopFaixas()
//CRUD: criar, listar, atualizar nome, excluir
//POO: Composição (contém lista de álbuns)
public class Artist {
    private Long id;
    private String name;
    private List<Album> albumList;

    public Artist(){

    }

    public Artist(Long id, String name, List<Album> albumList, List<Track> tracks) {
        this.id = id;
        this.name = name;
        this.albumList = albumList;
        this.tracks = tracks;
    }

    //Exibir albuns
    public List<Album> displayAlbums(){
        return this.albumList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }


}
