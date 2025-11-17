package com.example.ui.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import com.example.model.*;
import com.example.repository.*;

public class MainController {
    private BorderPane root;
    private TabPane tabPane;
    private ArtistaRepository artistaRepository;
    private AlbumRepository albumRepository;
    private FaixaRepository faixaRepository;
    private PlaylistRepository playlistRepository;

    private ArtistasController artistasController;
    private FaixasController faixasController;
    private PlaylistsController playlistsController;
    private AlbunsController albunsController;

    public MainController() {
        this.albumRepository = new AlbumRepository();
        this.artistaRepository = new ArtistaRepository(albumRepository);
        this.faixaRepository = new FaixaRepository();
        this.playlistRepository = new PlaylistRepository();
        
        this.root = new BorderPane();
        popularDadosIniciais();
        initUI();
        // Força atualização das listas após a criação completa
        atualizarTodasAsListas();
    }

    private void initUI() {
        // Header
        root.setTop(createHeader());
        
        // TabPane com abas
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-font-size: 12px;");
        
        // Criar controllers e armazenar referências
        artistasController = new ArtistasController(artistaRepository, this);
        albunsController = new AlbunsController(albumRepository, artistaRepository);
        faixasController = new FaixasController(faixaRepository, albumRepository, playlistRepository);
        playlistsController = new PlaylistsController(playlistRepository, faixaRepository);

        // Abas
        Tab abArtistas = new Tab("👤 Artistas", artistasController.getView());
        Tab abAlbuns = new Tab("💿 Álbuns", albunsController.getView());
        Tab abFaixas = new Tab("🎵 Faixas", faixasController.getView());
        Tab abPlaylists = new Tab("📃 Playlists", playlistsController.getView());

        tabPane.getTabs().addAll(abArtistas, abAlbuns, abFaixas, abPlaylists);
        root.setCenter(tabPane);
        
        // Footer
        root.setBottom(createFooter());
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setStyle("-fx-background-color: linear-gradient(to right, #1db954, #191414); -fx-padding: 20px;");
        header.setPadding(new Insets(20));
        header.setSpacing(10);
        
        Label titulo = new Label("🎵 MiniSpot - Seu Spotify Favorito");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label descricao = new Label("Gerencie suas músicas, artistas, álbuns e playlists");
        descricao.setStyle("-fx-font-size: 12px; -fx-text-fill: #b3b3b3;");
        
        header.getChildren().addAll(titulo, descricao);
        return header;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setStyle("-fx-background-color: #191414; -fx-padding: 10px;");
        footer.setPadding(new Insets(10));
        footer.setAlignment(Pos.CENTER_LEFT);
        
        Label status = new Label("✅ Status: " + getTotalItems() + " itens carregados");
        status.setStyle("-fx-text-fill: #1db954; -fx-font-size: 11px;");
        
        footer.getChildren().add(status);
        return footer;
    }

    private void popularDadosIniciais() {
        // Verifica se já existem dados
        if (artistaRepository.contar() > 0) return;
        
        // Criar artistas
        Artista artista1 = artistaRepository.adicionar("Fernanda Paula");
        Artista artista2 = artistaRepository.adicionar("João Silva");
        Artista artista3 = artistaRepository.adicionar("Maria Santos");

        // Criar faixas (Música e Podcast)
        Faixa faixa1 = faixaRepository.adicionarMusica("Canção da Manhã", java.time.Duration.ofMinutes(3).plusSeconds(45));
        Faixa faixa2 = faixaRepository.adicionarPodcast("Noite Estrelada", java.time.Duration.ofMinutes(4).plusSeconds(20));
        Faixa faixa3 = faixaRepository.adicionarMusica("Vento do Sul", java.time.Duration.ofMinutes(3).plusSeconds(15));
        Faixa faixa4 = faixaRepository.adicionarPodcast("Melodia do Coração", java.time.Duration.ofMinutes(5).plusSeconds(10));

        // Criar álbuns
        Album album1 = albumRepository.adicionar("Amanhecer", artista1, java.time.Year.of(2023));
        albumRepository.adicionarFaixaNoAlbum(album1.getId(), faixa1);
        albumRepository.adicionarFaixaNoAlbum(album1.getId(), faixa2);

        Album album2 = albumRepository.adicionar("Horizontes", artista2, java.time.Year.of(2024));
        albumRepository.adicionarFaixaNoAlbum(album2.getId(), faixa3);
        albumRepository.adicionarFaixaNoAlbum(album2.getId(), faixa4);

        // Criar playlist
        Playlist playlist1 = playlistRepository.adicionar("Minhas Favoritas", true);
        playlistRepository.adicionarFaixa(playlist1.getId(), faixa2);
        playlistRepository.adicionarFaixa(playlist1.getId(), faixa4);
    }

    private String getTotalItems() {
        int total = artistaRepository.contar() + albumRepository.contar() + 
                    faixaRepository.contar() + playlistRepository.contar();
        return String.valueOf(total);
    }

    private void atualizarTodasAsListas() {
        if (artistasController != null) {
            artistasController.atualizarLista();
        }
        if (albunsController != null) {
            albunsController.atualizarLista();
        }
        if (faixasController != null) {
            faixasController.atualizarLista();
        }
        if (playlistsController != null) {
            playlistsController.atualizarLista();
        }
    }

    public void atualizarComboArtistas() {
        if (albunsController != null) {
            albunsController.atualizarComboArtistas();
        }
    }

    public void atualizarListaDeAlbuns() {
        if (albunsController != null) {
            albunsController.atualizarLista();
        }
    }

    public BorderPane getRoot() {
        return root;
    }
}
