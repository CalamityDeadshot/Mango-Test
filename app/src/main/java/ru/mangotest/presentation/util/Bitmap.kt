package ru.mangotest.presentation.util

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream


fun Bitmap.toBase64(): String {
    val baos = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()

    return Base64.encodeToString(b, Base64.DEFAULT)
}