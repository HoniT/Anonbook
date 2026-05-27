const API_BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const postId = urlParams.get('id');

    if (!postId) {
        document.getElementById('singlePostContainer').innerHTML = '<p>Post not found.</p>';
        return;
    }

    fetchPostDetails(postId);

    const addCommentForm = document.getElementById('addCommentForm');
    addCommentForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const content = document.getElementById('commentContent').value;

        const commentDto = {
            postId: parseInt(postId),
            content: content
        };

        try {
            const response = await fetch(`${API_BASE_URL}/comments`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(commentDto)
            });

            if (response.ok) {
                window.location.reload();
            } else {
                alert('Failed to add comment.');
            }
        } catch (error) {
            console.error('Error adding comment:', error);
        }
    });

    async function fetchPostDetails(id) {
        try {
            const response = await fetch(`${API_BASE_URL}/posts/${id}`);
            if (!response.ok) throw new Error('Post not found');

            const post = await response.json();
            renderSinglePost(post);
        } catch (error) {
            document.getElementById('singlePostContainer').innerHTML = '<p>Error loading post.</p>';
            console.error(error);
        }
    }

    function renderSinglePost(post) {
        const container = document.getElementById('singlePostContainer');

        let imageHtml = '';
        if (post.imagePath) {
            imageHtml = `<img src="${API_BASE_URL}/posts/image/${post.imagePath}" class="post-image" alt="Post Image">`;
        }

        container.innerHTML = `
            <div class="post-header">N${post.id} ${post.createdAt || ''}</div>
            <div class="post-content">${post.content}</div>
            ${imageHtml}
        `;

        const commentList = document.getElementById('commentList');
        commentList.innerHTML = '';

        if (post.comments && post.comments.length > 0) {
            post.comments.forEach((comment, index) => {
                const li = document.createElement('li');
                li.className = 'comment-item';
                li.innerHTML = `- [N${index + 1}] ${comment.content}`;
                commentList.appendChild(li);
            });
        }
    }
});