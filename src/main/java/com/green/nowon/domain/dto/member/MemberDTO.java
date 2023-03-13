package com.green.nowon.domain.dto.member;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.green.nowon.domain.dto.board.BoardUpdateDTO;
import com.green.nowon.security.MyRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private long mno;
    private String email;
    private String pass;
    private String name;
    private String nickName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean social;
    private boolean deleted;

    private Set<MyRole> roles = new HashSet<>();

    public MemberDTO addRole(MyRole role){
        this.roles.add(role);
        return this;
    }

	public void updateMember(MemberUpdateDTO dto) {
		
	}

	
}
