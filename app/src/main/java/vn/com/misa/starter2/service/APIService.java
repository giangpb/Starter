package vn.com.misa.starter2.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.model.entity.Payment;

public interface APIService {

    Gson gsonBuilder = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    APIService API_SERVICE = new Retrofit.Builder()
            .baseUrl("http://192.168.1.110/starter/api/")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
            .create(APIService.class);

    @GET("user")
    Call<List<User>> getAllUser();

    @POST("user")
    Call<Boolean> insertUser(@Query("userName") String username,
                             @Query("password") String pass,
                             @Query("phone") String phone,
                             @Query("permiss")int per);

    @GET("sainvoice")
    Call<List<Payment>> getAllPayment();

    @POST("sainvoice")
    //?refID=122&refType=2&refNo=2&refDate=2021-09-09&amount=1&promotionItemsAmount=1&totalIemAmount=1&promotionRate=2&promotionAmount=3&discountAmount=2&preTaxAmount=1&totalAmount=1&receiveAmount=23&returnAmount=32&paymentStatus=23&orderID=hi&orderType=1&tableName=23&createDate=2021-08-08&userID=2
    Call<Boolean> insertSAInvoice(@Query("refID") String refID,
                                  @Query("refType") int refType,
                                  @Query("refNo") String refNo,
                                  @Query("refDate")String refDate,
                                  @Query("amount") int amount,
                                  @Query("promotionItemsAmount") int promotionItemsAmount,
                                  @Query("totalIemAmount") int totalIemAmount,
                                  @Query("promotionRate") int promotionRate,
                                  @Query("promotionAmount") int promotionAmount,
                                  @Query("discountAmount") int discountAmount,
                                  @Query("preTaxAmount") int preTaxAmount,
                                  @Query("totalAmount") int totalAmount,
                                  @Query("receiveAmount") int receiveAmount,
                                  @Query("returnAmount") int returnAmount,
                                  @Query("paymentStatus") int paymentStatus,
                                  @Query("orderID") String orderID,
                                  @Query("orderType") int orderType,
                                  @Query("tableName") String tableName,
                                  @Query("createDate") String createDate,
                                  @Query("userID") int userID
                                  );
}
