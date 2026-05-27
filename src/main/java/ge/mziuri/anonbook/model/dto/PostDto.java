package ge.mziuri.anonbook.model.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostDto {
    private String content;
    private MultipartFile image;
}
