package com.nightstalker.artic.features.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

object QrCodeGenerator {

    fun makeImage(text: String): Bitmap {
        val size = QR_IMAGE_SIZE
        val hints = hashMapOf<EncodeHintType, Any>()
        hints[EncodeHintType.CHARACTER_SET] =
            QR_CHARACTER_SET // Make the QR code support Cyrillic symbols
        hints[EncodeHintType.MARGIN] =
            QR_MARGIN  // Make the QR code buffer border narrower

        Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).run {
            if (text.isNotEmpty()) {
                val bits = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints)
                for (x in 0 until size) {
                    for (y in 0 until size) {
                        this.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            }
            return this
        }
    }


    private const val QR_IMAGE_SIZE = 256
    private const val QR_CHARACTER_SET = "UTF-8"  // Make the QR code support Cyrillic symbols
    private const val QR_MARGIN = 1  // Make the QR code buffer border narrower
    const val QR_BORDER_STROKE_WIDTH = 10
}