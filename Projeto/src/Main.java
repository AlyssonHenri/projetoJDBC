import db.entities.conta;
import db.entities.dao.daoFactory;
import db.entities.equipamento;
import db.entities.reserva;
import db.entities.treino;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static conta usuarioLogado = null;
    private static final Scanner scanner = new Scanner(System.in);
    static int escolha = 99;

    public static void main(String[] args) {
        while (escolha != 0) {
            if (usuarioLogado == null) {
                mostrarMenuLogin();
            } else {
                mostrarMenuPrincipal();
            }
        }
    }

    public static void mostrarMenuLogin() {
        System.out.println("=== Menu de Login ===");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        usuarioLogado = daoFactory.createContaDao().login(login, senha);

        if (usuarioLogado != null) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Login ou senha incorretos. Tente novamente.");
        }
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("=== Menu Principal ===");
        if (usuarioLogado.getTipo_conta().equals("cliente")) {
            System.out.println("1. Ver meus dados");
            System.out.println("2. Reservar equipamento");
            System.out.println("3. Criar novo treino");
            System.out.println("4. Listar meus treinos");
            System.out.println("5. Editar um treino");
            System.out.println("6. Deletar um treino");
            System.out.println("7. Listar equipamentos");
            System.out.println("0. Sair");
        } else if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
            System.out.println("1. Criar nova conta");
            System.out.println("2. Criar novo equipamento");
            System.out.println("3. Exibir dados de uma conta");
            System.out.println("4. Listar todas as contas");
            System.out.println("5. Deletar uma conta");
            System.out.println("6. Atualizar dados de uma conta");
            System.out.println("7. Atualizar dados de um equipamento");
            System.out.println("8. Deletar um equipamento");
            System.out.println("9. Listar dados de um equipamento");
            System.out.println("10. Listar equipamentos");
            System.out.println("0. Sair");
        }

        escolha = Integer.parseInt(scanner.nextLine());

        switch (escolha) {
            case 1:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    criarNovaConta();
                } else if (usuarioLogado.getTipo_conta().equals("cliente")) {
                    verMeusDados();
                }
                break;
            case 2:
                if (usuarioLogado.getTipo_conta().equals("cliente")) {
                    reservarEquipamento();
                } else if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    criarNovoEquipamento();
                }
                break;
            case 3:
                if (usuarioLogado.getTipo_conta().equals("cliente")) {
                    criarNovoTreino();
                } else if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    listarDadosConta();
                }
                break;
            case 4:
                if (usuarioLogado.getTipo_conta().equals("cliente")) {
                    listarMeusTreinos();
                } else if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    listarTodasAsContas();
                }
                break;
            case 5:
                if (usuarioLogado.getTipo_conta().equals("cliente")) {
                    editarTreino();
                } else if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    deletarConta();
                }
                break;
            case 6:
                if (usuarioLogado.getTipo_conta().equals("cliente")) {
                    deletarTreino();
                } else if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    atualizarConta();
                }
                break;
            case 7:
                if (usuarioLogado.getTipo_conta().equals("cliente")) {
                    listarTodosEquipamentos();
                } else if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    atualizarEquipamento();
                }
                break;
            case 8:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    deletarEquipamento();
                }
                break;
            case 9:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    listarDadosEquipamento();
                }
                break;
            case 10:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    listarTodosEquipamentos();
                }
                break;
            case 11:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    listarTodasReservas();
                }
                break;
            case 12:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    editarReserva();
                }
                break;
            case 0:
                usuarioLogado = null;
                System.out.println("Sessão encerrada.");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                break;
        }
    }

    public static void verMeusDados() {
        if (usuarioLogado.getTipo_conta().equals("cliente")) {
            System.out.println("=== Meus Dados ===");
            System.out.println("Nome: " + usuarioLogado.getNome());
            System.out.println("Telefone: " + usuarioLogado.getTelefone());
            System.out.println("E-mail: " + usuarioLogado.getE_mail());
            System.out.println("CPF: " + usuarioLogado.getCpf());
            System.out.println("Endereço: " + usuarioLogado.getEndereco());
            System.out.println("Data de Registro: " + usuarioLogado.getData_registro());
            System.out.println("Mensalidade: " + usuarioLogado.getMensalidade_cliente());
        }
    }

    public static void criarNovoTreino() {
        treino novoTreino = new treino();

        System.out.print("Nome do Treino: ");
        novoTreino.setNome(scanner.nextLine());
        System.out.print("Descrição do Treino: ");
        novoTreino.setDescricao(scanner.nextLine());

        daoFactory.createTreinoDao().inserir(novoTreino, usuarioLogado.getId());
        System.out.println("Novo treino criado com sucesso!");
    }

    public static void listarMeusTreinos() {
        List<treino> lista = daoFactory.createTreinoDao().procurarTodos(usuarioLogado.getId());

        if (lista.isEmpty()) {
            System.out.println("Você não tem treinos cadastrados.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            treino treinoLoop = lista.get(i);
            System.out.println("=== Treino " + (i + 1) + " ===");
            System.out.println("ID: " + treinoLoop.getId());
            System.out.println("Nome: " + treinoLoop.getNome());
            System.out.println("Descrição: " + treinoLoop.getDescricao());
            System.out.println();
        }
    }

    public static void editarTreino() {
        try {
            System.out.print("ID do Treino a ser editado: ");
            int id = Integer.parseInt(scanner.nextLine());

            treino treinoAlvo = daoFactory.createTreinoDao().procurarPorId(id);

            if (treinoAlvo == null || treinoAlvo.getConta_cliente() != usuarioLogado.getId()) {
                System.out.println("Treino não encontrado ou não pertence a você.");
                return;
            }

            System.out.println("=== Selecione os dados para modificar ===");
            System.out.println("1. Nome");
            System.out.println("2. Descricão");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Novo Nome: ");
                    treinoAlvo.setNome(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Nova Descrição: ");
                    treinoAlvo.setDescricao(scanner.nextLine());
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

            daoFactory.createTreinoDao().atualizar(treinoAlvo);
            System.out.println("Treino atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o treino: " + e.getMessage());
        }
    }

    public static void deletarTreino() {
     try{
         System.out.print("ID do treino: ");
         int id = Integer.parseInt(scanner.nextLine());

         treino treinoEncontrada = daoFactory.createTreinoDao().procurarPorId(id);

         daoFactory.createTreinoDao().deletarPorId(treinoEncontrada);
     } catch (Exception e){
         System.out.println("Treino não encontrado no banco de dados.");
     }
    }

    public static void criarNovaConta() {
        conta novaConta = new conta();

        System.out.print("Nome: ");
        novaConta.setNome(scanner.nextLine());
        System.out.print("Telefone: ");
        novaConta.setTelefone(scanner.nextLine());
        System.out.print("E-mail: ");
        novaConta.setE_mail(scanner.nextLine());
        System.out.print("CPF: ");
        novaConta.setCpf(scanner.nextLine());
        System.out.print("Endereço: ");
        novaConta.setEndereco(scanner.nextLine());
        System.out.print("Login: ");
        novaConta.setLogin(scanner.nextLine());
        System.out.print("Senha: ");
        novaConta.setSenha(scanner.nextLine());
        System.out.print("Tipo de Conta (cliente/funcionario): ");
        novaConta.setTipo_conta(scanner.nextLine());

        novaConta.setData_registro(new Date(System.currentTimeMillis()));

        if (novaConta.getTipo_conta().equals("cliente")) {
            System.out.print("Mensalidade do Cliente: ");
            novaConta.setMensalidade_cliente(Float.parseFloat(scanner.nextLine()));
            daoFactory.createContaDao().inserirCliente(novaConta);
        } else if (novaConta.getTipo_conta().equals("funcionario")) {
            System.out.print("Salário do Funcionário: ");
            novaConta.setSalario_funcionario(Float.parseFloat(scanner.nextLine()));
            System.out.print("Início do Expediente: ");
            novaConta.setInicio_expediente_funcionario(scanner.nextLine());
            System.out.print("Fim do Expediente: ");
            novaConta.setFim_expediente_funcionario(scanner.nextLine());
            daoFactory.createContaDao().inserirFuncionario(novaConta);
        }

        novaConta.setData_registro(new Date(System.currentTimeMillis()));
        System.out.println("Nova conta criada com sucesso!");
    }

    public static void atualizarConta() {
        try {
            System.out.print("ID da conta: ");
            int id = Integer.parseInt(scanner.nextLine());

            conta contaAlvo = daoFactory.createContaDao().procurarPorId(id);

            if (contaAlvo == null) {
                System.out.println("Conta não encontrada no banco de dados.");
                return;
            }

            System.out.println("=== Selecione os dados para modificar.");
            if (contaAlvo.getTipo_conta().equals("cliente")) {
                System.out.println("1. Nome");
                System.out.println("2. Telefone");
                System.out.println("3. E-mail");
                System.out.println("4. Endereço");
                System.out.println("5. Mensalidade");
                System.out.println("6. Login");
                System.out.println("7. Senha");
            } else if (contaAlvo.getTipo_conta().equals("funcionario") || contaAlvo.getTipo_conta().equals("admin")) {
                System.out.println("1. Nome");
                System.out.println("2. Telefone");
                System.out.println("3. E-mail");
                System.out.println("4. Endereço");
                System.out.println("5. Salário");
                System.out.println("6. Login");
                System.out.println("7. Senha");
                System.out.println("8. Início expediente");
                System.out.println("9. Fim expediente");
            }
            System.out.print("Escolha a opção (número): ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Novo Nome: ");
                    contaAlvo.setNome(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Novo Telefone: ");
                    contaAlvo.setTelefone(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Novo E-mail: ");
                    contaAlvo.setE_mail(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Novo Endereço: ");
                    contaAlvo.setEndereco(scanner.nextLine());
                    break;
                case 5:
                    if (contaAlvo.getTipo_conta().equals("cliente")) {
                        System.out.print("Nova Mensalidade: ");
                        contaAlvo.setMensalidade_cliente(Float.parseFloat(scanner.nextLine()));
                    } else {
                        System.out.print("Novo Salário: ");
                        contaAlvo.setSalario_funcionario(Float.parseFloat(scanner.nextLine()));
                    }
                    break;
                case 6:
                    System.out.print("Novo Login: ");
                    contaAlvo.setEndereco(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Nova Senha: ");
                    contaAlvo.setEndereco(scanner.nextLine());
                    break;
                case 8:
                    if (contaAlvo.getTipo_conta().equals("funcionario") || contaAlvo.getTipo_conta().equals("admin")) {
                        System.out.print("Novo Início do Expediente (HH:MM:SS): ");
                        contaAlvo.setInicio_expediente_funcionario(scanner.nextLine());
                    }
                    break;
                case 9:
                    if (contaAlvo.getTipo_conta().equals("funcionario") || contaAlvo.getTipo_conta().equals("admin")) {
                        System.out.print("Novo Fim do Expediente (HH:MM:SS): ");
                        contaAlvo.setFim_expediente_funcionario(scanner.nextLine());
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

            daoFactory.createContaDao().atualizar(contaAlvo);
            System.out.println("Conta atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar a conta: " + e.getMessage());
        }
    }

    public static void criarNovoEquipamento() {
        equipamento novoEquipamento = new equipamento();

        System.out.print("Nome do Equipamento: ");
        novoEquipamento.setNome(scanner.nextLine());
        System.out.print("Tipo de Equipamento: ");
        novoEquipamento.setTipo(scanner.nextLine());
        System.out.print("Status do Equipamento (true/false): ");
        novoEquipamento.setStatus(Boolean.parseBoolean(scanner.nextLine()));

        daoFactory.createEquipamentoDao().inserir(novoEquipamento);
        System.out.println("Novo equipamento criado com sucesso!");
    }

    public static void listarDadosConta() {
        try {
            System.out.print("ID da conta: ");
            int id = scanner.nextInt();

            conta contaEncontrada = daoFactory.createContaDao().procurarPorId(id);

            if (contaEncontrada.getTipo_conta().equals("cliente")){
                System.out.println("=== Dados ===");
                System.out.println("Nome: " + contaEncontrada.getNome());
                System.out.println("Telefone: " + contaEncontrada.getTelefone());
                System.out.println("E-mail: " + contaEncontrada.getE_mail());
                System.out.println("CPF: " + contaEncontrada.getCpf());
                System.out.println("Endereço: " + contaEncontrada.getEndereco());
                System.out.println("Data de Registro: " + contaEncontrada.getData_registro());
                System.out.println("Mensalidade: " + contaEncontrada.getMensalidade_cliente());
            } else {
                System.out.println("=== Dados ===");
                System.out.println("Nome: " + contaEncontrada.getNome());
                System.out.println("Telefone: " + contaEncontrada.getTelefone());
                System.out.println("E-mail: " + contaEncontrada.getE_mail());
                System.out.println("CPF: " + contaEncontrada.getCpf());
                System.out.println("Endereço: " + contaEncontrada.getEndereco());
                System.out.println("Data de Registro: " + contaEncontrada.getData_registro());
                System.out.println("Salário: " + contaEncontrada.getSalario_funcionario());
                System.out.println("Inicio expediente: " + contaEncontrada.getInicio_expediente_funcionario());
                System.out.println("Fim expediente: " + contaEncontrada.getFim_expediente_funcionario());
            }
        } catch (Exception e) {
            System.out.println("Conta não encontrada no banco de dados.");
        }
    }

    public static void listarTodasAsContas() {
        List<conta> lista = daoFactory.createContaDao().procurarTodos();

        for (int i = 0; i < lista.size(); i++){
            conta contaLoop = daoFactory.createContaDao().procurarPorId(lista.get(i).getId());

            System.out.println("=== ==Conta "+ i +"== ===");
            if (contaLoop.getTipo_conta().equals("cliente")){
                System.out.println("Nome: " + contaLoop.getNome());
                System.out.println("Telefone: " + contaLoop.getTelefone());
                System.out.println("E-mail: " + contaLoop.getE_mail());
                System.out.println("CPF: " + contaLoop.getCpf());
                System.out.println("Endereço: " + contaLoop.getEndereco());
                System.out.println("Data de Registro: " + contaLoop.getData_registro());
                System.out.println("Mensalidade: " + contaLoop.getMensalidade_cliente());
            } else {
                System.out.println("Nome: " + contaLoop.getNome());
                System.out.println("Telefone: " + contaLoop.getTelefone());
                System.out.println("E-mail: " + contaLoop.getE_mail());
                System.out.println("CPF: " + contaLoop.getCpf());
                System.out.println("Endereço: " + contaLoop.getEndereco());
                System.out.println("Data de Registro: " + contaLoop.getData_registro());
                System.out.println("Salário: " + contaLoop.getSalario_funcionario());
                System.out.println("Inicio expediente: " + contaLoop.getInicio_expediente_funcionario());
                System.out.println("Fim expediente: " + contaLoop.getFim_expediente_funcionario());
            }
        }
    }

    public static void deletarConta() {
        try {
            System.out.print("ID da conta: ");
            int id = Integer.parseInt(scanner.nextLine());

            conta contaEncontrada = daoFactory.createContaDao().procurarPorId(id);

            if (usuarioLogado.getTipo_conta().equals("admin") && contaEncontrada.getTipo_conta().equals("admin")){
                daoFactory.createContaDao().deletarPorId(contaEncontrada);
            } else {
                System.out.println("Operação negada.");
            }

        } catch (Exception e){
            System.out.println("Conta não encontrada no banco de dados.");
        }
    }

    public static void atualizarEquipamento() {
        try {
            System.out.print("ID do Equipamento a ser atualizado: ");
            int id = Integer.parseInt(scanner.nextLine());

            equipamento equipamentoAlvo = daoFactory.createEquipamentoDao().procurarPorId(id);

            if (equipamentoAlvo == null) {
                System.out.println("Equipamento não encontrado.");
                return;
            }

            System.out.println("=== Selecione os dados para modificar ===");
            System.out.println("1. Nome");
            System.out.println("2. Tipo");
            System.out.println("3. Status (true/false)");

            System.out.print("Escolha a opção (número): ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Novo Nome: ");
                    equipamentoAlvo.setNome(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Novo Tipo: ");
                    equipamentoAlvo.setTipo(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Novo Status (true/false): ");
                    equipamentoAlvo.setStatus(Boolean.parseBoolean(scanner.nextLine()));
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

            daoFactory.createEquipamentoDao().atualizar(equipamentoAlvo);
            System.out.println("Equipamento atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o equipamento: " + e.getMessage());
        }
    }

    public static void deletarEquipamento() {
        try {
            System.out.print("ID do Equipamento a ser deletado: ");
            int id = Integer.parseInt(scanner.nextLine());

            equipamento equipamentoAlvo = daoFactory.createEquipamentoDao().procurarPorId(id);

            if (equipamentoAlvo == null) {
                System.out.println("Equipamento não encontrado.");
                return;
            }

            daoFactory.createEquipamentoDao().deletarPorId(equipamentoAlvo);
            System.out.println("Equipamento deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar o equipamento: " + e.getMessage());
        }
    }

    public static void listarDadosEquipamento() {
        try {
            System.out.print("ID do equipamento: ");
            int id = scanner.nextInt();

            equipamento equipamentoEncontrado = daoFactory.createEquipamentoDao().procurarPorId(id);

            System.out.println("Nome: " + equipamentoEncontrado.getNome());
            System.out.println("Tipo: " + equipamentoEncontrado.getTipo());
            System.out.println("Status: " + equipamentoEncontrado.getStatus());
        } catch (Exception e) {
            System.out.println("Equipamento não encontrado no banco de dados.");
        }
    }

    public static void listarTodosEquipamentos() {
        List<equipamento> lista = daoFactory.createEquipamentoDao().procurarTodos();

        for (int i = 0; i < lista.size(); i++){
            equipamento equipamentoLoop = daoFactory.createEquipamentoDao().procurarPorId(lista.get(i).getId());

            System.out.println("=== ==Equipamento "+ i +"== ===");
            System.out.println("Nome: " + equipamentoLoop.getNome());
            System.out.println("Tipo: " + equipamentoLoop.getTipo());
            System.out.println("Status: " + equipamentoLoop.getStatus());

        }
    }

    public static void reservarEquipamento() {
        try {
            System.out.print("ID do equipamento: ");
            int id_equipamento = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            equipamento equipamentoEncontrado = daoFactory.createEquipamentoDao().procurarPorId(id_equipamento);

            System.out.print("Hora de início (HH:MM:SS): ");
            String horaInicioStr = scanner.nextLine();

            System.out.print("Hora de fim (HH:MM:SS): ");
            String horaFimStr = scanner.nextLine();

            System.out.print("Status da reserva (1 para ativo, 0 para inativo): ");
            int status = scanner.nextInt();

            reserva novaReserva = new reserva();
            novaReserva.setHora_inicio(horaInicioStr);
            novaReserva.setHora_fim(horaFimStr);
            novaReserva.setStatus(status);

            daoFactory.createReservaDao().criarReserva(usuarioLogado, novaReserva, equipamentoEncontrado);

            System.out.println("Reserva realizada com sucesso!");

        } catch (Exception e) {
            System.out.println("Não foi possível realizar a reserva: " + e.getMessage());
        }
    }

    public static void editarReserva() {
        try {
            System.out.print("ID da Reserva a ser atualizada: ");
            int id = Integer.parseInt(scanner.nextLine());

            reserva reservaAlvo = daoFactory.createReservaDao().procurarPorId(id);

            if (reservaAlvo == null) {
                System.out.println("Reserva não encontrada.");
                return;
            }

            System.out.println("=== Selecione os dados para modificar ===");
            System.out.println("1. Data de reserva");
            System.out.println("2. Hora de início");
            System.out.println("3. Hora de fim");
            System.out.println("4. Status (1=Ativa, 0=Cancelada)");

            System.out.print("Escolha a opção (número): ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Nova data da reserva (yyyy-MM-dd): ");
                    reservaAlvo.setData_reserva(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Nova hora de início (HH:mm:ss): ");
                    reservaAlvo.setHora_inicio(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Nova hora de fim (HH:mm:ss): ");
                    reservaAlvo.setHora_fim(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Novo Status (1=Ativa, 0=Cancelada): ");
                    reservaAlvo.setStatus(scanner.nextInt());
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

            daoFactory.createReservaDao().editarReserva(reservaAlvo);
            System.out.println("Reserva atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar a reserva: " + e.getMessage());
        }
    }

    public static void listarTodasReservas() {
        List<reserva> lista = daoFactory.createReservaDao().listarTodas();

        for (int i = 0; i < lista.size(); i++) {
            reserva reservaLoop = lista.get(i); // Não é necessário procurar novamente

            System.out.println("=== == Reserva " + i + " == ===");
            System.out.println("ID: " + reservaLoop.getId());
            System.out.println("Data: " + reservaLoop.getData_reserva());
            System.out.println("Hora de Início: " + reservaLoop.getHora_inicio());
            System.out.println("Hora de Fim: " + reservaLoop.getHora_fim());
            System.out.println("Status: " + reservaLoop.getStatus());
        }
    }

}