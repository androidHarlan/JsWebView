var sdk = new jsSdk()
var jsBridges;
var jsTransactionManagers = new jsTransactionManager();
var jsSubscribeManagers = new jsSubscribeManager();
sdk.onJsSdkDidInited()
function jsSdk(){
	this.init = function(){
		jsBridges = new jsBridge()
		//jsTransactionManagers = new jsTransactionManager();
		//jsSubscribeManagers = new jsSubscribeManager();
		jsBridges.initBridge(this.onJsBridgeDidInited)
	}
	this.onJsBridgeDidInited = function(bridge){
		jsBridges.bridge = bridge;
		jsTransactionManagers.init(jsBridges)
		jsSubscribeManagers.init(jsBridges)
	}
	this.onJsSdkDidInited = function(){
		setTimeout("sdk.init()",300);
	}
}

function jsBridge(){
	this.bridge = null;
	this.initBridge = function(onJsBridgeDidInited){
		setupWebViewJavascriptBridge(function(bridge) {//运用方法获取bridge
			if(onJsBridgeDidInited) {//判断是否存在参数
				onJsBridgeDidInited(bridge);//这里是用于注册调度bridge
			}
		})
	}
	this.callHandler = function(method,param,transacitionCallback){
		 this.bridge.callHandler(method,param,transacitionCallback);
	}
	this.registerHandler = function(method,callback){
		this.bridge.registerHandler(method,callback);	
	}
}

function jsTransaction(){
	this.transacitionId = null;
	this.method = null;
	this.param = null;
	this.struct = null;//结构体
	this.invokeCallbackByName = null;
		/*function(method,param){
		for(var p in param){
			if(typeof param[p] == 'function'&&p==method){
				param[p]();
			}
		}
	}*/
}

function jsTransactionManager() {
	this.jsBridge = null,
	this.jsTransactions = [],
	this.data = null,
	this.nowId = null,
	this.init = function(jsBridgeParam){
		try{
			jsBridgeParam.bridge.init(function(message, responseCallback) {//注册返回
				var data = {};
				responseCallback(data);
			});	
		}catch(e){
//			alert(e)
		}
		this.jsBridge = jsBridgeParam;
		console.log(this.jsBridge)
		// alert(this.jsBridge)
	}
	this.callback = function(method,param,callback){
		var id = uuid()
		this.nowId = id;
//		this.addTransaction(id,method,param)
		var jsonParam = this.formatDispatchParam(id,method,param);
		this.jsBridge.callHandler("submitFromWeb",jsonParam,callback);
	}
	this.call = function(method,param,invokeCallback){//只能有一个方法
		var id = uuid()
		this.addTransaction(id,method,param,invokeCallback)
		var jsonParam = this.formatDispatchParam(id,method,param);
		this.jsBridge.callHandler("submitFromWeb",jsonParam,function(responseData){
			//alert("有数据返回啦"+responseData)
			//if(responseData.success=="true"){
			//alert("callhandler");
			jsTransactionManagers.onTransactionSuccess(responseData)
			//}else{
			//	this.onTransactionError(responseData)
			//}
		});
	}
	this.onTransactionSuccess = function(data){
		var json = eval("("+data+")")
		//data是json数据
		//alert(json)
		//alert("onTransactionSuccess"+json.id);
		var transaction = this.getTransactionById(json.id);
		//alert(transaction.transacitionId);
		if(json.callback!=null){
			//alert(transaction.invokeCallbackByName)
			transaction.invokeCallbackByName(json.param)
		}
	}
	this.onTransactionError = function(data){
		alert("报错")
	}
	this.callUrl = function(url){
		var urlParam = url.split("?");
		if(urlParam.length==2){
			var method = urlParam[0]
			var paramArray = urlParam[1].split("&");
			var param = "{";
			for(var params in paramArray){
				var tempParams = paramArray[params].split("=");
				if(tempParams.length==1){
					param += "'"+tempParams[0]+"':"+"'',";
				}else if(tempParams.length==2){
					param += "'"+tempParams[0]+"':"+"'"+tempParams[1]+"',";
				}
			}
			if(param.length>2){
				param = param.substring(0,param.length-1)
			}
			param+="}"
			this.call(method,param)
		}else{
			this.call(method,"");
		}
	}
	this.addTransaction = function(id,method,param,invokeCallback){
		var jsTransactions = new  jsTransaction();
		jsTransactions.transacitionId = id;
		jsTransactions.method = method;
		jsTransactions.param = param;
		jsTransactions.invokeCallbackByName = invokeCallback;
		this.jsTransactions.push(jsTransactions)
	}
	this.addTransaction2 = function(id,method,param,struct){
		var jsTransactions = new  jsTransaction();
		jsTransactions.transacitionId = id;
		jsTransactions.method = method;
		jsTransactions.param = param;
		jsTransactions.struct = struct;
		this.jsTransactions.push(jsTransactions)
	}
	this.getTransactionById = function(id){	
		for (var i = 0 ;i<this.jsTransactions.length;i++) {
			var nowTransaction = this.jsTransactions[i]
			if(nowTransaction.transacitionId==id){
				
				return nowTransaction;
			}
		}
		return null;
	}
	this.formatDispatchParam = function(transactionId,method,param){
		var json = eval("("+"{'header':''}"+")");
		//var jsonParam = eval("("+param+")")
		//jsonParam["url"] = method;
		//jsonParam["id"] = transactionId;
		json["url"] = method;
		json["id"] = transactionId;
		json["params"] = param;
		return json
	}
}

function jsSubscriber() {
	this.subject = null
	this.pool = null
	this.callback = function(message){}
}

function jsSubscribeManager(){
	this.jsBridge = null;
	this.subscribers = [];
	this.init = function(jsBridgeParam){
//		jsBridgeParam.registerHandler("functionInJs",function(data,responseCallback){
//			var responseData = "Javascript Says Right back aka!";
//          responseCallback(responseData);
//		});
		jsBridgeParam.registerHandler("dispatchNotification",function(data,responseCallback){
			var responseData = "Javascript Says Right back aka!";
            responseCallback(responseData);
		});
		this.jsBridge = jsBridgeParam
	}
	this.subscribe = function(pool,subject,callback){
		var jsSubscriberParam = new jsSubscriber();
		jsSubscriberParam.pool = pool;
		jsSubscriberParam.subject = subject
		jsSubscriberParam.callback = callback
		this.addSubscribe(jsSubscriberParam)
		var jsTransactionManagerParam = new jsTransactionManager();
		jsTransactionManagerParam.call("subscribe",{'pool':pool,'subject':subject});//有问题
	}
	this.addSubscribe = function(subscribe){
		this.subscribers.push(subscribe)
	}
	this.dispatchNotification = function(data){
		var subscriber = this.getSubscriberBySubject(data.pool,data.subject)
		if(data){
			subscriber.callback(data.message);	
		}
	}
	this.getSubscriberBySubject = function(pool,subject){
		for (var subscriberNum in this.subscribers) {
			var subscriber = this.subscribers[subscriberNum]
			if(subscriber.pool==pool&&subscriber.subject==subject){
				return subscriber;
			}
			//subscriber.callback(subscriber[subscriber].message)
		}
	}
}

function setupWebViewJavascriptBridge(callback) {
	if(window.WebViewJavascriptBridge) {
		return callback(WebViewJavascriptBridge);
	}
	if(window.WVJBCallbacks) {
		return window.WVJBCallbacks.push(callback);
	}
	window.WVJBCallbacks = [callback];
	var WVJBIframe = document.createElement('iframe');
	WVJBIframe.style.display = 'none';
	WVJBIframe.src = 'tgwvjbscheme://__BRIDGE_LOADED__';
	document.documentElement.appendChild(WVJBIframe);
	setTimeout(function() {
		document.documentElement.removeChild(WVJBIframe)
	}, 0)
	if (window.WebViewJavascriptBridge) {
		callback(WebViewJavascriptBridge)
	} else {
		document.addEventListener(
			'WebViewJavascriptBridgeReady'
			, function() {
				callback(WebViewJavascriptBridge)
			},false);
	}
}

//生成uuid
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "";
 
    var uuid = s.join("");
    return uuid;
}