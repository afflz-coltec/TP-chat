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

            User newbee = new User(cliente, this);

            this.clientes.add(newbee);

            newbee.thd = new Thread(newbee);
            newbee.thd.start();
        }

    }

    public void service_message(String message) {
        for (User cliente : this.clientes) {
            cliente.inbox(message);
        }
    }
    public void user_bye(User bye) {
        
        this.clientes.remove(bye);
        bye.thd.interrupt();
    }

    public void global_message(String message, String user) {
        for (User cliente : this.clientes) {
            if (cliente.state.equals("online"));
            cliente.inbox(message, user);
        }
    }

    public String onlinow() {
        String online = "";
        for (User cliente : this.clientes) {
            System.out.println(cliente.nick);
            online += cliente.nick + "\t";
        }
        return online;
    }

    public void private_message(String message, String user, String snick) {
        for (User cliente : this.clientes) {
            if (cliente.nick.equals(snick)) {
                cliente.inbox(message, user);
                break;
            }
        }
    }
}
