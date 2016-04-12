package bg.jwd.bookmarks.scrap;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	File uploadFile(HttpServletRequest request, MultipartFile file);
	
	void importFile(HttpServletRequest request, MultipartFile file, String visibility);
}
