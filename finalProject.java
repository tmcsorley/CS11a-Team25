import java.util.concurrent.TimeUnit;
import java.util.Arrays;

public class finalProject {
  public static void main(String[] args){
    System.out.println("Welcome to Gladiator Arena!"); //Introduction to the user.
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    startMenu();
  }

  public static void startMenu(){
    System.out.println("Type 'start' to begin, 'quit' to exit, or 'credits' to see developer credits.");
    String option = TextIO.getln(); //Receives user input.
    switch (option){
      case "start": beginGame(); break;
      case "quit": quitGame(); break;
      case "credits": showCredits(); startMenu(); break;
      default: System.out.println("I don't understand this command!"); startMenu(); break;
    }
  }

  public static void beginGame(){
    int playerHp = 100; //Initialize player HP
    int maxHp = 100; //Maintains player's max HP despite player taking damage. Can increase but not decrease.
    int playerDef = 5; //Player defense
    int playerAttack = 10; //Player attack power
    Boolean item = false; //The player initially has no items.
    Boolean playerBlock = false; //Player does not block by default.
    int floorNumber = 1;
    String activeItem = ""; //Creates string location for item to be held.
    System.out.println();
    System.out.println("You have entered the arena...");
    String enemyName = ""; //Initializes enemy name string.
    int enemyHp = 0; //Enemy HP
    int enemyDef = 0; //Enemy defense
    int enemyAttack = 0; //Enemy attack
    enemyApproach(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
  }

  /** initializeEnemyArray creates a string array of various enemy names to be
  selected from.*/
  public static String[] nameBasicEnemies(String[] enemies){
    enemies[0] = "Goblin";
    enemies[1] = "Skeleton";
    enemies[2] = "Blob Creature";
    enemies[3] = "Mutant Bat";
    enemies[4] = "Banshee";
    enemies[5] = "Shadow Creature";
    enemies[6] = "Witch";
    enemies[7] = "Ogre";
    enemies[8] = "Evil Fairy";
    enemies[9] = "Enchanted Armor";
    return enemies;
  }

/* assignStats will assess which type of enemy is confronting the player and
  consequently decides the statistics of the enemy. It returns the stats in an array.*/
  public static int[] assignStats(String enemyName){
    int enemyHp = 0; //Enemy HP
    int enemyDef = 0; //Enemy defense
    int enemyAttack = 0; //Enemy attack
    switch (enemyName){
      case "Goblin": enemyHp = 50; enemyDef = 30; enemyAttack = 10; break;
      case "Skeleton": enemyHp = 40; enemyDef = 0; enemyAttack = 10; break;
      case "Blob Creature": enemyHp = 30; enemyDef = 40; enemyAttack = 5; break;
      case "Mutant Bat": enemyHp = 30; enemyDef = 30; enemyAttack = 5; break;
      case "Banshee": enemyHp = 40; enemyDef = 30; enemyAttack = 10; break;
      case "Shadow Creature": enemyHp = 50; enemyDef = 30; enemyAttack = 10; break;
      case "Witch": enemyHp = 50; enemyDef = 30; enemyAttack = 10; break;
      case "Ogre": enemyHp = 60; enemyDef = 30; enemyAttack = 15; break;
      case "Evil Fairy": enemyHp = 30; enemyDef = 30; enemyAttack = 5; break;
      case "Enchanted Armor": enemyHp = 40; enemyDef = 50; enemyAttack = 10; break;
    }
    int[] enemyStats = new int[3];
    enemyStats[0] = enemyHp;
    enemyStats[1] = enemyDef;
    enemyStats[2] = enemyAttack;
    return enemyStats;
  }

  public static void beginCombat(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){ //Allow player options for combatting the enemy.

    System.out.println();
    System.out.println("Will you attack, defend, use item, or scan enemy?");
    String action = TextIO.getln();
    switch (action){
      case "attack":
        int damage = (playerAttack - (int)(Math.random() * 3)) * (1 - (enemyDef/100)); //Algorithm calculates how much damage the player does.
        enemyHp = enemyHp - damage; //Deal damage.
        System.out.printf("" + enemyName + " took %d damage! %n",damage);
        try {
          TimeUnit.SECONDS.sleep(1); //Waits 1 second.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
        if (enemyHp <= 0){ //Check if enemy survived attack.
          System.out.println();
          System.out.println("You defeated the " + enemyName + "!");
          try {
            TimeUnit.SECONDS.sleep(1); //Waits 1 second.
          }
          catch (InterruptedException e) {
            e.printStackTrace(); //Prints exception.
          }
          System.out.println();
          newFloor(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); //Go to next floor.
        }
        else if (enemyHp > 0){
          enemyAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); //Enemy's turn begins.
        }
        break;
      case "defend":
        playerBlock = true;
        enemyAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
        break;
      case "item":
      case "use item":
        if (item == false){
          System.out.println();
          System.out.println("You have no items to use!");
          beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
          break;
        }
        else if (item == true){
          System.out.println("Use the " + activeItem + "?");
          String yesNo = TextIO.getln();
          switch (yesNo){ //Test for yes or no response.
            case "yes":
              switch (activeItem){
                case "Potion":
                  if (playerHp < (maxHp - 50)){
                    playerHp = playerHp + 50;
                  }
                  else {
                    playerHp = maxHp;
                  }
                  System.out.println();
                  System.out.println("You restored 50 HP.");
                  System.out.printf("Player HP: %d%n",playerHp);
                  try {
                    TimeUnit.SECONDS.sleep(1); //Waits 1 second.
                  }
                  catch (InterruptedException e) {
                    e.printStackTrace(); //Prints exception.
                  }
                  System.out.println();
                  item = false;
                  beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  break;
                case "Large Potion":
                  playerHp = maxHp;
                  System.out.println();
                  System.out.println("You fully healed.");
                  try {
                    TimeUnit.SECONDS.sleep(1); //Waits 1 second.
                  }
                  catch (InterruptedException e) {
                    e.printStackTrace(); //Prints exception.
                  }
                  System.out.println();
                  item = false;
                  beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  break;
                case "Bomb":
                  enemyHp = enemyHp - 30;
                  if (enemyHp <= 0){ //Check if enemy survived attack.
                    System.out.println();
                    System.out.println("You defeated the " + enemyName + "!");
                    item = false;
                    try {
                      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
                    }
                    catch (InterruptedException e) {
                      e.printStackTrace(); //Prints exception.
                    }
                    System.out.println();
                    newFloor(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); //Go to next floor.
                  }
                  else {
                    System.out.println();
                    System.out.println("Enemy took 30 damage!");
                    item = false;
                    try {
                      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
                    }
                    catch (InterruptedException e) {
                      e.printStackTrace(); //Prints exception.
                    }
                    System.out.println();
                    beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  }
                  break;
                case "Shield-B-Gone":
                  enemyDef = enemyDef - 4;
                  System.out.println("Enemy's defense decreased!");
                  item = false;
                  try {
                    TimeUnit.SECONDS.sleep(1); //Waits 1 second.
                  }
                  catch (InterruptedException e) {
                    e.printStackTrace(); //Prints exception.
                  }
                  System.out.println();
                  beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  break;
                case "Weakening Elixir":
                  enemyAttack = enemyAttack - 4;
                  System.out.println();
                  System.out.println("Enemy's attack power decreased!");
                  item = false;
                  try {
                    TimeUnit.SECONDS.sleep(1); //Waits 1 second.
                  }
                  catch (InterruptedException e) {
                    e.printStackTrace(); //Prints exception.
                  }
                  System.out.println();
                  beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  break;
              }
            case "no": beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); break;
            default: System.out.println("Not a valid command!"); beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); break;
          }
          break;
        }
      case "scan":
      case "scan enemy":
        System.out.printf("" + enemyName + ": %d HP, %d Defense, %d Attack%n",enemyHp,enemyDef,enemyAttack);
        try {
          TimeUnit.SECONDS.sleep(3); //Waits 3 seconds.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
        enemyAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
        break;
      case "quit":
      case "exit":
      case "end": quitGame(); break;
      default: System.out.println("That is not a valid command!");
      beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
    }
  }

  public static void enemyAttack(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){
    int damage = (enemyAttack - (int)(Math.random() * 3)) * (1 - (playerDef/100)); //Algorithm calculates how much damage the player takes.
    int blockDamage = (damage/2);
    if (playerBlock == false){
      playerHp = playerHp - damage;
      if (playerHp < 0){
        playerHp = 0;
      }
      System.out.println();
      System.out.printf("You took %d damage! %n",damage);
      System.out.printf("Player HP: %d%n",playerHp);
    }
    else if (playerBlock == true){
      playerHp = playerHp - blockDamage;
      if (playerHp < 0){
        playerHp = 0;
      }
      System.out.println();
      System.out.printf("You took %d damage! %n",blockDamage);
      System.out.printf("Player HP: %d%n",playerHp);
    }
    if (playerHp == 0){
      gameOver(enemyName, floorNumber);
    }
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    playerBlock = false;
    beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
  }

  public static void enemyApproach(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){
    String[] enemies = new String[10];
    nameBasicEnemies(enemies);
    enemyName = "" + enemies[(int)(Math.random() * 10)]; //Randomizes enemy name.
    int[] enemyStats = assignStats(enemyName);
    enemyHp = enemyStats[0]; //Enemy HP
    enemyDef = enemyStats[1]; //Enemy defense
    enemyAttack = enemyStats[2]; //Enemy attack
    char c = enemyName.charAt(0);
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    if (c=='A' || c=='E' || c=='I' || c=='O' || c=='U') { //Determines if enemy name starts with a vowel
      System.out.println();
      System.out.println("An " + enemyName + " approaches.");
    }
    else{
      System.out.println();
      System.out.println("A " + enemyName + " approaches.");
    }
    beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
  }

  /*newFloor increases floor number, levels up player, scans for trigger floors, and gives chance at receiving an item.*/
  public static void newFloor(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){
    floorNumber++;
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    /*if (floorNumber%5 == 0){
      //Boss battle.
    }*/
    int numGen = (int)(Math.random() * 10); //Generates random number.
    if (numGen == 3 || numGen == 8 || numGen == 9){ //If RNG creates 3, 8, 9, an item is chosen.
      int itemGen = (int)(Math.random() * 5); //Chooses random item.
      String tempItem = "";
      switch (itemGen){
        case 0: tempItem = "Potion"; break;
        case 1: tempItem = "Large Potion"; break;
        case 2: tempItem = "Bomb"; break;
        case 3: tempItem = "Shield-B-Gone"; break;
        case 4: tempItem = "Weakening Elixir"; break;
      }
      if (item == false){
        activeItem = tempItem;
        System.out.println();
        System.out.println("Congrats! You received a " + activeItem + "!");
        item = true;
      }
      else if (item == true){
        System.out.println("Would you like to swap your " + activeItem + " with a " + tempItem + "?");
        String yesNo = "";
        do {
          yesNo = TextIO.getln();
          if (yesNo.equalsIgnoreCase("yes")){
            activeItem = tempItem;
            System.out.println();
            System.out.println("Congrats! You received a " + activeItem + "!");
            break;
          }
          else if (yesNo.equalsIgnoreCase("no")){
            break;
          }
          else {
            System.out.println("Not a valid answer. Yes or no?");
          }
        } while (!yesNo.equalsIgnoreCase("yes") || !yesNo.equalsIgnoreCase("no"));
      }
      try {
        TimeUnit.SECONDS.sleep(1); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
    }
    System.out.println();
    System.out.println("Please choose a stat to increase:");
    System.out.println("'HP', 'Defense', 'Attack'");
    String levelUp = TextIO.getln();
    if (levelUp.equalsIgnoreCase("hp")){
      maxHp = maxHp + 10; //Max health increase.
      playerHp = playerHp + 10; //Immediate health increase.
      playerHp = playerHp + ((maxHp - playerHp)/2); //Heals half of missing health.
      try {
        TimeUnit.SECONDS.sleep(1); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      System.out.printf("Your HP is now %d! %n", maxHp);
      try {
        TimeUnit.SECONDS.sleep(1); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      System.out.println("You healed 50% of your lost health.");
    }
    else if (levelUp.equalsIgnoreCase("defense")){
      try {
        TimeUnit.SECONDS.sleep(1); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      playerDef = playerDef + 1;
      System.out.println("Your defense has increased!");
    }
    else if (levelUp.equalsIgnoreCase("attack")){
      try {
        TimeUnit.SECONDS.sleep(1); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      playerAttack = playerAttack + 1;
      System.out.println("Your attack has increased!");
    }
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    System.out.println();
    System.out.printf("Now entering floor %d...%n",floorNumber);
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    enemyApproach(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
  }

  public static void gameOver(String enemyName, int floorNumber){
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    System.out.println();
    System.out.println("You have perished at the hands of the " + enemyName + ".");
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    System.out.println();
    if (floorNumber == 1){
      System.out.printf("You only reached one floor...");
    }
    else {
      System.out.printf("You reached %d floors.%n",floorNumber);
    }
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    System.out.println();
    System.out.println("Would you like to 'quit', or 'replay'?");
    String endResponse = TextIO.getln();
    switch (endResponse){
      case "quit": quitGame(); break;
      case "replay": beginGame(); break;
    }
  }

  public static void quitGame(){
    System.exit(0);
  }

  public static void showCredits(){
    System.out.println();
    String filename = "devCredits.txt";
    TextIO.readFile(filename);
    String line = "";
    while (!TextIO.eof()){
      line = TextIO.getln();
      System.out.println(line);
      try {
        TimeUnit.SECONDS.sleep(1); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
    }
    System.out.println();
    TextIO.readStandardInput();
  }
}
