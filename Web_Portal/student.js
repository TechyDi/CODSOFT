document.addEventListener('DOMContentLoaded', () => {
    // --- Data Management ---
    let students = JSON.parse(localStorage.getItem('codsoft_students'));
    if (!students || students.length === 0) {
        students = [
            { roll: 101, name: "Alice Smith", grade: "A", email: "alice.smith@example.com" },
            { roll: 102, name: "Bob Johnson", grade: "B", email: "bob.j@example.com" },
            { roll: 103, name: "Charlie Brown", grade: "C", email: "charlie.b@example.com" },
            { roll: 104, name: "Diana Prince", grade: "A", email: "diana.p@example.com" },
            { roll: 105, name: "Evan Wright", grade: "D", email: "evan.w@example.com" },
            { roll: 106, name: "Fiona Gallagher", grade: "B", email: "fiona.g@example.com" },
            { roll: 107, name: "George Miller", grade: "F", email: "george.m@example.com" }
        ];
        // We can't call saveData yet as it's defined below, but we can set it directly:
        localStorage.setItem('codsoft_students', JSON.stringify(students));
    }
    let currentSort = { col: null, asc: true };
    
    function saveData() {
        localStorage.setItem('codsoft_students', JSON.stringify(students));
    }
    
    // --- DOM Elements ---
    const txtRoll = document.getElementById('txtRoll');
    const txtName = document.getElementById('txtName');
    const txtGrade = document.getElementById('txtGrade');
    const txtEmail = document.getElementById('txtEmail');
    const formError = document.getElementById('formError');
    
    const btnAdd = document.getElementById('btnAdd');
    const btnUpdate = document.getElementById('btnUpdate');
    const btnDelete = document.getElementById('btnDelete');
    const btnClear = document.getElementById('btnClear');
    
    const txtSearch = document.getElementById('txtSearch');
    const btnExport = document.getElementById('btnExport');
    
    const tableBody = document.getElementById('tableBody');
    const emptyState = document.getElementById('emptyState');
    const thElements = document.querySelectorAll('th[data-sort]');
    
    // --- Validation ---
    const EMAIL_REGEX = /^[A-Za-z0-9+_.-]+@(.+)$/;
    const GRADE_REGEX = /^[A-F]$/i;
    
    function showError(msg) {
        formError.textContent = msg;
        setTimeout(() => formError.textContent = '', 4000);
    }
    
    // --- Rendering ---
    function renderTable(dataToRender = students) {
        tableBody.innerHTML = '';
        
        if (dataToRender.length === 0) {
            emptyState.style.display = 'block';
            document.getElementById('studentTable').style.display = 'none';
            return;
        }
        
        emptyState.style.display = 'none';
        document.getElementById('studentTable').style.display = 'table';
        
        dataToRender.forEach(s => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${s.roll}</td>
                <td style="font-weight: 500;">${s.name}</td>
                <td><span class="badge ${getGradeBadge(s.grade)}">${s.grade}</span></td>
                <td style="color: var(--text-secondary);">${s.email || '-'}</td>
            `;
            
            tr.addEventListener('click', () => {
                // Highlight row
                document.querySelectorAll('#tableBody tr').forEach(row => row.classList.remove('selected-row'));
                tr.classList.add('selected-row');
                
                // Populate form
                txtRoll.value = s.roll;
                txtRoll.disabled = true; // Prevent changing ID on update
                txtName.value = s.name;
                txtGrade.value = s.grade;
                txtEmail.value = s.email;
            });
            
            tableBody.appendChild(tr);
        });
    }
    
    function getGradeBadge(grade) {
        if (['A', 'B'].includes(grade)) return 'badge-success';
        if (['C', 'D'].includes(grade)) return 'badge-warning';
        return 'badge-danger';
    }
    
    // Initial render
    renderTable();
    
    // --- Actions ---
    
    function clearForm() {
        txtRoll.value = '';
        txtRoll.disabled = false;
        txtName.value = '';
        txtGrade.value = '';
        txtEmail.value = '';
        formError.textContent = '';
        document.querySelectorAll('#tableBody tr').forEach(row => row.classList.remove('selected-row'));
    }
    
    btnClear.addEventListener('click', clearForm);
    
    btnAdd.addEventListener('click', () => {
        const roll = parseInt(txtRoll.value);
        const name = txtName.value.trim();
        const grade = txtGrade.value.trim().toUpperCase();
        const email = txtEmail.value.trim();
        
        if (!roll || !name || !grade) {
            showError('Roll No, Name, and Grade are required.');
            return;
        }
        
        if (students.some(s => s.roll === roll)) {
            showError('Roll Number already exists.');
            return;
        }
        
        if (!GRADE_REGEX.test(grade)) {
            showError('Grade must be a single letter (A-F).');
            return;
        }
        
        if (email && !EMAIL_REGEX.test(email)) {
            showError('Invalid email format.');
            return;
        }
        
        students.push({ roll, name, grade, email });
        saveData();
        clearForm();
        renderTable();
    });
    
    btnUpdate.addEventListener('click', () => {
        const roll = parseInt(txtRoll.value);
        if (!roll || !txtRoll.disabled) {
            showError('Select a student from the table to update.');
            return;
        }
        
        const name = txtName.value.trim();
        const grade = txtGrade.value.trim().toUpperCase();
        const email = txtEmail.value.trim();
        
        if (!name || !grade) {
            showError('Name and Grade are required.');
            return;
        }
        
        if (!GRADE_REGEX.test(grade)) {
            showError('Grade must be a single letter (A-F).');
            return;
        }
        
        if (email && !EMAIL_REGEX.test(email)) {
            showError('Invalid email format.');
            return;
        }
        
        const index = students.findIndex(s => s.roll === roll);
        if (index !== -1) {
            students[index] = { roll, name, grade, email };
            saveData();
            clearForm();
            renderTable();
        }
    });
    
    btnDelete.addEventListener('click', () => {
        const roll = parseInt(txtRoll.value);
        if (!roll || !txtRoll.disabled) {
            showError('Select a student from the table to delete.');
            return;
        }
        
        if (confirm('Are you sure you want to delete this student?')) {
            students = students.filter(s => s.roll !== roll);
            saveData();
            clearForm();
            renderTable();
        }
    });
    
    // --- Search ---
    txtSearch.addEventListener('input', (e) => {
        const q = e.target.value.toLowerCase();
        if (!q) {
            renderTable();
            return;
        }
        
        const filtered = students.filter(s => 
            s.name.toLowerCase().includes(q) || 
            s.roll.toString().includes(q)
        );
        renderTable(filtered);
    });
    
    // --- Sorting ---
    thElements.forEach(th => {
        th.addEventListener('click', () => {
            const col = th.dataset.sort;
            
            // Reset icons
            thElements.forEach(t => t.classList.remove('sort-asc', 'sort-desc'));
            
            if (currentSort.col === col) {
                currentSort.asc = !currentSort.asc;
            } else {
                currentSort = { col: col, asc: true };
            }
            
            th.classList.add(currentSort.asc ? 'sort-asc' : 'sort-desc');
            
            students.sort((a, b) => {
                let valA = a[col];
                let valB = b[col];
                
                if (typeof valA === 'string') valA = valA.toLowerCase();
                if (typeof valB === 'string') valB = valB.toLowerCase();
                
                if (valA < valB) return currentSort.asc ? -1 : 1;
                if (valA > valB) return currentSort.asc ? 1 : -1;
                return 0;
            });
            
            // Re-render currently searched data if search active
            const q = txtSearch.value.toLowerCase();
            if (q) {
                txtSearch.dispatchEvent(new Event('input'));
            } else {
                renderTable();
            }
        });
    });
    
    // --- Export ---
    btnExport.addEventListener('click', () => {
        if (students.length === 0) {
            alert('No data to export.');
            return;
        }
        
        let csv = 'Roll No,Name,Grade,Email\n';
        students.forEach(s => {
            csv += `${s.roll},"${s.name}",${s.grade},${s.email || ''}\n`;
        });
        
        const blob = new Blob([csv], { type: 'text/csv' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'students_export.csv';
        a.click();
        URL.revokeObjectURL(url);
    });
});
