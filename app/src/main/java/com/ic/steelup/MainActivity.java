package com.ic.steelup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView tipo_sistema;
    private AutoCompleteTextView tipo_contraventamento;
    private TextInputEditText largura;
    private TextInputEditText comprimento;
    private TextInputEditText altura;
    private TextInputEditText largura_dos_corredores;
    private TextInputEditText largura_do_montante;
    private TextInputEditText altura_entre_prateleiras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tipo_sistema = findViewById(R.id.input_system_type);
        tipo_contraventamento = findViewById(R.id.input_frame_bracing);
        Button button_scale = findViewById(R.id.button_scale);
        largura = findViewById(R.id.input_width);
        comprimento = findViewById(R.id.input_lenght);
        altura = findViewById(R.id.input_height);
        largura_dos_corredores = findViewById(R.id.input_passage_width);
        largura_do_montante = findViewById(R.id.input_width_frames);
        altura_entre_prateleiras = findViewById(R.id.input_space_shelfs);


        ArrayAdapter<CharSequence> adapter_system = ArrayAdapter.createFromResource(
                MainActivity.this,
                R.array.array_system_type,
                R.layout.dropdown_item
        );
        tipo_sistema.setAdapter(adapter_system);

        ArrayAdapter<CharSequence> adapter_bracing = ArrayAdapter.createFromResource(
                MainActivity.this,
                R.array.array_frame_bracing,
                R.layout.dropdown_item
        );
        tipo_contraventamento.setAdapter(adapter_bracing);

        button_scale.setOnClickListener(v -> scale());

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void scale() {
        String largura_value = Objects.requireNonNull(largura.getText()).toString();
        String comprimento_value = Objects.requireNonNull(comprimento.getText()).toString();
        String altura_value = Objects.requireNonNull(altura.getText()).toString();
        String largura_dos_corredores_value = Objects.requireNonNull(largura_dos_corredores.getText()).toString();
        String tipo_sistema_value = tipo_sistema.getText().toString();
        String largura_do_montante_value = Objects.requireNonNull(largura_do_montante.getText()).toString();
        String altura_entre_prateleiras_value = Objects.requireNonNull(altura_entre_prateleiras.getText()).toString();
        String tipo_contraventamento_value = tipo_contraventamento.getText().toString();

        if (
                largura_value.isEmpty() ||
                        comprimento_value.isEmpty() ||
                        altura_value.isEmpty() ||
                        largura_dos_corredores_value.isEmpty() ||
                        tipo_sistema_value.isEmpty() ||
                        largura_do_montante_value.isEmpty() ||
                        altura_entre_prateleiras_value.isEmpty() ||
                        tipo_contraventamento_value.isEmpty()
        ) {
            Toast.makeText(MainActivity.this, R.string.warning_preencher, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, Scale.class);
            intent.putExtra("largura", Double.parseDouble(largura_value));
            intent.putExtra("altura", Double.parseDouble(altura_value));
            intent.putExtra("comprimento", Double.parseDouble(comprimento_value));
            intent.putExtra("largura_dos_corredores", Double.parseDouble(largura_dos_corredores_value));
            intent.putExtra("tipo_sistema", tipo_sistema_value);
            intent.putExtra("largura_do_montante", Double.parseDouble(largura_do_montante_value));
            intent.putExtra("altura_entre_prateleiras", Double.parseDouble(altura_entre_prateleiras_value));
            intent.putExtra("tipo_contraventamento", tipo_contraventamento_value);
            startActivity(intent);
        }

    }
}