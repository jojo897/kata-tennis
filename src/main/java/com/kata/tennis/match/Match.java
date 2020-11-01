package com.kata.tennis.match;

import com.google.common.annotations.VisibleForTesting;
import com.kata.tennis.AbstractRule;
import com.kata.tennis.ExceptionUtils;
import com.kata.tennis.set.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kata.tennis.match.MatchConstants.*;

@Slf4j
@EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class Match extends AbstractRule {

    private final List<Set> sets = new ArrayList<>(MINIMUM_POINTS_TO_WIN);

    /**
     *
     * @param player1 name
     * @param player2 name
     */
    public Match(final String player1, final String player2) {
        super(player1, player2, MINIMUM_POINTS_TO_WIN, MINIMUM_LEAD_POINTS_TO_WIN, MAX_POINTS_TO_PLAY);
        startNewSet();
    }

    /**
     * This is only for testing purposes, hence visibility protected
     *
     * @param player1 name
     * @param player2 name
     * @param playedSets list of sets already played
     */
    @VisibleForTesting
    protected Match(final String player1, final String player2, final List<Set> playedSets) {
        super(player1, player2, MINIMUM_POINTS_TO_WIN, MINIMUM_LEAD_POINTS_TO_WIN, MAX_POINTS_TO_PLAY);
        //PROCESSING SETS AND UPDATING MATCH INTERNAL SCORE KEEPING
        playedSets.forEach(set -> {
            if (set.isPlayer1Winner()) {
                this.player1Points.incrementAndGet();
                this.sets.add(set);
            } else if (set.isPlayer2Winner()) {
                this.player2Points.incrementAndGet();
                this.sets.add(set);
            } else {
                final Set currentSet = getCurrentSet();
                if (currentSet == null || currentSet.isOver()) {
                    //ADD SET AS CURRENT SET
                    this.sets.add(set);
                } else if (currentSet != set) {
                    log.error("Current Set(id={}) is not over, will ignore Set(id={}): {}", currentSet.getId(), getId(), set);
                }
            }
        });
        startNewSet();
    }

    public Set getCurrentSet() {
        return !this.sets.isEmpty() ? this.sets.get(this.sets.size() - 1) : null;
    }

    public AbstractRule getCurrentGame() {
        if (this.isOver()) {
            return null;
        }

        final Set currentSet = this.getCurrentSet();

        if (currentSet == null || currentSet.isOver()) {
            return null;
        }

        final AbstractRule currentGame = currentSet.getCurrentGame();
        return currentGame != null && !currentGame.isOver() ? currentGame : null;
    }

    @Override
    public String getStatus() {
        if (!isOver()) {
            return IN_PROGRESS;
        }

        return isPlayer1Winner() ? PLAYER_ONE_WINS : PLAYER_TWO_WINS;
    }

    public String getScoreDetail() {
        ExceptionUtils.isNotNull(this.sets, "Match not correctly initialized");

        final List<String> scores = this.sets.stream().map(AbstractRule::getStatus).collect(Collectors.toList());
        return String.join(" ", scores);
    }

    @Override
    final public void player1Scores() {
        final AbstractRule currentSet = getCurrentSet();
        ExceptionUtils.isNotNull(currentSet, "Player1 can not score, no set started");

        //ACTION
        currentSet.player1Scores();

        if (!currentSet.isOver()) {
            return;
        }
        updateSetScores(currentSet);
        startNewSet();
    }

    @Override
    final public void player2Scores() {
        final AbstractRule currentSet = getCurrentSet();
        ExceptionUtils.isNotNull(currentSet, "Player2 can not score, no set started");

        //ACTION
        currentSet.player2Scores();
        if (!currentSet.isOver()) {
            return;
        }
        updateSetScores(currentSet);
        startNewSet();
    }

    private void startNewSet() {
        if (this.isOver()) {
            log.error("Can not start new set, current match is already over");
            return;
        }

        ExceptionUtils.isNotNull(this.sets, "Can not start new set");
        final AbstractRule currentSet = this.getCurrentSet();

        if (currentSet != null && !currentSet.isOver()) {
            log.error("Can not start new set, current set is not over yet");
            return;
        }

        this.sets.add(new Set(player1, player2));
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

    public String summary() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("Player 1: %s\n", this.player1));
        sb.append(String.format("Player 2: %s\n", this.player2));
        sb.append(String.format("Score: %s\n", this.getScoreDetail()));

        final AbstractRule currentGame = this.getCurrentGame();
        if (currentGame != null && !currentGame.isOver()) {
            sb.append(String.format("Current game status: %s\n", currentGame.getStatus()));
        }

        sb.append(String.format("Match Status: %s\n", this.getStatus()));
        return sb.toString();
    }
}
