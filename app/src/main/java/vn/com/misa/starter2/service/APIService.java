package vn.com.misa.starter2.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface APIService {

    Gson gsonBuilder = new GsonBuilder()
            .setDateFormat("")
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    APIService API_SERVICE = new Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
            .create(APIService.class);


}
