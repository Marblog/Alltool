package com.example.card.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.example.card.entity.Area
import com.example.card.mapper.AreaMapper
import com.example.card.service.AreaService
import com.example.card.util.GenerateRandomCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class AreaServiceImpl : ServiceImpl<AreaMapper, Area>(), AreaService {

    @Autowired
    lateinit var areaMapper: AreaMapper

    override fun getProvince(): List<Area> {
        // 查询省份数据，pid为0表示省份
        val wrapper = ktQuery().wrapper.eq(Area::pid, 0)
        return areaMapper.selectList(wrapper)
    }

    override fun getCity(provinceCode: String): List<Area> {
        // 查询城市数据，level为2表示城市，根据provinceCode过滤
        val wrapper = ktQuery().wrapper.eq(Area::provinceCode, provinceCode).eq(Area::level, 2)
        return areaMapper.selectList(wrapper)
    }

    override fun getCounty(provinceCode: String, cityCode: String): List<Area> {
        // 查询县区数据，level为3表示县区，根据provinceCode和cityCode过滤，并去除名称为"市辖区"的数据
        val wrapper = ktQuery().wrapper.eq(Area::provinceCode, provinceCode)
            .eq(Area::level, 3)
            .eq(Area::cityCode, cityCode)
            .ne(Area::name, "市辖区")
        return areaMapper.selectList(wrapper)
    }

    override fun getTown(provinceCode: String, cityCode: String, areaCode: String): List<Area> {
        // 查询乡镇数据，level为3表示乡镇，根据provinceCode、cityCode和areaCode过滤
        val wrapper = ktQuery().wrapper.eq(Area::provinceCode, provinceCode)
            .eq(Area::level, 4)
            .eq(Area::cityCode, cityCode)
            .eq(Area::areaCode, areaCode)
        return areaMapper.selectList(wrapper)
    }

    override fun getVillage(provinceCode: String, cityCode: String, areaCode: String, streetCode: String): List<Area> {
        val wrapper = ktQuery().wrapper.eq(Area::provinceCode, provinceCode)
            .eq(Area::level, 5)
            .eq(Area::cityCode, cityCode)
            .eq(Area::areaCode, areaCode)
            .eq(Area::streetCode, streetCode)
        return areaMapper.selectList(wrapper)
    }

    override fun generateCode(areaCode: String, date: String): String {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateObject = LocalDateTime.parse(date, dateFormat)
        val formattedDate = dateObject.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        return areaCode + formattedDate + GenerateRandomCode.generateRandomCode()
    }
}
