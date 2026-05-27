package ge.mziuri.anonbook.mapper;

import ge.mziuri.anonbook.model.dto.CommentDto;
import ge.mziuri.anonbook.model.dto.CommentResponseDto;
import ge.mziuri.anonbook.model.entity.Comment;

public class CommentMapper {
    public static Comment toEntity(CommentDto dto) {
        Comment entity = new Comment();
        entity.setContent(dto.getContent());
        return entity;
    }

    public static CommentResponseDto toResponseDto(Comment entity) {
        return new CommentResponseDto(entity.getContent(), entity.getCreatedAt());
    }
}
