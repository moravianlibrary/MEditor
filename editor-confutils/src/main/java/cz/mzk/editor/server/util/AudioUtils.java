package cz.mzk.editor.server.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * @author: Martin Rumanek
 * @version: 24.1.13
 *
 * Java sound api require soundcard. External utility is used. (package sox)
 */
public class AudioUtils {
    public static Double getLength(String path) throws IOException {
        File inputFile = new File(path);
        Double length = null;
        if (inputFile.isFile()) {

            try {
                Process proces = Runtime.getRuntime().exec("soxi -D " + inputFile.getAbsoluteFile());
                char[] buf = new char[10];
                Reader r = new InputStreamReader(proces.getInputStream(), "UTF-8");
                r.read(buf);
                length = Double.parseDouble(new String(buf));

            } catch (UnsupportedEncodingException e) {
                throw new IOException(e);
            } catch (NumberFormatException e) {
                throw new IOException(e);
            }

            return length;
        }


        return null;

    }

    public static String getLengthDigit(String path) throws IOException {
        int seconds = (int) Math.round(getLength(path));
        if (seconds >= 3600) {
            int hours = seconds / 3600;
            int minutes = seconds % 3600;
            seconds = minutes % 60;
            return new String(minutes + ":" + seconds);
        } else {
            int minutes = seconds / 60;
            seconds = seconds % 60;
            return new String(minutes + ":" + seconds);
        }
    }
}
