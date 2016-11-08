/**
 * Created by johnson on 2016/11/5.
 */

var blankX = 30;
var blankY = 25;

/**
 * convert xy to wh
 *
 * @param canvas
 * @param x
 * @param y
 * @param callback
 * @returns {{w: *, h: number}}
 */
function xyTOwh(canvas, x, y, callback) {
    var height = canvas.clientHeight;
    var result = {
        w: x + blankX,
        h: height - blankY - y
    };
    callback(result);
    return result;
}

/**
 * convert xy% to wh
 *
 * @param canvas
 * @param maxX
 * @param maxY
 * @param datX
 * @param datY
 * @param callback
 * @returns {{w: *, h: number}}
 */
function convertXY(canvas, maxX, maxY, datX, datY, callback) {
    var height = canvas.clientHeight;
    var width = canvas.clientWidth;
    var valueX = (width - blankX) / maxX * datX;
    var valueY = (height - blankY) / maxY * datY;
    return xyTOwh(canvas, valueX, valueY, callback);
}

/**
 * mark scale
 *
 * @param canvas
 * @param arrayX
 * @param arrayY
 */
function signTag(canvas, arrayX, arrayY) {
    var height = canvas.clientHeight;
    var width = canvas.clientWidth;
    var context = canvas.getContext("2d");
    context.beginPath();
    context.strokeStyle = "black";
    context.fillStyle = "black";

    var i = 0;
    for (i = 0; i < arrayX.length; i++) {
        var stx = (width - blankX) / arrayX.length * i;

        xyTOwh(canvas, stx, 0, function (data) {
            context.fillText(arrayX[i], data.w - 20, data.h + 20);
            context.moveTo(data.w, data.h);
        });
        xyTOwh(canvas, stx, -10, function (data) {
            context.lineTo(data.w, data.h);
        });
    }
    for (i = 0; i < arrayY.length; i++) {
        var sty = (height - blankY) / arrayY.length * i;

        xyTOwh(canvas, 0, sty, function (data) {
            context.fillText(arrayY[i], 2, data.h - 2);
            context.moveTo(data.w, data.h);
        });
        xyTOwh(canvas, -10, sty, function (data) {
            context.lineTo(data.w, data.h);
        });
    }
    context.stroke();
    context.closePath();
}

/**
 * convert max number to scale array for axis Y
 *
 * @param maxY
 * @returns {Array}
 */
function maxYToArray(maxY) {
    var arrayY = new Array();
    var base = maxY / 8;
    arrayY.push(0);
    arrayY.push(base);
    arrayY.push(base * 2);
    arrayY.push(base * 3);
    arrayY.push(base * 4);
    arrayY.push(base * 5);
    arrayY.push(base * 6);
    arrayY.push(base * 7);
    return arrayY;
}

/**
 * mark scale
 *
 * @param canvas
 * @param arrayX
 * @param maxY
 */
function signAsix(canvas, arrayX, maxY) {
    signTag(canvas, arrayX, maxYToArray(maxY));
}

/**
 * draw line chart
 *
 * @param canvas
 * @param dataArray
 * @param maxY
 * @param color
 */
function drawData(canvas, dataArray, maxY, color) {
    var maxX = dataArray.length;

    var context = canvas.getContext("2d");
    context.beginPath();
    context.strokeStyle = color;
    context.fillStyle = color;

    convertXY(canvas, maxX, maxY, 0, dataArray[0].subscribe, function (data) {
        context.moveTo(data.w, data.h);
    });
    for (var i = 1; i < dataArray.length; i++) {
        convertXY(canvas, maxX, maxY, i, dataArray[i].subscribe, function (data) {
            context.lineTo(data.w, data.h);
        });
    }
    context.stroke();
    context.closePath();
}

/**
 * draw axis
 *
 * @param canvas
 */
function drawAxis(canvas) {
    var context = canvas.getContext("2d");
    context.beginPath();
    context.strokeStyle = "black";
    context.fillStyle = "black";

    context.moveTo(blankX, canvas.height - blankY);
    context.lineTo(canvas.width, canvas.height - blankY);
    context.moveTo(blankX, 0);
    context.lineTo(blankX, canvas.height - blankY);
    context.stroke();
    context.closePath();
}

/**
 * init
 *
 * @param canvasId
 * @param callback
 */
function diagramInit(canvasId, callback) {
    var canvas = document.getElementById(canvasId);
    canvas.height = canvas.clientHeight;
    canvas.width = canvas.clientWidth;
    drawAxis(canvas);

    callback(canvas);
}