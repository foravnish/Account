package com.accountapp.accounts.model.response

data class LadgerListResponse(
    val data: List<DataItemLadger>?,
    val status: String = "",
    val msg: String = ""
)


data class DataItemLadger(
    val CREDIT: String = "",
    val TYPES: String = "",
    val DATE: String = "",
    val ACNAME: String = "",
    val DEBIT: String = "",
    val CREATED: String = "",
    val city: String = "",
    val ACCODE: String = "",
    val sno: String = "",
    val NARR: String = "",
    val ID: String = "",
    val DRCR: String = "",
    val TYPE: String = "",
    val COMPANY_GST: String = "",
    val BALANCE: String=""
)


