import java.util.concurrent.TimeUnit;

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
    }
  }

  public static void beginGame(){
    System.out.println();
    System.out.println("You have entered the arena...");
    String[] enemies = new String[10];
    nameBasicEnemies(enemies);
    String enemyName = "" + enemies[(int)(Math.random() * 10)]; //Randomizes enemy name.
    char c = enemyName.charAt(0);
    try {
      TimeUnit.SECONDS.sleep(1); //Waits 1 second.
    }
    catch (InterruptedException e) {
      e.printStackTrace(); //Prints exception.
    }
    if (c=='A' || c=='E' || c=='I' || c=='O' || c=='U') {
      System.out.println();
      System.out.println("An " + enemyName + " approaches.");
    }
    else{
      System.out.println();
      System.out.println("A " + enemyName + " approaches.");
    }
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
