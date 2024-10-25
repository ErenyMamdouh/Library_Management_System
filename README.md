# 📚 Library Management System

Welcome to the Library Management System! This application provides a set of RESTful APIs for managing library resources, including books, patrons, and borrowing records. The system supports CRUD operations (Create, Read, Update, Delete) for managing books and patrons, as well as borrowing and returning books.

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Spring Boot 2.5+
- MySQL Database
- Maven or Gradle

### Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/library-management-system.git
    ```
2. **Navigate to the project directory**:
    ```bash
    cd library-management-system
    ```
3. **Configure the database**:
   - Update `application.properties` with your MySQL database credentials.
   - Run the following SQL script to create the necessary tables:
    ```sql
    CREATE DATABASE library_management;
    ```

4. **Build the project**:
    ```bash
    mvn clean install
    ```
5. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

## 🔖 API Overview

### Book API
The Book API provides endpoints for managing books in the library.

- **Add a Book**: Create a new book record.
- **Get a Book by ID**: Retrieve details of a specific book.
- **Get All Books**: Retrieve a list of all books.
- **Update a Book**: Edit details of an existing book.
- **Delete a Book**: Remove a book record.

### Patron API
The Patron API allows for managing patrons who borrow books.

- **Add a Patron**: Register a new patron.
- **Get a Patron by ID**: Retrieve details of a specific patron.
- **Get All Patrons**: Retrieve a list of all registered patrons.
- **Update a Patron**: Edit details of an existing patron.
- **Delete a Patron**: Remove a patron record.

### Borrow API
The Borrow API provides endpoints to manage the borrowing and returning of books.

- **Borrow a Book**: Allows a patron to borrow a book, creating a new borrowing record.
- **Return a Book**: Records the return of a borrowed book and updates the borrowing record.

### Book API

- **Endpoint**: `/api/books/add`
  - **Method**: `POST`
  - **Description**: Add a new book to the library.
  - **Request Body**:
    ```json
    {
      "title": "Effective Java",
      "author": "Joshua Bloch",
      "isbn": "9780134685991",
      "publicationDate": "2018-01-06"
    }
    ```
  - **Response**: `201 Created`
  - **Response Body**:
    ```json
    {
      "id": 1,
      "title": "Effective Java",
      "author": "Joshua Bloch",
      "isbn": "9780134685991",
      "publicationDate": "2018-01-06"
    }
    ```

- **Endpoint**: `/api/books/get/{id}`
  - **Method**: `GET`
  - **Description**: Retrieve a book by its ID.
  - **Response**: `200 OK`
  - **Response Body**:
    ```json
    {
      "id": 1,
      "title": "Effective Java",
      "author": "Joshua Bloch",
      "isbn": "9780134685991",
      "publicationDate": "2018-01-06"
    }
    ```

### Patron API

- **Endpoint**: `/api/patrons/add`
  - **Method**: `POST`
  - **Description**: Register a new patron.
  - **Request Body**:
    ```json
    {
      "fullName": "John Doe",
      "email": "john.doe@example.com",
      "phoneNumber": "123-456-7890",
      "address": "123 Main St"
    }
    ```
  - **Response**: `201 Created`
  - **Response Body**:
    ```json
    {
      "id": 1,
      "fullName": "John Doe",
      "email": "john.doe@example.com",
      "phoneNumber": "123-456-7890",
      "address": "123 Main St"
    }
    ```

- **Endpoint**: `/api/patrons/get/{id}`
  - **Method**: `GET`
  - **Description**: Retrieve a patron by their ID.
  - **Response**: `200 OK`
  - **Response Body**:
    ```json
    {
      "id": 1,
      "fullName": "John Doe",
      "email": "john.doe@example.com",
      "phoneNumber": "123-456-7890",
      "address": "123 Main St"
    }
    ```

### Borrow API

- **Endpoint**: `/api/borrow/{bookId}/patron/{patronId}`
  - **Method**: `POST`
  - **Description**: Borrow a book by specifying the book ID and patron ID.
  - **Response**: `201 Created`
  - **Response Body**:
    ```json
    {
      "borrowId": 1,
      "bookId": 1,
      "patronId": 1,
      "borrowDate": "2024-08-15",
      "dueDate": "2024-08-29"
    }
    ```

- **Endpoint**: `/api/return/{bookId}/patron/{patronId}`
  - **Method**: `PUT`
  - **Description**: Return a borrowed book by specifying the book ID and patron ID.
  - **Response**: `200 OK`
  - **Response Body**:
    ```json
    {
      "borrowId": 1,
      "bookId": 1,
      "patronId": 1,
      "borrowDate": "2024-08-15",
      "dueDate": "2024-08-29",
      "returnDate": "2024-08-22"
    }
    ```

## 🛠️ Technologies Used

- **Java 11**
- **Spring Boot**
- **Hibernate / JPA**
- **MySQL**
- **Maven**

## 👥 Contributing

We welcome contributions! Please fork the repository and submit a pull request with your changes.

## 📞 Contact

If you have any questions or need support, feel free to reach out to us at erenymamdouh222@gmail.com.
