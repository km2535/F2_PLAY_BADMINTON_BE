package org.badminton.api.interfaces.member.dto;

import org.badminton.api.interfaces.member.validator.MemberImageValidator;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 수정 DTO")
public record MemberUpdateRequest(

        @MemberImageValidator
        String profileImageUrl
) {

}
