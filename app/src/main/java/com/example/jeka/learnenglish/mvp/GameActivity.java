package com.example.jeka.learnenglish.mvp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.jeka.learnenglish.mvp.GameModel;
import com.example.jeka.learnenglish.json.JSONHelper;
import com.example.jeka.learnenglish.MyRecyclerViewAdapter;
import com.example.jeka.learnenglish.R;
import com.example.jeka.learnenglish.data.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import stfalcon.universalpickerdialog.UniversalPickerDialog;

public class GameActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private GamePresenter presenter;
    MyRecyclerViewAdapter adapter;

    private static ArrayList<String> LIST_OF_RANGE = new ArrayList<>(
            Arrays.asList("c 1 по 250", "с 251 по 500", "с 501 по 750", "с 751 по 1000"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView =
                findViewById(R.id.recyclerView);
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
    public void onItemClick(View view, int position) {
        presenter.itemClick(position);

        Log.i("TAG", "You clicked number " +
                adapter.getItem(position) +
                ", which is at cell position " + position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:
                presenter.loadWords();
                presenter.loadWords();
                break;
            case R.id.menu_settings:
                showSettingsDialog();
                break;
            case R.id.menu_color:
                showChoiceColorDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChoiceColorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_choice_color, null);
        final CheckBox cbColor0 = mView.findViewById(R.id.checkBoxColor0);
        final CheckBox cbColor1 = mView.findViewById(R.id.checkBoxColor1);
        final CheckBox cbColor2 = mView.findViewById(R.id.checkBoxColor2);
        final CheckBox cbColor3 = mView.findViewById(R.id.checkBoxColor3);
        final CheckBox cbColor4 = mView.findViewById(R.id.checkBoxColor4);

        builder.setView(mView);
        builder.setPositiveButton(R.string.dialog_apply, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Integer> colorList = new ArrayList<>();
                if (cbColor0.isChecked()){
                    colorList.add(R.color.color0);
                }
                if (cbColor1.isChecked()){
                    colorList.add(R.color.color1);
                }
                if (cbColor2.isChecked()){
                    colorList.add(R.color.color2);
                }
                if (cbColor3.isChecked()){
                    colorList.add(R.color.color3);
                }
                if (cbColor4.isChecked()){
                    colorList.add(R.color.color4);
                }
                if (colorList.isEmpty()){
                    colorList.add(R.color.color2);
                    Toast.makeText(GameActivity.this, "Blue color is automatically selected", Toast.LENGTH_SHORT).show();
                }
                presenter.changedColorPalette(colorList);
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


    private void showSettingsDialog() {
        new UniversalPickerDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setContentTextSize(16)
                .setListener(new UniversalPickerDialog.OnPickListener() {
                    @Override
                    public void onPick(int[] selectedValues, int key) {
                        presenter.changedRangeWords(selectedValues[0]);
                    }
                })
                .setInputs(new UniversalPickerDialog.Input(0, LIST_OF_RANGE))
                .build()
                .show();
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
