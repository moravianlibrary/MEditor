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

/**
 * @author: Martin Rumanek
 * @version: 24.1.13
 * <p/>
 * See {@linktourl http://stackoverflow.com/questions/2709508/how-to-learn-wav-duration-in-java-media-frame-work}
 */
public class AudioUtils {
    public static Integer getLength(String path) {
        File inputFile = new File(path);
        AudioInputStream stream;

        try {
            stream = AudioSystem.getAudioInputStream(inputFile);

            AudioFormat format = stream.getFormat();
            if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format
                        .getSampleRate(), format.getSampleSizeInBits() * 2, format
                        .getChannels(), format.getFrameSize() * 2, format
                        .getFrameRate(), true); // big endian
                stream = AudioSystem.getAudioInputStream(format, stream);
            }
            DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),
                    ((int) stream.getFrameLength() * format.getFrameSize()));
            Clip clip = null;
            clip = (Clip) AudioSystem.getLine(info);
            clip.close();
            return (int) (clip.getBufferSize()
                    / (clip.getFormat().getFrameSize() * clip.getFormat()
                    .getFrameRate()));


        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String getTwoDigitLength(String path) {
        int seconds = getLength(path);
        int minutes = seconds / 3600;
        seconds = seconds % 60;

        return new String(minutes + ":" + seconds);
    }
}
