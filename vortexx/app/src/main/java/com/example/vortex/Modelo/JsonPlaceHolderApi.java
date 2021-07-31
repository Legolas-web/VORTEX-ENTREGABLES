package com.example.vortex.Modelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("preguntas")
    Call<List<clPregunta>> getPteguntas();

    @POST("add_pregunta")
    Call<Void> PostPregunta(@Body clPregunta pregunta);

    @DELETE("del_pregunta/{id}/")
    Call<Void> DeletePregunta(@Path("id") int idPregunta);

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    @GET("respuesta/{id}/")
    Call<List<clRespuesta>> getRespuestas(@Path("id") int idPregunta);

    @PUT("put_respuesta/{id}/")
    Call<Void> PutRespuesta(@Path("id") int idRespuesta, @Body clRespuesta respuesta);

    @POST("add_respuesta")
    Call<Void> PostRespuesta(@Body clRespuesta respuesta);

    @DELETE("del_respuesta/{id}/")
    Call<Void> DeleteRespuesta(@Path("id") int idRespuesta);




}
