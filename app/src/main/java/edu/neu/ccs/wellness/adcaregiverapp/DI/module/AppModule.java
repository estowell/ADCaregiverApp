package edu.neu.ccs.wellness.adcaregiverapp.DI.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.UserServiceHolder;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.OauthToken;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces.UserService;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amritanshtripathi on 6/20/18.
 */

@Module
public class AppModule {
    private final String baseurl = "http://wellness.ccs.neu.edu/adc_dev/";

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }


    @Provides
    @Singleton
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


    @Provides
    @Singleton
    public OkHttpClient okHttpClient(final Cache cache, HttpLoggingInterceptor httpLoggingInterceptor, final UserManager userManager, final UserServiceHolder serviceHolder) {
        return new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request intialRequest = chain.request();
                        Request modifiedRequest = intialRequest;
                        if (userManager.getUser() != null) {
                            modifiedRequest = intialRequest.newBuilder()
                                    .addHeader("Authorization", userManager.getAutharizationTokenWithType())
                                    .build();
                        }
                        Response response = chain.proceed(modifiedRequest);
                        boolean uauthorized = response.code() == 403;

                        if (uauthorized && userManager.isTokenExpired()) {
                            UserService service = serviceHolder.getService();
                            User user = userManager.getUser();
                            OauthToken oauthToken = service.refreshToken(Constants.GRANT_TYPE_REFRESH, userManager.geRefreshtToken(), Constants.CLIENT_ID, Constants.CLIENT_SECRET).execute().body();
                            assert user != null;
                            user.setToken(oauthToken);
                            userManager.saveUser(user);
                            modifiedRequest = intialRequest.newBuilder()
                                    .addHeader("Authorization", userManager.getAutharizationTokenWithType())
                                    .build();
                            return chain.proceed(modifiedRequest);
                        }
                        return response;
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); //10 MB
    }

    @Provides
    @Singleton
    public File file(Context context) {
        File file = new File(context.getCacheDir(), "HttpCache");
        file.mkdirs();
        return file;
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(this.getClass().getSimpleName(), message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    public Picasso picasso(Context context, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context).
                downloader(okHttp3Downloader).
                build();
    }

    @Provides
    @Singleton
    public OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }


    @Provides
    @Singleton
    public SharedPreferences getSharePreferences(Context context) {

        return context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);

    }

    @Provides
    @Singleton
    public UserManager getUserManager(SharedPreferences sharedPreferences) {
        return new UserManager(sharedPreferences);
    }

    public class AuthenticationInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    }

    @Provides
    @Singleton
    UserServiceHolder provideUserServiceHolder() {
        return new UserServiceHolder();
    }

    @Provides
    @Singleton
    UserService provideAPI(Retrofit retrofit, UserServiceHolder apiServiceHolder) {
        UserService apiService = retrofit.create(UserService.class);
        apiServiceHolder.setService(apiService);
        return apiService;
    }
}
