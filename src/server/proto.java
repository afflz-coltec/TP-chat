/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author afflz
 */
public class proto {

    byte[] service = new byte[1];
    byte[] data_size = new byte[2];
    byte[] data_content;
    byte[] checksum = new byte[2];

    void checksum() {
        
        char s = 0;
        for (byte b : service) {
            s += b;
        }
        for (byte b : data_size) {
            s += b;
        }
        for (byte b : data_content) {
            s += b;
        }
    }    

}
