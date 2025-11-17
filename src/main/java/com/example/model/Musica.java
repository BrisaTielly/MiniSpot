package com.example.model;

public class Musica extends Faixa {
    public Musica() {
        super();
    }

    @Override
    public void tocar() {
        System.out.println("Tocando música: " + getNome());
    }

    @Override
    public void exibirInfo() {
        System.out.println("Música: " + getNome() + " | Duração: " + getDuracao());
    }
}

