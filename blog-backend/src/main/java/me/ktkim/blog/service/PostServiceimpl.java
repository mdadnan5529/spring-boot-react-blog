package me.ktkim.blog.service;

import me.ktkim.blog.model.domain.Post;
import me.ktkim.blog.model.dto.PostDto;
import me.ktkim.blog.model.domain.User;
import me.ktkim.blog.security.service.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Kim Keumtae
 */

public interface PostServiceimpl {

//    @Autowired
//    private PostRepository postRepository;
//
    public Optional<Post> findForId(Long id) ;

    public PostDto registerPost(PostDto postDto, CustomUserDetails customUserDetails) ;

    public Optional<PostDto> editPost(PostDto editPostDto) ;

    public Page<Post> findByUserOrderedByCreatedDatePageable(User user, Pageable pageable) ;

    public Page<Post> findAllByOrderByCreatedDateDescPageable(Pageable pageable);

    public void deletePost(Long id) ;
}
