document.addEventListener('DOMContentLoaded', () => {
    const header = document.querySelector('header');
    if (header) {
        // Create the theme toggle button
        const toggleBtn = document.createElement('button');
        toggleBtn.id = 'themeToggle';
        toggleBtn.className = 'btn btn-icon btn-outline';
        // Make sure it sits on the right
        toggleBtn.style.marginLeft = 'auto'; 
        toggleBtn.title = "Toggle Light/Dark Mode";
        
        // Inject into header
        header.appendChild(toggleBtn);
        
        // Get current theme from HTML element (set by head script)
        const currentTheme = document.documentElement.getAttribute('data-theme') || 'dark';
        updateIcon(currentTheme);
        
        // Handle click
        toggleBtn.addEventListener('click', () => {
            let theme = document.documentElement.getAttribute('data-theme');
            let newTheme = theme === 'dark' ? 'light' : 'dark';
            
            document.documentElement.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
            updateIcon(newTheme);
        });
        
        function updateIcon(theme) {
            if(theme === 'light') {
                // Show Moon (implies clicking will switch to dark)
                toggleBtn.innerHTML = `<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path></svg>`;
            } else {
                // Show Sun (implies clicking will switch to light)
                toggleBtn.innerHTML = `<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="5"></circle><line x1="12" y1="1" x2="12" y2="3"></line><line x1="12" y1="21" x2="12" y2="23"></line><line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line><line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line><line x1="1" y1="12" x2="3" y2="12"></line><line x1="21" y1="12" x2="23" y2="12"></line><line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line><line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line></svg>`;
            }
        }
    }
});
