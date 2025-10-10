 function ajax (type,src,data,success) {
        var closeindex = layer.load();
        $.ajax({
            type: type,
            url: src,
            data: data,
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            error: function () {
                layer.msg('网络异常');
                layer.close(closeindex);
            },
            crossDomain: true
        }).done(function (ret) {
            console.log(ret)
            layer.close(closeindex);
            if(ret.code == -1000) {
                window.location.href="/"
                return false;
            }
            success?success(ret):function(){};
        });
}
 function ajaxjson (src,data,success) {
     var closeindex = layer.load();
     $.ajax({
         type: "POST",
         url: src,
         data: JSON.stringify(data),
         contentType: "application/json; charset=utf-8",
         dataType: 'json',
         xhrFields: {
             withCredentials: true
         },
         error: function () {
             layer.msg('网络异常');
             layer.close(closeindex);
         },
         crossDomain: true
     }).done(function (ret) {
         console.log(ret)
         layer.close(closeindex);
         if(ret.code == -1000) {
             window.location.href="/"
             return false;
         }
         success?success(ret):function(){};
     });
 }
