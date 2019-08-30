let width;
let n = 0,list = [],newarr = [],arr = [];
let zero = {
    x:0,
    y:0
}

const rootwid = 84;

//图片路径&移动时间间隔
const image_url = '../images/20190417203048.png';
const space = 450;

document.getElementById('init').onclick = function(){
    $.ajax({
        url:'./map',
        method:'GET',
        success:function(data){
            n = Number(data.count);
            data.data = data.data.map(v=>v.split(' ').slice(0,v.split(' ').length - 1).map(v=>Number(v)));
            list = data.data[0];
            arr = OneForTwo(list,n);
            newarr = data.data;
            let root = document.getElementById('root'),
                str = '';
            width = (100 / n * 0.01 * rootwid).toString();
            for(let i = 0;i < n;i++){
                for(let j = 0;j < n;j++){
                    if(arr[i][j]!=0){
                        let style = new Style({
                            left:j*width + 'vh',
                            top:i*width+'vh',
                            width: width+'vh',
                            height: width + 'vh',
                            'background-image':`url(${image_url})`,
                            'line-height':width + 'vh',
                            'background-position-y': `-${Math.floor((arr[i][j] - 1) / n) * rootwid/n}vh`,
                            'background-position-x': `-${((arr[i][j] - 1) % n ) * rootwid/n}vh `
                        })
                        str += `<div id='box-${arr[i][j]}' class='item' style='${style.toString()}'>${arr[i][j]}</div>`;

                    }
                    else
                        zero.x = j,zero.y = i;
                }
            }
            root.innerHTML = str;
            $('#root').css({
                'background':`#fff`
            })
            $('#init').css({
                'display':'none'
            })
            setTimeout(begin,1000);

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
        }
    });
}
function begin(){
    for (let i = 1,len = newarr.length; i < len; i++) {
        setTimeout(() => {
            change(OneForTwo(newarr[i], n));
        }, space * i);
    }
}
function OneForTwo(list,n) {
    const arr = new Array(n);
    for(let i = 0;i < n ;i++){
        arr[i] = [];
        for(let j = 0;j < n;j++){
            arr[i][j] =list[i*n+j];
        }
    }
    return arr;
}

function change(newarr) {
    let [y,x] = getID(newarr);
    let id = arr[y][x]
    let obj = {}
    if(y == zero.y){
        if(x > zero.x) obj = { left : `-=${width}vh` }
        else obj = { left : `+=${width}vh` }
    }
    else{
        if( y > zero.y) obj = { top : `-=${width}vh` }
        else obj = { top : `+=${width}vh` }
    }
    Object.assign(zero,{x,y});
    arr = newarr;
    $('#box-'+id).animate(obj);
    function getID(now) {
        let dir = [
            [0,1],
            [0,-1],
            [1,0],
            [-1,0]
        ];
        for (let key of dir) {
            let y = key[0] + zero.y , x =  key[1] + zero.x;
            if( y >= 0 && x >= 0  && x < n && y < n && now[y][x] == 0){
                return [y,x];
            }
        }
    }
}

$('#root').css({
    'background-image':`url(${image_url})`
})
