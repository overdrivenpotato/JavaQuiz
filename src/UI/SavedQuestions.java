package UI;

import underthehood.QHandler;
import underthehood.Question;
import underthehood.QuizPrinter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by marko on 17/12/13.
 */
public class SavedQuestions {
    private JPanel panel1;
    private JButton removeButton;
    private JList toPrintList;
    private JButton saveQuizToFileButton;
    private JButton saveAnswerKeyButton;
    private DefaultListModel listModel;
    private ArrayList<Integer> indices;

    public SavedQuestions() {
        indices = new ArrayList<Integer>();
        toPrintList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                saveQuizToFileButton.setEnabled(listModel.getSize() > 0);
                saveAnswerKeyButton.setEnabled(saveQuizToFileButton.isEnabled());
                removeButton.setEnabled(!toPrintList.isSelectionEmpty());
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = toPrintList.getSelectedIndex();
                indices.remove(selected);
                listModel.remove(selected);

                toPrintList.setListData(listModel.toArray());
                toPrintList.setSelectedIndex(selected);
            }
        });

        saveQuizToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser saveFile = new JFileChooser();
                saveFile.setSelectedFile(new File("QuizGen.txt"));
                if(saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    System.out.println("Saving to " + saveFile.getSelectedFile());
                    ArrayList<Question> printQuestions = new ArrayList<Question>();

                    for(int index : indices)
                    {
                        printQuestions.add(QHandler.questions().get(index));
                    }

                    new QuizPrinter().printQuiz(printQuestions, saveFile.getSelectedFile());
                }
            }
        });
        saveAnswerKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser saveFile = new JFileChooser();
                saveFile.setSelectedFile(new File("QuizGenAnswers.txt"));
                if(saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    System.out.println("Saving to " + saveFile.getSelectedFile());
                    ArrayList<Question> printQuestions = new ArrayList<Question>();

                    for(int index : indices)
                    {
                        printQuestions.add(QHandler.questions().get(index));
                    }

                    new QuizPrinter().printAnswerKey(printQuestions, saveFile.getSelectedFile());
                }
            }
        });
    }

    public JPanel getContents()
    {
        return panel1;
    }

    public void addQuestion(Question q) {
        indices.add(new Integer(QHandler.questions().indexOf(q)));
        listModel.addElement(HtmlFmt.fixIndent(q.getQuestion()));
        toPrintList.setListData(listModel.toArray());
    }

    private void createUIComponents() {
        listModel = new DefaultListModel();
        toPrintList = new JList(listModel);
    }
}
