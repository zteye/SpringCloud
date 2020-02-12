package com.demo.order.feign;
import entity.Result;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.demo.order.pojo.OrderConfig;
import com.github.pagehelper.PageInfo;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="order")
@RequestMapping("/orderConfig")
public interface OrderConfigFeign {

    /***
     * OrderConfig分页条件搜索实现
     * @param orderConfig
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) OrderConfig orderConfig, @PathVariable  int page, @PathVariable  int size);

    /***
     * OrderConfig分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param orderConfig
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<OrderConfig>> findList(@RequestBody(required = false) OrderConfig orderConfig);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Integer id);

    /***
     * 修改OrderConfig数据
     * @param orderConfig
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody OrderConfig orderConfig,@PathVariable Integer id);

    /***
     * 新增OrderConfig数据
     * @param orderConfig
     * @return
     */
    @PostMapping
    Result add(@RequestBody OrderConfig orderConfig);

    /***
     * 根据ID查询OrderConfig数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<OrderConfig> findById(@PathVariable Integer id);

    /***
     * 查询OrderConfig全部数据
     * @return
     */
    @GetMapping
    Result<List<OrderConfig>> findAll();
}