package com.example.ui.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.model.Artista;
import com.example.repository.ArtistaRepository;

public class ArtistasController {
    private VBox view;
    private ArtistaRepository repository;
    private ListView<Artista> listView;
    private TextField nomeField;
    private MainController mainController;

    public ArtistasController(ArtistaRepository repository, MainController mainController) {
        this.repository = repository;
        this.mainController = mainController;
        this.view = new VBox();
        this.listView = new ListView<>();
        initUI();
        atualizarLista();
    }

    private void initUI() {
        view.setSpacing(15);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #f5f5f5;");

        // Titulo
        Label titulo = new Label("Gerenciar Artistas");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Painel de entrada
        HBox inputPanel = criarInputPanel();

        // ListView
        listView.setPrefHeight(350);
        listView.setCellFactory(param -> new ArtistaCellFactory());
        
        // Botões de ação
        HBox botoesPanel = criarBotoesPanel();

        view.getChildren().addAll(titulo, inputPanel, listView, botoesPanel);
    }

    private HBox criarInputPanel() {
        HBox panel = new HBox();
        panel.setSpacing(10);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        panel.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label("Nome do Artista:");
        nomeField = new TextField();
        nomeField.setPromptText("Digite o nome...");
        nomeField.setPrefWidth(300);
        nomeField.setStyle("-fx-padding: 8px; -fx-border-radius: 4;");

        Button btnAdicionar = new Button("➕ Adicionar");
        btnAdicionar.setStyle("-fx-padding: 8px 20px; -fx-font-size: 12px; -fx-background-color: #1db954; " +
                            "-fx-text-fill: white; -fx-border-radius: 4; -fx-cursor: hand;");
        btnAdicionar.setOnAction(e -> adicionarArtista());

        panel.getChildren().addAll(label, nomeField, btnAdicionar);
        return panel;
    }

    private HBox criarBotoesPanel() {
        HBox panel = new HBox();
        panel.setSpacing(10);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(10));

        Button btnEditar = new Button("✏️ Editar");
        btnEditar.setStyle("-fx-padding: 10px 20px; -fx-background-color: #4a90e2; -fx-text-fill: white; -fx-border-radius: 4;");
        btnEditar.setOnAction(e -> editarArtista());

        Button btnExcluir = new Button("🗑️ Excluir");
        btnExcluir.setStyle("-fx-padding: 10px 20px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-border-radius: 4;");
        btnExcluir.setOnAction(e -> excluirArtista());

        Button btnDetalhes = new Button("👁️ Detalhes");
        btnDetalhes.setStyle("-fx-padding: 10px 20px; -fx-background-color: #9b59b6; -fx-text-fill: white; -fx-border-radius: 4;");
        btnDetalhes.setOnAction(e -> verDetalhes());

        panel.getChildren().addAll(btnEditar, btnExcluir, btnDetalhes);
        return panel;
    }

    private void adicionarArtista() {
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            mostrarAlerta("Campo vazio", "Por favor, digite o nome do artista.");
            return;
        }
        
        repository.adicionar(nome);
        nomeField.clear();
        atualizarLista();
        mainController.atualizarComboArtistas();
        mostrarSucesso("Artista adicionado com sucesso!");
    }

    private void editarArtista() {
        Artista selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Nenhum selecionado", "Selecione um artista para editar.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selecionado.getNome());
        dialog.setTitle("Editar Artista");
        dialog.setHeaderText("Digite o novo nome do artista:");
        dialog.showAndWait().ifPresent(novoNome -> {
            repository.atualizar(selecionado.getId(), novoNome);
            atualizarLista();
            mainController.atualizarComboArtistas();
            mainController.atualizarListaDeAlbuns();
            mostrarSucesso("Artista atualizado!");
        });
    }

    private void excluirArtista() {
        Artista selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Nenhum selecionado", "Selecione um artista para excluir.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar exclusão");
        alert.setHeaderText("Tem certeza?");
        alert.setContentText("Deseja excluir o artista '" + selecionado.getNome() + "'?");
        
        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                repository.excluir(selecionado.getId());
                atualizarLista();
                mainController.atualizarComboArtistas();
                mainController.atualizarListaDeAlbuns();
                mostrarSucesso("Artista excluído!");
            }
        });
    }

    private void verDetalhes() {
        Artista selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Nenhum selecionado", "Selecione um artista.");
            return;
        }

        StringBuilder detalhes = new StringBuilder();
        detalhes.append("ID: ").append(selecionado.getId()).append("\n");
        detalhes.append("Nome: ").append(selecionado.getNome()).append("\n");
        detalhes.append("Álbuns: ").append(selecionado.getListaAlbuns() != null ? 
                       selecionado.getListaAlbuns().size() : 0);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Artista");
        alert.setHeaderText("👤 " + selecionado.getNome());
        alert.setContentText(detalhes.toString());
        alert.showAndWait();
    }

    public void atualizarLista() {
        ObservableList<Artista> items = FXCollections.observableArrayList(repository.listarTodos());
        listView.setItems(items);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("✅ " + mensagem);
        alert.showAndWait();
    }

    public VBox getView() {
        return view;
    }

    // Custom cell factory para exibir artistas com estilo
    private static class ArtistaCellFactory extends ListCell<Artista> {
        @Override
        protected void updateItem(Artista item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cellBox = new HBox();
                cellBox.setSpacing(10);
                cellBox.setPadding(new Insets(10));
                cellBox.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0;");

                Label nomeLabel = new Label("👤 " + item.getNome());
                nomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label idLabel = new Label("ID: " + item.getId());
                idLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #999;");

                VBox textBox = new VBox(nomeLabel, idLabel);
                cellBox.getChildren().add(textBox);
                setGraphic(cellBox);
            }
        }
    }
}
