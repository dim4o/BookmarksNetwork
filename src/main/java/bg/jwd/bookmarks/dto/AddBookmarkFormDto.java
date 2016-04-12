package bg.jwd.bookmarks.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class AddBookmarkFormDto {

	@NotEmpty
	private String title;
	
	@NotEmpty
	private String link;
	
	private String description;
	
	private String visibility;
	
	private String keywords;
	
	private String tags;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String url) {
		this.link = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
