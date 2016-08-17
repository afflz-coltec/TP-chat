/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afflz
 */
public class User implements Runnable {

    byte[] service = new byte[1];
    byte[] data_size = new byte[2];
    byte[] data_content;
    byte[] checksum = new byte[2];

    int decodedService;
    int decodedData_size = 0;
    String decodeddata_content;
    int decodedchecksum;

    InputStream IS;

    User(Socket newbee) throws IOException {
        IS = newbee.getInputStream();
    }

    @Override
    public void run() {
        while (true) {
            try {
                IS.read(service, 0, 1);
                refresh(0);
                IS.read(data_size, 0, 2);
                refresh(1);
                IS.read(data_content, 0, decodedData_size);
                refresh(2);
                IS.read(checksum, 0, 2);
                refresh(3);

            } catch (IOException ex) {
            }
        }
    }

    private void refresh(int stage) {
        switch (stage) {
            case 0:
                break;
            case 1:
                data_content = new byte[decodedData_size];
                break;
            case 2:
                break;
            case 3:
                break;

        }

    }

}
