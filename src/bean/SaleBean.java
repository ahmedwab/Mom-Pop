package bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//will be used to genereate reports//

@XmlAccessorType(XmlAccessType.FIELD)
public class SaleBean {

	// adding XML El tags to genereate XML schema
	@XmlElement(name = "sid")
	int saleId;
	@XmlElement(name = "cid")
	int customerId;
	@XmlElement(name = "bid")
	int bookId;
	@XmlElement(name = "saleQuantity")
	int saleQty;
	@XmlElement(name = "saleDate")
	String saleDate;

	// constructor
	public SaleBean(String saleId, String customerID, String bookId, String saleQuantity, String saleDate) {
		this.saleId = Integer.parseInt(saleId);
		this.customerId = Integer.parseInt(customerID);
		this.bookId = Integer.parseInt(bookId);
		this.saleQty = Integer.parseInt(saleQuantity);
		this.saleDate = saleDate;

	}

	@Override
	public String toString() {
		return "SaleBean [saleId=" + saleId + ", customerId=" + customerId + ", bookId=" + bookId + ", saleQty="
				+ saleQty + ", saleDate=" + saleDate + "]";
	}

	
	
	
}
