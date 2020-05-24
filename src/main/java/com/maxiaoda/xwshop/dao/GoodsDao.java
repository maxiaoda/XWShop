package com.maxiaoda.xwshop.dao;

import com.maxiaoda.xwshop.entity.DataStatus;
import com.maxiaoda.xwshop.exception.ResourceNotFoundException;
import com.maxiaoda.xwshop.generate.Goods;
import com.maxiaoda.xwshop.generate.GoodsMapper;
import com.maxiaoda.xwshop.generate.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsDao {
    private final GoodsMapper goodsMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public GoodsDao(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    public Goods insertGoods(Goods goods) {
        long insertGoodsById = goodsMapper.insert(goods);
        goods.setId(insertGoodsById);
        return goods;
    }

    public Goods deleteGoods(Long goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods == null) {
            throw new ResourceNotFoundException("商品未找到！");
        }
        goods.setStatus(DataStatus.DELETE_STATUS);
        goodsMapper.updateByPrimaryKey(goods);
        return goods;
    }

}
