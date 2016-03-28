package eu.com.cache;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import eu.com.cache.Models.GameItem;

public class GameActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;

    private GameAdapter mAdapter;
    private ArrayList<GameItem> mListItems;

    private GameItem mLastSelectedItem = null;
    String[] arr = {"A", "F", "H", "Z", "F", "K", "X", "P", "G", "P", "Z", "X", "G", "H", "T", "N", "N", "K", "A", "T"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_items);
        mRecyclerView.setHasFixedSize(true);

        setUpTestGame();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(25));

        mAdapter = new GameAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            gameItem.setItemImageUrl(arr[i]);
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
            GameHolder vh = new GameHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(GameHolder holder, int position) {

            holder.itemView.setTag(mListItems.get(position));
            holder.mTvText.setText(mListItems.get(position).getItemText());

            if (mListItems.get(position).isChecked()) {
                holder.mIvCover.setVisibility(View.VISIBLE);
                holder.mIvCover.setBackgroundColor(Color.TRANSPARENT);
                holder.mIvCover.setImageResource(R.drawable.ic_cleared);
            } else {
                holder.mIvCover.setBackgroundColor(Color.RED);
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

        public TextView mTvText;
        public ImageView mIvCover;
        public ImageView mImage;

        public GameHolder(View itemView) {
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
                        return;
                    }

                    if (item.getItemTag().equals(mLastSelectedItem.getItemTag())) {
                        item.setChecked(true);
                        mLastSelectedItem.setChecked(true);
                        mAdapter.notifyDataSetChanged();
                        mLastSelectedItem = null;
                        return;
                    } else {
                        //Close previously opened item
                        mLastSelectedItem.setOpenTemp(false);
                        mAdapter.notifyDataSetChanged();

                        //open current Item
                        item.setOpenTemp(true);
                        mLastSelectedItem = item;
                        mAdapter.notifyDataSetChanged();
                    }

                }
            });
        }
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }
}
