/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.negocios;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class transmitter implements Runnable {

    private OutputStream servidor;
    PrintStream s;
    String cache;

    public transmitter(OutputStream servidor) {
        this.servidor = servidor;
        s = new PrintStream(this.servidor);
        System.out.println("OPEN TX");
        s.println("hello");
        s.println("itme");
    }

    public synchronized void send(String msg) {
        // recebe msgs do servidor e imprime na tela
        cache = msg;
        notifyAll();

    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                wait();
                if (!cache.equals("")) {
                    s.println(cache);
                    cache = "";
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(transmitter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
