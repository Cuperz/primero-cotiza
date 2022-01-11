package com.jumpitt.primero_cotiza.network;

import com.jumpitt.primero_cotiza.network.request.LoginRequest;
import com.jumpitt.primero_cotiza.network.request.RefreshTkRequest;
import com.jumpitt.primero_cotiza.network.response.BaseResponse;
import com.jumpitt.primero_cotiza.network.response.ClientsResponse;
import com.jumpitt.primero_cotiza.network.response.CompaniesResponse;
import com.jumpitt.primero_cotiza.network.response.LeadCheckResponse;
import com.jumpitt.primero_cotiza.network.response.LoginResponse;
import com.jumpitt.primero_cotiza.network.response.OfferResponse;
import com.jumpitt.primero_cotiza.network.response.ProfileResponse;
import com.jumpitt.primero_cotiza.network.response.RefreshTkResponse;
import com.jumpitt.primero_cotiza.network.response.TimerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    @Headers({"Accept:application/json"})
    @POST("oauth/token")
    Call<LoginResponse> signIn(@Body LoginRequest loginRequest);

    @POST("oauth/token")
    Call<RefreshTkResponse> refreshTk(@Body RefreshTkRequest tkRequest);

    @GET("api/v1/profile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String token);

    @Headers({"Accept:application/json"})
    @GET("api/v1/services")
    Call<CompaniesResponse> getCompanies(@Header("Authorization") String token);

    @Headers({"Accept:application/json"})
    @GET("api/v1/attentions")
    Call<ClientsResponse> getClients(@Header("Authorization") String token);

    @GET("api/v1/attentions/new")
    Call<LeadCheckResponse> getLeadId(@Header("Authorization")String token);

    @Headers({"Accept:application/json"})
    @POST("api/v1/offers/take/{offerId}")
    Call<OfferResponse> getOffer(@Header("Authorization") String token, @Path("offerId") int offerId);

    @Headers({"Accept:application/json"})
    @POST("api/v1/users/logout")
    Call<BaseResponse> logOut(@Header("Authorization") String token);

    @Headers({"Accept:application/json"})
    @POST("api/v1/accounts/{id}/status")
    Call<BaseResponse> onOffCompany(@Header("Authorization") String token, @Path("id") int companyId);

    @Headers({"Accept:application/json"})
    @POST("api/v1/users/notify/{fbToken}")
    Call<BaseResponse> sendFbToken(@Header("Authorization") String token, @Path("fbToken") String fbToken);

    @Headers({"Accept:application/json"})
    @POST("api/v1/offers/reject/{offer_id}")
    Call<BaseResponse> rejectLead(@Header("Authorization") String token, @Path("offer_id") int offerId);

    @Headers({"Accept:application/json"})
    @POST("api/v1/offers/call/{offer_id}")
    Call<BaseResponse> acceptLead(@Header("Authorization") String token, @Path("offer_id") int offerId);

    @Headers({"Accept:application/json"})
    @GET("api/v1/attentions/new")
    Call<TimerResponse> getTimer(@Header("Authorization") String token);
}
