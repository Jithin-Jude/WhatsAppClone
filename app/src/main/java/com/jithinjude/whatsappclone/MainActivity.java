package com.jithinjude.whatsappclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<ChatModel> chats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        chats.add(new ChatModel("Bill Gates","Welcome to microsoft","10:00 AM"));
        chats.add(new ChatModel("Jeff Bezos","Welcome to amazon","09:00 AM"));
        chats.add(new ChatModel("Larry Page","Welcome to alphabet","11:00 AM"));
        chats.add(new ChatModel("Mark Zuckerberg","Welcome to facebook","12:30 PM"));
        chats.add(new ChatModel("Elon Musk","Welcome to SpaceX","11:00 AM"));
        chats.add(new ChatModel("Steve Jobs","Welcome to Apple","10:30 AM"));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_chat);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chatAdapter = new ChatAdapter(chats);
        recyclerView.setAdapter(chatAdapter);
    }
}
