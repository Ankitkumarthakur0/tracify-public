const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const userRoutes = require('./routes/userRoutes');

dotenv.config();

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(cors());
app.use(bodyParser.json({ limit: '50mb' })); // Increased limit for base64 images
app.use(bodyParser.urlencoded({ limit: '50mb', extended: true }));

// Database Connection
let USE_MOCK_DB = true; // FORCE MOCK DB FOR NOW

// Make flag available to routes - MUST BE BEFORE ROUTES
app.use((req, res, next) => {
    req.USE_MOCK_DB = true; // Explicitly set to true
    next();
});

// Routes
app.use('/api', userRoutes);

// Root Route
app.get('/', (req, res) => {
    res.send(`Tracify Backend Running (Mode: In-Memory)`);
});

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
