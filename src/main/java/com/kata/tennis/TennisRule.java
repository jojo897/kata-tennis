package com.kata.tennis;

public interface TennisRule {
    /**
     * actions
     */

    void player1Scores();

    void player2Scores();

    /**
     * helpers
     */

    int getPlayer1Points();

    int getPlayer2Points();

    /**
     * State
     */
    String getStatus();

    boolean isOver();

    boolean isPlayer1Winner();

    boolean isPlayer2Winner();
}
