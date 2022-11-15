package com.nightstalker.artic.features.qrcode

/**
 * Константы для параметриции QR-code функционала
 *
 * @constructor Create empty Api constants
 */
object QrConstants {
    const val QR_IMAGE_SIZE = 256
    const val QR_CHARACTER_SET = "UTF-8"  // Make the QR code support Cyrillic symbols
    const val QR_MARGIN = 1  // Make the QR code buffer border narrower

    const val QR_BORDER_STROKE_WIDTH = 10
}