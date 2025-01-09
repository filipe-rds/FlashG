package br.edu.ifpb.pweb2.flashg.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import br.edu.ifpb.pweb2.flashg.entity.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.entity.Follow;
import br.edu.ifpb.pweb2.flashg.entity.FollowId;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FacadeService {

    @Autowired
    private PhotographerService photographerService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private FollowService followService;
    
    public List<Photographer> findByUsernameStartingWith(String nome){

        return photographerService.findByUsernameStartingWith(nome);
    
    }

    public Photographer findByIdPhotographer(Long id){
        return photographerService.findById(id);
    }

    public List<Photographer> findAll(){
        return photographerService.findAll();
    }

    public String checkFollowStatus(Photographer seguidor, Photographer seguido ){

        Follow existingFollow = isFollowed(seguidor, seguido);
        return (existingFollow) != null ? "Deixar de seguir" : "Seguir";
    }

    // Executa a açao de seguir ou deixar de seguir
    public boolean handleFollowAction(Long id, Long id2){

        // 01 -> Quem vai seguir
        Photographer Seguidor = photographerService.findById(id);
        // 02 -> Quem vai ser seguido
        Photographer Seguido = photographerService.findById(id2);
        
        Follow Follow = isFollowed(Seguidor, Seguido); // Seguido 

        if(Follow == null){
            follow(Seguidor, Seguido);
            return true;
        }
        else{
            unfollow(Seguidor,Seguido,Follow);
            return false;
        }
    }
    
     // Seguido 
     public Follow isFollowed(Photographer Seguidor,Photographer Seguido){ 

        List<Follow> listaDeSeguidos = Seguidor.getFollowing();

        if (listaDeSeguidos == null) {
            return null;
        } 
    
        for(Follow f : listaDeSeguidos){
            if(f.getFollowed().getId().equals(Seguido.getId())){
                return f;
            }
        }
        return null;
    }

    // Seguidor
    public Follow isFollower(Photographer Seguido,Photographer Seguidor){
        List<Follow> listaDeSeguidores = Seguido.getFollowers();

        if (listaDeSeguidores == null) {
            return null;
        }
        
        for(Follow f : listaDeSeguidores){
            if(f.getFollower().getId().equals(Seguidor.getId())){
                return f;
            }
        }
        return null;
    }
    public void unfollow(Photographer p1,Photographer p2,Follow follow){

        List<Follow> listaDeSeguindo = p1.getFollowing();
        List<Follow> listaDeSeguidores = p2.getFollowers();
        listaDeSeguindo.remove(follow);
        listaDeSeguidores.remove(follow);
        followService.delete(follow);
        photographerRepository.save(p1);
        photographerRepository.save(p2);

    }

    public void follow(Photographer Seguidor, Photographer Seguido){

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
    

    public List<Photographer> findAllFollowing(Long id){
        
        List<Photographer> photographers  =photographerService.findAllFollowing(id);
        return photographers;
    }


    public void uploadPhoto(Long id,Photo photo,MultipartFile file) throws Exception {
        Photographer photographer = photographerService.findById(id);
//        Photo photo = new Photo();
        photo.setPhotographer(photographer);
        //chama metodo que vai inserir a foto no banco
        photoService.create(photo,file);
        //adiciona a foto a lista de fotos do fotografo
        List<Photo> lista = photographer.getPhotos();
        lista.add(photo);
        photographer.setPhotos(lista);
        photographerRepository.save(photographer);

    }

    public String convertPhotoToBase64(Photo photo) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photo.getImageData());
    }

    public List<Photo> showPhotos(Long id){
        Photographer photographer = photographerService.findById(id);
        List<Photo> photos = photographerService.findAllPhotos(photographer.getId());
        List<String> photoUrls = new ArrayList<>();

        for (Photo photo : photos) {
            photoUrls.add(convertPhotoToBase64(photo));
            photo.setImageUrl(convertPhotoToBase64(photo));
        }
        return photos;
    }



}
