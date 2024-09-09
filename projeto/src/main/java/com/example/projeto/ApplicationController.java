package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class ApplicationController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField senha;
    @FXML
    private Button botaoLogin;
    private static Stage stage;
    public static Stage getStage(){
        return stage;
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
            // Exibir alerta de login incorreto
            Alertas.mostrarAlerta(null, null, "Login ou senha incorretos. Tente novamente.", Alert.AlertType.INFORMATION);
        }

    }
}