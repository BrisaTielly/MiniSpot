package com.example.repository;

import com.example.model.Album;
import com.example.model.Artista;
import com.example.model.Faixa;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepository {
    private List<Album> albuns;
    private long proximoId;

    public AlbumRepository() {
        this.albuns = new ArrayList<>();
        this.proximoId = 1;
    }

    public List<Album> listarTodos() {
        return albuns;
    }

    public Album buscarPorId(long id) {
        for (Album album : albuns) {
            if (album.getId() == id) {
                return album;
            }
        }
        return null;
    }

    public Album adicionar(String nome, Artista artista, Year ano) {
        Album novoAlbum = new Album(proximoId++, nome, artista, ano, new ArrayList<>());
        albuns.add(novoAlbum);
        if (artista != null) {
            artista.getListaAlbuns().add(novoAlbum);
        }
        return novoAlbum;
    }

    public void atualizarNome(long id, String novoNome) {
        Album album = buscarPorId(id);
        if (album != null) {
            album.setNome(novoNome);
        }
    }

    public void atualizarAno(long id, Year novoAno) {
        Album album = buscarPorId(id);
        if (album != null) {
            album.setAno(novoAno);
        }
    }

    public void excluir(long id) {
        Album album = buscarPorId(id);
        if (album != null) {
            if (album.getArtista() != null) {
                album.getArtista().getListaAlbuns().remove(album);
            }
            albuns.remove(album);
        }
    }

    public void excluirPorArtista(Artista artista) {
        if (artista.getListaAlbuns() != null) {
            albuns.removeAll(artista.getListaAlbuns());
        }
    }

    public void adicionarFaixaNoAlbum(long idAlbum, Faixa faixa) {
        Album album = buscarPorId(idAlbum);
        if (album != null) {
            album.getListaFaixas().add(faixa);
        }
    }

    public void removerFaixaDosTodosAlbuns(Faixa faixa) {
        for (Album album : albuns) {
            album.getListaFaixas().remove(faixa);
        }
    }

    public int contar() {
        return albuns.size();
    }
}

