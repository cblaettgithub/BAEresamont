package ba.work.chbla.ba_eresamont.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.util.TreeMap;

import ba.work.chbla.ba_eresamont.Models.ChatMessage;
import ba.work.chbla.ba_eresamont.R;

public class ChatFragment extends Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseListAdapter<ChatMessage> adapter;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    View mview;

    ChatFragment.OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(TreeMap hashMap, String choice, String mTitle);
    }
    public void onListItemClick(ListView l, View v, TreeMap hashMap, String choice) {
        mCallback.onArticleSelected(hashMap, choice, "");
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //setContentView(R.layout.activity_chat);
        //final String username= getIntent().getExtras().getString("User");
        mview = inflater.inflate(ba.work.chbla.ba_eresamont.R.layout.fragment_chat, container, false);

        Bundle bundle = getArguments();
        setUsername(bundle.getString("username"));
        FloatingActionButton fab =
                (FloatingActionButton) mview.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)mview.findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push()
                        .setValue(new ChatMessage(input.getText().
                                toString(),getUsername()));
                input.setText("");
            }
        });
        DisplayMessage();
        return mview;
    }

    public void DisplayMessage(){
        ListView listOfMessages = (ListView) mview.findViewById(R.id.list_of_messages);
        //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl(
        //       "http://..user");

        adapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        if (adapter!=null)
            listOfMessages.setAdapter(adapter);
    }
}
