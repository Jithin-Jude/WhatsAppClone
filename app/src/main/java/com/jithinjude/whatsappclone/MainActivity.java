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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private TextView textViewEmptyMsg;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        progressDialogue = new ProgressDialog(MainActivity.this);
        progressDialogue.setMessage("Loading chats...");
        progressDialogue.show();

        getChatData();

        recyclerView = findViewById(R.id.recyclerview_chat);
        textViewEmptyMsg = findViewById(R.id.tv_empty_msg);
        tabLayout = findViewById(R.id.tab_layout);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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

    private void getChatData(){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<ChatModel>> call = service.getAllChats();
        call.enqueue(new Callback<List<ChatModel>>() {
            @Override
            public void onResponse(Call<List<ChatModel>> call, Response<List<ChatModel>> response) {
                loadData(response.body());
            }

            @Override
            public void onFailure(Call<List<ChatModel>> call, Throwable t) {
                progressDialogue.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(List<ChatModel> chats) {
        chatAdapter = new ChatAdapter(this, chats);
        recyclerView.setAdapter(chatAdapter);
        progressDialogue.dismiss();
    }
}
