package ge.mziuri.anonbook.service;

import ge.mziuri.anonbook.mapper.PostMapper;
import ge.mziuri.anonbook.model.dto.PostDto;
import ge.mziuri.anonbook.model.entity.Post;
import ge.mziuri.anonbook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    private final Path uploadPath;

    public PostService(@Value("${spring.images.upload-dir:uploads}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(int id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + id));
    }

    public void savePost(PostDto postDto) {
        Post post = PostMapper.toEntity(postDto);

        MultipartFile file = postDto.getImage();
        if (file != null && !file.isEmpty() && Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".jpg")) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                post.setImagePath(fileName);
            } catch (IOException e) {
                throw new RuntimeException("Could not store image file: " + e.getMessage(), e);
            }
        }
        postRepository.save(post);
    }


    public Resource loadImageAsResource(String fileName) {
        try {
            Path filePath = uploadPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public String getContentType(String fileName) {
        try {
            Path filePath = uploadPath.resolve(fileName).normalize();
            String contentType = Files.probeContentType(filePath);

            if (contentType == null) {
                return "application/octet-stream";
            }
            return contentType;

        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
}
