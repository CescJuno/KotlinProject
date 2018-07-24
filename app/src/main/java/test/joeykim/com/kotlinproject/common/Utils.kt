package test.joeykim.com.kotlinproject.common

import android.util.Base64
import java.io.UnsupportedEncodingException
import com.google.gson.JsonParseException
import org.json.JSONObject



class Utils {
    public fun encodeString(s: String): String {
        var data = ByteArray(0)

        try {
            data = s.toByteArray(charset("UTF-8"))

        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } finally {

            return Base64.encodeToString(data, Base64.DEFAULT)

        }
    }

}