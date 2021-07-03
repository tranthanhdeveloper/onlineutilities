package net.onlineutilities.utils;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class SvgUtil {

    public static File convertTo(MultipartFile file, String type) throws IOException, TranscoderException {
        File tempSvgFile = Files.createTempFile("svg2png-", ".svg").toFile();
        OutputStream outputStream = new FileOutputStream(tempSvgFile);
        IOUtils.copy(file.getInputStream(), outputStream);
        outputStream.flush();


        String svgUriInput = tempSvgFile.toURI().toURL().toString();
        TranscoderInput transcoderInput = new TranscoderInput(svgUriInput);

        File tempPngFile = Files.createTempFile("svg2png-", "."+type).toFile();
        OutputStream pngOstream = new FileOutputStream(tempPngFile);

        TranscoderOutput transcoderOutput = new TranscoderOutput(pngOstream);
        ImageTranscoder imageTranscoder = null;
        if (type.equalsIgnoreCase("png")){
            imageTranscoder = new PNGTranscoder();
        }else if (type.equalsIgnoreCase("jpeg")){
            imageTranscoder = new JPEGTranscoder();
        }else {
            imageTranscoder = new TIFFTranscoder();
        }
        imageTranscoder.transcode(transcoderInput, transcoderOutput);
        pngOstream.flush();
        pngOstream.close();

        outputStream.close();

        Files.deleteIfExists(tempSvgFile.toPath());
        return tempPngFile;
    }
}
