package com.nightstalker.artic.features.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition

/**
 * Генератор QR-кодов
 *
 * @author Maxim Zimin
 */
class QrCodeGenerator {

    fun makeImage(text: String): Bitmap {
        val size = 256
        val hints = hashMapOf<EncodeHintType, Any>()
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8" // Make the QR code support Cyrillic symbols
        hints[EncodeHintType.MARGIN] = 1  // Make the QR code buffer border narrower

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