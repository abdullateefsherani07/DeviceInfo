package com.abdul.android.deviceinfo.appsanalyze.signature

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.util.Base64
import android.util.Log
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.security.MessageDigest
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

class SignatureDetailsRepository(private val context: Context) {

    suspend fun getSignatures(): List<StorageDetails?> {
        return withContext(Dispatchers.IO) {
            val signaturesList = mutableListOf<StorageDetails>()
            try {
                val packageManager = context.packageManager
                val signaturesMap = mutableMapOf<String, Int>()

                val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES)

                val signaturesSet = mutableListOf<Signature>()
                for (packageInfo in installedPackages) {
                    val signatures = packageInfo.signatures
                    for (signature in signatures) {
                        val cert = parseSignature(signature.toByteArray())
                        val algorithm = cert?.sigAlgName ?: "Unknown"
                        val count = signaturesMap[algorithm] ?: 0
                        signaturesMap[algorithm] = count + 1
                    }
                }

                for ((algorithm, count) in signaturesMap) {
                    signaturesList.add(StorageDetails(algorithm, installedPackages.size.toLong(), count.toLong()))
                }
                signaturesList.forEach {
                    if( it != null) {
                        Log.e("Signature", "Name: ${it.name}, Apps: ${it.used}, Total: ${it.total}")
                    } else {
                        Log.e("Signature", "No Signature")
                    }
                }
                signaturesList
            } catch (e: Exception) {
                Log.e("SignaturesRepository", "${e.message}")
                signaturesList
            }
        }
    }
    private fun parseSignature(signature: ByteArray): X509Certificate? {
        val certFactory = CertificateFactory.getInstance("X.509")
        val cert = certFactory.generateCertificate(ByteArrayInputStream(signature)) as? X509Certificate
        return cert
    }

}