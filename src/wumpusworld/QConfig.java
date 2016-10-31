package wumpusworld;

/**
 *
 * @author matte
 */
public class QConfig {
    
    // Rewards for different states
    static final int PITREWARD = -1;
    static final int WUMPUSREWARD = -1;
    static final int GOLDREWARD = 1;
    
    // Exploration vs exploitation. Set the probability for exploration
    static final double PROBABILITY = 0.2;
    
    // Q-learning params
    static final double LEARNINGRATE = 0.5;
    static final double DISCOUNTFACTOR = 0.8;
    
    // Number of learning iterations
    static final int ITERATIONS = 100000;   
    
}
