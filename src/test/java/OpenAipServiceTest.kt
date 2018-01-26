import com.basicfu.common.util.RedisUtil
import com.basicfu.system.Application
import com.basicfu.system.model.po.Menu
import com.basicfu.system.model.po.User
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

import java.util.ArrayList

@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@SpringBootTest(classes = arrayOf(Application::class))
class OpenAipServiceTest {
    @Autowired lateinit var redisTemplate:RedisTemplate<Any,Any>
    @Test
    @Throws(Exception::class)
    fun insertDsfUser() {
        RedisUtil.init(redisTemplate)
        val list = arrayListOf<User>()
        val u=User()
        u.id=1
        u.nickname="hii"
        list.add(u)
        val u2=User()
        u2.id=2
        u2.nickname="hii222"
        list.add(u2)
        val map = list.associateBy({ it.id.toString() }, { it })
        RedisUtil.hMSet("test",map)
        val hGetAll = RedisUtil.hGetAll("test", User::class.java)

        val list1 = arrayListOf<Menu>()
        val u1=Menu()
        u1.id=1
        u1.name="hii"
        list1.add(u1)
        val u4=Menu()
        u4.id=2
        u4.name="hii222"
        list1.add(u4)
        val map1 = list1.associateBy({ it.id.toString() }, { it })
        RedisUtil.hMSet("test1",map1)
        val hGetAll1 = RedisUtil.hGetAll("test1", Menu::class.java)

        val hmget=RedisUtil.hGet("test",1)
        println("success")
    }

}