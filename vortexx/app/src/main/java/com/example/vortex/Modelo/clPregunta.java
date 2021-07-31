package com.example.vortex.Modelo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class clPregunta {

    private Integer idPregunta;
    private String preguntaText;
    public int valorReturn = 1;


    //Instancias de HTTP
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-rest-vortex.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);



    public clPregunta() {

    }

    public clPregunta(String preguntaText) {
        this.idPregunta = idPregunta;
        this.preguntaText = preguntaText;
    }

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getPreguntaText() {
        return preguntaText;
    }

    public void setPreguntaText(String preguntaText) {
        this.preguntaText = preguntaText;
    }

    public int mtdCreaPregunta(String preguntaText){
        clPregunta pregunta = new clPregunta(preguntaText);

        Call<Void> calPost =jsonPlaceHolderApi.PostPregunta(pregunta);

        calPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){

                    valorReturn = 0;
                    return;
                }

                valorReturn = 1;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                valorReturn = 0;
                return;
            }
        });

        return valorReturn;
    }

    public int mtdElimina(int idPregunta){

        Call<Void> call = jsonPlaceHolderApi.DeletePregunta(idPregunta);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){

                    valorReturn = 0;
                    return;
                }

                valorReturn = 1;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                valorReturn = 0;
                return;
            }
        });

        return valorReturn;

    }



}
