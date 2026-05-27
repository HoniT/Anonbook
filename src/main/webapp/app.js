const API_BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    const toggleBtn = document.getElementById('togglePostFormBtn');
    const formContainer = document.getElementById('addPostFormContainer');
    const addPostForm = document.getElementById('addPostForm');
    const postsContainer = document.getElementById('postsContainer');

    toggleBtn.addEventListener('click', () => {
        formContainer.style.display = formContainer.style.display === 'block' ? 'none' : 'block';
    });

    fetchPosts();

    addPostForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const content = document.getElementById('postContent').value;
        const fileInput = document.getElementById('postFile');

        const formData = new FormData();
        formData.append('content', content);
        if (fileInput.files.length > 0) {
            formData.append('file', fileInput.files[0]);
        }

        try {
            const response = await fetch(`${API_BASE_URL}/posts`, {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                addPostForm.reset();
                formContainer.style.display = 'none';
                fetchPosts();
            } else {
                alert('Failed to add post.');
            }
        } catch (error) {
            console.error('Error adding post:', error);
        }
    });

    async function fetchPosts() {
        try {
            const response = await fetch(`${API_BASE_URL}/posts`);
            const posts = await response.json();
            renderPosts(posts);
        } catch (error) {
            console.error('Error fetching posts:', error);
        }
    }

    function renderPosts(posts) {
        postsContainer.innerHTML = '';
        posts.forEach(post => {
            const card = document.createElement('div');
            card.className = 'post-card';

            let imageHtml = '';
            if (post.imagePath) {
                imageHtml = `<img src="${API_BASE_URL}/posts/image/${post.imagePath}" class="post-image" alt="Post Image">`;
            }

            card.innerHTML = `
                <button class="btn-view" onclick="viewPost(${post.id})">View Post</button>
                <div class="post-header">N${post.id} ${post.createdAt || ''}</div>
                <div class="post-content">${post.content}</div>
                ${imageHtml}
            `;
            postsContainer.appendChild(card);
        });
    }
});

function viewPost(postId) {
    window.location.href = `post.html?id=${postId}`;
}