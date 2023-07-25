package com.ai.tool.service

import com.baomidou.mybatisplus.extension.service.IService
import com.ai.tool.entity.Area
import com.ai.tool.entity.Card


interface AreaService :IService<Area>{

    fun getProvince():List<Area>

    fun getCity(provinceCode:String):List<Area>
    fun getCounty(provinceCode:String,cityCode:String):List<Area>
    fun getTown(provinceCode:String,cityCode:String,areaCode:String):List<Area>

    fun getVillage(provinceCode:String,cityCode:String,areaCode:String,streetCode:String):List<Area>

    fun generateCode(areaCode:String,date:String,sex:String): Card

    fun checkCard(cardNo:String):Card
}