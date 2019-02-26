document.addEventListener('DOMContentLoaded', function () {
    var c = new ExtendedCanvas('#canvasWrapper',document.getElementById('taskImage').getAttribute('src'),document.getElementById('hint').getAttribute('value'),
        document.getElementById('tutorial').getAttribute('value'));
    c.element.addEventListener('click', function (e) {
        var x = e.pageX - this.offsetLeft;
        var y = e.pageY - this.offsetTop;
        c.fill(x, y);
    });
    c.element.addEventListener('click', function (e) {
        c.drawLines(e)
    });
    c.element.addEventListener('click', function (e) {
        c.drawTrasversalLines(e)
    });
    $("#undoColor").click(function () {
        c.undoColor();
    });
    $("#undoRiga").click(function () {
        c.undoRiga();
    });
    $("#undoRigaTrasversale").click(function () {
        c.undoRigaTrasversale();
    });
    $("#undotoStartTrasversale").click(function () {
        c.undoToStartTrasversale();
    });

    $("#undotoStart").click(function () {
        c.undoToStart();
    });

    $("#undoComponi").click(function () {
        c.undoComponi();
    });
    $("#confermaForm").click(function () {
        $("#output")[0].value = JSON.stringify(c.getOutput().sort());
        if (!c.checkAnswer()) $(".wrongAnswer").show();
        return c.checkAnswer();
    });
    $("#confermaFormCompleta").click(function () {
        c.setOutput($("#text").val());
        $("#output")[0].value = c.getOutput();
        return c.checkAnswer();
    });
    $("#buttonSI").click(function () {
        c.setOutput("SI");
        $("#output")[0].value = c.getOutput();
        $("#buttonSI").click();
    });
    $("#buttonNO").click(function () {
        c.setOutput("NO");
        $("#output")[0].value = c.getOutput();
        $("#buttonNO").click();
    });
    $("#showHint").mousedown(function () {
        c.showAnswer();
    });
    $("#showHint").mouseup(function () {
        c.hideAnswer();
    });
});
