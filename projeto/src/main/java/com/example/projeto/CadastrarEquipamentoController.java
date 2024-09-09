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
    private ImageView foto;
    @FXML
    private Button salvar;
    private File file;

    @FXML
    public void initialize() {
        tipo.setItems(FXCollections.observableArrayList("cardio", "força"));

        tipo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String opcao) {
                if (opcao != null) {
                    switch (opcao) {
                        case "cardio":
                            foto.setImage(new Image(getClass().getResource("/img/cardio-workout.png").toString()));
                            break;
                        case "força":
                            foto.setImage(new Image(getClass().getResource("/img/força.png").toString()));
                            break;
                    }
                }
            }
        });
    }

    @FXML
    void onFotoClick() {
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fc.getExtensionFilters().add(imageFilter);

        file = fc.showOpenDialog(foto.getScene().getWindow());

        if (file != null) {
            foto.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    public void onSalvarClick() throws IOException, URISyntaxException {
        if (nome.getText().isEmpty() || tipo.getValue() == null) {
            System.out.println("Preencha todos os campos obrigatórios.");
            return;
        }

        Equipamento novoEquipamento = new Equipamento();
        novoEquipamento.setNome(nome.getText());
        novoEquipamento.setTipo(tipo.getValue());

        if (file != null) {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            novoEquipamento.setImagem(fileBytes);
        } else {
            Image imagemPadrao;
            switch (tipo.getValue()) {
                case "cardio":
                    imagemPadrao = new Image(getClass().getResource("/img/cardio-workout.png").toString());
                    break;
                case "força":
                    imagemPadrao = new Image(getClass().getResource("/img/força.png").toString());
                    break;
                default:
                    imagemPadrao = null;
                    break;
            }

            if (imagemPadrao != null) {
                byte[] fileBytes = Files.readAllBytes(new File(getClass().getResource("/img/" + (tipo.getValue().equals("cardio") ? "cardio-workout.png" : "força.png")).toURI()).toPath());
                novoEquipamento.setImagem(fileBytes);
            } else {
                System.out.println("Erro ao carregar a imagem padrão.");
                return;
            }
        }

        DAOFactory.createEquipamentoDao().inserir(novoEquipamento);
        Stage fechar = (Stage) salvar.getScene().getWindow();
        fechar.close();
    }
}