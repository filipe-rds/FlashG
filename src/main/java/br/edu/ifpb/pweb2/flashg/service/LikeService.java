package br.edu.ifpb.pweb2.flashg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.entity.LikeId;
import br.edu.ifpb.pweb2.flashg.entity.Likee;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.repository.LikeRepository;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PhotoService photoService;
    @Autowired
    PhotographerService photographerService;

    public boolean handleLikeAction(long photoid, Long photographerid){ 

        // se existir curtida, descurtir
        if(isLiked(photoid, photographerid)){
            unlikePhoto(photoid, photographerid);
            return false;
        }
        // se não existir curtida, curtir
        else{
            likePhoto(photoid, photographerid);
            return true;
        }
    }


    public boolean isLiked(long photoid, Long photographerid) {
        return likeRepository.findLikeByPhotoAndPhotographer(photoid, photographerid).isPresent();
    }

    public void likePhoto(long photoid, Long photographerid){

        LikeId likeId = new LikeId(photoid, photographerid);
        Photo Photo = photoService.findById(photoid);
        Photographer photographer = photographerService.findById(photographerid);
        Likee like = new Likee(likeId, Photo, photographer);
        likeRepository.save(like);

        
    }

    public void unlikePhoto(long photoid, Long photographerid){
        likeRepository.delete(likeRepository.findLikeByPhotoAndPhotographer(photoid, photographerid).get());
    }

    public Integer getLikeCount(Long photoid){
        return likeRepository.getLikeCount(photoid);
    }

    //public 
    



    
}
