var eventBinded = false;
var my_window;
var thankshub = {
    reciever: false,
    init: function () {
        var thanks_button = document.createElement('a');
        $(thanks_button).attr('href', '#');
        $(thanks_button).text('Thanks for it!');
        $(thanks_button).addClass('minibutton thankshub_btn');

        var thankshub_btn_list = $(thanks_button).clone();
        thankshub_btn_list.removeClass('minibutton').addClass('button-outline thankshub_btn');
        var thankshub_btn_list_wrapper = document.createElement('div');
        $(thankshub_btn_list_wrapper).addClass('thankshub_btn_list_wrapper table-list-cell');
        $(thankshub_btn_list_wrapper).append(thankshub_btn_list);
        $('li.commit.commits-list-item').append(thankshub_btn_list_wrapper);
        $('.details-collapse .button-group').append(thanks_button);
        if (typeof chrome.extension != 'undefined') {
            var img_src = chrome.extension.getURL('icon.png');
            $('body').append('<style>.thankshub_form .header .logo:before { background: url(' + img_src + ') no-repeat; background-size:100%;}')
        }
        if (typeof chrome.runtime != 'undefined') {
            chrome.runtime.sendMessage('thankshub');
        }
    },
    auth: function () {

        var reciever = localStorage.getItem('thankshub_reciever');

        var content = '<div class="thankshub_form">\
    <div class="container">\
        <div class="header">\
            <div class="logo">ThanksHub!</div>\
        </div>\
        <div class="body">\
            <form action="https://sp-money.yandex.ru/oauth/authorize" method="post" data-submit-auth="">\
                <div class="form-group">\
                    <label for="input">Send money to <i>' + reciever + '</i> </label>\
                     <input type="hidden" name="client_id" value="6C5764252E3AFFAFD29B1023327C8AA75F8D95B1CE9C4DD457132DD7BF31D6C0"/>\
                     <input type="hidden" name="response_type" value="code"/>\
                     <input type="hidden" name="redirect_uri" value="http://thanks-hub.herokuapp.com/api/auth"/>\
                     <input type="hidden" name="scope" value="account-info operation-details payment-p2p"/>\
                </div>\
                <div class="form-group">\
                    <input type="submit" value="Pay with Yandex.Money" data-loading="false"/>\
                </div>\
            </form>\
        </div>\
    </div>\
	    </div>';
        thankshub.createModal("ThanksHub!", content);

    },
    bindEvents: function () {
        if (eventBinded == true) return false;
        $(document).on('click', '.thankshub_btn', function (e) {
            e.preventDefault();
            e.stopPropagation();
            var _self = $(this);
            localStorage.setItem('thankshub_reciever', $(_self.parents('.commit').find('.commit-author')).text());
            localStorage.setItem('thankshub_commit_id', $(_self.parents('.commit').find('.commit-links-group .sha')).attr('href'));

            thankshub.auth();
        });

        $(document).on('click', ".thankshub_modal_wrapper", function (e) {
            e.stopPropagation();
            $(this).remove();
        });

        $(document).on('click', ".thankshub_modal_wrapper *", function (e) {
            e.stopPropagation();
        });

        $(document).on('click', ".thankshub_modal_close", function (e) {
            e.stopPropagation();
            $(this).parents('.thankshub_modal_wrapper').remove();
        });

        $(document).on('submit', '[data-submit-auth]', function (e) {
            e.preventDefault();
            e.stopPropagation();
            var form = $(this).clone().off();
            var btn = $(this).find('[data-loading="false"]')
            btn.attr('disabled', 'disabled')
            btn.attr('data-loading', 'true');
            btn.val('OAuth in process...');
            $(this).find('[type="submit"]').attr('disabled', 'disabled');
            var auth_window = window.open("", "_blank");
            my_window = auth_window;
            auth_window.document.body.appendChild(form[0]);
            form.hide();
            form.submit();
        });


        $(document).on('submit', '[data-submit-payment]', function (e) {
            e.preventDefault();
            var btn = $(this).find('[data-loading="false"]')
            btn.attr('disabled', 'disabled')
            btn.attr('data-loading', 'true');
            btn.val('Payment in process...');
            $.post('https://thanks-hub.herokuapp.com/api/payment', $(this).serialize(), function (data) {
                $('.thankshub_form').html(data);
            });
        })

        eventBinded = true;

    },

    createModal: function (heading, content) {
        var modal = document.createElement('div');
        var modal_wrapper = document.createElement('div');
        var modal_header = document.createElement('div');
        var modal_body = document.createElement('div');
        var modal_heading = document.createElement('h1');
        $(modal_heading).text(heading);
        $(modal).addClass('thankshub_modal');
        $(modal_wrapper).addClass('thankshub_modal_wrapper');
        $(modal_header).addClass('thankshub_modal_header');
        $(modal_header).append('<a href="#" class="thankshub_modal_close thankshub_modal_close_times right">×</a>')
        $(modal_header).append(modal_heading);
        $(modal_body).addClass('thankshub_modal_body');
        $(modal).append(modal_header);
        $(modal).append(modal_body);
        $(modal_wrapper).append(modal);
        $(modal_body).append(content);
        $('body').append(modal_wrapper);
    },

    createPaymentForm: function (code) {
        var reciever = localStorage.getItem('thankshub_reciever');
        var commit_id = 'https://github.com' + localStorage.getItem('thankshub_commit_id');
        var content = ' <div class="container">\
        <div class="header">\
            <div class="logo">ThanksHub!</div>\
        </div>\
        <div class="body">\
            <form action="https://thanks-hub.herokuapp.com/api/payment" method="post" data-submit-payment="">\
                <div class="form-group">\
                    <label for="input">Amount:</label>\
                    <input type="text" name="amount" placeholder="Amount"/>\
                     <input type="hidden" name="code" value="' + code + '"/>\
                     <input type="hidden" name="gitHubUser" value="' + reciever + '"/>\
                     <input type="hidden" name="commitId" value="' + commit_id + '"/>\
                </div>\
                <div class="form-group">\
                    <input type="submit" value="Proceed!" data-loading="false"/>\
                </div>\
            </form>\
        </div>\
    </div>'
        $('.thankshub_form').html(content);
    }


}

$(document).on('pjax:complete', function () {
    thankshub.init();
})

