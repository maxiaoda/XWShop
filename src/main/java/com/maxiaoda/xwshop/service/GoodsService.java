package com.maxiaoda.xwshop.service;

import com.maxiaoda.xwshop.context.UserContext;
import com.maxiaoda.xwshop.dao.GoodsDao;
import com.maxiaoda.xwshop.dao.ShopDao;
import com.maxiaoda.xwshop.exception.NotAuthorizedForShopException;
import com.maxiaoda.xwshop.exception.ResourceNotFoundException;
import com.maxiaoda.xwshop.generate.Goods;
import com.maxiaoda.xwshop.generate.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GoodsService {
    private GoodsDao goodsDao;
    private ShopDao shopDao;

    @Autowired
    public GoodsService(GoodsDao goodsDao, ShopDao shopDao) {
        this.goodsDao = goodsDao;
        this.shopDao = shopDao;
    }

    public Goods createGoods(Goods goods) {
        Shop shopById = shopDao.findShopIdByGoods(goods.getShopId());

        if (Objects.equals(shopById.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
            return goodsDao.insertGoods(goods);
        } else {
            throw new NotAuthorizedForShopException("无权访问！");
        }
    }

    public Goods deleteGoodsById(Long goodsId) {
        Shop shopById = shopDao.findShopIdByGoods(goodsId);

        if (Objects.equals(shopById.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
            return goodsDao.deleteGoods(goodsId);
        } else {
            throw new NotAuthorizedForShopException("无权访问！");
        }

    }
}
