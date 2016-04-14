package bg.jwd.bookmarks.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "Users")
public class User implements Serializable{

	private static final long serialVersionUID = -1818569599389892270L;

	@Id
	@GenericGenerator(name="usersGen" , strategy="increment")
	@GeneratedValue(generator="usersGen")
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "username", unique = true)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "emial", unique = true)
	private String email;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "address")
	private String address;
	
	@Type(type="yes_no")
	private boolean enabled;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="author")
	private List<Bookmark> bookmarks;
	
	@Transient
	private boolean isFollow;
	
	// Many Users has many Roles
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = {
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH
			})
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	private Set<Role> roles;
	
	// Many To Many
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
			})
	@JoinTable(
			name="users_tags",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
	private Set<Tag> tags;

	// Many Users has many Followers
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = {
			CascadeType.DETACH, 
			//CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH
			})
	@JoinTable(
			name = "users_followers",
			joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "follwing_id", referencedColumnName = "user_id"))
	private Set<User> followersCollection;
	
	// Many Users has many Following
	/*@ManyToMany(mappedBy = "followersCollection", cascade = {
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH
			})*/
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = {
			CascadeType.DETACH, 
			//CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH
			})
	@JoinTable(
			name = "users_followers",
			joinColumns = @JoinColumn(name = "follwing_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "user_id"))
	private Set<User> followingCollection;
	
	public User(){ }
	public User(
			String username, 
			String password, 
			String email, 
			String firstName, 
			String lastName, 
			String address ){
		this.username = username;
		this.password = password;
		this.email = email;
		this.creationDate = new Date();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.roles = new HashSet<Role>();
		this.bookmarks = new ArrayList<Bookmark>();
		this.followersCollection = new HashSet<>();
		this.followingCollection = new HashSet<>();
		this.tags = new HashSet<>();
		this.isFollow = false;
		this.enabled = true;
	}
	
	public boolean isAdmin(){
		List<String> roles = this.roles.stream().map(r -> r.getRoleName()).collect(Collectors.toList());
		return roles.contains("admin");
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<User> getFollowersCollection() {
		return followersCollection;
	}

	public void setFollowersCollection(Set<User> followersCollection) {
		this.followersCollection = followersCollection;
	}

	public Set<User> getFollowingCollection() {
		return followingCollection;
	}

	public void setFollowingCollection(Set<User> followingCollection) {
		this.followingCollection = followingCollection;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public boolean getIsFollow() {
		return isFollow;
	}
	
	public void setIsFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}
	
	// TODO: height quality override / null ??
	@Override
	public boolean equals(Object that) {
		
		User other = (User) that;
		return new Long(this.userId).equals(new Long(other.userId));
	}
	
	@Override
    public int hashCode() {
        return this.username.hashCode();
    }
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Tag> getTags() {
		return tags;
	}
	
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
}
