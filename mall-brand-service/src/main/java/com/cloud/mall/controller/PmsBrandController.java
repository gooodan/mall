package com.cloud.mall.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.mall.common.api.CommonResult;
import com.cloud.mall.mbg.model.PmsBrand;
import com.cloud.mall.service.PmsBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author sg
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    @Resource
    private PmsBrandService pmsBrandService;

    @GetMapping(value = "listAll")
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(pmsBrandService.list());
    }

    @PostMapping(value = "/create")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        boolean flag = pmsBrandService.save(pmsBrand);
        if (flag) {
            LOGGER.debug("createBrand success:{}", pmsBrand);
            return CommonResult.success(pmsBrand);
        } else {
            LOGGER.debug("createBrand failed:{}", pmsBrand);
            return CommonResult.failed("创建失败");
        }
    }

    @PostMapping(value = "/update/{id}")
    public CommonResult updateBrand(@PathVariable("id") Long id,
                                    @RequestBody PmsBrand pmsBrandDto,
                                    BindingResult result) {
        boolean flag = pmsBrandService.updateById(pmsBrandDto);
        if (flag) {
            LOGGER.debug("updateBrand success:{}", pmsBrandDto);
            return CommonResult.success(pmsBrandDto);
        } else {
            LOGGER.debug("updateBrand failed:{}", pmsBrandDto);
            return CommonResult.failed("更新失败");
        }
    }

    @PostMapping(value = "/delete/{id}")
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        boolean flag = pmsBrandService.removeById(id);
        if (flag) {
            LOGGER.debug("delete success:id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed :id={}", id);
            return CommonResult.failed("删除失败");
        }
    }

    @GetMapping(value = "/list")
    public CommonResult<IPage<PmsBrand>> listBrand(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<PmsBrand> page = new Page<PmsBrand>(current, size);
        IPage<PmsBrand> iPage = pmsBrandService.page(page);

        return CommonResult.success(iPage);
    }

    @GetMapping(value = "/{id}")
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(pmsBrandService.getById(id));
    }
}
