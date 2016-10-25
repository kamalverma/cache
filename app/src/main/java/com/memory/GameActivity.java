package com.memory;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.memory.Models.GameItem;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;

    private GameAdapter mAdapter;
    private ArrayList<GameItem> mListItems;

    private GameItem mLastSelectedItem = null;
    String[] arr = {"B", "E", "H", "C", "E", "K", "D", "P", "G", "P", "C", "D", "G", "H", "T", "M", "M", "K", "B", "T"};
    Drawable[] arrDrawable;
    private int stepCounter = 0;
    private TextView mTvStepCounter, mTvTimer;

    private long totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3649361103885562~5297549332");

        int toolbarId = getResources().getIdentifier("toolbar", "id", getPackageName());
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        totalTime = 3 * 60 * 1000;
        arrDrawable = new Drawable[]{getResources().getDrawable(R.drawable.bear),
                getResources().getDrawable(R.drawable.elephant),
                getResources().getDrawable(R.drawable.horse),
                getResources().getDrawable(R.drawable.cobra),
                getResources().getDrawable(R.drawable.elephant),
                getResources().getDrawable(R.drawable.kangaroo),
                getResources().getDrawable(R.drawable.dog),
                getResources().getDrawable(R.drawable.panda),
                getResources().getDrawable(R.drawable.gcat),
                getResources().getDrawable(R.drawable.panda),
                getResources().getDrawable(R.drawable.cobra),
                getResources().getDrawable(R.drawable.dog),
                getResources().getDrawable(R.drawable.gcat),
                getResources().getDrawable(R.drawable.horse),
                getResources().getDrawable(R.drawable.tiger),
                getResources().getDrawable(R.drawable.monkey),
                getResources().getDrawable(R.drawable.monkey),
                getResources().getDrawable(R.drawable.kangaroo),
                getResources().getDrawable(R.drawable.bear),
                getResources().getDrawable(R.drawable.tiger)
        };

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_items);
        mRecyclerView.setHasFixedSize(true);

        mTvStepCounter = (TextView) findViewById(R.id.tv_counter);
        mTvTimer = (TextView) findViewById(R.id.tv_timer);
        mTvStepCounter.setText("Moves: " + stepCounter);

        setUpTestGame();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        mAdapter = new GameAdapter();
        mRecyclerView.setAdapter(mAdapter);


        new CountDownTimer(totalTime, 1000) {

            public void onTick(long millisUntilFinished) {
                mTvTimer.setText("time left " + (int) ((millisUntilFinished / 1000) / 60) + ":" + (int) ((millisUntilFinished / 1000) % 60));
            }

            public void onFinish() {
                mTvTimer.setText("Level Failed");
                showRetryDialog();
            }
        }.start();


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("6F7F147B2873902EEAE835330554AFC8").build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }


    private void showRetryDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                restart();
            }
        });

        builder.setCancelable(false);
        builder.setMessage("Level Failed.");

        builder.create().show();
    }

    private void restart() {
        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_restart) {
            restart();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpTestGame() {
        mListItems = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            GameItem gameItem = new GameItem();
            gameItem.setItemText(arr[i]);
            gameItem.setItemTag(arr[i]);
            gameItem.setItemImageUrl(arrDrawable[i]);
            if (arr[i].length() == 0)
                gameItem.setBlankItem(true);

            mListItems.add(gameItem);
        }

        Collections.shuffle(mListItems);
    }


    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {

        @Override
        public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_game_item, parent, false);
            return new GameHolder(v);
        }

        @Override
        public void onBindViewHolder(GameHolder holder, int position) {

            holder.itemView.setTag(mListItems.get(position));
            // holder.mTvText.setText(mListItems.get(position).getItemText());
            holder.mImage.setImageDrawable(mListItems.get(position).getItemImageUrl());

            if (mListItems.get(position).isChecked()) {
                holder.mIvCover.setVisibility(View.VISIBLE);
                holder.mIvCover.setBackgroundColor(Color.TRANSPARENT);
                holder.mIvCover.setImageResource(R.drawable.ic_cleared);
            } else {
                holder.mIvCover.setBackgroundColor(getColor(R.color.colorPrimary));
                holder.mIvCover.setImageResource(0);
                if (mListItems.get(position).isOpenTemp()) {
                    holder.mIvCover.setVisibility(View.GONE);
                } else {
                    holder.mIvCover.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

    }

    public class GameHolder extends RecyclerView.ViewHolder {

        TextView mTvText;
        ImageView mIvCover;
        ImageView mImage;

        GameHolder(View itemView) {
            super(itemView);

            mTvText = (TextView) itemView.findViewById(R.id.tv_item_text);
            mIvCover = (ImageView) itemView.findViewById(R.id.iv_item_cover);
            mImage = (ImageView) itemView.findViewById(R.id.iv_item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameItem item = (GameItem) v.getTag();

                    if (item.isChecked() || item.isOpenTemp()) {
                        return;
                    }

                    if (mLastSelectedItem == null) {
                        item.setOpenTemp(true);
                        mLastSelectedItem = item;
                        mAdapter.notifyDataSetChanged();
                    } else if (item.getItemTag().equals(mLastSelectedItem.getItemTag())) {
                        item.setChecked(true);
                        mLastSelectedItem.setChecked(true);
                        mAdapter.notifyDataSetChanged();
                        mLastSelectedItem = null;
                    } else {
                        //Close previously opened item
                        mLastSelectedItem.setOpenTemp(false);
                        mAdapter.notifyDataSetChanged();
                        //open current Item
                        item.setOpenTemp(true);
                        mLastSelectedItem = item;
                        mAdapter.notifyDataSetChanged();
                    }

                    checkGameFinished();
                }
            });
        }
    }

    private void checkGameFinished() {
        //Check if game is finished
        boolean gameFinished = true;
        for (GameItem gameItem : mListItems) {
            if (!gameItem.isChecked()) {
                gameFinished = false;
                break;
            }
        }
        if (gameFinished) {

            if (stepCounter <= 35) {
                //Three star
                mTvStepCounter.setText("Awesome.. " + stepCounter + " ");
            } else if (stepCounter <= 50) {
                //Two Star
                mTvStepCounter.setText("Good.. " + stepCounter + " ");
            } else if (stepCounter <= 55) {
                mTvStepCounter.setText("cleared.. " + stepCounter + " ");
                //One star
            } else {
                mTvStepCounter.setText("Bad Score.. " + stepCounter + " ");
                //Level Failed try Again
            }

        } else {
            stepCounter += 1;
            mTvStepCounter.setText("Moves: " + stepCounter + "");
        }
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int mVerticalSpaceHeight;

        VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }
}
