const express = require('express');
const router = express.Router();
const User = require('../models/User');
const bcrypt = require('bcryptjs');

// In-Memory Storage
let mockUsers = [];

// Helper to simulate DB operations
const mockDB = {
    findOne: (query) => {
        if (query.email) return mockUsers.find(u => u.email === query.email);
        if (query.username) return mockUsers.find(u => u.username === query.username);
        return null;
    },
    findById: (id) => mockUsers.find(u => u._id === id),
    save: (user) => {
        user._id = Date.now().toString(); // Simple ID
        user.createdAt = new Date();
        user.updatedAt = new Date();
        mockUsers.push(user);
        return user;
    },
    findByIdAndUpdate: (id, update, options) => {
        const userIndex = mockUsers.findIndex(u => u._id === id);
        if (userIndex === -1) return null;

        let user = mockUsers[userIndex];
        if (update.username) user.username = update.username;
        if (update.passwordHash) user.passwordHash = update.passwordHash;
        if (update.$set) {
            if (update.$set['downloads.atsDownloaded']) {
                if (!user.downloads) user.downloads = {};
                user.downloads.atsDownloaded = true;
            }
            if (update.$set['downloads.appDownloaded']) {
                if (!user.downloads) user.downloads = {};
                user.downloads.appDownloaded = true;
            }
        }
        user.updatedAt = new Date();
        mockUsers[userIndex] = user;
        return user;
    }
};

// 1. Register User (Step 1)
router.post('/register', async (req, res) => {
    try {
        const { name, phone, altWhatsapp, email, aadhar, address, imei1, imei2, photoUrl } = req.body;

        // Check if user already exists
        let existingUser;
        if (req.USE_MOCK_DB) {
            existingUser = mockDB.findOne({ email });
        } else {
            existingUser = await User.findOne({ email });
        }

        if (existingUser) {
            return res.status(400).json({ message: 'User with this email already exists' });
        }

        if (req.USE_MOCK_DB) {
            const newUser = {
                name, phone, altWhatsapp, email, aadhar, address, imei1, imei2, photoUrl,
                downloads: { atsDownloaded: false, appDownloaded: false }
            };
            const savedUser = mockDB.save(newUser);
            return res.status(201).json({ message: 'Registration successful (Mock DB)', userId: savedUser._id });
        } else {
            const newUser = new User({
                name, phone, altWhatsapp, email, aadhar, address, imei1, imei2, photoUrl
            });
            const savedUser = await newUser.save();
            return res.status(201).json({ message: 'Registration successful', userId: savedUser._id });
        }
    } catch (error) {
        console.error('Registration Error:', error);
        res.status(500).json({ message: 'Server error during registration' });
    }
});

// 2. Create Credentials (Step 2)
router.post('/create-credentials', async (req, res) => {
    try {
        const { userId, username, password } = req.body;

        if (!userId || !username || !password) {
            return res.status(400).json({ message: 'Missing required fields' });
        }
        const passwordHash = await bcrypt.hash(password, 10);

        let updatedUser;
        if (req.USE_MOCK_DB) {
            updatedUser = mockDB.findByIdAndUpdate(userId, { username, passwordHash }, { new: true });
        } else {
            updatedUser = await User.findByIdAndUpdate(
                userId,
                { username, passwordHash },
                { new: true }
            );
        }

        if (!updatedUser) {
            return res.status(404).json({ message: 'User not found' });
        }

        res.status(200).json({ message: 'Credentials created successfully' });
    } catch (error) {
        console.error('Credential Creation Error:', error);
        res.status(500).json({ message: 'Server error: ' + error.message });
    }
});

// 3. Get User Profile
router.get('/user/:id', async (req, res) => {
    try {
        let user;
        if (req.USE_MOCK_DB) {
            user = mockDB.findById(req.params.id);
            if (user) {
                // Clone and remove passwordHash
                const { passwordHash, ...userWithoutPass } = user;
                user = userWithoutPass;
            }
        } else {
            user = await User.findById(req.params.id).select('-passwordHash');
        }

        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }
        res.status(200).json(user);
    } catch (error) {
        console.error('Fetch User Error:', error);
        res.status(500).json({ message: 'Server error' });
    }
});

// 4. Mark Download
router.post('/mark-download', async (req, res) => {
    try {
        const { userId, type } = req.body; // type: 'ats' or 'app'

        if (!userId || !type) {
            return res.status(400).json({ message: 'Missing userId or type' });
        }

        const updateField = {};
        if (type === 'ats') updateField['downloads.atsDownloaded'] = true;
        if (type === 'app') updateField['downloads.appDownloaded'] = true;

        let updatedUser;
        if (req.USE_MOCK_DB) {
            // Mock update logic
            updatedUser = mockDB.findByIdAndUpdate(userId, { $set: updateField }, { new: true });
        } else {
            updatedUser = await User.findByIdAndUpdate(
                userId,
                { $set: updateField },
                { new: true }
            );
        }

        if (!updatedUser) {
            return res.status(404).json({ message: 'User not found' });
        }

        res.status(200).json({ message: 'Download status updated', downloads: updatedUser.downloads });
    } catch (error) {
        console.error('Mark Download Error:', error);
        res.status(500).json({ message: 'Server error' });
    }
});

// 5. Login User
router.post('/login', async (req, res) => {
    try {
        const { username, password } = req.body;

        if (!username || !password) {
            return res.status(400).json({ message: 'Missing username or password' });
        }

        let user;
        if (req.USE_MOCK_DB) {
            user = mockDB.findOne({ username });
        } else {
            user = await User.findOne({ username });
        }

        if (!user) {
            return res.status(400).json({ message: 'You are not a Tracify user. Please register first.' });
        }

        // Verify password
        const isMatch = await bcrypt.compare(password, user.passwordHash);
        if (!isMatch) {
            return res.status(400).json({ message: 'Invalid credentials' });
        }

        res.status(200).json({ message: 'Login successful', userId: user._id });
    } catch (error) {
        console.error('Login Error:', error);
        res.status(500).json({ message: 'Server error' });
    }
});

module.exports = router;


