import db.entities.conta;
import db.entities.dao.daoFactory;
import db.entities.equipamento;

import java.sql.Date;
import java.sql.SQLException;
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
            System.out.println("0. Sair");
        } else if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
            System.out.println("1. Criar nova conta");
            System.out.println("2. Criar novo equipamento");
            System.out.println("3. Exibir dados de uma conta");
            System.out.println("4. Listar todas as contas");
            System.out.println("5. Deletar uma conta");
            System.out.println("6. Atualizar dados de uma conta");
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
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    criarNovoEquipamento();
                }
                break;
            case 3:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    listarDadosConta();
                }
                break;
            case 4:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    listarTodasAsContas();
                }
                break;
            case 5:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    deletarConta();
                }
                break;
            case 6:
                if (usuarioLogado.getTipo_conta().equals("funcionario") || usuarioLogado.getTipo_conta().equals("admin")) {
                    atualizarConta();
                }
                break;
            case 0:
                usuarioLogado = null;
                System.out.println("Sessão encerrada.");
            default:
                System.out.println("Opção inválida. Tente novamente.");
                break;
        }
    }

    public static void verMeusDados() {
        if (usuarioLogado.getTipo_conta().equals("cliente")){
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
            int id = scanner.nextInt();

            conta contaAlvo = daoFactory.createContaDao().procurarPorId(id);

            if (contaAlvo.getTipo_conta().equals("cliente")){
                System.out.println("=== Selecione os dados para modificar.");
                System.out.println("1. Nome");
                System.out.println("2. Telefone");
                System.out.println("3. E-mail");
                System.out.println("4. Endereço");
                System.out.println("5. Mensalidade");

                int val = scanner.nextInt();
            } else {
                System.out.println("=== Selecione os dados para modificar.");
                System.out.println("1. Nome");
                System.out.println("2. Telefone");
                System.out.println("3. E-mail");
                System.out.println("4. Endereço");
                System.out.println("5. Salário");
                System.out.println("6. Inicio expediente");
                System.out.println("7. Fim expediente");

                int val = scanner.nextInt();
            }



        } catch (Exception e){
            System.out.println("Conta não encontrada no banco de dados.");
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
}
