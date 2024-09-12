package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.model.entities.Equipamento;
import com.example.projeto.model.entities.Reserva;
import com.example.projeto.util.Hora;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class TelaClienteController {
    @FXML
    private Text nomeUsuario;
    @FXML
    private Text e_mail;
    @FXML
    private ImageView foto;
    @FXML
    private Button logOut;
    @FXML
    private GridPane reservas;
    @FXML
    private MenuButton equipamentos;
    @FXML
    private DatePicker data;
    @FXML
    private TextField horaInicio;
    @FXML
    private TextField horaFim;
    private Equipamento equipamentoSelecionado;
    private Conta usuarioLogado;
    private static Stage stage;

    public void setUsuarioLogado(Conta usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        nomeUsuario.setText(usuarioLogado.getNome());
        e_mail.setText(usuarioLogado.getE_mail());

        if (usuarioLogado.getFoto() != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(usuarioLogado.getFoto());
            Image imagem = new Image(inputStream);
            foto.setImage(imagem);
        }

        populateEquipamentosMenu();
        populateReservasGrid();
    }

    private void populateEquipamentosMenu() {
        try {
            List<Equipamento> listaEquipamentos = DAOFactory.createEquipamentoDao().procurarTodos();

            equipamentos.getItems().clear();

            for (Equipamento equipamento : listaEquipamentos) {
                MenuItem menuItem = new MenuItem(equipamento.getNome());
                menuItem.setOnAction(event -> {
                    equipamentos.setText(equipamento.getNome());
                    equipamentoSelecionado = equipamento;
                });
                equipamentos.getItems().add(menuItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateReservasGrid() {
        try {
            reservas.getChildren().clear();

            List<Reserva> reservasList = DAOFactory.createReservaDao().listarConta(usuarioLogado);

            int row = 0;
            int col = 0;

            for (Reserva reserva : reservasList) {
                // Create a new FXMLLoader instance for each card
                FXMLLoader loader = new FXMLLoader(getClass().getResource("reserva_card_view.fxml"));

                // Load the FXML for this card
                AnchorPane reservaCard = loader.load();

                // Access and set the controls in the loaded FXML
                Text reservaTitle = (Text) reservaCard.lookup("#reservaTitle");
                Text reservaStart = (Text) reservaCard.lookup("#reservaStart");
                Text reservaEnd = (Text) reservaCard.lookup("#reservaEnd");
                ImageView deletarReserva = (ImageView) reservaCard.lookup("#deletarReserva");

                reservaTitle.setText("Reserva de " + reserva.getEquipamento());
                reservaStart.setText("Das: " + reserva.getHora_inicio());
                reservaEnd.setText("Até: " + reserva.getHora_fim());

                deletarReserva.setOnMouseClicked(event -> {
                    System.out.println("Deleting reservation: " + reserva.getId());
                });

                reservas.add(reservaCard, col++, row);

                if (col >= 2) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AnchorPane createReservaCard(Reserva reserva) {
        AnchorPane card = new AnchorPane();
        // Configure the card with reservation details
        // Example: Set card's text fields or labels with reservation information

        Text reservaText = new Text("Data: " + reserva.getData_reserva() + "\n" +
                "Início: " + reserva.getHora_inicio() + "\n" +
                "Fim: " + reserva.getHora_fim());
        // Add the Text or other elements to the card
        card.getChildren().add(reservaText);

        // You can add an ImageView or other controls here as needed
        // e.g., card.getChildren().add(new ImageView(new Image("image/path.png")));

        return card;
    }

    @FXML
    void onNovaReserva() {
        Reserva reserva = new Reserva();

        reserva.setData_reserva(String.valueOf(data.getValue()));
        reserva.setHora_inicio(Hora.formatarHora(horaInicio.getText()));
        reserva.setHora_fim(Hora.formatarHora(horaFim.getText()));

        DAOFactory.createReservaDao().criarReserva(usuarioLogado, reserva, equipamentoSelecionado);

        // Refresh the reservations grid after creating a new reservation
        populateReservasGrid();
    }

    @FXML
    void onLogOutClick() throws IOException {
        Stage tela = (Stage) logOut.getScene().getWindow();
        tela.close();

        stage = Application.newStage("application_view.fxml", "Login");
    }
}
