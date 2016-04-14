package bg.jwd.bookmarks.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.bookmarks.dao.UrlDao;
import bg.jwd.bookmarks.dao.generic.AbstractDao;
import bg.jwd.bookmarks.entities.Url;

@Repository
public class UrlDaoImpl extends AbstractDao<Url> implements UrlDao{

	public UrlDaoImpl(){
		super(Url.class);
	}

	// Not transactionl !!!
	//@Transactional
	@Override
	public Url addIfNotExists(Url url) {
		Url urlFromDb = this.getByProperty("link", url.getLink());
		if(urlFromDb == null){
			this.add(url);
		} else {
			return urlFromDb;
		}
		
		return url;
	}
}
