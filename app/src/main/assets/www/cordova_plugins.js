cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
     {
        "file": "plugins/cordova-plugin-intent/newIntent.js",//js文件路径
        "id": "org.apache.cordova.intent",//js cordova.define的id
        "clobbers": [
            "startIntent"//js 调用时的方法名
        ]
    }];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.2"
};
// BOTTOM OF METADATA
});