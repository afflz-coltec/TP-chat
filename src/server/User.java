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
            String service = cliente_in.nextLine();

            switch (service) {
                case "hello":
                    if (this.state.equals("offline")) {
                        boolean die = false;

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
                            state = "logged";

                            response("Hello " + temp_nick + "! Seu ID é: " + id);
                        }
                    }
                    else{
                        response("Você já está logado...");
                    }
                    break;
                case "changenick":
                    break;
                case "onlinenow":
                    response("Estes aqui estão online:"+servidor.onlinow());
                    break;
                case "getnick":
                    break;
                case "privatemsg":
                    break;
                case "globalmsg":
                    String message = cliente_in.nextLine();
                    servidor.global_message(message,this.nick);
                    break;
                case "privatefile":
                    break;
                case "globalfile":
                    break;
                case "byebye":
                    break;
            }
        }
    }

    private synchronized void response(String resp) {
        cliente_out.print("technical:");
        cliente_out.println(resp);
    }
    synchronized void inbox (String msg,String user){
        cliente_out.print("Message from "+user+":");
        cliente_out.println(msg);
    }

}
