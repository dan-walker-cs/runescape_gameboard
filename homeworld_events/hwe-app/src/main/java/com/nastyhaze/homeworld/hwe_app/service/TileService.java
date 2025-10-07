package com.nastyhaze.homeworld.hwe_app.service;

import com.nastyhaze.homeworld.hwe_app.domain.Player;
import com.nastyhaze.homeworld.hwe_app.domain.Tile;
import com.nastyhaze.homeworld.hwe_app.repository.TileRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.TileMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 *  Service layer between TileController & TileRepository.
 *  Provides logic to retrieve and mutate repository data for the API.
 *  This layer is BLOCKING & NON-REACTIVE.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TileService {

    private final TileRepository tileRepository;
    private final TileMapper tileMapper;


//    public List<TileResponse> findAllTiles() {
//        return tileRepository.findAll()
//            .stream()
//            .map(tileMapper::toResponse)
//            .toList();
//    }

    // TODO: Fake data for now, use above when DB integrated.
    public List<TileResponse> findAllTiles() {
        Player samplePlayer = new Player();
        samplePlayer.setDisplayName("samplePlayer");

        List<Tile> sampleTiles = new ArrayList<>();
        Tile sampleTile1 = new Tile();
        sampleTile1.setId(1L);
        sampleTile1.setTitle("TileSampleEntity1");
        sampleTile1.setDescription("testDesc1");
        sampleTile1.setWeight(2);
        sampleTile1.setReserved(false);
        sampleTile1.setCompleted(true);
        sampleTile1.setCompletedBy(samplePlayer);
        sampleTile1.setIconPath("assets/icons/sample_tile_icon.png");

        Tile sampleTile2 = new Tile();
        sampleTile2.setId(2L);
        sampleTile2.setTitle("TileSampleEntity2");
        sampleTile2.setDescription("testDesc2");
        sampleTile2.setWeight(5);
        sampleTile2.setReserved(true);
        sampleTile2.setReservedBy(samplePlayer);
        sampleTile2.setCompleted(false);
        sampleTile2.setIconPath("assets/icons/sample_tile_icon.png");

        Tile sampleTile3 = new Tile();
        sampleTile2.setId(3L);
        sampleTile2.setTitle("TileSampleEntity3");
        sampleTile2.setDescription("testDesc3");
        sampleTile2.setWeight(4);
        sampleTile2.setReserved(false);
        sampleTile2.setCompleted(false);
        sampleTile2.setIconPath("assets/icons/sample_tile_icon.png");

        sampleTiles.add(sampleTile1);
        sampleTiles.add(sampleTile2);
        sampleTiles.add(sampleTile3);

        return sampleTiles
            .stream()
            .map(tileMapper::toResponse)
            .toList();
    }
}
