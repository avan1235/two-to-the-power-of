package ml.dev.kotlin.poweroftwo.ui.theme

import androidx.compose.ui.graphics.Color

val Surface = Color(0xFFF9F5EB)
val Background = Color(0xFFBBADA0)
val BackgroundEmpty = Color(0xFFCCC0B3)
val Background2 = Color(0xFFEEE4DA)
val Background4 = Color(0xFFEDE0C8)
val Background8 = Color(0xFFF2B179)
val Background16 = Color(0xFFF59563)
val Background32 = Color(0xFFF67C5F)
val Background64 = Color(0xFFF65E3B)
val Background128 = Color(0xFFEDCF72)
val Background256 = Color(0xFFEDCC61)
val Background512 = Color(0xFFEDC850)
val Background1024 = Color(0xFFEDC53F)
val Background2048 = Color(0xFFEDC22E)
val Background4096 = Color(0xFF3C3A32)
val Background8192 = Color(0xFF3C3A32)

val FontDark = Color(0xFF222222)
val FontLight = Color(0xFFF9F6F2)

inline val Int.backgroundColor: Color
    get() = when (this) {
        2 -> Background2
        4 -> Background4
        8 -> Background8
        16 -> Background16
        32 -> Background32
        64 -> Background64
        128 -> Background128
        256 -> Background256
        512 -> Background512
        1024 -> Background1024
        2048 -> Background2048
        4096 -> Background4096
        else -> Background8192
    }

inline val Int.fontColor: Color get() = if (this <= 4) FontDark else FontLight