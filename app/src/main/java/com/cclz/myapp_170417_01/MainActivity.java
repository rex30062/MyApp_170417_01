package com.cclz.myapp_170417_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   /* volley
       1. 建立一個 queue
       2. 建立一個 request ( a. 成功了怎麼辦  b. 失敗了怎麼辦)
       3. 把 z 加入 1
       4. 叫 queue 跑

       JSON
       [] 陣列
       {} 物件
    */

    ListView lv;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        final StringRequest request=new StringRequest("http://data.ntpc.gov.tw/od/data/api/BF90FA7E-C358-4CDA-B579-B6C84ADC96A1?$format=json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("NET", response);
                        try {
                            JSONArray array=new JSONArray(response);
                            for(int i=0; i<array.length(); i++){
                                JSONObject obj=array.getJSONObject(i);
                                String s=obj.getString("district");
                                Log.d("NET", s);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Gson gson=new Gson();
                            ArrayList<Animal> mylist=gson.fromJson(response, new TypeToken<ArrayList<Animal>>() {}.getType());
                            ArrayList<String> strlist = new ArrayList<>();
                            for(Animal a:mylist){
                                Log.d("NET", a.district);
                                strlist.add(a.district);
                            }
                            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, strlist);
                            lv.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        queue.start();



    }
}
