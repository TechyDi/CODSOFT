# CODSOFT Java Development Internship Tasks

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Java_Swing-GUI-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

## 📌 Overview
This repository contains the source code for three Java Desktop Applications developed during my **Java Development Internship** at **CodSoft**. 

The goal of these projects was to demonstrate proficiency in **Object-Oriented Programming (OOP)**, **GUI Development (Swing/AWT)**, and **Data Persistence**. Each application features a custom-designed user interface, robust input validation, and event-driven logic.

---

## 📂 Project Structure

| Task | Project Name | Description | Key Tech Stack |
| :--- | :--- | :--- | :--- |
| **Task 1** | [Student Grade Calculator](#-task-1-student-grade-calculator) | Calculates total marks, average percentage, and assigns grades based on user input. | `Swing`, `GridLayout` |
| **Task 2** | [ATM Interface](#-task-2-atm-interface) | Simulates an ATM with Deposit, Withdraw, and Check Balance functionalities using OOP principles. | `Swing`, `OOP Encapsulation` |
| **Task 3** | [Student Management System](#-task-3-student-management-system) | A complete CRUD application with file persistence, responsive design, and data tables. | `Serialization`, `GridBagLayout`, `IO` |

---

## 🎓 Task 1: Student Grade Calculator
A clean, GUI-based application that automates the calculation of academic results.

### ✨ Key Features
* **Custom UI Design:** Replaced standard Java styling with a "Steel Blue" header and custom button colors (Emerald Green for calculation, Alizarin Red for clearing).
* **Input Validation:** Prevents crashes by validating non-numeric inputs and ensuring marks fall within the 0-100 range.
* **Dynamic Grading Logic:** automatically assigns grades (A-F) based on the calculated average percentage.
* **Result Display:** Shows Total Marks, Average %, and Grade with color-coded feedback (e.g., "F" Grade appears in Red).

---

## 💳 Task 2: ATM Interface
A banking simulation that strictly separates the **User Interface** from the **Business Logic**.

### ✨ Key Features
* **OOP Architecture:**
    * `ATMSystem.java`: Handles the GUI and user interactions.
    * `BankAccount.java`: A separate class that encapsulates the balance and performs logic validation (Deposit/Withdraw).
* **Modern "Kiosk" Design:** Features a dark navy theme, hover effects on buttons, and a digital-style balance display.
* **Real-time Feedback:** Status updates appear instantly on the dashboard without annoying popup boxes for every transaction.
* **Transaction Safety:** Prevents withdrawing more than the available balance or entering negative amounts.

---

## 🏫 Task 3: Student Management System (Capstone Project)
A robust desktop application for managing student records. This project demonstrates advanced Java concepts including **File I/O**, **Layout Managers**, and **UX Design**.

### 🚀 Technical Highlights
* **Data Persistence (Serialization):** unlike simple text files, this app saves `Student` objects directly to a `.dat` file. Data persists even after the application is closed.
* **Responsive Layout (`GridBagLayout`):** The interface adapts to different screen sizes. Input fields stretch and shrink dynamically.
* **Scrollable Interface:** Implemented `JScrollPane` for the sidebar, ensuring the application remains usable even on small / low-resolution screens.
* **Smart Placeholders:** Custom `FocusListener` logic provides modern "ghost text" (e.g., *"Enter Roll No"*) that disappears when the user clicks the field.
* **Interactive Table:** Clicking a row in the data table automatically populates the form fields for quick editing.

### 🛠 Functionalities
* **Create:** Add new students with unique Roll Numbers (ID validation included).
* **Read:** View all students in a structured table view.
* **Update:** Edit existing student details seamlessly.
* **Delete:** Remove records with a confirmation dialog.

---

## ⚙️ Setup & Installation

To run these projects locally, ensure you have **Java Development Kit (JDK) 8** or higher installed.

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/your-username/CODSOFT.git](https://github.com/your-username/CODSOFT.git)
    cd CODSOFT
    ```

2.  **Compile & Run (Example for Task 3):**
    Navigate to the project folder and run the following commands in your terminal:
    ```bash
    javac Student.java StudentManagement.java
    java StudentManagement
    ```

---

## 🛡 License
This project is licensed under the **MIT License**.

---

### 👨‍💻 Author
**Dinesh Kumar Mohanta** *Aspiring Software Engineer & Java Developer* [LinkedIn Profile](https://www.linkedin.com/in/dineshkmohanta) | [GitHub Profile](https://github.com/TechyDi)
