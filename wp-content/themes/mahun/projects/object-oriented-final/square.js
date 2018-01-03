//Mason McIntyre

function Picture(canvas){
    this.canvas = canvas;
    this.width = this.canvas.width();
    this.height = this.canvas.height();
    this.ctx = canvas[0].getContext('2d');
    this.rect = canvas[0].getBoundingClientRect();
    this.background = '#FFFFFF';
    this.square = new Square(this);
}

function Square(picture){
    this.picture = picture;
    this.rect = picture.rect;
    this.ctx = picture.ctx;
    this.width = 100;
    this.height = 100;
    this.x = (this.picture.width/2) - (this.width/2);
    this.y = (this.picture.height/2) - (this.height/2);
    this.cOutline = '#000000';
}

Picture.prototype.draw = function(){
    this.ctx.clearRect(0,0,this.width, this.height);
    this.ctx.fillStyle = this.background;
    this.ctx.fillRect(0,0, this.width, this.height);
    this.ctx.strokeStyle = this.square.cOutline;
    this.ctx.strokeRect(this.square.x, this.square.y, this.square.width, this.square.height);
    console.log('drawn');
}


$('document').ready(function(){
    var canvas = $('<canvas height="500" width="500"></canvas>');
    $('body').append(canvas);

    var pic = new Picture(canvas);
    

    $('#cake')[0].onclick = function()
    {
	var max = 20;
	var i;
	setInterval(function(){	    
	    if(i != max)
	    {
		i += 2;
		pic.square.width += 2;
		pic.square.height += 2;
		pic.square.x = (pic.width/2) - (pic.square.width/2);
		pic.square.y = (pic.height/2) - (pic.square.height/2);
		pic.draw();
	    }
	    else
	    {
		clearInterval();
	    }
	},50);
	i = 0;
        console.log("clicked");
    }

    $('#potion')[0].onclick = function()
    {
	var max = 20;
	var i;
	if(pic.square.width > 0)
	{
	    setInterval(function(){	   
		
		if(i != max)
		{
		    if(pic.square.width > 0)
		    {
			i += 2;
			pic.square.width -= 2;
			pic.square.height -= 2;
			pic.square.x = (pic.width/2) - (pic.square.width/2);
			pic.square.y = (pic.height/2) - (pic.square.height/2);
			pic.draw();
		    }
		}
		else
		{
		    clearInterval();
		}
	    
	    },50);
	}
	i = 0;
        console.log("clicked");
    }

    pic.draw();

})