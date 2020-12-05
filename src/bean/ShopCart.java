package bean;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


//(Dmitry)
public class ShopCart {

	public Map<BookBean, Integer> bookList;

	public ShopCart(Map<BookBean, Integer> bookList) {
		super();
		this.bookList = bookList;
	}

	public ShopCart() {
		bookList=new TreeMap<BookBean, Integer>();
	}

	// To remove 1 book from the cart
	public void removeBookFromCart(BookBean book) {
		if (bookList.containsKey(book)) {
			bookList.remove(book);
		}
	}

	@Override
	public String toString() {
		return "ShopCart [bookList=" + bookList + "]";
	}

	// To remove all books from the cart
	public void clearCart() {
		this.bookList.clear();
	}

	public void addBookToCart(BookBean book) {
		if (bookList.containsKey(book)) {
			bookList.put(book, bookList.get(book) + 1); // increasing the quantity by 1 if book is already in the
														// list
		} else
			bookList.put(book, 1);

	}

	// To get a total price of a row (BookPrice * Quantityt slected)
	public int getRowCost(BookBean book) {
		if (bookList.containsKey(book))
			return (bookList.get(book) * book.getBookPrice()); // multiplying quantity by price of one book
		else
			return 0;
	}

	// To get a total price of all items in the shopping cart
	public int calcTotalCartCost() {
		int cost = 0;
		for (Map.Entry<BookBean, Integer> entry : this.bookList.entrySet()) {
			cost += this.getRowCost(entry.getKey()); // sum up prices if all rows in the table
		}
		return cost;
	}
	
	public double calcTaxCost() {
		 DecimalFormat twoDForm = new DecimalFormat("#.##");
		 return Double.valueOf(twoDForm.format(0.13 * calcTotalCartCost()));

	}
	
	public double totalCost() {
		return calcTaxCost() + calcTotalCartCost();
	}


	// To change the number of units for a selected book
	public void changeCartBookQty(BookBean book, int qty) {
		if (bookList.containsKey(book))
			this.bookList.put(book, qty);
	}

	// NOT SURE HOW TO WORK WITH IMAGES
	public void geBookImage() {
	}

	public int getBooktPrice(BookBean book) {
		return book.getBookPrice();
	}

	public String getBookCategory(BookBean book) {
		return book.getBookCategory();
	}

	public String getBookTitle(BookBean book) {
		return book.getBookTitle();
	}

	public int getBookId(BookBean book) {
		return book.getBookId();
	}

	// To get the number of items in the cart:
	public int getCartSize() {
		int count = 0;
		for (Map.Entry<BookBean, Integer> entry : this.bookList.entrySet()) {
			count += entry.getValue();
		}
		return count;
	}

	// To get the list of items in the cart
	public Map<BookBean, Integer> getShopCart() {
		return this.bookList;
	}

	// to remove a book from the list by passing BID only
	// loops through the whole list to find a book with the BID provided
	public void removeBookByBid(String bid) {

		Iterator<Map.Entry<BookBean, Integer>> it = bookList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<BookBean, Integer> entry = it.next();
			Integer bookID = entry.getKey().getBookId();
			if (bookID.equals(Integer.parseInt(bid)))
				it.remove();
		}

	}

	public void updateQuantity(BookBean book, int qty) {
		if (bookList.containsKey(book)) {
			bookList.put(book, qty); // increasing the quantity by 1 if book is already in the
		}
	}

	public void increaseBookQty(String bid) {
		BookBean book = new BookBean();
		int qty = 0;

		Iterator<Map.Entry<BookBean, Integer>> it = bookList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<BookBean, Integer> entry = it.next();
			Integer bookID = entry.getKey().getBookId();
			if (bookID.equals(Integer.parseInt(bid))) {
				book = entry.getKey();
				qty = entry.getValue();

			}
			this.updateQuantity(book, qty + 1);
		}
	}

	public void decreaseBookQty(String bid) {
		BookBean book = new BookBean();
		int qty = 0;

		Iterator<Map.Entry<BookBean, Integer>> it = bookList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<BookBean, Integer> entry = it.next();
			Integer bookID = entry.getKey().getBookId();
			if (bookID.equals(Integer.parseInt(bid))) {
				book = entry.getKey();
				qty = entry.getValue();
			}
		}
		if (qty - 1 == 0)
			removeBookByBid(bid);
		if (qty - 1 > 0)
			this.updateQuantity(book, qty - 1);
	}

}
