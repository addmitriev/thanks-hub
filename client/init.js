
window.addEventListener("load", thankshub.init());

var s = document.createElement('script');
s.src = chrome.extension.getURL('thankshub.js');
s.onload = function() {
    	    thankshub.bindEvents();
};
(document.head||document.documentElement).appendChild(s);


chrome.runtime.onMessage.addListener(function(code, sender){
	 thankshub.createPaymentForm(code);
}) 
