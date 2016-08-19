/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.negocios;

import client.UI.mainUI;
import java.io.InputStream;
import java.util.Scanner;

public class receiver implements Runnable {

    private InputStream servidor;
    mainUI ui;
    Scanner s;

    public receiver(InputStream servidor) {
        this.servidor = servidor;
        s = new Scanner(this.servidor);
        System.out.println("Open RX");
    }

    public void setUI(mainUI u) {
        ui = u;
    }

    @Override
    public void run() {
        // recebe msgs do servidor e imprime na tela

        while (true) {
            if (s.hasNextLine()) {
                String type = s.nextLine();
                System.out.println(type);
                switch (type) {
                    case "message":
                        ui.message_send(s.nextLine());
                        break;
                    case "onlinow":
                        ui.onlinow_send(s.nextLine());
                        break;
                    case "technical":
                        System.out.println(s.nextLine());
                        break;

                }

            }
        }
    }
}
