import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a owl.
 * owles age, move, eat rabbits, and die.
 * 
 * @author Daniel Saisani
 * @version 18/02/2021
 */
public class Owl extends Animal
{
<<<<<<< HEAD
    public void act(List<Animal> newEagles)
    {
        skip;
=======
    // Characteristics shared by all owles (class variables).
    
    // The age at which an owl can start to breed.
    private static final int BREEDING_AGE = 7;
    // The likelihood of an owl breeding.
    private static final double BREEDING_PROBABILITY = 0.054;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 13;
    // number of steps an owl can go before it has to eat again.
    private static final int OWL_FOOD_VALUE = 9;
    private static final int RABBIT_FOOD_VALUE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom(); 
    // Individual characteristics (instance fields).
    // The owl's age.
    // The owl's food level, which is increased by eating rabbits.
    private int foodLevel;  
    /**
     * Create a owl. A owl can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the owl will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Owl(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        setMaxAge(100);
        if(randomAge) {
            setAge(rand.nextInt(getMaxAge()));
            foodLevel = rand.nextInt(OWL_FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = OWL_FOOD_VALUE;
        }
        getGender();
    }
    
    /**
     * This is what the owl does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newowles A list to return newly born owles.
     */
    public void act(List<Animal> newEagles)
    {
        incrementAge();
        incrementHunger();
        deathByAge();
        if(isAlive()) {
            giveBirth(newEagles);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    
    /**
     * Make this owl more hungry. This could result in the owl's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Owl) {
                Owl owl = (Owl) animal;
                if(owl.isAlive()) { 
                    if (owl.getAge()<25){
                        owl.setDead();
                        foodLevel += OWL_FOOD_VALUE;
                        if(foodLevel > 9)
                        {
                        foodLevel = 9;
                        }
                        return where;
                }
                }
            }
            else if(animal instanceof Rabbit)
            {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive())
                {
                    rabbit.setDead();
                    foodLevel += RABBIT_FOOD_VALUE;
                    if(foodLevel > 9)
                    {
                        foodLevel = 9;
                    }
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this owl is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newowles A list to return newly born owles.
     */
    private void giveBirth(List<Animal> newEagles)
    {
        // New owles are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Eagle young = new Eagle(false, field, loc);
            newEagles.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A owl can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return getAge() >= BREEDING_AGE;
>>>>>>> 704cbe1af6ccd6e9d924edb45d0dfd90f0af5ccb
    }
}
