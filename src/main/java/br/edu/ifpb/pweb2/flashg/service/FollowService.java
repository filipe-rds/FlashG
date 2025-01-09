package br.edu.ifpb.pweb2.flashg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.entity.Follow;
import br.edu.ifpb.pweb2.flashg.repository.FollowRepository;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public void save(Follow follow) {
        followRepository.save(follow);
    }

    public void delete(Follow follow) {
        followRepository.delete(follow);
    }
    
}
