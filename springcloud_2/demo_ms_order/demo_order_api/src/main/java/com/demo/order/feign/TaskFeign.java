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
import com.demo.order.pojo.Task;
import com.github.pagehelper.PageInfo;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="order")
@RequestMapping("/task")
public interface TaskFeign {

    /***
     * Task分页条件搜索实现
     * @param task
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) Task task, @PathVariable  int page, @PathVariable  int size);

    /***
     * Task分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param task
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Task>> findList(@RequestBody(required = false) Task task);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改Task数据
     * @param task
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Task task,@PathVariable Long id);

    /***
     * 新增Task数据
     * @param task
     * @return
     */
    @PostMapping
    Result add(@RequestBody Task task);

    /***
     * 根据ID查询Task数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Task> findById(@PathVariable Long id);

    /***
     * 查询Task全部数据
     * @return
     */
    @GetMapping
    Result<List<Task>> findAll();
}