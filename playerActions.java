import java.util.Scanner;
class playerActions{ 
  String action;
  Scanner input = new Scanner(System.in);
  playerActions(int[][] table2, boolean end){
  }
  String getAction(){
    System.out.println("Enter an action.\nEnter w to go up, s to go down, d to go right and a to go left. To check your time, enter t.");
		action = input.nextLine();
    return action;
  }
}
