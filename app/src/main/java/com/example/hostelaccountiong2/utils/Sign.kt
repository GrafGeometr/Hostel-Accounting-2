package com.example.hostelaccountiong2.utils

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Sign {
    fun sign(stringToSign: String, awsSecretKey: String): Any {
        val secretKeySpec = SecretKeySpec(awsSecretKey.toByteArray(), "HmacSHA1")
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(secretKeySpec)
        return Base64.getEncoder().encodeToString(mac.doFinal(stringToSign.toByteArray()))
    }
}