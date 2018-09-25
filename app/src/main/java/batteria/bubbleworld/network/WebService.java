package batteria.bubbleworld.network;

import android.content.Context;

import com.google.gson.GsonBuilder;

import batteria.bubbleworld.utils.Utils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {
    private  static  final String TAG = "WebService";
    private Context mContext;
    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static WebService instance = null;
    private Retrofit mRetrofit = null;
    public static WebService getInstance(){
        if (instance == null){
            instance = new WebService();
        }
        return instance;
    }
    private WebService(){
        this.mContext = Utils.getContext();
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http:///")
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }

}
