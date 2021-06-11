package com.example.sqlite.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.act_sql.R;
import com.example.sqlite.Database.Teman;
import com.example.sqlite.EditTeman;
import com.example.sqlite.MainActivity;
import com.example.sqlite.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listData;
    private Context control;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }
    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private TextView namaTxtView, telponTxtView;

        public TemanViewHolder(View view) {
            super(view);
            card = (CardView) view.findViewById(R.id.kartuku);
            namaTxtView = (TextView) view.findViewById(R.id.textId);
            telponTxtView = (TextView) view.findViewById(R.id.textTelepon);

        }
    }

    public TemanAdapter (Context control, ArrayList<Teman> listData){

        this.listData = listData;
    }
    @Override
    public TemanAdapter.TemanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View v = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        control = parent.getContext();
        return new TemanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TemanAdapter.TemanViewHolder holder, int position) {
        String nm,tlp,no;

        no = listData.get(position).getId();
        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelepon();
        //DBController db = new DBController(control);

        holder.namaTxtView.setText(nm);
        holder.namaTxtView.setTextSize(30);
        holder.telponTxtView.setText(tlp);

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(control, holder.card);
                popupMenu.inflate(R.menu.popupmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
                                Bundle bundle = new Bundle();
                                bundle.putString("id",no);
                                bundle.putString("nama",nm);
                                bundle.putString("telepon",tlp);

                                Intent intent = new Intent(v.getContext(), EditTeman.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.hapus:
                                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                                alertDlg.setTitle("Yakin "+nm+" akan dihapus ?");
                                alertDlg.setMessage("Tekan Ya untuk menghapus")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Hapus(no);
                                                Toast.makeText(v.getContext(),"Data berhasil dihapus", Toast.LENGTH_SHORT ).show();
                                            }
                                        })
                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog aDlg = alertDlg.create();
                                aDlg.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }







    private void Hapus(final String idx)
    {
        final int[] success = new int[1];

        String url_delete = "http://10.0.2.2:8090/tiumy/deletetmn.php";
        final String TAG = MainActivity.class.getSimpleName();
        final String TAG_SUCCES = "success";
        String tag_json_obj = "json_obj_req";

        StringRequest strReq2 = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response : " + response.toString());
                try{
                    JSONObject jObj = new JSONObject(response);
                    success [0] = jObj.getInt(TAG_SUCCES);
                }catch (JSONException e){
                    e.printStackTrace();
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.toString());
            }

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", idx);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq2, tag_json_obj);
    }
}