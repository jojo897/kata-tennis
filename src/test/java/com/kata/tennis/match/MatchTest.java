package com.kata.tennis.match;

import com.kata.tennis.AbstractRule;
import com.kata.tennis.set.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

@Slf4j
public class MatchTest {

    private final String player1 = "Jordan";
    private final String player2 = "John";

    @Test
    public void expect_zero_to_zero_when_set_starts_test() {
        //GIVEN
        final Match match = new Match(player1, player2);

        //EXPECT
        Assert.assertEquals("Invalid P1 name", this.player1, match.getPlayer1());
        Assert.assertEquals("Invalid P2 name", this.player2, match.getPlayer2());
        Assert.assertEquals("Invalid Match status", "in progress", match.getStatus());
        Assert.assertEquals("Invalid Match score", "(0-0)", match.getScoreDetail());
        log.info("summary \n{}", match.summary());
    }


    @Test
    public void expect_two_to_zero_after_player1_wins_two_sets_test() {
        //GIVEN
        final Set set1 = new Set(player1, player2, 7, 6);
        final Set set2 = new Set(player1, player2, 6, 4);
        final Match match = new Match(player1, player2, Arrays.asList(set1, set2));

        //WHEN
        match.player1Scores();

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 2, match.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, match.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "in progress", match.getStatus());
        Assert.assertEquals("Invalid Match score", "(7-6) (6-4) (0-0)", match.getScoreDetail());
        final AbstractRule currentGame = match.getCurrentGame();
        Assert.assertNotNull("Invalid Game", currentGame);
        Assert.assertEquals("Invalid Game status", "15-0", currentGame.getStatus());

        log.info("summary \n{}", match.summary());
    }

    @Test
    public void expect_two_to_zero_and_game_deuce_after_player1_and_player2_win_three_game_each_test() {
        //GIVEN
        final Set set1 = new Set(player1, player2, 7, 6);
        final Set set2 = new Set(player1, player2, 6, 4);
        final Match match = new Match(player1, player2, Arrays.asList(set1, set2));

        //WHEN
        IntStream.range(0, 3).forEach((i) -> {
            match.player1Scores();
            match.player2Scores();
        });

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 2, match.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, match.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "in progress", match.getStatus());
        Assert.assertEquals("Invalid Match score", "(7-6) (6-4) (0-0)", match.getScoreDetail());
        final AbstractRule currentGame = match.getCurrentGame();
        Assert.assertNotNull("Invalid Game", currentGame);
        Assert.assertEquals("Invalid Game status", "deuce", currentGame.getStatus());

        log.info("summary \n{}", match.summary());
    }

    @Test
    public void expect_two_to_zero_and_game_advantage_after_player1_wins_four_game_and_player2_wins_three_game_test() {
        //GIVEN
        final Set set1 = new Set(player1, player2, 7, 6);
        final Set set2 = new Set(player1, player2, 6, 4);
        final Match match = new Match(player1, player2, Arrays.asList(set1, set2));

        //WHEN
        IntStream.range(0, 3).forEach((i) -> {
            match.player1Scores();
            match.player2Scores();
        });
        match.player1Scores();

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 2, match.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, match.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "in progress", match.getStatus());
        Assert.assertEquals("Invalid Match score", "(7-6) (6-4) (0-0)", match.getScoreDetail());
        final AbstractRule currentGame = match.getCurrentGame();
        Assert.assertNotNull("Invalid Game", currentGame);
        Assert.assertEquals("Invalid Game status", "advantage", currentGame.getStatus());

        log.info("summary \n{}", match.summary());
    }

    @Test
    public void expect_match_over_when_player1_wins_three_sets_test() {
        //GIVEN
        final Set set1 = new Set(player1, player2, 7, 6);
        final Set set2 = new Set(player1, player2, 6, 4);
        final Set set3 = new Set(player1, player2, 6, 2);
        final Match match = new Match(player1, player2, Arrays.asList(set1, set2, set3));

        //WHEN

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 3, match.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, match.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "Player 1 wins", match.getStatus());
        Assert.assertEquals("Invalid Set status", "(7-6) (6-4) (6-2)", match.getScoreDetail());
        Assert.assertEquals("Invalid Set over", true, match.isOver());
        Assert.assertEquals("Invalid Set winner", true, match.isPlayer1Winner());
        Assert.assertEquals("Invalid Set winner", false, match.isPlayer2Winner());
        log.info("summary \n{}", match.summary());
    }

    @Test
    public void expect_match_over_when_player1_wins_tie_break_game_test() {
        //GIVEN
        final Set set1 = new Set(player1, player2, 7, 6);
        final Set set2 = new Set(player1, player2, 6, 4);
        final Set set3 = new Set(player1, player2, 6, 6);
        final Match match = new Match(player1, player2, Arrays.asList(set1, set2, set3));

        //WHEN
        IntStream.range(0, 7).forEach((i) -> match.player1Scores());

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 3, match.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, match.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "Player 1 wins", match.getStatus());
        Assert.assertEquals("Invalid Set status", "(7-6) (6-4) (7-6)", match.getScoreDetail());
        Assert.assertEquals("Invalid Set over", true, match.isOver());
        Assert.assertEquals("Invalid Set winner", true, match.isPlayer1Winner());
        Assert.assertEquals("Invalid Set winner", false, match.isPlayer2Winner());
        log.info("summary \n{}", match.summary());
    }

    @Test
    public void expect_match_is_not_over_when_player1_does_not_win_tie_break_game_test() {
        //GIVEN
        final Set set1 = new Set(player1, player2, 7, 6);
        final Set set2 = new Set(player1, player2, 6, 4);
        final Set set3 = new Set(player1, player2, 6, 6);//TIE-BREAK
        final Match match = new Match(player1, player2, Arrays.asList(set1, set2, set3));

        //WHEN
        IntStream.range(0, 7).forEach((i) -> match.player2Scores());

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 2, match.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 1, match.getPlayer2Points());
        Assert.assertEquals("Invalid Set status", "in progress", match.getStatus());
        Assert.assertEquals("Invalid Set status", "(7-6) (6-4) (6-7) (0-0)", match.getScoreDetail());
        Assert.assertEquals("Invalid Set over", false, match.isOver());
        Assert.assertEquals("Invalid Set winner", false, match.isPlayer1Winner());
        Assert.assertEquals("Invalid Set winner", false, match.isPlayer2Winner());
        log.info("summary \n{}", match.summary());
    }
}
