package marchiori_eduardo.com.github.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {


    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // DriverManagerDataSource: Não é recomendado para produção.
    // Ele não possui um pool de conexões, criando uma nova conexão física
    // para cada requisição ao banco, o que gera um alto custo de performance.
    // @Bean
    public DataSource dataSourceDS() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(this.url);
        ds.setUsername(this.username);
        ds.setPassword(this.password);
        ds.setDriverClassName(this.driver);
        return ds;
    }

    // HikariCP: É o pool de conexões padrão do Spring Boot.
    // É extremamente leve, rápido e oferece alta performance para aplicações produtivas.
    @Bean
    public DataSource dataSource() {

        // acesso à informações dos acessos ao banco de dados
        HikariConfig config = new HikariConfig();
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setDriverClassName(this.driver);
        config.setJdbcUrl(this.url);

        // Configuração do pool de conexões
        config.setMaximumPoolSize(10);//maximo de conexoes liberadas
        config.setMinimumIdle(1);//tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000);// em ms (10 minutos)
        config.setConnectionTimeout(60000); // em ms (1 minuto)
        config.setConnectionTestQuery("SELECT 1"); //query de teste de conexao



        return new HikariDataSource(config);
    }
}
