package com.kata.tennis.set;

import com.kata.tennis.game.TieBreakGame;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

@Slf4j
public class SetTest {

    private final String player1 = "Jordan";
    private final String player2 = "John";

    @Test
    public void expect_zero_to_zero_when_set_starts_test() {
        //GIVEN
        final Set set = new Set(player1, player2);

        //EXPECT
        Assert.assertEquals("Invalid P1 name", this.player1, set.getPlayer1());
        Assert.assertEquals("Invalid P2 name", this.player2, set.getPlayer2());
        Assert.assertEquals("Invalid Set status", "(0-0)", set.getStatus());
        log.info("summary {}", set);
    }


    @Test
    public void expect_one_to_zero_after_player1_scores_four_times_test() {
        //GIVEN
        final Set set = new Set(player1, player2, 0, 0);

        //WHEN
        IntStream.range(0, 4).forEach((i) -> set.player1Scores());

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 1, set.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, set.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "(1-0)", set.getStatus());
        log.info("summary {}", set);
    }

    @Test
    public void expect_one_to_zero_after_player1_scores_eight_times_test() {
        //GIVEN
        final Set set = new Set(player1, player2, 0, 0);

        //WHEN
        IntStream.range(0, 8).forEach((i) -> set.player1Scores());

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 2, set.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, set.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "(2-0)", set.getStatus());
        log.info("summary {}", set);
    }

    @Test
    public void expect_set_won_when_score_six_to_zero_test() {
        //GIVEN
        final Set set = new Set(player1, player2, 6, 0);

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 6, set.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, set.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "(6-0)", set.getStatus());
        Assert.assertEquals("Invalid Set status", true, set.isOver());
        log.info("summary {}", set);
    }

    @Test
    public void expect_tiebreak_game_when_player1_has_6_points_and_player2_has_6_points_test() {
        //GIVEN
        final Set set = new Set(player1, player2, 6, 6);

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 6, set.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 6, set.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "(6-6)", set.getStatus());
        Assert.assertEquals("Invalid Set over", false, set.isOver());
        Assert.assertEquals("Invalid Set winner", false, set.isPlayer1Winner());
        Assert.assertEquals("Invalid Set winner", false, set.isPlayer2Winner());
        Assert.assertEquals("Invalid Game, should be TieBreak", true, set.getCurrentGame() instanceof TieBreakGame);

        log.info("summary {}", set);
    }

    @Test
    public void expect_set_over_when_player1_wins_tie_break_game_test() {
        //GIVEN
        final Set set = new Set(player1, player2, 6, 6);

        //WHEN
        IntStream.range(0, 7).forEach((i) -> set.player1Scores());

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 7, set.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 6, set.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "(7-6)", set.getStatus());
        Assert.assertEquals("Invalid Set over", true, set.isOver());
        Assert.assertEquals("Invalid Set winner", true, set.isPlayer1Winner());
        Assert.assertEquals("Invalid Set winner", false, set.isPlayer2Winner());
        log.info("summary {}", set);
    }
}
