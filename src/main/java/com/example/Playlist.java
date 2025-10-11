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
public class Playlist implements IPlaylistManipulavel{
    private long id;
    private String nome;
    private List<Faixa> listaFaixas;
    private Boolean isPublic;

    public void adicionarFaixa(){}
    public void removerFaixa(){}
    public void mostrarPlaylist(){}
    public void reordenar(){}
}
