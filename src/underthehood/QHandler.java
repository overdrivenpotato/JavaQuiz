package underthehood;

import java.util.ArrayList;

/**
 * Created by Marko on 12/15/13.
 */
public class QHandler {
    private static QHandler handler;
    private ArrayList<Question> questionArrayList;

    public QHandler()
    {
        questionArrayList = new ArrayList<Question>();
    }

    public void setQuestionArrayList(ArrayList<Question> questionArrayList)
    {
        this.questionArrayList = questionArrayList;
    }

    public static QHandler getHandler()
    {
        return handler;
    }

    static
    {
        handler = new QHandler();
    }

    public ArrayList<Question> getQuestions() {
        return questionArrayList;
    }

    public static ArrayList<Question> questions()
    {
        return getHandler().getQuestions();
    }
}
