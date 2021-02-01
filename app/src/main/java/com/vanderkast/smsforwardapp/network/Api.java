package com.vanderkast.smsforwardapp.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    @POST("2016/hs/smsbank/")
    Call<Void> send(@Header("Authorization") String auth, @Body String text);
}
