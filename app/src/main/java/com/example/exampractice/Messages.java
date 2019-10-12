package com.example.exampractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import database.DBHelper;
import database.Tables;

public class Messages extends AppCompatActivity {

    TextView textViewUser , textViewSubject , textViewMessage;
    DBHelper dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Intent i = getIntent();

        textViewUser = (TextView)findViewById(R.id.textViewMessagesUser);
        textViewSubject = (TextView)findViewById(R.id.textViewMessagesSubject);
        textViewMessage = (TextView)findViewById(R.id.textViewMessagesMessage);

        dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.getLatestMessage();

        if(cursor.moveToNext()){
            textViewMessage.setText(cursor.getString(cursor.getColumnIndexOrThrow(Tables.Message.COLUMN_MESSAGE)));
            textViewSubject.setText(cursor.getString(cursor.getColumnIndexOrThrow(Tables.Message.COLUMN_SUBJECT)));
            textViewUser.setText(cursor.getString(cursor.getColumnIndexOrThrow(Tables.Message.COLUMN_USER)));
        }


    }
}
