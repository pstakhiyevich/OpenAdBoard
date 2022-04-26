### Description
**OpenAdBoard** is a marketplace platform where anyone can post classified advertisements within various categories and cities.

The platform supports three languages: English, Chinese, and Russian.

------------
### Project stack
Jakarta EE / MySQL / HTML5 / CSS3 / Bootstrap 5 / JavaScript

------------
The platform includes the following roles and their corresponded functionality:

|                                       | Guest | User | Moder | Admin |
|---------------------------------------|-------|------|-------|-------|
| View/Search items                     |   *   |   *  |   *   |   *   |
| Sign in/up                            |   *   |   *  |   *   |   *   |
| Sign out                              |       |   *  |   *   |   *   |
| Change own account email and password |       |   *  |   *   |   *   |
| Add items                             |       |   *  |   *   |   *   |
| Edit/Delete own items                 |       |   *  |   *   |   *   |
| View/Add/Delete own bookmarks         |       |   *  |   *   |   *   |
| View comments                         |       |   *  |   *   |   *   |
| Add/Delete own comments               |       |   *  |   *   |   *   |
| View contact info on an item page     |       |   *  |   *   |   *   |
| View user pages                       |       |   *  |   *   |   *   |
| Delete comments                       |       |      |   *   |   *   |
| Manage categories                     |       |      |   *   |   *   |
| Manage cities                         |       |      |   *   |   *   |
| Manage users                          |       |      |       |   *   |


------------
### Database diagram
![model](https://user-images.githubusercontent.com/42889643/165318338-705e1e66-7f78-42e9-8589-90fa2b1d66b6.png)

------------
### Usage
1. Clone the project
2. Create a new MySQL database using schema.sql from the data folder
3. Populate the database with randomly generated data using data.sql from the data folder
4. Fill up any valid email service's user and password in the mail.properties file, located in the resources/config/ folder, for email activation functionality 
5. Change the database.properties file, located in the resources/config/ folder, based on your database configurations
6. Build the project using gradle
7. Add new Tomcat 10.0.12 configuration to the project
8. Run Tomcat and open http://localhost:8080/ on the browser
9. For admin and moder functionality testing use one of the following accounts:
    Admin account: admin@admin.com password: aaaAAA111
    Moder account: moder@moder.com password: aaaAAA111

----------------
### Screenshots of the app
![image](https://user-images.githubusercontent.com/42889643/165316920-d77679bc-6ddf-40a4-b56b-e4ebf2228723.png)


![image](https://user-images.githubusercontent.com/42889643/165316697-0102d07d-2b25-4a66-a1f7-bbb2acb41e8b.png)