#sql("findById")
SELECT
 *
FROM p_comment
WHERE `id` = #para(0)
#end

#sql("getByTypeAndParent")
SELECT
 *
FROM p_comment
WHERE `type` = #para(0)
and `parent` = #para(1)
ORDER BY  `comment_time` DESC
#end