import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;


public class MainGUI extends Application {



   //boolean to determine if user is inputting name
    boolean isName = true;

    //user name
    String name;

    //gridpane object
    GridPane gridPane = new GridPane();

    //display for the word and text
    Label display = new Label("Enter your name to begin");

    //the word displayed
    String word;

    //user score
    int score = 0;


    //list of all the words
    List<String> wordList = new ArrayList<String>();

    //textfield for the user to input text
    TextField input = new TextField();



    public void start(Stage myStage) {


        //adds every word from textfile into the an arraylist of words
        try {
            String path = "/Users/s210230/IdeaProjects/TypingGameGUI/src/words";
            FileReader reader = new FileReader(path);

            BufferedReader buffReader = new BufferedReader(reader);
            String line = buffReader.readLine();

            //adds each line from the textfile to the arraylist
            while (line != null) {
                wordList.add(line);
                line = buffReader.readLine();
            }
            buffReader.close();
        } catch (Exception e) {
            System.out.println("naughty");
        }

        //new score display label
        Label scoreDisplay = new Label("Score: 0");

        //new stopwatch object
        Stopwatch stopwatch = new Stopwatch();


        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent event) {


                //if the user is inputting their name
                if (isName)

                {
                    //sets name variable as the text inside the textfield
                    name = input.getText();
                    isName = false;

                    //sets display as a random word and removes the word from list to not be repeated again
                    word = wordList.get((int) (Math.random() * wordList.size()));
                    wordList.remove(word);
                    display.setText(word);
                    input.setText("");

                    //starts the stopwatch
                    stopwatch.start();


                }

                //if the time exceeds 2 seconds
                else if (stopwatch.elapsedTime() > 2) {

                    display.setText("You ran out of time!");

                    //user cannot edit the textfield
                    input.setEditable(false);
                    try {

                        //writes user score and name to leaderboard textfile
                        String path = "/Users/s210230/IdeaProjects/TypingGameGUI/src/Leaderboard";
                        FileWriter write = new FileWriter(path, true);
                        PrintWriter printLine = new PrintWriter(write);
                        printLine.printf("%s" + "%n", score + "  " + name);
                        printLine.close();


                      //  gridPane.add(restart,4,4);

                    } catch (Exception e) {
                        System.out.println("exception");
                    }

                }

                //if user inputs the correct word
                else if (input.getText().equals(word))

                {
                    //increases the score
                    score++;

                    //adds a new word from word list to the display. word is removed from list to not be repeated again
                    word = wordList.get((int) (Math.random() * wordList.size()));
                    wordList.remove(word);
                    display.setText(word);
                    scoreDisplay.setText("Score: " + Integer.toString(score));
                    input.setText("");
                    stopwatch.start();


                }

                //if user gets it wrong
                else if(!input.getText().equals(word))

                {
                    try {
                        display.setText("Wrong!");

                        //cannot access textfield
                        input.setEditable(false);

                        //adds user score and name to leaderboard
                        String path = "/Users/s210230/IdeaProjects/TypingGameGUI/src/Leaderboard";
                        FileWriter write = new FileWriter(path, true);
                        PrintWriter printLine = new PrintWriter(write);
                        printLine.printf("%s" + "%n", score + "  " + name);
                        printLine.close();


                        //gridPane.add(restart,4,4);

                    } catch (Exception e) {
                        System.out.println("exception");
                    }


                }


            }



        });


        //print the leader board button
        Button printLeaderBoard = new Button("Print Leader board");



        List<String> leaderBoardList = new ArrayList<String>();
        printLeaderBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent event) {

                try {
                    //reads the leader board
                    String path = "/Users/s210230/IdeaProjects/TypingGameGUI/src/Leaderboard";
                    FileReader reader = new FileReader(path);

                    BufferedReader buffReader = new BufferedReader(reader);
                    String line = buffReader.readLine();

                    //if the textfile is clear, print that the leaderboard is clear
                    if (line == null) {
                        System.out.println("Leaderboard is clear");
                    }
                    int i = 0;

                    //removes each object from leaderboardlist
                    while(leaderBoardList.size()>0){
                        leaderBoardList.remove(i);
                    }

                    //adds each line from textfile to leaderboardlist
                    while (line != null) {
                        leaderBoardList.add(line);
                        line = buffReader.readLine();

                    }
                    buffReader.close();

                    //sorts leaderboardlist
                    Collections.sort(leaderBoardList, Collections.reverseOrder());

                    //if the leaderboard is not empty
                    if(leaderBoardList.size()!=0 ) {

                        System.out.println("Leaderboard: ");

                        //prints every element in the leaderboardlist
                        for (String s : leaderBoardList) {
                            System.out.println(s);
                        }
                    }


                } catch (Exception e) {
                    System.out.println("that is very naughty indeed");
                }
            }

        });


        //clear leaderboard button
        Button clearLeaderBoard = new Button("Clear Leader board");
        clearLeaderBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent event) {

                try {
                    //opens writer object to write to textfile
                    String path = "/Users/s210230/IdeaProjects/TypingGameGUI/src/Leaderboard";
                    FileWriter write = new FileWriter(path, false);
                    PrintWriter clearAll = new PrintWriter(write);

                    //clears the textfile
                    clearAll.print("");
                    clearAll.close();


                } catch (Exception e) {
                    System.out.println("2naughty4me");
                }
            }

        });

//sets margin between box and GUI objects
        gridPane.setPadding(new Insets(10, 10, 10, 10));

//max height and width for the gridpane
        gridPane.maxHeight(5);
        gridPane.maxWidth(5);

// size of each gridpane
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        //adds each object at a different grid index
        gridPane.add(display, 2, 0);
        gridPane.add(input, 2, 3);
        gridPane.add(scoreDisplay, 4, 0);
        gridPane.add(printLeaderBoard, 2, 4);
        gridPane.add(clearLeaderBoard, 4, 4);

//sets the scene and runs the program
        Scene scene = new Scene(gridPane, 700, 450);

        myStage.setTitle("Typing Test");
        myStage.setScene(scene);
        myStage.show();

    }


}
