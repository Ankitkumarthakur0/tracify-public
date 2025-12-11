const axios = require('axios');

const testLogin = async () => {
    try {
        console.log("Testing Login with non-existent user...");
        await axios.post('http://localhost:5000/api/login', {
            username: 'nonexistentuser',
            password: 'password123'
        });
    } catch (error) {
        if (error.response) {
            console.log("Status:", error.response.status);
            console.log("Message:", error.response.data.message);
        } else {
            console.log("Error:", error.message);
        }
    }
};

testLogin();
