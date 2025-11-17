package com.example.ui.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.model.Playlist;
import com.example.model.Faixa;
import com.example.model.Podcast;
import com.example.repository.PlaylistRepository;
import com.example.repository.FaixaRepository;
import java.time.Duration;

public class PlaylistsController {
    private VBox view;
    private PlaylistRepository playlistRepository;
    private FaixaRepository faixaRepository;
    private ListView<Playlist> playlistListView;
    private ListView<Faixa> faixasPlaylistListView;
    private TextField nomeField;
    private CheckBox publicaCheckBox;
    private Playlist playlistSelecionada;

    public PlaylistsController(PlaylistRepository playlistRepository, FaixaRepository faixaRepository) {
        this.playlistRepository = playlistRepository;
        this.faixaRepository = faixaRepository;
        this.view = new VBox();
        this.playlistListView = new ListView<>();
        this.faixasPlaylistListView = new ListView<>();
        initUI();
        atualizarLista();
    }

    private void initUI() {
        view.setSpacing(15);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #f5f5f5;");

        Label titulo = new Label("Gerenciar Playlists");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox inputPanel = criarInputPanel();

        // Painel com duas colunas (playlists e faixas)
        HBox contentPanel = new HBox();
        contentPanel.setSpacing(20);

        VBox playlistPanel = criarPlaylistPanel();
        VBox faixasPanel = criarFaixasPanel();

        contentPanel.getChildren().addAll(playlistPanel, faixasPanel);
        HBox.setHgrow(playlistPanel, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(faixasPanel, javafx.scene.layout.Priority.ALWAYS);

        view.getChildren().addAll(titulo, inputPanel, contentPanel);
    }

    private HBox criarInputPanel() {
        HBox panel = new HBox();
        panel.setSpacing(10);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        panel.setAlignment(Pos.CENTER_LEFT);

        Label labelNome = new Label("Nome:");
        nomeField = new TextField();
        nomeField.setPromptText("Nome da playlist...");
        nomeField.setPrefWidth(250);

        publicaCheckBox = new CheckBox("🌍 Pública");

        Button btnAdicionar = new Button("➕ Adicionar Playlist");
        btnAdicionar.setStyle("-fx-padding: 8px 20px; -fx-background-color: #1db954; -fx-text-fill: white;");
        btnAdicionar.setOnAction(e -> adicionarPlaylist());

        panel.getChildren().addAll(labelNome, nomeField, publicaCheckBox, btnAdicionar);
        return panel;
    }

    private VBox criarPlaylistPanel() {
        VBox panel = new VBox();
        panel.setSpacing(10);
        panel.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 10;");

        Label titulo = new Label("📃 Playlists");
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        playlistListView.setPrefHeight(300);
        playlistListView.setCellFactory(param -> new PlaylistCellFactory());
        playlistListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            playlistSelecionada = newVal;
            atualizarFaixasPlaylist();
        });

        HBox botoesPlaylist = new HBox();
        botoesPlaylist.setSpacing(10);
        botoesPlaylist.setAlignment(Pos.CENTER);

        Button btnEditarPlaylist = new Button("✏️ Editar");
        btnEditarPlaylist.setStyle("-fx-padding: 8px 15px; -fx-background-color: #4a90e2; -fx-text-fill: white;");
        btnEditarPlaylist.setOnAction(e -> editarPlaylist());

        Button btnExcluirPlaylist = new Button("🗑️ Excluir");
        btnExcluirPlaylist.setStyle("-fx-padding: 8px 15px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnExcluirPlaylist.setOnAction(e -> excluirPlaylist());

        botoesPlaylist.getChildren().addAll(btnEditarPlaylist, btnExcluirPlaylist);
        panel.getChildren().addAll(titulo, playlistListView, botoesPlaylist);
        
        return panel;
    }

    private VBox criarFaixasPanel() {
        VBox panel = new VBox();
        panel.setSpacing(10);
        panel.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 10;");

        Label titulo = new Label("🎵 Faixas da Playlist");
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        faixasPlaylistListView.setPrefHeight(200);
        faixasPlaylistListView.setCellFactory(param -> new FaixaPlaylistCellFactory());

        HBox panelAdicionar = new HBox();
        panelAdicionar.setSpacing(10);
        panelAdicionar.setAlignment(Pos.CENTER_LEFT);

        ComboBox<Faixa> faixaCombo = new ComboBox<>();
        faixaCombo.setPrefWidth(200);
        atualizarComboFaixas(faixaCombo);

        Button btnAdicionarFaixa = new Button("➕ Adicionar Faixa");
        btnAdicionarFaixa.setStyle("-fx-padding: 8px 15px; -fx-background-color: #1db954; -fx-text-fill: white;");
        btnAdicionarFaixa.setOnAction(e -> adicionarFaixaPlaylist(faixaCombo));

        Button btnRemoverFaixa = new Button("🗑️ Remover");
        btnRemoverFaixa.setStyle("-fx-padding: 8px 15px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnRemoverFaixa.setOnAction(e -> removerFaixaPlaylist());

        panelAdicionar.getChildren().addAll(faixaCombo, btnAdicionarFaixa, btnRemoverFaixa);

        Label duracaoLabel = new Label("⏱️ Duração total: 0:00");
        duracaoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");

        panel.getChildren().addAll(titulo, faixasPlaylistListView, panelAdicionar, duracaoLabel);

        // Atualizar duração total quando a playlist é selecionada
        playlistListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Duration total = Duration.ZERO;
                for (Faixa f : newVal.getListaFaixas()) {
                    total = total.plus(f.getDuracao());
                }
                duracaoLabel.setText("⏱️ Duração total: " + formatarDuracao(total));
            }
        });

        return panel;
    }

    private void adicionarPlaylist() {
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            mostrarAlerta("Campo vazio", "Digite o nome da playlist.");
            return;
        }

        playlistRepository.adicionar(nome, publicaCheckBox.isSelected());
        nomeField.clear();
        publicaCheckBox.setSelected(false);
        atualizarLista();
        mostrarSucesso("Playlist criada!");
    }

    private void editarPlaylist() {
        if (playlistSelecionada == null) {
            mostrarAlerta("Nenhuma selecionada", "Selecione uma playlist.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(playlistSelecionada.getNome());
        dialog.setTitle("Editar Playlist");
        dialog.setHeaderText("Novo nome:");
        dialog.showAndWait().ifPresent(novoNome -> {
            playlistRepository.atualizarNome(playlistSelecionada.getId(), novoNome);
            atualizarLista();
            mostrarSucesso("Playlist atualizada!");
        });
    }

    private void excluirPlaylist() {
        if (playlistSelecionada == null) {
            mostrarAlerta("Nenhuma selecionada", "Selecione uma playlist.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("Excluir '" + playlistSelecionada.getNome() + "'?");
        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                playlistRepository.excluir(playlistSelecionada.getId());
                playlistSelecionada = null;
                atualizarLista();
                mostrarSucesso("Playlist excluída!");
            }
        });
    }

    private void adicionarFaixaPlaylist(ComboBox<Faixa> faixaCombo) {
        if (playlistSelecionada == null) {
            mostrarAlerta("Nenhuma playlist", "Selecione uma playlist.");
            return;
        }

        Faixa selecionada = faixaCombo.getValue();
        if (selecionada == null) {
            mostrarAlerta("Nenhuma faixa", "Selecione uma faixa.");
            return;
        }

        playlistRepository.adicionarFaixa(playlistSelecionada.getId(), selecionada);
        atualizarFaixasPlaylist();
        faixaCombo.setValue(null);
        mostrarSucesso("Faixa adicionada!");
    }

    private void removerFaixaPlaylist() {
        if (playlistSelecionada == null) {
            mostrarAlerta("Nenhuma playlist", "Selecione uma playlist.");
            return;
        }

        Faixa selecionada = faixasPlaylistListView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Nenhuma faixa", "Selecione uma faixa para remover.");
            return;
        }

        playlistRepository.removerFaixa(playlistSelecionada.getId(), selecionada);
        atualizarFaixasPlaylist();
        mostrarSucesso("Faixa removida!");
    }

    public void atualizarLista() {
        ObservableList<Playlist> playlists = FXCollections.observableArrayList(playlistRepository.listarTodas());
        playlistListView.setItems(playlists);
    }

    private void atualizarFaixasPlaylist() {
        if (playlistSelecionada != null) {
            ObservableList<Faixa> faixas = FXCollections.observableArrayList(playlistSelecionada.getListaFaixas());
            faixasPlaylistListView.setItems(faixas);
        }
    }

    private void atualizarComboFaixas(ComboBox<Faixa> combo) {
        ObservableList<Faixa> faixas = FXCollections.observableArrayList(faixaRepository.listarTodas());
        combo.setItems(faixas);
        combo.setCellFactory(param -> new FaixaComboBoxCell());
        combo.setButtonCell(new FaixaComboBoxCell());
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

    private static class PlaylistCellFactory extends ListCell<Playlist> {
        @Override
        protected void updateItem(Playlist item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cellBox = new HBox();
                cellBox.setSpacing(10);
                cellBox.setPadding(new Insets(10));
                cellBox.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0;");

                String icone = item.getIsPublic() ? "🌍" : "🔒";
                Label nomeLabel = new Label(icone + " " + item.getNome());
                nomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label faixasLabel = new Label(item.getListaFaixas().size() + " faixa(s)");
                faixasLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #999;");

                VBox textBox = new VBox(nomeLabel, faixasLabel);
                cellBox.getChildren().add(textBox);
                setGraphic(cellBox);
            }
        }
    }

    private static class FaixaPlaylistCellFactory extends ListCell<Faixa> {
        @Override
        protected void updateItem(Faixa item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cellBox = new HBox();
                cellBox.setSpacing(10);
                cellBox.setPadding(new Insets(8));
                cellBox.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0;");

                String icone = item instanceof Podcast ? "🎙" : "♪";
                Label nomeLabel = new Label(icone + " " + item.getNome());
                nomeLabel.setStyle("-fx-font-size: 12px;");

                cellBox.getChildren().add(nomeLabel);
                setGraphic(cellBox);
            }
        }
    }

    private static class FaixaComboBoxCell extends ListCell<Faixa> {
        @Override
        protected void updateItem(Faixa item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                String icone = item instanceof Podcast ? "🎙" : "♪";
                setText(icone + " " + item.getNome());
            }
        }
    }
}
