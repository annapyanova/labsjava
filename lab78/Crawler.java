package lab78;

import java.net.*;

//создание нескольких потоков для обработки URL-адресов с корнем по указанному URL-адресу
public class Crawler {
    private URLPool pool;
    
    public int countThreads = 4; //кол-во потоков
	//включение протокола в корневой URL (упрощение проверки посещённых URL)
    public Crawler(String root, int max) throws MalformedURLException {
		pool = new URLPool(max);
		URL rootURL = new URL(root);
		pool.add(new URLDepthPair(rootURL, 0));
    }

    //создание потоков CrawlerTask для обработки URL
    public void crawl() {
		for (int i = 0; i < countThreads; i++) {
	    	CrawlerTask crawler = new CrawlerTask(pool);
	    	Thread thread = new Thread(crawler);
	    	thread.start();
		}
		//пока URL "ожидают", выполняется crawl()
		while (pool.getWaitCount() != countThreads) {
	    	try {
				Thread.sleep(500);
	    	}
			//завершение работы при равенстве общего кол-ва потоков  
			//и потоков, возвращаемых соответствующим методом
	    	catch (InterruptedException e) {
				System.out.println("Ignoring unexpected InterruptedException - " +
				   e.getMessage());
	    	}
		}
		pool.printURLs();
    }

    //запуск сканера, сканирующего ссылки, начиная с корневого URL
    public static void main(String[] args) {
		//оповещение пользователя о его синксической ошибке
		if (args.length < 2 || args.length > 5) {
	    	System.err.println("Usage: java Crawler <URL> <depth> " +
			    "<patience> -t <threads>");
	    	System.exit(1);
		}
		//создание экземпляра Crawler и вызов метода crawl()
		try { 
	    	Crawler crawler = new Crawler(args[0], Integer.parseInt(args[1]));
	    	switch (args.length) {
	    		case 3: 
					CrawlerTask.maxTime = Integer.parseInt(args[2]);
					break;
	    		case 4: 
					crawler.countThreads = Integer.parseInt(args[3]);
					break;
	    		case 5: 
					CrawlerTask.maxTime = Integer.parseInt(args[2]);
					crawler.countThreads = Integer.parseInt(args[4]);
					break;
	    	}
	    	crawler.crawl();
		}
		catch (MalformedURLException e) {
	    	System.err.println("Error: The URL " + args[0] + " is not valid");
	    	System.exit(1);
		}
		System.exit(0);
    }
}