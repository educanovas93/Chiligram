package com.chiligram.android.app.apiTools;


public class APIUtils {


    public static final String BASE_URL = "https://matrix.cablehogar.es";
    public static final String DOWNLOAD_SERVER = "matrix.cablehogar.es";


    /*
    public static final String BASE_URL = "https://matrix.org";
    public static final String DOWNLOAD_SERVER = "matrix.org";
    */
    public static APIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
