
# Hot pot management application
## 1. Preface
### 1.1. Inspiration
I've been ordered to build an application that utilizing DBMS, and we've decided to build a hot pot management application. The reason is that we are all big fans of hot pot, and we believe that a hot pot management application can be very useful for hot pot lovers.

I've taken a lot of preferences from the UI of KiotViet, an application that manage this in real life.
### 1.2. Technology
This app is building mainly by Java. 
- JavaFX: for the UI
- JDBC: for the connection to the database
- MySQL: for the database
- Maven: for the build tool
- Git: for the version control
- IntelliJ IDEA: for the IDE
- Scene Builder: for the UI design
- MySQL Workbench: for the database design
### 1.3 Database
The design and Sql statment is the work of all group not just my self. You can view the design here:
[ERD](https://drive.google.com/file/d/1xurWWLluZg9V0g4hCCQwojOTJ1R5oEPw/view?usp=share_link)

[Mapping](https://drive.google.com/file/d/1EUMETgpXjrrok_FOAyzvOBZnkNW9Df9J/view?usp=share_link)

## 2. Features
### 2.1. Implemented
- [x] Login
- [x] Logout
- [x] View the table list
- [x] View the menu
- [x] Create a receipt
- [x] View the receipt
- [x] Add product to the receipt
- [x] Remove product from the receipt
- [x] Find Customer and Employee
- [x] Add new Customer and Employee
- [x] Update Customer and Employee
- [x] View revenue report week ly
### 2.2. Things haven't been implemented
- [ ] Delete Customer and Employee
- [ ] Pay the receipt
- [ ] View the receipt detail
- [ ] View the receipt history
- [ ] View the receipt detail history
- [ ] View all the revenue report
- [ ] **Dockerize the application**
## 3. How to run
### 3.1. Prerequisites
- Java 21
- MySQL 8.0
- Maven 3.8.1
- JDBC 8.0.27 (although maven can handle this, but you should check it)
### 3.2. Run
1. Clone the repository
`bash git clone https://github.com/nhoxtin15/BTL_Database.git`
2. Open MySqlWorkBench and run the script in `SqlStatment/hocdatabaselao.sql`
3. Open the project in IntelliJ IDEA
4. Run the project by 'mvn clean javafx:run'
5. Login with the following account:
   - Username: `SEmployee` Password: `123456` for viewing table and cart
   - Username: `SManager` Password `123456` for viewing revenue report and employee

## 4. Screenshots
### 4.1. Login
![Login](https://github.com/nhoxtin15/BTL_Database/blob/main/Screenshot/Login.png)
### 4.2 Main page
![MainPage](https://github.com/nhoxtin15/BTL_Database/blob/main/Screenshot/MainPage.png)
![Menu](https://github.com/nhoxtin15/BTL_Database/blob/main/Screenshot/Menu.png)
![Receipt](https://github.com/nhoxtin15/BTL_Database/blob/main/Screenshot/Receipt.png)
### 4.3 Check out
![CheckOut](https://github.com/nhoxtin15/BTL_Database/blob/main/Screenshot/CheckOut.png)
![CustomerChooser](https://github.com/nhoxtin15/BTL_Database/blob/main/Screenshot/CustomerChooser.png)
![CustomerRegisteration](https://github.com/nhoxtin15/BTL_Database/blob/main/Screenshot/CustomerRegisteration.png)
### 4.4 Manager site
![ManagerSite](https://github.com/nhoxtin15/BTL_Database/blob/main/Screenshot/ManagerSite.png)

