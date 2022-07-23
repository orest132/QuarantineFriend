package me.kollcaku.QuarantineFriends.service;

import lombok.AllArgsConstructor;
import me.kollcaku.QuarantineFriends.configuration.impl.UserDetailsImpl;
import me.kollcaku.QuarantineFriends.entity.UserEntity;
import me.kollcaku.QuarantineFriends.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Username not found!");
        }
        return new UserDetailsImpl(user);
    }
}
