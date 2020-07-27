package com.accountapp.accounts.model.response

data class DataItemTB(
    val acname: String = "",
    val balance: String = "",
    val accode: String = "",
    val dr: String = "",
    val cr: String = ""
)


data class TrialBalanceRespone(
    val data: List<DataItemTB>?,
    val status: String = "",
    val msg: String = ""
)


