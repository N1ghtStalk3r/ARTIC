package com.nightstalker.artic.core.presentation

import java.util.*

// Время нового события в календаре может быть только в будующем
fun Long.normalizeEventDateTime(): Long =
    if (this < Date().time) Date().time else this