import java.util.Scanner;

public class BancoView {
    private Scanner scanner;

    public BancoView() {
        scanner = new Scanner(System.in);
    }

    public int mostrarMenuPrincipal() {
        System.out.println("1. Cadastrar Usuário");
        System.out.println("2. Fazer Login");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }

    public int mostrarMenuUsuario() {
        System.out.println("1. Consultar Saldo");
        System.out.println("2. Depositar");
        System.out.println("3. Sacar");
        System.out.println("4. Transferir");
        System.out.println("5. Criar Conta");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }

    public Usuario lerDadosUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.next();
        String cpf = "";
        boolean cpfValido = false;
        while (!cpfValido) {
            System.out.print("CPF: ");
            cpf = scanner.next();
            if (cpf.matches("\\d{11}")) {
                cpfValido = true;
            } else {
                System.out.println("CPF inválido. Tente novamente.");
            }
        }
        System.out.print("Senha: ");
        String senha = scanner.next();
        return new Usuario(nome, cpf, senha);
    }

    public double lerQuantia(String operacao) {
        System.out.print("Quantia para " + operacao + ": ");
        return scanner.nextDouble();
    }

    public int lerNumeroConta(String operacao) {
        System.out.print("Número da conta " + operacao + ": ");
        return scanner.nextInt();
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void mostrarSaldo(double saldo) {
        System.out.println("Saldo atual: " + saldo);
    }

    public String lerTipoConta() {
        System.out.print("Tipo da conta (corrente/poupanca): ");
        return scanner.next();
    }

    public boolean perguntarSeContinuar() {
        System.out.println("CPF errado. Deseja continuar? (1 - Sim, 0 - Não)");
        int opcao = scanner.nextInt();
        return opcao == 1;
    }
}
