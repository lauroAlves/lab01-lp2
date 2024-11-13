import java.util.ArrayList;

public class Banco {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Conta> contas = new ArrayList<>();

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public Usuario autenticarUsuario(String cpf, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }

    public Conta buscarContaPorNumero(int numero) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }

    public ArrayList<Conta> buscarContasPorUsuario(Usuario usuario) {
        ArrayList<Conta> resultado = new ArrayList<>();
        for (Conta conta : contas) {
            if (conta.getUsuario().equals(usuario)) {
                resultado.add(conta);
            }
        }
        return resultado;
    }

    public boolean validarCpf(String cpf) {
        // Lógica simples para validação do CPF (não sendo a validação completa, apenas exemplo)
        return cpf.matches("\\d{11}");
    }
}
