package com.example.ui.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.model.Album;
import com.example.model.Artista;
import com.example.repository.AlbumRepository;
import com.example.repository.ArtistaRepository;
import java.time.Year;
import javafx.util.StringConverter;

public class AlbunsController {
    private VBox view;
    private AlbumRepository albumRepository;
    private ArtistaRepository artistaRepository;
    private ListView<Album> listView;
    private TextField nomeField;
    private ComboBox<Artista> artistaCombo;

    public AlbunsController(AlbumRepository albumRepository, ArtistaRepository artistaRepository) {
        this.albumRepository = albumRepository;
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

        Label titulo = new Label("Gerenciar Álbuns");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox inputPanel = criarInputPanel();

        listView.setPrefHeight(350);
        listView.setCellFactory(param -> new AlbumCellFactory());
        
        HBox botoesPanel = criarBotoesPanel();

        view.getChildren().addAll(titulo, inputPanel, listView, botoesPanel);
    }

    private HBox criarInputPanel() {
        HBox panel = new HBox();
        panel.setSpacing(10);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        panel.setAlignment(Pos.CENTER_LEFT);

        Label labelNome = new Label("Nome:");
        nomeField = new TextField();
        nomeField.setPromptText("Nome do álbum...");
        nomeField.setPrefWidth(200);

        Label labelArtista = new Label("Artista:");
        artistaCombo = new ComboBox<>();
        artistaCombo.setPrefWidth(200);
        atualizarComboArtistas();

        artistaCombo.setConverter(new StringConverter<Artista>() {
            @Override
            public String toString(Artista object) {
                return object == null ? "Selecione" : object.getNome();
            }

            @Override
            public Artista fromString(String string) {
                return null;
            }
        });

        Button btnAdicionar = new Button("➕ Adicionar");
        btnAdicionar.setStyle("-fx-padding: 8px 20px; -fx-background-color: #1db954; -fx-text-fill: white;");
        btnAdicionar.setOnAction(e -> adicionarAlbum());

        panel.getChildren().addAll(labelNome, nomeField, labelArtista, artistaCombo, btnAdicionar);
        return panel;
    }

    private HBox criarBotoesPanel() {
        HBox panel = new HBox();
        panel.setSpacing(10);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(10));

        Button btnEditar = new Button("✏️ Editar");
        btnEditar.setStyle("-fx-padding: 10px 20px; -fx-background-color: #4a90e2; -fx-text-fill: white;");
        btnEditar.setOnAction(e -> editarAlbum());

        Button btnExcluir = new Button("🗑️ Excluir");
        btnExcluir.setStyle("-fx-padding: 10px 20px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnExcluir.setOnAction(e -> excluirAlbum());

        Button btnDetalhes = new Button("👁️ Detalhes");
        btnDetalhes.setStyle("-fx-padding: 10px 20px; -fx-background-color: #9b59b6; -fx-text-fill: white;");
        btnDetalhes.setOnAction(e -> verDetalhes());

        panel.getChildren().addAll(btnEditar, btnExcluir, btnDetalhes);
        return panel;
    }

    private void adicionarAlbum() {
        String nome = nomeField.getText().trim();
        Artista artista = artistaCombo.getValue();

        if (nome.isEmpty() || artista == null) {
            mostrarAlerta("Campo vazio", "Preencha todos os campos.");
            return;
        }

        albumRepository.adicionar(nome, artista, Year.now());
        nomeField.clear();
        artistaCombo.setValue(null);
        atualizarLista();
        mostrarSucesso("Álbum adicionado!");
    }

    private void editarAlbum() {
        Album selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Nenhum selecionado", "Selecione um álbum.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selecionado.getNome());
        dialog.setTitle("Editar Álbum");
        dialog.setHeaderText("Novo nome:");
        dialog.showAndWait().ifPresent(novoNome -> {
            albumRepository.atualizarNome(selecionado.getId(), novoNome);
            atualizarLista();
            mostrarSucesso("Álbum atualizado!");
        });
    }

    private void excluirAlbum() {
        Album selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Nenhum selecionado", "Selecione um álbum.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("Excluir '" + selecionado.getNome() + "'?");
        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                albumRepository.excluir(selecionado.getId());
                atualizarLista();
                mostrarSucesso("Álbum excluído!");
            }
        });
    }

    private void verDetalhes() {
        Album selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Nenhum selecionado", "Selecione um álbum.");
            return;
        }

        StringBuilder detalhes = new StringBuilder();
        detalhes.append("Nome: ").append(selecionado.getNome()).append("\n");
        detalhes.append("Artista: ").append(selecionado.getArtista().getNome()).append("\n");
        detalhes.append("Ano: ").append(selecionado.getAno()).append("\n");
        detalhes.append("Faixas: ").append(selecionado.getListaFaixas().size());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Álbum");
        alert.setHeaderText("💿 " + selecionado.getNome());
        alert.setContentText(detalhes.toString());
        alert.showAndWait();
    }

    public void atualizarLista() {
        ObservableList<Album> items = FXCollections.observableArrayList(albumRepository.listarTodos());
        listView.setItems(items);
    }

    public void atualizarComboArtistas() {
        ObservableList<Artista> artistas = FXCollections.observableArrayList(artistaRepository.listarTodos());
        artistaCombo.setItems(artistas);
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

    private static class AlbumCellFactory extends ListCell<Album> {
        @Override
        protected void updateItem(Album item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cellBox = new HBox();
                cellBox.setSpacing(10);
                cellBox.setPadding(new Insets(10));
                cellBox.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0;");

                Label nomeLabel = new Label("💿 " + item.getNome());
                nomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label infoLabel = new Label(item.getArtista().getNome() + " • " + item.getAno());
                infoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #999;");

                VBox textBox = new VBox(nomeLabel, infoLabel);
                cellBox.getChildren().add(textBox);
                setGraphic(cellBox);
            }
        }
    }
}
