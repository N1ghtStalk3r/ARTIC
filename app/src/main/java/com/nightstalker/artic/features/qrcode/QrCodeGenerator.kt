package com.nightstalker.artic.features.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition

class QrCodeGenerator {

    fun makeImage(text: String): Bitmap {
        val size = QrConstants.QR_IMAGE_SIZE
        val hints = hashMapOf<EncodeHintType, Any>()
        hints[EncodeHintType.CHARACTER_SET] =
            QrConstants.QR_CHARACTER_SET // Make the QR code support Cyrillic symbols
        hints[EncodeHintType.MARGIN] =
            QrConstants.QR_MARGIN  // Make the QR code buffer border narrower

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
}