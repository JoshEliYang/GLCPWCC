/**
 * Created by johnson on 2016/11/5.
 */

function openConfigDialog(){

}


/////////////////////////////// diagram function ///////////////////////////////////
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
    if (callback != undefined)
        callback(result);
    return result;
}

/**
 * convert wh to xy
 *
 * @param canvas
 * @param w
 * @param h
 * @param callback
 * @returns {{x: number, y: number}}
 */
function whTOxy(canvas, w, h, callback) {
    var height = canvas.clientHeight;
    var result = {
        x: w - blankX,
        y: height - blankY - h
    };
    if (callback != undefined)
        callback(result);
    return result;
}

/**
 * convert to xy
 *
 * @param canvas
 * @param maxX
 * @param maxY
 * @param datX
 * @param datY
 * @param callback
 * @returns {{x: number, y: number}}
 */
function convertXY(canvas, maxX, maxY, datX, datY, callback) {
    var height = canvas.clientHeight;
    var width = canvas.clientWidth;
    var valueX = (width - blankX) / maxX * datX;
    var valueY = (height - blankY) / maxY * datY;
    var data = {
        x: valueX,
        y: valueY
    };
    if (callback != undefined)
        callback(data);
    return data;
}

/**
 * convert to wh
 *
 * @param canvas
 * @param maxX
 * @param maxY
 * @param datX
 * @param datY
 * @param callback
 * @returns {{w: *, h: number}}
 */
function convertWH(canvas, maxX, maxY, datX, datY, callback) {
    var xy = convertXY(canvas, maxX, maxY, datX, datY);
    return xyTOwh(canvas, xy.x, xy.y, function (data) {
        callback(data);
    });
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

var mutipleLines = new Array();

/**
 * calculate parameters and draw lines
 *
 * @param canvas
 * @param lines
 * @param color
 * @param uncolor
 * @param tagId
 * @param tagName
 * @param maxX
 * @param maxY
 */
function drawMutipleLines(canvas, lines, color, uncolor, tagId, tagName, maxX, maxY) {
    var sub_points = new Array();
    var sub_lines = new Array();
    var unsub_points = new Array();
    var unsub_lines = new Array();
    var sub_values = new Array();
    var unsub_values = new Array();

    var lastSubPoint = convertXY(canvas, maxX, maxY, 0, lines[0].subscribe, function (data) {
        sub_points.push(data);
    });
    var lastUnsubPoint = convertXY(canvas, maxX, maxY, 0, lines[0].unsubscribe, function (data) {
        unsub_points.push(data);
    });
    sub_values.push(lines[0].subscribe);
    unsub_values.push(lines[0].unsubscribe);

    for (var i = 1; i < lines.length; i++) {
        var subPorint = convertXY(canvas, maxX, maxY, i, lines[i].subscribe, function (data) {
            sub_points.push(data);
        });
        var unsubPoint = convertXY(canvas, maxX, maxY, i, lines[i].unsubscribe, function (data) {
            unsub_points.push(data);
        });
        sub_values.push(lines[i].subscribe);
        unsub_values.push(lines[i].unsubscribe);

        var sub_lineParam = drawSingleLine(canvas, lastSubPoint, subPorint, color);
        var unsub_lineParam = drawSingleLine(canvas, lastUnsubPoint, unsubPoint, uncolor);

        sub_lines.push(sub_lineParam);
        unsub_lines.push(unsub_lineParam);
        lastSubPoint = subPorint;
        lastUnsubPoint = unsubPoint;
    }

    drawPoints(canvas, sub_points, color);
    drawPoints(canvas, unsub_points, uncolor);

    mutipleLines.push({
        sub_points: sub_points,
        sub_lines: sub_lines,
        unsub_points: unsub_points,
        unsub_lines: unsub_lines,
        sub_values: sub_values,
        unsub_values: unsub_values,
        color: color,
        uncolor: uncolor,
        tagId: tagId,
        tagName: tagName
    });
}

/**
 * draw single line
 *
 * @param canvas
 * @param point1
 * @param point2
 * @param maxX
 * @param maxY
 * @param color
 */
function drawSingleLine(canvas, point1, point2, color, offset) {
    var context = canvas.getContext("2d");
    context.beginPath();
    context.strokeStyle = color;
    xyTOwh(canvas, point1.x, point1.y, function (data) {
        if (offset != undefined)
            context.moveTo(data.w, data.h + offset);
        else
            context.moveTo(data.w, data.h);
    });
    xyTOwh(canvas, point2.x, point2.y, function (data) {
        if (offset != undefined)
            context.lineTo(data.w, data.h + offset);
        else
            context.lineTo(data.w, data.h);
    });
    context.stroke();
    context.closePath();
    return calculateKB(point1, point2);
}

/**
 * draw points
 *
 * @param canvas
 * @param points
 * @param maxX
 * @param maxY
 * @param color
 */
function drawPoints(canvas, points, color) {
    var context = canvas.getContext("2d");
    for (var i = 0; i < points.length; i++) {
        xyTOwh(canvas, points[i].x, points[i].y, function (data) {
            context.beginPath();
            context.fillStyle = color;
            context.arc(data.w, data.h, 3, 0, 2 * Math.PI, false);
            context.fill();
            context.closePath();
        });
    }
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

var init_data;

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
    init_data = {
        canvasId: canvasId,
        callback: callback
    }
}

/**
 * draw cross line
 *
 * @param canvas
 * @param w
 * @param h
 */
function drawCrossLine(canvas, w, h) {
    var context = canvas.getContext("2d");
    context.beginPath();
    context.fillStyle = "red";
    context.strokeStyle = "red";
    context.moveTo(0, h);
    context.lineTo(canvas.width, h);
    context.moveTo(w, 0);
    context.lineTo(w, canvas.height);
    context.stroke();
    context.closePath();
}

/**
 * draw bold lines
 *
 * @param canvas
 * @param lines
 * @param color
 */
function drawBoldLines(canvas, lines, color) {
    for (var i = 0; i < lines.length; i++) {
        var point1 = lines[i].point1;
        var point2 = lines[i].point2;
        drawSingleLine(canvas, point1, point2, color, 1);
        drawSingleLine(canvas, point1, point2, color, -1);
    }
}

/**
 * draw bold points
 *
 * @param canvas
 * @param points
 * @param color
 */
function drawBoldPoints(canvas, points, color) {
    var context = canvas.getContext("2d");
    for (var i = 0; i < points.length; i++) {
        xyTOwh(canvas, points[i].x, points[i].y, function (data) {
            context.beginPath();
            context.fillStyle = color;
            context.arc(data.w, data.h, 5, 0, 2 * Math.PI, false);
            context.fill();
            context.closePath();
        });
    }
}

/**
 * write value on the diagram
 *
 * @param canvas
 * @param points
 * @param values
 * @param color
 */
function writeValues(canvas, points, values, color) {
    var context = canvas.getContext("2d");
    context.beginPath();
    context.fillStyle = color;
    context.font = "20px Arial";
    for (var i = 0; i < values.length; i++) {
        xyTOwh(canvas, points[i].x + 5, points[i].y - 5, function (data) {
            context.fillText(values[i], data.w, data.h);
        });
    }
    context.fill();
    context.closePath();
}

/**
 * draw bold lines and points
 *
 *
 * @param tagId
 */
function drawBoldByTagId(tagId) {
    var canvas = document.getElementById(init_data.canvasId);
    for (var i = 0; i < mutipleLines.length; i++) {
        if (mutipleLines[i].tagId == tagId) {
            drawBoldLines(canvas, mutipleLines[i].sub_lines, mutipleLines[i].color);
            /*drawBoldLines(canvas, mutipleLines[i].unsub_lines, mutipleLines[i].uncolor);*/
            drawBoldPoints(canvas, mutipleLines[i].sub_points, mutipleLines[i].color);
            /*drawBoldPoints(canvas, mutipleLines[i].unsub_points, mutipleLines[i].uncolor);*/
            writeValues(canvas, mutipleLines[i].sub_points, mutipleLines[i].sub_values, mutipleLines[i].color);
            /*writeValues(canvas, mutipleLines[i].unsub_points, mutipleLines[i].unsub_values, mutipleLines[i].uncolor);*/
            break;
        }
    }
}

/**
 * reset diagram
 */
function resetDiagram() {
    var canvas = document.getElementById(init_data.canvasId);
    var context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height);
    diagramInit(init_data.canvasId, init_data.callback);
}

/**
 * mouse move on canvas
 *
 * @param canvas
 * @param e
 */
function canvasMouseMove(canvas, e) {
    var mouseLocation = getPointOnCanvas(canvas, e.pageX, e.pageY);
    resetDiagram();
    drawCrossLine(canvas, mouseLocation.x, mouseLocation.y);
    isPointInLines(canvas, mouseLocation.x, mouseLocation.y);
}

/**
 * mouse leave canvas
 *
 * @param canvas
 */
function canvasMouseOut(canvas) {
    resetDiagram();
}

/**
 * get point on canvas
 *
 * @param canvas
 * @param x
 * @param y
 * @returns {{x: number, y: number}}
 */
function getPointOnCanvas(canvas, x, y) {
    var bbox = canvas.getBoundingClientRect();
    return {
        x: x - bbox.left * (canvas.width / bbox.width),
        y: y - bbox.top * (canvas.height / bbox.height)
    };
}

/**
 * y=kx+b (calculate k and b)
 *
 * @param point1
 * @param point2
 * @returns {{k: number, b: number}}
 */
function calculateKB(point1, point2) {
    var k = (point2.y - point1.y) / (point2.x - point1.x);
    var b = point1.y - k * point1.x;
    return {
        k: k,
        b: b,
        point1: point1,
        point2: point2
    }
}

/**
 * check out if the point in this line
 *
 * @param point
 * @param line
 * @returns {boolean}
 * @constructor
 */
function PointInlineCheck(point, line) {
    var y = line.k * point.x + line.b;
    if (Math.round(y) != Math.round(point.y))
        return false;
    var maxX = (line.point1.x >= line.point2.x) ? line.point1.x : line.point2.x;
    var minX = (line.point1.x < line.point2.x) ? line.point1.x : line.point2.x;
    var maxY = (line.point1.y >= line.point2.y) ? line.point1.y : line.point2.y;
    var minY = (line.point1.y < line.point2.y) ? line.point1.y : line.point2.y;
    if ((point.x >= minX && point.x <= maxX) && (point.y >= minY && point.y <= maxY)) {
        return true;
    }
    return false;
}

/**
 * find out if the point in these lines
 *
 * @param point
 * @param lines
 * @returns {boolean}
 */
function isPointInLine(point, lines) {
    for (var i = 0; i < lines.length; i++)
        for (var x = point.x - 5; x < point.x + 5; x++)
            for (var y = point.y - 5; y < point.y + 5; y++)
                if (PointInlineCheck({
                        x: x,
                        y: y
                    }, lines[i])) {
                    return true;
                }
    return false;
}

/**
 *
 * @param canvas
 * @param w
 * @param h
 */
function isPointInLines(canvas, w, h) {
    var point = whTOxy(canvas, w, h);
    for (var i = 0; i < mutipleLines.length; i++) {
        if (isPointInLine(point, mutipleLines[i].sub_lines)) {
            drawBoldByTagId(mutipleLines[i].tagId);
            var context = canvas.getContext("2d");
            context.beginPath();
            context.fillStyle = "black";
            context.font = "bold 15px Arial";
            context.fillText(mutipleLines[i].tagName, w, h);
            context.fill();
            context.closePath();
            break;
        }
    }
}

//////////////////////////// end of diagram //////////////////////////////
