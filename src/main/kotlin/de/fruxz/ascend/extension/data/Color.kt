package de.fruxz.ascend.extension.data

import java.awt.Color as AwtColor

fun rgb(rgb: Int) = AwtColor(rgb)

fun rgb(red: Int, green: Int, blue: Int) = AwtColor(red, green, blue)

fun rgb(red: Int, green: Int, blue: Int, alpha: Int) = AwtColor(red, green, blue, alpha)

fun hsb(h: Float, s: Float, b: Float) = AwtColor(h, s, b)