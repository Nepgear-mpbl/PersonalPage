#sql("findById")
SELECT
 *
FROM p_article
WHERE `id` = #para(0)
#end

#sql("findByIdWithType")
SELECT
  p_article.*
  ,p_article_type.type_name
FROM p_article INNER JOIN p_article_type ON p_article.type=p_article_type.id
WHERE p_article.id = #para(0)
#end

#sql("getByType")
SELECT
 *
FROM p_article
WHERE `type` = #para(0)
ORDER BY  `article_time` DESC
#end

#sql("getAll")
SELECT
 *
FROM p_article
ORDER BY  `article_time` DESC
#end