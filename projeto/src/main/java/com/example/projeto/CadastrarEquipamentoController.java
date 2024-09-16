package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Equipamento;
import com.example.projeto.util.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class CadastrarEquipamentoController {
    @FXML
    private TextField nome;
    @FXML
    private TextField tipo;

    @FXML
    private Button salvar;

    @FXML
    public void onSalvarClick(){
        if (nome.getText().isEmpty() || tipo.getText().isEmpty()) {
            Alertas.mostrarAlerta(null, null, "Preencha todos os campos.", Alert.AlertType.INFORMATION);
            return;
        }

        Equipamento novoEquipamento = new Equipamento();
        novoEquipamento.setNome(nome.getText());
        novoEquipamento.setTipo(tipo.getText());

        DAOFactory.createEquipamentoDao().inserir(novoEquipamento);
        Stage fechar = (Stage) salvar.getScene().getWindow();
        fechar.close();
    }
}