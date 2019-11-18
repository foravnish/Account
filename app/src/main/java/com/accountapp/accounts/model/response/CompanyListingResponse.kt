package com.accountapp.accounts.model.response

data class DataItemComListing(val address: String = "",
                    val city: String = "",
                    val mobile: String = "",
                    val gst: String = "",
                    val mob_auth: String = "",
                    val reg_date: String = "",
                    val login_pass: String = "",
                    val ftp_pass: String = "",
                    val name: String = "",
                    val company: String = "",
                    val id: String = "",
                    val email: String = "",
                    val status: String = "")


data class CompanyListingResponse(val data: List<DataItemComListing>?,
                                  val status: String = "")


