# Bookmarks Network
Everyone can register to the system and can search from bookmarks in the system. Registered Users can **create and import** their own bookmarks and interact with other users, **follow/unfollow**, and visit their profiles.
## 1.	Guest
-	Can **search** for bookmarks (combine search by title, keyword, tag and description)
-	Can **register** profile
-	Can **login** if registered

## 2.	User
- User can **follow/unfollow** another user
- User can **change** his/her profile
- User can **visit** other user profile and view other user public bookmarks with **pagination**
- User can have own list of **bookmarks** with **pagination**
- User can **import/upload** bookmarks to the system from outer bookmarks file (.*html)
- User can **vote** positive/negative to other users’s bookmarks

## 3.	Admin
- Can view list of **all users** from the system
- Can view each user **profile**
- Can **add/remove role** to/from other users (except him/herself)
- Can remove role from each user (**except role "User" and his/her own role**)
- Can **disable/enable** another user
- Can **search** user by username, email, first, and last name
- Can **add user** with specific role(user/admin) and status(enabled/disabled)
- Can **edit/delete** public bookmarks only

## 4.	Roles
- **User** – default role. Can not be removed.
- **Admin** – the administrator account.

## 5.	Bookmark
- Can have **visibility** – private and public
- Can be **created** with **title, link, keywords, tags, description**
- Can be **searched** by criteria – **title, keyword, tag, description**
- Can be **edited** from its owner
- Can be **deleted** from its owner(or admin the bookmark is public)

## 6.	Relations
- Bookmark **has many** Keywords associated
- Bookmark **has many** tags associated
- Bookmark **has** Description, Title, Author, Url
- Url **has many** bookmarks associated
- User **has many** Followers
- User **has many** Followings
- User **has many** Bookmarks
