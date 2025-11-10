package com.example.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.ui.controller.MainController;

public class MainApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        MainController controller = new MainController();
        Scene scene = new Scene(controller.getRoot(), 1200, 700);
        
        // Aplicar CSS (se existir)
        String css = getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        primaryStage.setTitle("🎵 MiniSpot - Seu Spotify Favorito");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
