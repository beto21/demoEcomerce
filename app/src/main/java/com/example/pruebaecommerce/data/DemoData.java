package com.example.pruebaecommerce.data;

import com.example.pruebaecommerce.model.Articulo;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface DemoData {

    @Headers("X-IBM-Client-Id: adb8204d-d574-4394-8c1a-53226a40876e")
    @GET("/demo-gapsi/search")
    Single<Articulo> buscarArticulo(@Query("query") String value);

}
