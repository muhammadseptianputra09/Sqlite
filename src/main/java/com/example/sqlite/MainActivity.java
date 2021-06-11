package com.example.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.act_sql.R;
import com.example.sqlite.Database.Teman;
import com.example.sqlite.adapter.TemanAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private TemanAdapter adaptertmn;
    private ArrayList<Teman> temanArrayList = new ArrayList<>();
    private FloatingActionButton fabbtn;

    private static String url_select = "http://10.0.2.2:8090/tiumy/bacateman.php";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_ID = "id";
    private static final String TAG_Nama = "nama";
    private static final String TAG_Telpon = "telpon";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = findViewById(R.id.recyclerview);
        fabbtn = findViewById(R.id.floatingActionBtn);

        BacaData();
        adaptertmn = new TemanAdapter(temanArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adaptertmn);


        fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TambahTeman.class);
                startActivity(intent);
            }
        });
    }

    public void BacaData(){
        temanArrayList.clear();


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Teman item = new Teman();
                        item.setId(obj.getString(TAG_ID));
                        item.setNama(obj.getString(TAG_Nama));
                        item.setTelepon(obj.getString(TAG_Telpon));

                        temanArrayList.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adaptertmn.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "error : " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jArr);
    }

}