package com.onion.service;

import com.github.pagehelper.PageInfo;
import com.onion.common.ServerResponse;
import com.onion.pojo.Product;

public interface IProductService {

    ServerResponse<String> saveOrUpdate(Product product);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);
}
