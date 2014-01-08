package underthehood;

import UI.MainMenu;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Marko Mijalkovic
 * Date: 11/22/13
 * Time: 10:34 AM
 */
public class JavaQuizUpdatedDRV {

    public static void main(String[] args) throws Exception
    {
        QHandler.getHandler().setQuestionArrayList(getQuestions());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu();
            }
        });
    }

    public static ArrayList<Question> getQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        try
        {
            Scanner in = new Scanner(new FileReader("quiz2.txt")).useDelimiter(",");
            while(in.hasNext())
            {
                try //Read in questions, and catch any incomplete lines
                {
                    String quest = in.next().trim();
                    ArrayList<String> possibleAnswers = new ArrayList<String>();

                    String temp = "";
                    while(true)
                    {
                        possibleAnswers.add(in.next().trim());
                        if(possibleAnswers.get(possibleAnswers.size() - 1).length() == 1)
                        {
                            try
                            {
                                Integer.parseInt(possibleAnswers.get(possibleAnswers.size() - 1));
                            } catch(Exception e)
                            {
                                try
                                {
                                    temp = in.next().trim();
                                    Integer.parseInt(temp);
                                    break;
                                }
                                catch(Exception e2)
                                {
                                    possibleAnswers.add(temp);
                                }
                            }
                        }
                    }
                    char correct = possibleAnswers.get(possibleAnswers.size() - 1).charAt(0);
                    possibleAnswers.remove(possibleAnswers.get(possibleAnswers.size() - 1));
                    int complexity = Integer.parseInt(temp.trim());
                    questions.add(new Question(quest, possibleAnswers, correct, complexity));
                }
                catch(Exception e){
                    System.err.println("An error has occurred while loading question " + questions.size() + ".");
                    System.err.println("Stack trace of the error: ");
                    e.printStackTrace();
                    System.exit(2);
                }
            }
            in.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Could not load file.");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Loaded " + questions.size() + " questions.");
        return questions;
    }
}
