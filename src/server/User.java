/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author afflz
 */
public class User implements Runnable {

    String message;
    boolean die;

    Server servidor;

    InputStream IS;
    OutputStream OS;

    Scanner cliente_in;
    PrintStream cliente_out;
    String state = "offline";
    String nick;
    static List<String> global_nicks = new ArrayList<>();
    static int global_id = 0;
    int id;
    Thread thd;
    private boolean bye_bye = true;

    User(Socket newbee, Server conectado) throws IOException {
        IS = newbee.getInputStream();
        OS = newbee.getOutputStream();
        cliente_out = new PrintStream(OS);
        cliente_in = new Scanner(IS);
        servidor = conectado;
    }

    @Override
    public void run() {
        while (true) {
            String service = "";
            
            if (cliente_in.hasNextLine()) {
                service = cliente_in.nextLine();
            }
            System.out.println(service);

            switch (service) {
                case "hello":
                    if (this.state.equals("offline")) {
                        die = false;

                        response("Hello aceito");
                        String temp_nick = cliente_in.nextLine();
                        for (String gn : User.global_nicks) {
                            if (temp_nick.equals(gn)) {
                                response("deny");
                                die = true;
                                break;
                            }
                        }
                        if (!die) {
                            nick = temp_nick;
                            global_nicks.add(nick);
                            global_id++;
                            id = global_id;
                            state = "online";

                            response("Hello " + temp_nick + "! Seu ID é: " + id);
                        }
                    } else {
                        response("Você já está logado...");
                    }
                    break;
                case "changenick":
                    if (this.state.equals("online")) {
                        die = false;

                        String temp_nick = cliente_in.nextLine();
                        String exnick = this.nick;
                        for (String gn : User.global_nicks) {
                            if (temp_nick.equals(gn)) {
                                response("deny");
                                die = true;
                                break;
                            }
                        }
                        if (!die) {
                            nick = temp_nick;
                            global_nicks.add(nick);
                            global_id++;
                            id = global_id;
                            state = "online";

                            response("Seu novo nick é " + temp_nick);
                            servidor.service_message(exnick + " agora atende por " + this.nick);
                        }
                    }
                    break;
                case "onlinow":
                    if (this.state.equals("online")) {
                        onlinow(servidor.onlinow());
                    }
                    break;
                case "getnick":
                    if (this.state.equals("online")) {
                    }
                    break;
                case "privatemsg":
                    if (this.state.equals("online")) {
                        String recipient = cliente_in.nextLine();
                        message = cliente_in.nextLine();
                        servidor.private_message(message, this.nick, recipient);
                    }
                    break;
                case "globalmsg":
                    if (this.state.equals("online")) {
                        message = cliente_in.nextLine();
                        System.out.println(message);
                        servidor.global_message(message, this.nick);
                    }
                    break;
                case "privatefile":
                    if (this.state.equals("online")) {
                    }
                    break;
                case "globalfile":
                    if (this.state.equals("online")) {
                    }
                    break;
                case "byebye":
                    if (this.state.equals("online")) {
                        this.state = "offline";
                        this.bye_bye = false;
                        servidor.user_bye(this);
                        this.thd.interrupt();
                        User.global_nicks.remove(this.nick);
                    }
                    break;
            }
        }
    }

    private synchronized void response(String resp) {
        cliente_out.println("technical");
        cliente_out.println(resp);
    }

    synchronized void inbox(String msg, String user) {
        cliente_out.println("message");
        cliente_out.print("Message from " + user + ":");
        cliente_out.println(msg);
    }

    synchronized void inbox(String msg) {
        cliente_out.println(msg);
    }

    private void onlinow(String onlinow) {
        cliente_out.println("onlinow");
        cliente_out.println(onlinow);
    }
}
