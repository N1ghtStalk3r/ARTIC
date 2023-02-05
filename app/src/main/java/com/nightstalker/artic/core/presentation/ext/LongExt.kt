package com.nightstalker.artic.core.presentation.ext

import java.util.Date

// Время нового события в календаре может быть только в будующем
fun Long.normalizeEventDateTime(): Long =
    if (this < Date().time) Date().time else this