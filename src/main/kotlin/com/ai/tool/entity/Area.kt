package com.ai.tool.entity

import com.baomidou.mybatisplus.annotation.TableId

data class Area(
    @TableId
    var id: Long?,
    var name: String?,
    var pid: Long?,
    var provinceCode: String?,
    var cityCode: String?,
    var areaCode: String?,
    var streetCode: String?,
    var committeeCode: String?,
    var committeeType: String?,
    var sort: Int?,
    var level: Int?
) {
    override fun toString(): String {
        return "Area(id=$id, name=$name, pid=$pid, provinceCode=$provinceCode, " +
                "cityCode=$cityCode, areaCode=$areaCode, streetCode=$streetCode, " +
                "committeeCode=$committeeCode, committeeType=$committeeType, sort=$sort, level=$level)"
    }
}

