const axios = require('axios');

async function test() {
    try {
        const res = await axios.post('http://localhost:5000/api/register', {
            name: "Test",
            phone: "123",
            email: "test@test.com",
            aadhar: "123",
            address: "123",
            imei1: "123",
            imei2: "123"
        });
        console.log(res.data);
    } catch (err) {
        console.log(err.response ? err.response.data : err.message);
    }
}

test();
