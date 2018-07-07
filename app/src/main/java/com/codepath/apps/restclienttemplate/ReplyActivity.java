package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ReplyActivity extends AppCompatActivity {
    Tweet tweet;
    TwitterClient client;
    EditText editText;
    ImageButton imageButton;
    TweetAdapter.ViewHolder holder;
    User user;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        client = TwitterApp.getRestClient(this);
        editText = findViewById(R.id.etReply);
        textView = findViewById(R.id.tvUsername);
        String uName;
        uName = getIntent().getStringExtra("tvUsername");


        editText.setText("@"+uName + " ");
        editText.setSelection(editText.getText().length());


    }

    public void Tweet(View v){
        editText = findViewById(R.id.etReply);

        String my_reply, userName;
        my_reply = editText.getText().toString();






        client.sendTweet(my_reply, getIntent().getLongExtra("uid", 0), new JsonHttpResponseHandler(){


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Tweet tweet = Tweet.fromJSON(response);
                            Intent intent = new Intent();
                            intent.putExtra("my_tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, intent);
                            finish();

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }

                }
        ) ;
    }





}



