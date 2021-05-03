package com.example.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqliteb.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import database.DBController;

public class teman_baru extends AppCompatActivity {

    private TextInputEditText tNama,tTelpon;
    private Button simpanBtn;
    String nm,tlp;
    DBController controller = new DBController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teman_baru);
        tNama = (TextInputEditText)findViewById(R.id.tietNama);
        tTelpon = (TextInputEditText)findViewById(R.id.tietTelpon);
        simpanBtn= (Button)findViewById(R.id.buttonSave);

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tNama.getText().toString().equals("")||tTelpon.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Data belum Komplit!", Toast.LENGTH_SHORT).show();
                }else{
                    nm = tNama.getText().toString();
                    tlp = tTelpon.getText().toString();

                    HashMap<String,String> qvalues = new HashMap<>();
                    qvalues.put("nama",nm);
                    qvalues.put("telpon",tlp);

                controller.insertData(qvalues);
                callHome();

                }
            }
        });
        
    }

    private void callHome() {
        Intent intent = new Intent(teman_baru.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
