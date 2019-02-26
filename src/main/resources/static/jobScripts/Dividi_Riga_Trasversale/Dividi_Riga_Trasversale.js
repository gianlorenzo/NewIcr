var ExtendedCanvas = (function () {
    var context, data, dataOrigArr, dataOrig, canvas, output, hint, tutorial, tempOutput, event;

    function ExtendedCanvas(selector, imageSrc, hint2, tutorial2) {
        var wrapper = document.querySelector(selector);
        this.element = canvas = document.createElement('canvas');
        context = this.element.getContext('2d');
        if (hint2 == "") {
            tutorial2 = false;
            hint2 = "[]";
        }
        this.hint = JSON.parse(hint2);
        this.tutorial = tutorial2;
        loadImage.call(this, imageSrc, this, function (image) {
            canvas.setAttribute('width', image.width * 3);
            canvas.setAttribute('height', image.height * 3);
            context.webkitImageSmoothingEnabled = false;
            context.mozImageSmoothingEnabled = false;
            context.imageSmoothingEnabled = false; /// future
            context.drawImage(image, 0, 0, image.width * 3, image.height * 3);
            data = context.getImageData(0, 0, canvas.width, canvas.height);
            dataOrigArr = context.getImageData(0, 0, canvas.width, canvas.height).data;
            dataOrig = canvas.toDataURL();
        });
        wrapper.appendChild(this.element);
        this.output = [];
    }

    function loadImage(src, canvas2, cb) {
        var image = new Image();
        var canvas = this.element;
        $('.componi').hide();
        $('.undoRiga').hide();

        $('.toStart').hide();

        $('.undoColor').hide();
        $('.buttonSi').hide();
        $('.buttonNo').hide();
        image.onload = function () {
            cb(this);
            firstcolor = null;
            checkcolor = true;
            for (i = 0; (i < data.width && checkcolor); i++)
                for (j = 0; (j < data.height && checkcolor); j++) {
                    testcolor = canvas2.getPixelColor(i, j).join();
                    if (testcolor != [255, 255, 255, 255].join())
                        if (!firstcolor)
                            firstcolor = testcolor;
                        else if (firstcolor != testcolor)
                            checkcolor = false;
                }
            if (!canvas2.tutorial) {
                canvas2.setOutput(canvas2.hint);
                canvas2.fillImg("0,0,0,255".split(","));
            }
        }
        image.crossOrigin = 'Anonymous';
        image.src = src;
    }

    ExtendedCanvas.prototype.getPixelIndex = function (x, y) {
        return (Math.floor(y) * canvas.width + Math.floor(x)) * 4;
    }

    ExtendedCanvas.prototype.getPixelColor = function (x, y) {
        var index = this.getPixelIndex(x, y);
        var d = data.data;
        var r = d[index];
        var g = d[index + 1];
        var b = d[index + 2];
        var a = d[index + 3];
        return [r, g, b, a];
    }
    ExtendedCanvas.prototype.getOriginalPixelColor = function (x, y) {
        var index = this.getPixelIndex(x, y);
        var d = dataOrigArr;
        var r = d[index];
        var g = d[index + 1];
        var b = d[index + 2];
        var a = d[index + 3];
        return [r, g, b, a];
    }


    ExtendedCanvas.prototype.setPixelColor = function (x, y, color) {
        var index = this.getPixelIndex(x, y);
        var d = data.data;
        d[index] = color[0];
        d[index + 1] = color[1];
        d[index + 2] = color[2];
        d[index + 3] = color[3];
    }


    ExtendedCanvas.prototype.fillImg = function (fillColor) {
        var stack = [];
        context.fillStyle = fillColor;
        for (var i = 0; i < canvas.width; i++)
            for (var j = 0; j < canvas.height; j++)
                stack.push([i, j]);

        while (stack.length > 0) {
            var position = stack.pop();
            var posX = position[0];
            var posY = position[1];
            var posColor = this.getPixelColor(posX, posY).join();
            for (k in this.output)
                if (posColor === this.output[k]) {
                    this.setPixelColor(posX, posY, fillColor);
                }
        }
        context.putImageData(data, 0, 0);
    }


    ExtendedCanvas.prototype.setOutput = function (out) {
        this.output = out;
    }

    ExtendedCanvas.prototype.getOutput = function () {
        return this.output;
    }

    ExtendedCanvas.prototype.drawTrasversalLines = function() {
        var startX = 0;
        var startY = 0;
        var isDown;
        mouseClick = this.output;
        $("#canvasWrapper").bind("mousedown", function (e) {
            e.preventDefault();
            e.stopPropagation();
            var mouseX = e.offsetX;
            var mouseY = e.offsetY;
            isDown = true;
            startX = mouseX;
            startY = mouseY;

        });
        $("#canvasWrapper").bind("mouseup", function (e) {
            e.preventDefault();
            e.stopPropagation();
            if(!isDown) return;
            var mouseX = e.offsetX;
            var mouseY = e.offsetY;
            // draw the current line
            context.beginPath();
            context.moveTo(startX, startY);
            context.lineTo(mouseX, mouseY);
            context.stroke();
            context.closePath()
            isDown = false;
            mouseClick.push([startX,startY,mouseX,mouseY]);
            mouseClick.removeDuplicates();



        });

        $("#canvasWrapper").mouseout(function (e) {
            e.preventDefault();
            e.stopPropagation();
            if (!isDown) {
                return;
            }
            isDown = false;
        });
    }

    Array.prototype.removeDuplicates = function() {
        var input = this;
        var hashObject = new Object();
        for (var i = input.length - 1; i >= 0; i--) {
            var currentItem = input[i];

            if (hashObject[currentItem] == true) {
                input.splice(i, 1);
            }
            hashObject[currentItem] = true;
        }
        return input;
    }

    ExtendedCanvas.prototype.undoRigaTrasversale = function () {
        o = this.output;
        if (o.length > 0) {
            o.splice(-1,1);
            var canvasPic = new Image();
            canvasPic.src = dataOrig;
            canvasPic.onload = function () {
                context.drawImage(canvasPic, 0, 0);
                for (var i = 0; i < o.length; i++) {
                    console.log("pop:"+o);
                    for(var y=0;y<o[i].length;y++) {
                        context.beginPath();
                        context.moveTo(o[i][0],o[i][1]);
                        context.lineTo(o[i][2],o[i][3]);
                        context.stroke();
                        context.closePath();
                    }
                }
            };
        }

    }


    ExtendedCanvas.prototype.undoToStartTrasversale = function () {
        var canvasPic = new Image();
        this.output = [];
        canvasPic.src = dataOrig;
        canvasPic.onload = function () {
            context.drawImage(canvasPic, 0, 0);
            data = context.getImageData(0, 0, canvas.width, canvas.height);
        };


    }

    ExtendedCanvas.prototype.checkAnswer = function () {
        if (!this.tutorial) return true;
        temp = this.output.sort();
        if (temp.length != this.hint.length) return false;
        for (i = 0; i < temp.length; i++) {
            if (!temp[i].equals(this.hint[i]))
                return false;
        }
        return true;
    }

    ExtendedCanvas.prototype.showAnswer = function () {
        this.tempOutput = this.output;
        tempthis = this;
        var canvasPic = new Image();
        this.output = [];
        canvasPic.src = dataOrig;
        canvasPic.onload = function () {
            context.drawImage(canvasPic, 0, 0);
            data = context.getImageData(0, 0, canvas.width, canvas.height);
            tempthis.setOutput(tempthis.hint);
            tempthis.fillImg("0,0,0,255".split(","));

        };
    }

    ExtendedCanvas.prototype.hideAnswer = function () {
        var canvasPic = new Image();
        tempthis = this;
        this.setOutput(this.tempOutput);
        canvasPic.src = dataOrig;
        canvasPic.onload = function () {
            context.drawImage(canvasPic, 0, 0);
            data = context.getImageData(0, 0, canvas.width, canvas.height);
            tempthis.fillImg("0,0,0,255".split(","));
        };
    }
    return ExtendedCanvas;
})();

