package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Equipamento;
import javafx.fxml.FXML;
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
    private ComboBox<String> tipo;

    @FXML
    private Button salvar;

    @FXML
    public void initialize() {
        tipo.setItems(FXCollections.observableArrayList("cardio", "força"));
    }

    @FXML
    void onFotoClick() {
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fc.getExtensionFilters().add(imageFilter);

    }

    @FXML
    public void onSalvarClick(){
        if (nome.getText().isEmpty() || tipo.getValue() == null) {
            System.out.println("Preencha todos os campos obrigatórios.");
            return;
        }

        Equipamento novoEquipamento = new Equipamento();
        novoEquipamento.setNome(nome.getText());
        novoEquipamento.setTipo(tipo.getValue());

        DAOFactory.createEquipamentoDao().inserir(novoEquipamento);
        Stage fechar = (Stage) salvar.getScene().getWindow();
        fechar.close();
    }
}