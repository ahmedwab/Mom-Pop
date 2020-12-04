package bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "bookReport")

@XmlAccessorType (XmlAccessType.FIELD)

public class ListWrapper {
	@XmlElement(name = "bookList")
	public List<BookBean> list;
	
	@XmlElement (name="bookId")
	public String bookId;


	public ListWrapper(String bookId, List<BookBean> list) {
		super();
		this.bookId = bookId;
		this.list = list;
	}
	
	@Override
	public String toString() {
		return "ListWrapper [list=" + list + ", bookId=" + bookId + "]";
	}

	public ListWrapper () {
		//this(null,null,null);
	}
	

}