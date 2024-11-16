
import java.util.Scanner;

public class BancoController {
    private Banco banco;
    private BancoView view;
    private Usuario usuarioLogado;
    private Scanner scanner;

    public BancoController(Banco banco, BancoView view) {
        this.banco = banco;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao;
        do {
            opcao = view.mostrarMenuPrincipal();
            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    fazerLogin();
                    break;
                case 0:
                    view.mostrarMensagem("Saindo...");
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarUsuario() {
        Usuario usuario = view.lerDadosUsuario();
        if (banco.adicionarUsuario(usuario)) {
            view.mostrarMensagem("Usuário cadastrado com sucesso!");
        } else {
            view.mostrarMensagem("Erro: Já existe um usuário cadastrado com este CPF.");
        }
    }


    private void fazerLogin() {
        System.out.print("CPF: ");
        String cpf = scanner.next();
        if (!validadorCpf(cpf)) {
            view.mostrarMensagem("CPF errado");
            System.out.print("Deseja tentar novamente? (S/N): ");
            char escolha = scanner.next().charAt(0);
            if (escolha == 'S' || escolha == 's') {
                fazerLogin();
            } else {
                view.mostrarMensagem("Saindo...");
            }
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.next();
        usuarioLogado = banco.autenticarUsuario(cpf, senha);
        if (usuarioLogado != null) {
            view.mostrarMensagem("Login bem-sucedido!");
            menuUsuario();
        } else {
            view.mostrarMensagem("CPF ou senha inválidos!");
        }
    }

    private boolean validadorCpf(String cpf) {

        return cpf.matches("\\d{11}");
    }

    private void menuUsuario() {
        int opcao;
        do {
            opcao = view.mostrarMenuUsuario();
            switch (opcao) {
                case 1:
                    consultarSaldo();
                    break;
                case 2:
                    depositar();
                    break;
                case 3:
                    sacar();
                    break;
                case 4:
                    transferir();
                    break;
                case 5:
                    criarConta();
                    break;
                case 0:
                    view.mostrarMensagem("Saindo...");
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void consultarSaldo() {
        try {
            int numeroConta = view.lerNumeroConta("para consulta de saldo");
            Conta conta = banco.buscarContaPorNumero(numeroConta);
            if (conta != null && conta.getUsuario().equals(usuarioLogado)) {
                view.mostrarSaldo(conta.getSaldo());
            } else {
                view.mostrarMensagem("Conta não encontrada ou não pertence ao usuário logado.");
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao consultar saldo: " + e.getMessage());
        }
    }

    private void depositar() {
        try {
            int numeroConta = this.view.lerNumeroConta("para depósito");
            Conta conta = this.banco.buscarContaPorNumero(numeroConta);
            if (conta != null && conta.getUsuario().equals(this.usuarioLogado)) {
                double quantia = this.view.lerQuantia("depósito");
                if (quantia > 0) {
                    conta.depositar(quantia);
                    this.view.mostrarMensagem("Depósito realizado com sucesso!");
                } else {
                    this.view.mostrarMensagem("Valor de depósito inválido. Não é possível depositar valores negativos ou zero.");
                }
            } else {
                this.view.mostrarMensagem("Conta não encontrada ou não pertence ao usuário logado.");
            }
        } catch (Exception var5) {
            this.view.mostrarMensagem("Erro ao realizar depósito: " + var5.getMessage());
        }
    }


    private void sacar() {
        try {
            int numeroConta = view.lerNumeroConta("para saque");
            Conta conta = banco.buscarContaPorNumero(numeroConta);
            if (conta != null && conta.getUsuario().equals(usuarioLogado)) {
                double quantia = view.lerQuantia("saque");
                if (quantia > 0) {
                    if (conta.sacar(quantia)) {
                        view.mostrarMensagem("Saque realizado com sucesso!");
                    } else {
                        view.mostrarMensagem("Saldo insuficiente!");
                    }
                } else {
                    view.mostrarMensagem("Valor de saque inválido. Não é possível sacar valores negativos ou zero.");
                }
            } else {
                view.mostrarMensagem("Conta não encontrada ou não pertence ao usuário logado.");
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao realizar saque: " + e.getMessage());
        }
    }


    private void transferir() {
        try {
            int numeroContaOrigem = view.lerNumeroConta("de origem para transferência");
            Conta contaOrigem = banco.buscarContaPorNumero(numeroContaOrigem);

            if (contaOrigem != null) {
                int numeroContaDestino = view.lerNumeroConta("de destino para transferência");
                Conta contaDestino = banco.buscarContaPorNumero(numeroContaDestino);

                if (contaDestino != null) {
                    if (contaOrigem.getUsuario().equals(usuarioLogado)) {
                        double quantia = view.lerQuantia("transferência");

                        if (quantia > 0) {
                            if (contaOrigem.transferir(contaDestino, quantia)) {
                                view.mostrarMensagem("Transferência realizada com sucesso!");
                            } else {
                                view.mostrarMensagem("Saldo insuficiente!");
                            }
                        } else {
                            view.mostrarMensagem("Valor de transferência inválido. Não é possível transferir valores negativos ou zero.");
                        }
                    } else {
                        view.mostrarMensagem("A conta de origem não pertence ao usuário logado.");
                    }
                } else {
                    view.mostrarMensagem("Conta de destino não encontrada.");
                }
            } else {
                view.mostrarMensagem("Conta de origem não encontrada.");
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao realizar transferência: " + e.getMessage());
        }
    }


    private void criarConta() {
        try {
            if (banco.existeContaComCpf(usuarioLogado.getCpf())) {
                view.mostrarMensagem("Já existe uma conta cadastrada com este CPF.");
                return;
            }

            double saldoInicial = view.lerQuantia("saldo inicial");
            String tipo = view.lerTipoConta();
            Conta conta = new Conta(usuarioLogado, saldoInicial, tipo);
            banco.adicionarConta(conta);
            view.mostrarMensagem("Conta criada com sucesso! Número da conta: " + conta.getNumero());
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao criar conta: " + e.getMessage());
        }
    }

}
