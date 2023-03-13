package com.green.nowon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "delivery")
@Entity
public class DeliveryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	private boolean base;//기본배송지여부
	
	private String deliveryAddrName;
	
	private String receiverName;
	private String phone1;
	private String phone2;
	
	private String postcode;
	private String roadAddress;
	private String jibunAddress;
	private String detailAddress;
	private String extraAddress;
	
	@JoinColumn//member_mno
	@ManyToOne
	private MemberEntity member;
	
	public DeliveryEntity member(MemberEntity member) {
		this.member=member;
		return this;
	}

	public DeliveryEntity base(boolean base) {
		this.base=base;
		return this;
	}

}
