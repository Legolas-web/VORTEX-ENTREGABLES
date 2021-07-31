package com.example.vortex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vortex.Modelo.JsonPlaceHolderApi;
import com.example.vortex.Modelo.clPregunta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.layout.simple_expandable_list_item_1;
import static android.R.layout.simple_list_item_1;


public class index extends AppCompatActivity {

    ListView lvPregunta;
    FloatingActionButton btnFlotante;

    ArrayList<clPregunta> listPre;
    ArrayList<String> listInfo;
    private TextView lblTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        lvPregunta = (ListView)findViewById(R.id.lvPreguntas);
        lblTitle = (TextView)findViewById(R.id.lblTitle);
        btnFlotante = (FloatingActionButton)findViewById(R.id.floatingActionButton2);

        listInfo = new ArrayList<String>();
        listPre = new ArrayList<clPregunta>();


        btnFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(index.this, addPregunta.class);
                startActivity(intent);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-rest-vortex.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<clPregunta>> call = jsonPlaceHolderApi.getPteguntas();

        call.enqueue(new Callback<List<clPregunta>>() {
            @Override
            public void onResponse(Call<List<clPregunta>> call, Response<List<clPregunta>> response) {
                if (!response.isSuccessful()){
                    lblTitle.setText("code: " + response.code());
                    return;
                }

                List<clPregunta> preg = response.body();

                for (clPregunta pregunta : preg ){

                    int id = 0;
                    String preguntaText = "";

                    id += pregunta.getIdPregunta();
                    preguntaText += pregunta.getPreguntaText();

                    clPregunta preguntas = new clPregunta();

                    preguntas.setIdPregunta(id);
                    preguntas.setPreguntaText(preguntaText);

                    listPre.add(preguntas);

                }

                for (int i = 0; i < listPre.size(); i ++){
                    listInfo.add("    - " + listPre.get(i).getPreguntaText());
                }

                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), simple_list_item_1, listInfo);
                lvPregunta.setAdapter(adapter);

                lvPregunta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String idPre = ""+ listPre.get(position).getIdPregunta();

                        Intent intent = new Intent(index.this, respuesta.class);
                        intent.putExtra("idPregunta", idPre);
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onFailure(Call<List<clPregunta>> call, Throwable t) {
                lblTitle.setText(t.getMessage());
            }
        });



    }
}