package com.example.pruebaecommerce.data.retrofit;


import com.example.pruebaecommerce.data.DemoData;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientInteractor {

    private static Retrofit retrofit;
    private static DemoData demoData;


    public static DemoData getData(){
        synchronized (DemoData.class){
            if (demoData == null){
                demoData = builRetrofit(DemoData.class);
            }
            return demoData;
        }
    }



    private synchronized static <T> T builRetrofit(Class<T> type){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://00672285.us-south.apigw.appdomain.cloud/")
                    .client(RetrofitResources.getUnsafeOkHttpClient(RetrofitResources.getLoggingInterceptor()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            
        }
        return retrofit.create(type);
    }

}
