package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(currentUser.getId());
    }

    @RequestMapping(value = "add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, Integer count,Integer productId){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(currentUser.getId(),productId,count);
    }

    @RequestMapping(value = "update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, Integer count,Integer productId){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(currentUser.getId(),productId,count);
    }

    @RequestMapping(value = "delete.do")
    @ResponseBody
    public ServerResponse delete(HttpSession session,String productIds){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.delete(currentUser.getId(),productIds);
    }

    @RequestMapping(value = "select_all.do")
    @ResponseBody
    public ServerResponse selectAll(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(currentUser.getId(),Const.Cart.CHECKED,null);
    }

    @RequestMapping(value = "un_select_all.do")
    @ResponseBody
    public ServerResponse unSelectAll(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(currentUser.getId(),Const.Cart.UN_CHECKED,null);
    }

    @RequestMapping(value = "select.do")
    @ResponseBody
    public ServerResponse select(HttpSession session,Integer productId){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(currentUser.getId(),Const.Cart.CHECKED,productId);
    }

    @RequestMapping(value = "un_select.do")
    @ResponseBody
    public ServerResponse unSelect(HttpSession session,Integer productId){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(currentUser.getId(),Const.Cart.UN_CHECKED,productId);
    }

    @RequestMapping(value = "get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null){
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(currentUser.getId());
    }
}
