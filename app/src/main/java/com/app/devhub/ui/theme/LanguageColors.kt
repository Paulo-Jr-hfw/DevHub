package com.app.devhub.ui.theme

import androidx.compose.ui.graphics.Color


object LanguageColors {
    fun getLanguageColor(language: String?): Color {
        return when (language?.lowercase()) {
            "kotlin" -> Color(0xFF7F52FF)
            "java" -> Color(0xFFB07219)
            "javascript", "js" -> Color(0xFFF1E05A)
            "typescript", "ts" -> Color(0xFF3178C6)
            "python" -> Color(0xFF3572A5)
            "html" -> Color(0xFFE34C26)
            "css" -> Color(0xFF563D7C)
            "swift" -> Color(0xFFF05138)
            else -> Color.Gray
        }
    }
}