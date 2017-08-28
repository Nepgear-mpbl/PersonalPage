#sql("getAll")
SELECT
 *
FROM p_word
ORDER BY  `word_time` DESC
#end

#sql("findById")
SELECT
 *
FROM p_word
WHERE `id` = #para(0)
#end