package com.imdvlpr.expensetracker.helper.network

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.imdvlpr.expensetracker.BuildConfig.BASE_URL_OTP
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.helper.utils.Constants
import com.imdvlpr.expensetracker.model.OTP
import eu.amirs.JSON
import org.json.JSONException

class Api(private val context: Context) {

    fun sendOtp(data: OTP, callback: (OTP) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val url = BASE_URL_OTP + Constants.ENDPOINT.OTP
        val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response: String ->
                println(response)
                try {
                    callback(OTP(JSON(response)))
                } catch (e: JSONException) {
                    callback(OTP(isSuccess = false, message = context.getString(R.string.internal_server_error)))
                    Log.e("json-exception-otp", e.toString())
                }
            },
            Response.ErrorListener { error: VolleyError ->
                callback(OTP(isSuccess = false, message = context.getString(R.string.internal_server_error)))
                Log.e("volley-exception-otp", error.toString())
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if (data.action == Constants.PARAM.SEND_OTP) {
                    params["action"] = Constants.PARAM.SEND_OTP
                    params["phone"] = data.phoneNumber
                    params["is_resend"] = data.isResend.toString()
                } else {
                    params["action"] = Constants.PARAM.VERIFY_OTP
                    params["message_id"] = data.messageId.toString()
                    params["otp_number"] = data.otpNumber.toString()
                }
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