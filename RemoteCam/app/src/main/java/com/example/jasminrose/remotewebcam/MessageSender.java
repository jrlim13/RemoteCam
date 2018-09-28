package com.example.jasminrose.remotewebcam;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String, Void, String> {

    private Socket s;
    private PrintWriter pw;

    @Override
    protected String doInBackground(String... strings) {

        String value = strings[0];

        try {
            s = new Socket("192.168.43.110", 4444);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(value);
            pw.flush();
            pw.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
