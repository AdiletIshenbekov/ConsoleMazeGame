import java.io.*;
class leaderboard{
  String name;
  int time;
  leaderboard(String name, int score){
    this.name = name;
    time = Integer.valueOf(score);//initialize variable for final time

  }
  //method for viewing leaderboards
  String[][] displayBoard(String type)throws IOException{
    BufferedReader br;//initialize bufferedreader
    if(type.equals("easy")){//if user wants to view easy leaderboard create bufferedreader for leaderboard1.csv
      br = new BufferedReader(new FileReader("leaderboard1.csv"));
    }
    else if(type.equals("medium")){//if user wants to view medium leaderboard create bufferedreader for leaderboard2.csv
      br = new BufferedReader(new FileReader("leaderboard2.csv"));
    }
    else{//if user wants to view hard leaderboard create bufferedreader for leaderboard3.csv
      br = new BufferedReader(new FileReader("leaderboard3.csv"));
    }
    int lineCount=0;//create variable to count amount of lines in file
    String line = br.readLine();//read file line into variable
    //while there are lines in file, increment value of lineCount
    while(line!=null){
      lineCount++;
      line = br.readLine();
    }
    String[][] board = new String[lineCount][3];//create new 2d array for storing file contents
    br.close();//close and then reopen file
    if(type.equals("easy")){
      br = new BufferedReader(new FileReader("leaderboard1.csv"));
    }
    else if(type.equals("medium")){
      br = new BufferedReader(new FileReader("leaderboard2.csv"));
    }
    else{
      br = new BufferedReader(new FileReader("leaderboard3.csv"));
    }
    //read file into board array
    line = br.readLine();
    String[] convert;
    for(int i=0;i<lineCount;i++){
      convert = line.split(",");
      for(int j=0;j<board[0].length;j++){
        board[i][j] = convert[j];
      }
      line = br.readLine();
    }
    return board;//return board array to main
  }
  //method for finding the user's ranking in leaderboards
  void scorePosition(String[][] board, String difficulty)throws Exception{
    int[] score = new int[board.length];//initialize new array with a length equal to board's length
    int position = 0;//declare varaible to keep track of position
    //put times from the leaderboard into score array
    for(int i=1;i<board.length;i++){
      for(int j=0;j<3;j++){
        if(j==2)
        score[i] = Integer.valueOf(board[i][j]);
      }
    }
    //check to see if user's time is lower than times on the leaderboard
    for(int i=1;i<score.length;i++){
      if(time<score[i]){//if user's time is lower, set position to that ranking and break from for loop
        position = i;
        break;
      }
    }
    appendToFile(board, difficulty, position);//call appendToFileMethod
  }
  //writes user's score to file
  private void appendToFile(String[][] board, String difficulty, int position)throws Exception{
    //initialize and create buffered writer for corresponding file based on player's difficulty
    BufferedWriter bw;
    boolean condition = false;
    if(difficulty.equals("1")){
      bw = new BufferedWriter(new FileWriter("leaderboard1.csv"));
    }
    else if(difficulty.equals("2")){
      bw = new BufferedWriter(new FileWriter("leaderboard2.csv"));
    }
    else{
      bw = new BufferedWriter(new FileWriter("leaderboard3.csv"));
    }
    bw.write("Rank, Name, Time");//pre-add the category labels to file
    bw.newLine();//create new line in file
    for(int i=1;i<board.length;i++){//when this loop get's to the user's correct position based off their time, write the player's stats instead
      if(i==position){
        bw.write(position + ",");
        bw.write(this.name + ",");
        bw.write(String.valueOf(time));
        bw.newLine();
        condition = true;
      }
      for(int j=0;j<3;j++){
        if(j==0){
          if(condition == false){//if the user's stats hasn't been written to the file yet, print the leaderboard to the file as normal
            bw.write(board[i][j] + ",");
          }
          else{//if the user's stats have been written to the file already, increase the ranking number of all previous players by 1
            System.out.println(board[i][j]);
            int temp = Integer.valueOf(board[i][j]) + 1;
            bw.write((String.valueOf(temp)) + ",");
          }
        }
        else if(j==1){//if name is written to the leaderboard, add a comma to seperate the values for future use
          bw.write(board[i][j] + ",");
        }
        else{//if time is written to the leaderboard, no comma is needed
          bw.write(board[i][j]);
        }
      }
      bw.newLine();//create new line in file
    }
    bw.close();//close bufferedwriter
  }
}