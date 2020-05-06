package com.maxiaoda.xwshop.generate;

import com.maxiaoda.xwshop.generate.ShoppingCart;
import com.maxiaoda.xwshop.generate.ShoppingCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShoppingCartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    long countByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    int deleteByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    int insert(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    int insertSelective(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    List<ShoppingCart> selectByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    ShoppingCart selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    int updateByExampleSelective(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    int updateByExample(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    int updateByPrimaryKeySelective(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Wed May 06 17:06:15 CST 2020
     */
    int updateByPrimaryKey(ShoppingCart record);
}