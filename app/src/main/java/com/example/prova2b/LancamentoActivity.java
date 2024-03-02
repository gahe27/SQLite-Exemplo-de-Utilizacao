package com.example.prova2b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LancamentoActivity extends AppCompatActivity {

    private TextView txtNome;
    private EditText edtData;
    private EditText edtValor;
    private RadioGroup radioGroup;
    private RadioButton rbReceita;
    private RadioButton rbDespesa;
    private Button btnSalvarL;
    private Button btnConsultarL;
    private Button btnSair;

    public String nome;

    public String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento);

        LancamentoDAO lancamentoDAO = LancamentoDAO.getInstance(getBaseContext());


        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(getBaseContext());
        String idUsuario = String.valueOf(usuarioDAO.recuperarUsuarioNome(new Usuario(nome)).getIdUsuario());

        txtNome = findViewById(R.id.txtNome);
        txtNome.setText("Bem vindo, " + nome.toUpperCase() +"!");
        edtData = findViewById(R.id.edtData);
        edtValor = findViewById(R.id.edtValor);
        radioGroup = findViewById(R.id.radioGroup);
        rbReceita = findViewById(R.id.rbReceita);
        rbDespesa = findViewById(R.id.rbDespesa);
        rbDespesa.setSelected(true);
        btnSalvarL = findViewById(R.id.btnSalvarL);
        btnConsultarL = findViewById(R.id.btnConsultarL);
        btnSair = findViewById(R.id.btnSair);

        btnConsultarL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LancamentoActivity.this, ConsultaActivity.class);
                intent.putExtra("nome", nome);
                startActivity(intent);
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LancamentoActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rbReceita){
                    //Salva como receita
                    tipo = "Receita";
                }else if(i == R.id.rbDespesa){
                    //Salva como despesa
                    tipo = "Despesa";
                }
            }
        });

        btnSalvarL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valorF = edtValor.getText().toString();
                if(!TextUtils.isEmpty(edtData.getText().toString()) && !TextUtils.isEmpty(valorF)){
                    if(tipo.equals("Despesa")) valorF = "-"+valorF;
                    Lancamento lancamento = new Lancamento(nome, valorF, edtData.getText().toString(), tipo);
                    lancamentoDAO.salvar(lancamento);
                    Toast.makeText(LancamentoActivity.this, "Lançamento registrado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LancamentoActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}