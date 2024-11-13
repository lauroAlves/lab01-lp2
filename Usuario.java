public class Usuario {
    private String nome;
    private String cpf;
    private String senha;

    public Usuario(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getSenha() { return senha; }

    public boolean validarCpf() {
        return cpf.matches("\\d{11}");  // Validação simples do CPF
    }
}
