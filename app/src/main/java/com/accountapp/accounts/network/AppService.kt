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



    @GET(NetworkConstants.READ_COMPANY + "/{gstNo}")
    fun callReadCompany(@Path("gstNo") gstNo: String): Call<SignUpResponse>

    @FormUrlEncoded
    @POST(NetworkConstants.COMPANY_LIST_SEARCH)
    fun callSearchData(@Field("gst") gst: String?, @Field("acname") acname: String): Call<SearchCompanyList>

//    COMPANY_LIST_LADGER Api
    @GET(NetworkConstants.COMPANY_LIST_LADGER + "/{gstNo}"+ "/{acc_no}"+ "/{fromDate}"+ "/{endDate}")
    fun callLadgerList(@Path("gstNo") gstNo: String?, @Path("acc_no") acc_no: String,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String): Call<LadgerListResponse>

//    PDF_GENERATOR Api
    @GET(NetworkConstants.PDF_GENERATOR + "/{gstNo}"+ "/{acc_no}"+ "/{fromDate}"+ "/{endDate}")
    fun callPdffGenerateApi(@Path("gstNo") gstNo: String, @Path("acc_no") acc_no: String,@Path("fromDate") fromDate: String?, @Path("endDate") endDate: String): Call<PDFGeneratorReponse>

    //Forgot Password API
    @FormUrlEncoded
    @POST(NetworkConstants.FORGOT_PASSWORD)
    fun callForgotPassword(@Field("mobile") phone_number: String): Call<LoginResponse>


    //Change Password API
    @FormUrlEncoded
    @POST(NetworkConstants.CHANGE_PASSWORD)
    fun callChangePassword(@Field("mobile") phone_number: String,@Field("cpass") cpass: String,@Field("npass") npass: String): Call<LoginResponse>



}