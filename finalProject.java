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
    int playerDef = 5; //Player defense
    int playerAttack = 10; //Player attack power
    Boolean item = false; //The player initially has no items.
    Boolean playerBlock = false; //Player does not block by default.
    int enemyHp = 0; //Enemy HP
    int enemyDef = 0; //Enemy defense
    int enemyAttack = 0; //Enemy attack
    int floorNumber = 1;
    System.out.println();
    System.out.println("You have entered the arena...");
    String[] enemies = new String[10];
    nameBasicEnemies(enemies);
    String enemyName = "" + enemies[(int)(Math.random() * 10)]; //Randomizes enemy name.
    assignStats(enemyName, enemyHp, enemyDef, enemyAttack);
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
    beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName);
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
  public static int[] assignStats(String enemyName, int enemyHp, int enemyDef, int enemyAttack){
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

  public static void beginCombat(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName){ //Allow player options for combatting the enemy.
    System.out.println();
    System.out.println("Will you attack, defend, use item, or scan enemy?");
    String action = TextIO.getln();
    switch (action){
      case "attack":
        int damage = (playerAttack - (int)(Math.random() * 3)) * (1 - (enemyDef/100)); //Algorithm calculates how much damage the player does.
      enemyHp = enemyHp - damage; //Deal damage.
      System.out.printf("" + enemyName + " took %dn damage!",damage);
      if (enemyHp > 0){ //Check if enemy survived attack.
        enemyAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName); //Enemy's turn begins.
      }
      else {
        System.out.println("You defeated the " + enemyName + "!");
        floorNumber++;
        newFloor(); //Go to next floor.
        }
      break;
      case "defend":
        playerBlock = true;
        enemyAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName);
      case "item":
      case "use item": if (item == false){
        System.out.println("You have no items to use!");
      } else {
        System.out.println("Which item?");
      } break;
      case "scan":
      case "scan enemy":
    }
  }

  public static void enemyAttack(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName){
    int damage = (enemyAttack - (int)(Math.random() * 3)) * (1 - (playerDef/100)); //Algorithm calculates how much damage the player takes.
    int blockDamage = (damage/2);
    if (playerBlock == false){
      playerHp = playerHp - damage;
    }
    else if (playerBlock == true){
      playerHp = playerHp - blockDamage;
      System.out.printf("You took %dn damage!",blockDamage);
    }
    playerBlock = false;
    beginCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName);
  }

  public static void newFloor(){
    
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
