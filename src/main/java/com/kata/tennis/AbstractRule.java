package com.kata.tennis;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@EqualsAndHashCode(of="id") @ToString
public abstract class AbstractRule implements TennisRule {
    private static final AtomicLong COUNTER = new AtomicLong(0);

    private @Getter final long id;

    protected @Getter final String player1;
    protected final AtomicInteger player1Points;

    protected @Getter final String player2;
    protected final AtomicInteger player2Points;

    protected @Getter final int minRequiredPointsToWin;
    protected @Getter final int minLeadPointsToWin;
    protected @Getter final int maxPointsToPlay;

    /**
     *
     * @param player1 name
     * @param player2 name
     */
    public AbstractRule(final String player1, final String player2, final int minRequiredPointsToWin,
                        final int minLeadPointsToWin, final int maxPointsToPlay) {
        this.id = COUNTER.getAndIncrement();
        this.player1 = player1;
        this.player2 = player2;
        this.player1Points = new AtomicInteger(0);
        this.player2Points = new AtomicInteger(0);
        this.minRequiredPointsToWin = minRequiredPointsToWin;
        this.minLeadPointsToWin = minLeadPointsToWin;
        this.maxPointsToPlay = maxPointsToPlay;
    }

    /**
     *
     * @param p1 name        final int size = this.games.size();

     * @param p2 name
     * @param p1Points points
     * @param p2Points points
     * @param minRequiredPointsToWin minimum required points to win a game
     * @param minLeadPointsToWin a player has to lead the game by specific value to win a game
     */
    protected AbstractRule(final String p1, final String p2, final int p1Points, final int p2Points,
                           final int minRequiredPointsToWin, final int minLeadPointsToWin, final int maxPointsToPlay) {
        this.id = COUNTER.getAndIncrement();
        this.player1 = p1;
        this.player2 = p2;
        this.player1Points = new AtomicInteger(p1Points);
        this.player2Points = new AtomicInteger(p2Points);
        this.minRequiredPointsToWin = minRequiredPointsToWin;
        this.minLeadPointsToWin = minLeadPointsToWin;
        this.maxPointsToPlay = maxPointsToPlay;
    }


    /**
     * game specific
     */

    public abstract String getStatus();

    /**
     *  actions
     */

    public void player1Scores() {
        this.player1Points.incrementAndGet();
    }

    public void player2Scores() {
        this.player2Points.incrementAndGet();
    }

    /**
     *  helpers
     */

    public int getPlayer1Points() {
        return this.player1Points.get();
    }

    public int getPlayer2Points() {
        return this.player2Points.get();
    }

    public boolean isPlayer1Winner() {
        return isOver() && this.player1Points.get() > this.player2Points.get();
    }

    public boolean isPlayer2Winner() {
        return isOver() && this.player2Points.get() > this.player1Points.get();
    }

    /**
     *  helpers
     */

    public boolean isOver() {
        final int p1Points = getPlayer1Points();
        final int p2Points = getPlayer2Points();

        if (this.maxPointsToPlay > 0 && (p1Points >= this.maxPointsToPlay || p2Points >= this.maxPointsToPlay)) {
            return true;
        }

        return (p1Points >= this.minRequiredPointsToWin || p2Points >= this.minRequiredPointsToWin) &&
                (Math.abs(p1Points - p2Points) >= this.minLeadPointsToWin);
    }
}
