package com.example.project_1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pokefinder extends AppCompatActivity {

    private EditText PokemonName;
    private ImageView PokemonImage;
    private TextView PokemonDetails;

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokefinder);

        PokemonName = findViewById(R.id.PokemonName);
        Button btnSearch = findViewById(R.id.btnSearch);
        PokemonImage = findViewById(R.id.PokemonImage);
        PokemonDetails = findViewById(R.id.PokemonDetails);

        btnSearch.setOnClickListener(view -> fetchPokemonData());
    }

    private void fetchPokemonData() {
        String pokemonName = PokemonName.getText().toString().trim().toLowerCase();

        if (pokemonName.isEmpty()) {
            Toast.makeText(Pokefinder.this, "Enter Pokémon Name!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonserviceAPI apiService = retrofit.create(PokemonserviceAPI.class);
        Call<PokemonResponse> call = apiService.getPokemonInfo(pokemonName);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, @NonNull Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonResponse pokemon = response.body();

                    Log.d("PokemonResponse", Objects.requireNonNull(response.body()).toString());

                    // Fetch Pokémon image
                    String imageUrl = pokemon.getSprites().getOther().getOfficialArtwork().getFrontDefault();

                    if (imageUrl == null || imageUrl.isEmpty()) {
                        imageUrl = pokemon.getSprites().getFrontDefault();
                    }

                    // Display Pokémon Details
                    String details = "Name: " + pokemon.getName().toUpperCase() +
                            "\nType: " + pokemon.getTypes().get(0).getType().getName().toUpperCase();

                    PokemonDetails.setText(details);

                    // Load Pokémon image dynamically using Glide
                    Glide.with(Pokefinder.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.placeholder) // Show while loading
                            .error(R.drawable.error) // Show if failed
                            .into(PokemonImage);
                } else {
                    Toast.makeText(Pokefinder.this, "Pokémon not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonResponse> call, @NonNull Throwable t) {
                Toast.makeText(Pokefinder.this, "Error fetching data!", Toast.LENGTH_SHORT).show();
                Log.e("API Error", Objects.requireNonNullElse(t.getMessage(), "Unknown Error"));

            }
        });
    }
}
