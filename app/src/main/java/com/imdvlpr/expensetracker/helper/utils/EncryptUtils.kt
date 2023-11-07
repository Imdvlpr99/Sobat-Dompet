package com.imdvlpr.expensetracker.helper.utils

import android.content.Context
import android.util.Base64
import com.imdvlpr.expensetracker.R
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

fun Context.encrypt(data: String): String {
    val dataBytes = data.toByteArray(Charsets.UTF_8)
    val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    cipher.init(Cipher.ENCRYPT_MODE, convertPublicKey(this.getString(R.string.public_key)))
    val encryptedBytes = cipher.doFinal(dataBytes)
    return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
}

fun Context.decrypt(encryptedData: String): String {
    val encryptedBytes = Base64.decode(encryptedData, Base64.DEFAULT)
    val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    cipher.init(Cipher.DECRYPT_MODE, convertPrivateKey(this.getString(R.string.private_key)))
    val decryptedBytes = cipher.doFinal(encryptedBytes)
    return String(decryptedBytes, Charsets.UTF_8)
}

private fun convertPublicKey(publicKeyString: String): PublicKey {
    val publicKeyBytes = Base64.decode(publicKeyString, Base64.DEFAULT)
    val keyFactory = KeyFactory.getInstance("RSA")
    val keySpec = X509EncodedKeySpec(publicKeyBytes)
    return keyFactory.generatePublic(keySpec)
}

private fun convertPrivateKey(privateKeyString: String): PrivateKey {
    val privateKeyBytes = Base64.decode(privateKeyString, Base64.DEFAULT)
    val keyFactory = KeyFactory.getInstance("RSA")
    val keySpec = PKCS8EncodedKeySpec(privateKeyBytes)
    return keyFactory.generatePrivate(keySpec)
}
