package com.green.nowon.service.impl;

import com.green.nowon.domain.dto.goods.*;
import com.green.nowon.domain.entity.*;
import com.green.nowon.utils.MyFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.green.nowon.domain.dto.goods.GoodsDetailDTO;
import com.green.nowon.domain.dto.goods.GoodsInsertDTO;
import com.green.nowon.service.GoodsService;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsServiceProcess implements GoodsService {

    @Value("${file.location.temp}")
    private String locationTemp;

    @Value("${file.location.upload}")
    private String locationUpload;


    @Autowired
    GoodsEntityRepository goodsRepo;
    @Autowired
    CategoryGoodsEntityRepository cateGoodsRepo;
    @Autowired
    GoodsImgRepository imgRepo;
    @Autowired
    CategoryEntityRepository cateRepo;

    @Autowired
    ReviewEntityRepository reviewRepo;

    List<CategoryEntity> cates;


    //재귀메서드
    private void categoryList(CategoryEntity ca) {
        if (ca == null) return;
        cates.add(ca);
        categoryList(ca.getParent());
    }

    @Transactional
    @Override
    public void goodsOfCategory(long cateNo, Model model) {
        //카테고리에 해당하는 상품들 모두
        cates = new ArrayList<>();
        categoryList(cateRepo.findById(cateNo).get());

        model.addAttribute("cates", cates);
        model.addAttribute("list", cateGoodsRepo.findAllByCategoryNo(cateNo)
                .stream()
                .map(GoodsListDTO::new)
                .collect(Collectors.toList()));

    }

    //상품등록처리
    @Override
    public void save(GoodsInsertDTO dto) {
        //카테고리와 상품 등록
        //이미지 정보 등록, temp->실제 upload위치
        long[] categoryNo = dto.getCategoryNo();

        GoodsEntity entity = goodsRepo.save(dto.toGoodsEntity());
        for (long no : categoryNo) {
            cateGoodsRepo.save(CategoryGoodsEntity.builder()
                    .goods(entity)
                    .category(cateRepo.findById(no).get())
                    .build());
        }

        dto.toItemListImgs(entity, locationUpload).forEach(imgRepo::save);
        //이미지 temp->temp->실제 upload위치
    }

    //상품업데이트처리
    @Transactional
    @Override
    public void update(GoodsUpdateDTO dto) {
        GoodsEntity entity =goodsRepo.findById(dto.getNo()).orElseThrow().update(dto);
                //goodsRepo.save(dto.toGoodsEntity());
        goodsRepo.save(entity);

        Map<String, List<String>> infos = dto.toGoodsImg(entity, locationUpload);
        List<String> defChange = infos.get("defImg");
        List<String> deletedList = infos.get("delete");
        List<String> addedList = infos.get("add");
        if (defChange != null) {
            if (defChange.size() != 0) {
                imgRepo.save(imgRepo.findByDefImgAndGoodsNo(true, dto.getNo()).orElseThrow()
                        .update(defChange.get(0), defChange.get(1)));
            }
        }
        for (String newName : deletedList) {
            imgRepo.deleteByNewName(newName);
        }

        for (int i = 0; i < addedList.size(); i++) {
            String newName = addedList.get(i);
            String orgName = dto.getOrgName().get(dto.getNewName().indexOf(newName));
            imgRepo.save(GoodsImg.builder()
                    .orgName(orgName)
                    .newName(newName)
                    .url(locationUpload)
                    .goods(entity)
                    .build());
        }
    }

    //상품 삭제처리, 연관된 테이블:현재 카테고리, 이미지파일, ,차후 주문상세, 장바구니디테일 (삭제해야하나 싶기도함)
    @Transactional
    @Override
    public void removeGoods(long no) {
        //순서는 상품 참조하고있는 애들 먼저 삭제하면 될듯.
        //이미지, 카테고리, 상품 순?
        //이미지 삭제
        imgRepo.deleteByGoodsNo(no);
        //카테고리 삭제
        cateGoodsRepo.deleteByGoodsNo(no);
        //상품삭제
        goodsRepo.deleteById(no);
    }

    @Override
    public Map<String, String> fileTempUpload(MultipartFile gimg) {
        return MyFileUtils.fileUpload(gimg, locationTemp);
    }


    //관리자페이지 상품리스트 보여주기
    @Transactional
    @Override
    public void getList(Model model) {
        List<GoodsEntity> list = goodsRepo.findAll();
        model.addAttribute("list", list.stream()
                .map(GoodsListDTO::new)
                .collect(Collectors.toList()));//List<GoodsListDTO>
    }

    //관리자페이지에서 상품 수정버튼 눌렀을 때 페이지 이동 전 객체담기
    @Transactional
    @Override
    public void adminDetail(long no, Model model) {
        GoodsEntity entity = goodsRepo.findById(no).orElseThrow();
        model.addAttribute("dto", new GoodsDetailDTO(entity));

        //카테고리 수정은 일단 불가. 뿌려주기만 함.
        List<String> cates = cateGoodsRepo.findByGoodsNo(no).stream()
                .map(e -> e.getCategory().getName())
                .collect(Collectors.toList());
        model.addAttribute("cates", cates);


        //이미지정보도 가져가고, 뿌려주는것도 해야할 듯.
        List<GoodsImg> result = imgRepo.findAllByGoodsNo(no);
        List<GoodsDetailImgDTO> imgList = result.stream()
                .map(GoodsDetailImgDTO::new)
                .collect(Collectors.toList());
        model.addAttribute("imgs", imgList);

        List<ReviewEntity> des = reviewRepo.findAllByGoodsNoOrderByCreatedDateDesc(no);
        List<ReviewListDTO> reviewlist = des.stream().map(ReviewListDTO::new).collect(Collectors.toList());
        model.addAttribute("reviews", reviewlist);

        List<Long> rates = des.stream().map(e -> e.getRate()).collect(Collectors.toList());
        long sum = 0;
        for (long num : rates) {
            sum += num;
        }
        double average = (double) sum / rates.size();
        model.addAttribute("average", Math.round(average*100)/100.0);
    }

    @Override
    public void findAllGoods(Model model) {
        List<GoodsListDTO> list = goodsRepo.findAll().stream().map(GoodsListDTO::new).collect(Collectors.toList());

        for(GoodsListDTO dto: list){
            List<Long> rates = reviewRepo.findByGoodsNo(dto.getNo()).stream().map(e -> e.getRate()).collect(Collectors.toList());
            dto.setReCount(rates.size());
            long sum = 0;
            for(long num : rates){
                sum +=num;
            }
            double average = (double) sum / rates.size();
            dto.SetAvg(Math.round(average*100)/100.0);
        }
        model.addAttribute("list", list);
        model.addAttribute("cateType", "all");
    }

    @Override
    public void findCateGoods(long cno, Model model) {
        //List<GoodsListDTO> list = goodsRepo.findAll().stream().map(GoodsListDTO::new).collect(Collectors.toList());
        List<CategoryGoodsEntity> result= cateGoodsRepo.findByCategoryNo(cno);

        List<GoodsListDTO> list = result.stream().map(GoodsListDTO::new).collect(Collectors.toList());
        for(GoodsListDTO dto: list){
            List<Long> rates = reviewRepo.findByGoodsNo(dto.getNo()).stream().map(e -> e.getRate()).collect(Collectors.toList());
            dto.setReCount(rates.size());
            long sum = 0;
            for(long num : rates){
                sum +=num;
            }
            double average = (double) sum / rates.size();
            dto.SetAvg(Math.round(average*100)/100.0);
        }
        model.addAttribute("list", list);
        model.addAttribute("cateType", cno);
    }

}
