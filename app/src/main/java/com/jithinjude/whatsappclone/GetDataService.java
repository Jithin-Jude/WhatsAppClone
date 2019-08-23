package com.jithinjude.whatsappclone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by <Jithin/Jude> on 23,August,2019.
 * jithin.jude68@gmail.com
 */
public interface GetDataService {

    @GET("vvkub")
    Call<List<ChatModel>> getAllChats();
}
