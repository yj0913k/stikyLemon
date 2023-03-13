package com.green.nowon.domain.entity;

import com.green.nowon.domain.dto.goods.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SequenceGenerator(name = "sticky_gen_re",
	sequenceName = "sticky_seq_re", initialValue = 1, allocationSize = 1
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReviewEntity extends BaseDateEntity{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long rno;
	
	@Column(nullable = false)
	private String text;

	@Column(nullable = false)
	private long rate;
	
	//작성자 fk
	@JoinColumn(name = "mno", nullable = false)
	@ManyToOne(cascade = CascadeType.DETACH)
	private MemberEntity member;

	@JoinColumn(name = "no", nullable = false)
	@ManyToOne(cascade = CascadeType.DETACH)
	private GoodsEntity goods;




}