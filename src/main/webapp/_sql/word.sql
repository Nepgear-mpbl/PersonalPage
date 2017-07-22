#sql("getAll")
SELECT
 *
FROM p_word
#end

#sql("findById")
SELECT
 *
FROM p_word
WHERE `id` = #para(0)
#end