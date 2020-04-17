package com.vn.basemvvm

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun find() {
        val string = "onJsLoad();</script><title>Shared album - ĐẠT TRẦN - Google Photos</title><meta name=\"robots\" content=\"noindex\"><meta property=\"og:title\" content=\"New video by ĐẠT TRẦN\"><meta property=\"og:type\" content=\"video\"><meta property=\"og:url\" content=\"https://photos.google.com/share/AF1QipP-TlLqqBaaqLLlyI4ZumoQrZPOkdUGEbzgJBLHUhC7JbGvmMb4frzLH8Ib3Z2kQg?key=QjJjODhkd21jb2swRkFYZ2lMQjdsWkxOVWxZZWVn\"><meta property=\"og:image\" content=\"https://lh3.googleusercontent.com/NSGaIp13tCnZq2Rlj0wVZAad4eBLqM_wWBWxfO3l8d8FeFYhRNvGdShdX_J5xwR2GQsoAWiU9NRKt9pHsN9KhuQG9Ejvx-sIQfhyaYY0boM2bm6mWTespEanK3p9CV_FUsCMoBJiBg=w600-h315-p-k-no\"><meta property=\"og:image:width\" content=\"600\"><meta property=\"og:image:height\" content=\"315\"><meta name=\"twitter:card\" content=\"photo\"><meta name=\"twitter:site\" content=\"@googlephotos\"><meta name=\"fb:app_id\" content=\"1408502372789355\"><meta property=\"og:video\" content=\"https://lh3.googleusercontent.com/NSGaIp13tCnZq2Rlj0wVZAad4eBLqM_wWBWxfO3l8d8FeFYhRNvGdShdX_J5xwR2GQsoAWiU9NRKt9pHsN9KhuQG9Ejvx-sIQfhyaYY0boM2bm6mWTespEanK3p9CV_FUsCMoBJiBg=w600-h315-k-no-m18\"><meta property=\"og:video:secure_url\" content=\"https://lh3.googleusercontent.com/NSGaIp13tCnZq2Rlj0wVZAad4eBLqM_wWBWxfO3l8d8FeFYhRNvGdShdX_J5xwR2GQsoAWiU9NRKt9pHsN9KhuQG9Ejvx-sIQfhyaYY0boM2bm6mWTespEanK3p9CV_FUsCMoBJiBg=w600-h315-k-no-m18\"><meta property=\"og:video:type\" content=\"video/mp4\"><meta property=\"og:video:width\" content=\"600\"><meta property=\"og:video:height\" content=\"315\"><script id='og-inHeadScript' nonce=\"ey8e/fxj7OCJPrK/+mpK8g\">;this.gbar_={CONFIG:[[[0,\"www.gstatic.com\",\"og.qtm.en_US.EqBcnH-TWRk.O\",\"com.vn\",\"en\",\"31\",0,[4,2,\".40.40.40.40.40.40.\",\"\",\"1300102,3700303,3700697\",\"307318782\",\"0\"],null,\"liepXsrAOYXY-wTO7LyYBQ\",null,0,\"og.qtm.15aandm11is4m.L.X.O\",\"AA2YrTv1lGDphP_8g_rszPCsqy8KYgA11Q\",\"AA2YrTtT-VVtOi-uymSLz2WjDFUwP0j3-Q\",\"\",2,1,200,\"VNM\",null,null,\"269\",\"31\",1],null,[1,0.1000000014901161,2,1],[1,0.001000000047497451,1],[0,0,0,null,\"\",\"\",\"\",\"\"],[0,0,\"\",1,0,0,0,0,0,0,0,0,0,null,0,0,null,null,0,0,0,\"\",\"\",\"\",\"\",\"\",\"\",null,0,0,0,0,0,null,null,null,\"rgba(32,33,36,1)\",\"transparent\",0,0,1,1],null,null,[\"1\",\"gci_91f30755d6a6b787dcc2a4062e6e9824.js\",\"googleapis.client:plusone:gapi.iframes\",\"\",\"en\"],null,null,[0,\"\",null,\"\",0,\"There was an error loading your Marketplace apps.\",\"You have no Marketplace apps.\",0,[31,\"https://photos.google.com/?tab=qq\\u0026pageId=none\",\"Photos\",\"_blank\",\"0 -621px\",null,0],null,null,null,0,null,null,0],null,[\"m;/_/scs/abc-static/_/js/k=gapi.gapi.en.jw7XZHvcak8.O/d=1/ct=zgms/rs=AHpOoo-L1iz4xVj0PCdm2On38RCj6aYemA/m=__features__\",\"https://apis.google.com\",\"\",\"\",\"\",\"\",null,1,\"es_plusone_gc_20200310.0_p0\",\"en\",null,0,0],[0.009999999776482582,\"com.vn\",\"31\",[null,\"\",\"0\",null,1,5184000,null,null,\"\",0,1,\"\",0,0,0,0,0,0,1,0,0,0],null,[[\"\",\"\",\"0\",0,0,-1]],null,0,null,null,[\"5061451\",\"google\\\\.(com|ru|ca|by|kz|com\\\\.mx|com\\\\.tr)\$\",1]],[1,1,0,27043,31,\"VNM\",\"en\",\"307318782.0\",8,0.009999999776482582,0,0,null,null,0,0,\"\",null,null,null,\"liepXsrAOYXY-wTO7LyYBQ\",0],[[null,null,null,\"https://www.gstatic.com/og/_/js/k=og.qtm.en_US.EqBcnH-TWRk.O/rt=j/m=qabr,q_d,qawd,qsd,qmutsd,qapid/exm=qaaw,qadd,qaid,qein,qhaw,qhbr,qhch,qhga,qhid,qhin,qhpr/d=1/ed=1/rs=AA2YrTv1lGDphP_8g_rszPCsqy8KYgA11Q\"],[null,null,null,\"https://www.gstatic.com/og/_/ss/k=og.qtm.15aandm11is4m.L.X.O/m=qawd/excm=qaaw,qadd,qaid,qein,qhaw,qhbr,qhch,qhga,qhid,qhin,qhpr/d=1/ed=1/ct=zgms/rs=AA2YrTtT-VVtOi-uymSLz2WjDFUwP0j3-Q\"]],null,null,[\"\"]]],};this.gbar_=this.gbar_||{};(function(_){var window=this;"
        val match = Pattern.compile("og:video.+?content=\"(.+?)\">").matcher(string)
        if (match.find()) {
            val str = match.group(1)?.split("=")?.firstOrNull() ?: return
            println(str)
        }
    }
}
