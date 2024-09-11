/*
A Behavioral Pattern.

The Observer pattern is useful when you need to present data 
in several different forms at once. The Observer is intended 
to provide you with a means to define a one-to-many dependency 
between objects so that when one object changes state, all its 
dependents are notified and updated automatically. The object 
containing the data is separated from the objects that display 
the data and the display objects observe changes in that data.
*/

import java.util.ArrayList;

public class StockMarket extends Thread {

	String name = null;
	ArrayList stocksInMarket = new ArrayList();
	int duration = 0;

    public static void main(String[] args){
		if(args == null || args.length == 0){ return;}
		int duration = 0;
		try{
			duration = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException nfe){
			System.out.println("Wrong argument :(");
			return;
		}
		Thread market = new StockMarket("NSE", duration);
		market.start();
	}

    StockMarket(String name, int duration){
		System.out.println("-------------- Opening "+ name +" Stock Market ---------------");
		this.name = name;
		this.duration = duration;
		setupStocks();
		registerInvestors();
	}

	private void setupStocks(){
		stocksInMarket.add(new ITStock("IBM", 220));
		stocksInMarket.add(new ITStock("ORA", 198));
		stocksInMarket.add(new ITStock("WIPRO", 99));
		stocksInMarket.add(new ITStock("INFY", 378));
	}

	private void registerInvestors(){
		attachInvestor(new IndividualInvestor("P.S. Sengupta"), new String[]{"IBM"});
		attachInvestor(new IndividualInvestor("K.N. Dutta"), new String[]{"ORA", "WIPRO"});
		attachInvestor(new IndividualInvestor("S. Bera"), new String[]{"IBM"});
		attachInvestor(new IndividualInvestor("S. Dasgupta"), new String[]{"ORA", "INFY"});
		attachInvestor(new IndividualInvestor("N. Dutta"), new String[]{"ORA", "IBM"});
	}

	private void attachInvestor(Investor inv, String[] stockIds){
		for(Object obj : stocksInMarket){
			Stock stock = (Stock) obj;
			for(String stockId : stockIds){
				if(stock.getSymbol().equalsIgnoreCase(stockId)){
					stock.attach(inv);
				}
			}
		}
	}

	public void run(){
		while(duration-- > 0){
			try{
				int rand = (int)(Math.random()*10);
				while(rand >= stocksInMarket.size()){
					rand = (int)(Math.random()*10);
				}
				Stock stock = (Stock)stocksInMarket.get(rand);
				int sign = (int)(Math.random()*10);
				if((sign % 2) != 0){
					sign = -1;
				}
				double newPrice = stock.getPrice() + (((int)(Math.random()*10)) * sign);
				if(newPrice != stock.getPrice()){
					System.out.println();
					System.out.println(stock.getSymbol()+" changed from $"+stock.getPrice()+" to $"+newPrice);
					stock.setPrice(newPrice);
				}
				Thread.sleep(1000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("-------------- Closing "+ name +" Stock Market ---------------");
	}
}


// Subject
class Stock implements Cloneable{
	
	String symbol;
	double price;
	ArrayList investors = new ArrayList();
	
	public String getSymbol(){
		return symbol;
	}
	public double getPrice(){
		return price;
	}
	public void setPrice(double p){
		price = p;
		notifyInvestors();
	}

	public void attach(Investor inv){
		investors.add(inv);
		inv.add(this);
	}
	public void detach(Investor inv){
		investors.remove(inv);
		inv.remove(this);
	}

	public void notifyInvestors(){
		for (int i=0; i<investors.size() ; i++)
		{
			Investor inv = (Investor)investors.get(i);
			inv.update(this);
		}
	}
	public Stock cloneStock(){
		try{
			return (Stock)this.clone();
		}
		catch(CloneNotSupportedException cns){
			System.out.println(cns.getMessage());
		}
		return null;
	}
};


// Concrete subject
class ITStock extends Stock {

	ITStock(String sym, double prc){
		symbol = sym;
		price = prc;
	}
	
};

// Observer
abstract class Investor { 
	String name;
	ArrayList portfolio = new ArrayList();

	public void add(Stock stock){
		portfolio.add(stock.cloneStock());
	}

	public void remove(Stock stock){
		Stock old = findStock(stock.getSymbol());
		if(old != null){
			portfolio.remove(old);
		}
	}

	public Stock findStock(String sym){
		for (int i=0; i<portfolio.size(); i++)
		{
			if(((Stock)portfolio.get(i)).getSymbol().equals(sym)){
				return (Stock)portfolio.get(i);
			}
		}
		return null;
	}

	public abstract void update(Stock stock);
};

// Concrete observer
class IndividualInvestor extends Investor { 
	
	IndividualInvestor(String nm){
		name = nm;
	}
	
	public void update(Stock stock){
		Stock old = findStock(stock.getSymbol());
		if(old != null){
			remove(old);
			add(stock);
			System.out.println(name+" notified.");
		}
	}

};
