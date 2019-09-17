package com.dtu.capstone2.ereading

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * Create by Nguyen Van Phuc on 2/20/19
 */
class App : MultiDexApplication() {
    internal lateinit var localRepository: LocalRepository

    companion object {
        lateinit var instant: App
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instant = this
        localRepository = LocalRepository(applicationContext)
    }

    internal fun getCertificateVietnamNet(): SSLContext {
        val cert = resources.openRawResource(R.raw.gsdomainvalsha2g2r1)
        var sslContext: SSLContext? = null

        try {
            // creating a KeyStore containing our trusted CAs
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                load(null, null)
                setCertificateEntry("ca", CertificateFactory.getInstance("X.509").generateCertificate(cert))
            }

            // creating a TrustManager that trusts the CAs in our KeyStore
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                init(keyStore)
            }

            // creating an SSLSocketFactory that uses our TrustManager
            sslContext = SSLContext.getInstance("TLS").apply {
                init(null, tmf.trustManagers, null)
            }
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                cert.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sslContext!!
    }
}
