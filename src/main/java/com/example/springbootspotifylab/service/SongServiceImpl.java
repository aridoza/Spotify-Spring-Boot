package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.Song;
import com.example.springbootspotifylab.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository songRepository;
    @Override
    public Iterable<Song> listOfSongs() {
        return songRepository.findAll();
    }

    @Override
    public Song createSong(Song song) {
        return songRepository.save(song);
    }
}
