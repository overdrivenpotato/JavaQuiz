package UI;

/**
 * Created by Marko on 12/14/13.
 */
public class HtmlFmt {
    public static String fixIndent(String input)
    {
        return addBody(addPreFmtTag(fixForHTML(input)));
    }

    public static String addPreFmtTag(String input)
    {
        return "<pre>" + input + "</pre>";
    }

    public static String addBody(String input)
    {
        return "<html><body>" + input + "</body></html>";
    }

    public static String fixForHTML(String input)
    {
        return input.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    public static String addColor(String input, String color)
    {
        return "<p style=\"color:" + color + "\">" + input + "</p>";
    }

    public static String removeHTML(String input)
    {
        if(!input.contains("<html>"))
            return input;
        input = input.replaceAll("&lt;", "<").replaceAll("&gt;", ">");

        if(input.contains("<pre>"))
            return input.replaceAll("<html><body><pre>", "").replaceAll("</pre></body></html>", "");
        else
            return input.split("<html><body>")[0].split("</body></html>")[0];
    }
}
