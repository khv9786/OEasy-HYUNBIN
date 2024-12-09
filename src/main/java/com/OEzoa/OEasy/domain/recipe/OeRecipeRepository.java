package com.OEzoa.OEasy.domain.recipe;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OeRecipeRepository extends JpaRepository<OeRecipe, Long> {
    @Query(value = "SELECT recipe_img FROM oe_recipe ORDER BY RAND() limit :limit", nativeQuery = true)
    List<String> getRandomImg(@Param("limit") int limit);

    long count();

    Integer deleteByImgLike(String img);

    @Query(value = "SELECT * " +
            "FROM oe_recipe " +
            "WHERE recipe_pk < :pk " +
            "order by recipe_pk DESC limit :limit", nativeQuery = true)
    List<OeRecipe> findByRecipePkLessThanOrderByDescTopN(@Param("pk") long pk, @Param("limit") int limit);

    List<OeRecipe> findAllByOrderByRecipePkDesc(Pageable pageable);

    @Query(value = "select recipe_pk from oe_recipe order by recipe_pk desc limit 1", nativeQuery = true)
    long findTopId();

    @Query(value = "SELECT * FROM oe_recipe order by RAND() limit 1", nativeQuery = true)
    OeRecipe findRandomRecipe();

    @Query(value = "SELECT recipe_pk from oe_recipe ORDER BY RAND() limit 1", nativeQuery = true)
    long findRandomPk();
}
