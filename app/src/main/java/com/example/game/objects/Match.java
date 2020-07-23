package com.example.game.objects;

import com.example.game.enumerations.Turn;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Match {

    private Profile player1profile = new Profile(), player2profile = new Profile();
    private String winner = "";
    private Turn turn = Turn.PLAYER1;

    public Match() {

    }

    public Profile getPlayer1profile() {
        return player1profile;
    }

    public void setPlayer1profile(Profile player1profile) {
        this.player1profile = player1profile;
    }

    public Profile getPlayer2profile() {
        return player2profile;
    }

    public void setPlayer2profile(Profile player2profile) {
        this.player2profile = player2profile;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }
}
