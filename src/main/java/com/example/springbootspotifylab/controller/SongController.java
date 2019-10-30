package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.Song;
import com.example.springbootspotifylab.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping
    public Iterable<Song> listOfSongs() {
        return songService.listOfSongs();
    }

    @PostMapping
    public Song createSong(@RequestBody Song song) {
        return songService.createSong(song);
    }

}
