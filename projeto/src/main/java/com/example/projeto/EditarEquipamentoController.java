package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.model.entities.Equipamento;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.sql.SQLException;

public class EditarEquipamentoController {
    @FXML
    private TextField nome;
    @FXML
    private TextField tipo;
    @FXML
    private Button editar;

    private Equipamento equipamentoSelecionado;
    private static Stage stage;

    public void setEquipamentoSelecionado(Equipamento equipamentoSelecionado) {
        this.equipamentoSelecionado = equipamentoSelecionado;
        nome.setText(equipamentoSelecionado.getNome());
        tipo.setText(equipamentoSelecionado.getTipo());
    }

    @FXML
    private void onEditarClick() throws SQLException {
        Equipamento equipamentoEdit = new Equipamento();

        equipamentoEdit.setId(equipamentoSelecionado.getId());
        equipamentoEdit.setNome(nome.getText());
        equipamentoEdit.setTipo(tipo.getText());

        DAOFactory.createEquipamentoDao().atualizar(equipamentoEdit, 0);

        Stage fechar = (Stage) editar.getScene().getWindow();
        fechar.close();
    }
}
