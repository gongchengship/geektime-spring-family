package geektime.spring.data.datasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class DataSourceDemoApplication implements CommandLineRunner {

	private final static Logger log = LoggerFactory.getLogger(DataSourceDemoApplication.class);

	@Autowired //按类型装配,它是Spring 提供的
	@Qualifier("dataSource") //加了@Qualifier注解之后，就会按照名称装配
	private DataSource dataSource;

	@Resource //按照名称装配，如果未找到响应的名称，则尝试按类型装配
//	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DataSourceDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		showConnection();
		showData();
	}

	private void showConnection() throws SQLException {
		log.info(dataSource.toString());
		Connection conn = dataSource.getConnection();
		log.info(conn.toString());
		conn.close();
	}

	private void showData() {
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList("SELECT * FROM FOO");
		resultList.forEach(row -> log.info(row.toString()));
	}
}
