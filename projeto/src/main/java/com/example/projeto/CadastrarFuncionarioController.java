package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.util.Alertas;
import com.example.projeto.util.Hora;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

public class CadastrarFuncionarioController {
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
    private TextField salario;
    @FXML
    private TextField inicio_exp;
    @FXML
    private TextField fim_exp;
    @FXML
    private ImageView foto;
    @FXML
    private Button salvar;
    File file;

    @FXML
    public void initialize() {
        foto.setImage(new Image("file:src/main/resources/img/adm.png"));
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
                senha.getText().isEmpty() || inicio_exp.getText().isEmpty() || fim_exp.getText().isEmpty()) {

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
        novaConta.setTipo_conta("funcionario");
        novaConta.setSalario_funcionario(Float.parseFloat(salario.getText()));
        novaConta.setInicio_expediente_funcionario(Hora.formatarHora(inicio_exp.getText()));
        novaConta.setFim_expediente_funcionario(Hora.formatarHora(fim_exp.getText()));

        if (file != null) {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            novaConta.setFoto(fileBytes);
        } else {
            Image imagemPadrao = new Image("file:src/main/resources/img/adm.png");
            novaConta.setFoto(imageToByteArray(imagemPadrao));
        }

        novaConta.setData_registro(new java.sql.Date(System.currentTimeMillis()));

        DAOFactory.createContaDao().inserirFuncionario(novaConta);

        Alertas.mostrarAlerta(null,null,"Funcionario cadastrado com sucesso!", Alert.AlertType.INFORMATION);

        Stage fechar = (Stage) salvar.getScene().getWindow();
        fechar.close();
    }
    private byte[] imageToByteArray(Image imagem) throws IOException {
        File tempFile = File.createTempFile("temp_image", ".png");
        try (InputStream input = getClass().getResourceAsStream("/img/adm.png")) {
            if (input == null) {
                throw new IOException("Resource not found: /img/adm.png");
            }
            Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return Files.readAllBytes(tempFile.toPath());
    }

}
