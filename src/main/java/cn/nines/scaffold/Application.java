package cn.nines.scaffold;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: Application
 * @Description: 项目启动类
 * @author: Nines
 * @date: 2019年11月25日 14:23
 */

@SpringBootApplication
@MapperScan("cn.nines.scaffold.sys.mapper")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

}
