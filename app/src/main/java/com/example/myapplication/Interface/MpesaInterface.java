package com.example.myapplication.Interface;

import com.example.myapplication.Domain.LipaNaMpesa;
import com.example.myapplication.Domain.MpesaModel;
import com.example.myapplication.Domain.MpesaRequestResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MpesaInterface {


    //https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials

    @GET("/oauth/v1/generate?grant_type=client_credentials")
    Call<MpesaModel> getData(
            @Header("Authorization") String basicToken
    );
    //https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest
    @POST("/mpesa/stkpush/v1/processrequest")
    Call<MpesaRequestResponse> getRequest(
            @Header("Authorization") String basicToken,
            @Body LipaNaMpesa lipaNaMpesa
    );
}
