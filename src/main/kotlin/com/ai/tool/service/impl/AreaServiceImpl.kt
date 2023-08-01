package com.ai.tool.service.impl

import com.ai.tool.config.RedisUtil
import com.ai.tool.entity.Area
import com.ai.tool.entity.Card
import com.ai.tool.mapper.AreaMapper
import com.ai.tool.service.AreaService
import com.ai.tool.util.GenerateRandomCode
import com.ai.tool.util.card.IDCardUtils
import com.ai.tool.util.ip.IPUtils
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit


@Service
class AreaServiceImpl : ServiceImpl<AreaMapper, Area>(), AreaService {

    @Autowired
    lateinit var areaMapper: AreaMapper

    @Autowired
    lateinit var redisUtil: RedisUtil
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

    override fun generateCode(areaCode: String, date: String, sex: String, numbers: String): List<Card> {
        val ip = IPUtils.getLocalIPAddress() + IPUtils.getHostName()
        val formattedDate = date.replace("-", "")
        val cards = ArrayList<Card>()
        val currentCount = ip.let { redisUtil.incrementAndGetCurrentCount(it, TimeUnit.MINUTES.toMillis(30)) }
        if (currentCount != null) {
            if (currentCount > 5) {
                throw RuntimeException("您的IP已经被限制生成，请过半小时再来吧！")
            } else {
                repeat(numbers.toInt()) {
                    val s = areaCode + formattedDate + GenerateRandomCode.generateRandomCode(sex)
                    val card = Card()
                    val result = GenerateRandomCode.generateIdCard(s)
                    card.cardNo = result
                    cards.add(card)
                }
            }
        }
        return cards
    }

    override fun checkCard(cardNo: String): Card {
        val card = Card()
        card.cardNo = cardNo
        val isIdCard = IDCardUtils.isValidIDCard(cardNo)
        card.isValidIDCard = isIdCard
        card.gender = IDCardUtils.parseIDCard(cardNo)
        card.age = IDCardUtils.calculateAgeFromIdCard(cardNo)
        val provinceCode = cardNo.substring(0, 2)
        val cityCode = cardNo.substring(0, 4)
        val areaCode = cardNo.substring(0, 6)

        val areaKtQueryChainWrapper = ktQuery().wrapper.eq(Area::provinceCode, provinceCode).eq(Area::pid, 0)
        val provinceName = areaMapper.selectOne(areaKtQueryChainWrapper).name
        val cityName = areaMapper.selectOne(ktQuery().wrapper.eq(Area::cityCode, cityCode).eq(Area::level, 2)).name
        val areaName = areaMapper.selectOne(ktQuery().wrapper.eq(Area::areaCode, areaCode).eq(Area::level, 3)).name
        card.region = "$provinceName$cityName$areaName"
        return card
    }

    override fun queryText(queryText: String): List<Area> {
        val ktQueryWrapper = ktQuery().wrapper.like(Area::name, queryText).eq(Area::level, 1)
        return areaMapper.selectList(ktQueryWrapper)
    }
}
