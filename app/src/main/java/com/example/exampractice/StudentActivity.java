package com.example.exampractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import database.DBHelper;
import database.Tables;

public class StudentActivity extends AppCompatActivity {

    TextView textViewWelcome;
    ListView listViewMessages;
    DBHelper dh;
    private static final String CHANNEL_ID = "chanel1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Intent i = getIntent();
        String userName = i.getStringExtra("userName");





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CNN";
            String description ="Tell lies";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, Messages.class);
        intent.putExtra("notification","noti");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("New Message")
                .setContentText("click here to view")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        int notificationId = 8;
        notificationManager.notify(notificationId, builder.build());

        textViewWelcome= (TextView)findViewById(R.id.textViewStudentWelcome);



        listViewMessages = (ListView)findViewById(R.id.ListViewStudentMeassages);


        dh = new DBHelper(getApplicationContext());

        List<Message> mlist = new ArrayList<>();


        mlist = dh.getAllMessage();
        List<String> userNames=new ArrayList<>();
        List<String> ID=new ArrayList<>();
        List<String> messageList=new ArrayList<>();


        int j=0;

        for (Message message:mlist){

            userNames.add( "message from :"+message.getUname());
            ID.add(message.getId());
            messageList.add(message.getMessage());
        }

        String[] projection = {Tables.Message._ID,Tables.Message.COLUMN_USER, Tables.Message.COLUMN_SUBJECT , Tables.Message.COLUMN_MESSAGE};
        int[] toViews = {R.id.textViewSMLID,R.id.textViewSMLSubject,R.id.textViewSMLmessage,R.id.textViewSMLTeacher};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.single_message_list,dh.getAllmessagesCursor(),projection,toViews,0);
        //ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,R.layout.single_message_list,messageList);

        //listViewMessages.setAdapter(stringArrayAdapter);

        listViewMessages.setAdapter(simpleCursorAdapter);

       /* listViewMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(getApplicationContext(),Messages.class);
                intent1.putExtra("teacherName",(String)listViewMessages.getItemAtPosition(i));
                intent1.putExtra("Subject",(String)listViewMessages.getItemAtPosition(i));
                intent1.putExtra("Message",(String)listViewMessages.getItemAtPosition(i));
                startActivity(intent1);
            }
        });
        */
       listViewMessages.setOnItemClickListener(new ListView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Cursor c = (Cursor) adapterView.getItemAtPosition(i);
               Message message= new Message();

               message = dh.getClickedmessage(c.getString(c.getColumnIndexOrThrow(Tables.Message._ID)));

               Intent intent1 = new Intent(getApplicationContext(),Messages.class);
               intent1.putExtra("Message", message.getMessage() );
               intent1.putExtra("Subject", message.getSubject() );
               intent1.putExtra("teacherName", message.getUname() );
               intent1.putExtra("from","item");
               startActivity(intent1);

           }
       });

        textViewWelcome.setText("Welcome "+userName);
    }
}
