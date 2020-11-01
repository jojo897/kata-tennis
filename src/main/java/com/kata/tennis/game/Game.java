package com.kata.tennis.game;

import com.google.common.annotations.VisibleForTesting;
import com.kata.tennis.AbstractRule;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.kata.tennis.game.GameConstants.*;

@EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class Game extends AbstractRule {

    /**
     *
     * @param player1 name
     * @param player2 name
     */
    public Game(final String player1, final String player2) {
        super(player1, player2, MINIMUM_POINTS_TO_WIN, MINIMUM_LEAD_POINTS_TO_WIN, MAX_POINTS_TO_PLAY);
    }

    /**
     * This is only for testing purposes, hence visibility protected
     *
     * @param player1 name
     * @param player2 name
     * @param player1Points points
     * @param player2Points points
     */
    @VisibleForTesting
    protected Game(final String player1, final String player2, final int player1Points, final int player2Points) {
        super(player1, player2, player1Points, player2Points, MINIMUM_POINTS_TO_WIN, MINIMUM_LEAD_POINTS_TO_WIN, MAX_POINTS_TO_PLAY);
    }

    final public String getStatus() {
        final int p1Points = getPlayer1Points();
        final int p2Points = getPlayer2Points();
        final int diff = Math.abs(p1Points - p2Points);

        if (diff == 0) {
            return p1Points >= this.minRequiredPointsToWin - 1 ? GameConstants.DEUCE : String.format("%d-all", GameScore.evaluatePoints(p1Points));
        }
        if (p1Points < this.minRequiredPointsToWin && p2Points < this.minRequiredPointsToWin) {
            return String.format("%s-%s", GameScore.evaluatePoints(p1Points), GameScore.evaluatePoints(p2Points));
        }
        return diff >= this.minLeadPointsToWin ? GameConstants.GAME : GameConstants.ADVANTAGE;
    }
}
