package com.onion.controller.backend;

import com.github.pagehelper.PageInfo;
import com.onion.common.Const;
import com.onion.common.ResponseCode;
import com.onion.common.ServerResponse;
import com.onion.pojo.Product;
import com.onion.pojo.User;
import com.onion.service.IFileService;
import com.onion.service.IProductService;
import com.onion.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/product/")
public class ProductManagerController {

    @Resource
    private IProductService iProductService;

    @Resource
    private IFileService iFileService;

    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    public ServerResponse<String> productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        // 用户未登录时的逻辑，后期可以统一放置在拦截器中实现
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录！");
        }

        return iProductService.saveOrUpdate(product);
    }

    /**
     * 商品列表,pageNum与pageSize设置默认值
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> getProduceList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        return iProductService.getProductList(pageNum,pageSize);
    }


    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse<String> fileUpload(MultipartFile file) throws Exception {
        return iFileService.fileUpload(file);
    }
}
