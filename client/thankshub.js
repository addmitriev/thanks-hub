function thankshub(){
	var thanks_button = document.createElement('a');
	$(thanks_button).attr('href', '#');
	$(thanks_button).text('Thanks it!');
	$(thanks_button).addClass('minibutton thankshub_btn');

	var thankshub_btn_list = $(thanks_button).clone();
	thankshub_btn_list.removeClass('minibutton').addClass('button-outline thankshub_btn');
	var thankshub_btn_list_wrapper = document.createElement('div');
	$(thankshub_btn_list_wrapper).addClass('thankshub_btn_list_wrapper table-list-cell');
	$(thankshub_btn_list_wrapper).append(thankshub_btn_list);
	$('li.commit.commits-list-item').append(thankshub_btn_list_wrapper);

	$('.meta .actions').append(thanks_button);
}



	$(document).on('click','.thankshub_btn', function(e){
		e.preventDefault();
		alert('You are awesome!')
	});

