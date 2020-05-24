package com.maxiaoda.xwshop.controller;

import com.maxiaoda.xwshop.entity.Response;
import com.maxiaoda.xwshop.exception.NotAuthorizedForShopException;
import com.maxiaoda.xwshop.exception.ResourceNotFoundException;
import com.maxiaoda.xwshop.generate.Goods;
import com.maxiaoda.xwshop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class GoodsController {
    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping("/goods")
    public Response<Goods> createdGoods(@RequestBody Goods goods,
                                        HttpServletResponse response) {
        clean(goods);
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            return Response.of(goodsService.createGoods(goods));
        } catch (NotAuthorizedForShopException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            return Response.of(e.getMessage(), null);
        }
    }

    private void clean(Goods goods) {
        goods.setId(null);
        goods.setCreatedAt(new Date());
        goods.setUpdatedAt(new Date());
    }

    @DeleteMapping("/goods/{id}")
    public Response<Goods> deleteGoods(@PathVariable("id") Long goodsId,
                                       HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            Goods goods = goodsService.deleteGoodsById(goodsId);
            return Response.of(null,goods);
        } catch (NotAuthorizedForShopException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return Response.of(e.getMessage(), null);
        }catch (ResourceNotFoundException e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Response.of(e.getMessage(), null);
        }
    }
}
