/**
 * Created with IntelliJ IDEA.
 * User: Marko
 * Date: 12/10/13
 * Time: 9:29 AM
 */

package UI;

import underthehood.QHandler;
import underthehood.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class MainMenu extends GuiScreen{
    private JButton searchBrowseButton;
    private JButton randomQuestionButton;
    private JButton exitButton;
    private JPanel mainPanel;
    private QuestionDisplay questionPanel;

    public MainMenu()
    {
        this.panel = mainPanel;
        questionPanel = new QuestionDisplay();
        frame = new JFrame("Marko's Java Quiz!");
        frame.setMinimumSize(new Dimension(1200, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        this.fullScreen = true;
        showScreen();
        questionPanel.setFrame(frame);

        final GuiScreen parentScreen = this;
        final ArrayList<Question> questions = QHandler.questions();
        randomQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionPanel.showQuestion(questions.get(printInt(new Random().nextInt(questions.size()))), parentScreen, 0);
            }
        });
        randomQuestionButton.setVisible(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        searchBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchForm(parentScreen).setFrame(frame).showScreen();
            }
        });
    }

    public int printInt(int p)
    {
        System.out.println(p);
        return p;
    }

    public static void setupUI()
    {
        try {
//            UIManager.setLookAndFeel(
//                    UIManager.getSystemLookAndFeelClassName());

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    static
    {
        setupUI();
    }
}
