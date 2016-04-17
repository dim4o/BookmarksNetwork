package bg.jwd.bookmarks.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import bg.jwd.bookmarks.enums.VisibilityType;

@Indexed
@Entity
@Table(name = "bookmarks")
public class Bookmark implements Serializable {

	private static final long serialVersionUID = -4677864025079616081L;

	@Id
	@GenericGenerator(name = "bookmarkGen", strategy = "increment")
	@GeneratedValue(generator = "bookmarkGen")
	@Column(name = "bookmark_id")
	private long bookmarkId;

	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Column(name = "title")
	private String title;

	@ManyToOne(cascade = {
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH
			})
	@JoinColumn(name = "url_id")
	private Url url;

	@ManyToOne//(cascade=CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private User author;

	@Column(name = "creation_date")
	private Date creationDate;

	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Column(name = "description")
	private String description;

	@IndexedEmbedded
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = { 
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH })
	@JoinTable(
			name = "bookmarks_keywords", 
			joinColumns = @JoinColumn(name = "bookmark_id", referencedColumnName = "bookmark_id"), 
			inverseJoinColumns = @JoinColumn(name = "keyword_id", referencedColumnName = "keyword_id"))
	private Set<Keyword> keywords;

	@IndexedEmbedded
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = { 
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH })
	@JoinTable(
			name = "bookmarks_tags", 
			joinColumns = @JoinColumn(name = "bookmark_id", referencedColumnName = "bookmark_id"), 
			inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
	private Set<Tag> tags;

	@Column(name = "views")
	private int views;

	@Column(name = "raiting")
	private int raiting;

	@Column(name = "rank")
	private double rank;

	@Enumerated(EnumType.STRING)
	@Column(name = "visibility")
	private VisibilityType visibility;

	@Transient
	private String keywordsString;
	
	@Transient
	private String tagsString;
	
	@Transient
	private boolean saved;

	public Bookmark() {
	}

	public Bookmark(String title, Url url, User author, VisibilityType visibility) {
		this.title = title;
		this.url = url;
		this.author = author;
		this.setVisibility(visibility);
		this.creationDate = new Date();
		this.views = 0;
		this.raiting = 0;
		this.rank = 0.0d;
		this.keywords = new HashSet<Keyword>();
		this.tags = new HashSet<Tag>();
	}

	public long getBookmarkId() {
		return bookmarkId;
	}

	public void setBookmarkId(long bookmarkId) {
		this.bookmarkId = bookmarkId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getRaiting() {
		return raiting;
	}

	public void setRaiting(int raiting) {
		this.raiting = raiting;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	// TODO: height quality override / null ??
	@Override
	public boolean equals(Object that) {
		
		Bookmark other = (Bookmark) that;
		return new Long(this.bookmarkId).equals(new Long(other.bookmarkId));
	}

	public VisibilityType getVisibility() {
		return visibility;
	}

	public void setVisibility(VisibilityType visibility) {
		this.visibility = visibility;
	}

	public String getKeywordsString() {
		return Arrays.toString(this.keywords.toArray()).replaceAll("\\[|\\]","");
	}

	public void setKeywordsString(String keywordsString) {
		//this.keywordsString = Arrays.toString(this.keywords.toArray()).replaceAll("\\[|\\]|[,][]]","");
		this.keywordsString = keywordsString;
	}
	
	public String getTagsString() {
		return Arrays.toString(this.tags.toArray()).replaceAll("\\[|\\]","");
	}

	public void setTagsString(String tagsString) {
		this.tagsString = tagsString;
	}

	public boolean getSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
}
