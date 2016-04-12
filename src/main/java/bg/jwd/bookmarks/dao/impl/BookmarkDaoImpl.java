package bg.jwd.bookmarks.dao.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import bg.jwd.bookmarks.dao.BookmarkDao;
import bg.jwd.bookmarks.dao.generic.AbstractDao;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.entities.Url;
import bg.jwd.bookmarks.enums.VisibilityType;
import bg.jwd.bookmarks.util.UserUtils;

@Repository
public class BookmarkDaoImpl extends AbstractDao<Bookmark> implements BookmarkDao {

	@Autowired
	private SessionFactory sessionFactory;

	public BookmarkDaoImpl() {
		super(Bookmark.class);
	}

	@Override
	public void importBookmarks(HttpServletRequest request, MultipartFile file, String visibility) throws Throwable  {
		File uploadedFile = this.uploadFile(request, file);

		/*if(true){
			throw new Exception();
		}*/
		String html = null;
        //try {
		try{
			html = Files.toString(uploadedFile, Charsets.UTF_8);}
		catch(Exception e){
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBCCCCCCCCCCCC");
			e.printStackTrace();
		}
			Document doc = Jsoup.parse(html);
			
			Elements links = doc.select("a[href]");
			//List bookmarks = new ArrayList<Bookmark>();
			
			Transaction tn = null;
			Session session = this.sessionFactory.openSession();
			
			try {
				tn = session.beginTransaction();
				// body
				for (Element element : links) {
					String link = element.attr("href");
					String title = element.text();
					if(link != null && !link.isEmpty() && title != null && !title.isEmpty()){
						Url url = new Url(link);
						Criteria criteria = session.createCriteria(Url.class);
						criteria.add(Restrictions.like("link", link));
						@SuppressWarnings("unchecked")
						List<Url> urls = criteria.list();
						Url urlFromDb = urls.size() > 0 ? urls.get(0) : null;
						//Url urlFromDb = session.get(Url.class, link);
						// TODO: optimize repetitions
						if(urlFromDb != null){
							
							Bookmark currentBookmark = new Bookmark(
									title, 
									urlFromDb, 
									UserUtils.getCurrentUser().getUser(), 
									VisibilityType.Private);
							session.save(currentBookmark);
							continue;
						} else {
							session.save(url);
						}

						Bookmark currentBookmark = new Bookmark(
								title, 
								url, 
								UserUtils.getCurrentUser().getUser(), 
								VisibilityType.valueOf(visibility));
						
						session.save(currentBookmark);
					}
				}
				// body
				tn.commit();
			} catch(Exception e){
				tn.rollback();
				System.out.println("ROOOLLLLLBAAAAACCCCKKKKKKK !");
				e.printStackTrace();
			} finally {
				System.out.println("SESSION CLOSSSSSSSSSSSEEEE !");
				session.close();
			}
			
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	private File uploadFile(HttpServletRequest request, MultipartFile file) {
		// Root Directory.
        String uploadRootPath = request.getSession().getServletContext().getRealPath("upload");
        File uploadRootDir = new File(uploadRootPath);

        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }

        // Client File Name
        String name = file.getOriginalFilename();
        File uploadedFile = null;
 
        if (name != null && name.length() > 0) {
            try {
                byte[] bytes = file.getBytes();
 
                // Create the file on server
                File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
 
                // Stream to write data to file in server.
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                uploadedFile = serverFile;
	        } catch (Exception e) {
	            System.out.println("Error Write file: " + name);
	            // TODO: Error handling
	        }
        }
		return uploadedFile;
	}


	/*@Override
	public List<Bookmark> experimentalSearch(String keyword) {
		
		List<Bookmark> result = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Bookmark.class);
		
		Criteria c1 = session.createCriteria(Bookmark.class).createCriteria("keywords");;
		//Criteria c2 = c1.createCriteria("keywords");
		Criterion criterion1 = Restrictions.like("keyword", keyword + "%");
		c1.add(criterion1);
		
		Criteria c3 = session.createCriteria(Bookmark.class).createCriteria("tags");
		//Criteria c4 = c3.createCriteria("tags");
		Criterion criterion2 = Restrictions.like("tag", keyword + "%");
		c3.add(criterion2);
		
		Criterion finalCriterion = Restrictions.or(criterion1, criterion1);
		
		
		result = c1.add(finalCriterion).list();
		
		tx.commit();
		session.close();
		
		
		return result;
	}*/
	
	/*@Override
	public List<Bookmark> experimentalSearch(String keyword) {
		
		List<Bookmark> result = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		

		Criteria criteria = session.createCriteria(Bookmark.class);
				.createAlias("tags", "tagsAlias")
				.createAlias("keywords", "keyordsAlias");
		
		Criterion tagCriterion = Restrictions.like("tagsAlias.tagName", keyword + "%").ignoreCase();
		Criterion keywordCriterion = Restrictions.like("keyordsAlias.keyword", keyword + "%").ignoreCase();
		Criterion titleCriterion = Restrictions.like("title", keyword + "%").ignoreCase();
		
		Criterion finalCriterion = Restrictions.or(titleCriterion, keywordCriterion, tagCriterion);

		criteria.add(finalCriterion);
		
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		result = criteria.list();
		
		tx.commit();
		session.close();
		
		
		return result;
	}*/
}
