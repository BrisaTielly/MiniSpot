package com.example.repository;

import com.example.model.Artista;
import java.util.ArrayList;
import java.util.List;

public class ArtistaRepository {
    private List<Artista> artistas;
    private long proximoId;
    private AlbumRepository albumRepository;

    private FaixaRepository faixaRepository;

    public ArtistaRepository(AlbumRepository albumRepository, FaixaRepository faixaRepository) {
        this.artistas = new ArrayList<>();
        this.proximoId = 1;
        this.albumRepository = albumRepository;
        this.faixaRepository = faixaRepository;
    }

    public List<Artista> listarTodos() {
        return artistas;
    }

    public Artista buscarPorId(long id) {
        for (Artista artista : artistas) {
            if (artista.getId() == id) {
                return artista;
            }
        }
        return null;
    }

    public Artista adicionar(String nome) {
        Artista novoArtista = new Artista(proximoId++, nome, new ArrayList<>());
        artistas.add(novoArtista);
        return novoArtista;
    }

    public void atualizar(long id, String novoNome) {
        Artista artista = buscarPorId(id);
        if (artista != null) {
            artista.setNome(novoNome);
        }
    }

    public void excluir(long id) {
        Artista artista = buscarPorId(id);
        if (artista != null) {
            // Excluir faixas associadas ao artista (cascata)
            faixaRepository.excluirPorArtista(artista);

            albumRepository.excluirAlbunsPorArtista(id);
            artistas.remove(artista);
        }
    }

    public int contar() {
        return artistas.size();
    }
}

