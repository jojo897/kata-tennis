package com.kata.tennis.game;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
public class GameScenariosTest {

    @ParameterizedTest(name = "{index} {4} scenario")
    @CsvSource({"Jordan,John,2,3,30-40", "Jordan,John,3,3,deuce", "Jordan,John,4,5,advantage"})
    @CsvFileSource(resources = "/game-scenarios-test-data.csv")
    public void expect_correct_status_given_multiple_game_scenarios_test(final ArgumentsAccessor accessor) {
        final Game game = new Game(
                accessor.getString(0), accessor.getString(1), accessor.getInteger(2), accessor.getInteger(3)
        );
        //EXPECT
        Assert.assertEquals("Invalid game status", accessor.getString(4), game.getStatus());
        log.info("summary {}", game);
    }
}
