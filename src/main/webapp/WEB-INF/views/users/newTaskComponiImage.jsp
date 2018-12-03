<%--
  Created by IntelliJ IDEA.
  User: gianlorenzo
  Date: 28/11/18
  Time: 9.01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>

    <title>ICR</title>

    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>ICR</title>

    <!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/newTaskWord.css" />
    <!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
    <!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

    <!-- Favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico" />

    <style>
        .selectchar, .wrongAnswer {
            display: none;
        }
    </style>


</head>
<body class="landing">

<ul>
    <li>
        <h2> ${student.name} ${student.surname} </h2>
        <h2>Stai svolgendo il task ${student.taskEffettuati}</h2>
    </li>
</ul>

<div align="center">

        <h3>
            Nella seguente parola ci sono delle parti mancanti.
        </h3>

</div>
<form:form method="post" action="secondConsoleComponiWord"
           modelAttribute="taskResults" name="form">
    <div align="center">
        <table>
            <c:forEach varStatus="vs" var="result"
                       items="${taskResults.resultList}">
                <div id="canvasWrapper" style="height: 200px" >
                </div> <!-- immagine da etichettare -->
                </div>
                <div>
                    <h3 class="compose">
                        Scrivi nell'area di testo sottostante
                        <br/> quella che secondo te è la parola completa
                    </h3>
                    <input type="text" id="text" name="name" class=""   />
                </div>
                <button type=button id="undotoStart" class="selectword">RICOMINCIA</button>
                <button type=button id="undo" class="selectword">ANNULLA</button>
                <div  class="selectchar"><br><br><br></div>
                <button type=button id="buttonSI" class="selectchar">SI</button>
                <button type=button id="buttonNO" class="selectchar">NO</button>
                <form:input type="hidden" id="output"
                            path="resultList[${vs.index}].answer" />
                <br>

                <form:hidden path="resultList[${vs.index}].id" />
                <form:hidden path="resultList[${vs.index}].image.id" />
                <form:hidden path="resultList[${vs.index}].task.id" />
                <form:hidden path="resultList[${vs.index}].task.student.id" />
                <form:hidden path="resultList[${vs.index}].task.batch" />
                <form:hidden path="resultList[${vs.index}].task.job.id" />
                <form:hidden path="resultList[${vs.index}].task.startDate" />
            </c:forEach>
        </table>

    <div align="center" class="selectword">
        <input type="submit" name="action" id="confermaForm" value="Conferma e vai al prossimo task">
        <!--<input name="action" id="confermaForm" value="Conferma e vai al prossimo task">-->
    </div>
    <div align="center" class="wrongAnswer">
        <br>
        <h3><font color="red">Risposta sbagliata, vuoi vedere la soluzione?</font></h3>
        <button type=button id="showHint"><font color="red">Tieni premuto qui per vedere la soluzione</font></button>
        <br> <br>
    </div>
    <br/>
</form:form>
<div align="center">
    <form:form method="post" action="homeStudent">
        <input type="submit" value="Torna alla pagina dello studente">
    </form:form>
</div>

<script>

    var ExtendedCanvas = (function() {
        var context, data, dataOrigArr, dataOrig, canvas, output, hint, tutorial, tempOutput,t;
        function ExtendedCanvas(selector, imageSrc, hint2, tutorial2) {
            var wrapper = document.querySelector(selector);
            this.element = canvas = document.createElement('canvas');
            context = this.element.getContext('2d');
            t = document.getElementById("chatinput");
            if (hint2 == "") {
                tutorial2 = false;
                hint2 = "[]";
            }
            this.hint = JSON.parse(hint2);
            this.tutorial = tutorial2;
            loadImage.call(this, imageSrc, this, function(image) {
                canvas.setAttribute('width', image.width*3); <!-- centra l'immagine' -->
                canvas.setAttribute('height', image.height*3); <!-- centra l'immagine' -->
                context.webkitImageSmoothingEnabled = false;
                context.mozImageSmoothingEnabled = false;
                context.imageSmoothingEnabled = false; /// future
                context.drawImage(image,0,0,image.width*3, image.height*3);
                data = context.getImageData(0,0,canvas.width, canvas.height);
                dataOrigArr = context.getImageData(0,0,canvas.width, canvas.height).data;
                dataOrig = canvas.toDataURL();
            });
            wrapper.appendChild(this.element);
            this.output = [];
        }
        function loadImage(src, canvas2, cb) {
            var image = new Image();
            var canvas = this.element;
            image.onload = function() {
                cb(this);
                firstcolor = null;
                checkcolor = true;
                for (i=0;(i<data.width && checkcolor);i++)
                    for (j=0;(j<data.height && checkcolor);j++) {
                        testcolor = canvas2.getPixelColor(i, j).join();
                        if (testcolor != [255,255,255,255].join())
                            if (!firstcolor)
                                firstcolor = testcolor;
                            else if (firstcolor != testcolor)
                                checkcolor = false;
                    }
                if (checkcolor) {  $(".selectchar,.selectword").toggle(); }

            }
            image.crossOrigin = 'Anonymous';
            image.src = src;
        }


        ExtendedCanvas.prototype.getPixelIndex = function(x, y) {
            return (Math.floor(y) * canvas.width + Math.floor(x)) * 4;
        }

        ExtendedCanvas.prototype.selectSingleColor = function(x, y) {
            firstcolor = null;
            checkcolor = true;
            for (i=0;(i<data.width && checkcolor);i++)
                for (j=0;(j<data.height && checkcolor);j++) {
                    testcolor = this.getPixelColor(i, j).join();
                    if (testcolor != [255,255,255,255].join())
                        checkcolor = false;
                    this.fill(i,j);
                }
        }


        ExtendedCanvas.prototype.fill = function(x, y, fillColor) {
            if(x < 0 || y < 0 || x > canvas.width || y > canvas.height) {
                return;
            }

            fillColor = fillColor || [0,0,0,255];
            var color = this.getPixelColor(x, y).join();

            if(color === fillColor || color === [255,255,255,255].join()) {
                return;
            }

            if(color === fillColor.join()) {
                return;
            }
            this.output.push(color);
            temp = [];
            this.fillImg(fillColor);
        }

        ExtendedCanvas.prototype.getPixelColor = function(x, y) {
            var index = this.getPixelIndex(x, y);
            var d =  data.data;
            var r = d[index];
            var g = d[index + 1];
            var b = d[index + 2];
            var a = d[index + 3];
            return [r, g, b, a];
        }

        ExtendedCanvas.prototype.getOriginalPixelColor = function(x, y) {
            var index = this.getPixelIndex(x, y);
            var d =  dataOrigArr;
            var r = d[index];
            var g = d[index + 1];
            var b = d[index + 2];
            var a = d[index + 3];
            return [r, g, b, a];
        }

        ExtendedCanvas.prototype.setPixelColor = function(x, y, color) {
            var index = this.getPixelIndex(x, y);
            var d = data.data;
            d[index] = color[0];
            d[index + 1] = color[1];
            d[index + 2] = color[2];
            d[index + 3] = color[3];
        }

        ExtendedCanvas.prototype.setOutput = function(out) {
            this.output = out.slice();
        }

        ExtendedCanvas.prototype.getOutput = function() {
            return this.output;
        }

        ExtendedCanvas.prototype.checkAnswer = function() {

            if (!this.tutorial) return true;
            temp = this.output.sort();
            if (temp.length != this.hint.length) return false;
            for (i=0; i<temp.length; i++){
                if (!temp[i].equals(this.hint[i]))
                    return false;
            }
            return true;
        }

        ExtendedCanvas.prototype.showAnswer = function() {
            this.tempOutput = this.output;
            tempthis = this;
            var canvasPic = new Image();
            this.output = [];
            canvasPic.src = dataOrig;
            canvasPic.onload = function() {
                context.drawImage(canvasPic, 0, 0);
                data = context.getImageData(0,0,canvas.width, canvas.height);
                tempthis.setOutput(tempthis.hint);
                tempthis.fillImg("0,0,0,255".split(","));

            };
        }

        ExtendedCanvas.prototype.hideAnswer = function() {
            var canvasPic = new Image();
            tempthis = this;
            this.setOutput(this.tempOutput);
            canvasPic.src = dataOrig;
            canvasPic.onload = function() {
                context.drawImage(canvasPic, 0, 0);
                data = context.getImageData(0,0,canvas.width, canvas.height);
                tempthis.fillImg("0,0,0,255".split(","));
            };
        }


        ExtendedCanvas.prototype.fillImg = function(fillColor) {
            var stack = [];
            context.fillStyle = fillColor;
            for (var i = 0; i<canvas.width;i++)
                for (var j = 0; j<canvas.height;j++)
                    stack.push([i, j]);

            while(stack.length > 0) {
                var position = stack.pop();
                var posX = position[0];
                var posY = position[1];
                var posColor = this.getPixelColor(posX, posY).join();
                for (k in this.output)
                    if(posColor === this.output[k]) {
                        this.setPixelColor(posX, posY, fillColor);
                    }
            }
            context.putImageData(data, 0, 0);
        }

        ExtendedCanvas.prototype.undo = function() {

            document.getElementById('text').value = '';
        }

        ExtendedCanvas.prototype.undoToStart = function() {

            var canvasPic = new Image();
            this.output = [];
            canvasPic.src = dataOrig;
            canvasPic.onload = function() {
                context.drawImage(canvasPic, 0, 0);
                data = context.getImageData(0,0,canvas.width, canvas.height);
            };
        }
        return ExtendedCanvas;
    })();

    document.addEventListener('DOMContentLoaded', function() {
        var c = new ExtendedCanvas('#canvasWrapper', '${pageContext.request.contextPath}/${taskResults.resultList[0].image.path}', '${hint}', ${taskResults.resultList[0].task.job.tutorial});

        $("#undo").click(function() {
            c.undo();
        });
        $("#undotoStart").click(function() {
            c.undoToStart();
        });
        $("#testbutton").click(function() {
            $(".selectchar,.selectword").toggle();
        });
        $("#confermaForm").click(function() {
            c.setOutput($("#text").val());
            $("#output")[0].value = JSON.stringify(c.getOutput());
            return c.checkAnswer();
        });
        $("#buttonSI").click(function() {
            c.selectSingleColor();
            $("#confermaForm").click();
        });
        $("#buttonNO").click(function() {
            $("#confermaForm").click();
        });
        $("#showHint").mousedown(function() {
            c.showAnswer();
        });
        $("#showHint").mouseup(function() {
            c.hideAnswer();
        });

    });

</script>

<script src="js/selectAll.js"></script>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<!--[if lte IE 8]><script src="/js/ie/respond.min.js"></script><![endif]-->
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/backButton.js"></script>

</body>
</html>
