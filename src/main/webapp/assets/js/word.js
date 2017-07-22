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
});