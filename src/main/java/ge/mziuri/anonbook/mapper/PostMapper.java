package ge.mziuri.anonbook.mapper;

import ge.mziuri.anonbook.model.dto.PostDto;
import ge.mziuri.anonbook.model.dto.PostResponseDto;
import ge.mziuri.anonbook.model.entity.Post;

public class PostMapper {
    public static Post toEntity(PostDto dto) {
        Post entity = new Post();
        entity.setContent(dto.getContent());
        return entity;
    }

    public static PostResponseDto toResponseDto(Post entity) {
        return new PostResponseDto(entity.getContent(), entity.getImagePath(), entity.getCreatedAt(),
            entity.getComments().stream().map(CommentMapper::toResponseDto).toList());
    }
}
