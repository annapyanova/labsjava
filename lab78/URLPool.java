package lab78;

import java.util.*;

//хранение списка URL-адресов для поиска и глубины поиска
public class URLPool {
    private int maxDepth;
    private int waitCount = 0; //кол-во текущих потоков в методе wait()
    private LinkedList<URLDepthPair> rawURLs; //необработанные
    private LinkedList<URLDepthPair> processedURLs; //обработанные
    private HashSet<String> scanURLs; //уникальные отсканированные
    
	//URLPool с максимальной заданной глубиной
    public URLPool(int max) {
		rawURLs = new LinkedList<URLDepthPair>();
		processedURLs = new LinkedList<URLDepthPair>();
		scanURLs = new HashSet<String>();
		maxDepth = max;
    }
	
    //возвращение кол-ва ожидающих потоков
	public synchronized int getWaitCount() {
		return waitCount;
    }

	//добавление пары URLDepthPair к соответствующему списку
    public synchronized void add(URLDepthPair nextPair) {
		String newURL = nextPair.getURL().toString();
		String modifyURL = (newURL.endsWith("/")) ? newURL.substring(0, newURL.length() -1) : newURL;
		if (scanURLs.contains(modifyURL))
	    	return;
		scanURLs.add(modifyURL);
	
		if (nextPair.getDepth() < maxDepth) {
	    	rawURLs.add(nextPair);
	    	notify(); //в случае, когда новый URL-адрес добавлен к пулу
		}
		processedURLs.add(nextPair);
    }

    public synchronized URLDepthPair get() {
		//ожидание в случае, если ни один URL-адрес в настоящее время недоступен
		while (rawURLs.size() == 0) {
	    	waitCount++; //увеличивается непосредственно перед вызовом wait()
	    	try {
				wait();
	    	}
	    	catch (InterruptedException e) {
				System.out.println("Ignoring unexpected InterruptedException - " + 
				   e.getMessage());
	    	}
	    	waitCount--; //уменьшается сразу после выхода из режима ожидания
		}
		return rawURLs.removeFirst();
    }

    //вывод на экран всех обработанных URL
    public synchronized void printURLs() {
		System.out.println("\nUnique URLs Found: " + processedURLs.size());
		while (!processedURLs.isEmpty()) {
	    	System.out.println(processedURLs.removeFirst());
		}
    }
}