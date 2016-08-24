package com.rliossi.apps.zapimoveis_teste.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Rene on 20/08/2016.
 */
public class RestManager {
    private static final String BASE_URL = "http://demo3016039.mockable.io/";
    private static RestManager mRestManager;
    private Retrofit mRetrofit;
    private ServiceAPI mServiceAPI;

    private RestManager(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        mServiceAPI = mRetrofit.create(ServiceAPI.class);
    }

    public static RestManager getInstance(){
        if (mRestManager == null) {
            mRestManager = new RestManager();
        }
        return mRestManager;
    }

    public ServiceAPI getServiceAPI(){
        return mServiceAPI;
    }

}
