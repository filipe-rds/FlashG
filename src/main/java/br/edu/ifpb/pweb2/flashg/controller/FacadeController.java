package br.edu.ifpb.pweb2.flashg.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.pweb2.flashg.dtos.CommentDTO;
import br.edu.ifpb.pweb2.flashg.dtos.LikeDTO;
import br.edu.ifpb.pweb2.flashg.entity.Comment;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.exception.EmailAlreadyExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.service.FacadeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
public class FacadeController {

    @Autowired
    private FacadeService facadeService;

    @GetMapping(value = "")
    public ModelAndView index(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        mav.setViewName("redirect:/home");
        return mav;
    }

    @GetMapping(value = "/home")
    public ModelAndView home(ModelAndView mav, HttpSession session) {
        mav.setViewName("home");
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        session.setAttribute("loggedPhotographer", loggedPhotographer);
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        facadeService.logoutPhotographer(session);
        mav.setViewName("redirect:/auth/signin");
        return mav;
    }

    @GetMapping(value = "/searchPhotographers")
    public ModelAndView searchPhotographers(ModelAndView mav) {
        mav.setViewName("application/findPhotographers");
        return mav;
    }

    @GetMapping(value = "/findPhotographers")
    public ModelAndView getAllPhotographerStartingWith(ModelAndView mav, @RequestParam("username") String username, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photographer> photographers = facadeService.findByUsernameStartingWith(username);
        facadeService.removePhotographerFromArray(photographers, loggedPhotographer);
        mav.setViewName("application/findPhotographers");
        mav.addObject("photographers", photographers);
        mav.addObject("resultados", "Resultados para: " + username);
        return mav;
    }

    @GetMapping(value = "/showProfile/{id}")
    public ModelAndView showProfile(ModelAndView mav, @PathVariable("id") Long id, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        Photographer searchedPhotographer = facadeService.findByIdPhotographer(id);
        String status = facadeService.checkFollowStatus(loggedPhotographer, searchedPhotographer);
        List<Photo> photos = facadeService.showPhotos(searchedPhotographer.getId());
        List<String> statusLikes = facadeService.getStatusLikeOfPhotosOtherPhotographer(searchedPhotographer.getId(),loggedPhotographer.getId() );
        mav.setViewName("application/showProfilePhotographer");
        mav.addObject("status", status);
        mav.addObject("photographer", searchedPhotographer);
        mav.addObject("photos", photos);
        mav.addObject("statusLikes", statusLikes);
        return mav;
    }

    @GetMapping(value = "/myProfile")
    public ModelAndView myProfile(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photo> photos = facadeService.showPhotos(loggedPhotographer.getId());
        Photographer myProfile = facadeService.findByIdPhotographer(loggedPhotographer.getId());
        session.setAttribute("loggedPhotographer", myProfile);
        List<String> statusLikes= facadeService.getStatusLikeOfPhotos(myProfile.getId());
        mav.setViewName("application/myProfilePhotographer");
        mav.addObject("photographer", myProfile);
        mav.addObject("photos", photos);
        mav.addObject("statusLikes", statusLikes);
        return mav;
    }

    @PostMapping(value = "/followAction")
    public ModelAndView followAction(ModelAndView mav, @RequestParam("id") Long id, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        facadeService.handleFollowAction(loggedPhotographer.getId(), id);
        Photographer updatedLoggedPhotographer = facadeService.findByIdPhotographer(loggedPhotographer.getId());
        session.setAttribute("loggedPhotographer", updatedLoggedPhotographer);
        mav.setViewName("redirect:/showProfile/" + id);
        return mav;
    }

    @GetMapping(value = "/listPhotographerFollowing")
    public ModelAndView listAllFollowing(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photographer> seguidos = facadeService.findAllFollowing(loggedPhotographer.getId());
        mav.setViewName("application/listPhotographerFollowing");
        mav.addObject("seguidos", seguidos);
        return mav;
    }

    @GetMapping(value = "/listPhotographerFollowers")
    public ModelAndView listAllFollowers(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photographer> seguidores = facadeService.findAllFollowers(loggedPhotographer.getId());
        mav.setViewName("application/listPhotographerFollowers");
        mav.addObject("seguidores", seguidores);
        return mav;
    }

    @GetMapping(value = "/showAllPhotographers")
    public ModelAndView listAllPhotographer(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photographer> photographers = facadeService.findAllPhotographers();
        facadeService.removePhotographerFromArray(photographers, loggedPhotographer);
        mav.addObject("photographers", photographers);
        mav.setViewName("application/showAllPhotographers");
        return mav;
    }

    @GetMapping(value = "/uploadPhotos")
    public ModelAndView uploadPage(ModelAndView mav) {
        mav.addObject("photo", new Photo());
        mav.setViewName("application/uploadPhotos");
        return mav;
    }

    @PostMapping(value = "/uploadPhotos")
    public ModelAndView handleFileUpload(ModelAndView mav, HttpSession session, @RequestParam("image") MultipartFile file, @ModelAttribute("photo")Photo photo) throws Exception {
        if (file.isEmpty()) {
            mav.setViewName("application/uploadPhotos");
            return mav;
        }
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        facadeService.uploadPhoto(loggedPhotographer.getId(),photo,file);
        mav.setViewName("redirect:/myProfile");
        return mav;
    }

    @PostMapping(value = "/blockAction")
    public ModelAndView blockAction(ModelAndView mav, @RequestParam("id") Long id) {
        //facadeService.handleBlockAction(loggedPhotographer.getId(), id);
        //session.setAttribute("loggedPhotographer", updatedLoggedPhotographer);
        facadeService.handleBlockAction(id);
        mav.setViewName("redirect:/showAllPhotographers");
        return mav;
    }

    @GetMapping(value="/showEditProfilePhotographer/{id}")
    public ModelAndView editProfilePhotographer(ModelAndView mav, @PathVariable("id") Long id) {
        Photographer photographer = facadeService.findByIdPhotographer(id);
        mav.addObject("photographer", photographer);
        mav.setViewName("application/editProfilePhotographer");
        return mav;
    }


    @PostMapping(value="/editProfilePhotographer")
    public ModelAndView editProfilePhotographer(@Valid @ModelAttribute Photographer photographer, BindingResult result, HttpSession session , RedirectAttributes redirectAttributes) throws EmailAlreadyExists {

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("photographer", photographer);
            mav.setViewName("application/editProfilePhotographer");
            return mav;
        }

        // Faz o update do cara
        facadeService.updatePhotographer(photographer);

        // Resgata o fotógrafo que acabou de realizar o update
        Photographer updatedLoggedPhotographer = facadeService.findByIdPhotographer(photographer.getId());

        // Resgata o fotógrafo da sessão
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);

        // Resgata o fotógrafo da sessão atualizado com o update
        Photographer updatedLoggedPhotographerSession = facadeService.findByIdPhotographer(loggedPhotographer.getId());

        // Atualiza o fotógrafo da sessão
        session.setAttribute("loggedPhotographer", updatedLoggedPhotographerSession);

        //mav.addObject("photographer", updatedLoggedPhotographer);
        redirectAttributes.addFlashAttribute("success", "Perfil atualizado com sucesso!");

        long id = updatedLoggedPhotographer.getId();
        mav.setViewName("redirect:/showEditProfilePhotographer/" + id );

        return mav;
    }


    @PostMapping(value = "/myProfile")
    public ModelAndView handleProfileAvatar(ModelAndView mav, @RequestParam("avatar") MultipartFile file, HttpSession session) throws Exception {
        if (file.isEmpty()) {
            mav.setViewName("redirect:/myProfile");
        }
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        facadeService.updateAvatar(loggedPhotographer,file);
        mav.setViewName("redirect:/myProfile");
        return mav;
    }

    @PostMapping(value = "/addComment")
    public ResponseEntity<CommentDTO> addComment(@RequestBody Map<String, Object> commentData) {
        String commentText = (String) commentData.get("commentText"); //OK
        Long photographerId = Long.valueOf((String) commentData.get("photographer"));
        Long photoId = Long.valueOf((String) commentData.get("photo"));
        String createdAt = (String) commentData.get("createdAt");
        createdAt = createdAt.replace("Z", "");
        LocalDateTime createdAtDateTime = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Photographer photographer = facadeService.findByIdPhotographer(photographerId);
        Photo photo = facadeService.findPhotoById(photoId);
        // Cria uma instância do comentário e preenche os dados
        Comment comment = new Comment();
        comment.setCommentText(commentText);
        comment.setCreatedAt(createdAtDateTime);
        comment.setPhotographer(photographer);
        comment.setPhoto(photo);
        facadeService.saveComment(comment);
        facadeService.findAllCommentOfPhoto(photo);
        Photo photoBanco = facadeService.findPhotoById(photo.getId());
        CommentDTO commentDTO = new CommentDTO(comment.getPhotographer().getProfilePictureUrl(),comment.getCommentText(), comment.getDate(), comment.getPhotographer().getUsername(),photoBanco.getComments().size());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }

    @PostMapping(value = "/likeAction")
    public ResponseEntity<LikeDTO> likeAction(@RequestBody Map<String, Object> commentData) {
        Long photographerId = Long.valueOf((String) commentData.get("photographer"));
        Long photoId = Long.valueOf((String) commentData.get("photo"));
        //Photographer photographer = facadeService.findByIdPhotographer(photographerId);
        //Photo photo = facadeService.findPhotoById(photoId);
        System.out.println("-----------------------");
        System.out.println(photoId);
        System.out.println(photographerId);
        String response = facadeService.handleLikeAction(photoId, photographerId);
        Integer sizeLikes = facadeService.getLikeCountOfPhoto(photoId);
        System.out.println(sizeLikes);
        System.out.println("-----------------------");
        LikeDTO likeDTO = new LikeDTO(response, sizeLikes);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeDTO);
    }



}

