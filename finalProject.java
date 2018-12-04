import java.util.concurrent.TimeUnit;
import java.util.Arrays;

public class finalProject {
  public static void main(String[] args){
    System.out.println("Welcome to Gladiator Arena!"); //Introduction to the user.
    try {
      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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

  public static String[] nameBoss(String[] boss){ //boss stats and name
    boss[0] = "Guardian Deity of the Skies, Griffin";
    boss[1] = "Eidolon of the Mystic Fog, Aurora";
    boss[2] = "Dire Demon of the Abyss, Maloch";
    boss[3] = "Cowardly Human, Josh";
    return boss;
  }

  public static int[] assignBossStats(String enemyName, int floorNumber){
    int enemyHp = 0; //Enemy HP
    int enemyDef = 0; //Enemy defense
    int enemyAttack = 0; //Enemy attack
    switch(enemyName){
      case "Guardian Deity of the Skies, Griffin": enemyHp = 125; enemyDef = 50; enemyAttack = 25; break;
      case "Eidolon of the Mystic Fog, Aurora": enemyHp = 100; enemyDef = 65; enemyAttack = 20; break;
      case "Dire Demon of the Abyss, Maloch": enemyHp = 150; enemyDef = 25; enemyAttack = 15; break;
      case "Cowardly Human, Josh": enemyHp = 1000; enemyDef = 90; enemyAttack = 100; break;
    }
    int[] enemyStats = new int[3];
    if(floorNumber == 5){
      enemyStats[0] = enemyHp;
      enemyStats[1] = enemyDef;
      enemyStats[2] = enemyAttack;
    }
    else if(floorNumber == 10){   //enemy stat scaling
      enemyStats[0] = (int)(enemyHp * 1.5);
      enemyStats[1] = enemyDef + 10;
      enemyStats[2] = (int)(enemyAttack * 1.6);
    }
    else if(floorNumber >= 15){
      enemyStats[0] = (int)(enemyHp * 3);
      enemyStats[1] = enemyDef + 20;
      enemyStats[2] = (int)(enemyAttack * 3);
    }
    return enemyStats;
  }

/* assignStats will assess which type of enemy is confronting the player and
  consequently decides the statistics of the enemy. It returns the stats in an array.*/
  public static int[] assignStats(String enemyName, int floorNumber){
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
    if(floorNumber < 5){
      enemyStats[0] = enemyHp;
      enemyStats[1] = enemyDef;
      enemyStats[2] = enemyAttack;
    }
    else if(floorNumber > 5 & floorNumber < 10){   //enemy stat scaling
      enemyStats[0] = (int)(enemyHp * 1.5);
      enemyStats[1] = enemyDef + 10;
      enemyStats[2] = (int)(enemyAttack * 1.6);
    }
    else if(floorNumber > 10){
      enemyStats[0] = (int)(enemyHp * 3);
      enemyStats[1] = enemyDef + 20;
      enemyStats[2] = (int)(enemyAttack * 3);
    }
    return enemyStats;
  }

  public static void beginCombat(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){
    System.out.println();
    System.out.println("Will you attack, defend, use item, or scan enemy?");
    String action = TextIO.getln();
    switch (action){
      case "attack":
        int damage = (playerAttack - (int)(Math.random() * 3)) * (1 - (enemyDef/100)); //Algorithm calculates how much damage the player does.
        enemyHp = enemyHp - damage; //Deal damage.
        System.out.printf("" + enemyName + " took %d damage! %n",damage);
        try {
          TimeUnit.SECONDS.sleep(2); //Waits 1 second.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
        if (enemyHp <= 0){ //Check if enemy survived attack.
          System.out.println();
          System.out.println("You defeated the " + enemyName + "!");
          try {
            TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
                    TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
                  System.out.println("You have been fully healed.");
                  try {
                    TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
                      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
                      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
                    TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
                    TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
          TimeUnit.SECONDS.sleep(2); //Waits 3 seconds.
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


  public static void beginBossCombat(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){
    System.out.println();
    System.out.println("Will you attack, defend, use item, or scan enemy?");
    String action = TextIO.getln();
    switch (action){
      case "attack":
        int damage = (playerAttack - (int)(Math.random() * 3)) * (1 - (enemyDef/100)); //Algorithm calculates how much damage the player does.
        enemyHp = enemyHp - damage; //Deal damage.
        System.out.printf("" + enemyName + " took %d damage! %n",damage);
        try {
          TimeUnit.SECONDS.sleep(2); //Waits 1 second.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
        if (enemyHp <= 0){ //Check if enemy survived attack.
          System.out.println();
          System.out.println("Congratulations Mighty Warrior! You have defeated " + enemyName + "! On this day a new legend has been born!");
          try {
            TimeUnit.SECONDS.sleep(2); //Waits 1 second.
          }
          catch (InterruptedException e) {
            e.printStackTrace(); //Prints exception.
          }
          System.out.println();
          newFloor(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); //Go to next floor.
        }
        else if (enemyHp > 0){
          bossAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); //Enemy's turn begins.
        }
        break;
      case "defend":
        playerBlock = true;
        bossAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
        break;
      case "item":
      case "use item":
        if (item == false){
          System.out.println();
          System.out.println("You have no items to use!");
          beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
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
                    TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                  }
                  catch (InterruptedException e) {
                    e.printStackTrace(); //Prints exception.
                  }
                  System.out.println();
                  item = false;
                  beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  break;
                case "Large Potion":
                  playerHp = maxHp;
                  System.out.println();
                  System.out.println("You have been fully healed.");
                  try {
                    TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                  }
                  catch (InterruptedException e) {
                    e.printStackTrace(); //Prints exception.
                  }
                  System.out.println();
                  item = false;
                  beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  break;
                case "Bomb":
                  enemyHp = enemyHp - 30;
                  if (enemyHp <= 0){ //Check if enemy survived attack.
                    System.out.println();
                    System.out.println("Congratulations proud Warrior! you have defeated " + enemyName + "! A legend has been born!");
                    item = false;
                    try {
                      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
                      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                    }
                    catch (InterruptedException e) {
                      e.printStackTrace(); //Prints exception.
                    }
                    System.out.println();
                    beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  }
                  break;
                case "Shield-B-Gone":
                  enemyDef = enemyDef - 4;
                  System.out.println("Enemy's defense decreased!");
                  item = false;
                  try {
                    TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                  }
                  catch (InterruptedException e) {
                    e.printStackTrace(); //Prints exception.
                  }
                  System.out.println();
                  beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  break;
                case "Weakening Elixir":
                  enemyAttack = enemyAttack - 4;
                  System.out.println();
                  System.out.println("Enemy's attack power decreased!");
                  item = false;
                  try {
                    TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                  }
                  catch (InterruptedException e) {
                    e.printStackTrace(); //Prints exception.
                  }
                  System.out.println();
                  beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                  break;
              }
            case "no": beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); break;
            default: System.out.println("Not a valid command!"); beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); break;
          }
          break;
        }
      case "scan":
      case "scan enemy":
        System.out.printf("" + enemyName + ": %d HP, %d Defense, %d Attack%n",enemyHp,enemyDef,enemyAttack);
        try {
          TimeUnit.SECONDS.sleep(2); //Waits 3 seconds.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
        bossAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
        break;
      case "quit":
      case "exit":
      case "end": quitGame(); break;
      default: System.out.println("That is not a valid command!");
      beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
    }
  }

   public static void bossAttack(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){
    if(enemyName.equals("Guardian Deity of the Skies, Griffin")){
      int attack =(int)(Math.random() * 10);
      if(attack <= 1){
          System.out.println("The Griffin looks down on you with contempt. Your attacks mean nothing to it. (boss does nothing)");
          beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
        }
    if(attack >=2 & attack <= 5){

        System.out.println("The mighty Griffin rakes his claws at you.");

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
          TimeUnit.SECONDS.sleep(2); //Waits 1 second.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
        playerBlock = false;
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }
      if(attack==6){
        int heal = ((int)(Math.random()) * 4 + 8);
        System.out.println("With a surge of energy the Griffin heals himself! Griffin heals " + heal + "!");
        enemyHp = enemyHp + heal;
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
        }
      if(attack==7 || attack ==8){
        System.out.println("The griffin readys his claws... (boss attack up!)");
        enemyAttack = enemyAttack + 1;
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
        }
      else{
        System.out.println("the griffin steels himself for your attack... (boss def up!)");
        enemyDef = enemyDef + 5;
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }
    }

    else if(enemyName.equals("Eidolon of the Mystic Fog, Aurora")){
      int attack =(int)(Math.random() * 10);
      int attack2 = (int)(Math.random() * 10);
      if(attack >= 8 & attack2 >=7){
        System.out.println();
        System.out.println("The Eidolon rears its head. It looks like a big attack is coming!!");

        System.out.println();
        System.out.println("Will you attack, defend, use item, or scan enemy?");
        String action = TextIO.getln();
        switch (action){
          case "attack":
            int damage = (playerAttack - (int)(Math.random() * 3)) * (1 - (enemyDef/100)); //Algorithm calculates how much damage the player does.
            enemyHp = enemyHp - damage; //Deal damage.
            System.out.printf("" + enemyName + " took %d damage! %n",damage);
            try {
              TimeUnit.SECONDS.sleep(2); //Waits 1 second.
            }
            catch (InterruptedException e) {
              e.printStackTrace(); //Prints exception.
            }
            if (enemyHp <= 0){ //Check if enemy survived attack.
              System.out.println();
              System.out.println("Congratulations Mighty Warrior! You have defeated " + enemyName + "! On this day a new legend has been born!");
              try {
                TimeUnit.SECONDS.sleep(2); //Waits 1 second.
              }
              catch (InterruptedException e) {
                e.printStackTrace(); //Prints exception.
              }
              System.out.println();
              newFloor(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); //Go to next floor.
            }
            else if (enemyHp > 0){
              System.out.println("The Eidolon fires off a deadly icy torrent!");
               damage = (enemyAttack - (int)(Math.random() * 3)) * 3 * (1 - (playerDef/100)); //Algorithm calculates how much damage the player takes.
              int blockDamage = (damage/2);
              if (playerBlock == false){
                playerHp = playerHp - damage;
                if (playerHp < 0){
                  playerHp = 0;                                 //this section creates a two turn timer for an attack when you can decide if you want to defend
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
                TimeUnit.SECONDS.sleep(2); //Waits 1 second.
              }
              catch (InterruptedException e) {
                e.printStackTrace(); //Prints exception.
              }
              playerBlock = false;
              beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);

            }
            break;
          case "defend":
          System.out.println();
          System.out.println("The Eidolon fires off a deadly icy torrent!");
           damage = (enemyAttack - (int)(Math.random() * 3)) * 3 * (1 - (playerDef/100)); //Algorithm calculates how much damage the player takes.
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
            TimeUnit.SECONDS.sleep(2); //Waits 1 second.
          }
          catch (InterruptedException e) {
            e.printStackTrace(); //Prints exception.
          }
          playerBlock = false;
          beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
            break;
          case "item":
          case "use item":
            if (item == false){
              System.out.println();
              System.out.println("You have no items to use!");
              beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
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
                        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                      }
                      catch (InterruptedException e) {
                        e.printStackTrace(); //Prints exception.
                      }
                      System.out.println();
                      item = false;
                      beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                      break;
                    case "Large Potion":
                      playerHp = maxHp;
                      System.out.println();
                      System.out.println("You have been fully healed.");
                      try {
                        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                      }
                      catch (InterruptedException e) {
                        e.printStackTrace(); //Prints exception.
                      }
                      System.out.println();
                      item = false;
                      beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                      break;
                    case "Bomb":
                      enemyHp = enemyHp - 30;
                      if (enemyHp <= 0){ //Check if enemy survived attack.
                        System.out.println();
                        System.out.println("Congratulations proud Warrior! you have defeated " + enemyName + "! A legend has been born!");
                        item = false;
                        try {
                          TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
                          TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                        }
                        catch (InterruptedException e) {
                          e.printStackTrace(); //Prints exception.
                        }
                        System.out.println();
                        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                      }
                      break;
                    case "Shield-B-Gone":
                      enemyDef = enemyDef - 4;
                      System.out.println("Enemy's defense decreased!");
                      item = false;
                      try {
                        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                      }
                      catch (InterruptedException e) {
                        e.printStackTrace(); //Prints exception.
                      }
                      System.out.println();
                      beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                      break;
                    case "Weakening Elixir":
                      enemyAttack = enemyAttack - 4;
                      System.out.println();
                      System.out.println("Enemy's attack power decreased!");
                      item = false;
                      try {
                        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
                      }
                      catch (InterruptedException e) {
                        e.printStackTrace(); //Prints exception.
                      }
                      System.out.println();
                      beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
                      break;
                  }
                case "no": beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); break;
                default: System.out.println("Not a valid command!"); beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp); break;
              }
              break;
            }
          case "scan":
          case "scan enemy":
            System.out.printf("" + enemyName + ": %d HP, %d Defense, %d Attack%n",enemyHp,enemyDef,enemyAttack);
            try {
              TimeUnit.SECONDS.sleep(2); //Waits 3 seconds.
            }
            catch (InterruptedException e) {
              e.printStackTrace(); //Prints exception.
            }
            bossAttack(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
            break;
          case "quit":
          case "exit":
          case "end": quitGame(); break;
          default: System.out.println("That is not a valid command!");
          beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
        }

      }
      if(attack >=2 & attack <= 4){
        System.out.println();
        System.out.println("With a great bellow, the Eidolon fires icy bolts at you.");

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
          TimeUnit.SECONDS.sleep(2); //Waits 1 second.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
        playerBlock = false;
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }
      else {
        System.out.println("The Eidolon stares at you, unblinking... (boss does nothing)");
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }


    }
    else if(enemyName.equals("Dire Demon of the Abyss, Maloch")){
      int attack =(int)(Math.random() * 10);
      if(attack <=3){
        System.out.println();
        System.out.println("Pitiful human. You DARE challenge the great Maloch?! I will have your SOUL!! (Boss does nothing)");
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }
      else if(attack >=4 & attack <= 7){
        System.out.println();
        System.out.println("Feel the heat of my undying flames!");
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
          TimeUnit.SECONDS.sleep(2); //Waits 1 second.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
        playerBlock = false;
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }
    else if(attack >=8){
      if(item==true){
        System.out.println();
        System.out.println("Pitiful fool,  you thought " + activeItem + " could save you? (Maloch steals your item)");
        item = false;
        activeItem = "";
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }
      if(item==false){
        System.out.println();
        System.out.println("You DARE underestimate ME? The pinnacle of all Demons? Fine, I will show you TRUE POWER.");
        enemyAttack = enemyAttack + 3;
        enemyDef = enemyDef + 5;
        beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }
    }

    }
    else if(enemyName.equals("Cowardly Human, Josh")){
      System.out.println("Please don't hurt me... I'm just a weak human... just look at my stats...");
      System.out.println("Hp: " + enemyHp + "    Atk: " + enemyAttack + "      Def: " + enemyDef);
      try {
        TimeUnit.SECONDS.sleep(4); //Waits 4 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      System.out.println("See, I'm no match for you oh great and powerful warrior!..... I'll just take my leave.....");
      System.out.println();
      System.out.println("" + enemyName + " has just fleed!");
      newFloor(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
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

    public static void bossApproach(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){
      String[] boss = new String [4];
      nameBoss(boss);
      enemyName = "" + boss[(int)(Math.random() * 4)];
      int[] enemyStats = assignBossStats(enemyName, floorNumber);
      enemyHp = enemyStats[0]; //Enemy HP
      enemyDef = enemyStats[1]; //Enemy defense
      enemyAttack = enemyStats[2]; //Enemy attack
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      switch(enemyName){
        case "Cowardly Human, Josh" :
          System.out.println("Steel your wits! Calm your mind! Prepare yourself Warrior, for " + enemyName + " approaches!");
            try {
              TimeUnit.SECONDS.sleep(2); //Waits 1 second.
            }
            catch (InterruptedException e) {
              e.printStackTrace(); //Prints exception.
            }
            System.out.println("....Cowardly?");
            try {
              TimeUnit.SECONDS.sleep(2); //Waits 1 second.
            }
            catch (InterruptedException e) {
              e.printStackTrace(); //Prints exception.
            }
            System.out.println("....Human?");
            try {
              TimeUnit.SECONDS.sleep(2); //Waits 1 second.
            }
            catch (InterruptedException e) {
              e.printStackTrace(); //Prints exception.
            }
            System.out.println("....JOSH?!?");
            beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      break;
      default :   System.out.println("Steel your wits! Calm your mind! Prepare yourself Warrior, for " + enemyName + " approaches!");
      beginBossCombat(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      break;
    }
  }


  public static void enemyApproach(int playerHp, int playerDef, int playerAttack, Boolean item, Boolean playerBlock, int enemyHp, int enemyDef, int enemyAttack, int floorNumber, String enemyName, String activeItem, int maxHp){
    String[] enemies = new String[10];
    nameBasicEnemies(enemies);
    enemyName = "" + enemies[(int)(Math.random() * 10)]; //Randomizes enemy name.
    int[] enemyStats = assignStats(enemyName, floorNumber);
    enemyHp = enemyStats[0]; //Enemy HP
    enemyDef = enemyStats[1]; //Enemy defense
    enemyAttack = enemyStats[2]; //Enemy attack
    char c = enemyName.charAt(0);
    try {
      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    if (floorNumber%6 == 0){
      System.out.println("Brave warrior, for defeating the legendary " + enemyName + ". The Gods have granted you their blessings! (All stats up)");
      playerAttack = playerAttack + 5;
      maxHp = maxHp + 15;
      playerHp = maxHp;
      playerDef = playerDef + 10;

      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }

      System.out.printf("Your current and max hp is %d. %n", maxHp);
      System.out.printf("Your current attack is %d. %n", playerAttack);
      System.out.printf("Your current def is %d. %n", playerDef);

      try {
        TimeUnit.SECONDS.sleep(5); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }

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
          TimeUnit.SECONDS.sleep(2); //Waits 1 second.
        }
        catch (InterruptedException e) {
          e.printStackTrace(); //Prints exception.
        }
      }
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      System.out.println("Be wary.... the enemies have grown much stronger....");
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      System.out.printf("Now entering floor %d...%n", floorNumber);
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      enemyApproach(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
    }
  else {
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
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      System.out.printf("Your maximum HP is now %d! %n", maxHp);
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      System.out.println("You healed 50% of your lost health.");
      System.out.printf("Your current HP is now %d! %n", playerHp);
    }
    else if (levelUp.equalsIgnoreCase("defense")){
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      playerDef = playerDef + 3;
      System.out.println("Your defense has increased!");
      System.out.printf("Your current defense is %d. %n", playerDef);
    }
    else if (levelUp.equalsIgnoreCase("attack")){
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      playerAttack = playerAttack + 3;
      System.out.println("Your attack has increased!");
      System.out.printf("Your current attack is %d. %n", playerAttack);
    }
    try {
      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    System.out.println();

    if(floorNumber % 5 == 0){
      System.out.println("You hear a roar echoing from below... you have a bad feeling about this... (you have been fully healed!)");
      playerHp = maxHp;
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      System.out.printf("Now entering floor %d...%n", floorNumber);
      bossApproach(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
    }
    else {
      System.out.printf("Now entering floor %d...%n",floorNumber);
      try {
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
      enemyApproach(playerHp, playerDef, playerAttack, item, playerBlock, enemyHp, enemyDef, enemyAttack, floorNumber, enemyName, activeItem, maxHp);
      }
    }
  }

  public static void gameOver(String enemyName, int floorNumber){
    try {
      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    System.out.println();
    System.out.println("You have perished at the hands of the " + enemyName + ".");
    try {
      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
      TimeUnit.SECONDS.sleep(2); //Waits 1 second.
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
        TimeUnit.SECONDS.sleep(2); //Waits 1 second.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
    }
    System.out.println();
    TextIO.readStandardInput();
  }
}
