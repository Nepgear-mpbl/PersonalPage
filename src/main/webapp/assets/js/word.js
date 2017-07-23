$(document).ready(function () {
    $('#word-add-form').submit(function (e) {
        e.preventDefault();
        var postData = {text: $('#text-input').val()};
        var guestName = $('#guest-name').text();
        if (guestName !== '') {
            postData.name = guestName;
        }
        else {
            postData.name=$('#name-input').val();
        }
        console.log(postData);
        $.post('/word/uploadWord', postData, function (retJson) {
            if (retJson.status) {
                layer.msg(retJson.message);
                setTimeout('location.reload()',1000);
            } else {
                layer.msg(retJson.message);
            }
        });
    });
    $('.remove').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        layer.confirm('确定删除吗?', function (index) {
            $.post(url, {}, function (retJson) {
                if (retJson.status) {
                    layer.msg(retJson.message);
                    setTimeout('location.reload()', 1000);
                } else {
                    layer.msg(retJson.message);
                }
            });
            layer.close(index);
        });
    });
});