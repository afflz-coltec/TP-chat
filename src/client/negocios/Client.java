/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.negocios;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    public static void main(String[] args)
            throws UnknownHostException, IOException {
        // dispara cliente
        new Client("127.0.0.1", 12345).run();

    }

    private String host;
    private int porta;
    public receiver rx;
    public transmitter tx;

    public Client(String host, int porta) {
        this.host = host;
        this.porta = porta;

    }

    /**
     *
     */
    @Override
    public void run() {

        Socket cliente = null;
        try {
            cliente = new Socket(this.host, this.porta);
            System.out.println("O cliente se conectou ao servidor!");
            tx = new transmitter(cliente.getOutputStream());
            rx = new receiver(cliente.getInputStream());
        } catch (IOException ex) {
        }


        // lê msgs do teclado e manda pro servidor
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = null;

    }
}
