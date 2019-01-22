package com.onion.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onion.common.ServerResponse;
import com.onion.dao.ProductMapper;
import com.onion.pojo.Product;
import com.onion.service.IProductService;
import com.onion.vo.ProductListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(value = "iProductService")
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    /**
     * 新增或更新都可以使用这一个service方法
     * @param product
     * @return
     */
    public ServerResponse<String> saveOrUpdate(Product product){
        if(product != null){
            int count = 0;
            // id不为空时代表更新
            if(product.getId() != null){
                count = productMapper.updateByPrimaryKey(product);
            }else{
                count = productMapper.insert(product);
            }
            if(count > 0){
                return ServerResponse.createBySuccessMessage("成功！");
            }else{
                return ServerResponse.createBySuccessMessage("失败！");
            }
        }
        return ServerResponse.createByErrorMessage("传入参数错误！");
    }

    /**
     * 用户列表：通这mybatis实现动态分页功能
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        // 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        // 定义存放vo的list
        List<ProductListVo> productListVoList = new ArrayList<>();

        // 从数据库中查出productList
        List<Product> productList = productMapper.getProductList();

        // pojo转为vo
        for(Product product : productList){
            productListVoList.add(getProductListVo(product));
        }

        // 使用productList数据进行分页，但是页面展示数据为productListVoList
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 在Service层中定义私有化的pojo转vo的方法
     * @param product
     * @return
     */
    private ProductListVo getProductListVo(Product product){
        ProductListVo vo = new ProductListVo();

        vo.setId(product.getId());
        vo.setCategoryId(product.getCategoryId());
        vo.setMainImage(product.getMainImage());
        vo.setName(product.getName());
        vo.setPrice(product.getPrice());
        vo.setStatus(product.getStatus());
        vo.setStock(product.getStock());

        return vo;
    }
}
