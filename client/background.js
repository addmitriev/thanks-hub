chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
    if (typeof changeInfo.url != 'undefined') {
        if (changeInfo.url.indexOf('http://thanks-hub.herokuapp.com/api/auth?code=') != -1) {
            var startindex = 'http://thanks-hub.herokuapp.com/api/auth?code='.length;
            var code = changeInfo.url.substring(startindex, changeInfo.url.length);
            var tab = localStorage.getItem('thankshub_tab');
            chrome.tabs.sendMessage(parseInt(tab), code, function () {
            });
        }

    }


});

chrome.runtime.onMessage.addListener(function (message, sender) {
    localStorage.setItem('thankshub_tab', sender.tab.id);
});

