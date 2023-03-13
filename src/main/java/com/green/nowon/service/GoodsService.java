package com.green.nowon.service;

import com.green.nowon.domain.dto.goods.GoodsInsertDTO;
import com.green.nowon.domain.dto.goods.GoodsUpdateDTO;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface GoodsService {

	void save(GoodsInsertDTO dto);

    Map<String, String> fileTempUpload(MultipartFile gimg);

    void goodsOfCategory(long cateNo, Model model);

    void getList(Model model);

    void adminDetail(long gno, Model model);

    void update(GoodsUpdateDTO dto);

    void removeGoods(long no);

    void findAllGoods(Model model);

    void findCateGoods(long cno, Model model);

}
