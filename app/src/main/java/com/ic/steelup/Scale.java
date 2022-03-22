package com.ic.steelup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Scale extends AppCompatActivity {

    private double largura = 0;
    private double comprimento = 0;
    private double altura = 0;
    private double largura_dos_corredores = 0;
    private String tipo_sistema = "";
    private double largura_do_montante = 0;
    private double altura_entre_prateleiras = 0;
    private String tipo_contraventamento = "";

    private final double ESPACO_FIXO_DUPLO = 0.15;
    private final double BASE_ESTANTE = 0.0;
    private final double ALTURA_CONTRAVENTAMENTO = 0.6;

    private double comprimento_disponivel = 0;
    private int quantidade_corredores = 0;
    private double altura_disponivel = 0;
    private int filas_prateleiras = 0;
    private int quantidade_vigas = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        ImageButton ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(v -> goBack());

        getAllExtras();
        setDistribuicao();
        setContraventamentos();
        setVigas();

    }

    private void setDistribuicao() {
        //calc quantidade de corredores
        double bloco = 0;
        if (tipo_sistema.equals("Simples")) {
            bloco = largura_do_montante + largura_dos_corredores;

        } else if (tipo_sistema.equals("Duplo")) {
            bloco = largura_do_montante + ESPACO_FIXO_DUPLO + largura_do_montante + largura_dos_corredores;
        }
        comprimento_disponivel = comprimento - largura_dos_corredores;
        quantidade_corredores = ((int) (comprimento_disponivel / bloco)) + 1;
        TextView quantidade_corredores_view = findViewById(R.id.value_passage);
        quantidade_corredores_view.setText(Integer.toString(quantidade_corredores));

        //calc filas de prateleiras
        altura_disponivel = altura - BASE_ESTANTE;
        filas_prateleiras = (int) (altura_disponivel / altura_entre_prateleiras);
        TextView filas_prateleiras_view = findViewById(R.id.value_shelf);
        filas_prateleiras_view.setText(Integer.toString(filas_prateleiras));
    }

    private void setContraventamentos() {
        //calc espacamentos
        double quantidade_espacamentos = altura_disponivel / ALTURA_CONTRAVENTAMENTO;
        boolean exato = false;
        int quantidade_horizontal = 1;
        int quantidade_diagonal = 0;
        if (quantidade_espacamentos == (int) quantidade_espacamentos) {
            exato = true;
            quantidade_horizontal = 0;
        }
        //calc quantidade de contraventamentos horizontais e diagonais
        switch (tipo_contraventamento) {
            case "Tensão":
                quantidade_horizontal = ((quantidade_horizontal + (int) quantidade_espacamentos + 1) * (quantidade_corredores - 1)) * 2;
                quantidade_diagonal = (((int) quantidade_espacamentos * 2) * (quantidade_corredores - 1)) * 2;
                break;
            case "D":
                quantidade_horizontal = ((quantidade_horizontal + 2) * (quantidade_corredores - 1)) * 2;
                quantidade_diagonal = (((int) quantidade_espacamentos) * (quantidade_corredores - 1)) * 2;

                break;
            case "Z":
            case "K":
                quantidade_horizontal = ((quantidade_horizontal + (int) quantidade_espacamentos + 1) * (quantidade_corredores - 1)) * 2;
                quantidade_diagonal = (((int) quantidade_espacamentos) * (quantidade_corredores - 1)) * 2;
                break;
        }
        if (tipo_sistema.equals("Duplo")) {
            quantidade_horizontal *= 2;
            quantidade_diagonal *= 2;
        }
        TextView qt_horizontal_view = findViewById(R.id.value_amount_frames_hor);
        qt_horizontal_view.setText(Integer.toString(quantidade_horizontal));
        TextView qt_diagonal_view = findViewById(R.id.value_amount_frames_diag);
        qt_diagonal_view.setText(Integer.toString(quantidade_diagonal));

    }

    private void setVigas() {
        //calc quantidade de vigas
        quantidade_vigas = (filas_prateleiras * 2) * (quantidade_corredores - 1);
        if (tipo_sistema.equals("Duplo")) {
            quantidade_vigas *= 2;
        }
        TextView qt_vigas_view = findViewById(R.id.value_amount_beams);
        qt_vigas_view.setText(Integer.toString(quantidade_vigas));
    }

    private void getAllExtras() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            largura = extras.getDouble("largura");
            altura = extras.getDouble("altura");
            comprimento = extras.getDouble("comprimento");
            largura_dos_corredores = extras.getDouble("largura_dos_corredores");
            tipo_sistema = extras.getString("tipo_sistema");
            largura_do_montante = extras.getDouble("largura_do_montante");
            altura_entre_prateleiras = extras.getDouble("altura_entre_prateleiras");
            tipo_contraventamento = extras.getString("tipo_contraventamento");
        } else {
            Toast.makeText(Scale.this, R.string.warning_extras, Toast.LENGTH_SHORT).show();
            goBack();
        }
    }

    private void goBack() {
        finish();
    }

}