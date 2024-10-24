package org.badminton.domain.domain.club.info;

import java.time.LocalDateTime;
import java.util.Map;

import org.badminton.domain.common.enums.MemberTier;
import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.club.ClubMemberCountByTier;

public record ClubDetailsInfo(
	Long clubId,
	String clubName,
	String clubDescription,
	String clubImage,
	ClubMemberCountByTier clubMemberCountByTier,
	int clubMemberCount,
	LocalDateTime createdAt,
	boolean isClubMember
) {

	public static ClubDetailsInfo fromClubEntityAndMemberCountByTier(ClubSummaryInfo clubSummaryInfo,
		Map<MemberTier, Long> memberCountByTier,
		boolean isClubMember, int clubMembersCount) {
		return new ClubDetailsInfo(
				clubSummaryInfo.clubId(),
				clubSummaryInfo.clubName(),
				clubSummaryInfo.clubDescription(),
				clubSummaryInfo.clubImage(),
				ClubMemberCountByTier.ofClubMemberCountResponse(memberCountByTier),
				clubMembersCount,
				clubSummaryInfo.createdAt(),
				isClubMember
		);
	}
}