package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;

import org.badminton.domain.domain.club.Club;

public record ClubUpdateResponse(
	Long clubId,
	String clubName,
	String clubDescription,
	String clubImage,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static ClubUpdateResponse fromClubEntity(Club club) {
		return new ClubUpdateResponse(
			club.getClubId(),
			club.getClubName(),
			club.getClubDescription(),
			club.getClubImage(),
			club.getCreatedAt(),
			club.getModifiedAt()
		);
	}
}