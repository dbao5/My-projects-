/**
 * page3.js - a simple JavaScript file that gets loaded with
 * page 3 of Workbook 2 of CS559
 * 
 * started by Michael Gleicher, January 2019
 * modified by Florian Heimerl, August 2019
 * 
 * finished by STUDENT
 */

// we do enable typescript type checking - see
// http://graphics.cs.wisc.edu/WP/cs559-sp2019/typed-js/
// and
// https://github.com/Microsoft/TypeScript/wiki/Type-Checking-JavaScript-Files
// @ts-check

/* Set options for jshint (my preferred linter)
 * disable the warning about using bracket rather than dot
 * even though dot is better 
 * https://stackoverflow.com/questions/13192466/how-to-suppress-variable-is-better-written-in-dot-notation
 */
/* jshint -W069, esversion:6 */

/**
 * Put your code for picture 1 here
 * 
 * Remember to make:
 * - a circle
 * - a triangle
 * - a capsule (two semi-circles with straight lines connecting them)
 * - a sawtooth / mountain
 */
function wb2_pg3_ex1() {
    // use type information to make TypeScript happy
    /** @type {HTMLCanvasElement} */
    let canvas = (/** @type {HTMLCanvasElement} */ document.getElementById("canvas1"));
    if (canvas){
    let context1 = canvas.getContext('2d');
    context1.strokeStyle = "#3ere";
    context1.fillStyle = "#d389ff";
    context1.beginPath();
    context1.arc(55, 55, 30, 0, Math.PI * 2, true);
    context1.lineWidth = 10;
    context1.stroke();
    context1.fill();
    }
    // the student should fill in the rest...

}

/**
 * Put your code for picture 2 here
 */
function wb2_pg3_ex2() {
    // use type information to make TypeScript happy
    /** @type {HTMLCanvasElement} */
    let canvas = (/** @type {HTMLCanvasElement} */ document.getElementById("canvas2"));

    // the student should fill in the rest...

}

/**
 * you don't need to change this
 */
window.onload = function()
{
    wb2_pg3_ex1();
    wb2_pg3_ex2();
}