#sql("findById")
SELECT
 *
FROM p_picture
WHERE `id` = #para(0)
#end

#sql("getByType")
SELECT
 *
FROM p_picture
WHERE `type` = #para(0)
#end
