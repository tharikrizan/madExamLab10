package com.example.exampractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.DBHelper;
import database.Tables;

public class loginActivity extends AppCompatActivity {

    EditText editTextUserName , editTextPassword ;
    Button buttonLogin , buttonRegister;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUserName = (EditText)findViewById(R.id.editTextLoginUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextLoginPassword);

        buttonLogin = (Button)findViewById(R.id.buttonLoginLogin);
        buttonRegister =(Button)findViewById(R.id.buttonLoginRegister);

        dbHelper = new DBHelper(getApplicationContext());


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login[] = dbHelper.login(editTextUserName.getText().toString().trim(),editTextPassword.getText().toString());
                if(login[0]!= null) {
                    Toast.makeText(loginActivity.this, "username :" + login[0] + "user type: "+login[1], Toast.LENGTH_SHORT).show();
                    if(login[1].equals("Student")){
                        Intent intent = new Intent(loginActivity.this , StudentActivity.class );
                        intent.putExtra("userName",login[0]);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(loginActivity.this , TeacherActivity.class );
                        intent.putExtra("userName",login[0]);
                        startActivity(intent);
                    }

                }
                else {
                    Toast.makeText(loginActivity.this, "username : null"  , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
