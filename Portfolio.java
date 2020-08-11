import java.util.TreeMap;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.lang.Math;

//Construct a simple Portfolio class that has a collection of Stocks
public class Portfolio {
	// Double is number of shares, Stock is stock

	int[] shares;
	Stock[] stocks;

	public Portfolio(int[] shares, Stock[] stocks) {
		// 2400 shares of google, 15000 shares of microsoft
		this.shares = shares;
		this.stocks = stocks;
	}

	public double portfolioValue(String date) {
		double value = 0;
		for (int i = 0; i < shares.length; i++) {
			value += shares[i] * Stock.getPriceWithDate(stocks[i], date);
		}
		return value;
	}

	// and a "Profit" method that receives 2 dates and returns the profit of the
	// Portfolio between those dates. Assume each Stock has a "Price" method that
	// receives a date and returns its price.
	public double calculateProfit(String startDate, String endDate) {
		return portfolioValue(endDate) - portfolioValue(startDate);
	}

	// Bonus Track: make the Profit method return the "annualized return" of the
	// portfolio between the given dates.
	public double profit(String startDate, String endDate) {
		LocalDate sd = LocalDate.parse(startDate);
		LocalDate ed = LocalDate.parse(endDate);

		long days = ChronoUnit.DAYS.between(sd, ed);
		System.out.println("days: " + days);
		System.out
				.println("pvt/pvt-1: " + Math.pow((portfolioValue(endDate) / portfolioValue(startDate)), (252 / days)));
		return -1 + Math.pow((portfolioValue(endDate) / portfolioValue(startDate)), (252 / days));
	}

	public static void main(String[] args) {

		Stock google = new Stock("goog");
		Stock microsoft = new Stock("msft");
		Portfolio alpha = new Portfolio(new int[] { 2400, 15000 }, new Stock[] { google, microsoft });

		google.updateQuotes(Stock.goog);
		microsoft.updateQuotes(Stock.msft);

		System.out.println(Stock.getPriceWithDate(google, "2020-08-07"));
		System.out.println(Stock.getPriceWithDate(microsoft, "2020-08-05"));
		System.out.println("p value 2020 08 04: " + alpha.portfolioValue("2020-08-04"));
		System.out.println(alpha.calculateProfit("2020-08-04", "2020-08-06"));
		System.out.println(alpha.profit("2020-08-03", "2020-08-07"));

	}
}

class Stock {

	String ticker;
	TreeMap<LocalDate, Double> prices;

	public Stock(String ticker) {
		this.ticker = ticker;
		this.prices = new TreeMap<>();
	}

	public String getTicker() {
		return ticker;
	}

	public TreeMap<LocalDate, Double> getPrices() {
		return prices;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setPrices(TreeMap<LocalDate, Double> prices) {
		this.prices = prices;
	}

	public void updateQuotes(String[][] quotes) {
		for (String[] quote : quotes) {
			this.prices.put(LocalDate.parse(quote[0]), Double.parseDouble(quote[1]));
		}
	}

	static double getPriceWithDate(Stock stock, String date) {
		return stock.getPrices().get(LocalDate.parse(date));
	}

	// Simula datos obtenidos de sitio web
	static String[][] goog = { { "2020-08-07", "1496.10" }, { "2020-08-06", "1494.90" }, { "2020-08-05", "1491.80" },
			{ "2020-08-04", "1490.90" }, { "2020-08-03", "1492.30" } };

	static String[][] msft = { { "2020-08-07", "208.25" }, { "2020-08-06", "207.90" }, { "2020-08-05", "207.45" },
			{ "2020-08-04", "206.92" }, { "2020-08-03", "206.82" } };

}
