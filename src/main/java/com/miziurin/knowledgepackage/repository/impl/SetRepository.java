package com.miziurin.knowledgepackage.repository.impl;

import com.miziurin.knowledgepackage.model.Kpac;
import com.miziurin.knowledgepackage.model.KpacSet;
import com.miziurin.knowledgepackage.repository.ISetRepository;
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
public class SetRepository implements ISetRepository {

    private static final String INSERT_SET = "INSERT INTO sets VALUES(DEFAULT, :title)";
    private static final String INSERT_KPAC_INTO_SET = "INSERT INTO kpacs_sets VALUES(:setId, :kpacId)";
    private static final String SELECT_SET_BY_ID = "SELECT * FROM sets WHERE id = :id";
    private static final String SELECT_KPAKS_OF_SET = "SELECT kp.id, kp.title, kp.description, kp.creation_date " +
            "FROM kpacs_sets AS ks " +
            "INNER JOIN kpacs AS kp ON ks.kpac_id = kp.id " +
            "INNER JOIN sets AS s ON ks.set_id = s.id WHERE s.id=:id";
    private static final String DELETE_SET_BY_ID = "DELETE FROM sets WHERE id = :id";
    private static final String SELECT_ALL_SETS = "SELECT * FROM sets ORDER BY id";
    private static final String DELETE_PKAP_FROM_SET = "DELETE FROM kpacs_sets WHERE set_id=:setId AND kpac_id=:kpacId";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Inserts set entities into db. First, the {@link KpacSet} entity is adds,
     * and then inserts all the {@link Kpac} entities that it contains.
     *
     * @param set Knowledge package entity to insert
     */
    @Override
    public void save(KpacSet set) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue(TITLE_PARAM, set.getTitle());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SET, params, keyHolder);

        List<Kpac> kpacs = set.getKpacs();
        if (Objects.nonNull(kpacs) && !kpacs.isEmpty()) {
            long newSetId = keyHolder.getKey().longValue();
            List<Map<String, Object>> batchValues = new ArrayList<>(kpacs.size());

            for (Kpac kpac : kpacs) {
                batchValues.add(new MapSqlParameterSource()
                        .addValue(SET_ID_PARAM, newSetId)
                        .addValue(KPAC_ID_PARAM, kpac.getId()).getValues());
            }
            jdbcTemplate.batchUpdate(INSERT_KPAC_INTO_SET, batchValues.toArray(new Map[kpacs.size()]));
        }
    }

    /**
     * Retrieves a set from a db. Extracts the {@link KpacSet}
     * and all related {@link Kpac} entities, if any exists.
     *
     * @param id ID of the set to extract
     */
    @Override
    public KpacSet find(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue(ID_PARAM, id);
        KpacSet set = jdbcTemplate.queryForObject(SELECT_SET_BY_ID, params, new BeanPropertyRowMapper<>(KpacSet.class));
        List<Kpac> kpacs = jdbcTemplate.query(SELECT_KPAKS_OF_SET, params, new BeanPropertyRowMapper<>(Kpac.class));
        set.setKpacs(kpacs);
        return set;
    }

    @Override
    public void delete(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue(ID_PARAM, id);
        jdbcTemplate.update(DELETE_SET_BY_ID, params);
    }

    @Override
    public List<KpacSet> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SETS, new BeanPropertyRowMapper<>(KpacSet.class));
    }

    @Override
    public void deleteKpacFromSet(long setId, long kpacId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(SET_ID_PARAM, setId)
                .addValue(KPAC_ID_PARAM, kpacId);
        jdbcTemplate.update(DELETE_PKAP_FROM_SET, params);
    }
}
