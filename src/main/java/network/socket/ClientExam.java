package network.socket;


import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientExam {
    public static void main(String[] args) throws IOException {
        Socket sk = new Socket("localhost", 8000);
        InputStream is = sk.getInputStream();
        for (; ; ) {
            int n = is.available();
            if (n == 0) continue;
            byte[] bytes = is.readNBytes(n);
            String s = new String(bytes);
            System.out.println(s);
        }

    }


    @Test
    public void testSocket() throws IOException {
        Socket sk = new Socket("golang.org", 80);
        InputStream is = sk.getInputStream();
        int n = is.available();
        byte[] bytes = is.readNBytes(n);
        System.out.println(new String(bytes));
        is.close();
        sk.close();
    }
}
