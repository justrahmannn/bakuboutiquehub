# 🛍️ BakuBoutiqueHub

Welcome to **BakuBoutiqueHub**, a modern platform connecting boutiques with customers in Baku. This project consists of a high-performance **Android application** and a robust **Spring Boot backend**.

---

## 🚀 Key Features

- **🔐 Secure Authentication:** User registration and login using JWT (JSON Web Tokens).
- **👤 User Profiles:** Manage personal information and boutique preferences.
- **🏬 Boutique Management:** (In progress) Explore and manage boutique listings.
- **🛰️ Seamless Integration:** Real-time communication between Android and Backend via Retrofit.

---

## 🛠️ Technology Stack

### **Android Client**
- **Language:** Kotlin
- **Networking:** Retrofit & OkHttp
- **Architecture:** MVVM (Model-View-ViewModel)
- **DI:** Hilt (Dependency Injection)
- **Serialization:** Gson

### **Spring Boot Backend**
- **Language:** Java
- **Security:** Spring Security & JWT
- **Database:** JPA/Hibernate (configured for H2 development)
- **Build Tool:** Maven

---

## 📂 Project Structure

```text
bakuboutiquehub/
├── 📱 app/          # Android application module
├── ⚙️ backend/      # Spring Boot backend application
├── 📦 build.gradle  # Project-level build configuration
└── 📄 README.md     # Project documentation
```

---

## 🚦 Getting Started

### **1. Run the Backend**
1. Navigate to `backend/`.
2. Run `./mvnw spring-boot:run` or run `BakuboutiquehubApplication.java` from your IDE.
3. The server will start on `http://localhost:8080`.

### **2. Run the Android App**
1. Open the project in **Android Studio**.
2. Start an **Android Emulator**.
3. Run the `app` module.
4. Note: The app is pre-configured to use `10.0.2.2:8080` to connect to your local machine from the emulator.

---

## 🧪 Testing

To verify the integration:
1. Start the backend.
2. Open **Logcat** in Android Studio.
3. Filter by `OkHttp` to see network requests and responses (e.g., login, sign up).

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---
*Developed with ❤️ in Baku.*
