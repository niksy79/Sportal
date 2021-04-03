package com.example.demo.service;
import com.example.demo.dto.commentdto.CommentAddRequestDTO;
import com.example.demo.dto.commentdto.CommentAddResponseDTO;
import com.example.demo.dto.commentdto.CommentLikeRequestDTO;
import com.example.demo.dto.commentdto.CommentLikeResponseDTO;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {
    @Autowired
    NewsService newsService;
    @Autowired
    UserService userService;
    @Autowired
    CommentRepository commentRepository;


    @Transactional
    public CommentLikeResponseDTO likeUnlikeComment(CommentLikeRequestDTO requestDTO, User user) {
        Comment likedComment = getCommentById(requestDTO.getId());
        if (!user.getLikedComments().contains(likedComment)) {
            user.getLikedComments().add(likedComment);
            user.getDislikedComments().remove(likedComment);
        } else {
            user.getLikedComments().remove(likedComment);
        }
        userService.save(user);
        commentRepository.save(likedComment);

        return new CommentLikeResponseDTO(likedComment);
    }

    public CommentLikeResponseDTO dislikeComment(CommentLikeRequestDTO requestDTO, User user) {
        Comment disComment = getCommentById(requestDTO.getId());
        if (user.getDislikedComments().contains(disComment)){
            throw new BadRequestException("you already dislike this comment");
        }
        else {
            user.getDislikedComments().add(disComment);
            user.getLikedComments().remove(disComment);
        }
        userService.save(user);

        return new CommentLikeResponseDTO(disComment);
    }


    public CommentAddResponseDTO writeComment(CommentAddRequestDTO requestDTO, User loggedUser) {
        News news = newsService.getByID(requestDTO.getNewsId());
        Comment comment = new Comment(requestDTO.getText(), LocalDateTime.now(), news, loggedUser);
        CommentAddResponseDTO responseDTO = new CommentAddResponseDTO(news);
        responseDTO.setCommentText(requestDTO.getText());
        commentRepository.save(comment);

        return responseDTO;
    }

    @Override
    public List<CommentLikeResponseDTO> mostLikedComments() {
        List<Comment> allLiked = commentRepository.findAll()
                .stream()
                .filter(o1 -> o1.getLikers().size()> 0)
                .collect(Collectors.toList());
        List<CommentLikeResponseDTO> topComments = new ArrayList<>();
        for (Comment c : allLiked){
            topComments.add(new CommentLikeResponseDTO(c));
        }
        return topComments
                .stream()
                .sorted(((o1, o2) -> Long.compare(o2.getLikes(), o1.getLikes())))
                .limit(10)
                .collect(Collectors.toList());
    }

    public void deleteComment(long id){
        Comment comment = getCommentById(id);
        commentRepository.delete(comment);
    }

    public Comment getCommentById(long id){
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()){
            throw new NotFoundException("Comment not found");
        }
        return comment.get();
    }
}
