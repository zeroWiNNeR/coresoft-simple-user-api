package ru.vekovshinin.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.FlushMode;
import org.hibernate.cfg.BatchSettings;
import org.hibernate.cfg.FetchSettings;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.cfg.QuerySettings;
import org.hibernate.jpa.HibernateHints;
import org.hibernate.query.sqm.mutation.internal.inline.InlineMutationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.vekovshinin.configuration.properties.AppProperties;

@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(basePackages = {PersistenceConfig.REPO_PATH})
@EnableTransactionManagement
@EnableJpaAuditing
public class PersistenceConfig {

  public static final String REPO_PATH = "ru.vekovshinin.repository";
  public static final String MODEL_PATH = "ru.vekovshinin.model.domain";

  private final AppProperties appProperties;

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("%s://%s:%s/%s?%s".formatted(
        appProperties.getDbDriver(), appProperties.getDbHost(), appProperties.getDbPort(),
        appProperties.getDbName(), appProperties.getDbParam()
    ));
    config.setSchema(appProperties.getDbSchema());
    config.setUsername(appProperties.getDbUsername());
    config.setPassword(appProperties.getDbPassword());
    config.addDataSourceProperty("ApplicationName", appProperties.getAppName());
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    return new HikariDataSource(config);
  }

  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(adapter);
    em.setPackagesToScan(MODEL_PATH);

    Map<String, Object> props = new HashMap<>();
    props.put(JdbcSettings.POOL_SIZE, 10);
    props.put(JdbcSettings.AUTOCOMMIT, false);
    props.put(JdbcSettings.USE_SCROLLABLE_RESULTSET, true);
    props.put(BatchSettings.STATEMENT_BATCH_SIZE, 1000);
    props.put(BatchSettings.ORDER_UPDATES, true);
    props.put(QuerySettings.QUERY_MULTI_TABLE_MUTATION_STRATEGY,
        InlineMutationStrategy.class.getName());
    props.put(QuerySettings.FAIL_ON_PAGINATION_OVER_COLLECTION_FETCH, true);
    props.put(HibernateHints.HINT_FLUSH_MODE, FlushMode.COMMIT);
    props.put(FetchSettings.DEFAULT_BATCH_FETCH_SIZE, 20);
    em.setJpaPropertyMap(props);
    return em;
  }

}