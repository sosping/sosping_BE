package sosping.be.domain.member.domain;

import lombok.Getter;

@Getter
public enum MemberRoleType {
    TUTOR("ROLE_TUTOR");

    private final String role;

    MemberRoleType(String role) {
        this.role = role;
    }
}
