package vn.com.misa.starter2.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ‐ @created_by giangpb on 4/4/2021
 */
public class UnitService {

    private static UnitService unitService;

    public static UnitService getInstance(){
        if (unitService == null){
            unitService = new UnitService();
        }
        return  unitService;
    }

    public static Retrofit initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
