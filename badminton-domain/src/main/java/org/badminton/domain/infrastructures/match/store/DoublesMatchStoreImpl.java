package org.badminton.domain.infrastructures.match.store;

import lombok.RequiredArgsConstructor;
import org.badminton.domain.domain.match.entity.DoublesMatchEntity;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.infrastructures.match.repository.DoublesMatchRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoublesMatchStoreImpl implements DoublesMatchStore {

    private final DoublesMatchRepository doublesMatchRepository;

    @Override
    public void deleteDoublesBracket(Long leagueId) {
        doublesMatchRepository.deleteAllByLeague_LeagueId(leagueId);
    }

    @Override
    public void store(DoublesMatchEntity doublesMatch) {
        doublesMatchRepository.save(doublesMatch);
    }
}
