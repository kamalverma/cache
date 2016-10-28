package com.cache;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cache.common.AppKeys;
import com.cache.common.PrefUtils;

public class HomeActivity extends AppCompatActivity {

    private TextView mTvBestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);


        int toolbarId = getResources().getIdentifier("toolbar", "id", getPackageName());
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findViewById(R.id.btn_play1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(3 * 60 * 1000);
            }
        });

        findViewById(R.id.btn_play2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(2 * 60 * 1000);
            }
        });

        findViewById(R.id.btn_play3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(1 * 60 * 1000);
            }
        });
    }

    private void startGame(long millis) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(AppKeys.KEY_DURATION, millis);
        startActivity(intent);
    }


    private void setUpbestScore() {
        int bestScore = PrefUtils.getBestScore(this);
        if (bestScore > 0) {
            mTvBestScore = (TextView) findViewById(R.id.tv_best_score);
            mTvBestScore.setText(bestScore + "");
            findViewById(R.id.ll_best_score).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpbestScore();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
