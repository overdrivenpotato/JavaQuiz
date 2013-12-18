package underthehood;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by marko on 17/12/13.
 */
public class QuizPrinter {
    public QuizPrinter()
    {

    }

    public void printQuiz(ArrayList<Question> questions, File file)
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < questions.size(); i++)
            {
                Question q = questions.get(i);
                writer.write((i + 1) + ". " + q.getQuestion());
                for(int j = 0; j < q.getPosAnswers().size(); j++)
                {
                    writer.write("\n\n" + (char)(j + 'A') + ") " + q.getPosAnswers().get(j));
                }
                writer.write("\n\n\n\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Could not open file" + file.getName() + ". ");
        }
    }

    public void printAnswerKey(ArrayList<Question> questions, File file)
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < questions.size(); i++)
            {
                Question q = questions.get(i);
                writer.write((i + 1) + ". " + q.getCorrectAnswer());
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Could not open file" + file.getName() + ". ");
        }
    }
}
