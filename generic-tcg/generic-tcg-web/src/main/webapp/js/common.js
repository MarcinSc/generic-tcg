function iterObj(obj, func) {
    for (var key in obj) {
        if (obj.hasOwnProperty(key)) {
            func(key, obj[key]);
        }
    }
}

function log(text) {
    if (true)
//    if (getUrlParam("log") == "true")
        console.log(text);
}