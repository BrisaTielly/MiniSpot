package com.example;

import java.util.List;

//Atributos: id, nome, artista, ano, listaFaixas
//Métodos: exibirFaixas()
//CRUD: criar, listar, atualizar, excluir
//POO: Composição (contém lista de faixas); associação com Artista
public class Album {
    private Long id;
    private String name;
    private String artist;
    private int year;
    private List<Track> trackList;

    public Album (){

    }

    public Album(Long id, String name, String artist, int year, List<Track> trackList) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.year = year;
        this.trackList = trackList;
    }

    public List<Track> displayTracks(){
        return this.trackList;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }
}
