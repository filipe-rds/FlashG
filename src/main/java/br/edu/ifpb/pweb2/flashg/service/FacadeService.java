package br.edu.ifpb.pweb2.flashg.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import br.edu.ifpb.pweb2.flashg.entity.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FacadeService {

    @Autowired
    private PhotographerService photographerService;

    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService LikeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PhotoTagService photoTagService;


    public List<Photographer> findByUsernameStartingWith(String nome) {
        return photographerService.findByUsernameStartingWith(nome);
    }


    public Photographer findByIdPhotographer(Long id) {
        return photographerService.findById(id);
    }


    public Page<Photographer> findAllPhotographers(Pageable page) {
        return photographerService.findAllPhotographers(page);
    }


    public String checkFollowStatus(Photographer seguidor, Photographer seguido) {

        Follow existingFollow = isFollowed(seguidor, seguido);
        return (existingFollow) != null ? "Deixar de seguir" : "Seguir";
    }


    // Executa a açao de seguir ou deixar de seguir
    public boolean handleFollowAction(Long id, Long id2) {

        // 01 -> Quem vai seguir
        Photographer Seguidor = photographerService.findById(id);
        // 02 -> Quem vai ser seguido
        Photographer Seguido = photographerService.findById(id2);

        Follow Follow = isFollowed(Seguidor, Seguido); // Seguido 

        if (Follow == null) {
            follow(Seguidor, Seguido);
            return true;
        } else {
            unfollow(Seguidor, Seguido, Follow);
            return false;
        }
    }


    // Seguido
    public Follow isFollowed(Photographer Seguidor, Photographer Seguido) {

        List<Follow> listaDeSeguidos = Seguidor.getFollowing();

        if (listaDeSeguidos == null) {
            return null;
        }

        for (Follow f : listaDeSeguidos) {
            if (f.getFollowed().getId().equals(Seguido.getId())) {
                return f;
            }
        }
        return null;
    }


    // Seguidor
    public Follow isFollower(Photographer Seguido, Photographer Seguidor) {
        List<Follow> listaDeSeguidores = Seguido.getFollowers();

        if (listaDeSeguidores == null) {
            return null;
        }

        for (Follow f : listaDeSeguidores) {
            if (f.getFollower().getId().equals(Seguidor.getId())) {
                return f;
            }
        }
        return null;
    }


    public void unfollow(Photographer p1, Photographer p2, Follow follow) {

        List<Follow> listaDeSeguindo = p1.getFollowing();
        List<Follow> listaDeSeguidores = p2.getFollowers();
        listaDeSeguindo.remove(follow);
        listaDeSeguidores.remove(follow);
        followService.delete(follow);
        photographerRepository.save(p1);
        photographerRepository.save(p2);

    }


    public void follow(Photographer Seguidor, Photographer Seguido) {

        List<Follow> listaDeSeguindo = Seguidor.getFollowing();
        List<Follow> listaDeSeguidores = Seguido.getFollowers();

        // Adicionar na lista seguindos do fotógrafo que solicitou o follow
        FollowId followId = new FollowId(Seguidor.getId(), Seguido.getId());
        Follow follow = new Follow(followId, Seguidor, Seguido);
        followService.save(follow);
        // -------------------------------------------------------------
        listaDeSeguindo.add(follow);
        listaDeSeguidores.add(follow);
        // --------------------------------------------------
        // Adicionar na lista de seguidores do fotógrafo que foi seguido
        //FollowId followId2 = new FollowId(p2.getId(),p1.getId());
        //Follow follow2 = new Follow(followId2, p2, p1);
        photographerRepository.save(Seguidor);
        photographerRepository.save(Seguido);

    }


    public void removePhotographerFromArray(List<Photographer> photographers, Photographer photographer) {
        Long idPhotographer = photographer.getId();
        photographers.removeIf(p -> p.getId().equals(idPhotographer));
    }


    public boolean removePhotographerAdminFromArray(List<Photographer> photographers, Photographer photographer) {
        Long idPhotographer = photographer.getId();
        return photographers.removeIf(p -> p.getId().equals(idPhotographer));
    }


    public List<Photographer> findAllFollowing(Long id) {
        return photographerService.findAllFollowing(id);
    }


    public List<Photographer> findAllFollowers(Long id) {
        return photographerService.findAllFollowers(id);
    }


    public Photographer getLoggedPhotographer(HttpSession session) {
        return sessionService.getLoggedPhotographer(session);
    }


    public void logoutPhotographer(HttpSession session) {
        sessionService.logoutPhotographer(session);
    }


    public String checkBlockedStatus(Photographer photographer) {
        return photographerService.checkBlockedStatus(photographer);
    }


    public void handleBlockAction(Long id) {
        photographerService.handleBlockAction(id);
    }

    public void uploadPhoto(Long id, Photo photo, MultipartFile file, List<String> tagNames) throws Exception {
        Photographer photographer = photographerService.findById(id);
        photo.setPhotographer(photographer);

        photoService.create(photo,file);

        if (photo.getId() == null) {
            throw new RuntimeException("Photo ID is null after saving!");
        }

        List<Photo> lista = photographer.getPhotos();
        lista.add(photo);
        photographer.setPhotos(lista);
        photographerRepository.save(photographer);

        List<Tag> tags = getOrCreateTags(tagNames);

        createPhotoTags(photo, tags);
    }


    public String convertPhotoToBase64(Photo photo) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photo.getImageData());
    }


    public String convertAvatarToBase64(Photographer photographer) {
        if (photographer.getProfilePicture() == null) {
            return "https://media.cdnandroid.com/60/1f/2a/ad/b6/imagen-dazz-cam-vintage-film-camera-retro-art-0ori.jpg";
        }
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photographer.getProfilePicture());
    }


    public List<Photo> showPhotos(Long id) {
        Photographer photographer = photographerService.findById(id);
        List<Photo> photos = photographerService.findAllPhotos(photographer.getId());
        List<String> photoUrls = new ArrayList<>();

        for (Photo photo : photos) {
            photoUrls.add(convertPhotoToBase64(photo));
            photo.setImageUrl(convertPhotoToBase64(photo));
        }
        return photos;
    }


    public void updatePhotographer(Photographer photographer) {
        photographerService.updatePhotographer(photographer);
    }


    public Photographer updateAvatar(Photographer photographer, MultipartFile file) throws IOException {
        photographer.setProfilePicture(file.getBytes());
        photographer.setProfilePictureUrl(convertAvatarToBase64(photographer));
        photographerService.updatePhotographer(photographer);
        return photographer;
    }


    public Photo findPhotoById(Long id) {
        Photo p = photoService.findById(id);
        return p;
    }


    public void saveComment(Comment comment) {
        commentService.save(comment);
    }


    public List<Comment> findAllCommentOfPhoto(Photo photo) {
        return commentService.findByPhotoOrderByCreatedAtDesc(photo);
    }

    public List<CommentProjection> findAllCommentOfPhotoAtAsc(Long photoId) {
        return commentService.findByPhotoOrderByCreatedAtAsc(photoId);
    }

    public String handleLikeAction(long photoid, Long photographerid) {
        return LikeService.handleLikeAction(photoid, photographerid) ? "Descurtir" : "Curtir";
    }


    public Integer getLikeCountOfPhoto(Long idPhoto) {
        return LikeService.getLikeCount(idPhoto);
    }


    //public List<String> getLikeStatusOfPhotos(Long idPhotographer){


    //return LikeService.getLikeStatus(idPhotographer);
    //}


    public List<String> getStatusLikeOfPhotos(Long idPhotographer) {

        List<Photo> photos = photoService.getLikesByPhotographerId(idPhotographer);

        List<String> status = new ArrayList<>();

        for (Photo p : photos) {
            if (LikeService.isLiked(p.getId(), idPhotographer)) {
                status.add("Descurtir");
            } else {
                status.add("Curtir");
            }
        }

        return status;
    }


    public List<String> getStatusLikeOfPhotosOtherPhotographer(Long idPhotographer, Long PhotographerSessionId) {

        List<Photo> photos = photoService.getLikesByPhotographerId(idPhotographer);

        List<String> status = new ArrayList<>();

        for (Photo p : photos) {
            if (LikeService.isLiked(p.getId(), PhotographerSessionId)) {
                status.add("Descurtir");
            } else {
                status.add("Curtir");
            }
        }

        return status;
    }

    public Tag addTag(String text){
        Tag tag = new Tag();
        tag.setTagName(text);
        return tagService.AddTag(tag);
    }

    public void createPhotoTags(Photo photo, List<Tag> tags) {
        for (Tag tag : tags) {
            PhotoTagId photoTagId = new PhotoTagId(photo.getId(), tag.getId());
            PhotoTag photoTag = new PhotoTag(photoTagId, photo, tag);

            photoTag = photoTagService.create(photoTag);

            if (!photo.getPhotoTags().contains(photoTag)) {
                photo.getPhotoTags().add(photoTag);
            }

            if (!tag.getPhotoTags().contains(photoTag)) {
                tag.getPhotoTags().add(photoTag);
            }
        }

        photoService.save(photo);
        for (Tag tag : tags) {
            tagService.save(tag);
        }
    }





    public List<Tag> getAllTags() {
        return tagService.GetAllTags();
    }

    public List<Tag> GetTagsAlike(String name){
        return tagService.GetTagsAlike(name);
    }

    public List<Tag> getOrCreateTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();

        for (String tagName : tagNames) {

            Optional<Tag> existingTag = tagService.GetTag(tagName);
            Tag tag;

            if (existingTag.isPresent()) {
                tag = existingTag.get();
            } else {
                tag = new Tag();
                tag.setTagName(tagName);
                tag = tagService.AddTag(tag);
            }


            if (tag.getId() != null) {
                tags.add(tag);
            } else {
                throw new IllegalStateException("Erro ao criar tag: ID não foi gerado.");
            }
        }

        return tags;
    }

    public Map<Long, List<Tag>> getTagsForPhotos(List<Photo> photos) {
        Map<Long, List<Tag>> photoTagsMap = new HashMap<>();

        for (Photo photo : photos) {
            List<Tag> tags = photo.getPhotoTags().stream()
                    .map(PhotoTag::getTag)
                    .collect(Collectors.toList());
            photoTagsMap.put(photo.getId(), tags);
        }

        return photoTagsMap;
    }

    public Comment findCommentById(Long commentId) {
        return commentService.findCommentById(commentId);
    }


    public void updateComment(Comment comment) {
        commentService.save(comment);
    }


    public void deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
    }

    public void generatePDF(List<CommentProjection> comments) throws FileNotFoundException {
        commentService.generatePDF(comments);
    }
}
