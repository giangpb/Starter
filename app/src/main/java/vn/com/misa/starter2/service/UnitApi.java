package vn.com.misa.starter2.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.com.misa.starter2.model.entity.Unit;

/**
 * ‐ @created_by giangpb on 4/4/2021
 */
public interface UnitApi {

    @GET("users/{user}/repos")
    Call<List<Unit>> listRepos(@Path("user") String user);
}
