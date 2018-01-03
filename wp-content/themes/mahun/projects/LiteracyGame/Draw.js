var rubber;
var img;

function Rubber(canvasID) {
    this.canvas = $('#canvas');
    this.undo = $('#undo');
    this.ctx = this.canvas[0].getContext('2d');
    this.width = this.canvas.attr('width');
    this.height = this.canvas.attr('height');
    this.rect = this.canvas[0].getBoundingClientRect();
    this.background = '#FFFFFF';
    this.canvas.css('cursor','pointer');
    this.ctx.font = '64pt Arial';
    this.ctx.textAlign = 'center';
}

Rubber.prototype.draw = function() {
    this.ctx.clearRect(0,0,this.width,this.height);
    //this.background = cBackground;                                                                                                                  
    this.ctx.fillStyle = this.background;
    this.ctx.fillRect(0, 0, this.width, this.height);
    this.ctx.drawImage(img,475,0);
}


// main                                                                                                                                               
$(document).ready(function(){
    initComponents();
    rubber.draw();
});

// methods                                                                                                                                            
function initComponents()
{
    rubber = new Rubber();
    img = document.getElementById("player");
}