package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {
    private final String dir;
    private final String prefix;
    private final boolean append;

    private BufferedWriter intWriter;
    private BufferedWriter floatWriter;
    private BufferedWriter strWriter;

    public ResultWriter(String dir, String prefix, boolean append) {
        this.dir = dir;
        this.prefix = prefix;
        this.append = append;
    }

    private BufferedWriter openWriter(String name) throws IOException {
        File file = new File(dir, name);
        file.getParentFile().mkdirs();
        return new BufferedWriter(new FileWriter(file, append));
    }

    public void writeInteger(String val) throws IOException {
        if (intWriter == null) {
            intWriter = openWriter(prefix + "integers.txt");
        }
        intWriter.write(val);
        intWriter.newLine();
    }

    public void writeFloat(String val) throws IOException {
        if (floatWriter == null) {
            floatWriter = openWriter(prefix + "floats.txt");
        }
        floatWriter.write(val);
        floatWriter.newLine();
    }

    public void writeString(String val) throws IOException {
        if (strWriter == null) {
            strWriter = openWriter(prefix + "strings.txt");
        }
        strWriter.write(val);
        strWriter.newLine();
    }

    public void closeAll() throws IOException {
        if (intWriter != null) intWriter.close();
        if (floatWriter != null) floatWriter.close();
        if (strWriter != null) strWriter.close();
    }
}
