# Tracify – Landing Page & Android Dashboard (Public Repository)

This repository contains the **public modules** of the Tracify project:

- **Tracify Landing Page** → Full-stack website with secure registration  
- **Tracify Dashboard** → Android dashboard app (source code only)

**Note:** The ATS APK remains private and is not included here.

---

1. Tracify Landing Page (Full-Stack)

A modern, production-ready landing page built with a glassmorphism UI, secure credential system, and user onboarding workflow.

---

## Landing Page Preview

<p align="center">
  <img src="Tracifylandingpage/assets/landing.png" alt="Tracify Landing Page" width="800">
</p>

---

## Tech Stack (Landing Page)

### **Frontend**
- React (Vite)
- Vanilla CSS (Glassmorphism UI)
- Fetch API for backend communication

### **Backend**
- Node.js  
- Express.js  
- MongoDB + Mongoose  
- bcrypt (password hashing)

---

## Backend Setup

```bash
cd Tracifylandingpage/server
npm install

**Create .env**:

ini
Copy code
MONGO_URI=mongodb://localhost:27017/tracify
PORT=5000
Run backend:

bash
Copy code
node server.js
🎨 Frontend Setup
bash
Copy code
cd Tracifylandingpage/client
npm install
npm run dev
Frontend will run on:

arduino
Copy code
http://localhost:5173
Landing Page Features
Secure user registration

Live photo capture

Encrypted password creation

User profile with status

Protected ATS APK download (only after successful registration)

📱 2. Tracify Dashboard (Android App)
An Android dashboard app for registered Tracify users.
APK not included — only the app source code.

📸 Dashboard App Preview
<p align="center"> <img src="TracifyDashboard/assets/dashboard.png" alt="Tracify Dashboard App" width="800"> </p>
Replace dashboard.png with your actual app screenshot inside:
TracifyDashboard/assets/

🛠 Tech Stack (Android App)
Java / Kotlin

Android SDK

XML UI

Gradle Build System

⚙️ Build Instructions
Open project in Android Studio:

mathematica
Copy code
File → Open → TracifyDashboard/
Build:

mathematica
Copy code
Build → Make Project
Run → Run App
🧩 Repository Structure
css
Copy code
tracify-public/
│
├── Tracifylandingpage/
│     ├── client/   → React frontend
│     └── server/   → Node.js backend
│
└── TracifyDashboard/ → Android App Source Code
      ├── app/
      └── gradle/
🚀 Deployment Guide
Backend Deployment
Platforms:

Render

Railway

AWS / VPS

Any Node hosting

Set env variable:

ini
Copy code
MONGO_URI=<your-database-url>
Frontend Deployment
Recommended:

Vercel

Netlify

Cloudflare Pages

Build frontend:

bash
Copy code
npm run build
Deploy the dist/ folder.

🔒 Security Notes
ATS APK is not included in this public repo

APK download allowed only for verified users

All passwords are hashed using bcrypt

Backend validates user identity before access

🤝 Contribution
Pull requests are welcome.
For major changes, open an issue to discuss ideas.

👨‍💻 Author
Ankit Kumar
GitHub: https://github.com/Ankitkumarthakur0
