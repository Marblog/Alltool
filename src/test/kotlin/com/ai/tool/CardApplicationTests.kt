package com.example.card


import com.example.card.util.ip.IPUtils
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CardApplicationTests {

    @Test
    fun contextLoads() {
        println(IPUtils.getPublicIPAddress())
    }

}
