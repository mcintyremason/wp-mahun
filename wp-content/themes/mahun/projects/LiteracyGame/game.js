function Ship(canvas, url, x, y) {
    this.canvas = canvas;
    this.ctx = canvas[0].getContext('2d');
    this.img = $('<img>');
    this.img.attr('src', url);
    this.img.load(function(e) {
	this.width = this.img[0].width;
	this.height = this.img[0].height;
    }.bind(this));
    this.x = x;
    this.y = y;
    this.direction = 0;
    this.speed = 4;
    this.rspeed = 0.03;
    this.maxX = this.canvas.width();
    this.maxY = this.canvas.height();
    this.missles = new Array();
    this.oldTime = 0;
    this.firing = false;
    this.paused = false;
}

Ship.prototype.forward = function() {
    this.x -= this.speed * Math.sin(this.direction); 
    this.y -= this.speed * Math.cos(this.direction);
    if (this.x < 0)  this.x = this.maxX;
    else if (this.x > this.maxX) this.x = 0;
    if (this.y < 0) this.y = this.maxY;
    else if (this.y > this.maxY) this.y = 0;
}

Ship.prototype.backward = function() {
    this.x += this.speed * Math.sin(this.direction); 
    this.y += this.speed * Math.cos(this.direction);
    if (this.x < 0) this.x = this.maxX;
    else if (this.x > this.maxX) this.x = 0;
    if (this.y < 0) this.y = this.maxY;
    else if (this.y > this.maxY) this.y = 0;
}

Ship.prototype.cw = function() {
    this.direction += this.rspeed;
}

Ship.prototype.ccw = function() {
    this.direction -= this.rspeed;
}

Ship.prototype.draw = function() {
    this.ctx.save();
    this.ctx.translate(this.x, this.y);
    this.ctx.rotate(-this.direction);
    this.ctx.drawImage(this.img[0], -this.width / 2, -this.height / 2);
    this.ctx.restore();
    for (var i = 0; i < this.missles.length; i++) this.missles[i].draw();
}

Ship.prototype.fire = function() {
    this.newTime = new Date().getTime(); 
    if (this.newTime - this.oldTime > 200) {
	this.missles.push(new Missle(this.canvas, this.x, this.y, this.direction));
	this.oldTime = this.newTime;
    }              
}

function Missle(canvas, x, y, direction) {
    this.canvas = canvas;
    this.ctx = canvas[0].getContext('2d');
    this.x = x;
    this.y = y;
    this.maxX = this.canvas.width();
    this.maxY = this.canvas.height();
    this.speed = 8;
    this.direction = direction;
}

Missle.prototype.draw = function() {
    this.ctx.save();
    this.ctx.translate(this.x, this.y);
    this.ctx.rotate(-this.direction);
    this.ctx.fillStyle = 'black';
    this.ctx.fillRect(-1, -7, 2, 14);
    this.ctx.restore();
}


Missle.prototype.move = function() {
    this.x -= this.speed * Math.sin(this.direction);
    this.y -= this.speed * Math.cos(this.direction);
    if (this.x < 0) this.x = this.maxX;
    else if (this.x > this.maxX) this.x = 0;
    if (this.y < 0) this.y = this.maxY;
    else if (this.y > this.maxY) this.y = 0;
}

function Game(canvas) {
    this.canvas = canvas;
    this.width = this.canvas.width();
    this.height = this.canvas.height();
    this.canvas.css('border', '1px solid');
    this.ctx = canvas[0].getContext('2d');
    this.ship = new Ship(this.canvas, 'f15.png', this.width/2, this.height/2);
    this.timer = new Worker('timer.js');
    this.east = false; 
    this.north = false;
    this.south = false;
    this.west = false;
    $(document).keydown(function(e) { 
	console.log(e.keyCode);
	switch (e.keyCode) {
	case 32: this.ship.firing = true; break;
	case 37: this.east = true; break;
	case 38: this.north = true; break;
	case 39: this.west = true; break;
	case 40: this.south = true; break;
	case 80: 
	    if (this.paused) {
		this.paused = false; 
		this.timer.postMessage('on');
	    }
	    else {
		this.paused = true;
		this.timer.postMessage('off');
	    }
	    break;
	}
	return false;
    }.bind(this));
    $(document).keyup(function(e) { 
	console.log(e.keyCode);
	switch (e.keyCode) {
	case 32: this.ship.firing = false; break;
	case 37: this.east = false; break;
	case 38: this.north = false; break;
	case 39: this.west = false; break;
	case 40: this.south = false; break;
	}
	return false;
    }.bind(this));
    this.timer.addEventListener('message', function() {
	if (this.north) this.ship.forward();
	if (this.east) this.ship.cw();
	if (this.west) this.ship.ccw();
	if (this.south) this.ship.backward();
	if (this.ship.firing) this.ship.fire();
	for (var i = 0; i < this.ship.missles.length; i++) {
	    this.ship.missles[i].move();
	}
	this.draw();
    }.bind(this));
    this.timer.postMessage('on');
}

Game.prototype.draw = function() {
    this.ctx.fillStyle = 'white';
    this.ctx.fillRect(0, 0, this.width, this.height);
    this.ship.draw();
}

$(document).ready(function() {
    var canvas = $('<canvas height="600" width="600"></canvas>');
    $('body').append(canvas);
    new Game(canvas);
});