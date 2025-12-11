const axios = require('axios');

const testCreateCredentials = async () => {
    try {
        // First register a user to get an ID (using mock DB)
        console.log("Registering temp user...");
        const regRes = await axios.post('http://localhost:5000/api/register', {
            name: 'Test User',
            phone: '1234567890',
            email: 'test' + Date.now() + '@example.com',
            aadhar: '123412341234',
            address: 'Test Address',
            imei1: '123456789012345',
            imei2: '123456789012345'
        });
        const userId = regRes.data.userId;
        console.log("User ID:", userId);

        // Now try to create credentials
        console.log("Creating credentials...");
        await axios.post('http://localhost:5000/api/create-credentials', {
            userId: userId,
            username: 'testuser' + Date.now(),
            password: 'password123'
        });
        console.log("Credentials created successfully!");
    } catch (error) {
        if (error.response) {
            console.log("Status:", error.response.status);
            console.log("Message:", error.response.data.message);
        } else {
            console.log("Error:", error.message);
        }
    }
};

testCreateCredentials();
