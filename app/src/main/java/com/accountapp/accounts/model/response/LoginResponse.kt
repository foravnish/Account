package com.accountapp.accounts.model.response

data class LoginResponse(val detail: Detail,
                         val message: String = "",
                         val status: String = "")


data class Detail(val regDate: String = "",
                  val address: String = "",
                  val loginPass: String = "",
                  val city: String = "",
                  val ftpPass: String = "",
                  val name: String = "",
                  val mobile: String = "",
                  val gst: String = "",
                  val company: String = "",
                  val id: String = "",
                  val email: String = "",
                  val status: String = "")


