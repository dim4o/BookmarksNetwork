package bg.jwd.bookmarks.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
@Table(name = "tags")
public class Tag implements Serializable{

	private static final long serialVersionUID = -1926673611494095885L;

	@Id
	@GenericGenerator(name="tagGen" , strategy="increment")
	@GeneratedValue(generator="tagGen")
	@Column(name = "tag_id")
	private long tagId;
	
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Column(name = "tag_name", unique = true)
	private String tagName;
	
	@ManyToMany(mappedBy = "tags", cascade = {
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH
			})
	private List<Bookmark> bookmarks;

	@ManyToMany(mappedBy = "tags")
	private Set<User> users;
	
	public Tag(){ }
	
	public Tag(String tagName){
		this.tagName = tagName;
	}
	
	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return this.getTagName();
	}
	
	// TODO: height quality override / null ??
	@Override
	public boolean equals(Object that) {
		Tag other = (Tag)that;
		return this.tagName.equals(other.tagName);
	}
	
	@Override
    public int hashCode() {
        return this.tagName.hashCode();
    }
}
