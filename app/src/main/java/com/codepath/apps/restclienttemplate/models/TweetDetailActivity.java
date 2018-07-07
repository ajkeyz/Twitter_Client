
package com.codepath.apps.restclienttemplate.models;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.TwitterClient;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {
    TwitterClient twitterClient;
    public ImageView ivProfileIamge;
    public TextView tvUsername;
    public TextView tvBody;
    public TextView tvCreatedAt;

    Tweet tweet;
    public TextView tvReply;
    Button btReply;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        ivProfileIamge = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvBody = (TextView)findViewById(R.id.tvBody);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvUsername.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvCreatedAt.setText(TweetAdapter.getRelativeTimeAgo(tweet.createdAt));

        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileIamge);



    }





}
