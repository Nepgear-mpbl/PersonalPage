#sql("findById")
SELECT
 *
FROM p_picture
WHERE `id` = #para(0)
#end

#sql("findByIdWithType")
SELECT
  p_picture.*
  ,p_picture_type.type_name
FROM p_picture INNER JOIN p_picture_type ON p_picture.type=p_picture_type.id
WHERE p_picture.id = #para(0)
#end

#sql("getByType")
SELECT
 *
FROM p_picture
WHERE `type` = #para(0)
ORDER BY  `upload_time` DESC
#end


#sql("getAll")
SELECT
 *
FROM p_picture
ORDER BY  `upload_time` DESC
#end