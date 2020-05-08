package io.io;


import org.junit.Test;

import java.io.*;

public class FilterStreamExam {

    @Test
    public void testDataStream() throws IOException {
        File file = new File("C:\\Users\\dou\\Desktop\\data.txt");
        final var fileOutputStream = new FileOutputStream(file, true);
        final var dataOutputSteam = new DataOutputStream(fileOutputStream);
        final var dataInputStream = new DataInputStream(new FileInputStream(file));
        try (fileOutputStream;
             dataOutputSteam;
             dataInputStream) {
            for (int i = 0; i < 10; i++) dataOutputSteam.writeInt(i);
            dataOutputSteam.flush();
            dataInputStream.readInt();
        }
    }
}
