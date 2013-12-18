package UI;

import underthehood.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: 309009199
 * Date: 12/10/13
 * Time: 9:49 AM
 */
public class QuestionDisplay extends GuiScreen{
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButtons[];
    private JButton acceptButton;
    private JLabel titleLabel;
    private ButtonGroup buttonGroup;
    private JFrame frame;
    private Question currQuestion;
    private JPanel mainPanel;
    private JButton backButton;
    private JLabel correctLabel;
    private JButton nextQuestionButton;
    private GuiScreen parentUI;

    private int sourceQuestion;

    public QuestionDisplay() {

        this.panel = mainPanel;
        radioButtons = new JRadioButton[5];
        radioButtons[0] = radioButton1;
        radioButtons[1] = radioButton2;
        radioButtons[2] = radioButton3;
        radioButtons[3] = radioButton4;
        radioButtons[4] = radioButton5;
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentUI.showScreen();
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptButton.setEnabled(false);
                nextQuestionButton.setEnabled(true);
                for(JRadioButton button : radioButtons)
                {
                    button.setEnabled(false);
                }
                JRadioButton correct = radioButtons[currQuestion.getAnswerIndex()];
                correct.setFont(new Font(correct.getFont().getName(), Font.BOLD,correct.getFont().getSize()));

                if(currQuestion.getAnswerIndex() == getSelectedIndex())
                {
                    correctLabel.setForeground(Color.green);
                    correctLabel.setText("Correct!");
                }
                else
                {
                    correctLabel.setForeground(Color.red);
                    correctLabel.setText("Incorrect!");
                }
                correctLabel.setVisible(true);
            }
        });
    }

    private Question getNextQuestion() {
//        if
        return null;
    }

    public void showQuestion(Question question, final GuiScreen parentUI, int sourceQuestion)
    {
        this.sourceQuestion = sourceQuestion;
        clearData();
        this.parentUI = parentUI;
        this.currQuestion = question;

        boolean pre = currQuestion.getQuestion().contains("\n");
        String htmlSafeText = HtmlFmt.fixForHTML(currQuestion.getQuestion());
        titleLabel.setText(HtmlFmt.addBody(pre ? HtmlFmt.addPreFmtTag(htmlSafeText) : htmlSafeText));

        if(question.getPosAnswers().size() < 5)
        {
            for(int i = 4; i >= question.getPosAnswers().size(); i--)
            {
                radioButtons[i].setVisible(false);
            }
        }

        int i = 0;
        for(JRadioButton button : radioButtons)
        {
            if(question.getPosAnswers().size() <= i) break;
            String text = question.getPosAnswers().get(i++);
            boolean multiline = text.contains("\n");
            button.setText(multiline ? HtmlFmt.fixIndent(text) : text);
        }

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void clearData() {
        acceptButton.setEnabled(true);
        correctLabel.setVisible(false);
        nextQuestionButton.setEnabled(false);

        for(JRadioButton button : radioButtons)
        {
            button.setFont(new Font(button.getFont().getName(), Font.PLAIN,button.getFont().getSize()));
            button.setEnabled(true);
        }
    }

    public int getSelectedIndex()
    {
        String selected = getSelectedButton(buttonGroup).getText();
        for(int i = 0; i < currQuestion.getPosAnswers().size(); i++) {
            if(selected.equals(currQuestion.getPosAnswers().get(i))) {
                return i;
            }
        }
        return -1;
    }

    public  JRadioButton getSelectedButton(ButtonGroup group) {
        Enumeration<AbstractButton> e = group.getElements();
        while (e.hasMoreElements()) {
            AbstractButton b =  e.nextElement();
            if (b.isSelected()) return (JRadioButton) b;
        }
        return null;
    }

    public QuestionDisplay setFrame(JFrame frame)
    {
        this.frame = frame;
        return this;
    }

    private void createUIComponents() {
        correctLabel = new JLabel();

        acceptButton = new JButton();
        acceptButton.setMnemonic('A');

        nextQuestionButton = new JButton();
    }
}
