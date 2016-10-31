Assignment 1 - Wumpus World
The wumpus world is a very used and researched problem in artificial intelligence. It’s used in problems like knowledge representation, Reasoning and Planning. It is hide and seek type of problem where the player will go into a cave (a grid pattern type of map) and try to find the gold. In his way, there are pits and a monster called the Wumpus. 


Solution
This solution could be done in several ways. In this solution, machine learning is used. Specifically reinforcement learning and the Q-learning algorithm. 
Some notes about the Q-learning implementation in this solution:


The State-action values are stored in a static 2D array called simply, Q. This static array will be updated as the agent keeps learning. The array looks like this: Q[xy][action] where xy are the concatenated values of the x and y position on the grid. The action variable contains numbers of 0 to 4 included. The 0 value represents the reward of the xy square, i e. Q[11][0] holds the reward (if any) of square where x = 1 and y = 1. And number 1 stands for action “right”, 2 for action “down”, 3 for “left” and 4 for “up”. So Q[34][2] would hold the state-action value for square with x = 3, y = 4 and the action of “down”. 
QTable class holds the Q-table as well as methods needed for the Q-table, that includes getMaxQValueAction, getMaxQValue and a method for printing out the table, printQTable. 
QConfig holds configuration parameters for the entire solution. Here you can tweak it by changing for example the learning rate of the Q-learning algorithm or the number of learning iterations you want the agent to do and the probability of exploration vs exploitation. 


Running the program
When you run the program you want to run it in GUI-mode (standard). Then you want the agent to learn the map selected. You do this by pressing the “Train the Agent!” button. Note that this might hang the program for a few seconds while the agent is learning. The agent will by default run 100000 iterations but this can be changed in the QConfig class. The “Train the Agent!” button will be darkened out while the agent is learning. When this has finished you can start pressing “Run Solving Agent”, this will take you through the route step by step. It will run the route that the agent has learned is the best. 


Notes
A few notes. When the agent has reached the goal it will quickly pick up the gold which will end the game. So it might be harder to notice when it's done. It will print “Win win win” to the console when it has picked up the gold, as well as gain 1000 score points.  
Note that you will have to press “Run Solving Agent” multiple times since it will go through the route step by step. Currently you have to restart the program after you finished running through the route with “Run solving agent”. Meaning that you can’t re-train the agent to another map without restarting the program. 
The agent can handle all of the built-in maps as well as random maps.
The Q-table is currently stored as a static array, meaning it will hold in memory for as long as the program is running, i e. you will have to re-train the agent if the program exits. 






