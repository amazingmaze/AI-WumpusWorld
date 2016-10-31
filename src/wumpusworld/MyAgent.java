package wumpusworld;

import java.util.Random;

/**
 * Contains starting code for creating your own Wumpus World agent. Currently
 * the agent only make a random decision each turn.
 *
 * @author Johan Hagelbäck
 */
public class MyAgent implements Agent {

    private World w;
    
    // Get the QTable instance.
    private double[][] Q = QTable.getInstance();

    // Current position on the map.
    private int currentPos;

    /**
     * Creates a new instance of your solver agent.
     *
     * @param world Current world state
     */
    public MyAgent(World world) {
        w = world;
        // Set to starting position
        currentPos = 11;

    }

    @Override
    public void train() {       
        // Keep old position
        int oldPos = currentPos;

        // Select an action
        int action = selectAction();
        
        // Execute the selected action
        executeAction(action);

        // Take the action and get the reward
        int newPos = currentPos;
        double reward = Q[oldPos][0];
        
        // Get the max Q-value of the new position.
        double maxQValueOfNextState = QTable.getMaxQValue(newPos);
        
        // Update the Q-table, specifically the old State-action value. 
        Q[oldPos][action] = Q[oldPos][action] + QConfig.LEARNINGRATE * (reward + (QConfig.DISCOUNTFACTOR * maxQValueOfNextState) - Q[oldPos][action]);
        
    }

    // This method will select an action, and based on some set probability it will take a random action instead which forces exploration.
    public int selectAction() {
        Random rand = new Random();
        if (Math.random() < QConfig.PROBABILITY) {
            return rand.nextInt(4) + 1;
        } else {
            return QTable.getMaxQValueAction(currentPos);
        }

    }
    
    // Executes the selected action.
    public void executeAction(int action) {
        
        // Check for pits, gold, wumpus
        checkState();
        
        //Location of the player
        int x = w.getPlayerX();
        int y = w.getPlayerY();
               

        // Right
        if (action == 1) {

            if (w.isValidPosition(x + 1, y)) {
                currentPos = (x + 1) * 10 + y;
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
            } else {
            }

        }
        // Up
        if (action == 2) {

            if (w.isValidPosition(x, y - 1)) {
                
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPos = x * 10 + (y - 1);
            } else {
            }

        }
        // Left
        if (action == 3) {

            if (w.isValidPosition(x - 1, y)) {
                
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPos = (x - 1) * 10 + y;
            } else {
            }

        }
        // Down
        if (action == 4) {
            if (w.isValidPosition(x, y + 1)) {
                
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPos = x * 10 + (y + 1);
            } else {
            }             
        }

    }
    
    // Used to check if the current square has either a wumpus, pit or gold in it.
    public void checkState() {
        int x = w.getPlayerX();
        int y = w.getPlayerY();
        if (w.gameOver()) {
            if (w.hasWumpus(x, y)) {
                // Set the Reward for the currentPos 
                Q[currentPos][0] = QConfig.WUMPUSREWARD;
            }
        } else if (w.hasGlitter(x, y)) {
            // Set the Reward for the currentPos 
            Q[currentPos][0] = QConfig.GOLDREWARD;
            w.doAction(World.A_GRAB);
        } else if (w.hasPit(x, y)) {
            // Set the Reward for the currentPos 
            Q[currentPos][0] = QConfig.PITREWARD;
            w.doAction(World.A_CLIMB);
        }
        
    }

    /**
     * Asks your solver agent to execute an action.
     */
    @Override
    public void doAction() {
        
        // Get the best action from the current state.
        int action = QTable.getMaxQValueAction(currentPos);
        
        // Run the aciton
        executeAction(action);

        int x = w.getPlayerX();
        int y = w.getPlayerY();

        if (w.hasWumpus(x, y)) {
            System.out.println("Wumpus is here");
        } 
        else if (w.hasGlitter(x, y)) {
            // If we found gold, we win.
            System.out.println("Win win win!");
            w.doAction(World.A_GRAB);
        } else if (w.hasPit(x, y)) {
            System.out.println("Shouldn't be here... Unless there's no other way.");
            
        }

    }
     

}
