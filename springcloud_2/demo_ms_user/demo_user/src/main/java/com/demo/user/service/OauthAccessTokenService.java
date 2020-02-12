package com.demo.user.service;
import com.demo.user.pojo.OauthAccessToken;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:shenkunlin
 * @Description:OauthAccessToken业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface OauthAccessTokenService {

    /***
     * OauthAccessToken多条件分页查询
     * @param oauthAccessToken
     * @param page
     * @param size
     * @return
     */
    PageInfo<OauthAccessToken> findPage(OauthAccessToken oauthAccessToken, int page, int size);

    /***
     * OauthAccessToken分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<OauthAccessToken> findPage(int page, int size);

    /***
     * OauthAccessToken多条件搜索方法
     * @param oauthAccessToken
     * @return
     */
    List<OauthAccessToken> findList(OauthAccessToken oauthAccessToken);

    /***
     * 删除OauthAccessToken
     * @param id
     */
    void delete(String id);

    /***
     * 修改OauthAccessToken数据
     * @param oauthAccessToken
     */
    void update(OauthAccessToken oauthAccessToken);

    /***
     * 新增OauthAccessToken
     * @param oauthAccessToken
     */
    void add(OauthAccessToken oauthAccessToken);

    /**
     * 根据ID查询OauthAccessToken
     * @param id
     * @return
     */
     OauthAccessToken findById(String id);

    /***
     * 查询所有OauthAccessToken
     * @return
     */
    List<OauthAccessToken> findAll();
}
