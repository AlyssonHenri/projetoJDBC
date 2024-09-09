package com.example.projeto;

import com.example.projeto.model.entities.Conta;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaFuncionarioController {
    @FXML
    private Text nomeUsuario;
    private Conta usuarioLogado;
    private static Stage stage;
    public void setUsuarioLogado(Conta usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        nomeUsuario.setText(usuarioLogado.getNome());

    }

    @FXML
    void onNovoClienteClick() {
        try {
            stage = Application.newStage("cadastrar_cliente_view.fxml", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onNovoFuncionarioClick() {
        try {
            stage = Application.newStage("cadastrar_funcionario_view.fxml", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onNovoEquipamentoClick(){
        try {
            stage = Application.newStage("cadastrar_equipamento_view.fxml", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
