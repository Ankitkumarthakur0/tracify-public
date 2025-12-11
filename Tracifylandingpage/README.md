# Tracify – Landing Page (Full-Stack)

A production-ready, full-stack landing page for Tracify, featuring a modern glassmorphism UI, secure user onboarding, and restricted APK access based on registration status.

A production-ready landing page for Tracify, featuring a glassmorphism UI, user registration, and secure credential creation.

## Tech Stack
- **Frontend**: React + Vite + Vanilla CSS (Glassmorphism)
- **Backend**: Node.js + Express + MongoDB
- **Database**: MongoDB (Mongoose)

## Project Structure
- `client/`: React frontend application.
- `server/`: Node.js backend API.

## Setup Instructions

### 1. Backend Setup
1. Navigate to the `server` directory:
   ```bash
   cd server
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Create a `.env` file (already created) and ensure your MongoDB URI is correct:
   ```env
   MONGO_URI=mongodb://localhost:27017/tracify
   PORT=5000
   ```
4. Start the server:
   ```bash
   node server.js
   ```

### 2. Frontend Setup
1. Navigate to the `client` directory:
   ```bash
   cd client
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

## Deployment

### Backend
- Deploy the `server` directory to a Node.js hosting provider (Render, Heroku, Railway).
- Set `MONGO_URI` in the provider's environment variables.

### Frontend
- Deploy the `client` directory to a static host (Vercel, Netlify).
- Update the API calls in `client/src` to point to your production backend URL.

## Features
- **Registration**: Captures user details and live photo.
- **Credentials**: Secure password creation (hashed).
- **Downloads**: ATS APK is locked until registration is complete.
- **Profile**: View user details and download status.

## Contact
- **Developer**: Ankit Kumar
- **GitHub**: https://github.com/Ankitkumarthakur0
