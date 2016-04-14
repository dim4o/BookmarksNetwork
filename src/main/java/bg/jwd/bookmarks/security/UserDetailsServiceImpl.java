package bg.jwd.bookmarks.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import bg.jwd.bookmarks.entities.Role;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.servises.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CurrentUser resultUser = null;
		
		try {
			User user = userService.getByProperty("username", username);
			if(user == null){
				return null;
			}
			Set<GrantedAuthority> authorities = getUserAuthorities(user);
			resultUser = new CurrentUser(user, authorities);

			return resultUser;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultUser;
	}
	
	private Set<GrantedAuthority> getUserAuthorities(User user){
		Set<GrantedAuthority> authorities = new HashSet<>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			SimpleGrantedAuthority currentRole = new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase());
			authorities.add(currentRole);
		}
		
		return authorities;
	}
}
