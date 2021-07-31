package com.example.vortex.Modelo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class clRespuesta {

    private Integer idRespuesta;
    private String respuestaText;
    private int idPregunta;

    public int valorReturn = 1;

    //Instancias de HTTP
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-rest-vortex.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    public clRespuesta() {

    }

    public clRespuesta(String respuestaText, int idPregunta) {
        this.respuestaText = respuestaText;
        this.idPregunta = idPregunta;
    }

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getRespuestaText() {
        return respuestaText;
    }

    public void setRespuestaText(String respuestaText) {
        this.respuestaText = respuestaText;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }



    public int mtdCreaRespuesta(String respuestaText, int idPregun){

        clRespuesta respuesta = new clRespuesta(respuestaText, idPregun);

        Call<Void> callPost = jsonPlaceHolderApi.PostRespuesta(respuesta);

        callPost.enqueue(new Callback<Void>() {
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

    public int mtdActualiza(int idRespuesta, clRespuesta res){

        Call<Void> call = jsonPlaceHolderApi.PutRespuesta(idRespuesta, res);

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

    public int mtdElimina(int idRespuesta){

        Call<Void> call = jsonPlaceHolderApi.DeleteRespuesta(idRespuesta);

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
