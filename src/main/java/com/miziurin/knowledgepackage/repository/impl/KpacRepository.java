package com.miziurin.knowledgepackage.repository.impl;

import com.miziurin.knowledgepackage.model.Kpac;
import com.miziurin.knowledgepackage.model.KpacSet;
import com.miziurin.knowledgepackage.repository.IKpacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.miziurin.knowledgepackage.constants.Constants.DBConstants.ID_PARAM;
import static com.miziurin.knowledgepackage.constants.Constants.DBConstants.KPAC_ID_PARAM;
import static com.miziurin.knowledgepackage.constants.Constants.DBConstants.SET_ID_PARAM;
import static com.miziurin.knowledgepackage.constants.Constants.DBConstants.TITLE_PARAM;

@Repository
public class KpacRepository implements IKpacRepository {

    private static final String INSERT_KPAC = "INSERT INTO kpacs VALUES(DEFAULT, :title, :description, DEFAULT)";
    private static final String INSERT_SET_INTO_KPAC = "INSERT INTO kpacs_sets VALUES(:setId, :kpacId)";
    private static final String SELECT_KPACS_BY_ID = "Select * FROM kpacs WHERE id = :id";
    private static final String DELETE_KPAC_BY_ID = "DELETE FROM kpacs WHERE id = :id";
    private static final String SELECT_ALL_KPACS = "SELECT * FROM kpacs ORDER BY id";
    private static final String DESCRIPTION_PARAM = "description";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Inserts {@link Kpac} entities into db. First, the kpac entity is adds,
     * and then inserts all the {@link KpacSet} entities that it contains
     *
     * @param kpac Knowledge package entity to insert
     */
    @Override
    public void save(Kpac kpac) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(TITLE_PARAM, kpac.getTitle())
                .addValue(DESCRIPTION_PARAM, kpac.getDescription());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_KPAC, params, keyHolder);

        List<KpacSet> sets = kpac.getSets();
        if (Objects.nonNull(sets) && !sets.isEmpty()) {
            long newKpacId = keyHolder.getKey().longValue();
            List<Map<String, Object>> batchValues = new ArrayList<>(sets.size());

            for (KpacSet set : sets) {
                batchValues.add(new MapSqlParameterSource()
                        .addValue(SET_ID_PARAM, set.getId())
                        .addValue(KPAC_ID_PARAM, newKpacId).getValues());
            }
            jdbcTemplate.batchUpdate(INSERT_SET_INTO_KPAC, batchValues.toArray(new Map[sets.size()]));
        }
    }

    @Override
    public Kpac find(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue(ID_PARAM, id);
        return jdbcTemplate.queryForObject(SELECT_KPACS_BY_ID, params, new BeanPropertyRowMapper<>(Kpac.class));
    }

    @Override
    public void delete(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue(ID_PARAM, id);
        jdbcTemplate.update(DELETE_KPAC_BY_ID, params);
    }

    @Override
    public List<Kpac> findAll() {
        return jdbcTemplate.getJdbcTemplate().query(SELECT_ALL_KPACS, new BeanPropertyRowMapper<>(Kpac.class));
    }
}
