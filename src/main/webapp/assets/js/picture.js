$(document).ready(function () {
    var isPicSelected=false;
    $('#img-input').change(function () {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#preview-img').attr("src", e.target.result);
        };
        reader.readAsDataURL(file);
        isPicSelected=true;
    });
    $('#img-upload-btn').click(function () {
        if(!isPicSelected){
            layer.msg("没图？你传什么");
            return;
        }
        if($('#title-input').val()===''){
            layer.msg("不要忘了标题");
            return;
        }
        if($('#intro-input').val()===''){
            layer.msg("不要忘了简介");
            return;
        }
        var formData = new FormData(document.getElementById("img-upload-form"));
        console.log(formData);
        $.ajax({
            url: '/picture/uploadPicture',
            type: 'POST',
            xhr: function () {
                myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
                }
                return myXhr;
            },
            beforeSend:function () {
                $('progress').show();
            },
            success: function (retJson) {
                $('progress').hide();
                layer.msg(retJson.message.toString());
                setTimeout('location.reload()',1000);
            },
            error: function () {
                $('progress').hide();
                layer.msg("上传失败");
            },
            data: formData,
            cache: false,
            contentType: false,
            processData: false
        });
    });
    function progressHandlingFunction(e) {
        $('progress').attr({value: e.loaded, max: e.total});
    }

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
    $('#picture-select').change(function () {
        var type = $(this).children('option:selected').val();
        if(type==='0'){
            location.href="/picture";
        }else{
            location.href="/picture?type="+type.toString();
        }
    })
});


