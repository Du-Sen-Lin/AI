const express = require('express'),
      path = require('path'),
      fs = require('fs'),
      promisefy = require('util').promisify,
      child_process = require('child_process');
const app = express(),
      exec = promisefy(child_process.exec),
      readFile = promisefy(fs.readFile);
app.use(express.static(path.join(__dirname,'public')))
    .get('/map', async function(req,res){
        read('node test.js','./map/hh4.txt',res);
    })
    .get('/slide',async function(req,res){
        read('node test.js','./map/slide.txt',res);
    })
   .listen(8080);

async function read(cmd, filename,res){
    await exec(cmd);
    let data = await readFile(filename);
    data = data.toLocaleString().split('\r\n');
    const response = { 
        code : 1,  
        count : data[0] 
    };
    data = data.slice(1,data.length - 1)
    response.data = data;
    res.json(response);
}