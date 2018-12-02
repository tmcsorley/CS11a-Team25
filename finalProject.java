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
        TimeUnit.SECONDS.sleep(1); //Waits 2 seconds.
      }
      catch (InterruptedException e) {
        e.printStackTrace(); //Prints exception.
      }
    }
    System.out.println();
    TextIO.readStandardInput();
  }
}
