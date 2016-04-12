package bg.jwd.bookmarks.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "urls")
public class Url implements Serializable{

	private static final long serialVersionUID = -7283238305838396245L;

	@Id
	@GenericGenerator(name="urlGen" , strategy="increment")
	@GeneratedValue(generator="urlGen")
	@Column(name = "url_id")
	private long urlId;
	
	@Column(name = "link", unique = true)
	private String link;

	public Url(){ }
	
	public Url(String value){
		this.link = value;
		// TODO: date. 01.01- 11:59
		this.bookmarks = new ArrayList<Bookmark>();
	}
	
	public long getUrlId() {
		return urlId;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="url", cascade = {
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH
			})
	private List<Bookmark> bookmarks;

	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}
	
	// TODO: height quality override / null ??
	@Override
	public boolean equals(Object that) {
		Url other = (Url)that;
		return this.link.equals(other.link);
	}
}
