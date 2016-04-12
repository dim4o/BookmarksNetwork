package bg.jwd.bookmarks.scrap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
//import javax.transaction.Transaction;
//import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import bg.jwd.bookmarks.dao.BookmarkDao;
import bg.jwd.bookmarks.dao.UrlDao;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.entities.Url;
import bg.jwd.bookmarks.enums.VisibilityType;
import bg.jwd.bookmarks.util.UserUtils;

//@Transactional
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	SessionFactory sessionFactrory;
	
	@Autowired
	private UrlDao urDao;
	
	@Autowired
	private BookmarkDao bookmarkDao;
	
	@Override
	public File uploadFile(HttpServletRequest request, MultipartFile file) {
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

	@Transactional
	@Override
	public void importFile(HttpServletRequest request, MultipartFile file, String visibility) {
		File uploadedFile = this.uploadFile(request, file);

		String html = null;
		int count = 0;
        //try {
		try{
			html = Files.toString(uploadedFile, Charsets.UTF_8);}
		catch(Exception e){
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBCCCCCCCCCCCC");
			e.printStackTrace();
		}
			Document doc = Jsoup.parse(html);
			
			Elements links = doc.select("a[href]");
			List bookmarks = new ArrayList<Bookmark>();
			
			/*Transaction tn = null;
			Session session = this.sessionFactrory.openSession();*/
			
			/*try {
				tn = session.beginTransaction();*/
				// body
				for (Element element : links) {
					String link = element.attr("href");
					String title = element.text();
					if(link != null && !link.isEmpty() && title != null && !title.isEmpty()){
						Url url = new Url(link);
						Url urlFromDb = urDao.getByProperty("link", link);
						if(urlFromDb != null){
							continue;
						} else {
							urDao.add(url);
							
						}
						/*count++;
						if(count == 10){
							throw new Exception("NEW EXCEPTION---");
						}*/
						
						Bookmark currentBookmark = new Bookmark(
								title, 
								url, 
								UserUtils.getCurrentUser().getUser(), 
								VisibilityType.valueOf(visibility));
						
						bookmarkDao.add(currentBookmark);
					}
				}
				// body
			/*	tn.commit();
			} catch(Exception e){
				tn.rollback();
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBCCCCCCCCCCCC");
				e.printStackTrace();
			} finally {
				System.out.println("SESSION CLOSSSSSSSSSSS");
				session.close();
			}*/
			
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
