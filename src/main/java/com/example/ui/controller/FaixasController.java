package com.example.ui.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.model.Faixa;
import com.example.model.Podcast;
import com.example.repository.FaixaRepository;
import com.example.repository.AlbumRepository;
import com.example.repository.PlaylistRepository;
import java.time.Duration;

public class FaixasController {
    private VBox view;
    private FaixaRepository faixaRepository;
    private AlbumRepository albumRepository;
    private PlaylistRepository playlistRepository;
    private ListView<Faixa> listView;
    private TextField nomeField;
    private Spinner<Integer> minutosSpinner;
    private Spinner<Integer> segundosSpinner;
    private ChoiceBox<String> tipoChoiceBox;
    private TextField apresentadorField;

    public FaixasController(FaixaRepository faixaRepository, AlbumRepository albumRepository, PlaylistRepository playlistRepository) {
        this.faixaRepository = faixaRepository;
        this.albumRepository = albumRepository;
        this.playlistRepository = playlistRepository;
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

        HBox inputPanel = criarInputPanel();
        listView.setPrefHeight(300);
        listView.setCellFactory(param -> new FaixaCellFactory());
        
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
        nomeField.setPromptText("Nome da faixa...");
        nomeField.setPrefWidth(200);

        Label labelDuracao = new Label("Duração:");
        minutosSpinner = new Spinner<>(0, 59, 3);
        minutosSpinner.setPrefWidth(70);
        Label labelMin = new Label("min");
        
        segundosSpinner = new Spinner<>(0, 59, 45);
        segundosSpinner.setPrefWidth(70);
        Label labelSeg = new Label("seg");

        Label labelTipo = new Label("Tipo:");
        tipoChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Música", "Podcast"));
        tipoChoiceBox.setValue("Música");

        apresentadorField = new TextField();
        apresentadorField.setPromptText("Apresentador do podcast...");
        apresentadorField.setPrefWidth(180);
        apresentadorField.setVisible(false);

        tipoChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            apresentadorField.setVisible("Podcast".equals(newVal));
        });

        Button btnAdicionar = new Button("➕ Adicionar");
        btnAdicionar.setStyle("-fx-padding: 8px 20px; -fx-background-color: #1db954; -fx-text-fill: white;");
        btnAdicionar.setOnAction(e -> adicionarFaixa());

        panel.getChildren().addAll(labelNome, nomeField, labelDuracao, minutosSpinner, labelMin, segundosSpinner, labelSeg, labelTipo, tipoChoiceBox, apresentadorField, btnAdicionar);
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

        Faixa novaFaixa;
        if ("Podcast".equals(tipoChoiceBox.getValue())) {
            String apresentador = apresentadorField.getText().trim();
            novaFaixa = faixaRepository.adicionarPodcast(nome, duracao, apresentador.isEmpty() ? null : apresentador);
        } else {
            novaFaixa = faixaRepository.adicionarMusica(nome, duracao);
        }

        nomeField.clear();
        minutosSpinner.getValueFactory().setValue(3);
        segundosSpinner.getValueFactory().setValue(45);
        tipoChoiceBox.setValue("Música");
        apresentadorField.clear();
        apresentadorField.setVisible(false);
        atualizarLista();
        mostrarSucesso("Faixa adicionada!");
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
        detalhes.append("Duração: ").append(formatarDuracao(selecionada.getDuracao())).append("\n");
        detalhes.append("Tipo: ").append(selecionada instanceof Podcast ? "🎙 Podcast" : "♪ Música");
        if (selecionada instanceof Podcast) {
            Podcast p = (Podcast) selecionada;
            detalhes.append("\nApresentador: ").append(p.getApresentador() == null ? "(não informado)" : p.getApresentador());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes da Faixa");
        alert.setHeaderText("🎵 " + selecionada.getNome());
        alert.setContentText(detalhes.toString());
        alert.showAndWait();
    }

    public void atualizarLista() {
        ObservableList<Faixa> items = FXCollections.observableArrayList(faixaRepository.listarTodas());
        listView.setItems(items);
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
                Label nomeLabel = new Label(icone + " " + item.getNome());
                nomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label duracaoLabel = new Label(formatarDuracao(item.getDuracao()));
                duracaoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #999;");

                String apresentador = item instanceof Podcast ? ((Podcast) item).getApresentador() : null;
                if (apresentador != null && !apresentador.isBlank()) {
                    Label apresentadorLabel = new Label("👤 " + apresentador);
                    apresentadorLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #555;");
                    VBox textBox = new VBox(nomeLabel, duracaoLabel, apresentadorLabel);
                    cellBox.getChildren().add(textBox);
                } else {
                    VBox textBox = new VBox(nomeLabel, duracaoLabel);
                    cellBox.getChildren().add(textBox);
                }
                setGraphic(cellBox);
            }
        }
    }
}
