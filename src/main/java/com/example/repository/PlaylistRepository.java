package com.example.repository;

import com.example.model.Playlist;
import com.example.model.Faixa;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepository {
    private List<Playlist> playlists;
    private long proximoId;

    public PlaylistRepository() {
        this.playlists = new ArrayList<>();
        this.proximoId = 1;
    }

    public List<Playlist> listarTodas() {
        return playlists;
    }

    public Playlist buscarPorId(long id) {
        for (Playlist playlist : playlists) {
            if (playlist.getId() == id) {
                return playlist;
            }
        }
        return null;
    }

    public Playlist adicionar(String nome, boolean isPublica) {
        Playlist novaPlaylist = new Playlist(proximoId++, nome, new ArrayList<>(), isPublica);
        playlists.add(novaPlaylist);
        return novaPlaylist;
    }

    public void atualizarNome(long id, String novoNome) {
        Playlist playlist = buscarPorId(id);
        if (playlist != null) {
            playlist.setNome(novoNome);
        }
    }

    public void atualizarVisibilidade(long id, boolean isPublica) {
        Playlist playlist = buscarPorId(id);
        if (playlist != null) {
            playlist.setIsPublic(isPublica);
        }
    }

    public void excluir(long id) {
        Playlist playlist = buscarPorId(id);
        if (playlists != null) {
            playlists.remove(playlist);
        }
    }

    public void adicionarFaixa(long idPlaylist, Faixa faixa) {
        Playlist playlist = buscarPorId(idPlaylist);
        if (playlist != null) {
            playlist.getListaFaixas().add(faixa);
        }
    }

    public void removerFaixa(long idPlaylist, Faixa faixa) {
        Playlist playlist = buscarPorId(idPlaylist);
        if (playlist != null) {
            playlist.getListaFaixas().remove(faixa);
        }
    }

    public void removerFaixaDeTodasPlaylists(Faixa faixa) {
        for (Playlist playlist : playlists) {
            playlist.getListaFaixas().remove(faixa);
        }
    }

    public int contar() {
        return playlists.size();
    }
}

