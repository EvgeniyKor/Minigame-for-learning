package com.example.jeka.learnenglish.mvp;

import android.os.AsyncTask;

import com.example.jeka.learnenglish.R;
import com.example.jeka.learnenglish.json.JSONHelper;
import com.example.jeka.learnenglish.data.Doublet;
import com.example.jeka.learnenglish.data.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class GameModel {

    private List<Word> data;
    private List<Integer> colorPalette;
    private JSONHelper jsonHelper;
    private int fileNum = 0;
    private int amountItems = 15;
    //private List<Doublet> doublets;

    GameModel(JSONHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
        initColorPalette();
        //this.doublets = jsonHelper.importFromJSON();
    }

    private void initColorPalette(){
        colorPalette = new ArrayList<>();
        colorPalette.add(R.color.color0);
        colorPalette.add(R.color.color1);
        colorPalette.add(R.color.color2);
    }

    public List<Integer> getColorPalette() {
        return colorPalette;
    }

    public void setColorPalette(List<Integer> colorPalette) {
        this.colorPalette = colorPalette;
    }

    private void setData(List<Word> data) {
        this.data = null;
        this.data = data;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setAmountItems(int amountItems) {
        this.amountItems = amountItems;
    }

    public int getAmountItems() {
        return amountItems;
    }


    public void loadWords(LoadWordCallback callback){
        LoadWordsTask loadWordsTask = new LoadWordsTask(callback);
        loadWordsTask.execute();
    }

    public boolean check(int firstClickPos, int secondClickPos) {
        return data.get(firstClickPos).getId() ==
                data.get(secondClickPos).getId();
    }


    interface LoadWordCallback {
        void onLoad(List<Word> words);
    }

    class LoadWordsTask extends AsyncTask<Void, Void, List<Word>> {

        private final LoadWordCallback callback;

        LoadWordsTask(LoadWordCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            if (callback != null) {
                callback.onLoad(words);
            }
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
            List<Word> words = new LinkedList<>();
            List<Doublet> doublets = jsonHelper.importFromJSON(fileNum);
            for (int i = 0; i < amountItems; i++){
                int randId = new Random().nextInt(250);
                int randColor = new Random().nextInt(colorPalette.size());
                Word wordRU = new Word();
                wordRU.setId(randId);
                wordRU.setText(doublets.get(randId).getText());
                wordRU.setColorId(colorPalette.get(randColor));
                words.add(wordRU);
                Word wordEN = new Word();
                wordEN.setId(randId);
                wordEN.setText(doublets.get(randId).getAnswer());
                wordEN.setColorId(colorPalette.get(randColor));
                words.add(wordEN);
            }
            shuffleAndSaveData(words);
            return words;
        }

        private void shuffleAndSaveData(List<Word> words){
            Collections.shuffle(words);
            setData(words);
        }
    }
}


