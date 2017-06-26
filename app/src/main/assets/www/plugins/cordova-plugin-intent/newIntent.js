cordova.define("org.apache.cordova.intent",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            goBack: function(){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "PluginIntent",//feature name
                "goBack",//action
                null//要传递的参数，json格式
                );
            },
            startLogin: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "PluginIntent",//feature name
                "startLogin",//action
                [content]//要传递的参数，json格式
                );
            },
             goHome: function(){
                 exec(
                 function(message){//成功回调function
                     console.log(message);
                 },
                 function(message){//失败回调function
                     console.log(message);
                 },
                 "PluginIntent",//feature name
                 "goHome",//action
                 null//要传递的参数，json格式
                 );
             },
              goOrder: function(){
                  exec(
                  function(message){//成功回调function
                      console.log(message);
                  },
                  function(message){//失败回调function
                      console.log(message);
                  },
                  "PluginIntent",//feature name
                  "goOrder",//action
                  null//要传递的参数，json格式
                  );
              },
               goCommodity: function(){
                   exec(
                   function(message){//成功回调function
                       console.log(message);
                   },
                   function(message){//失败回调function
                       console.log(message);
                   },
                   "PluginIntent",//feature name
                   "goCommodity",//action
                   null//要传递的参数，json格式
                   );
               }

        }
});
