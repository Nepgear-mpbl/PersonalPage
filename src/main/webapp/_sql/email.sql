#sql("getAll")
SELECT
 *
FROM p_email
ORDER BY  `addtime` DESC
#end

#sql("findById")
SELECT
 *
FROM p_email
WHERE `id` = #para(0)
#end

#sql("findByAddress")
SELECT
 *
FROM p_email
WHERE `address` = #para(0)
#end