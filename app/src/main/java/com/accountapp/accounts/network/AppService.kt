package network

import com.accountapp.accounts.model.response.*
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by Avnish on 2/4/18.
 */
interface AppService {

    //signup API
    @FormUrlEncoded
    @POST(NetworkConstants.SIGNUP)
    fun callSignUp(
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("login_pass") login_pass: String,
        @Field("company_name") company_name: String,
        @Field("email") email: String,
        @Field("gst") gst: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("status") status: String
    ): Call<SignUpResponse>


    //Login API
    @FormUrlEncoded
    @POST(NetworkConstants.SIGN_IN)
    fun callLogin(
        @Field("mobile") phone_number: String,
        @Field("pass") Pass: String
    ): Call<LoginResponse>



    @GET(NetworkConstants.READ_COMPANY + "/{gstNo}"+"/{session}")
    fun callReadCompany(@Path("gstNo") gstNo: String,@Path("session") session: String): Call<SignUpResponse>

    @FormUrlEncoded
    @POST(NetworkConstants.COMPANY_LIST_SEARCH)
    fun callSearchData(@Field("gst") gst: String?, @Field("acname") acname: String): Call<SearchCompanyList>

//    COMPANY_LIST_LADGER Api
    @GET(NetworkConstants.COMPANY_LIST_LADGER + "/{gstNo}"+ "/{acc_no}"+ "/{fromDate}"+ "/{endDate}")
    fun callLadgerList(@Path("gstNo") gstNo: String?, @Path("acc_no") acc_no: String,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String,@Query("financial_year") financial_year:String): Call<LadgerListResponse>

//    PDF_GENERATOR Api
    @GET(NetworkConstants.PDF_GENERATOR + "/{gstNo}"+ "/{acc_no}"+ "/{fromDate}"+ "/{endDate}")
    fun callPdffGenerateApi(@Path("gstNo") gstNo: String, @Path("acc_no") acc_no: String,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String,@Query("financial_year") financial_year:String): Call<PDFGeneratorReponse>

    //Forgot Password API
    @FormUrlEncoded
    @POST(NetworkConstants.FORGOT_PASSWORD)
    fun callForgotPassword(@Field("mobile") phone_number: String): Call<LoginResponse>


    //Change Password API
    @FormUrlEncoded
    @POST(NetworkConstants.CHANGE_PASSWORD)
    fun callChangePassword(@Field("mobile") phone_number: String,@Field("cpass") cpass: String,@Field("npass") npass: String): Call<LoginResponse>


    //Add Company API
    @FormUrlEncoded
    @POST(NetworkConstants.ADD_COMPANY)
    fun callAddCompany(
        @Field("company_name") comName: String,
        @Field("gst") gst: String,
        @Field("status") status: String,
        @Field("mobile") mobile: String

    ): Call<SignUpResponse>



    //   My Company Api
    @GET(NetworkConstants.MY_COMPANY + "/{mobile}" )
    fun callCompanyList(@Path("mobile") mobile: String?): Call<CompanyListingResponse>

    //   My Trial Blance Api
    @GET(NetworkConstants.TRIAL_BALANCE +"/{gstNo}"+ "/{fromDate}"+ "/{endDate}" )
    fun callTrialBalanceList(@Path("gstNo") gstNo: String?,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String,@Query("financial_year") financial_year:String): Call<TrialBalanceRespone>


    //    PDF_GENERATOR Trial Balance Api
    @GET(NetworkConstants.TRIAL_BALANCE_PDF + "/{gstNo}"+ "/{fromDate}"+ "/{endDate}")
    fun callPdffGenerateTrialBalance(@Path("gstNo") gstNo: String,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String,@Query("financial_year") financial_year:String): Call<PDFGeneratorReponse>


    //   Sundry Cradator
    @GET(NetworkConstants.SUNDRY_CR +"/{gstNo}"+ "/{fromDate}"+ "/{endDate}" )
    fun callSundryCradator(@Path("gstNo") gstNo: String?,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String,@Query("financial_year") financial_year:String): Call<TrialBalanceRespone>

    //   Sundry Cradator PDF
    @GET(NetworkConstants.SUNDRY_CR_PDF +"/{gstNo}"+ "/{fromDate}"+ "/{endDate}" )
    fun callSundryCradatorPDF(@Path("gstNo") gstNo: String?,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String,@Query("financial_year") financial_year:String): Call<PDFGeneratorReponse>


    //   Sundry Dabator
    @GET(NetworkConstants.SUNDRY_DR +"/{gstNo}"+ "/{fromDate}"+ "/{endDate}" )
    fun callSundryDebator(@Path("gstNo") gstNo: String?,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String,@Query("financial_year") financial_year:String): Call<TrialBalanceRespone>

    //   Sundry Dabator pdf
    @GET(NetworkConstants.SUNDRY_DR_PDF +"/{gstNo}"+ "/{fromDate}"+ "/{endDate}" )
    fun callSundryDebatorPDF(@Path("gstNo") gstNo: String?,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String,@Query("financial_year") financial_year:String): Call<PDFGeneratorReponse>

    @FormUrlEncoded
    @POST(NetworkConstants.EDIT_PROFILE)
    fun callEditProfile(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("company_name") company_name: String,
        @Field("email") status: String,
        @Field("address") mobile: String,
        @Field("city") city: String,
        @Field("mobile") mobileNo: String

    ): Call<LoginResponse>

}