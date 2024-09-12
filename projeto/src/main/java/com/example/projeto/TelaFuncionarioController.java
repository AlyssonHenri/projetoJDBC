package com.example.projeto;

import com.example.projeto.model.dao.DAOFactory;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.model.entities.Equipamento;
import com.example.projeto.model.entities.Reserva;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaFuncionarioController {
    // elemento da tabela de reservas
    @FXML
    private TableView<Reserva> tabelaReserva;
    @FXML
    private TableColumn<Reserva, String> nomeClienteColumn;
    @FXML
    private TableColumn<Reserva, String> nomeEquipamentoClienteColumn;
    @FXML
    private TableColumn<Reserva, String> inicioReservaClienteColumn;
    @FXML
    private TableColumn<Reserva, String> fimReservaClienteColumn;


    // elemntos da tabela de equipamentos
    @FXML
    private TableView<Equipamento> tabelaEquipamentos;
    @FXML
    private TableColumn<Equipamento, String> nomeEquipamentoColumn;
    @FXML
    private TableColumn<Equipamento, String> tipoEquipamentoColumn;
    @FXML
    private TableColumn<Equipamento, String> statusEquipamentoColumn;

    // elementos da tabela de contas
    @FXML
    private TableView<Conta> tabelaContas;
    @FXML
    private TableColumn<Conta, String> nomeContaColumn;
    @FXML
    private TableColumn<Conta, String> tipoContaColumn;

    @FXML
    private TextField buscaReservaField;
    @FXML
    private TextField buscaEquipamentoField;
    @FXML
    private TextField buscaContaField;
    @FXML
    private Button logOut;

    // valores para receber e usar os dados do usuario logado
    private Conta usuarioLogado;
    @FXML
    private Text nomeUsuario;
    private static Stage stage;

    public void setUsuarioLogado(Conta usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        nomeUsuario.setText(usuarioLogado.getNome());
    }

    @FXML
    void initialize() {
        //tabela de reservas
        nomeClienteColumn.setCellValueFactory(cellData -> {
            Conta conta = DAOFactory.createContaDao().procurarPorId(cellData.getValue().getConta_cliente());
            return new SimpleStringProperty(conta.getNome());
        });
        nomeEquipamentoClienteColumn.setCellValueFactory(cellData -> {
            Equipamento equipamento = DAOFactory.createEquipamentoDao().procurarPorId(cellData.getValue().getEquipamento());
            return new SimpleStringProperty(equipamento.getNome());
        });
        inicioReservaClienteColumn.setCellValueFactory(new PropertyValueFactory<>("hora_inicio"));
        fimReservaClienteColumn.setCellValueFactory(new PropertyValueFactory<>("hora_fim"));

        //tabela de equipamentos
        nomeEquipamentoColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipoEquipamentoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        // tive que fazer isso aqui pra fazer a conversão da booleana pra string ficar exibida na tabela
        statusEquipamentoColumn.setCellValueFactory(cellData -> {
            int status = cellData.getValue().getStatus_equipamento();
            if (status == 0) {
                return new SimpleStringProperty("Inativo");
            } else if (status == 1) {
                return new SimpleStringProperty("Ativo");
            } else {
                return new SimpleStringProperty("Desconhecido");
            }
        });

        //tabela de contas
        nomeContaColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipoContaColumn.setCellValueFactory(new PropertyValueFactory<>("tipo_conta"));

        // menu de contexto da tabela reservas
        tabelaReserva.setRowFactory(tv -> {
            TableRow<Reserva> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    Reserva reserva = row.getItem();
                    showContextMenuReserva(reserva, event);
                }
            });
            return row;
        });

        // menu de contexto da tabela contas
        tabelaContas.setRowFactory(tv -> {
            TableRow<Conta> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    Conta conta = row.getItem();
                    showContextMenuConta(conta, event);
                }
            });
            return row;
        });

        // menu de contexto da tabela contas
        tabelaEquipamentos.setRowFactory(tv -> {
            TableRow<Equipamento> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    Equipamento equipamento = row.getItem();
                    showContextMenuEquipamento(equipamento, event);
                }
            });
            return row;
        });
    }

    public void loadTabelaReservas(){
        List<Reserva> lista = DAOFactory.createReservaDao().listarTodas();
        ObservableList<Reserva> reservasFiltradas = FXCollections.observableArrayList(lista);
        tabelaReserva.setItems(reservasFiltradas);
    }

    public void loadTabelaContas(){
        List<Conta> lista = DAOFactory.createContaDao().procurarTodos();
        ObservableList<Conta> contasList = FXCollections.observableArrayList(lista);
        tabelaContas.setItems(contasList);
    }

    public void loadTabelaEquipamentos(){
        List<Reserva> lista = DAOFactory.createReservaDao().listarTodas();
        ObservableList<Reserva> reservasList = FXCollections.observableArrayList(lista);
        tabelaReserva.setItems(reservasList);
    }

    // métodos para exibir os menu de contexto
    private void showContextMenuReserva(Reserva reserva, MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deletar = new MenuItem("Deletar");
        deletar.setOnAction(e -> DAOFactory.createReservaDao().deletarPorId(reserva));

        if(reserva.getStatus() == 1){
            MenuItem desativar = new MenuItem("Desativar");
            desativar.setOnAction(e -> {
                try {
                    onMudarEstadoReserva(reserva, 1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            contextMenu.getItems().addAll(desativar);
        }else{
            MenuItem ativar = new MenuItem("Ativar");
            ativar.setOnAction(e -> {
                try {
                    onMudarEstadoReserva(reserva, 0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            contextMenu.getItems().addAll(ativar);
        }

        contextMenu.getItems().addAll(deletar);

        // Exibe o menu no ponto do clique
        contextMenu.show(tabelaReserva, event.getScreenX(), event.getScreenY());
    }

    private void showContextMenuConta(Conta conta, MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        if( conta.getId() != usuarioLogado.getId() ){
            MenuItem deletar = new MenuItem("Deletar");
            deletar.setOnAction(e -> DAOFactory.createContaDao().deletarPorId(conta));

            contextMenu.getItems().addAll(deletar);
        }

        MenuItem editar = new MenuItem("Editar");
        editar.setOnAction(e -> {
            try {
                onEditarContaClick(conta);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        contextMenu.getItems().addAll(editar);

        contextMenu.show(tabelaReserva, event.getScreenX(), event.getScreenY());
    }

    private void showContextMenuEquipamento(Equipamento equipamento, MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deletar = new MenuItem("Deletar");
        deletar.setOnAction(e -> DAOFactory.createEquipamentoDao().deletarPorId(equipamento));

        MenuItem editar = new MenuItem("Editar");
        editar.setOnAction(e -> {
            try {
                onEditarEquipamentoClick(equipamento);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        if(equipamento.getStatus_equipamento() == 1){
            MenuItem desativar = new MenuItem("Desativar");
            desativar.setOnAction(e -> {
                try {
                    onMudarEstadoEquipamento(equipamento, 1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            contextMenu.getItems().addAll(desativar);
        }else{
            MenuItem ativar = new MenuItem("Ativar");
            ativar.setOnAction(e -> {
                try {
                    onMudarEstadoEquipamento(equipamento, 0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            contextMenu.getItems().addAll(ativar);
        }

        contextMenu.getItems().addAll(deletar, editar);

        contextMenu.show(tabelaReserva, event.getScreenX(), event.getScreenY());
    }

    @FXML
    void onClickReservas(){
        List<Reserva> lista = DAOFactory.createReservaDao().listarTodas();
        ObservableList<Reserva> reservasList = FXCollections.observableArrayList(lista);
        tabelaReserva.setItems(reservasList);
    }

    @FXML
    void onClickEquipamentos(){
        List<Equipamento> lista = DAOFactory.createEquipamentoDao().procurarTodos();
        ObservableList<Equipamento> equipamentosList = FXCollections.observableArrayList(lista);
        tabelaEquipamentos.setItems(equipamentosList);
    }

    @FXML
    void onClickContas(){
        List<Conta> lista = DAOFactory.createContaDao().procurarTodos();
        ObservableList<Conta> contasList = FXCollections.observableArrayList(lista);
        tabelaContas.setItems(contasList);
    }

    @FXML
    void onBuscaReserva() {
        String filtro = buscaReservaField.getText().toLowerCase();
        List<Reserva> lista = DAOFactory.createReservaDao().listarTodas();
        ObservableList<Reserva> reservasFiltradas = FXCollections.observableArrayList();

        for (Reserva reserva : lista) {
            Conta cliente = DAOFactory.createContaDao().procurarPorId(reserva.getConta_cliente());
            if (cliente.getNome().toLowerCase().contains(filtro)) {
                reservasFiltradas.add(reserva);
            }
        }

        tabelaReserva.setItems(reservasFiltradas);
    }

    @FXML
    void onBuscaEquipamento() {
        String filtro = buscaEquipamentoField.getText().toLowerCase();
        List<Equipamento> lista = DAOFactory.createEquipamentoDao().procurarTodos();
        ObservableList<Equipamento> equipamentosFiltrados = FXCollections.observableArrayList();

        for (Equipamento equipamento : lista) {
            if (equipamento.getNome().toLowerCase().contains(filtro)) {
                equipamentosFiltrados.add(equipamento);
            }
        }

        tabelaEquipamentos.setItems(equipamentosFiltrados);
    }

    @FXML
    void onBuscaConta() {
        String filtro = buscaContaField.getText().toLowerCase();
        List<Conta> lista = DAOFactory.createContaDao().procurarTodos();
        ObservableList<Conta> contasFiltradas = FXCollections.observableArrayList();

        for (Conta conta : lista) {
            if (conta.getNome().toLowerCase().contains(filtro)) {
                contasFiltradas.add(conta);
            }
        }

        tabelaContas.setItems(contasFiltradas);
    }

    @FXML
    void onNovoClienteClick() {
        try {
            stage = Application.newStage("cadastrar_cliente_view.fxml", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onNovoFuncionarioClick() {
        try {
            stage = Application.newStage("cadastrar_funcionario_view.fxml", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onNovoEquipamentoClick(){
        try {
            stage = Application.newStage("cadastrar_equipamento_view.fxml", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onLogOutClick() throws IOException {
        Stage tela = (Stage) logOut.getScene().getWindow();
        tela.close();

        stage = Application.newStage("application_view.fxml","Login");
    }

    @FXML
    void onEditarContaClick(Conta c) throws IOException {
        FXMLLoader loader;

        if (c.getTipo_conta().equalsIgnoreCase("cliente")) {
            loader = new FXMLLoader(getClass().getResource("editar_conta_cliente_view.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("editar_conta_funcionario_view.fxml"));
        }

        Parent root = loader.load();
        Scene scene = new Scene(root);

        Object controller = loader.getController();

        // passa os dados do usuarioLogado para o controlador
        if (controller instanceof EditarClienteController) {
            ((EditarClienteController) controller).usuarioSelecionado(c);
        } else if (controller instanceof EditarFuncionarioController) {
            ((EditarFuncionarioController) controller).usuarioSelecionado(c);
        }

        // config do Stage e exibir a nova tela
        Stage stage = new Stage();
        stage.setScene(scene);

        String titulo = "Editando " + c.getNome();
        stage.setTitle(titulo);

        stage.show();
    }

    @FXML
    void onEditarEquipamentoClick(Equipamento e) throws IOException {
        FXMLLoader loader;

        loader = new FXMLLoader(getClass().getResource("editar_equipamento_view.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        Object controller = loader.getController();

        ((EditarEquipamentoController) controller).setEquipamentoSelecionado(e);

        Stage stage = new Stage();
        stage.setScene(scene);

        String titulo = "Editando " + e.getNome();
        stage.setTitle(titulo);

        stage.show();
    }

    @FXML
    void onMudarEstadoEquipamento(Equipamento e, int alt) throws SQLException {
        Equipamento equipamentoEdit = new Equipamento();

        equipamentoEdit.setId(e.getId());

        if(alt == 0){
            equipamentoEdit.setStatus_equipamento(1);
            DAOFactory.createEquipamentoDao().atualizar(equipamentoEdit,1);
        }else{
            equipamentoEdit.setStatus_equipamento(0);
            DAOFactory.createEquipamentoDao().atualizar(equipamentoEdit,1);
        }
        loadTabelaEquipamentos();
    }

    @FXML
    void onMudarEstadoReserva(Reserva r, int alt) throws SQLException {
        Reserva reservaEdit = new Reserva();

        reservaEdit.setId(r.getId());

        if(alt == 0){
            reservaEdit.setStatus(1);
            DAOFactory.createReservaDao().editarReserva(reservaEdit);
        }else{
            reservaEdit.setStatus(0);
            DAOFactory.createReservaDao().editarReserva(reservaEdit);
        }

    }
}