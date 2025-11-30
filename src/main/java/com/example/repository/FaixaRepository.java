package com.example.repository;

import com.example.model.Album;
import com.example.model.Artista;
import com.example.model.Faixa;
import com.example.model.Musica;
import com.example.model.Podcast;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FaixaRepository {
    private List<Faixa> faixas;
    private long proximoId;

    public FaixaRepository() {
        this.faixas = new ArrayList<>();
        this.proximoId = 1;
    }

    public List<Faixa> listarTodas() {
        return faixas;
    }

    public Faixa buscarPorId(long id) {
        for (Faixa faixa : faixas) {
            if (faixa.getId() == id) {
                return faixa;
            }
        }
        return null;
    }

    public Faixa adicionarMusica(String nome, Duration duracao, Artista artista, Album album) {
        Musica nova = new Musica();
        nova.setId(proximoId++);
        nova.setNome(nome);
        nova.setDuracao(duracao);
        nova.setArtista(artista);
        nova.setAlbum(album);
        faixas.add(nova);
        return nova;
    }

    public Faixa adicionarPodcast(String nome, Duration duracao, Artista artista) {
        return adicionarPodcast(nome, duracao, artista, null);
    }

    public Faixa adicionarPodcast(String nome, Duration duracao, Artista artista, String apresentador) {
        Podcast nova = new Podcast();
        nova.setId(proximoId++);
        nova.setNome(nome);
        nova.setDuracao(duracao);
        nova.setArtista(artista);
        if (apresentador != null && !apresentador.isBlank()) {
            nova.setApresentador(apresentador);
        }
        faixas.add(nova);
        return nova;
    }

    public void atualizarNome(long id, String novoNome) {
        Faixa faixa = buscarPorId(id);
        if (faixa != null) {
            faixa.setNome(novoNome);
        }
    }

    public void atualizarDuracao(long id, Duration novaDuracao) {
        Faixa faixa = buscarPorId(id);
        if (faixa != null) {
            faixa.setDuracao(novaDuracao);
        }
    }

    public void excluir(long id) {
        Faixa faixa = buscarPorId(id);
        if (faixa != null) {
            faixas.remove(faixa);
        }
    }

    public void excluirPorAlbum(Album album) {
        faixas.removeIf(f -> f.getAlbum() != null && f.getAlbum().getId() == album.getId());
    }

    public void excluirPorArtista(Artista artista) {
        faixas.removeIf(f -> f.getArtista() != null && f.getArtista().getId() == artista.getId());
    }

    public int contar() {
        return faixas.size();
    }
}
