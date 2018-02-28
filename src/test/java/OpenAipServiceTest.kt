import com.basicfu.common.util.RedisUtil
import com.basicfu.system.Application
import com.basicfu.system.model.vo.DictVo
import com.basicfu.system.service.DictService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@SpringBootTest(classes = arrayOf(Application::class))
class OpenAipServiceTest {
    @Autowired lateinit var redisTemplate:RedisTemplate<Any,Any>
    @Autowired lateinit var dictService: DictService
    @Test
    @Throws(Exception::class)
    fun insertDsfUser() {
        RedisUtil.init(redisTemplate)
        val vo=DictVo()
        vo.id=1
        vo.name="性别"
        dictService.insert(vo)
    }

}