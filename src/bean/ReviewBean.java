package bean;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType (XmlAccessType.FIELD)
public class ReviewBean {
	
//	@XmlElement(name = "bid")
	private String bid;
//	@XmlElement(name = "author")
	private String author;
//	@XmlElement(name = "review")
	private String review;
	
	//constructors
	
	public ReviewBean() {
		
	}
public ReviewBean(String bid,String author,String review) {
	this.setBid(bid);
	this.author=author;
	this.review=review;
		
	}
	
	
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	
	

}
