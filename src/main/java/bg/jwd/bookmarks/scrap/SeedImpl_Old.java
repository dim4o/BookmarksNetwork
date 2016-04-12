package bg.jwd.bookmarks.scrap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.bookmarks.dao.RoleDao;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.entities.Keyword;
import bg.jwd.bookmarks.entities.Role;
import bg.jwd.bookmarks.entities.Tag;
import bg.jwd.bookmarks.entities.Url;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.enums.VisibilityType;

@Repository
public class SeedImpl_Old implements SeedDao_Old {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private RoleDao roleDao;

	@Transactional
	@Override
	public void seedData() {
		removeData();
		SeedUsers();
		seedBookmarks();
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void seedBookmarks(){
		Session session = this.sessionFactory.getCurrentSession();
		
		List<User> allUsers = session.createCriteria(User.class).list();
		
		User admin = allUsers.stream()
				.filter(u -> u.getUsername().equals("admin"))
				.findFirst().get();
		User gosho = allUsers.stream()
				.filter(u -> u.getUsername().equals("gosho"))
				.findFirst().get();
		User maria = allUsers.stream()
				.filter(u -> u.getUsername().equals("maria"))
				.findFirst().get();
		User boby = allUsers.stream()
				.filter(u -> u.getUsername().equals("boby"))
				.findFirst().get();
		User tony = allUsers.stream()
				.filter(u -> u.getUsername().equals("tony"))
				.findFirst().get();
		User maya = allUsers.stream()
				.filter(u -> u.getUsername().equals("maya"))
				.findFirst().get();
		
		Url url_1 = new Url("http://thisIsURL_1");
		Url url_2 = new Url("http://thisIsURL_2");
		Url url_3 = new Url("http://thisIsURL_3");
		Url url_4 = new Url("http://thisIsURL_4");
		Url url_5 = new Url("http://thisIsURL_5");
		Url url_6 = new Url("http://thisIsURL_6");
		
		Keyword keyword_1 = new Keyword("key_1");
		Keyword keyword_2 = new Keyword("key_2");
		Keyword keyword_3 = new Keyword("key_3");
		Keyword keyword_4 = new Keyword("key_4");
		Keyword keyword_5 = new Keyword("key_5");
		Keyword keyword_6 = new Keyword("key_6");
		
		Tag tag_1 = new Tag("tag_1");
		Tag tag_2 = new Tag("tag_2");
		Tag tag_3 = new Tag("tag_3");
		Tag tag_4 = new Tag("tag_4");
		Tag tag_5 = new Tag("tag_5");
		Tag tag_6 = new Tag("tag_6");
		
		session.save(keyword_1);
		session.save(keyword_2);
		session.save(keyword_3);
		session.save(keyword_4);
		session.save(keyword_5);
		session.save(keyword_6);	
		
		session.save(url_1);
		session.save(url_2);
		session.save(url_3);
		session.save(url_4);
		session.save(url_5);
		session.save(url_6);
		
		session.save(tag_1);
		session.save(tag_2);
		session.save(tag_3);
		session.save(tag_4);
		session.save(tag_5);
		session.save(tag_6);

		Bookmark bookmark_1 = new Bookmark("Title_1", url_1, admin, VisibilityType.Private);
		Bookmark bookmark_2 = new Bookmark("Title_2", url_1, admin, VisibilityType.Private);
		Bookmark bookmark_3 = new Bookmark("Title_3", url_1, admin, VisibilityType.Private);
		Bookmark bookmark_4 = new Bookmark("Title_4", url_2, gosho, VisibilityType.Public);
		Bookmark bookmark_5 = new Bookmark("Title_5", url_2, gosho, VisibilityType.Public);
		Bookmark bookmark_6 = new Bookmark("Title_6", url_2, gosho, VisibilityType.Public);
		
		Set<Keyword> keywordSet_1 = new HashSet<Keyword>();
		keywordSet_1.add(keyword_1);
		keywordSet_1.add(keyword_2);
		keywordSet_1.add(keyword_3);
		
		Set<Keyword> keywordSet_2 = new HashSet<Keyword>();
		keywordSet_2.add(keyword_4);
		keywordSet_2.add(keyword_5);
		keywordSet_2.add(keyword_6);
		
		bookmark_1.setKeywords(keywordSet_1);
		bookmark_2.setKeywords(keywordSet_2);
		
		Set<Tag> tagSet_1 = new HashSet<Tag>();
		tagSet_1.add(tag_1);
		tagSet_1.add(tag_2);
		tagSet_1.add(tag_3);
		
		Set<Tag> tagSet_2 = new HashSet<Tag>();
		tagSet_2.add(tag_4);
		tagSet_2.add(tag_5);
		tagSet_2.add(tag_6);
		
		bookmark_1.setKeywords(keywordSet_1);
		bookmark_2.setKeywords(keywordSet_2);
		
		bookmark_1.setTags(tagSet_1);
		bookmark_2.setTags(tagSet_2);
		
		session.save(bookmark_1);
		session.save(bookmark_2);
		session.save(bookmark_3);
		session.save(bookmark_4);
		session.save(bookmark_5);
		session.save(bookmark_6);
		
		admin.getBookmarks().add(bookmark_1);
		admin.getBookmarks().add(bookmark_2);
		admin.getBookmarks().add(bookmark_3);
		
		gosho.getBookmarks().add(bookmark_4);
		gosho.getBookmarks().add(bookmark_5);
		gosho.getBookmarks().add(bookmark_6);
		
		session.update(admin);
		session.update(gosho);
	}
	

	private void SeedUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Role> roles = new ArrayList<Role>();
		Role adminRole = new Role("admin");
		roles.add(adminRole);
		Role userRole = new Role("user");
		roles.add(userRole);

		roleDao.add(adminRole);
		roleDao.add(userRole);

		List<Role> roles1 = new ArrayList<Role>();
		List<Role> roles2 = new ArrayList<Role>();
		roles1.add(adminRole);
		roles2.add(userRole);

		User user1 = new User("admin", "202cb962ac59075b964b07152d234b70", "admin@aa.aa",
				"Pesho", "Peshev", "address1");
		user1.setRoles(roles);

		User user2 = new User("gosho", "202cb962ac59075b964b07152d234b70", "gosho@aa.a", 
				"Gosho", "Goshev", "address2");
		user2.setRoles(roles);

		adminRole.getUsers().add(user1);
		adminRole.getUsers().add(user2);

		userRole.getUsers().add(user1);
		userRole.getUsers().add(user2);
		session.save(user1);
		session.save(user2);

		User user3 = new User("maria", "202cb962ac59075b964b07152d234b70", "maria@aa.a", "Maria",
				"Maharadjata", "address3");
		User user4 = new User("tony", "202cb962ac59075b964b07152d234b70", "tony@aa.a", "Toni",
				"Tonchev", "address4");
		User user5 = new User("boby", "202cb962ac59075b964b07152d234b70", "boby@aa.a", "Bobi",
				"Bobarev", "address5");
		User user6 = new User("maya", "202cb962ac59075b964b07152d234b70", "maya@aa.a", "Maya",
				"Selska", "address6");
		
		userRole.getUsers().add(user3);
		userRole.getUsers().add(user4);
		userRole.getUsers().add(user5);
		userRole.getUsers().add(user6);
		user3.getRoles().add(userRole);
		user4.getRoles().add(userRole);
		user5.getRoles().add(userRole);
		user6.getRoles().add(userRole);
		
		user1.getFollowersCollection().add(user2);
		user1.getFollowersCollection().add(user3);
		user1.getFollowersCollection().add(user4);
		
		user2.getFollowersCollection().add(user1);
		user2.getFollowersCollection().add(user2);
		user2.getFollowersCollection().add(user3);
		
		user4.getFollowersCollection().add(user2);
		user5.getFollowersCollection().add(user2);
		user6.getFollowersCollection().add(user2);
		
		user2.getFollowingCollection().add(user4);
		user2.getFollowingCollection().add(user5);
		user2.getFollowingCollection().add(user6);

		session.save(user1);
		session.save(user2);
		session.save(user3);
		session.save(user4);
		session.save(user5);
		session.save(user6);
	}

	private void removeData() {

		Session session = this.sessionFactory.getCurrentSession();
		String sql = "DELETE FROM Role";
		Query  query = session.createQuery(sql);
		query.executeUpdate();

		sql = "DELETE FROM Bookmark";
		query = session.createQuery(sql);
		query.executeUpdate();

		sql = "DELETE FROM Url";
		query = session.createQuery(sql);
		query.executeUpdate();
		
		sql = "DELETE FROM Tag";
		query = session.createQuery(sql);
		query.executeUpdate();

		sql = "DELETE FROM Keyword";
		query = session.createQuery(sql);
		query.executeUpdate();
		
		sql = "DELETE FROM User";
		query = session.createQuery(sql);
		query.executeUpdate();
	}
}
