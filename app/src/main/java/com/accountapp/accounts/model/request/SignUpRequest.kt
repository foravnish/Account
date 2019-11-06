package com.accountapp.accounts.model.request

data class SignUpRequest(val name: String,
                         val mobile: String,
                         val login_pass: String,
                         val company_name: String,
                         val email: String,
                         val gst: String,
                         val address: String,
                         val city: String

)