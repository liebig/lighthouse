function toast(message, type) {
    $.notify({
		// options
		message: message 
	},
	{
		// settings
		element: 'body',
		type: type,
		placement: {
			from: "top",
			align: "center"
		},
		offset: 0,
		spacing: 10,
		z_index: 1031,
		delay: 5000,
		timer: 1000,
		mouse_over: null,
		animate: {
			enter: 'animated fadeInDown',
			exit: 'animated fadeOutUp'
		}
	});
};
