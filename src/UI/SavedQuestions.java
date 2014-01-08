package UI;

import underthehood.QHandler;
import underthehood.Question;
import underthehood.QuizPrinter;
import underthehood.Util;

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
    private JList<Object> toPrintList;
    private JButton saveQuizToFileButton;
    private JButton saveAnswerKeyButton;
    private JButton sortByComplexityButton;
    private DefaultListModel<Object> listModel;
    private ArrayList<Integer> indices;

    public SavedQuestions() {
        indices = new ArrayList<Integer>();
        toPrintList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
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
                saveQuizToFileButton.setEnabled(listModel.getSize() > 0);
                saveAnswerKeyButton.setEnabled(saveQuizToFileButton.isEnabled());
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
        sortByComplexityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortComplexity();
            }
        });
    }

    public void sortComplexity()
    {
        ArrayList<Question> temp = new ArrayList<Question>();

        for(Integer i : indices)
            temp.add(QHandler.questions().get(i.intValue()));

        for (int i = 0; i < temp.size(); i++)
        {
            Question valueToInsert = temp.get(i);
            int holePos = i;
            while(holePos > 0 && valueToInsert.getComplexity() < temp.get(holePos - 1).getComplexity())
            {
                temp.set(holePos, temp.get(holePos - 1));
                holePos -= 1;
            }
            temp.set(holePos, valueToInsert);
        }

        indices.clear();
        listModel.clear();
        for(Question q : temp)
        {
            indices.add(Util.getQuestionIndex(q.getQuestion()));
            listModel.addElement(HtmlFmt.fixIndent(q.getQuestion()));
        }
        toPrintList.setListData(listModel.toArray());
    }

    public JPanel getContents()
    {
        return panel1;
    }

    public void addQuestion(Question q) {
        indices.add(new Integer(QHandler.questions().indexOf(q)));
        listModel.addElement(HtmlFmt.fixIndent(q.getQuestion()));
        toPrintList.setListData(listModel.toArray());
        saveQuizToFileButton.setEnabled(listModel.getSize() > 0);
        saveAnswerKeyButton.setEnabled(saveQuizToFileButton.isEnabled());
    }

    private void createUIComponents() {
        listModel = new DefaultListModel<Object>();
        toPrintList = new JList<Object>(listModel);
    }
}
