package com.vanderkast.smsforwardapp.di;

import android.content.Context;

import com.vanderkast.smsforwardapp.email.EmailSender;
import com.vanderkast.smsforwardapp.email.GMailSender;
import com.vanderkast.smsforwardapp.network.Api;
import com.vanderkast.smsforwardapp.sms.SmsDateFilter;

import java.util.function.Function;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DependencyProvider {
    private final Context context;
    private final Retrofit retrofit;

    public DependencyProvider(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(Settings.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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
        return retrofit.create(Api.class);
    }

    @Provides
    public EmailSender sender(GMailSender sender) {
        return sender;
    }
}
