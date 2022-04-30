# Results Report

a. For the alpha-beta agent, compare the effects of increasing search depth and 
improving the evaluation function (which should be briefly described).  

For the alpha-beta agent, here is a short summary of the strength at certain search depths:

- depth = 1

- only strategy is to not move where it can be jumped

depth = 2

- has very limited overall strategy, will mostly just see small sequences of moves that will win pieces but that is it
 
depth = 4

- the agent is very hard to beat now, almost impossible. Many times it will take away key squares for your pieces and force you into the open

depth = 8

- there is no mercy, the agent sees pretty much every tactic, and I can never beat it, or even draw, it keeps double jumping me every time

Also, I improved the evaluation function so that kings count as two piece, and then the agent would very often just jump me, create one king, and then shark many of my pieces from the back

b. For the MCTS agent, compare the performance using the theoretical optimal 
value 𝐶𝐶 = √2 with one using a different value of 𝐶𝐶 set by yourself.  

𝐶𝐶 = 0

- The agent has a really hard time seeing any strategy at all

𝐶𝐶 = 1

- The agent sometimes misses some only-move key sequences that allow you to take the advantage, where if the player makes any other move, the agent would win.

𝐶𝐶 = √2

- The agent is really hard to beat, I can't beat it

𝐶𝐶 = 2

- The agent will sometimes give me pieces for absolutely no reason
 
c. Compare the performances by the alpha-beta search, MCTS, and hybrid agents. 

Alpha-Beta search will always go for tactics, and try to win by taking all of your pieces and pure out-calculation.

Monte-Carlo Search is the much more defensive/positional player, it will not always go for a risky play that it cannot calculate all the possibilities, and will instead find a waiting move to wait for a better opportunity.

The hybrid agent will have a mix of both, like a creative player.
