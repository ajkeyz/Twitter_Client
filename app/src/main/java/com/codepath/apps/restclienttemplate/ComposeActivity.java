package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    private TextView tvCharacterCount;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient(this);
        tvCharacterCount = findViewById(R.id.tvCharacterCount);
        editText = findViewById(R.id.etComposeTweet);

        editText.addTextChangedListener(mTextEditorWatcher);


    }


    public void onButtonClick(View view){

        editText = (EditText) findViewById(R.id.etComposeTweet);

        final String mytweet;

        mytweet = editText.getText().toString();



        if (!getIntent().getBooleanExtra("reply", false)){
            client.sendTweet(mytweet, new JsonHttpResponseHandler() {




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
            });


        }

        else {
            client.sendTweet(mytweet, getIntent().getLongExtra("uid", 0), new JsonHttpResponseHandler(){

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


    private EditText mEditText;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvCharacterCount.setText(String.valueOf(140 - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };




   /* public void Tweet (View V){

        editText = (EditText) findViewById(R.id.etComposeTweet);

        String mytweet;

        mytweet = editText.getText().toString();



        if (!getIntent().getBooleanExtra("reply", false)){
            client.sendTweet(mytweet, new JsonHttpResponseHandler() {




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
            });


        }

        else {
            client.sendTweet(mytweet, getIntent().getLongExtra("uid", 0), new JsonHttpResponseHandler(){

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


    }*/




}
