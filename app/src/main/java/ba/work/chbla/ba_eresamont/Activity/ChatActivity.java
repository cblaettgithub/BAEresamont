package ba.work.chbla.ba_eresamont.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.DateFormat;

import ba.work.chbla.ba_eresamont.Models.ChatMessage;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {
    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ba.work.chbla.ba_eresamont.R.layout.activity_chat);
        final String username= getIntent().getExtras().getString("User");
        DisplayMessage();

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(ba.work.chbla.ba_eresamont.R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(ba.work.chbla.ba_eresamont.R.id.input);
               FirebaseDatabase.getInstance().getReference().push()
                       .setValue(new ChatMessage(input.getText().
                               toString(),username));
               input.setText("");

            }

        });

    }
    public void DisplayMessage(){
        ListView listOfMessages = (ListView)findViewById(ba.work.chbla.ba_eresamont.R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                ba.work.chbla.ba_eresamont.R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(ba.work.chbla.ba_eresamont.R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(ba.work.chbla.ba_eresamont.R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(ba.work.chbla.ba_eresamont.R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }

}
