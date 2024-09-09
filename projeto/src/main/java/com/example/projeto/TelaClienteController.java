package com.example.projeto;

import com.example.projeto.model.entities.Conta;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;

public class TelaClienteController {
    @FXML
    private Text nomeUsuario;
    @FXML
    private Text e_mail;
    @FXML
    private ImageView foto;
    private Conta usuarioLogado;
    private static Stage stage;

    public void setUsuarioLogado(Conta usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        nomeUsuario.setText(usuarioLogado.getNome());
        e_mail.setText(usuarioLogado.getE_mail());

        // precesso de conveter byte[] para InputStream e setar a imagem
        if( usuarioLogado.getFoto() != null ){
            ByteArrayInputStream inputStream = new ByteArrayInputStream(usuarioLogado.getFoto());
            Image imagem = new Image(inputStream);
            foto.setImage(imagem);
        }
    }
}
