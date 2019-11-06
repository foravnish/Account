package com.accountapp.accounts.model.response

data class DataItem(val ACCODE: String = "",
                    val ACNAME: String = "",
                    val COMPANY_GST: String = "")


data class SearchCompanyList(val data: List<DataItem>?,
                             val status: String = "")


