package com.cloud.mall.controller;


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
 * @author snow
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

}
