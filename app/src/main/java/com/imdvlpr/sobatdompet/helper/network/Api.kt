package com.imdvlpr.sobatdompet.helper.network

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.imdvlpr.sobatdompet.BuildConfig.BASE_URL
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.helper.utils.Constants
import com.imdvlpr.sobatdompet.model.OTP
import eu.amirs.JSON
import org.json.JSONException

class Api(private val context: Context) {

    fun sendOtp(data: OTP, callback: (OTP) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val url = BASE_URL + Constants.ENDPOINT.SEND_OTP
        val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response: String ->
                println(response)
                try {
                    callback(OTP(JSON(response)))
                } catch (e: JSONException) {
                    callback(OTP(isSuccess = false, message = context.getString(R.string.response_server_error)))
                    Log.e("json-exception-otp", e.toString())
                }
            },
            Response.ErrorListener { error: VolleyError ->
                callback(OTP(isSuccess = false, message = context.getString(R.string.response_server_error)))
                Log.e("volley-exception-otp", error.toString())
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["phone"] = data.phoneNumber
                params["is_resend"] = data.isResend.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)
        stringRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
    }

    fun sendOtpEmail(data: OTP, callback: (OTP) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val url = BASE_URL + Constants.ENDPOINT.SEND_OTP_EMAIL
        val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response: String ->
                println(response)
                try {
                    callback(OTP(JSON(response)))
                } catch (e: JSONException) {
                    callback(OTP(isSuccess = false, message = context.getString(R.string.response_server_error)))
                    Log.e("json-exception-otp", e.toString())
                }
            },
            Response.ErrorListener { error: VolleyError ->
                callback(OTP(isSuccess = false, message = context.getString(R.string.response_server_error)))
                Log.e("volley-exception-otp", error.toString())
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = data.email
                params["is_resend"] = data.isResend.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)
        stringRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
    }

    fun verifyEmail(data: OTP, callback: (OTP) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val url = BASE_URL + Constants.ENDPOINT.VERIFY_OTP
        val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response: String ->
                println(response)
                try {
                    callback(OTP(JSON(response)))
                } catch (e: JSONException) {
                    callback(OTP(isSuccess = false, message = context.getString(R.string.response_server_error)))
                    Log.e("json-exception-otp", e.toString())
                }
            },
            Response.ErrorListener { error: VolleyError ->
                callback(OTP(isSuccess = false, message = context.getString(R.string.response_server_error)))
                Log.e("volley-exception-otp", error.toString())
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["message_id"] = data.messageId.toString()
                params["otp_number"] = data.otpNumber.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)
        stringRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
    }
}