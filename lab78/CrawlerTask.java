package lab78;

import java.io.*;
import java.net.*;
import java.util.regex.*;

//веб-сканирование нескольких потоков (просмотр веб-страниц, добавление новых ссылок в пул для сканирования в нескольких потоках)
public class CrawlerTask implements Runnable {
    public static final String LINK_REGEX = "href\\s*=\\s*\"([^$^\"]*)\"";
    public static final Pattern LINK_PATTERN = Pattern.compile(LINK_REGEX, Pattern.CASE_INSENSITIVE);
    public static int maxTime = 5; //время, в теч. кот. сокет будет ожидать ответ сервера
    private URLPool pool; 

    public CrawlerTask(URLPool p) {
		pool = p;
    }

    //сокет для отправки HTTP-запроса на веб-страницу URLDepthPair
    public Socket sendRequest(URLDepthPair nextPair) 
	throws UnknownHostException, SocketException, IOException {
		//создание сокета
		Socket socket = new Socket(nextPair.getHost(), 80);
		socket.setSoTimeout(maxTime * 1000);
		
		//запрос ресурса у host веб-страницы
		OutputStream out = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(out, true);
		writer.println("GET " + nextPair.getDocPath() + " HTTP/1.1");
		writer.println("Host: " + nextPair.getHost());
		writer.println("Connection: close");
		writer.println(); 

		return socket;
    }

    //метод для обработки url путём нахождения всех ссылок и добавления их в URL-Pool
    public void processURL(URLDepthPair url) throws IOException { 
		Socket socket;
		try {
	    	socket = sendRequest(url);
		}
		catch (UnknownHostException e) {
	    	System.err.println("Host "+ url.getHost() + " couldn't be determined"); 
	    	return;
		}
		catch (SocketException e) {
	    	System.err.println("Error with socket connection: " + url.getURL() + 
			       " - " + e.getMessage());
	    	return;
		}
		catch (IOException e) {
	    	System.err.println("Couldn't retrieve page at " + url.getURL() +
			       " - " + e.getMessage());
	    	return;
		}

		InputStream input = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input)); 
	
		String line;
		//цикл продолжается, пока пока в пуле не останется пар URL-Depth
		while ((line = reader.readLine()) != null) { //получение пары URL-Depth из пула
	    	Matcher LinkFinder = LINK_PATTERN.matcher(line);//получение веб-страницы по URL-адресу
	    		while (LinkFinder.find()) { //поиск на странице других URL-адресов
					String newURL = LinkFinder.group(1);
					URL newSite;
					try { 
		    			if (URLDepthPair.isAbsolute(newURL))
							newSite = new URL(newURL);
		    			else newSite = new URL(url.getURL(), newURL);
						//добаление к пулу новой пары URL-Depth (глубина на 1 больше предыдущей)
		    			pool.add(new URLDepthPair(newSite, url.getDepth() + 1));
					}
					catch (MalformedURLException e) {
		    			System.err.println("Error with URL - " + e.getMessage());
					}
	    		}
		}
		reader.close();
		//закрытие socket
		try {
	    	socket.close();
		}
		catch (IOException e) {
	    	System.err.println("Couldn't close connection to " + url.getHost() +
			       " - " + e.getMessage());
        }
    }
    
    //метод для первого URL в пуле
	@Override
    public void run() {
		URLDepthPair nextPair;
		while (true) {
	    	nextPair = pool.get();
	    	try {
				processURL(nextPair);
	    	}
	    	catch (IOException e) {
				System.err.println("Error reading the page at " + nextPair.getURL() +
				   " - " + e.getMessage());
	    	}
		}
    }
}
