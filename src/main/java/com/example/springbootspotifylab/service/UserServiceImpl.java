package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.config.JwtUtil;
import com.example.springbootspotifylab.model.Song;
import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.repositories.SongRepository;
import com.example.springbootspotifylab.repositories.UserRepository;
import com.example.springbootspotifylab.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public String login(User user){
        User newUser = userRepository.findByUsername(user.getUsername());

        if (newUser != null && bCryptPasswordEncoder.matches(user.getPassword(), newUser.getPassword())) {
            UserDetails userDetails = loadUserByUsername(newUser.getUsername());
            return jwtUtil.generateToken(userDetails);
        }

//        if(userRepository.login(user.getUsername(), user.getPassword()) != null){
//            UserDetails userDetails = loadUserByUsername(user.getUsername());
//            return jwtUtil.generateToken(userDetails);
//        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);

        if(user==null)
            throw new UsernameNotFoundException("User null");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
                true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(user.getUserRole().getName()));

        return authorities;
    }

    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }


    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String createUser(User newUser) {
        UserRole userRole = userRoleService.getRole(newUser.getUserRole().getName());
        newUser.setUserRole(userRole);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        System.out.println(userRole);

        if(userRepository.save(newUser) != null){
            UserDetails userDetails = loadUserByUsername(newUser.getUsername());
            return jwtUtil.generateToken(userDetails);
        }
        return null;
    }

    @Override
    public HttpStatus deleteById(Long userId) {
        userRepository.deleteById(userId);
        return HttpStatus.OK;
    }



    @Override
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addSong(String username, Long songId) {
        User user = userRepository.findByUsername(username);
        Song song = songRepository.findById(songId).get();
        user.addSong(song);
        userRepository.save(user);
        return user;
    }

    @Override
    public User deleteSongFromUser(String username, Long songId) {
        User currentUser = userRepository.findByUsername(username);
        Song song = songRepository.findById(songId).get();
        currentUser.getSongs().remove(song);
        //currentUser.deleteSong(song);
        userRepository.save(currentUser);
        return currentUser;
    }


}
