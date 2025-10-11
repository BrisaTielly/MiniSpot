package com.example;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Artista {
    private long id;
    private String nome;
    private List<Album> listaAlbuns;

    public void exibirAlbuns(){}
    public void exibirTopFaixas(){}
}
