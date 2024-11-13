public class Conta {
    private static int contador = 1000;
    private int numero;
    private double saldo;
    private Usuario usuario;
    private String tipo;

    public Conta(Usuario usuario, double saldoInicial, String tipo) {
        this.numero = contador++;
        this.saldo = saldoInicial;
        this.usuario = usuario;
        this.tipo = tipo;
    }

    public int getNumero() { return numero; }
    public double getSaldo() { return saldo; }
    public Usuario getUsuario() { return usuario; }
    public String getTipo() { return tipo; }

    public void depositar(double quantia) {
        saldo += quantia;
    }

    public boolean sacar(double quantia) {
        if (saldo >= quantia) {
            saldo -= quantia;
            return true;
        } else {
            return false;
        }
    }

    public boolean transferir(Conta destino, double quantia) throws IllegalArgumentException {
        if (this == destino) {
            throw new IllegalArgumentException("Transferência não pode ser feita para a mesma conta.");
        }
        if (saldo >= quantia) {
            saldo -= quantia;
            destino.depositar(quantia);
            return true;
        } else {
            return false;
        }
    }
}
