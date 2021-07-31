package com.example.vortex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vortex.Modelo.clPregunta;

public class addPregunta extends AppCompatActivity {

    Button btnRegistrarPregunta;
    TextView txtPregunta;
    String pregunta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pregunta);

        btnRegistrarPregunta = (Button)findViewById(R.id.btnCreaPregunta);
        txtPregunta = (TextView)findViewById(R.id.txtPreguntaText);

        btnRegistrarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pregunta = txtPregunta.getText().toString();

                if (pregunta.length() == 0){
                    modalExitoso("DIGITAR DATOS EN EL FORM");
                }else {
                    clPregunta pregu = new clPregunta();

                    int val = 0;
                    val = pregu.mtdCreaPregunta(pregunta);

                    if (val == 1){
                        modalExitoso("pregunta CREADA con EXITO");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(addPregunta.this, index.class);
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

    }

    public void modalExitoso(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(addPregunta.this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.exitoso, null);

        TextView lvlMensaje = (TextView)view.findViewById(R.id.lvlMensaje);

        lvlMensaje.setText(text);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}