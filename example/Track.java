package com.example;

//Atributos: id, nome, duração
//Métodos abstratos: tocar(), exibirInfo()
//POO: Classe abstrata (não é instanciada diretamente)
//Herança/Polimorfismo:
// criar subclasses FaixaNormal e FaixaFavorita
// que implementam métodos abstratos de formas diferentes
public abstract class Track {
    private Long id;
    private String name;
    private int duration;

    public Track() {
    }

    public Track(Long id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public abstract void play();

    public abstract void displayInfo();

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
