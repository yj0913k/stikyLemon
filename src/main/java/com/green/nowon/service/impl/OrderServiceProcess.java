package com.green.nowon.service.impl;

import com.green.nowon.domain.dto.goods.OrderGoodsDTO;
import com.green.nowon.domain.dto.goods.OrderGoodsListDTO;
import com.green.nowon.domain.dto.member.DeliveryInfoDTO;
import com.green.nowon.domain.dto.member.DeliveryListDTO;
import com.green.nowon.domain.dto.member.OrderGoodsInsertDTO;
import com.green.nowon.domain.dto.member.OrderInsertDTO;
import com.green.nowon.domain.entity.*;
import com.green.nowon.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceProcess implements OrderService {
    @Autowired
    GoodsEntityRepository goodsRepo;
    @Autowired
    private DeliveryEntityRepository deliveryRepo;
    @Autowired
    MemberEntityRepository memRepo;
    @Autowired
    CartGoodsDetailRepository cartGoodsRepo;
    @Autowired
    CartEntityRepository cartRepo;
    @Autowired
    OrderEntityRepository orderRepo;
    @Autowired
    OrderGoodsEntityRepository orderGoodsRepo;
    
    @Override
    public void baseOfdeliveries(String email, Model model) {
        model.addAttribute("base", deliveryRepo.findByBaseAndMember_email(true,email)
                .map(DeliveryListDTO::new)
                .orElseThrow()
        );
    }

    //회원의 모든 배송지
    @Override
    public void deliveries(String email, Model model) {
        model.addAttribute("list", deliveryRepo.findAllByMember_email(email).stream()
                .map(DeliveryListDTO::new)
                .collect(Collectors.toList())
        );
    }

    @Override
    public long deliveryInfoSave(DeliveryInfoDTO dto, String email) {
        return deliveryRepo.save(dto.toEntity()
                        .base(deliveryRepo.countByMember_email(email)==0?true:false)//배송지정보가 없으면 base=true
                        .member(memRepo.findByEmail(email).orElseThrow()))
                .getNo();//배송지 정보등록 완료후 pk값리턴
    }


    @Transactional
    @Override
    public void orderGoods(OrderGoodsDTO dto, Model model) {
        model.addAttribute("list", goodsRepo.findById(dto.getGoodsNo())
                .map(OrderGoodsListDTO::new)
                .get()
                .quantity(dto.getQuantity()));
    }

    @Override
    public void orderGoodsFromCart(long mno, Model model) {
        List<OrderGoodsDTO> result =  cartGoodsRepo.findByCart(cartRepo.findByMember_mno(mno).get())
                .stream()
                .map(OrderGoodsDTO::new)
                .collect(Collectors.toList());
        List<OrderGoodsListDTO> list = new ArrayList<>();
        result.forEach(s->
                list.add(goodsRepo.findById(s.getGoodsNo())
                        .map(OrderGoodsListDTO::new)
                        .get()
                        .quantity(s.getQuantity())));

        model.addAttribute("list", list);


    }

    //주문완료후 결제정보 DB저장
    @Override
    public void save(OrderInsertDTO dto, String email) {

        //주문
        OrderEntity orderResult=orderRepo.save(OrderEntity.builder()
                .status(OrderStaus.ORDER)
                .paymentNo(dto.getPaymentNo())
                .member(memRepo.findByEmail(email).orElseThrow())
                //.delivery(DeliveryEntity.builder().no(dto.getDeliveryNo()).build())
                .delivery(deliveryRepo.findById(dto.getDeliveryNo()).orElseThrow())
                .build());

        //orderItem
        //아이템 재고 주문수량만큼 감소
        for(OrderGoodsInsertDTO oiDto : dto.getOrderGoods()) {
            orderGoodsRepo.save(OrderGoodsEntity.builder()
                    .quantity(oiDto.getQuantity())
                    .goods(GoodsEntity.builder().no(oiDto.getGoodsNo()).build())
                    .order(orderResult)
                    .build());
        }
    }
    //오더정보가져오기~
    @Override
	public void getOrders(String email, Model model) {
    	 List<OrderEntity> result = orderRepo.findByMemberEmail(email);
    	 
    	 result.forEach(e->{
    		 List<OrderGoodsEntity> OGE = (orderGoodsRepo.findByOrder(e));
    			
    		 
    	 });
    	 //model.addAttribute("list",
	}
    
    
	

	

//    //이짱이 페이먼트 연결한것 배송지 등록창이 보이게끔!
//    @Override
//    public void allOfdeliveries(String email, Model model) {
//        model.addAttribute("base", deliveryRepo.findByMember_email(email)
//                .stream()
//                .map(DeliveryListDTO::new)
//        );
//
//    }
    @Override
	public void allOfdeliveries(String email, Model model) {
		// TODO Auto-generated method stub
		
	}
}
