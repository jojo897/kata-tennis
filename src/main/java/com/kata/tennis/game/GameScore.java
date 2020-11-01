package com.kata.tennis.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor @ToString(of = "value")
public enum GameScore {
    love(0, 0),
    fifteen(15,1),
    thirty(30, 2),
    forty(40, 3),
    game(40, 4);

    private @Getter final Integer value;
    private @Getter final Integer points;

    private static final Map<Integer, GameScore> scoresByPoint = Arrays.stream(values()).collect(Collectors.toMap(GameScore::getPoints, Function.identity()));

    public static Integer evaluatePoints(final int points) {
        final GameScore score = scoresByPoint.getOrDefault(points, null);
        return score != null ? score.value : null;
    }
}
