package underthehood;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: 309009199
 * Date: 11/22/13
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class Question {
    private String quest;
    private ArrayList<String> posAnswers;
    private char correct;
    private int complexity;

    public Question(String quest, ArrayList<String> posAnswers, char correct, int complexity)
    {
        this.quest = quest;
        this.posAnswers = posAnswers;
        this.correct = correct;
        this.complexity = complexity;
    }

    public char getCorrectAnswer()
    {
        return Character.toUpperCase(correct);
    }

    public String getAnswerString()
    {
        return posAnswers.get(getAnswerIndex());
    }

    public int getAnswerIndex()
    {
        return getCorrectAnswer() - 'A';
    }

    public String getQuestion()
    {
        return quest;
    }

    public ArrayList<String> getPosAnswers()
    {
        return posAnswers;
    }

    public String toString()
    {
        String s;
        s = quest + ",\n";
        for(int i = 0; i < posAnswers.size(); i++)
        {
            s += posAnswers.get(i).trim() + ",\n";
        }
        s += correct + ",\n";
        s += complexity;
        return s;
    }

    public int getComplexity() {
        return complexity;
    }
}
