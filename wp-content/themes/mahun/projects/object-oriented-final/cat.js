// Mason McIntyre

function Picture(canvas){
    this.canvas = canvas;
    this.width = this.canvas.width();
    this.height = this.canvas.height();
    this.ctx = canvas[0].getContext('2d');
    this.img = new Image();
    this.img.width = 500;
    this.img.height = 400;
    this.img.onload = function(){
	this.ctx.drawImage(this.img,0,0);
    }.bind(this);
    this.img.src = "cat.png";
    this.img.opacity = 1.0;
}

Picture.prototype.draw = function(){
    this.ctx.clearRect(0,0,this.width, this.height);
    this.ctx.drawImage(this.img,0,0);
    console.log('drawn');
}

$(document).ready(function() {
    var canvas = $('<canvas height="400" width="500"></canvas>');

    $('body').append(canvas);

    var pic = new Picture(canvas);

    $('#fade')[0].onclick = function()
    {
	var alpha = pic.ctx.globalAlpha;
	setInterval(function(){
	    if(alpha > 0)
	    {
		alpha -=.01;	
		pic.ctx.globalAlpha = alpha;
		pic.draw();
	    }
	    else
		clearInterval();
	},10);
	console.log("clicked");
    }
    pic.draw();
})
		  