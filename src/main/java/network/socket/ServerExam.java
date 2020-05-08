package network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerExam {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8000);
        for (; ; ) {
            Socket conn = serverSocket.accept();
            handleConn(conn);
        }
    }


    public static void handleConn(Socket conn) throws IOException, InterruptedException {
        if (conn == null) return;
        var os = conn.getOutputStream();
        for (; ; ) {
            var date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(date);
            os.write(time.getBytes());
            os.flush();
            System.out.println(time);
        }
    }
}
