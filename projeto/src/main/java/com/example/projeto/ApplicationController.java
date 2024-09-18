package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.util.Alertas;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Locale;

public class ApplicationController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField senha;
    @FXML
    private Button botaoLogin;
    @FXML
    private ImageView fundo;
    @FXML
    private ImageView foto;
    private static Stage stage;
    public static Stage getStage(){
        return stage;
    }

    private final Image[] imageArray = {
            new Image("file:src/main/resources/img/fotos-login/foto-1.jpg"),
            new Image("file:src/main/resources/img/fotos-login/foto-2.jpg"),
            new Image("file:src/main/resources/img/fotos-login/foto-3.jpg"),
            new Image("file:src/main/resources/img/fotos-login/foto-4.jpg"),
            new Image("file:src/main/resources/img/fotos-login/foto-5.jpg"),
            new Image("file:src/main/resources/img/fotos-login/foto-6.jpg"),
            new Image("file:src/main/resources/img/fotos-login/foto-7.jpg"),

    };
    private int indexImagemAtual = 0;

    @FXML
    public void initialize() {
        mudarFotoLogin();
    }

    private void mudarFotoLogin() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), foto);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.3);
            fadeOut.setOnFinished(e -> {
                indexImagemAtual = (indexImagemAtual + 1) % imageArray.length;
                foto.setImage(imageArray[indexImagemAtual]);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), foto);
                fadeIn.setFromValue(0.3);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    void onLoginClick() throws IOException {

        // Faz o login do usuário
        Conta usuarioLogado = DAOFactory.createContaDao().login(login.getText(), senha.getText());

        if (usuarioLogado != null) {
            Stage loginStage = (Stage) botaoLogin.getScene().getWindow();
            loginStage.close(); // Fecha tela de login

            // Carregar a nova tela
            FXMLLoader loader;
            if (usuarioLogado.getTipo_conta().toLowerCase(Locale.ROOT).equals("cliente")) {
                loader = new FXMLLoader(getClass().getResource("tela_cliente_view.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("tela_funcionario_view.fxml"));
            }

            Parent root = loader.load();
            Scene scene = new Scene(root);

            // salva o controlador da nova tela
            Object controller = loader.getController();

            // passa os dados do usuarioLogado para o controlador
            if (controller instanceof TelaClienteController) {
                ((TelaClienteController) controller).setUsuarioLogado(usuarioLogado);
            } else if (controller instanceof TelaFuncionarioController) {
                ((TelaFuncionarioController) controller).setUsuarioLogado(usuarioLogado);
            }

            // config do Stage e exibir a nova tela
            Stage stage = new Stage();
            stage.setScene(scene);

            // título da janela
            String titulo = usuarioLogado.getTipo_conta().equals("Cliente") ? "Olá " + usuarioLogado.getNome() : "Painel de controle";
            stage.setTitle(titulo);

            stage.show(); // Mostrar a nova tela

        } else {
            Alertas.mostrarAlerta(null, null, "Login ou senha incorretos. Tente novamente.", Alert.AlertType.INFORMATION);
        }

    }
}