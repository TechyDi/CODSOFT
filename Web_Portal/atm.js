document.addEventListener('DOMContentLoaded', () => {
    // --- Data Management ---
    let accounts = JSON.parse(localStorage.getItem('codsoft_atm_accounts'));
    
    // Initialize default data if empty
    if (!accounts) {
        accounts = {
            "12345": { pin: "1234", balance: 10000.00, history: [] },
            "98765": { pin: "9876", balance: 500.00, history: [] }
        };
        saveData();
    }
    
    let currentUser = null;

    function saveData() {
        localStorage.setItem('codsoft_atm_accounts', JSON.stringify(accounts));
    }

    // --- DOM Elements ---
    const loginPanel = document.getElementById('loginPanel');
    const mainPanel = document.getElementById('mainPanel');
    const historyPanel = document.getElementById('historyPanel');
    
    const loginAccNo = document.getElementById('loginAccNo');
    const loginPin = document.getElementById('loginPin');
    const loginError = document.getElementById('loginError');
    const btnLogin = document.getElementById('btnLogin');
    
    const displayBalance = document.getElementById('displayBalance');
    const statusMessage = document.getElementById('statusMessage');
    const txtAmount = document.getElementById('txtAmount');
    
    const btnDeposit = document.getElementById('btnDeposit');
    const btnWithdraw = document.getElementById('btnWithdraw');
    const btnHistory = document.getElementById('btnHistory');
    const btnLogout = document.getElementById('btnLogout');
    
    const historyList = document.getElementById('historyList');
    const btnBackToMain = document.getElementById('btnBackToMain');

    // --- Navigation ---
    function showPanel(panel) {
        loginPanel.classList.remove('active');
        mainPanel.classList.remove('active');
        historyPanel.classList.remove('active');
        panel.classList.add('active');
    }

    function updateDisplay() {
        if (!currentUser) return;
        displayBalance.textContent = `₹ ${accounts[currentUser].balance.toFixed(2)}`;
    }

    function showStatus(msg, isError = false) {
        statusMessage.textContent = msg;
        statusMessage.style.color = isError ? 'var(--danger-color)' : 'var(--success-color)';
        setTimeout(() => {
            if(statusMessage.textContent === msg) {
                statusMessage.textContent = 'Ready for transaction';
                statusMessage.style.color = 'var(--text-secondary)';
            }
        }, 3000);
    }

    // --- Event Listeners ---
    btnLogin.addEventListener('click', () => {
        const acc = loginAccNo.value.trim();
        const pin = loginPin.value.trim();
        
        if (accounts[acc] && accounts[acc].pin === pin) {
            currentUser = acc;
            loginError.textContent = '';
            loginPin.value = '';
            statusMessage.textContent = 'Welcome back!';
            statusMessage.style.color = 'var(--success-color)';
            updateDisplay();
            showPanel(mainPanel);
        } else {
            loginError.textContent = 'Invalid Account Number or PIN.';
        }
    });

    btnLogout.addEventListener('click', () => {
        currentUser = null;
        txtAmount.value = '';
        showPanel(loginPanel);
    });

    btnDeposit.addEventListener('click', () => {
        const amt = parseFloat(txtAmount.value);
        if (isNaN(amt) || amt <= 0) {
            showStatus('Enter a valid positive amount.', true);
            return;
        }
        
        accounts[currentUser].balance += amt;
        
        // Add history record
        const date = new Date().toLocaleString();
        accounts[currentUser].history.unshift({ type: 'deposit', amount: amt, date: date });
        
        saveData();
        updateDisplay();
        txtAmount.value = '';
        showStatus(`Successfully deposited ₹${amt.toFixed(2)}`);
    });

    btnWithdraw.addEventListener('click', () => {
        const amt = parseFloat(txtAmount.value);
        if (isNaN(amt) || amt <= 0) {
            showStatus('Enter a valid positive amount.', true);
            return;
        }
        
        if (amt > accounts[currentUser].balance) {
            showStatus('Insufficient funds.', true);
            return;
        }
        
        accounts[currentUser].balance -= amt;
        
        // Add history record
        const date = new Date().toLocaleString();
        accounts[currentUser].history.unshift({ type: 'withdraw', amount: amt, date: date });
        
        saveData();
        updateDisplay();
        txtAmount.value = '';
        showStatus(`Successfully withdrawn ₹${amt.toFixed(2)}`, false);
    });

    btnHistory.addEventListener('click', () => {
        historyList.innerHTML = '';
        const history = accounts[currentUser].history;
        
        if (history.length === 0) {
            historyList.innerHTML = '<div style="text-align:center; color: var(--text-secondary); padding: 2rem 0;">No recent transactions.</div>';
        } else {
            history.forEach(record => {
                const isDep = record.type === 'deposit';
                historyList.innerHTML += `
                    <div class="history-item ${record.type}">
                        <div>
                            <div style="font-weight: 600;">${isDep ? 'Deposit' : 'Withdrawal'}</div>
                            <div style="font-size: 0.8rem; color: var(--text-secondary);">${record.date}</div>
                        </div>
                        <div class="amt" style="font-weight: 700; font-family: monospace; font-size: 1.1rem;">
                            ${isDep ? '+' : '-'} ₹${record.amount.toFixed(2)}
                        </div>
                    </div>
                `;
            });
        }
        showPanel(historyPanel);
    });
    
    btnBackToMain.addEventListener('click', () => showPanel(mainPanel));
});
