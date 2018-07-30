package com.example.jeka.learnenglish.mvp;

import com.example.jeka.learnenglish.data.Word;

import java.util.List;

public class GamePresenter {
    private GameModel gameModel;
    private GameActivity gameView;


    GamePresenter(GameModel model) {
        this.gameModel = model;
    }

    public void attachView(GameActivity gameActivity){
        this.gameView = gameActivity;
    }

    public void detachView(){
        this.gameView = null;
    }

    public void loadWords(){
        gameModel.loadWords(new GameModel.LoadWordCallback() {
            @Override
            public void onLoad(List<Word> words) {
                gameView.showData(words);
            }
        });
    }

    private int firstClickPos = -1;

    public void itemClick(int position) {
        if (firstClickPos == position) {
            gameView.setTarget(firstClickPos, false);
            firstClickPos = -1;
            return;
        }
        if (firstClickPos == -1){
            firstClickPos = position;
            gameView.setTarget(firstClickPos, true);
        } else {
            gameView.setTarget(firstClickPos, false);
            if (gameModel.check(firstClickPos, position)) {
                gameView.hideItems(firstClickPos, position);
                firstClickPos = -1;
            }
            firstClickPos = -1;
        }
    }

    public void changedRangeWords(int value) {
        gameModel.setFileNum(value);
        loadWords();
        loadWords();
    }

    public void changedColorPalette(List<Integer> colorList) {
        gameModel.setColorPalette(colorList);
        loadWords();
        loadWords();
    }
}
