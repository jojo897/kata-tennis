package com.kata.tennis.set;

import com.google.common.annotations.VisibleForTesting;
import com.kata.tennis.AbstractRule;
import com.kata.tennis.ExceptionUtils;
import com.kata.tennis.game.Game;
import com.kata.tennis.game.TieBreakGame;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static com.kata.tennis.set.SetConstants.*;

@Slf4j
@EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class Set extends AbstractRule {

    private @Getter
    AbstractRule currentGame;

    /**
     *
     * @param player1 name
     * @param player2 name
     */
    public Set(final String player1, final String player2) {
        super(player1, player2, MINIMUM_POINTS_TO_WIN, MINIMUM_LEAD_POINTS_TO_WIN, MAX_POINTS_TO_PLAY);
        startNewGame();
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
    public Set(final String player1, final String player2, final int player1Points, final int player2Points) {
        super(player1, player2, player1Points, player2Points, MINIMUM_POINTS_TO_WIN, MINIMUM_LEAD_POINTS_TO_WIN, MAX_POINTS_TO_PLAY);
        startNewGame();
    }

    @Override
    public String getStatus() {
        final int p1Points = getPlayer1Points();
        final int p2Points = getPlayer2Points();

        return String.format("(%s-%s)", p1Points, p2Points);
    }

    @Override
    final public void player1Scores() {
        final AbstractRule currentGame = getCurrentGame();
        ExceptionUtils.isNotNull(currentGame, "Player1 can not score, no game started");

        //ACTION
        currentGame.player1Scores();

        if (!currentGame.isOver()) {
            return;
        }
        updateSetScores(currentGame);
        startNewGame();
    }

    @Override
    final public void player2Scores() {
        final AbstractRule currentGame = getCurrentGame();
        ExceptionUtils.isNotNull(currentGame, "Player2 can not score, no game started");

        //ACTION
        currentGame.player2Scores();
        if (!currentGame.isOver()) {
            return;
        }
        updateSetScores(currentGame);
        startNewGame();
    }

    private synchronized void startNewGame() {
        final AbstractRule currentGame = this.getCurrentGame();

        if (currentGame != null && !currentGame.isOver()) {
            log.error("Can not start new game, current game is not over yet");
            return;
        }

        final int player1Points = getPlayer1Points();

        if (player1Points == this.minRequiredPointsToWin && player1Points == getPlayer2Points()) {
            this.currentGame = new TieBreakGame(player1, player2);
            log.info("Next game is a tie-break game");
        } else {
            this.currentGame = new Game(player1, player2);
        }
    }

    private void updateSetScores(final AbstractRule current) {
        ExceptionUtils.isNotNull(current, "Can not update score, invalid game");

        if (current.isPlayer1Winner()) {
            this.player1Points.incrementAndGet();
        } else if (current.isPlayer2Winner()) {
            this.player2Points.incrementAndGet();
        } else {
            log.error("Couldn't update scores, no winners found");
        }
    }
}
