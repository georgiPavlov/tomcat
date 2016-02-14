
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;



/**
 * Created by georgipavlov on 14.02.16.
 */
@WebServlet("/general")
public class WebRss extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        URL url = new URL("http://www.dnevnik.bg/rss/?page=index");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed newsfeed = null;
        try {
            newsfeed = input.build(new XmlReader(http));
        } catch (IllegalArgumentException | FeedException e) {
        }
        List entries = newsfeed.getEntries();
        Iterator itEntries = entries.iterator();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(response.getWriter());

        while (itEntries.hasNext()) {
            SyndEntry entry = (SyndEntry) itEntries.next();
            writer.print("<a href=\"" + entry.getLink() + "\">" );
            writer.print("<h3>" + entry.getTitle() + "</h3>" + "</a>");
            writer.print("<p>" + entry.getDescription().getValue() + "</p>\n");
            writer.print(entry.getAuthor() + "\n");
            writer.print(entry.getPublishedDate().toString());

        }
    }
}
