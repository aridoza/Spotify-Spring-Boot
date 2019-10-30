package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.Song;

public interface SongService {
    public Iterable<Song> listOfSongs();

    public Song createSong(Song song);
}
