package com.sbtopzzzteam.somethingcool.REST;

import android.content.Context;
import android.provider.Settings;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Methods {
    private static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void createUserLoginRequest(Context context, String emailAddress, UserLoginRequest userLoginRequest) {
        String deviceId = getAndroidId(context);

        EndPoints apiClient = APIClient.getClient().create(EndPoints.class);
        Map<String, Object> map = new HashMap<>();
        map.put("emailAddress", emailAddress);

        Call<Map<String, Object>> call = apiClient.createUserLoginRequest(deviceId, map);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map1 = response.body();
                double code = (double) map1.get("code");
                if (code == 0) {
                    String userId = map1.get("userId").toString();

                    userLoginRequest.onSuccess(userId);
                } else {
                    userLoginRequest.onFailure(map1.get("error").toString());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                userLoginRequest.onFailure(t.getMessage());
            }
        });
    }
    public static void createUserLoginStatusRequest(String emailAddress, UserLoginStatusRequest userLoginStatusRequest) {
        EndPoints apiClient = APIClient.getClient().create(EndPoints.class);
        Map<String, Object> map = new HashMap<>();
        map.put("emailAddress", emailAddress);

        Call<Map<String, Object>> call = apiClient.createUserLoginStatusRequest(map);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map1 = response.body();
                double code = (double) map1.get("code");
                if (code == 0) {
                    userLoginStatusRequest.onSuccess(((double) map1.get("state")));
                } else {
                    userLoginStatusRequest.onFailure(map1.get("error").toString());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                userLoginStatusRequest.onFailure(t.getMessage());
            }
        });
    }
    public static void createUserRegisterRequest(Context context, String emailAddress, String fullName, String phoneNumber, UserRegisterRequest userRegisterRequest) {
        String deviceId = getAndroidId(context);

        EndPoints apiClient = APIClient.getClient().create(EndPoints.class);
        Map<String, Object> map = new HashMap<>();
        map.put("deviceId", deviceId);
        map.put("emailAddress", emailAddress);
        map.put("fullName", fullName);
        map.put("phoneNumber", phoneNumber);

        Call<Map<String, Object>> call = apiClient.createUserRegisterRequest(map);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map1 = response.body();

                double code = (double) map1.get("code");

                if (code == 0) {
                    userRegisterRequest.onSuccess();
                } else {
                    userRegisterRequest.onFailure(map1.get("error").toString());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                userRegisterRequest.onFailure(t.getMessage());
            }
        });
    }
    public static void createUserLoginAuthTokenRequest(Context context, String emailAddress, UserLoginAuthTokenRequest userLoginAuthTokenRequest) {
        String deviceId = getAndroidId(context);

        EndPoints apiClient = APIClient.getClient().create(EndPoints.class);
        Map<String, Object> map = new HashMap<>();
        map.put("deviceId", deviceId);
        map.put("emailAddress", emailAddress);

        Call<Map<String, Object>> call = apiClient.createUserLoginAuthTokenRequest(map);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> map1 = response.body();

                double code = (double) map1.get("code");

                if (code == 0) {
                    userLoginAuthTokenRequest.onSuccess(map1.get("authToken").toString());
                } else {
                    userLoginAuthTokenRequest.onFailure(map1.get("error").toString());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                userLoginAuthTokenRequest.onFailure(t.getMessage());
            }
        });
    }

    public interface UserLoginRequest {
        void onSuccess(String userId);
        void onFailure(String error);
    }
    public interface UserLoginStatusRequest {
        void onSuccess(double verified);
        void onFailure(String error);
    }
    public interface UserRegisterRequest {
        void onSuccess();
        void onFailure(String error);
    }
    public interface UserLoginAuthTokenRequest {
        void onSuccess(String authToken);
        void onFailure(String error);
    }
}
