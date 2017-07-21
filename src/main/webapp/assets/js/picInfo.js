$(document).ready(function () {
    $('#comment-add-form').submit(function (e) {
        e.preventDefault();
        var data = $("#comment-add-form").serializeArray()[0];
        var formData = {text: data.value};
        console.log(formData);
        var guestName = $('#guest-name').text();
        if (guestName !== '') {
            formData.name = guestName;
            sendRequest(formData);
        }
        else {
            $('#comment-name-div').modal({
                relatedTarget: this,
                dimmer: false,
                onConfirm: function (e) {
                    if (e.data !== '') {
                        formData.name = e.data;
                    } else {
                        formData.name = '游客';
                    }
                    console.log(formData);
                    sendRequest(formData);
                },
                onCancel: function (e) {
                    formData.name = '游客';
                    console.log(formData);
                    sendRequest(formData);
                }
            });
        }
    });

    function sendRequest(postData) {
        $.post('/comment/addComment/0-' + $('#pic-id').text(), postData, function (retJson) {
            if (retJson.status) {
                layer.msg("评论成功");
                location.reload();
            } else {
                layer.msg(retJson.message);
            }
        });
    }
});