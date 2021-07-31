package com.example.vortex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vortex.Modelo.JsonPlaceHolderApi;
import com.example.vortex.Modelo.clPregunta;
import com.example.vortex.Modelo.clRespuesta;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class respuesta extends AppCompatActivity {

    TextView lblId, lblInfo, lblidRes;
    EditText txtrespuesta;
    Button btnenvia, btncancela, btnedita, btnborra, btnEliminaPregunta;
    String idPregunta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta);

        lblId = (TextView)findViewById(R.id.lblIdRespuesta);
        txtrespuesta = (EditText)findViewById(R.id.txtRespuesta);
        btnenvia = (Button)findViewById(R.id.btnEnviar);
        btncancela = (Button)findViewById(R.id.btnCancelar);
        btnedita = (Button)findViewById(R.id.btnEditar);
        btnborra = (Button)findViewById(R.id.btnBorrar);
        btnEliminaPregunta = (Button)findViewById(R.id.btnElimina);
        lblInfo = (TextView)findViewById(R.id.lblInfo);
        lblidRes = (TextView)findViewById(R.id.lblIdRes);

        idPregunta = getIntent().getStringExtra("idPregunta");

        lblId.setText(idPregunta);


        int idPre = Integer.parseInt(idPregunta);


        clPregunta pre = new clPregunta();
        clRespuesta res = new clRespuesta();

        btncancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(respuesta.this, index.class);
                startActivity(intent);
                finish();
            }
        });

        btnenvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String respuestaTex = txtrespuesta.getText().toString();

                if (respuestaTex.length() == 0){
                    modalExitoso("DIGITAR DATOS EN EL FORM");
                }else {
                    String resp = txtrespuesta.getText().toString();

                    int val = 0;

                    val = res.mtdCreaRespuesta(resp, idPre);

                    if (val == 1){
                        modalExitoso("respuesta CREADA con EXITO");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(respuesta.this, index.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


        btnEliminaPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int res = 0;
                res = pre.mtdElimina(idPre);

                if (res == 1){
                    modalExitoso("pregunta ELIMINADA con EXITO");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(respuesta.this, index.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-rest-vortex.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<clRespuesta>> call = jsonPlaceHolderApi.getRespuestas(idPre);

        call.enqueue(new Callback<List<clRespuesta>>() {
            @Override
            public void onResponse(Call<List<clRespuesta>> call, Response<List<clRespuesta>> response) {
                if (!response.isSuccessful()){

                    return;
                }

                List<clRespuesta> respuestas = response.body();

                if (respuestas.isEmpty() == true){
                    btnenvia.setVisibility(View.VISIBLE);
                    btncancela.setVisibility(View.VISIBLE);
                    btnEliminaPregunta.setVisibility(View.VISIBLE);
                    lblInfo.setVisibility(View.VISIBLE);

                    return;
                }else{
                    btnedita.setVisibility(View.VISIBLE);
                    btnborra.setVisibility(View.VISIBLE);

                }

                for (clRespuesta respuesta : respuestas ){

                    int id = 0;
                    String respuestaText = "";
                    int idPregunta = 0;

                    id += respuesta.getIdRespuesta();
                    respuestaText += respuesta.getRespuestaText();
                    idPregunta += respuesta.getIdPregunta();

                    clRespuesta respuestass = new clRespuesta();

                    respuestass.setIdRespuesta(id);
                    respuestass.setRespuestaText(respuestaText);
                    respuestass.setIdPregunta(idPregunta);

                    txtrespuesta.setText(respuestaText);

                    String idfinal = String.valueOf(id);

                    lblidRes.setText(idfinal);

                }


            }

            @Override
            public void onFailure(Call<List<clRespuesta>> call, Throwable t) {

            }
        });

        btnedita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String respuestaTex = txtrespuesta.getText().toString();

                if (respuestaTex.length() == 0){
                    modalExitoso("DIGITAR DATOS EN EL FORM");
                }else {
                    String idRespuesta = lblidRes.getText().toString();

                    int idRes = Integer.parseInt(idRespuesta);

                    String resText = txtrespuesta.getText().toString();
                    clRespuesta respuesta = new clRespuesta(resText, idPre);

                    int val = 0;

                    val = res.mtdActualiza(idRes, respuesta);

                    if (val == 1){
                        modalExitoso("respuesta ACTUALIZADA con EXITO");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(respuesta.this, index.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }



            }
        });

        btnborra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idRespuesta = lblidRes.getText().toString();
                int idRes = Integer.parseInt(idRespuesta);

                int val = 0;

                val = res.mtdElimina(idRes);

                if (val == 1){
                    modalExitoso("respuesta ELIMINADA con EXITO");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(respuesta.this, index.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void modalExitoso(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(respuesta.this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.exitoso, null);

        TextView lvlMensaje = (TextView)view.findViewById(R.id.lvlMensaje);

        lvlMensaje.setText(text);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}