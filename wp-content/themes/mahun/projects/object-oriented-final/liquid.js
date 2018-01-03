// Mason McIntyre

function Picture(canvas){
    this.canvas = canvas;
    this.width = this.canvas.width();
    this.height = this.canvas.height();
    this.rect = canvas[0].getBoundingClientRect();
    this.ctx = canvas[0].getContext('2d');
    this.background = '#1C6BA0';
    this.rectangle = new Rectangle(this);
    this.displacementy = 0;
    this.oldx = 0;
    this.oldy = 0;
    this.newx = 0;
    this.newy = 0;
    this.canvas.mousedown(function(e) {
	this.moving = true;
	this.oldx = e.pageX - this.rect.left;
	this.oldy = e.pageY - this.rect.top;
	this.mouse(e);
	this.canvas.css('cursor', 'pointer');
    }.bind(this));
    this.canvas.mousemove(function(e) {
	if (this.moving) this.mouse(e);
    }.bind(this));
    this.canvas.mouseup(function(e) {
	this.mouse(e);
	this.moving = false;
	this.canvas.css('cursor', 'default');
    }.bind(this));
}

function Rectangle(picture){
    this.picture = picture;
    this.ctx = this.picture.ctx;
    this.x = 0;
    this.y = this.picture.height/2;
    this.width = this.picture.width;
    this.height = this.picture.height - this.y;
    this.cFill = this.picture.background;
}

Picture.prototype.mouse = function(e) {
    this.newx = e.pageX - this.rect.left;
    this.newy = e.pageY - this.rect.top;
    this.displacementy = this.newy - this.oldy;
  
    this.rectangle.y += this.displacementy;
    this.rectangle.height = this.height - this.rectangle.y;

    this.oldx = this.newx;
    this.oldy = this.newy;
    this.draw();
}

Picture.prototype.draw = function(){
    this.ctx.clearRect(0,0,this.width, this.height);
    this.ctx.fillStyle = '#FFFFFF'
    this.ctx.fillRect(0,0, this.width, this.height);
    this.ctx.fillStyle = this.rectangle.cFill;
    this.ctx.fillRect(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height);
    console.log('drawn');
}


$('document').ready(function(){
    var canvas = $('<canvas height="500" width="500"></canvas>');
    $('body').append(canvas);

    var pic = new Picture(canvas);    

    pic.draw();

})