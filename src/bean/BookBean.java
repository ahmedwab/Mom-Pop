package bean;

import java.sql.Blob;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType (XmlAccessType.FIELD)
public class BookBean  implements Comparable<BookBean>{
	
	//adding XML El tags to genereate XML schema
//	@XmlElement(name = "image")
	int bookId;
//	@XmlElement(name = "title")
	String bookTitle;
//	@XmlElement(name = "price")
	int bookPrice;
//	@XmlElement(name = "quantity")
//	int bookQty;
//	@XmlElement(name = "category")
	String bookCategory;
//	@XmlElement(name = "author")
	private String author;
	
	
		
	//constructor	
	public BookBean ( String bid,String author, String title, String price, String category) {
		this.bookId = Integer.parseInt(bid);
		this.author=author;
		this.bookPrice = Integer.parseInt(price);
		this.bookTitle = title;
		this.bookCategory = category;
		this.setAuthor(author);
	}
	
	//emptry constructor
	public BookBean () {};


	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}


	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}



	public String getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}
	
	
	//books are differnt as long as their bid is different
	//other attribytes can be the same
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		BookBean other = (BookBean) obj;
		
		if (bookId != other.bookId)
			return false;

		return true;
	}
	
	


	//// FOR TESTING
	@Override
	public String toString() {
		return "BookBean [bookId=" + bookId + ", bookPrice=" + bookPrice + ", bookTitle="
				+ bookTitle + "]";
	}


	@Override
	public int compareTo(BookBean book) {
		// TODO Auto-generated method stub
		return this.bookId - book.bookId;
	}


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
