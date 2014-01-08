package underthehood;

import UI.HtmlFmt;

import java.util.regex.Pattern;

/**
 * Created by marko on 18/12/13.
 */
public class Util {
    public static int getQuestionIndex(String q)
    {
        for(int i = 0; i < QHandler.questions().size(); i++)
        {
            if(HtmlFmt.removeHTML(q.trim()).contains(QHandler.questions().get(i).getQuestion().trim()))
                return i;
        }
        return -1;
    }

    public static boolean containsCaseInsensitive(String a, String b)
    {
        return Pattern.compile(Pattern.quote(a), Pattern.CASE_INSENSITIVE).matcher(b).find();
    }
}
