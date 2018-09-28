/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webcamserver;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import javax.imageio.ImageIO;

/**
 *
 * @author Jasmin Rose
 */
public class WebcamServer {
    
    static Socket s;
    static ServerSocket ss;
    static BufferedReader br;
    static String string;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Server Listening......");
        try {
            ss = new ServerSocket(4444);
            while(true) {
                s = ss.accept();
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                string = br.readLine();
                if(string.equals("0.0")) {
                    Webcam webcam = Webcam.getDefault();
                    webcam.setViewSize(new Dimension(640, 480));
                    webcam.open();
                    Thread.sleep(1000);
                    String fileName = String.format("picture-%d.png", Calendar.getInstance().getTimeInMillis());
                    ImageIO.write(webcam.getImage(), "PNG", new File(fileName));
                    System.out.println("Picture captured");
                    webcam.close();       
                }
            }
        } catch (WebcamException | IOException e) {
        }
    }
    
}