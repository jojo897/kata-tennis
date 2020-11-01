# kata-tennis<br/>
Le but est de modéliser un match de tennis entre 2 joueurs<br/>
<br/>
Le programme doit permettre de faire gagner les points par un des 2 joueurs et de déterminer le score, le status du jeu en cours (0-0, 15-0, ... Deuce, advantage) ainsi que le status de la partie (in progress, player 1 wins, player 2 wins) selon le format ci-dessous:<br/>
<br/>
***Exemple 1***<br/>
Player 1: nom du joueur 1<br/>
Player 2: nom du joueur 2<br/>
Score: (6-1) (7-5) (1-0)<br/>
Current game status: 15-30<br/>
Match Status: in progress<br/>
<br/>
***Exemple 2***<br/>
Player 1: nom du joueur 1<br/>
Player 2: nom du joueur 2<br/>
Score: (6-1) (7-5) (0-0)<br/>
Current game status: deuce<br/>
Match Status: in progress<br/>
<br/>
***Exemple 3***<br/>
Player 1: nom du joueur 1<br/>
Player 2: nom du joueur 2<br/>
Score: (6-1) (7-5) (0-0)<br/>
Current game status: advantage<br/>
Match Status: in progress<br/>
<br/>
***Exemple 4***<br/>
Player 1: nom du joueur 1<br/>
Player 2: nom du joueur 2<br/>
Score: (6-1) (7-5) (6-0)<br/>
Match Status: Player 1 wins<br/>
<br/>
***Exemple 5***<br/>
Player 1: nom du joueur 1<br/>
Player 2: nom du joueur 2<br/>
Score: (6-1) (7-5) (2-6) (6-7) (4-6)<br/>
Match Status: Player 2 wins<br/>

# Tennis Rules

## Win a game
In a standard game (as opposed to a tie-break), each point increases the score of the player to the next value of the following sequence: 0, 15, 30, 40

### Deuce State
If both players have 40, then it’s deuce. The next player to score a point will have the advantage (yes, it is not the winning point !). If the opponent scores, the advantage is lost and they are back to deuce (40-40)
To win in deuce, a player must:
1.  gain the advantage
2.  score while he has the advantage

### Tie-break
In a tie-break game, points are counted as integers and not using the 0-15-30-40 sequence
To win a tie-break game, a player must score:
* at least 7 points
* 2 more points than the opponent (Rules explained <a href="https://en.wikipedia.org/wiki/Tennis_scoring_system">here</a>
)

## Win a set
To win a game, a player must win:
* at least 6 games
* 2 more games than the opponent

### Tie-break
If both players have 6 games, then the next game is a tie-break
The winner of the tie-breaker games wins the set

## Win the match
To win the match, a player must win 3 sets
