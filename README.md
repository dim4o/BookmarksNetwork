# Bookmarks Network
Everyone can register to the system and can search from bookmarks in the system. Registered Users can create and import their own bookmarks and interact with other users, follow/unfollow, and visit their profiles.
## 1.	Guest
a.	Can search for bookmarks (combine search by title, keyword, tag and description)
b.	Can register profile
c.	Can login if registered
2.	User
a.	User can follow/unfollow another user
b.	User can change his/her profile
c.	User can visit other user profile and view other user public bookmarks with pagination
d.	User can have own list of bookmarks with pagination
e.	User can import/upload bookmarks to the system from outer bookmarks file (.*html)
f.	User can vote positive/negative to other users’s bookmarks
3.	Admin
a.	Can view list of all users from the system
b.	Can view each user profile
c.	Can add/remove role to/from other users (except him/herself)
d.	Can remove role from each user (except role “User” and his/her own role)
e.	Can disable/enable another user
f.	Can search user by username, email, first, and last name
g.	Can add user with specific role(user/admin) and status(enabled/disabled)
h.	Can edit/delete public bookmarks only
4.	Roles
a.	User – default role. Can not be removed.
b.	Admin – the administrator account.
5.	Bookmark
a.	Can have visibility – private and public
b.	Can be created with title, link, keywords, tags, description
c.	Can be searched by criteria – title, keyword, tag, description
d.	Can be edited from its owner
e.	Can be deleted from its owner(or admin the bookmark is public)
6.	Relations
a.	Bookmark has many Keywords associated
b.	Bookmark has many tags associated
c.	Bookmark has Description, Title, Author, Url
d.	Url has many bookmarks associated
e.	User has many Followers
f.	User has many Followings
g.	User has many Bookmarks