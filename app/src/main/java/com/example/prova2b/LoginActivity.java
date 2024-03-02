package com.example.prova2b;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_usuario;
    private EditText edt_senha;
    private CheckBox ckb_senha;
    private Button btn_login;
    private Button btn_registrar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(getBaseContext());

        edt_usuario = findViewById(R.id.edt_usuario);
        edt_senha = findViewById(R.id.edt_senha);
        ckb_senha = findViewById(R.id.ckbSenha);
        btn_login = findViewById(R.id.btn_login);
        btn_registrar = findViewById(R.id.btn_registrar);

        ckb_senha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edt_senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else{
                    edt_senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuarioInput = edt_usuario.getText().toString();
                String senhaInput = edt_senha.getText().toString();

                if(!TextUtils.isEmpty(usuarioInput) && !TextUtils.isEmpty(senhaInput)){
                    if(usuarioDAO.recuperarUsuarioNomeSenha(new Usuario(edt_usuario.getText().toString(), edt_senha.getText().toString())) != null){
                        Intent intent = new Intent(LoginActivity.this, LancamentoActivity.class);
                        intent.putExtra("nome", usuarioInput);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Usuário não cadastrado!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               String usuarioInput = edt_usuario.getText().toString();
               String senhaInput = edt_senha.getText().toString();

               if(!TextUtils.isEmpty(usuarioInput) && !TextUtils.isEmpty(senhaInput)){
                   if(usuarioDAO.recuperarUsuarioNome(new Usuario(usuarioInput, senhaInput)) != null){
                       Toast.makeText(LoginActivity.this, "Nome já utilizado, escolha outra opção!", Toast.LENGTH_SHORT).show();
                   }
                   else{
                       usuarioDAO.salvar(new Usuario(usuarioInput, senhaInput));
                       Toast.makeText(LoginActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(LoginActivity.this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
               }
           }
        });
    }
}