# 🚀 Java Development Internship Portfolio | CodSoft

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Java_Swing-GUI-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active_Development-brightgreen?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

## 📌 Overview
Welcome to my collection of enterprise-grade Java Desktop Applications developed during my **Java Development Internship** at **CodSoft**. 

My goal with this repository is to push the boundaries of standard **Java Swing** development. These projects demonstrate not just core logic, but advanced **UI/UX patterns**, **Custom Renderers**, **Data Persistence**, and **Event-Driven Architecture**.

> *"I don't just write code that runs; I build interfaces that users actually want to use."*

---

## 📂 Project Structure

| Task | Project Name | Description | Key Tech Stack |
| :--- | :--- | :--- | :--- |
| **Task 1** | [Student Grade Calculator](#-task-1-student-grade-calculator) | An automated academic assessment tool with dynamic logic and error handling. | `Swing`, `Input Validation` |
| **Task 2** | [ATM Interface](#-task-2-atm-interface) | A banking simulation focusing on secure transaction logic and OOP encapsulation. | `OOP`, `Event Handling` |
| **Task 3** | [Student Management System](#-task-3-student-management-system) | **(Capstone)** A feature-rich CRUD CRM with live search, highlighting, and bulk operations. | `Serialization`, `Custom Renderers`, `Filters` |

---

## 🎓 Task 1: Student Grade Calculator
A clean, GUI-based application designed to automate the academic grading process with precision.

### ✨ Key Features
* **Custom UI Design:** Replaced standard Java styling with a "Steel Blue" header and semantic button colors (Emerald Green for calculation, Alizarin Red for clearing).
* **Robust Validation:** Prevents crashes by intercepting non-numeric inputs and ensuring marks fall within the 0-100 range.
* **Dynamic Grading:** Automatically assigns grades (A-F) based on weighted averages.
* **Visual Feedback:** Results are color-coded (e.g., failing grades appear in Red) to provide immediate visual context.

---

## 💳 Task 2: ATM Interface
A secure banking simulation that strictly separates the **User Interface** from the **Business Logic**.

### ✨ Key Features
* **MVC-Style Architecture:**
    * `ATMSystem.java`: Handles the View (GUI) and Controller logic.
    * `BankAccount.java`: The Model that encapsulates sensitive balance data and performs logic validation.
* **Modern Kiosk UI:** Features a dark navy theme, hover effects on buttons, and a digital-style balance display.
* **Transaction Safety:** Implements strict checks to prevent overdrawing or negative deposits.

### 🔮 Future Roadmap (Coming Soon)
- [ ] **Security Layer:** Implementing PIN authentication (Hashed) before accessing the dashboard.
- [ ] **Transaction History:** Adding a "Mini Statement" feature to view the last 5 transactions.
- [ ] **Multi-User Support:** allowing different account numbers to log in with distinct balances.

---

## 🏫 Task 3: Student Management System (Capstone Project)
*The crown jewel of this portfolio.* This is not just a form; it is a fully interactive data management dashboard. It demonstrates advanced Swing capabilities like **Custom Cell Renderers**, **Document Filters**, and **Live Filtering**.

### 🚀 Technical Highlights
* **Live Search with Highlighting:** As you type, the table filters in real-time. Matching text inside the table is **highlighted in yellow**, mimicking the "Find in Page" behavior of modern browsers.
* **Smart Data Persistence:** Uses `Java Serialization` to store objects directly to a `.dat` file. Data survives application restarts.
* **Strict Input Normalization:** A custom `DocumentFilter` forces Grade inputs to be uppercase (A-F) and rejects invalid characters before they appear on the screen.
* **Empty-State Dashboard:** To simulate real-world app performance, data is loaded in the background but the table starts empty until the user searches or clicks "Show All."

### 🛠 Functionalities
* **Create:** Add new students with auto-validating Roll Numbers.
* **Read:** Live search by Name or Roll ID, or view the full database.
* **Update:** Click any row to auto-populate the form for editing.
* **Delete:** Remove any specific record with a confirmation dialog.

### 🔮 Future Roadmap (Coming Soon)
- [ ] **Authentication System:** Adding a Login Screen with "Admin" (Full Access) and "Guest" (Read-Only) roles.
- [ ] **Gmail-Style Bulk Actions:** The table features a checkbox column allowed for selecting and deleting multiple records simultaneously.
- [ ] **Database Integration:** Migrating from `.dat` files to **MySQL/SQLite** for scalable storage.
- [ ] **Report Generation:** A feature to export the student list to a **PDF** or **CSV** file.

---

## ⚙️ Setup & Installation

To run these projects locally, ensure you have **Java Development Kit (JDK) 8** or higher installed.

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/TechyDi/CODSOFT.git](https://github.com/TechyDi/CODSOFT.git)
    cd CODSOFT
    ```

2.  **Compile & Run (Example for Task 3):**
    Navigate to the project folder and run:
    ```bash
    javac Student.java StudentManagement.java
    java StudentManagement
    ```

---

## 🤝 Connect & Collaborate
I am always open to discussing **Java Engineering**, **Backend Development**, and **Software Architecture**. 

If you find this code useful or want to collaborate on the "Future Roadmap" features, let's connect!

<p align="left">
  <a href="https://www.linkedin.com/in/dineshkmohanta">
    <img src="https://img.shields.io/badge/LinkedIn-Connect-blue?style=for-the-badge&logo=linkedin" alt="Connect on LinkedIn" />
  </a>
  <a href="mailto:dineshkmohanta@gmail.com">
    <img src="https://img.shields.io/badge/Email-Contact_Me-red?style=for-the-badge&logo=gmail" alt="Email Me" />
  </a>
</p>

---

### 👨‍💻 Author
**Dinesh Kumar Mohanta** *Final Year B.Tech CSE | Java Developer | Engineering Enthusiast* [GitHub Profile](https://github.com/TechyDi)