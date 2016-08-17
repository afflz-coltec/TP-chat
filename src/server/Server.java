package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // inicia o servidor
        new Server().executa();
    }

    private int porta;
    private List<User> clientes;

    public Server() {
        System.out.println("Digite a porta em que deseja manter este servidor:");
        this.porta = scanner.nextInt();
        this.clientes = new ArrayList<>();
    }

    public void executa() throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta " + this.porta + " aberta!");

        while (true) {
            // aceita um cliente
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente "
                    + cliente.getInetAddress().getHostAddress()
            );
            
            User newbee = new User(cliente);

            this.clientes.add(newbee);

            new Thread(newbee).start();
        }

    }

//    public void distribuiMensagem(String msg) {
//        // envia msg para todo mundo
//        for (PrintStream cliente : this.clientes) {
//            cliente.println(msg);
//        }
//    }
}
