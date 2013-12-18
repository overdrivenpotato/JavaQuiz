package UI;

import underthehood.QHandler;
import underthehood.Question;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTML;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Marko
 * Date: 12/14/13
 * Time: 2:17 PM
 */
public class SearchForm extends GuiScreen{
    private JList searchList;
    private JTextArea searchTextArea;
    private JButton searchButton;
    private JButton backButton;
    private JButton tryThisQuestionButton;
    private JButton startQuizWithThisButton;
    private JList browseList;
    private JButton tryThisQuestionButton2;
    private JButton backButton1;
    private JTabbedPane mainPanelUI;
    private JLabel searchingText;
    private JCheckBox searchAnswerChoicesCheckBox;
    private JPanel panelHolder;
    private DefaultListModel searchListModel;
    private DefaultListModel browseListModel;

    private GuiScreen parentScreen;

    public SearchForm(final GuiScreen parentScreen) {
        this.panel = panelHolder;
        this.parentScreen = parentScreen;

        searchingText.setVisible(false);

        browseList.setLayoutOrientation(JList.VERTICAL);
        browseList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(browseList.isSelectionEmpty())
                {
                    tryThisQuestionButton2.setEnabled(false);
                }
                else
                {
                    tryThisQuestionButton2.setEnabled(true);
                }
            }
        });
        populateBrowseList();

        searchList.setLayoutOrientation(JList.VERTICAL);
        searchList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (searchList.isSelectionEmpty()) {
                    tryThisQuestionButton.setEnabled(false);
                    startQuizWithThisButton.setEnabled(false);
                } else {
                    tryThisQuestionButton.setEnabled(true);
                    startQuizWithThisButton.setEnabled(true);
                }
            }
        });

        searchTextArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                if (searchTextArea.getText().trim().length() == 0) {
                    searchButton.setEnabled(false);
                } else {
                    searchButton.setEnabled(true);
                }

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchingText.setVisible(true);
                searchingText.updateUI();
                searchListModel.clear();
                searchForTerms(searchTextArea.getText());
            }
        });

        ActionListener backButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentScreen.showScreen();
            }
        };
        backButton.addActionListener(backButtonListener);
        backButton1.addActionListener(backButtonListener);

        final GuiScreen currUI = this;
        tryThisQuestionButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuestionDisplay().setFrame(frame).showQuestion(QHandler.getHandler().getQuestions().get(browseList.getSelectedIndex()), currUI, 0);
            }
        });
        tryThisQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuestionDisplay().setFrame(frame).showQuestion(QHandler.questions().get(getQuestionIndex((String) searchList.getSelectedValue())), currUI, 0);
            }
        });
        startQuizWithThisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGui.get().addQuestionSaved(QHandler.questions().get(getQuestionIndex((String) searchListModel.get(searchList.getSelectedIndex()))));
            }
        });
    }

    private int getQuestionIndex(String q)
    {
        for(int i = 0; i < QHandler.questions().size(); i++)
        {
            if(HtmlFmt.removeHTML(q.trim()).contains(QHandler.questions().get(i).getQuestion().trim()))
                return i;
        }
        return -1;
    }

    private void populateBrowseList() {
        new Thread() {
            @Override
            public void run() {
                DefaultListModel temp = new DefaultListModel();

                try {
                    for(Question q : QHandler.questions())
                        temp.addElement(HtmlFmt.fixIndent(q.getQuestion()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                browseListModel = temp;
                browseList.setListData(browseListModel.toArray());
            }
        }.start();
    }

    private void searchForTerms(String input)
    {
        final String[] searchStrings = input.toLowerCase().split(",");

        for(int i = 0; i < searchStrings.length; i++)
        {
            searchStrings[i] = searchStrings[i].trim();
        }

        searchButton.setEnabled(false);

        new Thread() {
            @Override
            public void run() {
                DefaultListModel tempList = new DefaultListModel();
                try {
                    for(String searchString : searchStrings)
                    {
                        if(searchString.length() <= 0)
                            continue;
                        for(Question q : QHandler.questions())
                        {
                            String colorTag = HtmlFmt.addColor("Found in answers: ", "#FF6600");

                            String s1 = HtmlFmt.fixIndent(q.getQuestion());
                            String s2 = HtmlFmt.addBody(
                                    colorTag +
                                            HtmlFmt.addPreFmtTag(HtmlFmt.fixForHTML(q.getQuestion())
                                            ));
                            for(int i = 0; i < tempList.size(); i++)
                            {
                                String item = (String) tempList.get(i);
                                if(Pattern.compile(Pattern.quote(s2), Pattern.CASE_INSENSITIVE).matcher(item).find()
                                    || Pattern.compile(Pattern.quote(s1), Pattern.CASE_INSENSITIVE).matcher(item).find())
                                    continue;
                            }
//                            if(tempList.contains(HtmlFmt.fixIndent(q.getQuestion())) ||
//                                    tempList.contains(
//                                            HtmlFmt.addBody(
//                                                    colorTag +
//                                                            HtmlFmt.addPreFmtTag(HtmlFmt.fixForHTML(q.getQuestion())
//                            ))))
//                            {
//                                continue;
//                            }
                            boolean found = false;
                            if(searchAnswerChoicesCheckBox.isSelected())
                            {
                                for(String p : q.getPosAnswers())
                                {
                                    if(p.contains(searchString))
                                        found = true;
                                }
                            }
                            if(q.getQuestion().contains(searchString))
                                tempList.addElement(HtmlFmt.fixIndent(q.getQuestion()));
                            else if(found)
                                tempList.addElement(HtmlFmt.addBody(colorTag + HtmlFmt.addPreFmtTag(HtmlFmt.fixForHTML(q.getQuestion()))));

                        }

                        searchingText.setVisible(false);
                        searchButton.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                searchListModel = tempList;
                searchList.setListData(searchListModel.toArray());
            }
        }.start();

    }

    private void createUIComponents() {
        searchListModel = new DefaultListModel();
        searchList = new JList(searchListModel);

        browseListModel = new DefaultListModel();
        browseList = new JList(browseListModel);
    }
}