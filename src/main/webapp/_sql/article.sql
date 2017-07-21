#sql("findById")
SELECT
 *
FROM p_article
WHERE `id` = #para(0)
#end

#sql("getByType")
SELECT
 *
FROM p_article
WHERE `type` = #para(0)
#end
