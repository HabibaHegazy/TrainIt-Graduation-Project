package com.example.ipingpong.shared.datasource.local;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SendMessage extends AsyncTask<String, Void, Void> {

    private Exception exception;

    @Override
    protected Void doInBackground(String... strings) {

        try {
            try {
                Socket socket = new Socket("192.168.1.2", 8090);
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.println(strings[0]);
                printWriter.print(strings[0]);
                printWriter.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (Exception e){
            this.exception = e;
            return null;
        }
        return null;

    }
}

