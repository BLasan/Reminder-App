package com.example.benura.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

   public Button login,exit;
   private TextView txt1,txt2;
   public  EditText ed1,ed2;
  public String string4,string5;
  private FirebaseDatabase database1=FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1=(EditText)findViewById(R.id.userEdit);
        ed2=(EditText)findViewById(R.id.emailEdit);
        login=(Button)findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference();
                    string4=ed1.getText().toString();
                    myref.child(string4);
                    String strings=ed2.getText().toString();
                    Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Login.class);
                    String getrec=ed1.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("d", string4);
                    bundle.putString("e",strings);
                    i.putExtras(bundle);
                    startActivity(i);
                    ed1.setText(null);
                    ed2.setText(null);

                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        exit=(Button)findViewById(R.id.exitBtn);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               database1.goOffline();
               finishAffinity();
            }
        });
    }
}