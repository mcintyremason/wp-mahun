function go() {
    if (on) {
	setTimeout(function() {
	    postMessage(''); 
	    go();
	}, 16.67);
    }
}

addEventListener('message', function(x) {
    if(x.data == 'on') 
    {
	on = true;
   	go();
    }
});