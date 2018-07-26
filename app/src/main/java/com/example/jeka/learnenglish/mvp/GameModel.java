package com.example.jeka.learnenglish.mvp;

import android.os.AsyncTask;

import com.example.jeka.learnenglish.json.JSONHelper;
import com.example.jeka.learnenglish.data.Doublet;
import com.example.jeka.learnenglish.data.Word;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class GameModel {

    private List<Word> data;
    private JSONHelper jsonHelper;


    private int amountItems = 15;

    GameModel(JSONHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }


    private void setData(List<Word> data) {
        this.data = data;
    }

    public void loadWords(LoadWordCallback callback){
        LoadWordsTask loadWordsTask = new LoadWordsTask(callback);
        loadWordsTask.execute();
    }

    public boolean check(int firstClickPos, int secondClickPos) {
        if (data.get(firstClickPos).getId() ==
                data.get(secondClickPos).getId()){
            return true;
        } else {
            return false;
        }
    }

    private void shuffle(List<Word> words){
        Collections.shuffle(words);
        setData(words);
    }

    interface LoadWordCallback {
        void onLoad(List<Word> words);
    }

    class LoadWordsTask extends AsyncTask<Void, Void, List<Word>> {

        private final LoadWordCallback callback;

        public LoadWordsTask(LoadWordCallback callback) {
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
            List<Doublet> doublets = jsonHelper.importFromJSON();
            for (int i = 0; i < amountItems; i++){
                int randId = new Random().nextInt(250);
                int randColor = new Random().nextInt(3);
                Word wordRU = new Word();
                wordRU.setId(randId);
                wordRU.setText(doublets.get(randId).getText());
                wordRU.setTarget(false);
                wordRU.setConceal(false);
                wordRU.setColorId(randColor);
                words.add(wordRU);
                Word wordEN = new Word();
                wordEN.setId(randId);
                wordEN.setText(doublets.get(randId).getAnswer());
                wordEN.setTarget(false);
                wordEN.setConceal(false);
                wordEN.setColorId(randColor);
                words.add(wordEN);
            }
            shuffle(words);
            return words;
        }
    }
}
