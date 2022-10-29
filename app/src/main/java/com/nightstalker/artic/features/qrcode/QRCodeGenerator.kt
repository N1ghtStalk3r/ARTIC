package com.nightstalker.artic.features.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.nightstalker.artic.features.exhibition.domain.Exhibition

class QRCodeGenerator {

    private var counter = 0

    private lateinit var exhibitionText: Exhibition

    fun setBaseForText(exhibition: Exhibition){
        exhibitionText = exhibition
    }

    fun getCounter() = counter



    fun makeImage(text: String): Bitmap {

        val size = 256 //pixels
        val qrCodeContent = text //"WIFI:S:$ssid;T:WPA;P:$password;;"

        val hints = hashMapOf<EncodeHintType, Any>()
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8") // Make the QR code support Cyrillic symbols
        hints.put(EncodeHintType.MARGIN, 1)  // Make the QR code buffer border narrower

        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size, hints)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    fun getTextQRCode(): String{
        var textQRCode : String
        if(counter++ % 2 == 0 )
        {
            textQRCode = "${counter}. ЭПИК и ARTIC представляют: ${exhibitionText.title} "
        } else
        {
            textQRCode = exhibitionText.imageUrl.toString()
        }
        Log.d("ArticLog", "QR-code = $textQRCode")
        return   textQRCode

    }
}