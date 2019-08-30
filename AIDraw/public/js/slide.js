
let n, list = [], arr = [];
let width,height;
let zero = -1;
let num;
const white = '#2486ff',black = '#a2a9b6';
$.ajax({
    url:'./slide',
    data : {
        id:window.location.search.substr(window.location.search.indexOf('=')+1)
    },
    method:'GET',
    success:function(data){
        num = data.count;
        list = data.data[0];
        n = list.length;
        arr = data.data;
        width = window.innerWidth * 0.9 / n ;
        height = width * 0.618;
        $('#root').css({
            height: height + 'px',
            width : window.innerWidth * 0.9 - n + 1 + 'px'
        });
        init();
    }
})

document.getElementById('init').onclick = function(){
    $('#init').css({
        display:'none'
    })
    $('#root').css({
        opacity : 1
    })
    setTimeout(()=>{
        begin();
    },1000);
}

function begin() {
    for (let i = 1,len = arr.length ; i <= len; i++) {
        setTimeout(()=>{
            list = arr[i];
            change(i);
        },1200*i);
    }
}

function change() {
    let newz = list.indexOf('E');
    let style = list[zero] == 'W' ?'lightwhite' : 'lightblack';
    $('#box-'+ newz).addClass(style);
    setTimeout(()=>{
        $('#box-'+ newz)
            .animate({opacity:0})
            .removeClass(style)
        $('#box-'+ zero)
        .css({
            background : list[zero] == 'B' ? black : white
        })
        .animate({opacity:1})
        zero = newz;
    },600)
}

function Style(style){
    return Object.assign(style,{
        toString(){
            let str = '';
            for (const key in style) {
                if(typeof style[key] !== 'function'){
                    str += `${key}:${style[key]};`
                }
            }
            return str;
        }
        
    })
}
function init(){
    let str = '';
    for(let i = 0;i < n ;i ++){
        let style = new Style({
            left: width * i  - i  + 'px',
            width : width + 'px',
            height: height + 'px',
            'line-height':height + 'px',
            background : list[i] == 'B' ? black : white
        });
        if(list[i] == 'E'){
            Object.assign(style,{
                opacity : 0
            })
            zero = i;
        }
        str += `<div id='box-${i}' class='item' style=${style.toString()} ></div>`;
        
    }
    $('#root').html(str);
}