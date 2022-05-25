/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qrcodegen;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ivona
 */
public class QRCodeGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            String urlPath = "C:\\InSoft\\INV_NS_00015-1286_vurl.txt";
            String size = "400";

            if (args.length > 0) {
                urlPath = args[0];
            }
            if (args.length > 1) {
                size = args[1];
            }

            int sizeInt = Integer.parseInt(size);

            String qrCodeData = returnURLFromFile(urlPath);

            System.out.println(qrCodeData);

            String filePath = urlPath.replace("vurl.txt", "qrcode.bmp");
            String charset = "UTF-8";

            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            hints.put(EncodeHintType.MARGIN, -1);

            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, sizeInt, sizeInt, hints);
            File file = new File(filePath);
            MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), file.toPath());

            System.out.println("QR Code has been generated successfully at the location " + filePath);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String returnURLFromFile(String urlPath) {
        File file = new File(urlPath);
        String name = "";
        StringBuilder data = new StringBuilder();
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                name = input.nextLine();
                data.append(name);
            }
        } catch (Exception e) {
            System.out.println("File not found or is empty");
            return "";
        }
        return data.toString().trim();
    }

}
