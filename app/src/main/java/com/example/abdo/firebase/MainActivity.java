package com.example.abdo.firebase;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
EditText email ,pass ,name , phone ;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    private FirebaseAuth mAuth;
    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText) findViewById(R.id.t1) ;
        pass=(EditText) findViewById(R.id.t2) ;
        name =(EditText) findViewById(R.id.t3) ;
        phone = (EditText) findViewById(R.id.t4) ;

        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("Accounts");


    }
    public  void  Sign  (View view)
    {

        if(email.getText().toString().equals(""))
        {
            Toast.makeText(MainActivity.this,"Pleas Enter Your Email", Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().equals(""))
        {
            Toast.makeText(MainActivity.this,"Pleas Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        else if(name.getText().toString().equals(""))
        {
            Toast.makeText(MainActivity.this,"Pleas Enter Your Name", Toast.LENGTH_SHORT).show();
        }
        else if(phone.getText().toString().equals(""))
        {
            Toast.makeText(MainActivity.this,"Pleas Enter Your Phone", Toast.LENGTH_SHORT).show();
        }

else {
            progressDialog.setTitle("Wating");
            progressDialog.setMessage("Pleas wait.....");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                Toast.makeText(MainActivity.this, "....Eroor....", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {

                                Map<String, String> map = new HashMap<String,String>();
                                map.put("Account", email.getText().toString());
                                map.put("password", pass.getText().toString());
                                map.put("name", name.getText().toString());
                                map.put("phone", phone.getText().toString());
                                myRef.push().setValue(map);
                                Toast.makeText(MainActivity.this, "....success....", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            email.setText("");
                            pass.setText("");
                            phone.setText("");
                            name.setText("");


                        }
                    });
        }
    }

    public void move(View view) {
        Intent intent = new Intent(this , Main2Activity.class) ;
        startActivity(intent);
    }
}
