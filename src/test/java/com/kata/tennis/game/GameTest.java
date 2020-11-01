package com.kata.tennis.game;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class GameTest {

    private final String player1 = "Jordan";
    private final String player2 = "John";

    @Test
    public void expect_zero_all_when_game_starts_test() {
        //GIVEN
        final Game game = new Game(player1, player2);

        //EXPECT
        Assert.assertEquals("Invalid P1 name", this.player1, game.getPlayer1());
        Assert.assertEquals("Invalid P2 name", this.player2, game.getPlayer2());
        Assert.assertEquals("Invalid game status", "0-all", game.getStatus());
        log.info("summary {}", game);
    }


    @Test
    public void expect_fifteen_to_zero_after_player1_scores_test() {
        //GIVEN
        final Game game = new Game(player1, player2, 0 , 0);

        //WHEN
        game.player1Scores();

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 1, game.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, game.getPlayer2Points());
        Assert.assertEquals("Invalid game status", "15-0", game.getStatus());
        log.info("summary {}", game);
    }

    @Test
    public void expect_thirty_to_zero_after_player1_scores_twice_test() {
        //GIVEN
        final Game game = new Game(player1, player2, 0 , 0);

        //WHEN
        game.player1Scores();
        game.player1Scores();

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 2, game.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 0, game.getPlayer2Points());
        Assert.assertEquals("Invalid game status", "30-0", game.getStatus());
        log.info("summary {}", game);
    }

    @Test
    public void expect_deuce_when_player1_and_player2_have_same_score_40_test() {
        //GIVEN
        final Game game = new Game(player1, player2, 3 , 3);

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 3, game.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 3, game.getPlayer2Points());
        Assert.assertEquals("Invalid game status", "deuce", game.getStatus());
        log.info("summary {}", game);
    }

    @Test
    public void expect_deuce_when_player1_has_5_points_and_player2_has_4_points_test() {
        //GIVEN
        final Game game = new Game(player1, player2, 5 , 4);

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 5, game.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 4, game.getPlayer2Points());
        Assert.assertEquals("Invalid game status", "advantage", game.getStatus());
        Assert.assertEquals("Invalid game over", false, game.isOver());
        Assert.assertEquals("Invalid game winner", false, game.isPlayer1Winner());
        Assert.assertEquals("Invalid game winner", false, game.isPlayer2Winner());
        log.info("summary {}", game);
    }

    @Test
    public void expect_game_done_when_player1_has_6_points_and_player2_has_4_points_test() {
        //GIVEN
        final Game game = new Game(player1, player2, 6 , 4);

        //EXPECT
        Assert.assertEquals("Invalid P1 new total points", 6, game.getPlayer1Points());
        Assert.assertEquals("Invalid P2 new total points", 4, game.getPlayer2Points());
        Assert.assertEquals("Invalid game status", "game", game.getStatus());
        Assert.assertEquals("Invalid game over", true, game.isOver());
        Assert.assertEquals("Invalid game winner", true, game.isPlayer1Winner());
        Assert.assertEquals("Invalid game winner", false, game.isPlayer2Winner());
        log.info("summary {}", game);
    }
}
