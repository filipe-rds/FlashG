package br.edu.ifpb.pweb2.flashg.service;

import java.io.IOException;
import java.util.List;

import br.edu.ifpb.pweb2.flashg.entity.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.entity.Follow;
import br.edu.ifpb.pweb2.flashg.entity.FollowId;
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
    
    public List<Photographer> findByUsernameStartingWith(String nome){

        return photographerService.findByUsernameStartingWith(nome);
    
    }

    public Photographer findByIdPhotographer(Long id){
        return photographerService.findById(id);
    }

    public List<Photographer> findAll(){
        return photographerService.findAll();
    }

    public void follow(Long id, Long id2){

        // 01 -> Quem vai seguir
        Photographer photographer = photographerService.findById(id);
        // 02 -> Quem vai ser seguido
        Photographer photographer2 = photographerService.findById(id2);

        List<Follow> listaDeSeguindo = photographer.getFollowing();
        List<Follow> listaDeSeguidores = photographer2.getFollowers();

        boolean entrouNoIf = false;

        for (Follow f : listaDeSeguindo) {
            if(f.getFollowed().getId() == id2){
                listaDeSeguindo.remove(f);
                photographerRepository.save(photographer);
                entrouNoIf = true;
            }
        }

        for(Follow f : listaDeSeguidores){
            if(f.getFollower().getId() == id){
                listaDeSeguidores.remove(f);
                photographerRepository.save(photographer2);
                if(entrouNoIf){
                    return;
                }
            }
        }
        // --------------------------------------------------
        // Adicionar na lista seguindos do fotógrafo que solicitou o follow
        FollowId followId = new FollowId(id, id2);
        Follow follow = new Follow(followId, photographer, photographer2);
        listaDeSeguindo.add(follow);
        photographerRepository.save(photographer);
        // --------------------------------------------------
        // Adicionar na lista de seguidores do fotógrafo que foi seguido
        FollowId followId2 = new FollowId(id2,id);
        Follow follow2 = new Follow(followId2, photographer2, photographer);
        listaDeSeguidores.add(follow2);
        photographerRepository.save(photographer2);


    }

    public List<Photographer> findAllFollowing(Long id){
        return photographerService.findAllFollowing(id);
    }


    public void uploadPhoto(Long id, MultipartFile file) throws IOException {
        Photographer photographer = photographerService.findById(id);
        Photo photo = new Photo();
        photo.setPhotographer(photographer);
        //chama metodo que vai inserir a foto no banco
        photoService.create(photo,file);
        //adiciona a foto a lista de fotos do fotografo
        List<Photo> lista = photographer.getPhotos();
        lista.add(photo);
        photographer.setPhotos(lista);
        photographerRepository.save(photographer);

    }

}
