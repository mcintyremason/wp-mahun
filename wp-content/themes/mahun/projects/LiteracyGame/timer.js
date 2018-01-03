function go() {
    if (on) {
	setTimeout(function() {
	    postMessage(''); 
	    go();
	}, 16.67);
    }
}

addEventListener('message', function(x) {
    switch (x.data) {
    case 'on': 
	on = true;
	go();
	break;
    case 'off':
	on = false;
	break;
    }
});