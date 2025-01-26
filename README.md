# E-commerce Application 🛒

This is a comprehensive Java-based E-commerce application built to simulate an online shopping platform. It includes features for user management, product management, cart management, and order processing. The application utilizes Java Servlets and JSP for backend processing, with a MySQL database for data persistence.

---

![Image](https://github.com/user-attachments/assets/5d87dce9-af74-4ee4-a277-50d6e691c7f9)

---

## Features ✨

### User Management 👥
- Register as a new user.
- Login and logout functionality.
- Role-based access control (Admin 👨‍💼 and Customer 👩‍💻).
- Admins can activate or deactivate users.

### Product Management 🛍️
- Admins can add, edit, and remove products.
- Customers can view product listings.

### Shopping Cart 🛒
- Add products to the cart.
- Update product quantities in the cart (increase/decrease).
- Remove products from the cart.

### Order Processing 📦
- Place orders from the cart.
- View order history.

### Admin Dashboard 📊
- Manage users.
- Manage product inventory.

---

## Technologies Used 💻

### Frontend 🎨
- **HTML**: For structure.
- **CSS**: For styling.
- **JSP**: For dynamic rendering of pages.

### Backend 🔧
- **Java Servlets**: For handling business logic.
- **JDBC**: For database interaction.

### Database 🗄️
- **MySQL**: For data persistence.

---

## Getting Started 🚀

### Prerequisites 📋
- Java JDK 8 or higher
- Apache Tomcat (version 9 recommended)
- MySQL Server
- Maven (for dependency management)

### Installation 🛠️

1. Clone the repository:
   ```bash
   git clone https://github.com/chanukaprabodha/E-commerce-Application.git
   ```

2. Open the project in your preferred IDE (e.g., IntelliJ IDEA or Eclipse).

3. Set up the database:
   - Import the `ecommerce.sql` file located in the `database` folder.
   - Update the database connection details in the `db.properties` file:
     ```properties
     db.url=jdbc:mysql://localhost:3306/ecommerce
     db.username=root
     db.password=yourpassword
     ```

4. Build the project using Maven:
   ```bash
   mvn clean install
   ```

5. Deploy the application to Apache Tomcat:
   - Copy the generated WAR file from the `target` directory to the `webapps` folder of your Tomcat installation.

6. Start the Tomcat server and access the application:
   - Visit `http://localhost:8080/E-commerce-Application` in your browser.

---

## Usage 🖱️

### Admin 👨‍💼
1. Log in using admin credentials.
2. Access the admin dashboard to manage users and products.

### Customer 👩‍💻
1. Register or log in as a customer.
2. Browse available products and add them to the cart.
3. Proceed to checkout and place an order.

---

## Project Structure 📂

```
E-commerce-Application
├── src
│   ├── main
│   │   ├── java
│   │   │   └── lk/ijse
│   │   │       ├── servlet    # Servlets for handling requests
│   │   │       ├── dao        # Data Access Objects
│   │   │       ├── entity     # Entity classes
│   │   │       └── dto        # Data Transfer Objects
│   │   ├── resources
│   │   │   └── db.properties  # Database configuration
│   │   └── webapp
│   │       ├── css            # Stylesheets
│   │       ├── js             # JavaScript files
│   │       ├── jsp            # JSP files
│   │       └── index.jsp      # Entry point
├── database
│   └── ecommerce.sql          # Database schema and sample data
├── pom.xml                    # Maven configuration file
└── README.md                  # Project documentation
```

### Database Setup 💾
- The SQL file for the database schema and sample data is located in the `resources` folder:
  [ecommerce.sql](src/main/resources/ecommerce.sql)

---

## Screenshots 📸
Add screenshots of your application here to showcase the user interface and features.

1. **Landing Page**
   ![Image](https://github.com/user-attachments/assets/5d87dce9-af74-4ee4-a277-50d6e691c7f9)

2. **Admin Dashboard**
   ![Image](https://github.com/user-attachments/assets/7c90919d-0e39-498c-b5de-1a77d91ffc5b)

3. **Login Page**
   ![Image](https://github.com/user-attachments/assets/c05c2301-a6a5-4f2b-9633-e08cec655c9b)

---

## Contributors 🤝
- **Chanuka Prabodha** - Developer

---

## License 📜
This project is licensed under the [MIT License](LICENSE).
