package com.example;

import java.time.Year;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    private long id;
    private String nome;
    private Artista artista;
    private Year ano;
    private List<Faixa> listaFaixas;

    public void exibirFaixas(){}
}
