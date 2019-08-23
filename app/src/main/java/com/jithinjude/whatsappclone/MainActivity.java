package com.jithinjude.whatsappclone;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewEmptyMsg;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDoalog;

    //List<ChatModel> chats = new ArrayList<>();
    //TODO: Hardrcoded data should replace with data from JSON
/*    private String imgUrl1 = "https://amp.businessinsider.com/images/5c420c0a17c2c5620a282ed3-750-563.jpg";
    private String imgUrl2 = "https://amp.businessinsider.com/images/5c0a8dcbd5000c1793675674-750-562.jpg";
    private String imgUrl3 = "https://timedotcom.files.wordpress.com/2016/06/gettyimages-495426754.jpg";
    private String imgUrl4 = "https://upload.wikimedia.org/wikipedia/commons/1/14/Mark_Zuckerberg_F8_2018_Keynote_%28cropped_2%29.jpg";
    private String imgUrl5 = "https://www.biography.com/.image/t_share/MTE1ODA0OTcxOTUyMDE0ODYx/elon-musk-20837159-1-402.png";
    private String imgUrl6 = "https://wallpapercave.com/wp/solVdHy.jpg";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading chats...");
        progressDoalog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<ChatModel>> call = service.getAllChats();
        call.enqueue(new Callback<List<ChatModel>>() {
            @Override
            public void onResponse(Call<List<ChatModel>> call, Response<List<ChatModel>> response) {
                progressDoalog.dismiss();
                Log.d("RESPONSE",response.body().toString());
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<ChatModel>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

/*        //TODO: Hardrcoded data should replace with data from JSON
        chats.add(new ChatModel("Bill Gates","Welcome to Microsoft","10:00 AM",imgUrl1));
        chats.add(new ChatModel("Jeff Bezos","Welcome to Amazon","09:00 AM",imgUrl2));
        chats.add(new ChatModel("Larry Page","Welcome to Alphabet","11:00 AM",imgUrl3));
        chats.add(new ChatModel("Mark Zuckerberg","Welcome to Facebook","12:30 PM",imgUrl4));
        chats.add(new ChatModel("Elon Musk","Welcome to SpaceX","11:00 AM",imgUrl5));
        chats.add(new ChatModel("Steve Jobs","Welcome to Apple","10:30 AM",imgUrl6));*/

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_chat);
        textViewEmptyMsg = findViewById(R.id.tv_empty_msg);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

/*        chatAdapter = new ChatAdapter(this, chats);
        recyclerView.setAdapter(chatAdapter);*/

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("CHAT")) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewEmptyMsg.setVisibility(View.INVISIBLE);
                }else if(tab.getText().equals("STATUS")){
                    recyclerView.setVisibility(View.INVISIBLE);
                    textViewEmptyMsg.setText("Status empty");
                    textViewEmptyMsg.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setVisibility(View.INVISIBLE);
                    textViewEmptyMsg.setText("Call logs empty");
                    textViewEmptyMsg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void generateDataList(List<ChatModel> chats) {
        chatAdapter = new ChatAdapter(this, chats);
        recyclerView.setAdapter(chatAdapter);
    }
}
