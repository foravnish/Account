package network

/**
 * Created by Avnish
 */
object NetworkConstants {

    const val BASE_URL: String = "https://murtifood.com/api/"         // Staging

    //   Note:  Be remember Base URL must be change in 'network_security_config.xml' file as well.

    const val SIGNUP = "create_comapny"
    const val SIGN_IN = "login"
    const val READ_COMPANY = "read_comapny_file"
    const val COMPANY_LIST_SEARCH = "comapny_account_list/"
    const val COMPANY_LIST_LADGER = "comapny_account_ledger"
    const val PDF_GENERATOR = "ledger_pdf_url"
    const val FORGOT_PASSWORD = "forgot_pass"
    const val CHANGE_PASSWORD = "change_pass"
    const val ADD_COMPANY = "add_comapny"
    const val MY_COMPANY = "get_my_company"
}
