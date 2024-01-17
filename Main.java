import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalTime;
import java.time.ZonedDateTime;   
class Main{

//************************ ASCII Image Colours *************************

public static final String ANSI_RESET= "\u001B[0m";
public static final String ANSI_CYAN= "\u001B[36m";
public static final String ANSI_YELLOW= "\u001B[33m";
public static final String ANSI_RED= "\u001b[31m";

//**********************************************************************

	public static void main(String[] args) throws Exception
	{
    String maze=fileToASCIIimage("maze.txt"); //Setting maze variable to the string fileToASCIIimage that will read the text out from the maze.txt
    System.out.println(ANSI_CYAN + ANSI_RED + maze + ANSI_RESET); //Print "MAZE GAME out on console when game is run"
    int endTime = 9999999;//set variable to an impossible number so certain code doesn't run before the game is played once and the value of this variable is changed
    String level="0";
    String input = null;
    Scanner scan=new Scanner(System.in);
    System.out.println("What would you like your name to display as on the leaderboard.");
    String name=scan.nextLine();//let user set their name
    do{
      boolean end=false;
      do{
        System.out.println("This is a game all about speed. You must complete a maze, which is randomly generated, as fast as possible. If you want to try you can press 1. If you would like to look at the leaderboard section press 2. if you would like to exit press 3.");
        input=scan.nextLine();
        if(input.equals("2"))
        {
          String choice = null;
          System.out.println("Which leaderboard would you like to view (type either easy, medium or hard)");
          choice = scan.nextLine().toLowerCase();
          while(!choice.equals("easy") && !choice.equals("medium") && !choice.equals("hard")){
            System.out.println("Please type easy, medium or hard to view the corresponding leaderboard");
            choice = scan.nextLine().toLowerCase();
          }
          leaderboard a = new leaderboard(name, endTime);//instantiate new object and set object parameters 
          String[][] list = a.displayBoard(choice);//call method used to show leaderboard
          for(int i=0;i<list.length;i++){
            for(int j=0;j<3;j++){
              if(j==0){
                //%-# and | is used to format console output
                System.out.format("%-5.5s",list[i][j]);
              }
              else if(j==1){
                System.out.format("%-20.20s", "|" + list[i][j]);
              }
              else{
                System.out.format("%-40.40s", "|" + list[i][j]);
              }
            }
            System.out.println();
          }
          Thread.sleep(1000);
          if(endTime!=9999999){//if user has played the game once, endTime's value won't equal an impossible value
            System.out.println("Would you like to add your score to the leaderboard?(y/n)");
            input = scan.nextLine().toLowerCase();
            if(input.equals("y")){
              a.scorePosition(list, level);//if user wants to add their score to the leaderboard, call scorePosition method from leaderboard class
            }
          }
        }
        else if(input.equals("3"))
        {
          System.exit(1);//exits program
        }
      }while(!input.equals("1"));
      System.out.println("What difficulty would you like, 1 for easy, 2 for medium and 3 for hard.(Easy (10x10) Medium(20x20) Hard(30x30))");
      level=scan.nextLine();//getting what difficulty the player wants
      int[][] table2=level(level);//getting the random maze
      int length=table2.length;//getting the length of the maze
      System.out.println("To ensure you're screen is in correct resolution to view the maze, please take the next 5 seconds to make sure the following line fits on a single line");
      System.out.println("_____________________________________________________________");
      Thread.sleep(5000);//waits 5 seconds for you to re adjust your screen
      //playerActions action = new playerAction();
      //System.out.println("The timer starts as soon as you do your first move.");
      boolean first=true;
      //initialize variables needed for timer
      long time1 = 0;
      long time2 = 0;
      long time = 0;
      while(end==false)
      {
        outTable(table2,length);//output the table 
        System.out.println("Which way would you like to move. (w=up, s=down, d=right and a=left)");
        String move=scan.nextLine().toLowerCase();
        if(first==true)
        {
          first=false;//change this to false becuase its run through one time
          //time1 = System.currentTimeMillis();//get the start time
          time1 = System.currentTimeMillis();
        }
        if(move.equals("w")) //If user presses W to move:
        {
          for(int x=0;x<length;x++)
          {
            for(int y=0;y<length;y++)
            {
              if(table2[x][y]==2)
              {
                if(table2[x-1][y]==4)//if they finish the maze 
                {
                  System.out.println("You have finished The maze.");
                  end=true;//to stop looping through the code
                  break;
                }
                else if(!(table2[x-1][y]==1))
                {
                  table2[x][y]=0;//change postion of player
                  table2[x-1][y]=2;//change postion of player
                  break;
                }
                
                System.out.println("--------Sorry that was an invalid move.--------");
              }
            }
          }
        }
        else if(move.equals("a")) //If user presses A to move:
        {	
          for(int x=0;x<length;x++)
          {
            for(int y=0;y<length;y++)
            {
              if(table2[x][y]==2)
              {
                if(table2[x][y-1]==4) //If they move into the red circle, user is done
                {
                  System.out.println("You have finished The maze.");
                  end=true;
                  break;
                }
                if(!(table2[x][y-1]==1)) //If they move into wall
                {
                  table2[x][y]=0;
                  table2[x][y-1]=2;
                  break;
                }
                System.out.println("--------Sorry that was an invalid move.--------");
              }
            }
          }
        }
        boolean exit=false;
        if(move.equals("s")) //If user presses S to move:
        {
          for(int x=0;x<length;x++)
          {
            for(int y=0;y<length;y++)
            {
              if(table2[x][y]==2)
              {
                if(table2[x+1][y]==4) //If they move into the red circle, user is done
                {
                  System.out.println("You have finished The maze.");
                  end=true;
                  exit=true;
                  break;
                }
                if(!(table2[x+1][y]==1)) //If they move into wall
                {
                  table2[x][y]=0;
                  table2[x+1][y]=2;
                  exit=true;
                  break;
                }
                if(exit==true)
                {
                  break;
                }
                System.out.println("--------Sorry that was an invalid move.--------");
              }
              if(exit==true)
              {
                break;
              }
            }
            if(exit==true)
            {
              break;
            }
          }
        }
        else if(move.equals("d")) //If user presses D to move:
        {
          for(int x=0;x<length;x++)
          {
            for(int y=0;y<length;y++)
            {
              if(table2[x][y]==2)
              {
                if(table2[x][y+1]==4) //If they move into the red circle, user is done
                {
                  System.out.println("You have finished The maze.");
                  end=true;
                  break;
                }
                if(!(table2[x][y+1]==1))
                {
                  table2[x][y]=0;
                  table2[x][y+1]=2;
                  break;
                }
                System.out.println("--------Sorry that was an invalid move.--------");
              }
            }
          }
        }
        if(end==true)
        {
          time2 = System.currentTimeMillis();//get time at the end
          time=time2-time1;//minus the times to get how long it takes the player to finish
          time=time/1000;//convert from miliseconds to seconds
          System.out.println("You took " + time + " seconds!");
          endTime = Math.toIntExact(time);//convert long to int
        }
        //when game ends:
        //public void getTime(startTime){
          //endTime = return (current time - start time) (final time)
        //}
      }
    }while(!input.equals("3"));//loop until user quits program
	}//end main
	public static void outTable(int table[][], int length)
	{
		System.out.println("Maze:");
		int first=0;
		for(int x=0;x<length;x++)//checks each postion of the array and outputs an emoji based on the number 
		{
			if(first==1)
			{
				System.out.println("");
			}
			first=1;
			for(int y=0;y<length;y++)
			{
				int temp=table[x][y];
				if(temp==0)
				{
					//temp=0 nothing
					System.out.print("\uD83D\uDFE1");
				}
				else if(temp==1)
				{
					//temp=1 wall
					System.out.print("\u26AB");
				}
				else if(temp==2)
				{
					//temp=2 player 
					System.out.print("\uD83D\uDFE2");
				}
				else if(temp==4)
				{
					//temp=4 end 
					System.out.print("\uD83D\uDD34");
				}
			}
		}
		System.out.println("");
	}//end outTable
	private static int[][] level(String level1)
	{
		//Easy Levels
		final int [][]maze1={
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,0,0,0,0,4},
			{1,0,1,0,1,0,1,1,1,0,1},
			{1,0,1,0,0,0,1,0,0,0,1},
			{1,1,1,0,1,0,1,1,1,1,1},
			{1,0,0,0,1,0,1,0,0,0,1},
			{1,0,1,1,1,1,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,1,0,1},
			{1,0,1,0,1,1,1,0,1,1,1},
			{1,2,1,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1},
		};
		final int [][]maze2={
			{1,1,1,1,4,1,1,1,1,1,1},
			{1,0,0,0,0,1,0,0,0,0,1},
			{1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,0,1,1,1,1},
			{1,0,0,1,0,1,0,0,0,0,1},
			{1,0,1,1,0,1,1,0,1,0,1},
			{1,0,0,0,0,0,1,0,1,0,1},
			{1,0,1,1,1,2,0,0,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1},
		};
		final int [][]maze3={
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,0,1,0,0,0,0,0,2},
			{1,0,0,0,1,0,1,1,1,0,1},
			{1,0,1,0,0,0,1,0,1,0,1},
			{1,0,1,0,1,0,0,0,1,0,1},
			{1,0,1,1,1,0,1,0,1,0,1},
			{1,0,0,0,1,0,1,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1},
			{1,0,1,0,0,0,1,0,1,0,1},
			{4,0,1,1,1,0,1,0,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1},
		};
		final int [][]maze4={
			{1,1,1,1,1,4,1,1,1,1,1},
			{1,0,0,0,1,0,1,0,0,0,1},
			{1,0,1,1,1,0,1,0,1,0,1},
			{1,0,1,0,0,0,1,1,1,0,1},
			{1,0,1,0,1,1,1,0,0,0,1},
			{1,0,1,0,1,0,1,1,1,0,1},
			{1,0,1,0,1,0,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,1,0,1},
			{1,0,1,1,1,1,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,1,0,1},
			{1,1,1,1,1,2,1,1,1,1,1},
		};
		final int [][]maze5={
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,0,0,1,0,4},
			{1,1,0,1,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,1,0,0,0,1},
			{1,1,1,0,1,0,1,1,1,0,1},
			{1,0,1,0,0,2,0,0,1,0,1},
			{1,0,1,1,1,0,1,1,1,0,1},
			{1,0,1,0,0,0,1,0,0,0,1},
			{1,0,1,0,1,1,1,0,1,0,1},
			{1,0,0,0,0,0,1,0,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1},
		};
		//Medium Levels
		final int [][]maze6={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,1,1,0,0,0,1,0,0,0,0,0,1,0,4},
			{1,0,1,0,1,0,0,1,0,1,0,0,0,1,0,1,0,1,0,1},
			{1,1,1,0,1,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,0,1,1,1,0,1,1,1,1,1,1,1},
			{1,0,1,0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1},
			{1,0,1,1,0,1,1,0,1,1,0,1,1,1,0,0,0,1,0,1},
			{1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1},
      {1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1},
      {1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,1},
      {1,0,1,1,1,1,1,1,0,1,0,1,1,0,1,0,1,1,0,1},
      {1,0,0,0,0,0,0,1,0,1,0,0,1,0,1,0,0,0,0,1},
      {1,0,1,0,1,1,0,1,0,1,1,0,1,1,1,0,1,1,1,1},
      {1,0,1,0,0,1,1,1,1,1,0,0,0,0,1,0,0,0,0,1},
      {1,1,1,0,1,1,0,0,0,1,1,1,1,1,1,1,1,1,0,1},
      {1,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1,0,1},
      {1,1,1,0,1,0,1,1,1,1,1,1,0,1,1,1,0,1,0,1},
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
		final int [][]maze7={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,4},
			{1,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,0,1},
			{1,0,1,0,0,0,1,0,0,0,0,0,0,1,0,1,1,1,1,1},
			{1,0,0,0,1,0,1,1,1,1,1,0,1,1,0,0,0,0,0,1},
			{1,0,1,0,0,0,1,0,0,0,1,0,1,0,0,1,0,1,1,1},
			{1,0,1,0,1,1,1,0,0,0,1,1,1,1,1,1,0,0,0,1},
			{1,1,1,0,0,0,0,0,1,0,1,0,1,0,1,0,0,1,0,1},
			{1,0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,1},
      {1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,0,0,0,1},
			{1,0,1,0,0,1,0,0,1,0,1,0,1,0,1,1,0,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,0,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
			{1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1},
			{1,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,1,1},
			{1,0,0,1,0,1,1,1,1,0,1,0,1,1,1,1,0,0,0,1},
			{1,1,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,1},
			{1,1,0,0,0,1,1,0,0,0,1,0,1,1,1,1,1,1,0,1},
			{2,0,0,1,1,1,0,0,1,1,1,0,1,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
		final int [][]maze8={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,0,0,0,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
			{1,1,1,1,1,0,1,0,0,1,0,0,0,0,0,0,1,0,0,1},
			{1,0,0,0,1,0,1,1,1,1,0,1,1,0,1,0,1,0,1,1},
			{1,0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,0,0,1},
			{1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,0,1,1,0,1},
			{1,0,1,0,0,0,1,0,1,0,1,0,0,0,1,1,1,0,0,1},
			{1,0,1,1,1,1,1,0,1,0,0,0,1,0,0,0,0,0,1,1},
      {1,0,0,0,0,0,0,0,1,4,0,1,1,1,1,1,1,1,1,1},
			{1,0,1,0,1,0,1,1,1,1,1,1,0,0,0,1,0,0,0,1},
			{1,0,1,0,1,0,1,0,0,0,0,1,0,0,1,1,0,1,0,1},
			{1,0,1,0,0,0,1,1,1,1,0,1,0,1,1,1,1,1,0,1},
			{1,1,1,0,1,1,0,0,0,1,0,1,0,0,1,0,0,0,0,1},
			{1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,1,1,1},
			{1,1,0,1,1,1,0,1,1,1,1,1,1,1,0,0,0,1,0,1},
			{1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1},
			{1,1,0,0,0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1},
			{1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1},
		};
		final int [][]maze9={
			{1,1,1,1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1},
			{1,0,1,0,0,0,1,1,1,1,1,1,1,1,0,0,0,1,0,1},
			{1,0,1,1,1,1,1,0,0,0,0,1,0,1,1,1,1,1,0,1},
			{1,0,1,0,0,0,1,0,1,1,0,1,0,0,0,0,0,1,0,1},
			{1,0,1,1,1,0,1,0,1,0,0,1,0,1,0,1,1,1,0,1},
			{1,0,0,0,1,0,1,1,1,0,1,1,1,1,0,1,0,0,0,1},
			{1,0,1,0,1,0,0,0,0,2,0,0,0,0,0,1,0,1,0,1},
			{1,0,1,0,1,1,1,1,1,0,1,1,0,1,0,1,1,1,0,1},
      {1,0,1,0,0,0,1,0,1,0,1,0,0,1,0,0,1,0,0,1},
			{1,0,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1,1},
			{1,0,1,1,1,0,1,0,0,0,1,0,0,0,0,0,1,0,1,1},
			{1,0,0,0,0,0,1,0,1,0,1,1,1,1,1,0,1,0,1,1},
			{1,1,0,1,1,1,1,1,1,0,1,0,0,0,1,0,0,0,0,1},
			{1,1,0,1,0,0,0,0,0,0,1,0,1,0,1,0,1,0,1,1},
			{1,1,0,1,1,1,1,1,1,1,1,0,1,0,0,0,1,0,0,1},
			{1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,1},
			{1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
		final int [][]maze10={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,1,0,0,0,0,0,0,1,1,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,1,0,1,0,0,1,0,1,1,1,1},
			{1,0,1,0,1,0,1,0,1,1,0,1,1,1,1,0,0,0,0,1},
			{1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,1},
			{1,0,1,0,1,0,1,0,1,1,0,1,1,1,1,0,1,0,0,1},
			{1,0,1,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,1,1},
			{1,0,1,0,0,0,1,0,1,1,1,1,1,1,1,0,1,1,1,1},
			{1,0,1,1,1,0,1,0,0,0,0,0,1,0,0,2,0,0,0,1},
      {1,0,0,0,1,0,1,1,1,1,1,1,1,0,1,0,1,1,0,1},
			{1,1,1,0,1,0,4,1,0,0,0,0,0,0,1,0,0,1,0,1},
			{1,0,0,0,1,1,1,1,0,1,1,1,1,0,1,0,1,1,0,1},
			{1,0,1,1,1,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1},
			{1,0,0,0,0,0,0,1,0,1,0,0,1,0,1,1,0,1,0,1},
			{1,0,1,1,1,1,0,1,0,1,1,0,1,0,0,0,0,1,0,1},
			{1,0,1,0,0,1,0,0,0,0,1,0,1,1,1,0,1,1,0,1},
			{1,0,1,0,1,1,1,1,1,1,1,0,0,0,1,0,0,1,0,1},
			{1,0,1,0,1,0,1,0,0,0,1,0,1,0,1,1,1,1,0,1},
			{1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
		//Hard Mazes
		final int [][]maze11={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,1,0,1,0,4},
			{1,0,1,0,1,0,1,1,1,0,1,1,0,1,0,0,0,1,1,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,1,0,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,1,0,1},
			{1,0,1,0,1,0,1,0,1,1,1,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,0,0,0,0,0,0,1,0,1,0,1,1,1,1,1,0,0,0,1,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,1,1,1,0,1,1,1,0,0,0,1,1,1,1,1,0,1,0,1},
			{1,1,1,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1},
			{1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,1,1,0,1,0,0,1,0,1,1},
			{1,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,1},
			{1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,1},
			{1,0,0,1,1,0,1,0,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
			{1,1,0,0,0,0,1,0,0,0,1,0,1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,1,0,1},
			{1,1,0,1,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,0,0,1,0,0,0,1,0,0,1},
			{1,0,0,0,0,0,0,0,1,1,1,0,0,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,1},
			{1,1,1,1,1,1,1,0,0,0,1,1,1,0,1,0,1,0,1,0,0,0,0,0,1,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,1,0,1,1,1,0,1,1,1,1,1,1,1,1},
			{1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,0,1,0,0,0,1,0,0,0,0,0,0,0,1,1},
			{1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,1,1,1,0,1,1,1,1,1,0,0,0,1,1},
			{1,0,1,1,1,1,0,1,0,0,1,0,1,0,1,1,1,0,0,0,1,0,0,0,1,0,1,0,1,1},
			{1,0,1,0,0,1,0,1,0,0,0,0,1,0,1,0,0,0,1,1,1,0,1,0,0,0,1,0,0,1},
			{1,0,1,0,1,1,0,1,0,1,1,1,1,0,1,1,1,1,1,1,0,0,1,0,1,1,1,1,0,1},
			{1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,1,0,0,1,0,1,0,1},
			{1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1,1,1},
			{1,2,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1},
			{1,0,1,1,1,0,1,0,1,1,1,1,0,1,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,0,1,1,1,0,1,0,1,1,1,0,1,0,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
		final int [][]maze12={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1},
			{1,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,0,0,1,0,1,0,1},
			{1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,1,1,0,1,0,1,0,0,1,0,1,0,0,0,1},
			{1,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,0,1,0,1,0,1},
			{1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,1,1,0,1,0,1,0,0,1,0,1,0,1},
			{1,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,1,1,0,1,0,1},
			{1,0,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,0,0,0,1,0,0,0,0,1,0,1},
			{1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,0,1,0,1,1,1,0,1,0,1,0,0,0,0,0,1,0,0,1,0,1},
			{1,0,0,0,1,0,0,0,0,0,1,0,0,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,0,1},
			{1,0,1,0,0,0,1,0,1,1,1,1,0,0,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,0,1,1,0,1,0,0,0,0,1,1,0,0,0,1,1,1,1,1,1,0,1,0,1,0,1},
			{1,0,0,1,1,1,1,0,1,1,1,1,0,0,1,1,1,0,0,1,0,0,0,1,0,1,0,1,0,1},
			{1,1,0,0,0,0,0,0,0,1,0,1,1,0,1,0,1,1,0,1,0,1,1,1,1,1,1,1,0,1},
			{1,0,0,1,0,1,0,1,1,1,0,1,0,0,1,0,1,0,0,1,0,0,0,0,1,0,0,0,0,1},
			{1,0,1,1,0,1,1,1,0,0,0,1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1},
			{1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,1},
			{1,0,1,0,1,1,0,1,1,1,0,1,0,1,0,0,1,0,1,0,1,0,1,1,1,1,1,0,0,1},
			{1,0,1,0,1,0,0,1,0,0,0,1,0,1,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,1},
			{1,1,1,0,1,0,1,1,0,1,0,1,0,0,0,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1},
			{1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,1},
			{1,0,0,1,1,1,0,1,1,1,0,1,0,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,0,1},
			{1,0,1,1,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1},
			{1,0,1,0,0,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,0,0,1},
			{1,0,1,0,1,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1},
			{1,0,1,0,1,0,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,4,1},
		};
		
		Random r = new Random();
		int[][] table=null;// the return maze
		int diff=r.nextInt(5);//generates random number for random maze
		if(level1.equals("1"))
		{
			if(diff==1)
			{
				table=maze1;//gets maze for player
				return table;
			}
			else if(diff==2)
			{
				table=maze2;
				return table;
			}
			else if(diff==3)
			{
				table=maze3;
				return table;
			}
			else if(diff==4)
			{
				table=maze4;
				return table;
			}
			else
			{
				table=maze5;
				return table;
			}
		}
		else if(level1.equals("2"))
		{
			if(diff==1)
			{
				table=maze6;
				return table;
			}
			else if(diff==2)
			{
				table=maze7;
				return table;
			}
			else if(diff==3)
			{
				table=maze8;
				return table;
			}
			else if(diff==4)
			{
				table=maze9;
				return table;
			}
			else
			{
				table=maze10;
				return table;
			}
		}
		else
		{
			if(diff==1)
			{
				table=maze11;
				return table;
			}
			else
			{
				table=maze12;
				return table;
			}
		}
	}//end mazes
  public static String fileToASCIIimage(String file)throws IOException
  {//outputs the letters in big red font at the start of the game
      BufferedReader br=new BufferedReader(new FileReader(
      file));
      String data="";
      int line=0;
      do{
        line=br.read();
        if(line!=-1)data+=(char)line;
      }while(line!=-1);
      br.close();
      return data;
  }

}//end class main