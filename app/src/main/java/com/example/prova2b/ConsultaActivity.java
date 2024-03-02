package com.example.prova2b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ConsultaActivity extends AppCompatActivity {

    private TextView txtNome;
    private Button btnConsultar;
    private ListView lstConsulta;
    private Button btnVoltar;
    private Button btnSair;

    private EditText edtDataDe;
    private EditText edtDataAte;

    private RadioGroup radioGroupConsulta;
    private RadioButton rbReceitaConsulta;
    private RadioButton rbDespesaConsulta;
    private RadioButton rbTodos;

    public String nome;
    public String tipo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance(getBaseContext());

        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        txtNome = findViewById(R.id.txtNome);
        txtNome.setText("Bem vindo, " + nome.toUpperCase() + "!");
        edtDataDe = findViewById(R.id.edtDataDe);
        edtDataAte = findViewById(R.id.edtDataAte);
        btnConsultar = findViewById(R.id.btnConsultar);
        lstConsulta = findViewById(R.id.lstConsulta);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnSair = findViewById(R.id.btnSair);
        radioGroupConsulta = findViewById(R.id.radioGroupConsulta);
        rbDespesaConsulta = findViewById(R.id.rbDespesaConsulta);
        rbReceitaConsulta = findViewById(R.id.rbReceitaConsulta);
        rbTodos = findViewById(R.id.rbTodos);

        start(lancamentoDAO);

        radioGroupConsulta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rbReceitaConsulta){
                    //Salva como receita
                    tipo = "Receita";
                }else if(i == R.id.rbDespesaConsulta){
                    //Salva como despesa
                    tipo = "Despesa";
                }else if(i == R.id.rbTodos){
                    //Salva como despesa
                    tipo = "Todos";
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultaActivity.this, LancamentoActivity.class);
                intent.putExtra("nome", nome);
                startActivity(intent);
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataDe = edtDataDe.getText().toString();
                String dataAte = edtDataAte.getText().toString();

                if(!TextUtils.isEmpty(dataDe) && !TextUtils.isEmpty(dataAte)){
                    if(!tipo.equals("Todos")) {
                        List<String> dataList = lancamentoDAO.recuperarLancamentoCompleto(dataDe, dataAte, tipo);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ConsultaActivity.this, android.R.layout.simple_list_item_1, dataList);
                        lstConsulta.setAdapter(adapter);
                    }else{
                        List<String> dataList = lancamentoDAO.recuperarLancamentoDatas(dataDe, dataAte);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ConsultaActivity.this, android.R.layout.simple_list_item_1, dataList);
                        lstConsulta.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(ConsultaActivity.this, "Digite um intervalo válido para consulta!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void start(LancamentoDAO lancamentoDAO){
        List<String> dataList = lancamentoDAO.recuperarLancamentoAutomatico();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ConsultaActivity.this, android.R.layout.simple_list_item_1, dataList);
        lstConsulta.setAdapter(adapter);
        lstConsulta.deferNotifyDataSetChanged();
    }
}