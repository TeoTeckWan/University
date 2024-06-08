document.addEventListener('DOMContentLoaded', function () {
    const sendBtn = document.getElementById('send-btn');
    const userInput = document.getElementById('user-input');
    const chatBox = document.querySelector('.chats');

    // Initial greeting message from Jamie
    appendMessage("Hello! How can I help you?", 'Jamie', 'bot-chat');

    sendBtn.addEventListener('click', sendMessage);
    userInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });

    function sendMessage() {
        const inputText = userInput.value.trim();
        if (inputText === "") return;

        appendMessage(inputText, 'You', 'user-chat');
        userInput.value = '';
        chatBox.scrollTop = chatBox.scrollHeight;

        setTimeout(function () {
            const botResponse = getBotResponse(inputText);
            appendMessage(botResponse, 'Jamie', 'bot-chat');
            chatBox.scrollTop = chatBox.scrollHeight;
        }, 500);
    }

    function appendMessage(text, sender, className) {
        const messageBox = document.createElement('div');

        // Bot or User?
        const messageDiv = document.createElement('div');
        messageDiv.className = className;
        
        // Name (Jamie or You)
        const nameDiv = document.createElement('div');
        nameDiv.className = 'name';
        nameDiv.textContent = sender;
        
        // Content
        const textDiv = document.createElement('div');
        textDiv.className = 'text';
        textDiv.textContent = text;
        
        // Date
        const timestampDiv = document.createElement('div');
        timestampDiv.className = 'timestamp';
        timestampDiv.textContent = new Date().toLocaleString();

        if(className === "bot-chat"){
            messageBox.className = "bot-box";
        }
        else{
            messageBox.className = "user-box";
        }
        messageDiv.appendChild(textDiv);
        messageBox.appendChild(nameDiv);
        messageBox.appendChild(messageDiv);
        messageBox.appendChild(timestampDiv);

        console.log(messageDiv);
        chatBox.appendChild(messageBox);
    }

    function getBotResponse(input) {
        input = input.trim();
    
        // Question
        if (input.includes("?")) {
            // Yell a question
            if ((input.toUpperCase() === input || input.includes("!")) && input.includes("?")) {
                return "Please give me some time to resolve the issue.";
            } else {
                return "Yes";
            }
        }
        // Yell
        else if (input.toUpperCase() === input || input.includes("!")) {
            return "Please remain calm.";
        }
        // Jamie name
        else if (input.toLowerCase().includes("jamie")) {
            return "Can I help you?";
        }
        // Others
        else {
            return "Sorry, I don’t understand";
        }
    }
});
