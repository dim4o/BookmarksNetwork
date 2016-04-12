package bg.jwd.bookmarks.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class BookmarkSearchDto {
	
	@NotNull
	@NotEmpty
	private String searchTerm;
	
	private boolean titleSearch;
	
	private boolean keywordSearch;
	
	private boolean tagSearch;
	
	private boolean descriptionSearch;
	
	private boolean globalSearch;

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public boolean isTitleSearch() {
		return titleSearch;
	}

	public void setTitleSearch(boolean titleSearch) {
		this.titleSearch = titleSearch;
	}

	public boolean isKeywordSearch() {
		return keywordSearch;
	}

	public void setKeywordSearch(boolean keywordSearch) {
		this.keywordSearch = keywordSearch;
	}

	public boolean isTagSearch() {
		return tagSearch;
	}

	public void setTagSearch(boolean tagSearch) {
		this.tagSearch = tagSearch;
	}

	public boolean isDescriptionSearch() {
		return descriptionSearch;
	}

	public void setDescriptionSearch(boolean descriptionSearch) {
		this.descriptionSearch = descriptionSearch;
	}

	public boolean isGlobalSearch() {
		return globalSearch;
	}

	public void setGlobalSearch(boolean globalSearch) {
		this.globalSearch = globalSearch;
	}  
}
