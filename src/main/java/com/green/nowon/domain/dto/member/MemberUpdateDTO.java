package com.green.nowon.domain.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Setter
@Getter
public class MemberUpdateDTO {
		private long mno;
		private String email;
	    private String pass;
	    private String name;
	    private String nickName;
}
