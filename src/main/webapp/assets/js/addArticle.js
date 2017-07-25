$(document).ready(function () {
    $('#article-add-form').submit(function (e) {
        e.preventDefault();
        var postData = {title: $('#title-input').val(),text: $('#text-input').val(),type:$('#type-input').children('option:selected').val()};
        if($('#abstract-input').val()!==''){
            postData.abstract=$('#abstract-input').val();
        }
        console.log(postData);
        $.post('/article/uploadArticle', postData, function (retJson) {
            if (retJson.status) {
                layer.msg(retJson.message);
                setTimeout("location.href = '/article'",1000);
            } else {
                layer.msg(retJson.message);
            }
        });
    });
});