function Rubber(canvasID) {
    this.canvas = $('#canvas');
    this.undo = $('#undo');
    this.ctx = this.canvas[0].getContext('2d');
    this.width = this.canvas.attr('width');
    this.height = this.canvas.attr('height');
    this.rect = this.canvas[0].getBoundingClientRect();
    this.background = $('#cBackground')[0].value;
    this.canvas.css('cursor','pointer');    
    this.ctx.font = '64pt Arial';
    this.ctx.textAlign = 'center';
    this.shapes = new Array();
    this.reshapes = new Array();
    this.shapeprops = new Array();
    this.savestring = '';
    this.currshape = null;
    this.oldx = 0;
    this.oldy = 0;
    this.newx = 0;
    this.newy = 0;
    this.dispx = 0;
    this.dispy = 0;
    this.canvas.mousedown(function(e){
	this.oldx = e.pageX - this.rect.left;
	this.oldy = e.pageY - this.rect.top;
	if(this.inshape(e) == false)
	{
	    if(this.currshape != null)
		this.currshape.selected = false;
	    if($('#shapes')[0].value != null)
	    {
		this.currshape = new Shape(this, e);
	    }
	}
		
	this.moving = true;
	this.mouse(e);
    }.bind(this));
    this.canvas.mousemove(function(e){
	if(this.moving) this.mouse(e);
	requestAnimationFrame(function() { this.draw(); }.bind(this));
     }.bind(this));
    this.canvas.mouseup(function(e){
	this.mouse(e);
	this.moving = false;
	if(this.currshape.shapetype == 'text')
	{
	    var drawtext = prompt('Please enter the text you wish to draw.', 'Text');
	    if(drawtext != null)
		this.currshape.text = drawtext;
	}
	if(this.currshape.topush == true)
	{
	    this.shapes.push(this.currshape);	    
	    this.currshape.topush = false;
	    this.currshape.selected = false;
	}
	this.draw();
    }.bind(this));
}

Rubber.prototype.inshape = function(e)
{
    var x = e.pageX - this.rect.left;
    var y = e.pageY - this.rect.top;
    var indomain = false;
    var inrange = false;
    var ret = false;
    var found = false;

    if(this.currshape != null)
	this.currshape.selected = false;

    if(this.shapes.length > 0)
    {
	var i = this.shapes.length-1;
	while(found != true && i >= 0)
	{
	    switch(this.shapes[i].shapetype)
	    {
	    case 'rectangle':
		if(this.shapes[i].fill == true)
		{
		    if(x > this.shapes[i].firstx && x < this.shapes[i].x)
			indomain = true;
		    else if(x < this.shapes[i].firstx && x > this.shapes[i].x)
		    	indomain = true;
		    else
			indomain = false;
		    if(y > this.shapes[i].firsty && y < this.shapes[i].y)
			inrange = true;
		    else if(y < this.shapes[i].firsty && y > this.shapes[i].y)
			inrange = true;
		    else
			inrange = false;
		    if(indomain == true && inrange == true)
		    {
			ret = true;
			this.currshape = this.shapes[i];
			this.currshape.selected = true;
			if(this.shapes.indexOf(this.currshape) < this.shapes.length-1)
			    $('#forward')[0].disabled = false;
			else
			    $('#forward')[0].disabled = 'disabled';
			if(this.shapes.indexOf(this.currshape) > 0)
			    $('#backward')[0].disabled = false;
			else
			    $('#backward')[0].disabled = 'disabled';
			found = true;
			console.log(this.shapes[i]);
			break;
		    }
		    indomain = false;
		    inrange = false;
		}
		break;
	    case 'ellipse':
		if(this.shapes[i].fill == true)
		{
		    if(x > this.shapes[i].firstx && x < this.shapes[i].x)
			indomain = true;
		    else if(x < this.shapes[i].firstx && x > this.shapes[i].x)
		    	indomain = true;
		    else
			indomain = false;
		    if(y > this.shapes[i].firsty && y < this.shapes[i].y)
			inrange = true;
		    else if(y < this.shapes[i].firsty && y > this.shapes[i].y)
			inrange = true;
		    else
			inrange = false;
		    if(indomain == true && inrange == true)
		    {
			ret = true;
			this.currshape = this.shapes[i];
			this.currshape.selected = true;
			found = true;
			console.log(this.shapes[i]);
			break;
		    }
		    indomain = false;
		    inrange = false;
		}
		break;
	    }
	    i--;
	}
    }
    return ret;    
}

function Shape(rubber, e){
    this.rubber = rubber;
    this.rect = rubber.canvas[0].getBoundingClientRect();
    this.ctx = rubber.canvas[0].getContext('2d');
    this.firstx = e.pageX - this.rect.left;
    this.firsty = e.pageY - this.rect.top;
    this.x =  e.pageX - this.rect.left;
    this.y =  e.pageY - this.rect.top;
    this.cOutline = $('#cOutline')[0].value;
    this.cFill = $('#cFill')[0].value;
    this.outline = false;
    this.fill = false;
    this.shapetype = null;
    this.centerx = this.firstx + ((this.x - this.firstx)/2);
    this.centery = this.firsty + ((this.y - this.firsty)/2);
    this.text = '';
    this.delim = ',';
    this.topush = true;
    this.selected = false;
    
    if($('#shapes')[0].value == "rectangle")
    {
	this.shapetype = 'rectangle';
    }
    if($('#shapes')[0].value == "line")
    {
	this.shapetype = 'line';
    }
    if($('#shapes')[0].value == "ellipse")
    {
	this.shapetype = 'ellipse';
    }
    if($('#shapes')[0].value == "text")
    {
	this.shapetype = 'text';
    }
    if($('#outline')[0].checked == true)
    {
	this.outline = true;
    }
    if($('#fill')[0].checked == true)
    {
	this.fill = true;
    }
   
    this.properties = this.firstx +this.delim+ this.firsty +this.delim+ this.x +this.delim+ this.y +this.delim+ this.cOutline +this.delim+ this.cFill +this.delim+ this.outline +this.delim+ this.fill +this.delim+ this.shapetype +this.delim+ this.centerx +this.delim+ this.centery +this.delim+ this.text;

}

Rubber.prototype.draw = function() {
    this.ctx.clearRect(0,0,this.width,this.height);
    this.background = $('#cBackground')[0].value;
    this.ctx.fillStyle = this.background;
    this.ctx.fillRect(0, 0, this.width, this.height);
    
    for(var i = 0; i < this.shapes.length; i++)
    {
	this.shapes[i].draw();    
	if(this.shapes[i].selected == true)
	{
	    this.ctx.strokeStyle = '#000000';
	    if(this.shapes[i].firstx < this.shapes[i].x && this.shapes[i].firsty > this.shapes[i].y)
		this.ctx.strokeRect(this.shapes[i].firstx - 5, this.shapes[i].firsty + 5, (this.shapes[i].x - this.shapes[i].firstx) + 10, (this.shapes[i].y - this.shapes[i].firsty) - 10);
	    else if(this.shapes[i].firstx > this.shapes[i].x && this.shapes[i].firsty > this.shapes[i].y)
		this.ctx.strokeRect(this.shapes[i].firstx + 5, this.shapes[i].firsty + 5, (this.shapes[i].x - this.shapes[i].firstx) - 10, (this.shapes[i].y - this.shapes[i].firsty) - 10);
	    else if(this.shapes[i].firstx < this.shapes[i].x && this.shapes[i].firsty < this.shapes[i].y)
		this.ctx.strokeRect(this.shapes[i].firstx - 5, this.shapes[i].firsty - 5, (this.shapes[i].x - this.shapes[i].firstx) + 10, (this.shapes[i].y - this.shapes[i].firsty) + 10);
	    else if(this.shapes[i].firstx > this.shapes[i].x && this.shapes[i].firsty < this.shapes[i].y)
		this.ctx.strokeRect(this.shapes[i].firstx + 5, this.shapes[i].firsty - 5, (this.shapes[i].x - this.shapes[i].firstx) - 10, (this.shapes[i].y - this.shapes[i].firsty) + 10);
	    
	}
    }

    if(this.shapes.length == 0)
    {
	$('#undo')[0].disabled = "disabled";
    }
    else
    {
	$('#undo')[0].disabled = false;
    }

    if(this.reshapes.length == 0)
    {
	$('#redo')[0].disabled = "disabled";
    }
    else
    {
	$('#redo')[0].disabled = false;
    }
    if(this.currshape != null && this.currshape.selected == false)
	this.currshape.draw();
    
}

Shape.prototype.draw = function() {
    if(this.shapetype == 'rectangle' && this.outline == true && this.fill == false)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.strokeRect(this.firstx, this.firsty, this.x - this.firstx , this.y - this.firsty);
    }
    if(this.shapetype == 'rectangle' && this.outline == true && this.fill == true)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.fillStyle = this.cFill;
	this.ctx.fillRect(this.firstx, this.firsty, this.x - this.firstx , this.y - this.firsty);
	this.ctx.strokeRect(this.firstx, this.firsty, this.x - this.firstx , this.y - this.firsty);
    }
    if(this.shapetype == 'rectangle' && this.outline == false && this.fill == true)
    {
	this.ctx.fillStyle = this.cFill;
	this.ctx.fillRect(this.firstx, this.firsty, this.x - this.firstx , this.y - this.firsty);
    }
    if(this.shapetype == 'line' && this.outline == true)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.beginPath();
	this.ctx.moveTo(this.firstx, this.firsty);
	this.ctx.lineTo(this.x, this.y);
	this.ctx.stroke();
    }
    if(this.shapetype == 'ellipse' && this.outline == true && this.fill == false)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.fillStyle = this.cFill;
	this.ctx.beginPath();
	this.ctx.moveTo(this.x, this.centery);
	var t = 0;
	var x;
	var y;
	for(var i = 0; i <= 1; i += .01)
	{
	    t = (i*2*Math.PI)/1
	    x = this.centerx + ((this.x-this.centerx) * Math.cos(t));
	    y = this.centery + ((this.y-this.centery) * Math.sin(t));
	    this.ctx.lineTo(x, y);
	    this.ctx.stroke();	    
	}
	this.ctx.lineTo(this.x, this.centery);
	this.ctx.stroke();	    
    }
    if(this.shapetype == 'ellipse' && this.outline == true && this.fill == true)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.fillStyle = this.cFill;
	this.ctx.beginPath();
	this.ctx.moveTo(this.x, this.centery);
	var t = 0;
	var x;
	var y;
	for(var i = 0; i <= 1; i += .01)
	{
	    t = (i*2*Math.PI)/1
	    x = this.centerx + ((this.x-this.centerx) * Math.cos(t));
	    y = this.centery + ((this.y-this.centery) * Math.sin(t));
	    this.ctx.lineTo(x, y);
	    this.ctx.stroke();	    
	}
	this.ctx.lineTo(this.x, this.centery);
	this.ctx.stroke();
	this.ctx.fill();
    }
    if(this.shapetype == 'ellipse' && this.outline == false && this.fill == true)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.fillStyle = this.cFill;
	this.ctx.beginPath();
	this.ctx.moveTo(this.x, this.centery);
	var t = 0;
	var x;
	var y;
	for(var i = 0; i <= 1; i += .01)
	{
	    t = (i*2*Math.PI)/1
	    x = this.centerx + ((this.x-this.centerx) * Math.cos(t));
	    y = this.centery + ((this.y-this.centery) * Math.sin(t));
	    this.ctx.lineTo(x, y);

	}
	this.ctx.lineTo(this.x, this.centery);
	this.ctx.fill();
    }

    if(this.shapetype == 'text' && this.outline == true && this.fill == true)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.fillStyle = this.cFill;
	this.ctx.font = '20px Georgia';
	this.ctx.fillText(this.text, this.x, this.y);
	this.ctx.strokeText(this.text, this.x, this.y);
    }
    if(this.shapetype == 'text' && this.outline == false && this.fill == true)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.fillStyle = this.cFill;
	this.ctx.font = '20px Georgia';
	this.ctx.fillText(this.text, this.x, this.y);
    }
    if(this.shapetype == 'text' && this.outline == true && this.fill == false)
    {
	this.ctx.strokeStyle = this.cOutline;
	this.ctx.fillStyle = this.cFill;
	this.ctx.font = '20px Georgia';
	this.ctx.strokeText(this.text, this.x, this.y);
    }
    
}

Rubber.prototype.mouse = function(e){
    if(this.currshape.selected == false)
    {
	
	if(this.moving)
	{
	    if($('#shapes')[0].value != null)
	    {
		this.currshape.x = e.pageX - this.currshape.rect.left;
		this.currshape.y = e.pageY - this.currshape.rect.top;
		this.currshape.centerx = this.currshape.firstx+((this.currshape.x-this.currshape.firstx)/2);
		this.currshape.centery = this.currshape.firsty+((this.currshape.y-this.currshape.firsty)/2);
	    }
	}
    
	this.currshape.draw();
    }
    else
    {
	this.newx = e.pageX - this.rect.left;
	this.newy = e.pageY - this.rect.top;
	if(this.moving)
	{
	    var i = this.shapes.indexOf(this.currshape);
	    this.disx = this.newx - this.oldx;
	    this.disy = this.newy - this.oldy;
	    this.shapes[i].firstx += this.disx;
	    this.shapes[i].x += this.disx;
	    this.shapes[i].firsty += this.disy;
	    this.shapes[i].y += this.disy;
	    this.shapes[i].centerx = this.shapes[i].firstx + ((this.shapes[i].x - this.shapes[i].firstx)/2);
	    this.shapes[i].centery = this.shapes[i].firsty + ((this.shapes[i].y - this.shapes[i].firsty)/2);
	    this.oldx = this.newx;
	    this.oldy = this.newy;
	
	}
	
	this.disx = 0;
	this.disy = 0;
    }
}

$(document).ready(function() {
    console.log("Ready");
    $('#cBackground')[0].value = '#FFFFFF';
    $('#cOutline')[0].value = "#000000";
    $('#cFill')[0].value = "#FFFFFF";
    $('#outline')[0].checked = true;
    $('#undo')[0].disabled = "disabled";
    $('#forward')[0].disabled = "disabled";
    $('#backward')[0].disabled = "disabled";
    var rubber = new Rubber();

    $('#forward')[0].onclick = function(){
	if(rubber.currshape != null)
	{
	    if(rubber.shapes.indexOf(rubber.currshape)+1 < rubber.shapes.length)
	    {
		var temp = rubber.shapes[rubber.shapes.indexOf(rubber.currshape)+1];
		console.log(temp);
		rubber.shapes[rubber.shapes.indexOf(rubber.currshape)+1] = rubber.currshape;
		rubber.shapes[rubber.shapes.indexOf(rubber.currshape)] = temp;
	    }
	    if(rubber.shapes.indexOf(rubber.currshape) == rubber.shapes.length-1)
	    {
		$('#forward')[0].disabled = 'disabled';
	    }
	    if(rubber.shapes.indexOf(rubber.currshape)-1 > -1)
	    {
		$('#backward')[0].disabled = false;
	    }
	}
	rubber.draw();
    }

    $('#backward')[0].onclick = function(){
	if(rubber.currshape != null)
	{
	    var i = rubber.shapes.indexOf(rubber.currshape);
	    if(i-1 > -1)
	    {
		var t = rubber.shapes[i-1];
		console.log(t);
		rubber.shapes[i-1] = rubber.currshape;
		rubber.shapes[i] = t;
	    }
	    console.log(i);
	    if(i-1 == 0)
	    {
		$('#backward')[0].disabled = 'disabled';
	    }
	    if(rubber.shapes.indexOf(rubber.currshape) < rubber.shapes.length-1)
	    {
		$('#forward')[0].disabled = false;
	    }
	}
	rubber.draw();
    }

    $('#outline')[0].onchange = function(){
	if(rubber.currshape != null)
	{
	    if(rubber.currshape.selected == true)
	    {
		var i = rubber.shapes.indexOf(rubber.currshape);
		if($('#outline')[0].checked == true)
		{
		    rubber.currshape.outline = true;
		}
		else
		{
		    rubber.currshape.outline = false;
		}
			
		rubber.shapes[i] = rubber.currshape;
	    }  
	}
	rubber.draw();
    }

    $('#fill')[0].onchange = function(){
	if(rubber.currshape != null)
	{
	    if(rubber.currshape.selected == true)
	    {
		var i = rubber.shapes.indexOf(rubber.currshape);
		if($('#fill')[0].checked == true)
		{
		    rubber.currshape.fill = true;
		}
		else
		{
		    rubber.currshape.fill = false;
		}
			
		rubber.shapes[i] = rubber.currshape;
	    }  
	}
	rubber.draw();
    }

    $('#cOutline')[0].onchange = function(){
	if(rubber.currshape != null)
	{
	    if(rubber.currshape.selected == true)
	    {
		var i = rubber.shapes.indexOf(rubber.currshape);
		rubber.currshape.cOutline = $('#cOutline')[0].value;
		rubber.shapes[i] = rubber.currshape;
	    }  
	}
	rubber.draw();
    }

    $('#cFill')[0].onchange = function(){
	if(rubber.currshape != null)
	{
	    if(rubber.currshape.selected == true)
	    {
		var i = rubber.shapes.indexOf(rubber.currshape);
		rubber.currshape.cFill = $('#cFill')[0].value;
		rubber.shapes[i] = rubber.currshape;
	    }  
	}
	rubber.draw();
    }


    $('#undo')[0].onclick = function(){
	rubber.reshapes.push(rubber.shapes[rubber.shapes.length-1]);
	rubber.shapes[rubber.shapes.length-1] = null;
	rubber.shapes.length--;
	rubber.currshape = null;
	rubber.draw();
    }
    $('#redo')[0].onclick = function(){
	rubber.shapes.push(rubber.reshapes[rubber.reshapes.length-1]);
	rubber.reshapes[rubber.reshapes.length-1] = null;
	rubber.reshapes.length--;
	rubber.draw();
    }
    $('#cBackground')[0].onchange = function(){
	rubber.draw();
    }
    $('#save')[0].onclick = function(){
	var savename = prompt('Please enter the name of the file you wish to save.', 'default');
	rubber.savestring = '';
	rubber.shapeprops = new Array();
	for(var i = 0; i < rubber.shapes.length; i++)
	{
	    rubber.shapes[i].properties = rubber.shapes[i].firstx +rubber.shapes[i].delim+ rubber.shapes[i].firsty +rubber.shapes[i].delim+ rubber.shapes[i].x +rubber.shapes[i].delim+ rubber.shapes[i].y +rubber.shapes[i].delim+ rubber.shapes[i].cOutline +rubber.shapes[i].delim+ rubber.shapes[i].cFill +rubber.shapes[i].delim+ rubber.shapes[i].outline +rubber.shapes[i].delim+ rubber.shapes[i].fill +rubber.shapes[i].delim+ rubber.shapes[i].shapetype +rubber.shapes[i].delim+ rubber.shapes[i].centerx +rubber.shapes[i].delim+ rubber.shapes[i].centery +rubber.shapes[i].delim+ rubber.shapes[i].text;
	    rubber.shapeprops.push(rubber.shapes[i].properties);
	}
	for(var i = 0; i < rubber.shapeprops.length; i++)
	{
	    rubber.savestring += '!' + rubber.shapeprops[i];
	}

	console.log(rubber.savestring);
	localStorage.setItem(savename, rubber.savestring);
	
	
    }
    $('#load')[0].onclick = function(e)
    {
	var loadname = prompt('Please enter the name of the file wish to load.', 'default');
	var loadstring = localStorage.getItem(loadname);
	console.log(loadstring);
	if(loadstring != null)
	{
	    var loadarray = loadstring.split('!');
	    
	    for(var i = 1; i < loadarray.length; i++)
	    {
		var shape = new Shape(rubber, e);
		var parseload = loadarray[i].split(',');
		shape.firstx = parseFloat(parseload[0]);
		shape.firsty = parseFloat(parseload[1]);
		shape.x = parseFloat(parseload[2]);
		shape.y =  parseFloat(parseload[3]);
		shape.cOutline = parseload[4];
		shape.cFill = parseload[5];
		if(parseload[6] == 'true')
		    shape.outline = true;
		else
		    shape.outline = false;
		if(parseload[7] == 'true')
		    shape.fill = true;
		else
		    shape.fill = false;
		shape.shapetype = parseload[8];
		shape.centerx = parseFloat(parseload[9]);
		shape.centery = parseFloat(parseload[10]);
		shape.text = parseload[11];		
		shape.topush = false;
		rubber.shapes.push(shape);
	    }
	    rubber.draw();
	}
    }
/*    var black = true;
    setInterval(function(){

    },500);
*/ 
    rubber.draw();

})
