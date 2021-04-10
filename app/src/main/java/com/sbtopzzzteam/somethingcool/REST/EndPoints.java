package com.sbtopzzzteam.somethingcool.REST;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPoints {
    @POST("/login")
    Call<Map<String, Object>> createUserLoginRequest(@Query("deviceId") String deviceId, @Body Map<String, Object> map);
    @POST("/login/status")
    Call<Map<String, Object>> createUserLoginStatusRequest(@Body Map<String, Object> map);
    @POST("/login/authToken")
    Call<Map<String, Object>> createUserLoginAuthTokenRequest(@Body Map<String, Object> map);

    @POST("/register")
    Call<Map<String, Object>> createUserRegisterRequest(@Body Map<String, Object> map);
}