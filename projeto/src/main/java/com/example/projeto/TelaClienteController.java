package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.model.entities.Equipamento;
import com.example.projeto.model.entities.Reserva;
import com.example.projeto.util.Alertas;
import com.example.projeto.util.Hora;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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

        // Adiciona listener para garantir que o GridPane já tenha uma Scene associada
        reservas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                iniciarAtualizacaoPeriodica();
            }
        });
    }

    private void iniciarAtualizacaoPeriodica() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {popularReservas();populateEquipamentosMenu();})
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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

    private void popularReservas() {
        reservas.getChildren().clear();

        List<Reserva> reservasList = DAOFactory.createReservaDao().listarConta(usuarioLogado);

        // listener para alterações de tamanho da janela
        Stage currentStage = (Stage) reservas.getScene().getWindow();
        currentStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            ajustarGrid(reservasList, newVal.doubleValue());
        });

        ajustarGrid(reservasList, currentStage.getWidth());  // Configuração inicial da grid
    }

    private void ajustarGrid(List<Reserva> reservasList, double larguraJanela) {
        int numColunas = calcularNumColunas(larguraJanela);
        int row = 0;
        int col = 0;

        try {
            for (Reserva reserva : reservasList) {
                if (reserva.getStatus() != 0) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("reserva_card_view.fxml"));
                    AnchorPane reservaCard = loader.load();

                    Text reservaTitle = (Text) reservaCard.lookup("#reservaTitle");
                    Text reservaStart = (Text) reservaCard.lookup("#reservaStart");
                    Text reservaEnd = (Text) reservaCard.lookup("#reservaEnd");
                    ImageView deletarReserva = (ImageView) reservaCard.lookup("#deletarReserva");

                    reservaTitle.setText("Reserva de " + DAOFactory.createEquipamentoDao().procurarPorId(reserva.getEquipamento()).getNome());
                    reservaStart.setText("Das: " + reserva.getHora_inicio());
                    reservaEnd.setText("Até: " + reserva.getHora_fim());

                    deletarReserva.setOnMouseClicked(event -> {
                        reserva.setStatus(0);
                        try {
                            DAOFactory.createReservaDao().editarReserva(reserva);
                            popularReservas();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    reservas.add(reservaCard, col++, row);

                    if (col >= numColunas) {
                        col = 0;
                        row++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calcularNumColunas(double larguraJanela) {
        double larguraCard = 300; // Defina conforme o tamanho dos componentes
        int numColunas = (int) (larguraJanela / larguraCard);

        // Limita o número de colunas mínimo e máximo
        if (numColunas < 1) {
            numColunas = 1;  // No mínimo, uma coluna
        }
        return numColunas;
    }


    @FXML
    void onNovaReserva() {
        Date dataAtual = new Date(System.currentTimeMillis());
        Reserva reserva = new Reserva();

        reserva.setData_reserva(String.valueOf(data.getValue()));
        reserva.setHora_inicio(Hora.formatarHora(horaInicio.getText()));
        reserva.setHora_fim(Hora.formatarHora(horaFim.getText()));

        if (data.getValue().isBefore(dataAtual.toLocalDate())) {
            Alertas.mostrarAlerta(null, null, "Data inválida.", Alert.AlertType.INFORMATION);
        } else if (Integer.parseInt(horaInicio.getText()) > Integer.parseInt(horaFim.getText())) {
            Alertas.mostrarAlerta(null, null, "Horas inválidas.", Alert.AlertType.INFORMATION);
        } else if (equipamentoSelecionado == null) {
            Alertas.mostrarAlerta(null, null, "Selecione um equipamento.", Alert.AlertType.INFORMATION);
        } else {
            DAOFactory.createReservaDao().criarReserva(usuarioLogado, reserva, equipamentoSelecionado);
            popularReservas();
        }
    }

    @FXML
    void onLogOutClick() throws IOException {
        Stage tela = (Stage) logOut.getScene().getWindow();
        tela.close();

        stage = Application.newStage("application_view.fxml", "Login");
    }
}
