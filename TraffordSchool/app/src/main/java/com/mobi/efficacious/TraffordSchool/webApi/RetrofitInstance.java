package com.mobi.efficacious.TraffordSchool.webApi;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
// UAT CODE
    private static final String BASE_URL = "http://e-smarts.co.in/TraffordWebAPIUAT/api/";
    public static final String Image_URL = "http://e-smarts.co.in/TraffordWebAPIUAT/image/";
    public static final String pdfUrl="http://e-smarts.com/TraffordAPI/PDF/";
    public static final String resultUrl="http://www.e-smarts.co.in/TraffordUATResult/result.aspx?";

    // DEMO UAT CODE
//    private static final String BASE_URL = "http://e-smarts.com/EfficaciousSchoolDemoAPI/api/";
//    public static final String Image_URL = "http://e-smarts.com/EfficaciousSchoolDemoAPI/image/";
//    public static final String pdfUrl="http://e-smarts.com/EfficaciousSchoolDemoAPI/PDF/";
//    public static final String resultUrl="http://e-smarts.com/EfficaciousSchoolDemoAPI/result.aspx?";




    // Production Code
//    private static final String BASE_URL = "http://e-smarts.com/TraffordAPI/api/";
//    public static final String Image_URL = "http://e-smarts.com/TraffordAPI/image/";
//    public static final String resultUrl=" http://e-smarts.com/TraffordResult/result.aspx?";



    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getUnsafeOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}