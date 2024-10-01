package org.badminton.api.league.model.dto;

import java.time.LocalDateTime;

import org.badminton.domain.league.entity.LeagueParticipantEntity;

public record LeagueParticipationCancelResponse(

	Long leagueId,
	Long clubMemberId,
	LocalDateTime createdAt,
	LocalDateTime deletedAt
) {

	public static LeagueParticipationCancelResponse entityToLeagueParticipationCancelResponse(
		LeagueParticipantEntity entity) {

		return new LeagueParticipationCancelResponse(entity.getLeague().getLeagueId(),
			entity.getClubMember().getClubMemberId(),
			entity.getCreatedAt(), entity.getModifiedAt());
	}
}
