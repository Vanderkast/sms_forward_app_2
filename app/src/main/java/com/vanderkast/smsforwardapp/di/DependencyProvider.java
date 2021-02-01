package com.vanderkast.smsforwardapp.di;

import android.content.Context;

import com.vanderkast.smsforwardapp.MainActivity;
import com.vanderkast.smsforwardapp.email.EmailSender;
import com.vanderkast.smsforwardapp.email.GMailSender;
import com.vanderkast.smsforwardapp.extension.ErrorKeeper;
import com.vanderkast.smsforwardapp.extension.PreferenceErrorKeeper;
import com.vanderkast.smsforwardapp.extension.StringPreference;
import com.vanderkast.smsforwardapp.network.Api;
import com.vanderkast.smsforwardapp.sms.SmsDateFilter;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.function.Function;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class DependencyProvider {
    private final Context context;
    private Retrofit retrofit;

    public DependencyProvider(Context context) {
        this.context = context;
    }

    @Provides
    public Context getContext() {
        return context;
    }

    @Provides
    public Function<String, SmsDateFilter> smsDateFilterFunction() {
        return SmsDateFilter::new;
    }

    @Provides
    public Api api() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Settings.SERVER_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(Api.class);
    }

    @Provides
    public EmailSender sender(GMailSender sender) {
        return sender;
    }

    @Provides
    public ErrorKeeper keeper() {
        return new PreferenceErrorKeeper(new StringPreference(context.getSharedPreferences(MainActivity.PREFERENCES_NAME, Context.MODE_PRIVATE), "error"));
    }
}
