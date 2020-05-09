package com.maxiaoda.xwshop.dao;

import com.maxiaoda.xwshop.exception.ResourceNotFoundException;
import com.maxiaoda.xwshop.generate.Goods;
import com.maxiaoda.xwshop.generate.GoodsMapper;
import com.maxiaoda.xwshop.generate.Shop;
import com.maxiaoda.xwshop.generate.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopDao {
    private final ShopMapper shopMapper;
    private final GoodsMapper goodsMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public ShopDao(ShopMapper shopMapper, GoodsMapper goodsMapper) {
        this.shopMapper = shopMapper;
        this.goodsMapper = goodsMapper;
    }

    public Shop findShopIdByGoods(Long goodsId) {
        try {
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            return shopMapper.selectByPrimaryKey(goods.getShopId());
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("商品未找到！");
        }
    }
}
