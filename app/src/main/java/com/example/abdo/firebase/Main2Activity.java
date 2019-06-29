package com.example.abdo.firebase;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {
    EditText email, pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main2);
        email = (EditText) findViewById(R.id.t1);
        pass = (EditText) findViewById(R.id.t2);
    }

    public void login(View view) {

        if(email.getText().toString().equals(""))
        {
            Toast.makeText(Main2Activity.this,"Pleas Enter Your Email", Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().equals(""))
        {
            Toast.makeText(Main2Activity.this,"Pleas Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (!task.isSuccessful()) {
                                Toast.makeText(Main2Activity.this, "failed",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Main2Activity.this, "success",
                                        Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getApplicationContext(), Main3Activity.class);
                                in.putExtra("Name", email.getText().toString());
                                startActivity(in);
                                email.setText("");
                                pass.setText("");
                            }


                        }


                    });
        }
    }


}
