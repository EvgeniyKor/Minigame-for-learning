package com.example.jeka.learnenglish.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.jeka.learnenglish.mvp.GameModel;
import com.example.jeka.learnenglish.json.JSONHelper;
import com.example.jeka.learnenglish.MyRecyclerViewAdapter;
import com.example.jeka.learnenglish.R;
import com.example.jeka.learnenglish.data.Word;

import java.util.List;

public class GameActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private GamePresenter presenter;
    MyRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView =
                (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new GridLayoutManager(this, 3));
        adapter = new MyRecyclerViewAdapter();
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        JSONHelper jsonHelper = new JSONHelper(this);
        GameModel gameModel = new GameModel(jsonHelper);
        presenter = new GamePresenter(gameModel);
        presenter.attachView(this);



        loadData();

        //ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh){
            presenter.loadWords();
            presenter.loadWords();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.itemClick(position);

        Log.i("TAG", "You clicked number " +
                adapter.getItem(position) +
                ", which is at cell position " + position);
    }

    public void showData(List<Word> data){
        adapter.setData(data);
    }

    public void loadData(){
        presenter.loadWords();
    }

    public void hideItems(int firstClickPos, int secondClickPos) {
        adapter.getItem(firstClickPos).setConceal(true);
        adapter.getItem(secondClickPos).setConceal(true);
        adapter.notifyItemChanged(firstClickPos);
        adapter.notifyItemChanged(secondClickPos);
    }

    public void restoreItems(int firstClickPos, int secondClickPos) {
        adapter.getItem(firstClickPos).setTarget(false);
        adapter.getItem(secondClickPos).setTarget(false);
        adapter.notifyItemChanged(firstClickPos);
        adapter.notifyItemChanged(secondClickPos);

    }

    public void setTarget(int position, boolean targ) {
        adapter.getItem(position).setTarget(targ);
        adapter.notifyItemChanged(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
