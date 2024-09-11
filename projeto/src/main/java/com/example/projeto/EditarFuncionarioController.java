package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.util.Hora;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class EditarFuncionarioController {
    @FXML
    private TextField nome;
    @FXML
    private ImageView foto;
    @FXML
    private TextField telefone;
    @FXML
    private TextField email;
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
    private TextField inicio;
    @FXML
    private TextField fim;
    @FXML
    private Button editar;

    File file;

    private Conta usuarioSelecionado;

    public void usuarioSelecionado(Conta usuarioSelecionado){
        this.usuarioSelecionado = usuarioSelecionado;
        nome.setText(usuarioSelecionado.getNome());
        telefone.setText(usuarioSelecionado.getTelefone());
        email.setText(usuarioSelecionado.getE_mail());
        cpf.setText(usuarioSelecionado.getCpf());
        endereco.setText(usuarioSelecionado.getEndereco());
        login.setText(usuarioSelecionado.getLogin());
        senha.setText(usuarioSelecionado.getSenha());
        salario.setText(String.valueOf(usuarioSelecionado.getSalario_funcionario()));
        inicio.setText(String.valueOf(usuarioSelecionado.getInicio_expediente_funcionario()));
        fim.setText(String.valueOf(usuarioSelecionado.getFim_expediente_funcionario()));

        // precesso de conveter byte[] para InputStream e setar a imagem
        if( usuarioSelecionado.getFoto() != null ){
            ByteArrayInputStream inputStream = new ByteArrayInputStream(usuarioSelecionado.getFoto());
            Image imagem = new Image(inputStream);
            foto.setImage(imagem);
        }
    }

    @FXML
    private void onEditarClick() throws IOException, SQLException {
        Conta contaEdit = new Conta();

        contaEdit.setId(usuarioSelecionado.getId());
        contaEdit.setTipo_conta(usuarioSelecionado.getTipo_conta());
        contaEdit.setNome(String.valueOf(nome.getText()));
        contaEdit.setTelefone(String.valueOf(telefone.getText()));
        contaEdit.setE_mail(String.valueOf(email.getText()));
        contaEdit.setCpf(String.valueOf(cpf.getText()));
        contaEdit.setEndereco(String.valueOf(endereco.getText()));
        contaEdit.setLogin(String.valueOf(login.getText()));
        contaEdit.setSenha(String.valueOf(senha.getText()));
        contaEdit.setSalario_funcionario(Float.parseFloat(String.valueOf(salario.getText())));
        contaEdit.setInicio_expediente_funcionario(Hora.formatarHora(inicio.getText()));
        contaEdit.setFim_expediente_funcionario(Hora.formatarHora(fim.getText()));

        if(file!=null){
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            contaEdit.setFoto(fileBytes);
        }

        DAOFactory.createContaDao().atualizar(contaEdit);

        Stage fechar = (Stage) editar.getScene().getWindow();
        fechar.close();

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

}
