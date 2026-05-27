package ge.mziuri.anonbook.service;

import ge.mziuri.anonbook.mapper.CommentMapper;
import ge.mziuri.anonbook.model.dto.CommentDto;
import ge.mziuri.anonbook.model.entity.Comment;
import ge.mziuri.anonbook.model.entity.Post;
import ge.mziuri.anonbook.repository.CommentRepository;
import ge.mziuri.anonbook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public void addComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find Post with ID: " + commentDto.getPostId()));

        Comment comment = CommentMapper.toEntity(commentDto);
        comment.setPost(post);

        commentRepository.save(comment);
    }
}
