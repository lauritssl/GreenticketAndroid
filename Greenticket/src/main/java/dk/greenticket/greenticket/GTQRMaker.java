package dk.greenticket.greenticket;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by lalan on 11/02/14.
 */
public class GTQRMaker {
    Bitmap qr;

    public GTQRMaker(){
    }

    public Bitmap convertToBitmap(String text, int width, int height){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        qr = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);

        Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        //hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        try {
            BitMatrix qrMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width,height,hints);

            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    qr.setPixel(x, y, qrMatrix.get(x,y) ? Color.BLACK : Color.WHITE);
                }
            }

        } catch (WriterException e) {
            e.printStackTrace();
        }



        return qr;
    }

}
