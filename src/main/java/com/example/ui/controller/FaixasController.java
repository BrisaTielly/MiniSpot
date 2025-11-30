package com.example.ui.controller;

import javafx.geometry.Insets;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.model.Album;
import com.example.model.Artista;
import com.example.model.Faixa;
import com.example.model.Podcast;
import com.example.repository.FaixaRepository;
import com.example.repository.AlbumRepository;
import com.example.repository.PlaylistRepository;
import com.example.repository.ArtistaRepository;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import java.time.Duration;

public class FaixasController {
    private VBox view;
    private FaixaRepository faixaRepository;
    private AlbumRepository albumRepository;
    private PlaylistRepository playlistRepository;
    private ArtistaRepository artistaRepository;
    private ListView<Faixa> listView;
    private TextField nomeField;
    private Spinner<Integer> minutosSpinner;
    private Spinner<Integer> segundosSpinner;
    private ChoiceBox<String> tipoChoiceBox;
    private ChoiceBox<Artista> artistaChoiceBox;
    private ChoiceBox<Album> albumChoiceBox;
    private TextField apresentadorField;

    public FaixasController(FaixaRepository faixaRepository, AlbumRepository albumRepository,
            PlaylistRepository playlistRepository, ArtistaRepository artistaRepository) {
        this.faixaRepository = faixaRepository;
        this.albumRepository = albumRepository;
        this.playlistRepository = playlistRepository;
        this.artistaRepository = artistaRepository;
        this.view = new VBox();
        this.listView = new ListView<>();
        initUI();
        atualizarLista();
    }

    private void initUI() {
        view.setSpacing(15);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #f5f5f5;");

        Label titulo = new Label("Gerenciar Faixas");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox inputPanel = criarInputPanel();
        listView.setPrefHeight(300);
        listView.setCellFactory(param -> new FaixaCellFactory());
        
        HBox botoesPanel = criarBotoesPanel();

        view.getChildren().addAll(titulo, inputPanel, listView, botoesPanel);
    }

    private VBox criarInputPanel() {
        VBox panel = new VBox();
        panel.setSpacing(10);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER_LEFT);

        // Row 0: Nome, Duração, Tipo
        Label labelNome = new Label("Nome:");
        nomeField = new TextField();
        nomeField.setPromptText("Nome da faixa...");
        nomeField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(nomeField, Priority.ALWAYS);

        Label labelDuracao = new Label("Duração:");
        HBox duracaoBox = new HBox(5);
        duracaoBox.setAlignment(Pos.CENTER_LEFT);
        minutosSpinner = new Spinner<>(0, 59, 3);
        minutosSpinner.setPrefWidth(80);
        minutosSpinner.setEditable(true);
        Label labelMin = new Label("m");
        segundosSpinner = new Spinner<>(0, 59, 45);
        segundosSpinner.setPrefWidth(80);
        segundosSpinner.setEditable(true);
        Label labelSeg = new Label("s");
        duracaoBox.getChildren().addAll(minutosSpinner, labelMin, segundosSpinner, labelSeg);

        Label labelTipo = new Label("Tipo:");
        tipoChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Música", "Podcast"));
        tipoChoiceBox.setValue("Música");
        tipoChoiceBox.setPrefWidth(100);

        grid.add(labelNome, 0, 0);
        grid.add(nomeField, 1, 0);
        grid.add(labelDuracao, 2, 0);
        grid.add(duracaoBox, 3, 0);
        grid.add(labelTipo, 4, 0);
        grid.add(tipoChoiceBox, 5, 0);

        // Row 1: Artista, Álbum, Botão
        Label labelArtista = new Label("Artista:");
        artistaChoiceBox = new ChoiceBox<>();
        artistaChoiceBox.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(artistaChoiceBox, Priority.ALWAYS);
        artistaChoiceBox.setConverter(new StringConverter<Artista>() {
            @Override
            public String toString(Artista object) {
                return object == null ? "Selecione..." : object.getNome();
            }

            @Override
            public Artista fromString(String string) {
                return null;
            }
        });

        apresentadorField = new TextField();
        apresentadorField.setPromptText("Apresentador...");
        apresentadorField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(apresentadorField, Priority.ALWAYS);
        apresentadorField.setVisible(false);

        Label labelAlbum = new Label("Álbum:");
        albumChoiceBox = new ChoiceBox<>();
        albumChoiceBox.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(albumChoiceBox, Priority.ALWAYS);
        albumChoiceBox.setConverter(new StringConverter<Album>() {
            @Override
            public String toString(Album object) {
                return object == null ? "Nenhum" : object.getNome();
            }

            @Override
            public Album fromString(String string) {
                return null;
            }
        });

        Button btnAdicionar = new Button("➕ Adicionar");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAdicionar.setOnAction(e -> adicionarFaixa());

        grid.add(labelArtista, 0, 1);
        grid.add(artistaChoiceBox, 1, 1);
        grid.add(apresentadorField, 1, 1);

        grid.add(labelAlbum, 2, 1);
        grid.add(albumChoiceBox, 3, 1);

        grid.add(btnAdicionar, 4, 1, 2, 1);

        tipoChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean isPodcast = "Podcast".equals(newVal);
            apresentadorField.setVisible(isPodcast);
            labelArtista.setText(isPodcast ? "Apresentador:" : "Artista:");

            labelAlbum.setVisible(!isPodcast);
            albumChoiceBox.setVisible(!isPodcast);

            if (isPodcast) {
                if (grid.getChildren().contains(artistaChoiceBox))
                    grid.getChildren().remove(artistaChoiceBox);
                if (!grid.getChildren().contains(apresentadorField))
                    grid.add(apresentadorField, 1, 1);

                // Clear album selection for podcast
                albumChoiceBox.setValue(null);
            } else {
                if (grid.getChildren().contains(apresentadorField))
                    grid.getChildren().remove(apresentadorField);
                if (!grid.getChildren().contains(artistaChoiceBox))
                    grid.add(artistaChoiceBox, 1, 1);
                atualizarComboAlbuns();
            }
        });

        artistaChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            atualizarComboAlbuns();
        });

        grid.getChildren().remove(apresentadorField);

        panel.getChildren().add(grid);
        return panel;
    }

    private HBox criarBotoesPanel() {
        HBox panel = new HBox();
        panel.setSpacing(10);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(10));

        Button btnEditar = new Button("✏️ Editar");
        btnEditar.setStyle("-fx-padding: 10px 20px; -fx-background-color: #4a90e2; -fx-text-fill: white;");
        btnEditar.setOnAction(e -> editarFaixa());

        Button btnExcluir = new Button("🗑️ Excluir");
        btnExcluir.setStyle("-fx-padding: 10px 20px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnExcluir.setOnAction(e -> excluirFaixa());

        Button btnDetalhes = new Button("👁️ Detalhes");
        btnDetalhes.setStyle("-fx-padding: 10px 20px; -fx-background-color: #9b59b6; -fx-text-fill: white;");
        btnDetalhes.setOnAction(e -> verDetalhes());

        panel.getChildren().addAll(btnEditar, btnExcluir, btnDetalhes);
        return panel;
    }

    private void adicionarFaixa() {
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            mostrarAlerta("Campo vazio", "Digite o nome da faixa.");
            return;
        }

        int minutos = minutosSpinner.getValue();
        int segundos = segundosSpinner.getValue();
        Duration duracao = Duration.ofMinutes(minutos).plusSeconds(segundos);

        Artista artista = null;
        if (!"Podcast".equals(tipoChoiceBox.getValue())) {
            artista = artistaChoiceBox.getValue();
            if (artista == null) {
                mostrarAlerta("Artista obrigatório", "Selecione um artista para a música.");
                return;
            }
        }

        Faixa novaFaixa;
        if ("Podcast".equals(tipoChoiceBox.getValue())) {
            String apresentador = apresentadorField.getText().trim();
            novaFaixa = faixaRepository.adicionarPodcast(nome, duracao, null,
                    apresentador.isEmpty() ? null : apresentador);
        } else {
            Album album = albumChoiceBox.getValue();
            novaFaixa = faixaRepository.adicionarMusica(nome, duracao, artista, album);
        }

        // Adicionar ao álbum se selecionado
        Album album = albumChoiceBox.getValue();
        if (album != null) {
            albumRepository.adicionarFaixaNoAlbum(album.getId(), novaFaixa);
        }

        nomeField.clear();
        minutosSpinner.getValueFactory().setValue(3);
        segundosSpinner.getValueFactory().setValue(45);
        tipoChoiceBox.setValue("Música");
        apresentadorField.clear();
        // Resetar combos
        if (!artistaChoiceBox.getItems().isEmpty())
            artistaChoiceBox.getSelectionModel().selectFirst();
        if (!albumChoiceBox.getItems().isEmpty())
            albumChoiceBox.setValue(null);

        atualizarLista();
        mostrarSucesso("Faixa adicionada" + (album != null ? " e vinculada ao álbum!" : "!"));
    }

    private void editarFaixa() {
        Faixa selecionada = listView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Nenhuma selecionada", "Selecione uma faixa.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selecionada.getNome());
        dialog.setTitle("Editar Faixa");
        dialog.setHeaderText("Novo nome:");
        dialog.showAndWait().ifPresent(novoNome -> {
            faixaRepository.atualizarNome(selecionada.getId(), novoNome);
            atualizarLista();
            mostrarSucesso("Faixa atualizada!");
        });
    }

    private void excluirFaixa() {
        Faixa selecionada = listView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Nenhuma selecionada", "Selecione uma faixa.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("Excluir '" + selecionada.getNome() + "'?");
        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                albumRepository.removerFaixaDosTodosAlbuns(selecionada);
                playlistRepository.removerFaixaDeTodasPlaylists(selecionada);
                faixaRepository.excluir(selecionada.getId());
                atualizarLista();
                mostrarSucesso("Faixa excluída!");
            }
        });
    }

    private void verDetalhes() {
        Faixa selecionada = listView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Nenhuma selecionada", "Selecione uma faixa.");
            return;
        }

        StringBuilder detalhes = new StringBuilder();
        detalhes.append("ID: ").append(selecionada.getId()).append("\n");
        detalhes.append("Nome: ").append(selecionada.getNome()).append("\n");
        if (selecionada instanceof Podcast) {
            Podcast p = (Podcast) selecionada;
            detalhes.append("Apresentador: ")
                    .append(p.getApresentador() == null ? "(não informado)" : p.getApresentador()).append("\n");
        } else {
            detalhes.append("Artista: ")
                    .append(selecionada.getArtista() != null ? selecionada.getArtista().getNome() : "Desconhecido")
                    .append("\n");
            detalhes.append("Álbum: ")
                    .append(selecionada.getAlbum() != null ? selecionada.getAlbum().getNome() : "Nenhum")
                    .append("\n");
        }
        detalhes.append("Duração: ").append(formatarDuracao(selecionada.getDuracao())).append("\n");
        detalhes.append("Tipo: ").append(selecionada instanceof Podcast ? "🎙 Podcast" : "♪ Música");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes da Faixa");
        alert.setHeaderText("🎵 " + selecionada.getNome());
        alert.setContentText(detalhes.toString());
        alert.showAndWait();
    }

    public void atualizarLista() {
        ObservableList<Faixa> items = FXCollections.observableArrayList(faixaRepository.listarTodas());
        listView.setItems(items);
        atualizarComboArtistas();
        atualizarComboAlbuns();
    }

    public void atualizarComboAlbuns() {
        if (albumRepository != null && albumChoiceBox != null) {
            Artista artistaSelecionado = artistaChoiceBox.getValue();
            List<Album> albuns;

            if (artistaSelecionado != null && !"Podcast".equals(tipoChoiceBox.getValue())) {
                albuns = artistaSelecionado.getListaAlbuns();
            } else {
                // Se for podcast ou nenhum artista selecionado, mostra todos (ou vazio?)
                // Para podcast, mostra todos. Para música sem artista (não deve acontecer),
                // vazio.
                if ("Podcast".equals(tipoChoiceBox.getValue())) {
                    albuns = albumRepository.listarTodos();
                } else {
                    albuns = FXCollections.emptyObservableList();
                }
            }

            if (albuns == null)
                albuns = FXCollections.emptyObservableList();

            Album selecionado = albumChoiceBox.getValue();
            albumChoiceBox.setItems(FXCollections.observableArrayList(albuns));

            if (selecionado != null && albumChoiceBox.getItems().contains(selecionado)) {
                albumChoiceBox.setValue(selecionado);
            } else {
                albumChoiceBox.setValue(null);
            }
        }
    }

    public void atualizarComboArtistas() {
        if (artistaRepository != null && artistaChoiceBox != null) {
            Artista selecionado = artistaChoiceBox.getValue();
            artistaChoiceBox.setItems(FXCollections.observableArrayList(artistaRepository.listarTodos()));
            if (selecionado != null && artistaChoiceBox.getItems().contains(selecionado)) {
                artistaChoiceBox.setValue(selecionado);
            } else if (!artistaChoiceBox.getItems().isEmpty()) {
                artistaChoiceBox.getSelectionModel().selectFirst();
            }
        }
    }

    private String formatarDuracao(Duration duracao) {
        long minutos = duracao.toMinutes();
        long segundos = duracao.minusMinutes(minutos).getSeconds();
        return String.format("%d:%02d", minutos, segundos);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText("✅ " + mensagem);
        alert.showAndWait();
    }

    public VBox getView() {
        return view;
    }

    private class FaixaCellFactory extends ListCell<Faixa> {
        @Override
        protected void updateItem(Faixa item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cellBox = new HBox();
                cellBox.setSpacing(10);
                cellBox.setPadding(new Insets(10));
                cellBox.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0;");

                String icone = item instanceof Podcast ? "🎙" : "♪";
                String textoPrincipal;

                if (item instanceof Podcast) {
                    Podcast p = (Podcast) item;
                    String apresentador = p.getApresentador() != null ? p.getApresentador() : "Desconhecido";
                    textoPrincipal = icone + " " + item.getNome() + " - " + apresentador;
                } else {
                    String nomeArtista = item.getArtista() != null ? item.getArtista().getNome() : "Desconhecido";
                    String nomeAlbum = item.getAlbum() != null ? " • 💿 " + item.getAlbum().getNome() : "";
                    textoPrincipal = icone + " " + item.getNome() + " - " + nomeArtista + nomeAlbum;
                }

                Label nomeLabel = new Label(textoPrincipal);
                nomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label duracaoLabel = new Label(formatarDuracao(item.getDuracao()));
                duracaoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #999;");

                VBox textBox = new VBox(nomeLabel, duracaoLabel);
                cellBox.getChildren().add(textBox);
                setGraphic(cellBox);
            }
        }
    }
}
