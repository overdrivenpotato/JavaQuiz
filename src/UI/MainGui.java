package UI;

import underthehood.Question;

import javax.swing.*;

/**
 * Created by marko on 17/12/13.
 */
public class MainGui {
    private JPanel panel1;
    private JPanel right;
    private JPanel left;
    private JSplitPane splitter;
    private SavedQuestions printList;

    public MainGui()
    {
        this.printList = new SavedQuestions();
        splitter.setRightComponent(printList.getContents());
        splitter.setResizeWeight(0.5);
    }

    public void showScreen(JPanel panel, JFrame frame)
    {
        splitter.setLeftComponent(panel);
        frame.setContentPane(splitter);
        this.left = panel;
    }

    private static MainGui handle;

    public static MainGui get()
    {
        return handle;
    }

    static {
        handle = new MainGui();
    }

    public void showFullScreen(JPanel panel, JFrame frame) {
        splitter.setLeftComponent(panel);
        frame.setContentPane(panel);
        this.left = panel;
    }

    public void addQuestionSaved(Question q)
    {
        printList.addQuestion(q);
    }
}
