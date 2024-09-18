package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import com.example.projeto.util.Alertas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CadastrarClienteController {
    @FXML
    private TextField nome;
    @FXML
    private TextField telefone;
    @FXML
    private TextField e_mail;
    @FXML
    private TextField cpf;
    @FXML
    private TextField endereco;
    @FXML
    private TextField login;
    @FXML
    private TextField senha;
    @FXML
    private TextField mensalidade;
    @FXML
    private ImageView foto;
    @FXML
    private Button salvar;
    File file;

    @FXML
    public void initialize() {
        foto.setImage(new Image("file:src/main/resources/img/account.png"));
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
    public void onSalvarClick() throws IOException {
        if (nome.getText().isEmpty() || telefone.getText().isEmpty() || e_mail.getText().isEmpty() ||
                cpf.getText().isEmpty() || endereco.getText().isEmpty() || login.getText().isEmpty() ||
                senha.getText().isEmpty() || mensalidade.getText().isEmpty()) {

            Alertas.mostrarAlerta("Erro", null, "Por favor, preencha todos os campos obrigatórios.", Alert.AlertType.ERROR);
            return;
        }

        Conta novaConta = new Conta();
        novaConta.setNome(nome.getText());
        novaConta.setTelefone(telefone.getText());
        novaConta.setE_mail(e_mail.getText());
        novaConta.setCpf(cpf.getText());
        novaConta.setEndereco(endereco.getText());
        novaConta.setLogin(login.getText());
        novaConta.setSenha(senha.getText());
        novaConta.setTipo_conta("cliente");

        if (file != null) {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            novaConta.setFoto(fileBytes);
        } else {
            Image imagemPadrao = new Image("file:src/main/resources/img/account.png");
            novaConta.setFoto(imageToByteArray(imagemPadrao));
        }

        novaConta.setData_registro(new java.sql.Date(System.currentTimeMillis()));
        novaConta.setMensalidade_cliente(Float.parseFloat(mensalidade.getText()));

        DAOFactory.createContaDao().inserirCliente(novaConta);

        Alertas.mostrarAlerta(null, null, "Cliente cadastrado com sucesso!", Alert.AlertType.INFORMATION);

        Stage fechar = (Stage) salvar.getScene().getWindow();
        fechar.close();
    }

    private byte[] imageToByteArray(Image imagem) throws IOException {
        File tempFile = File.createTempFile("temp_image", ".png");
        try (InputStream input = getClass().getResourceAsStream("/img/account.png")) {
            if (input == null) {
                throw new IOException("Resource not found: /img/account.png");
            }
            Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return Files.readAllBytes(tempFile.toPath());
    }

}
