package bg.jwd.bookmarks.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.bookmarks.dao.KeywordDao;
import bg.jwd.bookmarks.entities.Keyword;
import bg.jwd.bookmarks.services.generic.AbstractService;
import bg.jwd.bookmarks.servises.KeywordService;

public class KeywordsServiceImpl extends AbstractService<Keyword> implements KeywordService{

	@Autowired
	KeywordDao keywordDao;
	
	@Override
	public List<Keyword> getAsCollection(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public boolean addAll(Set<Keyword> keywords) {
		return keywordDao.addAll(keywords);
	}
}
