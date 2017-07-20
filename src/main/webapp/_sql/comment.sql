#sql("findById")
SELECT
 *
FROM p_comment
WHERE `id` = #para(0)
#end

#sql("getByType")
SELECT
 *
FROM p_comment
WHERE `type` = #para(0)
#end