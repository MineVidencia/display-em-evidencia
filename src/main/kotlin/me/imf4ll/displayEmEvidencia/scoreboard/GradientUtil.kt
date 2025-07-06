package me.imf4ll.displayEmEvidencia.scoreboard

import net.md_5.bungee.api.ChatColor
import java.awt.Color

object GradientUtil {

    fun createGradient(text: String, startHex: String, endHex: String): String {
        val start = Color.decode(startHex)
        val end = Color.decode(endHex)
        val builder = StringBuilder()

        for (i in text.indices) {
            val ratio = i.toDouble() / (text.length - 1).coerceAtLeast(1)

            val r = (start.red + ratio * (end.red - start.red)).toInt()
            val g = (start.green + ratio * (end.green - start.green)).toInt()
            val b = (start.blue + ratio * (end.blue - start.blue)).toInt()

            val stepColor = Color(r, g, b)

            builder.append(ChatColor.of(stepColor)).append(text[i])
        }

        return builder.toString()

    }

}