package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDetailActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    Context context;
    TwitterClient client;
    Tweet tweet;
    long ID;
    MyAppGlideModule myAppGlideModule;

    //pass in the tweet array constructor
    public TweetAdapter(List<Tweet> tweets) {

        mTweets = tweets;
    }

    //for each row, inflate the layout and cache references into Viewholder class

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data according to position
        Tweet tweet = mTweets.get(position);
        //populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvCreatedAt.setText(getRelativeTimeAgo(tweet.createdAt));


        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .into(holder.ivProfileIamge);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create viewholder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileIamge;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvCreatedAt;
        ImageButton ibReply;
        public TextView tvReply;
        Button btReply;
        ImageButton ibRetweet;

        public ViewHolder(View itemView) {
            super(itemView);


            //perform findViewByID lookups
            ivProfileIamge = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvReply = itemView.findViewById(R.id.tvUsername);
            ibReply = (ImageButton) itemView.findViewById(R.id.ibReply);
            ibReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Tweet reply_tweet = mTweets.get(position);
                    Intent intent = new Intent(v.getContext(), ReplyActivity.class);

                    intent.putExtra("reply", true);
                    intent.putExtra("uid", reply_tweet.uid);
                    intent.putExtra("tvUsername", reply_tweet.user.name);
                   // tvReply.setText("@" + reply_tweet.uid);


                    Activity activity = (Activity) v.getContext();


                    activity.startActivityForResult(intent, 2);


                }
            });
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            //get item position
            int position = getAdapterPosition();
            //make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                //get the movie at the position, this wont work if the class is static
                Tweet tweet = mTweets.get(position);
                //create an intent for the activity
                Intent intent = new Intent(context, TweetDetailActivity.class);
                //serialize the movie using parceler, use its short name as a key
                intent.putExtra("tweet", Parcels.wrap(tweet));
                //show the activity
                context.startActivity(intent);

            }
        }

    }

        // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
        public static String getRelativeTimeAgo(String rawJsonDate) {
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);

            String relativeDate = "";
            try {
                long dateMillis = sf.parse(rawJsonDate).getTime();
                relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();


            } catch (ParseException e) {
                e.printStackTrace();
            }

            return relativeDate;
        }

        public void clear() {
            mTweets.clear();
            notifyDataSetChanged();
        }

        // Add a list of items -- change to type used
        public void addAll(List<Tweet> list) {
            mTweets.addAll(list);
            notifyDataSetChanged();
        }


    public void OnRetweet(View v){






        if (tweet.retweeted = false){
            tweet.retweeted = true;


            client.ReTweet(ID, new JsonHttpResponseHandler() {



                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Tweet tweet = Tweet.fromJSON(response);
                        // Intent intent = new Intent();
                        // intent.putExtra("my_tweet", Parcels.wrap(tweet));
                        // setResult(RESULT_OK, intent);


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



    }






}

