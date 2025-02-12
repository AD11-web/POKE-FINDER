package com.example.project_1;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonserviceAPI {


    @GET("pokemon/{name}")
    Call<PokemonResponse> getPokemonInfo(@Path("name") String name);
}