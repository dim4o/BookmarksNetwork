package bg.jwd.bookmarks.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "keywords")
public class Keyword implements Serializable{

	private static final long serialVersionUID = -2316336224054158237L;

	@Id
	@GenericGenerator(name="keywordGen" , strategy="increment")
	@GeneratedValue(generator="keywordGen")
	@Column(name = "keyword_id")
	private long keywordId;
	
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Column(name = "keyword", unique = true)
	private String keyword;
	
	@ManyToMany(mappedBy = "keywords", cascade = {
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH
			})
	private List<Bookmark> bookmarks;

	public Keyword(){}
	
	public Keyword(String keyword){
		this.keyword = keyword;
	}
	
	public long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(long keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}
	
	@Override
	public String toString() {
		return this.getKeyword();
	}
	
	// TODO: height quality override / null ??
	@Override
	public boolean equals(Object that) {
		Keyword other = (Keyword)that;
		return this.keyword.equals(other.keyword);
	}
	
	@Override
    public int hashCode() {
        return this.keyword.hashCode();
    }
}
