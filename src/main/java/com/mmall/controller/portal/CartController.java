package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse list(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);
        return iCartService.list(currentUser.getId());
    }

    @RequestMapping(value = "add.do")
    @ResponseBody
    public ServerResponse add(HttpServletRequest httpServletRequest, Integer count,Integer productId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);
        return iCartService.add(currentUser.getId(),productId,count);
    }

    @RequestMapping(value = "update.do")
    @ResponseBody
    public ServerResponse update(HttpServletRequest httpServletRequest, Integer count,Integer productId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);

        return iCartService.update(currentUser.getId(),productId,count);
    }

    @RequestMapping(value = "delete.do")
    @ResponseBody
    public ServerResponse delete(HttpServletRequest httpServletRequest,String productIds){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);

        return iCartService.delete(currentUser.getId(),productIds);
    }

    @RequestMapping(value = "select_all.do")
    @ResponseBody
    public ServerResponse selectAll(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);
        return iCartService.selectOrUnSelect(currentUser.getId(),Const.Cart.CHECKED,null);
    }

    @RequestMapping(value = "un_select_all.do")
    @ResponseBody
    public ServerResponse unSelectAll(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);
        return iCartService.selectOrUnSelect(currentUser.getId(),Const.Cart.UN_CHECKED,null);
    }

    @RequestMapping(value = "select.do")
    @ResponseBody
    public ServerResponse select(HttpServletRequest httpServletRequest,Integer productId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);
        return iCartService.selectOrUnSelect(currentUser.getId(),Const.Cart.CHECKED,productId);
    }

    @RequestMapping(value = "un_select.do")
    @ResponseBody
    public ServerResponse unSelect(HttpServletRequest httpServletRequest,Integer productId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);
        return iCartService.selectOrUnSelect(currentUser.getId(),Const.Cart.UN_CHECKED,productId);
    }

    @RequestMapping(value = "get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createBySuccess(0);
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);
        return iCartService.getCartProductCount(currentUser.getId());
    }
}
