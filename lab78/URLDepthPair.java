package lab78;

import java.net.*;
import java.util.regex.*;

//работа с парой: URL + глубина поиска
public class URLDepthPair {
    public static final String URL_REGEX = "(https?:\\/\\/)((\\w+\\.)+\\.(\\w)+[~:\\S\\/]*)";
    public static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX,  Pattern.CASE_INSENSITIVE);
    private URL URL;
    private int depth;
    
    public URLDepthPair(URL url, int d) throws MalformedURLException {
	    URL = new URL(url.toString());
	    depth = d;
    }
    
    //вывод содержимого пары
    @Override 
    public String toString() {
	    return "URL: " + URL.toString() + ", Depth: " + depth;
    }

    //возвращение URL
    public URL getURL() {
	    return URL;
    }
    
    //возвращение глубины поиска
    public int getDepth() {
	    return depth;
    } 

    //возвращение host, которое указано в URL 
    public String getHost() {
	    return URL.getHost();
    }
    
    //возвращение пути указанного URL
    public String getDocPath() {
	    return URL.getPath();
    }
    
    //проверка на абсолютный URL-адрес
    public static boolean isAbsolute(String url) {
	    Matcher URLChecker = URL_PATTERN.matcher(url);
	    if (!URLChecker.find())
	        return false;
	    return true;
    }
}