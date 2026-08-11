document.addEventListener('DOMContentLoaded', () => {
    const subjectsContainer = document.getElementById('subjectsContainer');
    const addSubjectBtn = document.getElementById('addSubjectBtn');
    const calculateBtn = document.getElementById('calculateBtn');
    const clearBtn = document.getElementById('clearBtn');
    const saveBtn = document.getElementById('saveBtn');
    const resultsPanel = document.getElementById('resultsPanel');
    const studentNameInput = document.getElementById('studentName');
    
    const initialSubjects = ['Physics', 'Chemistry', 'Maths', 'English', 'Computer Science'];
    let subjectCount = 0;

    // Initialize with default subjects
    initialSubjects.forEach(sub => addSubjectRow(sub));

    addSubjectBtn.addEventListener('click', () => addSubjectRow(`Subject ${subjectCount + 1}`));

    function addSubjectRow(defaultName) {
        subjectCount++;
        const rowId = `subject-${subjectCount}`;
        
        const row = document.createElement('div');
        row.className = 'subject-row animate-fade-in';
        row.id = rowId;
        
        row.innerHTML = `
            <input type="text" placeholder="Subject Name" value="${defaultName}" class="subject-name">
            <input type="number" placeholder="Marks (0-100)" min="0" max="100" class="subject-mark">
            <button class="btn btn-danger btn-icon remove-btn" onclick="document.getElementById('${rowId}').remove()">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="18" y1="6" x2="6" y2="18"></line><line x1="6" y1="6" x2="18" y2="18"></line></svg>
            </button>
        `;
        
        subjectsContainer.appendChild(row);
    }

    calculateBtn.addEventListener('click', () => {
        const rows = document.querySelectorAll('.subject-row');
        if (rows.length === 0) {
            alert('Please add at least one subject.');
            return;
        }

        let totalMarks = 0;
        let validSubjects = 0;
        let hasError = false;

        rows.forEach(row => {
            const markInput = row.querySelector('.subject-mark').value;
            const subName = row.querySelector('.subject-name').value;
            
            if (markInput === '') return; // Skip empty
            
            const mark = parseFloat(markInput);
            if (isNaN(mark) || mark < 0 || mark > 100) {
                alert(`Please enter valid marks (0-100) for subject: ${subName}`);
                hasError = true;
                return;
            }
            
            totalMarks += mark;
            validSubjects++;
        });

        if (hasError) return;
        
        if (validSubjects === 0) {
            alert('No valid marks entered.');
            return;
        }

        const percentage = totalMarks / validSubjects;
        const grade = calculateGradeLetter(percentage);

        // Update UI
        document.getElementById('totalMarksDisplay').textContent = `${totalMarks} / ${validSubjects * 100}`;
        document.getElementById('percentageDisplay').textContent = `${percentage.toFixed(2)}%`;
        
        const gradeDisplay = document.getElementById('gradeDisplay');
        gradeDisplay.textContent = grade;
        
        // Color coding grade
        if (grade === 'F') gradeDisplay.style.color = 'var(--danger-color)';
        else if (grade === 'A') gradeDisplay.style.color = 'var(--success-color)';
        else gradeDisplay.style.color = 'var(--primary-color)';

        // Show results panel with animation
        resultsPanel.style.display = 'block';
        resultsPanel.classList.add('animate-fade-in');
    });

    clearBtn.addEventListener('click', () => {
        studentNameInput.value = '';
        document.querySelectorAll('.subject-mark').forEach(input => input.value = '');
        resultsPanel.style.display = 'none';
    });

    saveBtn.addEventListener('click', () => {
        const name = studentNameInput.value.trim();
        if (!name) {
            alert('Please enter a student name before saving.');
            return;
        }
        
        const record = {
            id: Date.now(),
            name: name,
            total: document.getElementById('totalMarksDisplay').textContent,
            percentage: document.getElementById('percentageDisplay').textContent,
            grade: document.getElementById('gradeDisplay').textContent,
            date: new Date().toLocaleDateString()
        };
        
        // Save to localStorage
        let savedGrades = JSON.parse(localStorage.getItem('codsoft_grades')) || [];
        savedGrades.push(record);
        localStorage.setItem('codsoft_grades', JSON.stringify(savedGrades));
        
        alert('Results saved successfully!');
        
        // Change button visual temporarily
        const originalText = saveBtn.textContent;
        saveBtn.textContent = 'Saved!';
        setTimeout(() => saveBtn.textContent = originalText, 2000);
    });

    function calculateGradeLetter(avg) {
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }
});
