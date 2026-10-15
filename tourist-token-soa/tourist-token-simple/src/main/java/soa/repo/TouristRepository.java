package soa.repo;

import soa.models.Tourist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TouristRepository {
    private final JdbcTemplate jdbc;

    public TouristRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Tourist save(Tourist tourist) {
        String sql = "INSERT INTO tourists(name, location, country) VALUES (?, ?, ?)";
        KeyHolder keys = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tourist.getName());
            ps.setString(2, tourist.getLocation());
            ps.setString(3, tourist.getCountry());
            return ps;
        }, keys);
        tourist.setId(keys.getKey().longValue());
        return tourist;
    }

    public List<Tourist> findAll() {
        return jdbc.query("SELECT id, name, location, country FROM tourists ORDER BY id",
                (rs, row) -> new Tourist(rs.getLong("id"), rs.getString("name"),
                        rs.getString("location"), rs.getString("country")));
    }

    public Tourist findById(long id) {
        List<Tourist> list = jdbc.query("SELECT id, name, location, country FROM tourists WHERE id = ?",
                (rs, row) -> new Tourist(rs.getLong("id"), rs.getString("name"),
                        rs.getString("location"), rs.getString("country")), id);
        return list.isEmpty() ? null : list.get(0);
    }

    public int update(long id, Tourist t) {
        return jdbc.update("UPDATE tourists SET name=?, location=?, country=? WHERE id=?",
                t.getName(), t.getLocation(), t.getCountry(), id);
    }

    public int delete(long id) {
        return jdbc.update("DELETE FROM tourists WHERE id=?", id);
    }
}
