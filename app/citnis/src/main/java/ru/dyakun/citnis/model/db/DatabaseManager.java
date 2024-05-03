package ru.dyakun.citnis.model.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.model.query.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class DatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final DatabaseManager instance = new DatabaseManager();

    private final String url;
    private final String user;
    private final String passwd;

    private DatabaseManager() {
        Properties props = new Properties();
        String propsPath = Objects.requireNonNull(getClass().getResource("/database.properties")).getPath();
        try {
            BufferedReader bf = Files.newBufferedReader(Paths.get(propsPath), StandardCharsets.UTF_8);
            props.load(bf);
        } catch (IOException e) {
            logger.error("Database props load failed", e);
        }
        url = props.getProperty("db.url");
        user = props.getProperty("db.user");
        passwd = props.getProperty("db.passwd");
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    public <T> List<T> executeQuery(String query, Mapper<T> mapper) {
        logger.info("Executing query: \n{}", query);
        List<T> result = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, passwd);
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                result.add(mapper.map(rs));
            }
        } catch (SQLException e) {
            logger.error("Database query execute failed", e);
        }
        return result;
    }

    public Map<Mapper<?>, List<?>> executeMultipleQueries(String query, List<Mapper<?>> mappers) {
        logger.info("Executing multiple queries: \n{}", query);
        Map<Mapper<?>, List<?>> res = new HashMap<>();
        try (Connection con = DriverManager.getConnection(url, user, passwd);
             PreparedStatement pst = con.prepareStatement(query)) {
            int i = 0;
            boolean isResult;
            do {
                try (ResultSet rs = pst.getResultSet()) {
                    var list = readResultSet(rs, mappers.get(i));
                    res.put(mappers.get(i), list);
                    isResult = pst.getMoreResults();
                    i++;
                }
            } while (isResult && i < mappers.size());
        } catch (SQLException e) {
            logger.error("Database multiple queries execute failed", e);
        }
        return res;
    }

    private <T> List<T> readResultSet(ResultSet rs, Mapper<T> mapper) throws SQLException {
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            result.add(mapper.map(rs));
        }
        return result;
    }

}
