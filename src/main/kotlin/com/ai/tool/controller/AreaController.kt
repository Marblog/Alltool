package com.ai.tool.controller

import com.ai.tool.entity.Area
import com.ai.tool.entity.Card
import com.ai.tool.service.AreaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AreaController {

    @Autowired
    lateinit var areaService: AreaService

    @GetMapping("/province")
    fun getProvince(): List<Area> {
        return areaService.getProvince()
    }

    @GetMapping("/city/{provinceCode}")
    fun getCity(@PathVariable provinceCode: String): List<Area> {
        return areaService.getCity(provinceCode)
    }

    @GetMapping("/county/")
    fun getCounty(provinceCode: String, cityCode: String): List<Area> {
        return areaService.getCounty(provinceCode, cityCode)
    }

    @GetMapping("/town/")
    fun getTown(provinceCode: String, cityCode: String, areaCode: String): List<Area> {
        return areaService.getTown(provinceCode, cityCode, areaCode)
    }

    @GetMapping("/village/")
    fun getVillage(provinceCode: String, cityCode: String, areaCode: String, streetCode: String): List<Area> {
        return areaService.getVillage(provinceCode, cityCode, areaCode, streetCode)
    }

    @GetMapping("/generateCode/")
    fun generateCode(areaCode: String, date: String, sex: String, numbers: String): List<Card> {
        return areaService.generateCode(areaCode, date, sex, numbers)
    }


    @GetMapping("/checkCard/")
    fun checkCard(cardNo: String): Card {
        return areaService.checkCard(cardNo)
    }

    @GetMapping("/query/")
    fun queryText(queryText: String): List<Area> {
        return areaService.queryText(queryText)
    }

}